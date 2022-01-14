package difficultymod.capabilities;

import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.capabilities.temperature.TemperatureProvider;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.DifficultyMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityManager 
{
    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayer)) 
        	return;

        event.addCapability(new ResourceLocation(DifficultyMod.MODID, "thirst"), new ThirstProvider());
        event.addCapability(new ResourceLocation(DifficultyMod.MODID, "stamina"), new StaminaProvider());
        event.addCapability(new ResourceLocation(DifficultyMod.MODID, "temperature"), new TemperatureProvider());
    }
}
