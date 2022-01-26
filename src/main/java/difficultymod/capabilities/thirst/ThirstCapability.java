package difficultymod.capabilities.thirst;

import javax.vecmath.Vector3d;

import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.networking.ThirstUpdatePacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ThirstCapability implements IThirst
{
	private Thirst thirst;
	private Thirst lastThirst;
    
    private EntityPlayer player;
    
    private Vector3d movementVec = new Vector3d();
    
    private int thirstTimer;
    
    public ThirstCapability() 
    {
    	thirst = new Thirst().SetThirst ( 20 );
    	lastThirst = thirst;
    }
    
    public void SetPlayer (EntityPlayer player)
    {
    	if ( this.player == null ) {
        	this.player = player;
        	this.onSendClientUpdate();
    	}
    }
    
    /**
     * Update the player's thirst to the provided amount.
     * @param thirst The thirst builder.
     */
	@Override
	public void Set ( Thirst thirst ) 
	{
		this.thirst = thirst;
	}

	/**
	 * Retreives the player's current thirst amount.
	 * @return The current thirst object.
	 */
	@Override
	public Thirst Get () 
	{
		return this.thirst;
	}
	
	/**
	 * Add values to the current thirst stats.
	 * @param thirst The thirst object to combine.
	 */
	@Override
	public void Add ( Thirst thirst ) 
	{
		this.thirst.thirst += thirst.thirst;
		this.thirst.exhaustion += thirst.exhaustion;
		this.thirst.hydration += thirst.hydration;
		
		if (thirst.thirst != 0)
			this.onSendClientUpdate();
	}

	/**
	 * Remove values from the current thirst stats.
	 * @param thirst The thirst object to combine.
	 */
	@Override
	public void Remove(Thirst thirst) 
	{
		this.thirst.thirst -= thirst.thirst;
		this.thirst.exhaustion -= thirst.exhaustion;
		this.thirst.hydration -= thirst.hydration;
		
		if (thirst.thirst != 0)
			this.onSendClientUpdate();
	}
	
	@Override
	public boolean HasChanged()
	{
		return this.Get().thirst != this.lastThirst.thirst;
	}
	
	@Override
	public void onSendClientUpdate()
	{
		DifficultyMod.network.sendTo(new ThirstUpdatePacket(this.Get()), (EntityPlayerMP) this.player);
	}
	
	@Override
    public void OnTick ( Phase phase )
    {
		// Check for potential tick damage.
    	if ( this.Get( ).thirst <= 0 ) {
        	this.thirstTimer ++;
        	
        	if ( thirstTimer > ConfigHandler.common.thirstSettings.thirstTickRate ) {
        		thirstTimer = 0;
        		player.attackEntityFrom ( DifficultyMod.SOURCE_THIRST, 1.0F );
        	}
        	
        	return;
    	}
    	
    	// Only run the movement exhaustion process on the start of a tick.
    	switch (phase) {
    	case START:
    		OnTickStart();
    	case END:
        	this.movementVec = new Vector3d ( player.posX, player.posY, player.posZ );

        	if ( this.Get( ).exhaustion >= 20 ) this.Consume(); // If exhausion is maxed, consume a bar.
        	
            if ( player.isSprinting ( ) && this.Get().thirst <= 6 )
                player.setSprinting(false);	
    	}
    }
	
	private void OnTickStart ()
	{
		Vector3d movement = new Vector3d ( player.posX, player.posY, player.posZ );
		movement.sub ( movementVec ); movement.absolute ( );
		int distance = (int) Math.round( movement.length( ) * 100.0F );
		
		if ( distance > 0 ) applyMovementExhaustion(player, distance);
		
		this.Add( new Thirst ( ).SetExhaustion ( 0.8f / ConfigHandler.common.thirstSettings.secondsPerDroplet ) ); // Consume a base exhaustion based on the specified duration.
		
    	if (player.world.getDifficulty() == EnumDifficulty.PEACEFUL) // Refill the thirst gauge on peaceful.
    		this.Add(new Thirst().SetThirst(1));	
	}
    
    public void Consume() {
    	// Apply a reduction in either thirst or hydration. Depending on what the player has.
    	this.Remove(new Thirst()
    			.SetThirst(this.thirst.hydration <= 0 ? 1 : 0)
    			.SetHydration(this.thirst.hydration > 0 ? 1 : 0)
    			.SetExhaustion(20)
    	);
	}

	public void applyJump ( EntityPlayer player ) 
	{
		this.Add ( new Thirst().SetExhaustion (0.15 ) );
    }
    
    private void applyMovementExhaustion ( EntityPlayer player, int distance )
    {
        if (player.isInWater())
            this.Add( new Thirst ( ).SetExhaustion( 0.015F * (float) distance * 0.01F ) );
        
        if (!player.onGround) return;
    
        // Apply base movement penalty.
        this.Add( new Thirst ( ).SetExhaustion( ( ( player.isSprinting( ) ) ? 0.09F : 0.01F) * (float) distance * 0.01F ) );
    }
	
    /**
     * @deprecated
     * @param amount The exhausion to add
     */
    public void AddExhaustion(float amount)
    {
    	this.Add(new Thirst().SetExhaustion(amount));
    }
    
}
