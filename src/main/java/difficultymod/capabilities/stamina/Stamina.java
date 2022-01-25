package difficultymod.capabilities.stamina;

import difficultymod.core.init.PotionInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;

public class Stamina {

	public float stamina;
	public float extraStamina;
	
	public float fitness = 0;
	
	public Stamina () {}
	
	public float GetTotalStamina ()
	{
		return stamina + extraStamina;
	}
	
	public Stamina SetStamina (float stamina)
	{
		this.stamina = stamina;
		return this;
	}
	
	public Stamina SetExtraStamina (float stamina)
	{
		this.extraStamina = stamina;
		return this;
	}
	
	public Stamina SetFitness ( float fitness )
	{
		this.fitness = fitness;
		return this;
	}
	
	/**
	 * Get the player's max stamina.
	 */
	public float GetMaxStamina(EntityPlayer player) 
	{
		FoodStats stats = player.getFoodStats();
		boolean staminaless = player.getActivePotionEffect(PotionInit.STAMINA) != null ? true : false;
		
		return (staminaless ? StaminaCapability.staminaCaps[stats.getFoodLevel()]*2 : StaminaCapability.staminaCaps[stats.getFoodLevel()]) + fitness;
	}
	
}
