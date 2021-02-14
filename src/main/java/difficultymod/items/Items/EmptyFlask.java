package difficultymod.items.Items;

import java.util.List;

import difficultymod.items.BaseItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EmptyFlask extends BaseItem {
	
	public EmptyFlask()
	{
		super("emptyFlask", "empty_flask", null);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("DEPRECATED");
		tooltip.add("REPLACED WITH VANILLA BOTTLE");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
