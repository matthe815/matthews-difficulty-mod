package difficultymod.networking;

import difficultymod.core.ConfigHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.RayTraceResult;
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
	    		RayTraceResult result = ctx.getServerHandler().player.rayTrace(2.0, 1.0f);
	    		
	    		if (ctx.getServerHandler().player.world.getBlockState(result.getBlockPos()).getBlock() == Blocks.WATER) {
	    			System.out.println("Right clicked water");
	    		}
	    	}
	    		return null;
	    }
	  	}
}
