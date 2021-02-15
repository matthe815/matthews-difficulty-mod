package difficultymod.gui.handlers;

import java.util.Random;

import difficultymod.api.temperature.TempHelper;
import difficultymod.core.ConfigHandler;
import difficultymod.temperature.ITemp;
import difficultymod.temperature.TempProvider;
import difficultymod.temperature.Temperature;
import difficultymod.thirst.Thirst;
import difficultymod.thirst.ThirstProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public class GUIController {
    public static final ResourceLocation OVERLAY = new ResourceLocation("difficultymod:textures/gui/overlay.png");

    ///
    /// Aspects controlling GUI components, including randomness.
    ///
    private final Random    random         = new Random();
    private final Minecraft minecraft      = Minecraft.getMinecraft();
    public static boolean   hungerCanceled = false;
    public static boolean   thirstCanceled = false;
    protected int           offset;
    
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
		///
		/// General variable definitions, controlling both sets of GUI.
		///
		Minecraft      mc         = Minecraft.getMinecraft();
		EntityPlayer   player     = mc.player;
		EnumDifficulty difficulty = minecraft.world.getDifficulty();
		
		if (event.getType() != ElementType.ALL)
			return;
		
		if (!ConfigHandler.common.temperatureSettings.disableTemperature)
			DrawTemperature();
		
		if (!ConfigHandler.common.thirstSettings.disableThirst)
			DrawThirst();
		
		// Apply the relative right height to create bar offsets.
		offset = GuiIngameForge.right_height;
		
		if (event.isCanceled())
			return;
		
		ScaledResolution scale = event.getResolution();
		
		drawTemperature(player.getCapability(TempProvider.TEMPERATURE, null), mc, 0, 0, 1, scale);

		TempHelper.GetPlayer(player);
	}
	
	/**
	 * Draw the thirst GUI to the players' screen, taking account resolution and client settings.
	 * @param event  The render event of which called this.
	 * @param player The player of which this event was called on.
	 */
	private void DrawThirst(RenderGameOverlayEvent.Pre event, EntityPlayer player)
	{
		///
		/// The relative screen resolution, including monitor width and height.
		///
        ScaledResolution resolution = event.getResolution();
        int              width      = resolution.getScaledWidth();
        int              height     = resolution.getScaledHeight();
		
        minecraft.getTextureManager().bindTexture(OVERLAY); // Bind the textures related to the overlay.
        
		if (event.getType() == ElementType.FOOD && !ConfigHandler.client.useOldGUI) {
			event.setCanceled(true);
			
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
    	if (GUIController.thirstCanceled)
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
    	if (GUIController.hungerCanceled)
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
  
	
	public static void drawTemperature (ITemp temperature, Minecraft mc, int left, int top, float alpha, ScaledResolution scale)
	{
		if (temperature.GetTemperature(mc.player) == Temperature.FREEZING)
			renderVignette(mc, scale, 0, 0, 255);
		else if (temperature.GetTemperature(mc.player) == Temperature.BURNING)
			renderVignette(mc, scale, 255, 0, 0);
	}
	
	private static final ResourceLocation VIGNETTE_TEX_PATH = new ResourceLocation("difficultymod:textures/misc/vignette.png");
	
	private static void renderVignette(Minecraft mc, ScaledResolution scaledRes, float r, float g, float b) {
		GlStateManager.disableDepth();
		GlStateManager.enableBlend();
		GlStateManager.depthMask(false);
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(r, g, b, 0.4f);
        GlStateManager.disableAlpha();
		mc.getTextureManager().bindTexture(VIGNETTE_TEX_PATH);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos(0.0D, (double)scaledRes.getScaledHeight(), -90.0D).tex(0.0D, 1.0D).endVertex();
		bufferbuilder.pos((double)scaledRes.getScaledWidth(), (double)scaledRes.getScaledHeight(), -90.0D).tex(1.0D, 1.0D).endVertex();
		bufferbuilder.pos((double)scaledRes.getScaledWidth(), 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
		bufferbuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}
}
