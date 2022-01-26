package difficultymod.creativetabs;

import difficultymod.core.DifficultyMod;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * A base-class for building new creative-tabs, allowing you to set icons and other values.
 * @author Matthew
 *
 */
public class CreativeTabBuilder extends CreativeTabs {

	Item renderItem;
	
	public CreativeTabBuilder() {
		super(DifficultyMod.MODID + "." + "drinks");
	}
	
	/**
	 * Set the rendered item in the creative-tab to a new item.
	 * @param render The item to render in the tab.
	 * @return The tab builder.
	 */
	public CreativeTabBuilder setTabIcon(Item render) {
		this.renderItem = render;
		return this;
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(this.renderItem);
	}

}
