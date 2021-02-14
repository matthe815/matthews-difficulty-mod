package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
public class TempCore extends ItemArmor 
{

	public TempCore() {
		super(DifficultyMod.wet_material, 0, EntityEquipmentSlot.CHEST);
		this.setRegistryName("difficultymod:temp_core");
		this.setUnlocalizedName("difficultymod.tempCore");
		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}
}
