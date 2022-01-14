package difficultymod.core.init;

import difficultymod.networking.PlayerLeftClickAirPacket;
import difficultymod.networking.StaminaUpdatePacket;
import difficultymod.networking.ThirstUpdatePacket;
import extratan.networking.MessageExhaustionSync;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkCore {
	public static void init (SimpleNetworkWrapper network, Side side)
	{
		if (side == Side.CLIENT) {
		    network.registerMessage(ThirstUpdatePacket.Handler.class, ThirstUpdatePacket.class, -1, Side.CLIENT);
		    network.registerMessage(StaminaUpdatePacket.Handler.class, StaminaUpdatePacket.class, 2, Side.CLIENT);
		    network.registerMessage(PlayerLeftClickAirPacket.Handler.class, PlayerLeftClickAirPacket.class, 3, Side.CLIENT);
		}
		network.registerMessage(ThirstUpdatePacket.Handler.class, ThirstUpdatePacket.class, -1, Side.SERVER);
		network.registerMessage(StaminaUpdatePacket.Handler.class, StaminaUpdatePacket.class, 2, Side.SERVER);
		network.registerMessage(PlayerLeftClickAirPacket.Handler.class, PlayerLeftClickAirPacket.class, 3, Side.SERVER);
		network.registerMessage(MessageExhaustionSync.class, MessageExhaustionSync.class, 0, Side.SERVER);	
	}
}
