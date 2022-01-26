package difficultymod.capabilities.temperature;

/**
 * The gateway into modifying temperature values. Modifiers push temperature in a specified direction for a temporary time.
 * @author Matthew
 *
 */
public class Modifier {

	private int ticks=-1;
	
	private String id;

	private double deviance;
	
	public Modifier(String name, double deviance, int timer)
	{
		this.id = name;
		this.deviance = deviance;
		this.ticks = timer;
	}
	
	public void Tick () {
		ticks--;
		return;
	}
	
	/**
	 * Get the reference ID for the modifier.
	 * @return The modifier ID.
	 */
	public String GetID () {
		return this.id;
	}
	
	/**
	 * Get the remaining ticks for the modifier.
	 * @return The total time remaining
	 */
	public int GetRemainingTicks () {
		return this.ticks;
	}
	
	/**
	 * Get the amount of points the modifier deviates.
	 * @return The modifier deviance
	 */
	public double GetDeviance () {
		return this.deviance;
	}
}
