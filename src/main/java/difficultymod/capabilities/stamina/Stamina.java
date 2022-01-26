package difficultymod.capabilities.stamina;

import difficultymod.core.init.PotionInit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.FoodStats;

/**
 * Global stamina-builder class. Uses a series of setters to create a stamina component.
 * @author Matthew
 */
public class Stamina {

	public float stamina;
	public float extraStamina;
	
	public float fitness = 0;
	
	public Stamina () {}
	
	/**
	 * Get total current stamina + extra stamina.
	 * @return The combined total
	 */
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
	 * Get the player's max stamina based on fitness and extra stamina on top of hunger.
	 * @param player Player to base hunger from.
	 * @return The current maximum stamina.
	 */
	public float GetMaxStamina(EntityPlayer player) 
	{
		FoodStats stats = player.getFoodStats();
		boolean staminaless = player.getActivePotionEffect(PotionInit.STAMINA) != null ? true : false;
		
		return (staminaless ? StaminaCapability.staminaCaps[stats.getFoodLevel()]*2 : StaminaCapability.staminaCaps[stats.getFoodLevel()]) + fitness;
	}
	
}
