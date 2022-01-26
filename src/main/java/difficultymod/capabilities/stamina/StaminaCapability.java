package difficultymod.capabilities.stamina;

import difficultymod.api.capability.CapabilityHelper;
import difficultymod.api.stamina.ActionType;
import difficultymod.api.stamina.StaminaHelper;
import difficultymod.capabilities.thirst.IThirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.core.init.PotionInit;
import difficultymod.networking.StaminaUpdatePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class StaminaCapability implements IStamina
{
	private Stamina stamina = new Stamina().SetStamina(100);
	private float lastStamina = 0;
	
	private int staminaRechargeTick = 0;
	
	private EntityPlayer player;
	
	public static float[] regenRates = new float[] {0, 0, 0, 0, 0, 0, 0, 0, 0.01F, 0.05F, 0.07F, 0.1F, 0.12F, 0.13F, 0.15F, 0.17F, 0.19F, 0.20F, 0.22F, 0.25F, 0.30F};
	public static float[] staminaCaps = new float[] {10, 15, 18, 22, 25, 29, 32, 34, 39, 45, 49, 52, 59, 65, 68, 71, 84, 92, 94, 96, 100};

	public StaminaCapability() {}
	
	/**
	 * Get the stamina regeneration rate, calculated by total thirst and difficulty.
	 * @return Current regeneration rate.
	 */
	public float GetRegenerationRate () 
	{
		// If thirst is disabled, set it to null.
		IThirst thirst = CapabilityHelper.GetThirst ( this.player );
				
		float regenRate = 0.33f; // Base regen rate for if the scaling amount fails.
		
		if ( ( int ) thirst.Get().thirst < regenRates.length ) // Error evaluation, incase thirst is higher than max.
			regenRate = StaminaCapability.regenRates[(int)thirst.Get().thirst];	
				
		return player.world.getDifficulty()==EnumDifficulty.PEACEFUL ? regenRate*2 : regenRate; // Double the regen rate in peaceful and return it.
	}

    /**
     * Set the player's current stamina to the supplied parameter, erasing past values.
     * @param stamina The desired stamina builder object.
     */
	@Override
	public void Set ( Stamina stamina ) 
	{
		this.stamina = stamina;
	}

	/**
	 * Get the player's current stamina.
	 * @return The current running total of the player.
	 */
	@Override
	public Stamina Get () 
	{
		return stamina;
	}
	
	/**
	 * Add the supplied stamina object into the current stamina object, turning into extra stamina when capping.
	 * @param value The desired stamina builder.
	 */
	@Override
	public void Add(Stamina value) 
	{
		float untilMax = stamina.GetMaxStamina(this.player) - stamina.stamina;
		stamina.stamina += Math.min(value.stamina, untilMax);
		value.stamina -= untilMax;
		
		if (value.stamina > 0) stamina.extraStamina += value.stamina;
		this.stamina.fitness += value.fitness;
		
		onSendClientUpdate();
	}
	
	/**
	 * Add the supplied stamina object directly into the player stamina, ignoring extra stamina.
	 * The current stamina outside of extra-stamina is reset to the cap every frame.
	 * @param value The desired stamina builder.
	 */
	public void AddDirect(Stamina value) 
	{
		stamina.stamina += value.stamina;
	
		onSendClientUpdate();
	}
	
	/**
	 * Return the current waiting tick for waiting for stamina to recharge.
	 * @return The current wait tick.
	 */
	public int GetStaminaHoldTick ()
	{
		return this.staminaRechargeTick;
	}

	/**
	 * Remove stamina from the current stamina pool using a supplied stamina builder object.
	 * @param value The desired stamina builder.
	 */
	@Override
	public void Remove(Stamina value) 
	{		
		// Use extra stamina for main stamina.
		if (this.stamina.extraStamina > 0)
		this.stamina.extraStamina -= value.stamina;
		else this.stamina.stamina -= value.stamina;
		
		this.stamina.fitness -= value.fitness;
		
		onSendClientUpdate();
	}
	
	public void SetPlayer (EntityPlayer player)
	{
		this.player = player;
	}
	
	/**
	 * Fire an action, returning whether or not the action was successful.
	 * @param action A string-based action type.
	 * @param defStamina The default stamina for an action.
	 * @return Whether or not the action was successful.
	 */
	public boolean FireAction(String action, float defStamina) 
	{
		if (this.player == null || this.player.isCreative()) // Creative bypass.
			return true;
		
		float requiredStamina = StaminaHelper.GetUsage(action) != 0 ? StaminaHelper.GetUsage(action) : defStamina;

		if ( player.getActivePotionEffect ( PotionInit.STAMINALESS ) != null ) // Stop right here if the player has Staminaless.
			return true;
		
		if ( this.stamina.stamina >= requiredStamina ) {
			this.Remove(new Stamina ().SetStamina ( requiredStamina ).SetFitness( - ( requiredStamina / 170 ) ) );
			staminaRechargeTick = 40;
			return true;
		}
		
		if (this.player != null && ConfigHandler.common.staminaSettings.applySlowness) // Apply slowness if out of stamina and is enabled.
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
		
		return false;
	}
	

	public boolean FireAction(ActionType action, float defStamina) {
		return FireAction(action.toString().toLowerCase(), defStamina);
	}

	@Override
	public void OnTick(Phase phase) 
	{
		if (player.world.isRemote) // Do not attempt to do anything to the local copy.
			return;
		
		// Disable stamina when disabled.
		if (ConfigHandler.common.staminaSettings.disableStamina || (ConfigHandler.common.staminaSettings.disableStaminaDuringPeaceful && player.world.getDifficulty() == EnumDifficulty.PEACEFUL))
			return;
		
		if (player.isSprinting())
		{
			if (!FireAction(ActionType.RUNNING, 0.33f)) // If running fails to occur, cancel running.
			{
				player.setSprinting(false);
			}
			
			return; // Halt stamina regeneration process if running.
		}
		
		this.staminaRechargeTick --; // Wait for the stamina recharge tick.
		if (this.staminaRechargeTick > 0) return;

		this.stamina.stamina = Math.min(this.stamina.stamina, this.stamina.GetMaxStamina(player)); // Prevent stamina from exceeding max.
		this.AddDirect( new Stamina( ).SetStamina( this.GetRegenerationRate() * 1.5f ) );
	}

	public boolean HasChanged() 
	{
		return lastStamina != Get().stamina;
	}
	
	public void onSendClientUpdate() 
	{
		DifficultyMod.network.sendTo(new StaminaUpdatePacket(Get()), (EntityPlayerMP) player);
	}

}
