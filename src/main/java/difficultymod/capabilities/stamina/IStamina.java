package difficultymod.capabilities.stamina;

import difficultymod.api.stamina.ActionType;
import difficultymod.capabilities.IBaseCapability;

public interface IStamina extends IBaseCapability {

	/**
	 * Set the player's current stamina.
	 * @param stamina The object to base new stamina off of.
	 */
	void Set(Stamina stamina);

	/**
	 * Get the player's current stamina.
	 * @return Returns the current stamina object.
	 */
	Stamina Get();

	/**
	 * Combine a supplied stamina builder, adding together values.
	 * @param builder Stamina builder.
	 */
	void Add(Stamina builder);

	/**
	 * Combine a supplied stamina builder, removing values.
	 * @param builder Stamina builder.
	 */
	void Remove(Stamina builder);

	/**
	 * Fire off an action, performing applicable maths and operations.
	 * @param type Action Type (Running, Mining).
	 * @param defStamina The default stamina.
	 * @return Whether or not the action succeeded.
	 */
	boolean FireAction(ActionType type, float defStamina);

}
