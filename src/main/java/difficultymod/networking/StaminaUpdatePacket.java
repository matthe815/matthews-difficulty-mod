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

public class StaminaUpdatePacket implements IMessage {

	  private float stamina;

	  public StaminaUpdatePacket() {

	  }

	  public StaminaUpdatePacket(float f) 
	  {
		  if (ConfigHandler.Debug_Options.showPacketMessages)
			  System.out.println(f);
		  
		  this.stamina = f;
	  }

	  @Override
	  public void fromBytes(ByteBuf buf) 
	  {
		  this.stamina = buf.readFloat();
	  }

	  @Override
	  public void toBytes(ByteBuf buf) 
	  {
		  buf.writeFloat(this.stamina);
	  }

	  public static class Handler implements IMessageHandler<StaminaUpdatePacket, IMessage> {

	    @Override
	    public IMessage onMessage(StaminaUpdatePacket message, MessageContext ctx) {
	    	if (ConfigHandler.Debug_Options.showPacketMessages)
	    		System.out.println("Got client thirst message.");
	    	
	    	if (ctx.side == Side.CLIENT) {
	    		if (Minecraft.getMinecraft().player == null || !Minecraft.getMinecraft().player.hasCapability(StaminaProvider.STAMINA, null))
	    			return null;
	    		
	    		IStamina stamina = Minecraft.getMinecraft().player.getCapability(StaminaProvider.STAMINA, null);
	    		
	    		stamina.SetStamina(message.stamina);
	    	}
	    		return null;
	    }
	  	}
}
