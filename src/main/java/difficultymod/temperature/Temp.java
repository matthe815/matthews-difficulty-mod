package difficultymod.temperature;

import difficultymod.core.ConfigHandler;
import difficultymod.core.init.PotionInit;
import difficultymod.items.TemperatureArmor;
import difficultymod.thirst.ThirstProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.fml.common.Loader;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

/***
 * All of the methods required to manipulate the player's temperature.
 * @author Matthew
 *
 */
public class Temp implements ITemp 
{
	private Temperature lastTemperature = Temperature.NORMAL;
	private int temperaturePoint = 0;
	private EntityPlayer player = null;
    
	public Temp(){}

	/**
	 * Returns whether or not the temperature has changed at all since the last packet.
	 * @apiNote Used for networking.
	 * @return
	 */
	public void SetPlayer(EntityPlayer player)
	{
		this.player = player;
	}
	
	public boolean HasChanged()
	{
		return this.GetTemperature(player)!=lastTemperature;
	}
	
	public void onSendClientUpdate()
	{
		this.lastTemperature = this.GetTemperature(player);
	}
	

	@Override
	public int GetTemperaturePoint() {
		return temperaturePoint;
	}

	@Override
	public void AddTemperaturePoint() {
		temperaturePoint++;
	}
	
	@Override
	public void ResetTemperaturePoint() {
		temperaturePoint = 0;
	}

	/**
	 * Retrieve the temperature penalty as result of a player's armor.
	 */
	@Override
	public int GetPlayerArmorPenalty(EntityPlayer player) {
		int temp = 0;
		for (ItemStack stack : player.getArmorInventoryList())
			if (stack.getItem() instanceof TemperatureArmor)
				temp+=((TemperatureArmor)stack.getItem()).GetWarmth()+0.2f;
			else
				temp+=0.2f;
		
		return temp;
	}

	@Override
	public Temperature GetTemperature(EntityPlayer player) {
		if (this.player==null) // Cache the player.
			this.player = player;
		
		if (player.isCreative())
			return Temperature.NORMAL;
		
		float temp = player.world.getBiome(player.getPosition()).getTemperature(player.getPosition())*10+2.4f;
		System.out.println(player.world.getBiome(player.getPosition()).getBiomeName() + ": " + temp);
		
		if (player.getActivePotionEffect(PotionInit.TEMPERATURE_IMMUNITY)!=null)
			return Temperature.NORMAL;
		
		///
		// Modifier controller.
		///
		if (!player.world.isDaytime())
			temp-=player.world.getBiome(player.getPosition()).getBiomeName()=="desert" ? 6 : 1;

		temp+=GetPlayerArmorPenalty(player); // Apply the armor warmth value -- all armor applies a base 0.2 increase.
		
		if (player.isInWater() || player.world.isRainingAt(player.getPosition())) {
			temp-=1.5f;
			
			for (ItemStack stack : player.getArmorInventoryList())
				if (stack.getItem() instanceof TemperatureArmor)
					if (((TemperatureArmor)stack.getItem()).IsDrenchable() || ((ItemArmor)stack.getItem()).getArmorMaterial()==ArmorMaterial.LEATHER) // If the armor is drenchable or leather.
						temp-=0.7f;
		}
		
		// Serene seasons compatibility layer.
		if (Loader.isModLoaded("sereneseasons")) {
			Season season = SeasonHelper.getSeasonState(player.world).getSeason();
			
			switch (season) {
			case SUMMER:
				temp+=1.2f;
				break;
			case WINTER:
				temp-=1.4f;
				break;
			case AUTUMN:
				temp-=0.5f;
				break;
			case SPRING:
				temp+=0.3f;
				break;
			}
		}
			
		
		if (player.isSprinting())
			temp+=2;
		
		if (temp>14&&player.getCapability(ThirstProvider.THIRST, null).GetThirst()>10&&player.getActivePotionEffect(PotionInit.HEAT_RESISTANCE)==null) {
			player.addPotionEffect(new PotionEffect(PotionInit.HEAT_RESISTANCE, 100, 0));
			player.getCapability(ThirstProvider.THIRST, null).SetThirst(player.getCapability(ThirstProvider.THIRST, null).GetThirst()-1);
		}
		
		this.AddTemperaturePoint();
		
		if (temp<ConfigHandler.common.temperatureSettings.freezingThreshold&&player.getActivePotionEffect(PotionInit.HEAT_RESISTANCE)==null)
			return Temperature.FREEZING;
		else if (temp > ConfigHandler.common.temperatureSettings.burningThreshold&&player.getActivePotionEffect(PotionInit.HEAT_RESISTANCE)==null)
			return Temperature.BURNING;
		else
			return Temperature.NORMAL;
	}

	@Override
	public void Update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void SetTemperature(Temperature temp) {
		this.lastTemperature = temp;
	}
}
