package difficultymod.networking;

import difficultymod.capabilities.stamina.Stamina;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.core.ConfigHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class PlayerLeftClickAirPacket implements IMessage {

	  public PlayerLeftClickAirPacket() 
	  {
	  }

	  @Override
	  public void fromBytes(ByteBuf buf) 
	  {
	  }

	  @Override
	  public void toBytes(ByteBuf buf) 
	  {
	  }

	  public static class Handler implements IMessageHandler<PlayerLeftClickAirPacket, IMessage> {

	    @Override
	    public IMessage onMessage(PlayerLeftClickAirPacket message, MessageContext ctx) {
	    	if (ConfigHandler.Debug_Options.showPacketMessages)
	    		System.out.println("Got client left-click air message.");
	    	
	    	if (ctx.side == Side.SERVER) {
	    		if (!Minecraft.getMinecraft().player.hasCapability(StaminaProvider.STAMINA, null))
	    			return null;
	    		
	    		StaminaCapability stamina = (StaminaCapability)Minecraft.getMinecraft().player.getCapability(StaminaProvider.STAMINA, null);
	    		
	    		stamina.Remove(new Stamina().SetStamina(-5));
	    	}
	    		return null;
	    }
	  	}
}
