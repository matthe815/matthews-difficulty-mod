package difficultymod.capabilities.stamina;

import difficultymod.core.init.PotionInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;

public class Stamina {

	public float stamina;
	
	public Stamina () {}
	
	public Stamina SetStamina (float stamina)
	{
		this.stamina = stamina;
		return this;
	}
	
	/**
	 * Get the player's max stamina.
	 */
	public float GetMaxStamina(EntityPlayer player) 
	{
		FoodStats stats = player.getFoodStats();
		boolean staminaless = player.getActivePotionEffect(PotionInit.STAMINALESS) != null ? true : false;
		
		return (staminaless ? StaminaCapability.staminaCaps[stats.getFoodLevel()]*2 : StaminaCapability.staminaCaps[stats.getFoodLevel()]);
	}
	
}
