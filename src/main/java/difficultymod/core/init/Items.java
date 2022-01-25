package difficultymod.core.init;

import difficultymod.items.Items.*;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.ArmorItem;
import difficultymod.items.CanteenItem;
import difficultymod.items.DrinkableItem;
import lieutenant.registry.RegisterHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class Items {
	
	public static DrinkableItem HOT_DRINK = new DrinkableItem("hotDrink", CreativeTabHandler.DifficultyModTab) {
		@Override
		public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			player.addPotionEffect(new PotionEffect(PotionInit.COLD_RESISTANCE, 600));
			
			return super.onItemUseFinish(stack, world, entity);
		}
	};
	
	public static DrinkableItem COLD_DRINK = new DrinkableItem("coldDrink", CreativeTabHandler.DifficultyModTab) {
		@Override
		public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
		{
			EntityPlayer player = (EntityPlayer)entity;
			
			player.addPotionEffect(new PotionEffect(PotionInit.HEAT_RESISTANCE, 600));
			
			return super.onItemUseFinish(stack, world, entity);
		}
	};
	
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
	
	public static final DrinkableItem CLEAN_WATER_FLASK = new DrinkableItem("cleanWater", CreativeTabHandler.DifficultyModTab);

	public static final ItemBucket CANTEEN_EMPTY = new EmptyCanteen();
	
	public static final ItemBucket CANTEEN2_EMPTY = new EmptyCanteen2();
	
	public static final CanteenItem CANTEEN = new CanteenItem("smallCanteen", CreativeTabHandler.DifficultyModTab, 2) {
		public ItemStack GetConsumedItem() {
			return new ItemStack(CANTEEN_EMPTY);
		};
	};
	
	public static final CanteenItem CANTEEN2 = new CanteenItem("largeCanteen", CreativeTabHandler.DifficultyModTab, 3) {
		public ItemStack GetConsumedItem() {
			return new ItemStack(CANTEEN2_EMPTY);
		};
	};
	
	public static final ArmorItem WET_HELM = new ArmorItem("wetHelm", DifficultyMod.wet_material, EntityEquipmentSlot.HEAD);
	
	public static final ArmorItem WET_CHESTPLATE = new ArmorItem("wetChest", DifficultyMod.wet_material, EntityEquipmentSlot.CHEST);
	
	public static final ArmorItem WOOL_BOOTS = new ArmorItem("woolBoots", DifficultyMod.wool_material, EntityEquipmentSlot.FEET) {
		public float GetWarmth() {
			return 0.4f;
		};
	};
	
	public static final ArmorItem WOOL_CHESTPLATE = new ArmorItem("woolShirt", DifficultyMod.wool_material, EntityEquipmentSlot.CHEST) {
		public float GetWarmth() {
			return 0.9f;
		};
	};
	
	public static final ArmorItem WOOL_LEGGINGS = new ArmorItem("woolPants", DifficultyMod.wool_material, EntityEquipmentSlot.LEGS) {
		public float GetWarmth() {
			return 0.7f;
		};
	};
	
	public static final ArmorItem WOOL_HAT = new ArmorItem("woolHat", DifficultyMod.wool_material, EntityEquipmentSlot.HEAD) {
		public float GetWarmth() {
			return 0.4f;
		};
	};
	
	public static void init ()
	{	
		for (Item item : RegisterHandler.ITEMS) {
			item.setCreativeTab(CreativeTabHandler.DifficultyModTab);
		}
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
