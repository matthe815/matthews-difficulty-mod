package difficultymod.api.thirst;

import java.util.ArrayList;
import java.util.List;

import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

/**
 * The entry point to any thirst behavior.
 * @author Matthew
 *
 */
public class ThirstHelper 
{
	private static List<Drink> registered_drinks = new ArrayList<Drink>();
	
	/**
	 * Retrieve a player's thirst capability, allowing you to access any of the methods.
	 * @param player The player.
 	 * @return The thirst capability.
	 */
    public static ThirstCapability GetPlayer(EntityPlayer player)
    {
        return (ThirstCapability)player.getCapability(ThirstProvider.THIRST, null);
    }
    
    /**
     * Register a set of items as drinks.
     * @param items Drink list
     */
    public static void RegisterDrinks(Item[] items)
    {
    	for (Item item : items)
    		RegisterDrink(new Drink(item.getRegistryName(), 1));
    }
    
    /**
     * Register a new drink into the system, with effects to be applied upon drinking.
     * @param drink The drink to register.
     */
    public static void RegisterDrink(Drink drink)
    {
    	registered_drinks.add(drink);
    }
    
    /**
     * Obtain an array of all of the currently registered drinks.
     * @return The drink object list.
     */
    public static Drink [ ] GetDrinks ()
    {
    	return registered_drinks.toArray(new Drink[0]);
    }
    
    /**
     * Grab the data of a currently loaded drink.
     * @param location The resource location of the drink.
     * @return The drink object.
     */
    public static Drink GetDrink(ResourceLocation location)
    {
    	for (Drink drink : registered_drinks)
    		if (drink.GetResourceLocation().equals(location))
    			return drink;
    	
    	return null;
    }
}
