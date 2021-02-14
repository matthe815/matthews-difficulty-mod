package difficultymod.networking;

import difficultymod.core.ConfigHandler;
import difficultymod.stamina.IStamina;
import difficultymod.stamina.StaminaProvider;
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
	    		
	    		IStamina stamina = Minecraft.getMinecraft().player.getCapability(StaminaProvider.STAMINA, null);
	    		
	    		stamina.SetStamina(stamina.GetStamina()-5);
	    	}
	    		return null;
	    }
	  	}
}
