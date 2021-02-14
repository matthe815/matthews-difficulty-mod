package difficultymod.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

@Deprecated
public interface ITemperateItem {
	public void OnConsume(ItemStack stack, EntityLivingBase entity);
}
