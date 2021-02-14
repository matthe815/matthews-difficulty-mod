package difficultymod.networking;

import difficultymod.core.ConfigHandler;
import difficultymod.temperature.TempProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class TemperatureUpdatePacket implements IMessage {

	  private float currentTemp;

	  public TemperatureUpdatePacket() {

	  }

	  public TemperatureUpdatePacket(float current) 
	  {
		  if (ConfigHandler.Debug_Options.showPacketMessages)
			  System.out.println(current);
		  
		  this.currentTemp = current;
	  }

	  @Override
	  public void fromBytes(ByteBuf buf) 
	  {
		  this.currentTemp = buf.readFloat();
	  }

	  @Override
	  public void toBytes(ByteBuf buf) 
	  {
		  buf.writeFloat(this.currentTemp);
	  }

	  public static class Handler implements IMessageHandler<TemperatureUpdatePacket, IMessage> {

	    @Override
	    public IMessage onMessage(TemperatureUpdatePacket message, MessageContext ctx) {
	    	if (ConfigHandler.Debug_Options.showPacketMessages)
	    		System.out.println("Got client temperature message.");
	    	
	    	if (ctx.side == Side.CLIENT) {
	    		if (Minecraft.getMinecraft().player == null || !Minecraft.getMinecraft().player.hasCapability(TempProvider.TEMPERATURE, null))
	    			return null;
	    	}
	    		return null;
	    }
	  	}
}
