package difficultymod.core.init;

import difficultymod.items.Items.*;
import difficultymod.core.ConfigHandler;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.DrinkableItem;
import lieutenant.registry.RegisterHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class Items {
	
	public static final DrinkableItem APPLE_JUICE = new DrinkableItem("appleJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 7; };
	};
	
	public static final DrinkableItem CACTUS_JUICE = new DrinkableItem("cactusJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 4; };
	};
	
	public static final DrinkableItem CARROT_JUICE = new DrinkableItem("carrotJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 6; };
	};
	
	public static final DrinkableItem GOLDEN_CARROT_JUICE = new DrinkableItem("goldenCarrotJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 8; };
	};
	
	public static final DrinkableItem GOLDEN_APPLE_JUICE = new DrinkableItem("goldenAppleJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 10; };
	};
	
	public static final DrinkableItem CHORUS_FRUIT_JUICE = new DrinkableItem("chorusFruitJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 6; };
	};
	
	public static final DrinkableItem MELON_JUICE = new DrinkableItem("melonJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 6; };
	};
	
	public static final DrinkableItem BEETROOT_JUICE = new DrinkableItem("beetrootJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 5; };
	};
	
	public static final DrinkableItem PUMPKIN_JUICE = new DrinkableItem("pumpkinJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 5; };
	};
	
	public static final DrinkableItem GLISTERING_MELON_JUICE = new DrinkableItem("glisteringMelonJuice", CreativeTabHandler.DifficultyModTab) {
		public int GetThirstModifier() { return 8; };
	};
	
	
	public static void init ()
	{
		AddItems(new Item[] {
				//Juices
				new CleanWaterFlask(),
				new EmptyCanteen(),
				new EmptyCanteen2(),
				new Level1Canteen(),
				new Level2Canteen(),
				new WoolArmorBoots(),
				new WoolArmorChest(),
				new WoolArmorHelm(),
				new WoolArmorLeggings(),
				new TempCore(),
				new WetHelm(),
				new WetChest()
		});
		
		if (!ConfigHandler.common.expansions.disableExtraTANFeatures)
			AddItems(new Item[] {
					new HotDrink(),
					new ColdDrink(),	
			});
	}
	
	/**
	 * Add a group of items.
	 */
	public static void AddItems(Item[] items)
	{
		for (Item item : items)
			RegisterHandler.AddItem(item);
	}
	
	/**
	 * Add a group of blocks.
	 */
	public static void AddBlocks(Block[] blocks)
	{
		for (Block block : blocks)
			RegisterHandler.AddBlock(block);
	}
}
