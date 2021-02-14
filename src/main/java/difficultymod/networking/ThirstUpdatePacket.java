package difficultymod.networking;

import difficultymod.core.ConfigHandler;
import difficultymod.thirst.IThirst;
import difficultymod.thirst.ThirstProvider;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class ThirstUpdatePacket implements IMessage {

	  private int currentThirst;
	  private float currentHydration;
	  private int currentMaxThirst;

	  public ThirstUpdatePacket() {

	  }

	  public ThirstUpdatePacket(int current, float currentHy, int currentMT) 
	  {
		  System.out.println(current);
		  this.currentThirst = current;
		  this.currentHydration = currentHy;
		  this.currentMaxThirst = currentMT;
	  }

	  @Override
	  public void fromBytes(ByteBuf buf) 
	  {
		  this.currentThirst = buf.readInt();
		  this.currentHydration = buf.readFloat();
		  this.currentMaxThirst = buf.readInt();
	  }

	  @Override
	  public void toBytes(ByteBuf buf) 
	  {
		  buf.writeInt(this.currentThirst);
		  buf.writeFloat(this.currentHydration);
		  buf.writeInt(this.currentMaxThirst);
	  }

	  public static class Handler implements IMessageHandler<ThirstUpdatePacket, IMessage> {

	    @Override
	    public IMessage onMessage(ThirstUpdatePacket message, MessageContext ctx) {
	    	if (ConfigHandler.Debug_Options.showPacketMessages)
	    		System.out.println("Got client thirst message.");
	    	
	    	if (ctx.side == Side.CLIENT) {
	    		if (Minecraft.getMinecraft().player == null || !Minecraft.getMinecraft().player.hasCapability(ThirstProvider.THIRST, null))
	    			return null;
	    		
	    		IThirst thirst = Minecraft.getMinecraft().player.getCapability(ThirstProvider.THIRST, null);
	    		
	    		thirst.SetThirst(message.currentThirst);
	    		thirst.SetHydration(message.currentHydration);
	    		thirst.SetMaxThirst(message.currentMaxThirst);
	    	}
	    		return null;
	    }
	  	}
}
