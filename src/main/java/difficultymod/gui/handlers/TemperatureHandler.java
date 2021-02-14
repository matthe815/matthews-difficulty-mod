package difficultymod.gui.handlers;

import difficultymod.api.temperature.TempHelper;
import difficultymod.core.ConfigHandler;
import difficultymod.temperature.ITemp;
import difficultymod.temperature.TempProvider;
import difficultymod.temperature.Temperature;
import net.minecraft.client.Minecraft;
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

public class TemperatureHandler {
    public static final ResourceLocation OVERLAY = new ResourceLocation("difficultymod:textures/gui/overlay.png");
    
    public static boolean hungerCanceled = false;
    public static boolean thirstCanceled = false;
    
    protected int offset;
    
	@SubscribeEvent(priority=EventPriority.LOW)
	public void onPreRender(RenderGameOverlayEvent.Pre event)
	{
		if (ConfigHandler.common.temperatureSettings.disableTemperature)
			return;
		
		if (event.getType() != ElementType.ALL)
			return;
		
		offset = GuiIngameForge.right_height;
		
		if (event.isCanceled())
			return;
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		
		ScaledResolution scale = event.getResolution();
		
		drawTemperature(player.getCapability(TempProvider.TEMPERATURE, null), mc, 0, 0, 1, scale);

		TempHelper.GetPlayer(player);
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
