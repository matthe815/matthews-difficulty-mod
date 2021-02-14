package difficultymod.api.stamina;

import difficultymod.core.ConfigHandler;
import difficultymod.stamina.*;
import net.minecraft.entity.player.EntityPlayer;

/**
 * The entry point to any stamina behavior.
 * @author Matthew
 *
 */
public class StaminaHelper 
{
	/**
	 * Retrieve a player's stamina capability, allowing you to access the methods.
	 * @param player
	 * @return
	 */
    public static IStamina GetPlayer(EntityPlayer player)
    {
        return player.getCapability(StaminaProvider.STAMINA, null);
    }
    
    /**
     * Get the usage for a specific action.
     * @param action
     * @return
     */
    public static float GetUsage(String action)
    {
    	for (String actionValue : ConfigHandler.common.staminaSettings.actionDepletion) {
    		if (actionValue.startsWith(action))
    			return Integer.parseInt(actionValue.replaceAll("\\D\\g", ""));
    	}
    	
    	return 0;
    }
    
    /**
     * Get the usage for a specific action.
     * @param action
     * @return
     */
    public static float GetUsage(ActionType action)
    {
    	return GetUsage(action.toString().toLowerCase());
    }
}
