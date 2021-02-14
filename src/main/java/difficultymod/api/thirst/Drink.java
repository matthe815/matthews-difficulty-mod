package difficultymod.api.thirst;

import net.minecraft.util.ResourceLocation;

/**
 * The base entry-point for drinks.
 * @author Matthew
 *
 */
public class Drink 
{
	private ResourceLocation location;
	private int value;
	private int temp;
	
	/**
	 * Register a brand new drink into the system using the provided ResourceLocation and value.
	 * @param location
	 * @param value
	 */
	public Drink(ResourceLocation location, int value)
	{
		this.location = location;
		this.value = value;
	}
	
	/**
	 * Retrieve a string form of the ResourceLocation.
	 * @return
	 */
	public String GetItemID()
	{
		return location.toString();
	}
	
	/**
	 * Retrieve the provided ResourceLocation from the drink.
	 * @return
	 */
	public ResourceLocation GetResourceLocation()
	{
		return location;
	}
	
	/**
	 * Retrieve how much thirst is lost/recovered from drinking that.
	 * @return
	 */
	public int GetThirstValue()
	{
		return value;
	}
	
	/**
	 * Retrieve how much temp is lost/recovered from drinking.
	 * @return
	 */
	public int GetTempValue()
	{
		return temp;
	}
}
