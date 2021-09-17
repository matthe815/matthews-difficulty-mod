package difficultymod.capabilities.thirst;

import difficultymod.core.ConfigHandler;

public class Thirst {
	
	public int thirst = 0;
	public double hydration = 0;
	public double exhaustion = 0;

	public Thirst () {}
	
	public Thirst SetThirst(int thirst)
	{
		this.thirst = thirst;
		return this;
	}
	
	public Thirst SetHydration(double hydration)
	{
		this.hydration = hydration;
		return this;
	}
	
	public Thirst SetExhaustion(double exhaustion)
	{
		this.exhaustion = exhaustion;
		return this;
	}
	
	public int GetMaxThirst()
	{
		return ConfigHandler.common.thirstSettings.maxThirstLevel;
	}
	
}
