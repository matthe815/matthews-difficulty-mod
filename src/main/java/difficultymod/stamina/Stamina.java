package difficultymod.stamina;

import difficultymod.api.stamina.ActionType;
import difficultymod.api.stamina.StaminaHelper;
import difficultymod.core.ConfigHandler;
import difficultymod.core.init.PotionInit;
import difficultymod.thirst.IThirst;
import difficultymod.thirst.ThirstProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.FoodStats;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class Stamina implements IStamina 
{
	private float stamina;
	private float lastStamina = -1;
	private float[] regenRates = new float[] {0, 0, 0, 0, 0, 0, 0, 0, 0.01F, 0.05F, 0.07F, 0.1F, 0.12F, 0.13F, 0.15F, 0.17F, 0.19F, 0.20F, 0.22F, 0.25F, 0.30F};
	public float[] staminaCaps = new float[] {10, 15, 18, 22, 25, 29, 32, 34, 39, 45, 49, 52, 59, 65, 68, 71, 84, 92, 94, 96, 100};

    public Stamina()
    {
    	this.stamina = 100F;
    }

    /**
     * Set the player's current stamina.
     */
	@Override
	public void SetStamina(float stamina) 
	{
		this.stamina = stamina;
	}

	/**
	 * Get the player's current stamina.
	 */
	@Override
	public float GetStamina() 
	{
		return this.stamina;
	}
	
	/**
	 * Get the player's max stamina.
	 */
	@Override
	public float GetMaxStamina(EntityPlayer player) 
	{
		FoodStats stats = player.getFoodStats();
		boolean staminaless = player.getActivePotionEffect(PotionInit.STAMINALESS) != null ? true : false;
		
		return staminaless ? staminaCaps[stats.getFoodLevel()]*2 : staminaCaps[stats.getFoodLevel()];
	}

	/**
	 * Get the stamina regeneration rate.
	 */
	@Override
	public float GetRegenerationRate() 
	{
		return 1F;
	}
	
	/**
	 * Fire an action, returning whether or not the action was successful.
	 */
	@Override
	public boolean FireAction(String action, EntityPlayer player) {
		float requiredStamina = StaminaHelper.GetUsage(action) != 0 ? StaminaHelper.GetUsage(action) : 0.33f;
		
		if (player.getActivePotionEffect(PotionInit.STAMINALESS)!=null) // Stop right here if the player has Staminaless.
			return true;
		
		switch (action.toLowerCase()) {
		case "jumping":
			requiredStamina = 10;
			break;
		}
		
		if (this.GetStamina() >= requiredStamina) {
			this.SetStamina(this.GetStamina()-requiredStamina);	
			return true;
		}
		
		if (ConfigHandler.common.staminaSettings.applySlowness)
			player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100, 2));
		return false;
	}
	
	/**
	 * Fire an action, returning whether or not the action was successful.
	 */
	@Override
	public boolean FireAction(ActionType action, EntityPlayer player) {
		return FireAction(action.toString().toLowerCase(), player);
	}

	public void update(EntityPlayer player, World world, Phase phase) 
	{
		if (world.isRemote) // Do not attempt to do anything to the local copy.
			return;
		
		IThirst th = ConfigHandler.common.thirstSettings.disableThirst ? // If thirst is disabled, set it to null.
				player.getCapability(ThirstProvider.THIRST, null) : null;
				
		boolean stamina = player.getActivePotionEffect(PotionInit.STAMINA) != null ? true : false;
		
		if (player.isSprinting() && !player.isCreative())
			if (!FireAction(ActionType.RUNNING, player))
				player.setSprinting(false);
		
		if (this.GetStamina() >= this.GetMaxStamina(player)) {
			this.stamina = this.GetMaxStamina(player);
			return;
		}
			
		float regenRate = 0.33f;
		
		if (th!=null && th.GetThirst() < regenRates.length)
			regenRate = stamina ? this.regenRates[th.GetThirst()]*2 : this.regenRates[th.GetThirst()];	

		if (!player.isSprinting()||player.getActivePotionEffect(PotionInit.STAMINALESS)!=null)
			this.stamina+=world.getDifficulty()==EnumDifficulty.PEACEFUL ? regenRate*2 : regenRate;
	}

	public boolean HasChanged() 
	{
		return this.stamina != this.lastStamina;
	}

	public void onSendClientUpdate() 
	{
		this.lastStamina = this.stamina;
	}
}
