package difficultymod.core.init;

import difficultymod.events.DrinkEvents;
import difficultymod.events.LivingEvent;
import extratan.gui.handlers.SyncHandler;
import net.minecraftforge.common.MinecraftForge;

public class EventCore {
	public static void init()
	{
        MinecraftForge.EVENT_BUS.register(new difficultymod.capabilities.CapabilityManager());
		MinecraftForge.EVENT_BUS.register(new LivingEvent());
		MinecraftForge.EVENT_BUS.register(new DrinkEvents());
		MinecraftForge.EVENT_BUS.register(new SyncHandler());
	}
}
