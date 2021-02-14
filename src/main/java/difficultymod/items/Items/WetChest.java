package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;

public class WetChest extends TemperatureArmor 
{

	public WetChest() {
		super(DifficultyMod.wet_material, 0, EntityEquipmentSlot.CHEST);
		this.setRegistryName("difficultymod:wet_chestplate");
		this.setUnlocalizedName("difficultymod.wetChest");
		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}

	@Override
	public float GetWarmth() {
		return 0.2f;
	}

	@Override
	public boolean IsDrenchable() {
		return false;
	}
}
