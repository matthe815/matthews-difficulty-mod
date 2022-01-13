package difficultymod.gui.handlers;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import difficultymod.capabilities.temperature.TemperatureCapability;
import difficultymod.capabilities.temperature.TemperatureProvider;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
	private static final 
		ResourceLocation    VIGNETTE_TEX_PATH = new ResourceLocation("difficultymod:textures/misc/vignette.png");
    
    private int updateCounter;
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
    	// When every frame ends, it should increment the update counter, this is used for randomization.
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
        
		// Draw a vignette.
		if (event.getType() == ElementType.VIGNETTE && !ConfigHandler.common.temperatureSettings.disableTemperature) {
			drawTemperature((TemperatureCapability)player.getCapability(TemperatureProvider.TEMPERATURE, null), mc, event.getResolution());
		}
			
		if (event.getType() != ElementType.AIR)
			return;
		
		if (!ConfigHandler.common.thirstSettings.disableThirst)
			drawSurvivalStats(event, player);
	}
	
	/**
	 * Draw the thirst GUI to the players' screen, taking account resolution and client settings.
	 * @param event  The render event of which called this.
	 * @param player The player of which this event was called on.
	 */
	private void drawSurvivalStats(RenderGameOverlayEvent.Pre event, EntityPlayer player)
	{
		///
		/// The relative screen resolution, including monitor width and height.
		///
        ScaledResolution resolution = event.getResolution();
        int              width      = resolution.getScaledWidth();
        int              height     = resolution.getScaledHeight();
        ThirstCapability thirst     = (ThirstCapability)player.getCapability(ThirstProvider.THIRST, null);
        
		if (event.getType().equals(ElementType.FOOD) && !ConfigHandler.client.useOldGUI) {
			event.setCanceled(true);
		    createGUIChunkBar(width, height+4, 0, 36, player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel(), 20);
		}
		
        createGUIChunkBar(width + (!ConfigHandler.client.useOldGUI ? 10 : 0), height+5, 0, (!ConfigHandler.client.useOldGUI) ? 16 : 25, thirst.Get().thirst, (float)thirst.Get().hydration, thirst.Get().GetMaxThirst());
    	GuiIngameForge.right_height += 12; // Increment the right height.
	}
	
	/**
	 * Create a chunk-based GUI bar. Exactly like the heart bar, or vanilla hunger bar.
	 * @param width The X position on the display for the bar.
	 * @param height The Y position on the display for the bar.
	 * @param textureX The texture-offset in the X direction.
	 * @param textureY The texture-offset in the Y direction.
	 * @param current The current value of the chunk bar.
	 * @param max The maximum value of the chunk bar.
	 */
	private void createGUIChunkBar(int x, int y, int textureX, int textureY, int current, float saturation, int max)
	{
        Minecraft.getMinecraft().getTextureManager().bindTexture(OVERLAY); // Bind the textures related to the overlay.
        GL11.glEnable(GL11.GL_BLEND); // Fix black background.

		// Determine the left and top positions.
		int left = x / 2 + 91,
			top  = y - GuiIngameForge.right_height;
		
		// Begin rendering each individual nugget.
		for (int i = 0; i < (max/2); i++) {
			int dropletHalf = i * 2 + 1; // The amount necessary to render a half nugget.
			
			///
			/// Control icon and texture renderer.
			///
			int iconIndex        = 0,
				backgroundOffset = 0,
				startX           = (left - i * 8) - 10,                      // Determine where to draw the next nugget.
				startY           = top-6;                                    // Determines the Y position to align the texture, is also used for empty animations.
		
            if (saturation <= 0.0F && updateCounter % (current * 3 + 1) == 0) // Dynamically render "drain-capable" animation.
                startY = startY + (random.nextInt(3) - 1);
            
			minecraft.ingameGUI.drawTexturedModalRect(startX, startY, 0, 25, 9, 9); // Draw the image background.
			
			// Render over-top.
			if (current >= dropletHalf)
				minecraft.ingameGUI.drawTexturedModalRect(startX, startY, (iconIndex + (current == dropletHalf ? 5 : 4)) * 9, textureY, 9, 9);
		}
		
	    minecraft.getTextureManager().bindTexture(Gui.ICONS); // Rebind the Minecraft textures.
	}
  
	/**
	 * Render any necessary vignettes to the screen, representing heat-stroke or freezing to death.
	 * @param temperature
	 * @param mc
	 * @param scale
	 */
	private void drawTemperature (TemperatureCapability temperature, Minecraft mc, ScaledResolution scale)
	{
		temperature.SetPlayer(mc.player);
		
		switch (temperature.Get()) {
		case FREEZING:
			renderVignette(mc, scale, 0, 0, 255);
			break;
			
		case BURNING:
			renderVignette(mc, scale, 255, 0, 0);
			break;
			
		default:
			break;
		}
	}
	
	private void renderVignette(Minecraft mc, ScaledResolution scaledRes, float r, float g, float b) {
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
