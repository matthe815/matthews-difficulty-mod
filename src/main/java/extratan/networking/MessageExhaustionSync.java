package extratan.networking;

import difficultymod.api.thirst.ThirstHelper;
import extratan.gui.handlers.NetworkHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageExhaustionSync implements IMessage, IMessageHandler<MessageExhaustionSync, IMessage>
{
	float thirstExhaustion;

	public MessageExhaustionSync(){}
	
	public MessageExhaustionSync(float exhaustionLevel)
	{
		this.thirstExhaustion = exhaustionLevel;
	}
	
	@Override
	public IMessage onMessage(MessageExhaustionSync message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			@Override
			public void run() {
				System.out.println("Recieved a thirst network packet for " + message.thirstExhaustion);
				ThirstHelper.GetPlayer(NetworkHelper.getSidedPlayer(ctx)).SetExhaustion(message.thirstExhaustion);
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.thirstExhaustion = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.thirstExhaustion);
	}
}
