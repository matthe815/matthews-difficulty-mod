package difficultymod.items;

import net.minecraft.item.Item;
import difficultymod.core.DifficultyMod;
import net.minecraft.creativetab.CreativeTabs;

public class BaseItem extends Item 
{
	
		public BaseItem(String name, String registryName, CreativeTabs creativeTab)
		{
			this.setUnlocalizedName(DifficultyMod.MODID + "." + name);
			this.setRegistryName(registryName);
			this.setCreativeTab(creativeTab);
			this.setMaxDamage(5);
		}

}
