package difficultymod.capabilities.temperature;

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
	 * @return
	 */
	public String GetID () {
		return this.id;
	}
	
	/**
	 * Get the remaining ticks for the modifier.
	 * @return
	 */
	public int GetRemainingTicks () {
		return this.ticks;
	}
	
	/**
	 * Get the amount of points the modifier deviates.
	 * @return
	 */
	public double GetDeviance () {
		return this.deviance;
	}
}
