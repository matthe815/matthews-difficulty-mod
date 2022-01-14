package difficultymod.api.capability;

import difficultymod.capabilities.stamina.IStamina;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.capabilities.temperature.ITemperature;
import difficultymod.capabilities.temperature.TemperatureCapability;
import difficultymod.capabilities.temperature.TemperatureProvider;
import difficultymod.capabilities.thirst.IThirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import net.minecraft.entity.player.EntityPlayer;

public class CapabilityHelper {

	public static IThirst GetThirst (EntityPlayer player) {
		if ((ConfigHandler.common.thirstSettings.disableThirst) || !player.hasCapability ( ThirstProvider.THIRST, null )) return null;
			
		ThirstCapability thirst = (ThirstCapability) player.getCapability ( ThirstProvider.THIRST, null );
		thirst.SetPlayer(player);
		
		return thirst;
	}
	
	public static IStamina GetStamina (EntityPlayer player) {
		StaminaCapability stamina = (StaminaCapability) player.getCapability ( StaminaProvider.STAMINA, null );
		stamina.SetPlayer(player);
		
		return stamina;
	}
	
	public static ITemperature GetTemperature (EntityPlayer player) {
		TemperatureCapability temperature = (TemperatureCapability) player.getCapability ( TemperatureProvider.TEMPERATURE, null );
		temperature.SetPlayer(player);
		
		return temperature;
	}
	
}
