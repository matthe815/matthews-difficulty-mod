package difficultymod.capabilities;

import difficultymod.core.DifficultyMod;
import difficultymod.stamina.StaminaProvider;
import difficultymod.temperature.TempProvider;
import difficultymod.thirst.ThirstProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityManager 
{
    public static final ResourceLocation THIRST = new ResourceLocation(DifficultyMod.MODID, "thirst");
    public static final ResourceLocation STAMINA = new ResourceLocation(DifficultyMod.MODID, "stamina");
    public static final ResourceLocation TEMPERATURE = new ResourceLocation(DifficultyMod.MODID, "temperature");

    @SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event)
    {
        if (!(event.getObject() instanceof EntityPlayer)) 
        	return;

        event.addCapability(THIRST, new ThirstProvider());
        event.addCapability(STAMINA, new StaminaProvider());
        event.addCapability(TEMPERATURE, new TempProvider());
    }
}
