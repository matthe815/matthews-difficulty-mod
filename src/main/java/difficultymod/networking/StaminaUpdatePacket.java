package difficultymod.networking;

import difficultymod.capabilities.stamina.IStamina;
import difficultymod.capabilities.stamina.Stamina;
import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.core.ConfigHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class StaminaUpdatePacket implements IMessage {

	  private float stamina;
	  private float extraStamina;
	  private float fitness;

	  public StaminaUpdatePacket() {

	  }

	  public StaminaUpdatePacket(Stamina stamina) 
	  {
		  if (ConfigHandler.Debug_Options.showPacketMessages)
			  System.out.println(stamina.stamina);
		  
		  this.stamina = stamina.stamina;
		  this.extraStamina = stamina.extraStamina;
		  this.fitness = stamina.fitness;
	  }

	  @Override
	  public void fromBytes(ByteBuf buf) 
	  {
		  this.stamina = buf.readFloat();
		  this.extraStamina = buf.readFloat();
		  this.fitness = buf.readFloat();
	  }

	  @Override
	  public void toBytes(ByteBuf buf) 
	  {
		  buf.writeFloat(this.stamina);
		  buf.writeFloat(this.extraStamina);
		  buf.writeFloat(this.fitness);
	  }

	  public static class Handler implements IMessageHandler<StaminaUpdatePacket, IMessage> {

	    @Override
	    public IMessage onMessage(StaminaUpdatePacket message, MessageContext ctx) {

	    	if (ctx.side == Side.CLIENT) {
	    		if (Minecraft.getMinecraft().player == null)
	    			return null;
	    		
	    		IStamina stamina = Minecraft.getMinecraft().player.getCapability(StaminaProvider.STAMINA, null);
	    		
	    		if (ConfigHandler.Debug_Options.showPacketMessages)
	    			System.out.println(message.stamina);
	    		
	    		stamina.Set(new Stamina().SetStamina(message.stamina).SetExtraStamina(message.extraStamina).SetFitness(message.fitness));
	    	}
	    		return null;
	    }
	  	}
}
