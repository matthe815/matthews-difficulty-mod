package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;
public class WoolArmorLeggings extends TemperatureArmor 
{

	public WoolArmorLeggings() {
		super(DifficultyMod.wool_material, 0, EntityEquipmentSlot.LEGS);
		this.setRegistryName("difficultymod:wool_leggings");
		this.setUnlocalizedName("difficultymod.woolLeggings");

		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}

	@Override
	public float GetWarmth() {
		return 1.4f;
	}

	@Override
	public boolean IsDrenchable() {
		return true;
	}
	
}
