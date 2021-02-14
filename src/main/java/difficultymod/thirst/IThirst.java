package difficultymod.thirst;

public interface IThirst 
{
	public void SetThirst(int thirst);
	public void SetMaxThirst(int thirst);
	public void SetExhaustion(float exhaustion);
	public void SetHydration(float hydration);
	public void AddStats(int thirst, float hydration);
	
	public int GetThirst();
	public int GetMaxThirst();
	public float GetExhaustion();
	public float GetMaxExhaustion();
	public float GetHydration();
	
	public void SetChangeTime(int ticks);
	public int GetChangeTime();
	public int GetChangeRate();
	public void SetChangeRate(int ticks);
}
