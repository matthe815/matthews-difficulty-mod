package difficultymod.items;

import difficultymod.core.DifficultyMod;
import lieutenant.registry.RegisterHandler;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public abstract class TemperatureArmor extends ItemArmor {

	/**
	 * The effect on body temperature when worn.
	 * @return The warmth value of the armor.
	 */
	public abstract float GetWarmth();
	
	/**
	 * Whether or not the item has the opposite effect when in water.
	 * @return The drenchability.
	 */
	public abstract boolean IsDrenchable();
	
	public TemperatureArmor(ArmorMaterial material, int so, EntityEquipmentSlot slot) {
		super(DifficultyMod.wool_material, so, slot);
		RegisterHandler.AddItem(this);
	}

}
