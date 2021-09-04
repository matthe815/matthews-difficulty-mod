package difficultymod.core.init;

import difficultymod.items.Items.*;
import difficultymod.core.ConfigHandler;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.DrinkableItem;
import lieutenant.registry.RegisterHandler;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public class ItemCore {
	
	public static void init ()
	{
		AddItems(new Item[] {
				//Juices
				new DrinkableItem("appleJuice", "apple_juice", -1, CreativeTabHandler.DifficultyModTab, 7),
				new DrinkableItem("cactusJuice", "cactus_juice", -1, CreativeTabHandler.DifficultyModTab, 4),
				new DrinkableItem("carrotJuice", "carrot_juice", -1, CreativeTabHandler.DifficultyModTab, 6),
				new DrinkableItem("goldenCarrotJuice", "golden_carrot_juice", -1, CreativeTabHandler.DifficultyModTab, 8),
				new DrinkableItem("goldenAppleJuice", "golden_apple_juice", -1, CreativeTabHandler.DifficultyModTab, 10),
				new DrinkableItem("chorusFruitJuice", "chorus_fruit_juice", -1, CreativeTabHandler.DifficultyModTab, 6),
				new DrinkableItem("melonJuice", "melon_juice", -1, CreativeTabHandler.DifficultyModTab, 6),
				new DrinkableItem("beetrootJuice", "beetroot_juice", -1, CreativeTabHandler.DifficultyModTab, 7),
				new DrinkableItem("pumpkinJuice", "pumpkin_juice", -1, CreativeTabHandler.DifficultyModTab, 5),
				new DrinkableItem("glisteringMelonJuice", "glistering_melon_juice", -1, CreativeTabHandler.DifficultyModTab, 8),
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
