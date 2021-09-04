package difficultymod.creativetabs;

import difficultymod.core.DifficultyMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class DifficultyModTab extends CreativeTabs {

	public DifficultyModTab() {
		super(DifficultyMod.MODID + "." + "drinks");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(Item.getByNameOrId("difficultymod:canteen1"));
	}

}
