package difficultymod.items;

import net.minecraft.item.ItemStack;

public interface IBottledConsumable {
	public int GetThirstModifier();
	public int GetTemperatureModifier();
	public ItemStack GetConsumedItem();
}
