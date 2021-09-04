package difficultymod.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import difficultymod.core.DifficultyMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import lieutenant.registry.*;

public class BaseItem extends Item
{
		public BaseItem(String name, CreativeTabs creativeTab)
		{
			setUnlocalizedName(DifficultyMod.MODID + "." + name)
				.setRegistryName(name)
				.setCreativeTab(creativeTab)
				.setMaxDamage(5);
			
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
		

}
