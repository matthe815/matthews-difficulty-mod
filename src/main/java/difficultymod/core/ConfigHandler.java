package difficultymod.core;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.*;

@Config(modid = "difficultymod")
public class ConfigHandler {
	@Name("Client Settings")
	public static Client client = new Client();
	
	@Name("Common Settings")
	public static Common common = new Common();
	
	public static class Client {
		@Name("Use TAN-esque GUI")
		@Comment(value = { "Alters the dimensions of certain UI elements to reflect that of TAN's UI.", "DEFAULT: FALSE" })
		public boolean useOldGUI = false;
	}
	
	public static class Common {
		public class Expansions {
			@Name("Disable Extra TAN")
			@RequiresWorldRestart
			@Comment(value = { "Determines whether or not features added by Extra TAN will be excluded.", "DEFAULT: FALSE" })
			public boolean disableExtraTANFeatures = false;
		}
		
		public class GameplayMechanics {
			@Name("Finite-Water Mode")
			@RequiresWorldRestart
			@Comment(value = { "Determines whether or not an entire water source will be used when filling a canteen.", "DEFAULT: FALSE" })
			public boolean finiteWater = false;
		}
		
		public class StaminaSettings {
			@Name("Disable Stamina")
			@RequiresWorldRestart
			@Comment(value = { "Determines whether or not stamina will be disabled.", "DEFAULT: FALSE" })
			public boolean disableStamina = false;
			
			@Name("Disable Peaceful Stamina")
			@RequiresWorldRestart
			@Comment(value = { "Determines whether or not stamina will be disabled when on the peaceful difficulty.", "DEFAULT: FALSE" })
			public boolean disableStaminaDuringPeaceful = false;
			
			@Name("Apply Slowness On Depletion")
			@RequiresWorldRestart
			@Comment(value = {"Determines if the player will be punished with a slowness effect when stamina bottoms out.", "DEFAULT: TRUE"})
			public boolean applySlowness = true;
			
			@Name("Stamina Depletion")
			@RequiresWorldRestart
			@Comment(value = { 
				"Change the amount of stamina that each action takes.",
				"To modify an action, fill a new value into the array with the format of {ACTION}:{STAMINA_REQUIRED}",
				"Negative numbers are accepted.",
				"List of applicable actions: JUMPING, RUNNING, MINING, ATTACKING",
				"Enter a range between -100 - 100"
			})
			public String[] actionDepletion = new String[] {};
		}
		
		public class ThirstSettings {
			@Name("Disable Thirst")
			@RequiresWorldRestart
			@Comment(value = { "Determines whether or not the thirst system will be disabled.", "DEFAULT: FALSE" })
			public boolean disableThirst = false;
			
			@Name("Maximum Thirst")
			@Comment(value = {
			    "Determines the maximum amount of half thirst 'droplets' the player has.",
			    "DEFAULT: 20"
			})
			public int maxThirstLevel = 20;
			
			@Name("Thirst Tick Rate")
			@Comment(value = {
				"Determines how many ticks it takes for a single half-droplet to deplete.",
				"DEFAULT: 80"
			})
			public int thirstTickRate = 80;
			
			@Name("Enable Quenched Effect")
			@RequiresWorldRestart
			@Comment(value = { "Whether or not to use the 'quenched' effect after drinking liquids when high on thirst.", "DEFAULT: TRUE"})
			public boolean wellFedEffect = true;
			
			@Name("Thirst Restoration")
			@Comment(value = {
				"Change the amount of thirst that drinking an item recovers.",
				"To modify an amount, place a new value into the array. EX. minecraft:apple:1",
				"Negative numbers are accepted.",
				"Enter a range between -20 - 20"
			})
			public String[] drinkThirst = new String[] {};
		}
		
		public class TemperatureSettings {
			@Name("Disable Temperature")
			@RequiresWorldRestart
			@Comment(value = { "Determines whether or not the temperature system will be disabled.", "DEFAULT: FALSE" })
			public boolean disableTemperature = false;
			
			@Name("Climate Effect Duration")
			@RequiresWorldRestart
			@Comment(value = {"Determines how long hypo/hyperthermia will last upon being applied. (In ticks -- seconds * 20)", "DEFAULT: 20"})
			public int climateDamageLength = 20;
			
			@Name("Freezing Threshold")
			@RequiresWorldRestart
			@Comment(value = {"Determines the temperature threshold that you begin freezing to death.", "DEFAULT: 8"})
			public int freezingThreshold = 8;

			@Name("Overheating Threshold")
			@RequiresWorldRestart
			@Comment(value = {"Determines the temperature threshold that you begin overheating.", "DEFAULT: 8"})
			public int burningThreshold = 14;
			
			@Name("Biome Temperature")
			@RequiresWorldRestart
			@Comment(value = { 
				"Determines how hot/cold each biome is, the equilibrium is 0.8. Ex. minecraft:plains:0.8"
			})
			public String[] biomeTemperature = new String[] {};
		}
		
		@Name("Compatibility and Expansions")
		public Expansions expansions = new Expansions();
		
		@Name("Gameplay Mechanics")
		public GameplayMechanics mechanics = new GameplayMechanics();

		@Name("Stamina Settings")
		public StaminaSettings staminaSettings = new StaminaSettings();
		
		@Name("Thirst Settings")
		public ThirstSettings thirstSettings = new ThirstSettings();
		
		@Name("Temperature Settings")
		public TemperatureSettings temperatureSettings = new TemperatureSettings();
	}
	
	@Name("Debug Options")
	public static DebugOptions Debug_Options = new DebugOptions();
	
	public static class DebugOptions {
		public boolean showPacketMessages = false;
		
		public boolean showMiscMessages = false;
		
		public boolean showUpdateMessages = false;
	}
}
