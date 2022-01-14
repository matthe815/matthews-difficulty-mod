package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;

public class WoolBoots extends TemperatureArmor 
{

	public WoolBoots() {
		super(DifficultyMod.wool_material, 0, EntityEquipmentSlot.FEET);
		this.setRegistryName("difficultymod:wool_boots");
		this.setUnlocalizedName("difficultymod.woolBoots");
		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}

	@Override
	public float GetWarmth() {
		return 0.8f;
	}

	@Override
	public boolean IsDrenchable() {
		return true;
	}
}
