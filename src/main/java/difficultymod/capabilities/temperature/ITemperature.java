package difficultymod.capabilities.temperature;

import difficultymod.capabilities.IBaseCapability;
import net.minecraft.entity.player.EntityPlayer;

public interface ITemperature extends IBaseCapability {

	/**
	 * Retrieve the temperature penalty as result of a player's armor.
	 */
	int GetPlayerArmorPenalty(EntityPlayer player);

	/**
	 * Get the current temperature with modifiers applied.
	 * @return
	 */
	Temperature Get();

}
