package difficultymod.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import difficultymod.api.thirst.ThirstHelper;
import difficultymod.core.DifficultyMod;
import difficultymod.thirst.IThirst;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class BaseBreakableDrinkableItem extends ItemBucketMilk {

	protected String name;
	public Item emptyVersion;
	
	List<ItemStack> timedItems = new ArrayList<ItemStack>();
	Map<ItemStack,Timer> itemTimers = new HashMap<ItemStack,Timer>();
	
	public BaseBreakableDrinkableItem(String name, String registryName, CreativeTabs creativeTab)
	{
		this.name = name;
		
		setUnlocalizedName(DifficultyMod.MODID + "." + name)
			.setRegistryName(registryName)
			.setCreativeTab(creativeTab);
		
		setMaxDamage(5);
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
		
		if ((th.GetThirst() + 3) > 20)
			th.SetThirst(20);
		else
			th.SetThirst(th.GetThirst() + 3);
		
		if ((th.GetHydration() + 2) > 20)
			th.SetHydration(20);
		else
			th.SetHydration(th.GetHydration() + 2);
		
		itemTimers.get(stack).cancel();
		itemTimers.remove(stack);
		timedItems.remove(timedItems.indexOf(stack));
		
		ItemStack iStack = new ItemStack(Item.getByNameOrId("extratan:empty_flask"));
		iStack.setItemDamage(stack.getItemDamage());
		
		return iStack;
	}
	
	
	@Override
	public void onUpdate(final ItemStack stack, final World worldIn, final Entity entityIn, final int itemSlot, final boolean isSelected) 
	{
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected); // Do the typical action.
		
		if (!timedItems.contains(stack))
		{
			timedItems.add(stack); // Add the stack.
			Timer timer = new Timer(); // Add a new timer.
			
			itemTimers.put(stack, timer); // Add the timer to the map.
			
			timer.schedule(new TimerTask() { public void run() { damageTimer(stack, worldIn, entityIn, itemSlot); return; } }, 60000, 60000);
		}
	}
	
	public void damageTimer(ItemStack stack, World worldIn, Entity entityIn, int itemSlot) 
	{
		if (entityIn instanceof EntityPlayer)
		{
			setDamage(stack, getDamage(stack)+1); // Up the damage a bit
			
			if (getDamage(stack) > getMaxDamage(stack)-1)
			{
				EntityPlayer player = (EntityPlayer)entityIn; // Fetch the player.
				player.playSound(new SoundEvent(new ResourceLocation("minecraft:block.glass.break")), 1, 1); // Play a glass break sound.
				player.inventory.removeStackFromSlot(itemSlot); // Delete the item.
				itemTimers.get(stack).cancel(); // Cancel the timer when done.
				itemTimers.remove(stack); // Remove the timer.
				timedItems.remove(timedItems.indexOf(stack)); // Removed the stack from the list.
			}	
		}
	}
}
