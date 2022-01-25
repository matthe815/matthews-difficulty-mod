package difficultymod.capabilities.stamina;

import difficultymod.api.stamina.ActionType;
import difficultymod.api.stamina.StaminaHelper;
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
	 * Get the stamina regeneration rate.
	 */
	public float GetRegenerationRate() 
	{
		// If thirst is disabled, set it to null.
		ThirstCapability thirst = ConfigHandler.common.thirstSettings.disableThirst ? (ThirstCapability)player.getCapability(ThirstProvider.THIRST, null) : null;
				
		float regenRate = 0.33f; // Base regen rate.
		boolean stamina = player.getActivePotionEffect(PotionInit.STAMINA) != null ? true : false; // Double-stamina regeneration potion.	
		
		if (thirst != null && (int)thirst.Get().thirst < regenRates.length)
			regenRate = stamina ? StaminaCapability.regenRates[(int)thirst.Get().thirst]*2 : StaminaCapability.regenRates[(int)thirst.Get().thirst];	
				
		return player.world.getDifficulty()==EnumDifficulty.PEACEFUL ? regenRate*2 : regenRate;
	}

    /**
     * Set the player's current stamina.
     */
	@Override
	public void Set(Stamina stamina) 
	{
		this.stamina = stamina;
	}

	/**
	 * Get the player's current stamina.
	 */
	@Override
	public Stamina Get() 
	{
		return stamina;
	}
	
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
	
	public void AddDirect(Stamina value) 
	{
		stamina.stamina += value.stamina;
	
		onSendClientUpdate();
	}
	
	public int GetStaminaHoldTick ()
	{
		return this.staminaRechargeTick;
	}

	@Override
	public void Remove(Stamina value) 
	{
		if (ConfigHandler.Debug_Options.showUpdateMessages) {
			System.out.println("Removing " + value.stamina);
			System.out.println(this.stamina.stamina);	
		}
		
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
	 */
	public boolean FireAction(String action, float defStamina) 
	{
		if (this.player == null || this.player.isCreative()) // Creative bypass.
			return true;
		
		float requiredStamina = StaminaHelper.GetUsage(action) != 0 ? StaminaHelper.GetUsage(action) : defStamina;

		if (player.getActivePotionEffect(PotionInit.STAMINALESS)!=null) // Stop right here if the player has Staminaless.
			return true;
		
		System.out.println(this.stamina.fitness);
		
		if (this.stamina.stamina >= requiredStamina) {
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

	/**
	 * Fire an action, returning whether or not the action was successful.
	 * @deprecated
	 */
	public boolean FireAction(ActionType action, EntityPlayer player, float defStamina) {
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
