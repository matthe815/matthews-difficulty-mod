package difficultymod.items;

import difficultymod.core.DifficultyMod;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ArmorItem extends TemperatureArmor {

	public ArmorItem(String name, ArmorMaterial material, EntityEquipmentSlot slot) {
		super(material, 0, slot);
		
		this.setUnlocalizedName(DifficultyMod.MODID + "." + name);
		this.setRegistryName(name);
	}

	@Override
	public float GetWarmth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean IsDrenchable() {
		// TODO Auto-generated method stub
		return false;
	}

}
