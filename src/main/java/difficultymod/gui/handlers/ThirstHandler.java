package difficultymod.gui.handlers;

import java.util.Random;

import difficultymod.core.ConfigHandler;
import difficultymod.thirst.Thirst;
import difficultymod.thirst.ThirstProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class ThirstHandler {
    public static final ResourceLocation OVERLAY = new ResourceLocation("difficultymod:textures/gui/overlay.png");
    
    private final Random random = new Random();
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    public static boolean hungerCanceled = false;
    public static boolean thirstCanceled = false;
    
    private int updateCounter;
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == Phase.END && !minecraft.isGamePaused())
            updateCounter++;
    }
    
	@SubscribeEvent(priority=EventPriority.LOW)
	public void onPreRender(RenderGameOverlayEvent.Pre event)
	{
		if (ConfigHandler.common.thirstSettings.disableThirst)
			return;
		
		EnumDifficulty difficulty = minecraft.world.getDifficulty();
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        
        ScaledResolution resolution = event.getResolution();
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
		
		if (event.getType() == ElementType.FOOD && !ConfigHandler.client.useOldGUI) {
			event.setCanceled(true);
			
	        minecraft.getTextureManager().bindTexture(OVERLAY);
		    drawHunger(width, height+4, player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel());
		    minecraft.getTextureManager().bindTexture(Gui.ICONS);
		}
		
		if (event.getType() != ElementType.AIR)
				return;
		
        minecraft.getTextureManager().bindTexture(OVERLAY);
        Thirst thirst = (Thirst)player.getCapability(ThirstProvider.THIRST, null);
        drawThirst(width+(!ConfigHandler.client.useOldGUI ? 10 : 0), height+5, thirst.GetThirst(), thirst.GetHydration(), thirst.GetMaxThirst());
	    minecraft.getTextureManager().bindTexture(Gui.ICONS);
	    
    	GuiIngameForge.right_height += 10;
	}
    
    private void drawThirst(int width, int height, int thirstLevel, float thirstHydrationLevel, float maxThirst)
    {
    	if (ThirstHandler.thirstCanceled)
    		return;
    	
        int left = width / 2 + 91;
        int top = height - GuiIngameForge.right_height;
        
        for (int i = 0; i < (maxThirst/2); i++)
        {
            int dropletHalf = i * 2 + 1;
            int iconIndex = 0;
            int backgroundOffset = 0;
            int startX = (left - i * 8) - 10;
            int startY = top-6;
            
            int textureX = ConfigHandler.client.useOldGUI ? 24 : 16;
            
            if (thirstHydrationLevel <= 0.0F && updateCounter % (thirstLevel * 3 + 1) == 0)
                startY = startY + (random.nextInt(3) - 1);
            
            minecraft.ingameGUI.drawTexturedModalRect(startX, startY, backgroundOffset, textureX, 9, 9);
            
            if (thirstLevel >= dropletHalf)
            	minecraft.ingameGUI.drawTexturedModalRect(startX, startY, (iconIndex + (thirstLevel == dropletHalf ? 5 : 4)) * 9, textureX, 9, 9);
        }
    }
    
    private void drawHunger(int width, int height, int thirstLevel, float thirstHydrationLevel)
    {
    	if (ThirstHandler.hungerCanceled)
    		return;
    	
        int left = width / 2 + 91;
        int top = height - GuiIngameForge.right_height;
        
        for (int i = 0; i < 10; i++)
        {
            int dropletHalf = i * 2 + 1;
            int iconIndex = 0;
            int backgroundOffset = 0;
            int startX = left - i * 8 - 9;
            int startY = top;
            
            if (thirstHydrationLevel <= 0.0F && updateCounter % (thirstLevel * 3 + 1) == 0)
                startY = top + (random.nextInt(3) - 1);
            
            minecraft.ingameGUI.drawTexturedModalRect(startX, startY, backgroundOffset, 44, 9, 9);
            minecraft.ingameGUI.drawTexturedModalRect(startX, startY, (iconIndex + thirstLevel > dropletHalf ? 4 : 5) * 9, 44, 9, 9);
        }
    }
}
