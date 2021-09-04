package difficultymod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CanteenItem extends DrinkableItem 
{
	public CanteenItem(String name, CreativeTabs creativeTab)
	{
		super(name, creativeTab);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		super.onItemUseFinish(stack, world, entity);
		
		EntityPlayer player = (EntityPlayer)entity;
		
		// If in creative mode, don't actually apply durability effects.
		if (player.isCreative()) return stack;
		
		stack.setItemDamage(stack.getItemDamage()+1);
		
		// If the item is fully consumed, change back into empty.
		if (stack.getItemDamage()==stack.getMaxDamage())
			return GetConsumedItem();
			
		return stack;
	}
}
