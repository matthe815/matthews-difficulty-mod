package difficultymod.api.stamina;

import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.core.ConfigHandler;
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
	 * @param player The player to derive the capability from.
	 * @return The stamina capability.
	 */
    public static StaminaCapability GetPlayer(EntityPlayer player)
    {
        return ( StaminaCapability ) player.getCapability( StaminaProvider.STAMINA, null );
    }
    
    /**
     * Get the usage for a specific action based on a supplied action string.
     * @param action A stringified action name.
     * @return The total stamina usage.
     */
    public static float GetUsage(String action)
    {
    	for (String actionValue : ConfigHandler.common.staminaSettings.actionDepletion) {
    		if ( actionValue.startsWith ( action ) ) {
    			return Integer.parseInt ( actionValue.replaceAll ( "\\D\\g", "" ) );
    		}
    	}
    	
    	return 0;
    }
    
    /**
     * Get the usage for a specific action with a supplied action-type enum.
     * @param action An action supplied action ID.
     * @return The total stamina usage.
     */
    public static float GetUsage(ActionType action)
    {
    	return GetUsage ( action.toString( ).toLowerCase( ) );
    }
}
