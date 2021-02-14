package difficultymod.thirst;

import javax.vecmath.Vector3d;

import difficultymod.core.ConfigHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class Thirst implements IThirst 
{
    private int thirstLevel;
    private int maxThirstLevel;
    
    private int prevThirstLevel;
    private float prevHydrationLevel;
    private int prevMaxThirstLevel;
    
    private float thirstHydrationLevel;
    private float thirstExhaustionLevel;
    
    private int tickRate = 80;
    
    private Vector3d movementVec;
    
    /**Used to time the seconds passed since thirst damage was last dealt to the player*/
    private int thirstTimer;
    
    public Thirst()
    {
    	this.maxThirstLevel = ConfigHandler.common.thirstSettings.maxThirstLevel;
    	this.thirstLevel = this.maxThirstLevel;
    	this.thirstHydrationLevel = this.thirstLevel/4;
    	this.tickRate = ConfigHandler.common.thirstSettings.thirstTickRate;
    }
    
    /**
     * Update the player's thirst to the provided amount.
     */
	@Override
	public void SetThirst(int thirst) 
	{
		this.thirstLevel = thirst;
	}
	
	/**
	 * Set the player's maximum thirst to the provided amount.
	 */
	public void SetMaxThirst(int thirst)
	{
		this.maxThirstLevel = thirst;
	}

	/**
	 * Set the player's exhaustion to the provided amount.
	 */
	@Override
	public void SetExhaustion(float exhaustion) 
	{
		this.thirstExhaustionLevel = exhaustion;
	}

	/**
	 * Set the player's hydration to the provided amount.
	 */
	@Override
	public void SetHydration(float hydration)
	{
		this.thirstHydrationLevel = hydration;
	}

	/**
	 * Add thirst and hydration to the player's current total.
	 */
	@Override
	public void AddStats(int thirst, float hydration) 
	{
		this.thirstLevel = Math.min(thirst + this.thirstLevel, 20);
		this.thirstHydrationLevel = Math.min(this.thirstHydrationLevel + (float)thirst * hydration * 2.0F, (float)this.thirstLevel);
	}

	/**
	 * Retreives the player's current thirst amount.
	 */
	@Override
	public int GetThirst() 
	{
		return this.thirstLevel;
	}
	
	/**
	 * Retreive the player's current max thirst.
	 */
	public int GetMaxThirst()
	{
		return this.maxThirstLevel;
	}

	/**
	 * Retreives the player's current exhaustion.
	 */
	@Override
	public float GetExhaustion() 
	{
		return this.thirstExhaustionLevel;
	}

	/**
	 * Retreives the "tick" point for exhaustion.
	 */
	@Override
	public float GetMaxExhaustion()
	{
		return 4;
	}
	
	/**
	 * Retreives the player's current hydration.
	 */
	@Override
	public float GetHydration()
	{
		return this.thirstHydrationLevel;
	}

	/**
	 * Sets the current processing tick.
	 */
	@Override
	public void SetChangeTime(int ticks)
	{
		this.thirstTimer = ticks;
	}

	/**
	 * Gets the current processing tick.
	 */
	@Override
	public int GetChangeTime() 
	{
		return this.thirstTimer;
	}
	
	/**
	 * Get the rate at which the player's thirst changes.
	 */
	@Override
	public int GetChangeRate()
	{
		return tickRate;
	}
	
	/**
	 * Sets the rate at which the player's thirst changes.
	 */
	@Override
	public void SetChangeRate(int rate)
	{
		tickRate = rate;
	}
	
	/**
	 * Returns whether or not the player is currently thirsty.
	 * @return
	 */
	public boolean IsThirsty()
	{
		return this.thirstLevel < 20;
	}
	
	public boolean HasChanged()
	{
		return this.thirstLevel != this.prevThirstLevel || this.thirstHydrationLevel != this.prevHydrationLevel || this.maxThirstLevel != this.prevMaxThirstLevel;
	}
	
	public void onSendClientUpdate()
	{
		this.prevThirstLevel = this.thirstLevel;
		this.prevHydrationLevel = this.thirstHydrationLevel;
		this.prevMaxThirstLevel = this.maxThirstLevel;
	}
	
    /**
     * @param player
     * @param world
     * @param phase
     */
    public void update(EntityPlayer player, World world, Phase phase)
    {
    	switch (phase) {
    	case START:
    		if (movementVec == null)
    			return;
    		
    		Vector3d movement = new Vector3d(player.posX, player.posY, player.posZ);
    		movement.sub(movementVec); movement.absolute();
    		int distance = (int)Math.round(movement.length() * 100.0F);
    		
    		if (distance > 0) applyMovementExhaustion(player, distance);
    		else this.AddExhaustion(0.0001F);
    		break;
		default:
			break;
    	}

    	if (phase != Phase.END)
    		return;
    	
    	if (world.getDifficulty() == EnumDifficulty.PEACEFUL)
    		this.thirstLevel = Math.min(this.thirstLevel + 1, 20);
    	
    	this.movementVec = new Vector3d(player.posX, player.posY, player.posZ);

    	if (this.GetExhaustion() >= this.GetMaxExhaustion()) {
    		if (this.thirstHydrationLevel > 0)
    			this.thirstHydrationLevel = Math.max(this.thirstHydrationLevel - 1.0F, 0.0F);
    		else
    			this.thirstLevel = Math.max(this.thirstLevel - 1, 0);
    		
    		this.thirstExhaustionLevel -= 4.0F;
    	}
    	
    	if (this.GetThirst()<=0) {
        	this.thirstTimer++;
        	
        	if (thirstTimer > tickRate) {
        		thirstTimer = 0;
        		player.attackEntityFrom(DamageSource.STARVE, 1.0F);
        	}	
    	}
    	
        if (player.isSprinting() && thirstLevel <= 6)
            player.setSprinting(false);
    }
    
    public void applyJump(EntityPlayer player)
    {
    	this.AddExhaustion(0.15F);
    }
    
    private void applyMovementExhaustion(EntityPlayer player, int distance)
    {
        if (player.isInsideOfMaterial(Material.WATER))
            this.AddExhaustion(0.015F * (float)distance * 0.01F);
        else if (player.isInWater())
            this.AddExhaustion(0.015F * (float)distance * 0.01F);
        else if (player.onGround) {
            if (player.isSprinting())
                this.AddExhaustion(0.099999994F * (float)distance * 0.01F);
            else
                this.AddExhaustion(0.01F * (float)distance * 0.01F);
        }
    }
    
    public void AddExhaustion(float amount)
    {
    	this.thirstExhaustionLevel = Math.min(this.thirstExhaustionLevel + amount, 40.0F);
    }
    
}
