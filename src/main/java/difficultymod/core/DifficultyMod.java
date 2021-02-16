package difficultymod.core;

import difficultymod.gui.handlers.TemperatureHandler;
import difficultymod.gui.handlers.ThirstHandler;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import difficultymod.core.init.*;
import extratan.enchantments.ColdResistanceEnchantment;
import extratan.enchantments.HeatResistanceEnchantment;
import extratan.gui.handlers.*;
import extratan.lootfunctions.ApplyRandomTempProt;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.functions.LootFunction.Serializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = DifficultyMod.MODID, version = DifficultyMod.VERSION, name = DifficultyMod.NAME, dependencies = DifficultyMod.DEPENDENCIES)
public class DifficultyMod {

	public static final String MODID = "difficultymod";
	public static final String VERSION = "1.1.601";
	public static final String NAME = "Matthew's Difficulty Mod";
	public static final String DEPENDENCIES = "required-after:lieutenant";
	
	private ItemStack waterBottle = new ItemStack(Items.POTIONITEM, 1, 0);
	
	public static ArmorMaterial wool_material = EnumHelper.addArmorMaterial("wool", "difficultymod:wool", 115, new int[] {1, 2, 1, 1}, 20, null, 2F);
	public static ArmorMaterial wet_material = EnumHelper.addArmorMaterial("wet", "difficultymod:wet", 95, new int[] {1, 1, 1, 1}, 25, null, 1F);
	public static ArmorMaterial temp_material = EnumHelper.addArmorMaterial("temp", "difficultymod:temp", 240, new int[] {1, 3, 1, 1}, 20, null, 1.5F);
	public static HeatResistanceEnchantment heat_resistance = new HeatResistanceEnchantment();
	public static ColdResistanceEnchantment cold_resistance = new ColdResistanceEnchantment();
	
	public static SimpleNetworkWrapper network;
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) 
	{
		System.out.println(MODID + " is pre-loading!");
		
	    network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);

	    ItemCore.init();
	    CapabilityCore.init();
	    EventCore.init();
	    NetworkCore.init(network, event.getSide());
	    PotionInit.Init();
	    
		ForgeRegistries.ENCHANTMENTS.register(heat_resistance);
		ForgeRegistries.ENCHANTMENTS.register(cold_resistance);
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) 
	{
		System.out.println(MODID + " is loading!");
		
		NBTTagCompound waterTag = new NBTTagCompound();
		waterTag.setString("Potion", "minecraft:water");
		
		waterBottle.setTagCompound(waterTag);
		
		GameRegistry.addSmelting(Items.GLASS_BOTTLE, new ItemStack(Item.getByNameOrId("difficultymod:empty_flask")), 1);
		GameRegistry.addSmelting(waterBottle, new ItemStack(Item.getByNameOrId("difficultymod:flask_with_hot_water")), 2);
		GameRegistry.addSmelting(new ItemStack(Item.getByNameOrId("difficultymod:clean_water")), new ItemStack(Item.getByNameOrId("difficultymod:flask_with_hot_water")), 2);

		
		if (event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new HUDHandler());	
			MinecraftForge.EVENT_BUS.register(new ThirstHandler());	
			MinecraftForge.EVENT_BUS.register(new TemperatureHandler());	
		}
		
		LootFunctionManager.registerFunction(new Serializer<ApplyRandomTempProt>(new ResourceLocation("difficultymod:apply_random_temp_prot"), ApplyRandomTempProt.class) {

			@Override
			public ApplyRandomTempProt deserialize(JsonObject object, JsonDeserializationContext deserializationContext,
					LootCondition[] conditionsIn) {
				return new ApplyRandomTempProt();
			}

			@Override
			public void serialize(JsonObject object, ApplyRandomTempProt functionClazz,
					JsonSerializationContext serializationContext) {}
		});
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) 
	{
		System.out.println(MODID + " is post-loading!");
	}
	
}
