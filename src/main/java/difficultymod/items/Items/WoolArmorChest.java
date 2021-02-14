package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
public class WoolArmorChest extends TemperatureArmor 
{

	public WoolArmorChest() {
		super(DifficultyMod.wool_material, 0, EntityEquipmentSlot.CHEST);
		this.setRegistryName("difficultymod:wool_chestplate");
		this.setUnlocalizedName("difficultymod.woolChest");
		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}

	@Override
	public float GetWarmth() {
		return 2;
	}

	@Override
	public boolean IsDrenchable() {
		return true;
	}
}
