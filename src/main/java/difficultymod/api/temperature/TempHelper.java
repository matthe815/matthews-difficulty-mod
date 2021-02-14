package difficultymod.api.temperature;

import difficultymod.temperature.ITemp;
import difficultymod.temperature.TempProvider;
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
	 * @param player
	 * @return
	 */
    public static ITemp GetPlayer(EntityPlayer player)
    {
        return player.getCapability(TempProvider.TEMPERATURE, null);
    }
    
    /**
     * Return the number of ticks until the temperature updates as defined in the config.
     * @return
     */
    public static float GetMaxUpdateTicks()
    {
    	return 10;
    }
}
