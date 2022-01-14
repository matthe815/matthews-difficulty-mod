package difficultymod.capabilities.temperature;

public class Modifier {
	public int timer=-1;
	public String name;
	public float modifier;
	
	public Modifier(String name, Float biomeActTemp, int timer)
	{
		this.name = name;
		this.modifier = biomeActTemp;
		this.timer = timer;
	}
	
	public boolean Update()
	{
		timer--;
		
		if (timer==0)
			return true;
		
		return false;
	}
}
