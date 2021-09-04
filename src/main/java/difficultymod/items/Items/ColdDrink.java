package difficultymod.items.Items;

import difficultymod.core.init.PotionInit;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.DrinkableItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

public class ColdDrink extends DrinkableItem {

	public ColdDrink() {
		super("coldDrink", CreativeTabHandler.DifficultyModTab);
	}
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World world, EntityLivingBase entity)
	{
		((EntityPlayer)entity).addPotionEffect(new PotionEffect(PotionInit.HEAT_RESISTANCE, 600));
		return super.onItemUseFinish(stack, world, entity); // Perform the usual effects for a consumable.
	}
	
}
