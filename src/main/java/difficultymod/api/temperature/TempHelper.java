package difficultymod.api.temperature;

import difficultymod.capabilities.temperature.TemperatureCapability;
import difficultymod.capabilities.temperature.TemperatureProvider;
import net.minecraft.entity.player.EntityPlayer;

/**
 * The entry point to any temperature behavior.
 * @author Matthew
 *
 */
public class TempHelper 
{
	/**
	 * Retrieve a player's temperature capability, allowing you to access any of the methods.
	 * @param player The player.
	 * @return The temperature capability.
	 */
    public static TemperatureCapability GetPlayer(EntityPlayer player)
    {
        return (TemperatureCapability)player.getCapability(TemperatureProvider.TEMPERATURE, null);
    }
    
    /**
     * Return the number of ticks until the temperature updates as defined in the config.
     * @return The ticks until temperature update.
     */
    public static float GetMaxUpdateTicks()
    {
    	return 10;
    }
}
