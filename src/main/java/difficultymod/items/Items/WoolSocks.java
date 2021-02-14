package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.TemperatureArmor;
import net.minecraft.inventory.EntityEquipmentSlot;

public class WoolSocks extends TemperatureArmor 
{

	public WoolSocks() {
		super(DifficultyMod.wool_material, 0, EntityEquipmentSlot.FEET);
		this.setRegistryName("difficultymod:wool_socks");
		this.setUnlocalizedName("difficultymod.woolSocks");
		this.setCreativeTab(CreativeTabHandler.DifficultyModTab);
	}

	@Override
	public float GetWarmth() {
		return 0.3f;
	}

	@Override
	public boolean IsDrenchable() {
		return true;
	}
}
