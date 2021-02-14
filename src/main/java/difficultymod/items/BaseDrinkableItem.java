package difficultymod.items;

import difficultymod.api.thirst.ThirstHelper;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.core.init.PotionInit;
import difficultymod.thirst.IThirst;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class BaseDrinkableItem extends ItemBucketMilk {

	protected int tempModifier;
	private int thirst;
	protected String name;
	public Item emptyVersion;
	
	public BaseDrinkableItem(String name, String registryName, int temperature, CreativeTabs creativeTab)
	{
		this.name = name;
		this.tempModifier = temperature;
		
		setUnlocalizedName(DifficultyMod.MODID + "." + name)
			.setRegistryName(registryName)
			.setCreativeTab(creativeTab);
		
		setMaxDamage(5);
		this.thirst = 3;
		
		this.tempModifier = temperature;
	}
	
	public BaseDrinkableItem(String name, String registryName, int temperature, CreativeTabs creativeTab, int thirst)
	{
		this.name = name;
		this.tempModifier = temperature;
		
		setUnlocalizedName(DifficultyMod.MODID + "." + name)
			.setRegistryName(registryName)
			.setCreativeTab(creativeTab);
		
		setMaxDamage(5);
		this.thirst = thirst;
		
		this.tempModifier = temperature;
	}
	
	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
		return false;
	}
	
	@Override
	public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
		return false;
	}
	
	@Override
	public int getItemEnchantability() {
		return 0;
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		super.onItemUseFinish(stack, world, entity);
		EntityPlayer player = (EntityPlayer)entity;
		
		if (player.isCreative())
			return stack;
		
		IThirst th = ThirstHelper.GetPlayer(player);
		
		th.SetThirst((th.GetThirst()+thirst) > 20 ? 20 : (th.GetThirst() + thirst));
		th.SetHydration((th.GetHydration()+thirst) > 20 ? 20 : th.GetHydration() + thirst/2);
		
		if (th.GetThirst() >= 19 && ConfigHandler.common.thirstSettings.wellFedEffect)
			player.addPotionEffect(new PotionEffect(PotionInit.THIRSTQUENCHED, 1500, 1));
		
		ItemStack iStack = new ItemStack(Item.getByNameOrId("minecraft:glass_bottle"));
		iStack.setItemDamage(stack.getItemDamage());
		return iStack;
	}
}
