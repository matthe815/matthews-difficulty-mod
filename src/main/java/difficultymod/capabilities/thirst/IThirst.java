package difficultymod.capabilities.thirst;

import difficultymod.capabilities.IBaseCapability;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public interface IThirst extends IBaseCapability
{
	public void Set (Thirst thirst);
	public Thirst Get ();
	
	public void OnTick(Phase phase);
	
	/**
	 * Remove values from the current thirst stats.
	 * @param thirst The thirst builder.
	 */
	void Remove(Thirst thirst);
	/**
	 * Add values to the current thirst stats.
	 * @param thirst The thirst builder.
	 */
	
	void Add(Thirst thirst);
}
