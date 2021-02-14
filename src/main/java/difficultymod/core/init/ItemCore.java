package difficultymod.core.init;

import difficultymod.items.Items.*;
import difficultymod.core.ConfigHandler;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.BaseDrinkableItem;
import lieutenant.registry.RegisterHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ItemCore {
	
	public static void init ()
	{
		AddItems(new Item[] {
				//Juices
				new BaseDrinkableItem("appleJuice", "apple_juice", -1, CreativeTabHandler.DifficultyModTab, 7),
				new BaseDrinkableItem("cactusJuice", "cactus_juice", -1, CreativeTabHandler.DifficultyModTab, 4),
				new BaseDrinkableItem("carrotJuice", "carrot_juice", -1, CreativeTabHandler.DifficultyModTab, 6),
				new BaseDrinkableItem("goldenCarrotJuice", "golden_carrot_juice", -1, CreativeTabHandler.DifficultyModTab, 8),
				new BaseDrinkableItem("goldenAppleJuice", "golden_apple_juice", -1, CreativeTabHandler.DifficultyModTab, 10),
				new BaseDrinkableItem("chorusFruitJuice", "chorus_fruit_juice", -1, CreativeTabHandler.DifficultyModTab, 6),
				new BaseDrinkableItem("melonJuice", "melon_juice", -1, CreativeTabHandler.DifficultyModTab, 6),
				new BaseDrinkableItem("beetrootJuice", "beetroot_juice", -1, CreativeTabHandler.DifficultyModTab, 7),
				new BaseDrinkableItem("pumpkinJuice", "pumpkin_juice", -1, CreativeTabHandler.DifficultyModTab, 5),
				new BaseDrinkableItem("glisteringMelonJuice", "glistering_melon_juice", -1, CreativeTabHandler.DifficultyModTab, 8),
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
