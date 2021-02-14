package difficultymod.items;

import difficultymod.core.DifficultyMod;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public abstract class TemperatureArmor extends ItemArmor {

	/**
	 * The effect on body temperature when worn.
	 * @return
	 */
	public abstract float GetWarmth();
	
	/**
	 * Whether or not the item has the opposite effect when in water.
	 * @return
	 */
	public abstract boolean IsDrenchable();
	
	public TemperatureArmor(ArmorMaterial material, int so, EntityEquipmentSlot slot) {
		super(DifficultyMod.wool_material, so, EntityEquipmentSlot.CHEST);
	}

}
