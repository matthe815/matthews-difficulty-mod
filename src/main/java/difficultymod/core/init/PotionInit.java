package difficultymod.core.init;

import difficultymod.core.DifficultyMod;
import difficultymod.items.Items.PotionColdResistance;
import difficultymod.items.Items.PotionHeatResistance;
import difficultymod.items.Items.PotionHyperThermia;
import difficultymod.items.Items.PotionHypoThermia;
import difficultymod.items.Items.PotionStaminaless;
import difficultymod.items.Items.PotionTemperatureImmunity;
import difficultymod.items.Items.PotionTemperatureResistance;
import difficultymod.items.Items.PotionThirstQuench;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionInit {
	
	public static final Potion HEAT_RESISTANCE = new PotionHeatResistance(ForgeRegistries.POTIONS.getEntries().size()+1);
	public static final Potion COLD_RESISTANCE = new PotionColdResistance(ForgeRegistries.POTIONS.getEntries().size()+2);
	public static final Potion HYPOTHERMIA = new PotionHypoThermia(ForgeRegistries.POTIONS.getEntries().size()+3);
	public static final Potion HYPERTHERMIA = new PotionHyperThermia(ForgeRegistries.POTIONS.getEntries().size()+4);
	public static final Potion THIRSTQUENCHED = new PotionThirstQuench(ForgeRegistries.POTIONS.getEntries().size()+5);
	public static final Potion STAMINALESS = new PotionStaminaless(ForgeRegistries.POTIONS.getEntries().size()+6);
	public static final Potion STAMINA = new PotionStaminaless(ForgeRegistries.POTIONS.getEntries().size()+7);
	public static final Potion TEMPERATURE_IMMUNITY = new PotionTemperatureResistance(ForgeRegistries.POTIONS.getEntries().size()+8);
	
	public static void Init ()
	{
		/**
		 * Register types
		 */
		ForgeRegistries.POTIONS.register(HEAT_RESISTANCE
			.setRegistryName(DifficultyMod.MODID + ":" + "heat_resistance")
			.setPotionName("potion.chilled").setBeneficial());
			
		ForgeRegistries.POTIONS.register(COLD_RESISTANCE
			.setRegistryName(DifficultyMod.MODID + ":" + "cold_resistance")
			.setPotionName("potion.steaming").setBeneficial());
			
		ForgeRegistries.POTIONS.register(new PotionTemperatureImmunity(55)
			.setRegistryName(DifficultyMod.MODID + ":" + "temperature_immunity")
			.setPotionName("potion.temperature_immunity").setBeneficial());
		
		ForgeRegistries.POTIONS.register(HYPOTHERMIA
			.setRegistryName(DifficultyMod.MODID + ":" + "hypothermia")
			.setPotionName("potion.hypothermia"));
				
		ForgeRegistries.POTIONS.register(HYPERTHERMIA
			.setRegistryName(DifficultyMod.MODID + ":" + "hyperthermia")
			.setPotionName("potion.hyperthermia"));
		
		ForgeRegistries.POTIONS.register(THIRSTQUENCHED
			.setRegistryName(DifficultyMod.MODID + ":" + "thirstquenched")
			.setPotionName("potion.quenched").setBeneficial());
		
		ForgeRegistries.POTIONS.register(STAMINALESS
				.setRegistryName(DifficultyMod.MODID + ":" + "staminaless")
				.setPotionName("potion.staminaless").setBeneficial());
		
		ForgeRegistries.POTIONS.register(STAMINA
				.setRegistryName(DifficultyMod.MODID + ":" + "stamina")
				.setPotionName("potion.stamina").setBeneficial());

		/**
		 * Create craftable potions.
		 */
		ForgeRegistries.POTION_TYPES.register(new PotionType(new PotionEffect[] {
				new PotionEffect(HEAT_RESISTANCE, 2400)
		}).setRegistryName("heat_resistance"));
		
		ForgeRegistries.POTION_TYPES.register(new PotionType(new PotionEffect[] {
				new PotionEffect(COLD_RESISTANCE, 2400)
		}).setRegistryName("cold_resistance"));
		
		ForgeRegistries.POTION_TYPES.register(new PotionType(new PotionEffect[] {
			new PotionEffect(TEMPERATURE_IMMUNITY, 2400)
		}).setRegistryName("temperature_immunity"));
		
		ForgeRegistries.POTION_TYPES.register(new PotionType(new PotionEffect[] {
				new PotionEffect(STAMINALESS, 2400)
			}).setRegistryName("staminaless"));
		
		ForgeRegistries.POTION_TYPES.register(new PotionType(new PotionEffect[] {
				new PotionEffect(STAMINA, 2400)
			}).setRegistryName("stamina"));
	}
}
