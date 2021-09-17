package difficultymod.capabilities.thirst;

import javax.vecmath.Vector3d;

import difficultymod.core.ConfigHandler;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ThirstCapability implements IThirst
{
	private Thirst thirst = new Thirst().SetThirst(20);
	private Thirst lastThirst = new Thirst();
    
    private int tickRate = ConfigHandler.common.thirstSettings.thirstTickRate;
    private EntityPlayer player;
    
    private Vector3d movementVec;
    
    /**Used to time the seconds passed since thirst damage was last dealt to the player*/
    private int thirstTimer;
    
    public ThirstCapability() {}
    
    public void SetPlayer (EntityPlayer player)
    {
    	this.player = player;
    }
    
    /**
     * Update the player's thirst to the provided amount.
     */
	@Override
	public void Set(Thirst thirst) 
	{
		this.thirst = thirst;
	}

	/**
	 * Retreives the player's current thirst amount.
	 */
	@Override
	public Thirst Get() 
	{
		return this.thirst;
	}
	
	/**
	 * Add values to the current thirst stats.
	 * @param thirst
	 */
	@Override
	public void Add(Thirst thirst) 
	{
		this.thirst.thirst += thirst.thirst;
		this.thirst.exhaustion += thirst.exhaustion;
		this.thirst.hydration += thirst.hydration;
	}

	/**
	 * Remove values from the current thirst stats.
	 * @param thirst
	 */
	@Override
	public void Remove(Thirst thirst) {
		this.thirst.thirst -= thirst.thirst;
		this.thirst.exhaustion -= thirst.exhaustion;
		this.thirst.hydration -= thirst.hydration;
	}
	
	@Override
	public boolean HasChanged()
	{
		return !this.thirst.equals(this.lastThirst);
	}
	
	@Override
	public void onSendClientUpdate()
	{
		this.lastThirst = this.thirst;
	}
	
    /**
     * @param phase
     */
	@Override
    public void OnTick(Phase phase)
    {
    	switch (phase) {
    	case END:
    		break;
    		
    	case START:
    		if (movementVec == null)
    			return;
    		
    		Vector3d movement = new Vector3d(player.posX, player.posY, player.posZ);
    		movement.sub(movementVec); movement.absolute();
    		int distance = (int)Math.round(movement.length() * 100.0F);
    		
    		if (distance > 0) applyMovementExhaustion(player, distance);
    		else this.AddExhaustion(0.0001F);
		default:
			return;
    	}
    	
    	if (player.world.getDifficulty() == EnumDifficulty.PEACEFUL) // Refill the thirst gauge on peaceful.
    		this.Add(new Thirst().SetThirst(1));
    	
    	this.movementVec = new Vector3d(player.posX, player.posY, player.posZ);

    	if (this.Get().exhaustion >= 20) this.Consume();
    	
    	if (this.Get().thirst <= 0) {
        	this.thirstTimer++;
        	
        	if (thirstTimer > tickRate) {
        		thirstTimer = 0;
        		player.attackEntityFrom(DamageSource.STARVE, 1.0F);
        	}	
    	}
    	
        if (player.isSprinting() && this.Get().thirst <= 6)
            player.setSprinting(false);
    }
    
    public void Consume() {
    	// Apply a reduction in either thirst or hydration. Depending on what the player has.
    	this.Remove(new Thirst().SetThirst(this.thirst.hydration <= 0 ? 1 : 0).SetHydration(this.thirst.hydration > 0 ? 1 : 0));
    	this.Remove(new Thirst().SetExhaustion(-20));
	}

	public void applyJump(EntityPlayer player)
    {
		this.Add(new Thirst().SetExhaustion(0.15));
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
    
	/**
	 * Set the player's maximum thirst to the provided amount.
	 * @deprecated
	 */
	public void SetMaxThirst(int thirst)
	{
		return;
	}
	
    /**
     * @deprecated
     * @param amount
     */
    public void AddExhaustion(float amount)
    {
    	this.Add(new Thirst().SetExhaustion(amount));
    }
    
}
