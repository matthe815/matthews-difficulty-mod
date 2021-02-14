package extratan.enchantments;

import difficultymod.core.DifficultyMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ColdResistanceEnchantment extends Enchantment {

	public ColdResistanceEnchantment() {
		super(Rarity.RARE, EnumEnchantmentType.ARMOR, new EntityEquipmentSlot[] {EntityEquipmentSlot.CHEST, EntityEquipmentSlot.HEAD, EntityEquipmentSlot.FEET, EntityEquipmentSlot.LEGS});
		setRegistryName(DifficultyMod.MODID + ":" + "cold_resistance");
		setName("cold_resistance");
	}

	@Override
	public int getMaxLevel() {
		return 2;
	}
	
}
