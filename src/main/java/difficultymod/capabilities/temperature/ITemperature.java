package difficultymod.capabilities.temperature;

import difficultymod.capabilities.IBaseCapability;
import net.minecraft.entity.player.EntityPlayer;

public interface ITemperature extends IBaseCapability {

	/**
	 * Retrieve the temperature penalty as result of a player's armor.
	 * @param player The player.
	 * @return The total armor penalty.
	 */
	float GetPlayerArmorPenalty(EntityPlayer player);
	
	/**
	 * Get a list of current modifiers for the player.
	 * @return Modifier list.
	 */
	Modifier [ ] GetModifiers ();
	
	/**
	 * Add a given modifier to the player modifier list.
	 * @param modifier The modifier to add to the player.
	 */
	void Add ( Modifier modifier );
	
	/**
	 * Get the current target temperature for the player.
	 * @return Target temperature.
	 */
	float GetTargetTemperature ();

	/**
	 * Get the current temperature with modifiers applied.
	 * @return The current temperature enum.
	 */
	Temperature Get();

}
