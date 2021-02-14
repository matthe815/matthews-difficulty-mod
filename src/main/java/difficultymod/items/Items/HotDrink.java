package difficultymod.items.Items;

import difficultymod.core.init.PotionInit;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.BaseDrinkableItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class HotDrink extends BaseDrinkableItem 
{
	public HotDrink() 
	{
		super("hotDrink", "flask_with_hot_water", 4, CreativeTabHandler.DifficultyModTab);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		EntityPlayer player = (EntityPlayer)entity;
		
		player.addPotionEffect(new PotionEffect(PotionInit.COLD_RESISTANCE, 600));
		
		return super.onItemUseFinish(stack, world, entity);
	}
}
