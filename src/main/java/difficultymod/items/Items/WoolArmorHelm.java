package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
public class WoolArmorHelm extends TemperatureArmor 
{

	public WoolArmorHelm() {
		super(DifficultyMod.wool_material, 0, EntityEquipmentSlot.HEAD);
		this.setRegistryName("difficultymod:wool_helm");
		this.setUnlocalizedName("difficultymod.woolHelm");

		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}

	@Override
	public float GetWarmth() {
		return 1.0f;
	}

	@Override
	public boolean IsDrenchable() {
		return true;
	}
}
