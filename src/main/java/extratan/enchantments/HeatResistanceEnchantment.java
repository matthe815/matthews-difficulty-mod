package extratan.enchantments;

import difficultymod.core.DifficultyMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class HeatResistanceEnchantment extends Enchantment {

	public HeatResistanceEnchantment() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[] {EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD, EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS});
		setRegistryName(DifficultyMod.MODID + ":" + "heat_resistance");
		setName("heat_resistance");
	}
	
	@Override
	public int getMaxLevel() {
		return 2;
	}

}
