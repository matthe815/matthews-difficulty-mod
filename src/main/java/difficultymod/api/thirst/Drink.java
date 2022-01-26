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
	 * @param location The resource location to register the drink to.
	 * @param value The supplied value of the drink.
	 */
	public Drink ( ResourceLocation location, int value )
	{
		this.location = location;
		this.value = value;
	}
	
	/**
	 * Retrieve a string form of the ResourceLocation.
	 * @return The stringified ID
	 */
	public String GetItemID()
	{
		return location.toString();
	}
	
	/**
	 * Retrieve the provided ResourceLocation from the drink.
	 * @return The resource location.
	 */
	public ResourceLocation GetResourceLocation()
	{
		return location;
	}
	
	/**
	 * Retrieve how much thirst is lost/recovered from drinking that.
	 * @return The total thirst supplied.
	 */
	public int GetThirstValue()
	{
		return value;
	}
	
	/**
	 * Retrieve how much temp is lost/recovered from drinking.
	 * @return The total temperature lost from drinking.
	 */
	public int GetTempValue()
	{
		return temp;
	}
}
