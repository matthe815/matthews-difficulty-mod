package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;

public class WetHelm extends TemperatureArmor 
{

	public WetHelm() {
		super(DifficultyMod.wet_material, 0, EntityEquipmentSlot.HEAD);
		this.setRegistryName("difficultymod:wet_helm");
		this.setUnlocalizedName("difficultymod.wetHelm");
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
