package difficultymod.capabilities.temperature;

import java.util.HashMap;
import java.util.Map;

import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import difficultymod.core.init.PotionInit;
import difficultymod.items.TemperatureArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

/***
 * All of the methods required to manipulate the player's temperature.
 * @author Matthew
 *
 */
public class TemperatureCapability implements ITemperature
{
	private Temperature lastTemperature = Temperature.NORMAL;
	private EntityPlayer player;
	
	private Map < String, Modifier > modifiers = new HashMap < String, Modifier > ( );
	private double[] seasonTemps =  new double[] { 1.2f, -1.4f, -0.5f, 0.3f };
    
	public TemperatureCapability() {}
	
	public boolean HasChanged()
	{
		return this.Get()!=lastTemperature;
	}
	
	public void onSendClientUpdate()
	{
		this.lastTemperature = this.Get();
	}
	
	public void SetPlayer(EntityPlayer player)
	{
		this.player = player;
	}

	/**
	 * Retrieve the temperature penalty as result of a player's armor.
	 */
	@Override
	public int GetPlayerArmorPenalty(EntityPlayer player) {
		int temp = 0;
		
		for (ItemStack stack : player.getArmorInventoryList())
			if (stack.getItem() instanceof TemperatureArmor)
				temp += ( (TemperatureArmor) stack.getItem() ).GetWarmth() + 0.2f;
			else
				temp += 0.2f;
		
		return temp;
	}
	
	public double GetPlayerWetPenalty ( EntityPlayer player ) {
		double temp = 0;
		
		for (ItemStack stack : player.getArmorInventoryList())
		{
			if (stack.getItem() instanceof TemperatureArmor)
			{
				if (((TemperatureArmor)stack.getItem()).IsDrenchable() || ((ItemArmor)stack.getItem()).getArmorMaterial()==ArmorMaterial.LEATHER) // If the armor is drenchable or leather.
					temp-=0.7f;
			}
		}
		
		return temp;
	}

	
	public Modifier [ ] GetModifiers () {
		return modifiers.values().toArray ( new Modifier [ 0 ] );
	}
	
	/**
	 * Add a modifier to the player.
	 * @param modifier
	 */
	public void Add (Modifier modifier) {
		modifiers.put(modifier.GetID(), modifier);
	}
	
	private boolean IsWetAtPosition ( ) {
		return player.isInWater ( ) || player.world.isRainingAt ( player.getPosition ( ) );
	}

	/**
	 * Get the current temperature with modifiers applied.
	 * @return
	 */
	@Override
	public Temperature Get() {
		if (player == null || player.isCreative() || ConfigHandler.common.temperatureSettings.disableTemperature || player.getActivePotionEffect ( PotionInit.TEMPERATURE_IMMUNITY ) != null)
			return Temperature.NORMAL;
		
		float temp = player.world.getBiome(player.getPosition()).getTemperature(player.getPosition())*10+2.4f;
		
		if (ConfigHandler.Debug_Options.showUpdateMessages)
			System.out.println(player.world.getBiome(player.getPosition()).getBiomeName() + ": " + temp);
		
		///
		// Modifier controller.
		///
		if (!player.world.isDaytime())
			Add ( new Modifier ( "Time of Day", - ( player.world.getBiome ( player.getPosition( ) ).getBiomeClass().equals( Biomes.DESERT.getBiomeClass( ) ) ? 6 : 1 ), 0 ) );

		Add ( new Modifier ( "Clothing", GetPlayerArmorPenalty(player), 0 ) ); // Apply the armor warmth value -- all armor applies a base 0.2 increase.
		
		if ( IsWetAtPosition ( ) ) // Detect wetness.
			Add ( new Modifier ( "Wetness", -1.5 + GetPlayerWetPenalty(player), 0 ) );
		
		// Serene seasons compatibility layer.
		if ( Loader.isModLoaded ( "sereneseasons" ) ) {
			Season season = SeasonHelper.getSeasonState(player.world).getSeason();
			Add ( new Modifier ( "Season", seasonTemps [ season.ordinal() ], 0 ) );
		}
		

		if (player.isSprinting()) // You're a bit warmer when running.
			Add( new Modifier ( "Sprinting", 2.0, 0 ) );
		
		for (Modifier modifier : this.GetModifiers()) {
			temp += modifier.GetDeviance();
			modifier.Tick();
			
			if (modifier.GetRemainingTicks() <= 0) modifiers.remove(modifier.GetID()); // remove modifier out of ticks.
		}
		
		// Obtain the thirst capability, returns null if disabled.
		ThirstCapability capability = player.hasCapability(ThirstProvider.THIRST, null) ? (ThirstCapability)player.getCapability(ThirstProvider.THIRST, null) : null;
		
		// Apply a sweat-effect, a heat resistance effective as long as you have avaliable thirst.
		if (temp > ConfigHandler.common.temperatureSettings.burningThreshold && capability.Get().thirst > 10) {
			capability.Add( new Thirst().SetExhaustion(0.4) );
			return Temperature.NORMAL;
		}
		
		if (temp < ConfigHandler.common.temperatureSettings.freezingThreshold && player.getActivePotionEffect(PotionInit.COLD_RESISTANCE) == null)
			return Temperature.FREEZING;
		else if (temp > ConfigHandler.common.temperatureSettings.burningThreshold && player.getActivePotionEffect(PotionInit.HEAT_RESISTANCE) == null)
			return Temperature.BURNING;
		
		return Temperature.NORMAL;
	}

	@Override
	public void OnTick(Phase phase) {
		// TODO Auto-generated method stub
		
	}
}
