package difficultymod.networking;

import difficultymod.capabilities.thirst.IThirst;
import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ThirstUpdatePacket implements IMessage {

	  private int thirst;
	  private double hydration;

	  public ThirstUpdatePacket () {}
	  
	  public ThirstUpdatePacket(Thirst thirst) 
	  {
		  if (ConfigHandler.Debug_Options.showPacketMessages)
			  System.out.println(thirst.thirst);
		  
		  this.thirst = thirst.thirst;
		  this.hydration = thirst.hydration;
	  }

	  @Override
	  public void fromBytes(ByteBuf buf) 
	  {
		  this.thirst = buf.readInt();
		  this.hydration = buf.readDouble();
	  }
		  

	  @Override
	  public void toBytes(ByteBuf buf) 
	  {
		  buf.writeInt(this.thirst);
		  buf.writeDouble(this.hydration);
	  }

	  public static class Handler implements IMessageHandler<ThirstUpdatePacket, IMessage> {

	    @Override
	    public IMessage onMessage(ThirstUpdatePacket message, MessageContext ctx) {
	    	if (ConfigHandler.Debug_Options.showPacketMessages)
	    		System.out.println("Got client thirst message.");
	    	
	    	if (ctx.side == Side.CLIENT) {
	    		if (Minecraft.getMinecraft().player == null)
	    			return null;
	    		
	    		IThirst thirst = Minecraft.getMinecraft().player.getCapability(ThirstProvider.THIRST, null);
	    		
	    		thirst.Set(new Thirst().SetThirst(message.thirst).SetHydration(message.hydration));
	    	}
	    		return null;
	    }
	  	}
}
