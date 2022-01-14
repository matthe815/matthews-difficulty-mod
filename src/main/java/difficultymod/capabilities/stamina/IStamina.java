package difficultymod.capabilities.stamina;

import difficultymod.api.stamina.ActionType;
import difficultymod.capabilities.IBaseCapability;

public interface IStamina extends IBaseCapability {

	/**
	 * Set the player's current stamina.
	 */
	void Set(Stamina stamina);

	/**
	 * Get the player's current stamina.
	 */
	Stamina Get();

	void Add(Stamina value);

	void Remove(Stamina value);

	boolean FireAction(ActionType type, float defStamina);

}
