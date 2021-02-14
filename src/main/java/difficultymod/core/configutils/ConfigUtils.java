package difficultymod.core.configutils;

import difficultymod.api.thirst.Drink;
import difficultymod.api.thirst.ThirstHelper;
import difficultymod.core.ConfigHandler;
import net.minecraft.util.ResourceLocation;

public class ConfigUtils 
{
	private static boolean config_loaded = false;
	
	/**
	 * Load the thirst values from the config. (Only fires once)
	 * @return
	 */
	public static void LoadItemThirst()
	{
		if (config_loaded)
			return;
		
		String[] drinkThirst = ConfigHandler.common.thirstSettings.drinkThirst;
		
		for (String drink : drinkThirst) {
			String itemId = drink.substring(0, drink.indexOf(" "));
			ThirstHelper.RegisterDrink(new Drink(new ResourceLocation(itemId), Integer.parseInt(drink.substring(itemId.length()+1))));
		}
		
		config_loaded = true;
		
		return;
	}
	
	public static float SearchConfigArrayForAction(String name)
	{
		String[] actions = ConfigHandler.common.staminaSettings.actionDepletion;
		
		for (String action : actions) {
			if (ConfigHandler.Debug_Options.showMiscMessages) {
				System.out.println(action);
				System.out.println(action.startsWith(name));
				System.out.println(action.substring(name.length()+1));
			}
			
			if (action.startsWith(name) == true) {
				return Float.parseFloat(action.substring(name.length()+1));
			}
		}
		
		return 0;
	}
}
