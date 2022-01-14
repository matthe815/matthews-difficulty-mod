package difficultymod.capabilities.temperature;

import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import difficultymod.core.init.PotionInit;
import difficultymod.items.TemperatureArmor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.nbt.NBTBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
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
				temp+=((TemperatureArmor)stack.getItem()).GetWarmth()+0.2f;
			else
				temp+=0.2f;
		
		return temp;
	}

	/**
	 * Get the current temperature with modifiers applied.
	 * @return
	 */
	@Override
	public Temperature Get() {
		if (player == null)
			return Temperature.NORMAL;
		
		if (player.isCreative() || ConfigHandler.common.temperatureSettings.disableTemperature)
			return Temperature.NORMAL;
		
		float temp = player.world.getBiome(player.getPosition()).getTemperature(player.getPosition())*10+2.4f;
		
		if (ConfigHandler.Debug_Options.showUpdateMessages)
			System.out.println(player.world.getBiome(player.getPosition()).getBiomeName() + ": " + temp);
		
		if (player.getActivePotionEffect(PotionInit.TEMPERATURE_IMMUNITY)!=null)
			return Temperature.NORMAL;
		
		///
		// Modifier controller.
		///
		if (!player.world.isDaytime())
			temp-=player.world.getBiome(player.getPosition()).getBiomeName()=="desert" ? 6 : 1;

		temp+=GetPlayerArmorPenalty(player); // Apply the armor warmth value -- all armor applies a base 0.2 increase.
		
		if (player.isInWater() || player.world.isRainingAt(player.getPosition())) 
		{
			temp-=1.5f; // Apply a base temperature reduction in water.
			
			for (ItemStack stack : player.getArmorInventoryList())
			{
				if (stack.getItem() instanceof TemperatureArmor)
				{
					if (((TemperatureArmor)stack.getItem()).IsDrenchable() || ((ItemArmor)stack.getItem()).getArmorMaterial()==ArmorMaterial.LEATHER) // If the armor is drenchable or leather.
						temp-=0.7f;
				}
			}
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
			
		
		if (player.isSprinting()) // You're a bit warmer when running.
			temp+=2;
		
		// Obtain the thirst capability, returns null if disabled.
		ThirstCapability capability = player.hasCapability(ThirstProvider.THIRST, null) ? (ThirstCapability)player.getCapability(ThirstProvider.THIRST, null) : null;
		
		// Apply a sweat-effect, a heat resistance effective as long as you have avaliable thirst.
		if (temp>14 && capability.Get().thirst > 10 && player.getActivePotionEffect(PotionInit.HEAT_RESISTANCE)==null) 
		{
			player.addPotionEffect(new PotionEffect(PotionInit.HEAT_RESISTANCE, 100, 0));
			capability.Remove(new Thirst().SetThirst(1));
		}
		
		if (temp < ConfigHandler.common.temperatureSettings.freezingThreshold && player.getActivePotionEffect(PotionInit.COLD_RESISTANCE) == null)
			return Temperature.FREEZING;
		else if (temp > ConfigHandler.common.temperatureSettings.burningThreshold && player.getActivePotionEffect(PotionInit.HEAT_RESISTANCE) == null)
			return Temperature.BURNING;
		else
			return Temperature.NORMAL;
	}

	@Override
	public void OnTick(Phase phase) {
		// TODO Auto-generated method stub
		
	}
}
