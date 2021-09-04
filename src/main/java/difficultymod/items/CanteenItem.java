package difficultymod.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BaseCanteen extends BaseDrinkableItem 
{
	public BaseCanteen(String name, String registryName, int maxCapacity, int temperature, CreativeTabs creativeTab)
	{
		super(name, registryName, temperature, creativeTab);
		this.setMaxDamage(maxCapacity);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		super.onItemUseFinish(stack, world, entity);
		
		EntityPlayer player = (EntityPlayer)entity;
		
		if (player.isCreative())
			return stack;
		
		stack.setItemDamage(stack.getItemDamage()+1);
		
		if (stack.getItemDamage()==stack.getMaxDamage()) {
			switch (stack.getItem().getRegistryName().toString()) {
			
			case "difficultymod:canteen1":
				stack = new ItemStack(Item.getByNameOrId("difficultymod:empty_canteen"));
				break;
				
			case "difficultymod:canteen2":
				stack = new ItemStack(Item.getByNameOrId("difficultymod:empty_canteen2"));
				break;
				
			}
		}
			
		return stack;
	}
}
