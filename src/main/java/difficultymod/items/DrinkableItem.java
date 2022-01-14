package difficultymod.items;

import difficultymod.api.thirst.ThirstHelper;
import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.core.init.PotionInit;
import lieutenant.registry.RegisterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class DrinkableItem extends ItemBucketMilk implements IBottledConsumable {

	public DrinkableItem(String name, CreativeTabs creativeTab)
	{
		setUnlocalizedName(DifficultyMod.MODID + "." + name)
			.setRegistryName(name)
			.setCreativeTab(creativeTab);
		
		setMaxDamage(5);
		RegisterHandler.AddItem(this);
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
		
		// Cast and store the player and their thirst level.
		EntityPlayer player = (EntityPlayer)entity;
		ThirstCapability thirst = ThirstHelper.GetPlayer(player);
		
		thirst.Add(new Thirst().SetThirst(GetThirstModifier()).SetHydration(GetThirstModifier()/2));
		
		// If the player has a sufficient amount of thirst, and the effect enabled, apply quenched.
		if (thirst.Get().thirst >= 16 && ConfigHandler.common.thirstSettings.wellFedEffect)
			player.addPotionEffect(new PotionEffect(PotionInit.THIRSTQUENCHED, 1500, 1));
		
		ItemStack iStack = GetConsumedItem();
		iStack.setItemDamage(stack.getItemDamage());
		return player.isCreative() ? stack : iStack;
	}

	@Override
	public int GetThirstModifier() {
		return 0;
	}

	@Override
	public ItemStack GetConsumedItem() {
		return null;
	}

	@Override
	public int GetTemperatureModifier() {
		return 0;
	}
}
