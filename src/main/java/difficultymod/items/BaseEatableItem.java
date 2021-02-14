package difficultymod.items;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import difficultymod.core.DifficultyMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;

public class BaseEatableItem extends ItemFood {
	
		public BaseEatableItem(String name, String registryName, CreativeTabs creativeTab)
		{
			super(1, false); // Import the SUPER constructor.
			setUnlocalizedName(DifficultyMod.MODID + "." + name);
			setRegistryName(registryName);
			setCreativeTab(creativeTab);
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
}
