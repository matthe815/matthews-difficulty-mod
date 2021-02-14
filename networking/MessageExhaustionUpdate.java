package extratan.networking;

import extratan.core.ExtraTAN;
import extratan.gui.handlers.NetworkHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import toughasnails.api.thirst.ThirstHelper;

public class MessageExhaustionUpdate implements IMessage, IMessageHandler<MessageExhaustionUpdate, IMessage>
{
	float thirstExhuasion;

	public MessageExhaustionUpdate(){}
	
	public MessageExhaustionUpdate(float exhaustionLevel)
	{
		this.thirstExhuasion = exhaustionLevel;
	}
	
	@Override
	public IMessage onMessage(MessageExhaustionUpdate message, MessageContext ctx) {
		Minecraft.getMinecraft().addScheduledTask(new Runnable() {
			@Override
			public void run() {
				System.out.println("Recieved a thirst network packet for " + message.thirstExhuasion);
				ThirstHelper.getThirstData(NetworkHelper.getSidedPlayer(ctx)).setExhaustion(message.thirstExhuasion);
			}
		});
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.thirstExhuasion = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this.thirstExhuasion);
	}
}
