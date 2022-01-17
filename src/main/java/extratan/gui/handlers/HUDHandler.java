package extratan.gui.handlers;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import difficultymod.api.stamina.StaminaHelper;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
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
public class HUDHandler 
{
	protected int offset;
	protected int disappearTicks = 0;
	
    private final static Random random = new Random();
    private final Minecraft minecraft = Minecraft.getMinecraft();
    
    private static int updateCounter;
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (event.phase == Phase.END && !minecraft.isGamePaused())
            updateCounter++;
    }
	
	private static final ResourceLocation icons = new ResourceLocation(DifficultyMod.MODID, "textures/gui/icons.png");
	
	@SubscribeEvent(priority=EventPriority.LOW)
	public void onPreRender(RenderGameOverlayEvent.Pre event)
	{
		if (ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
		if (event.getType() != ElementType.AIR)
			return;
		
		offset = GuiIngameForge.right_height;
		
		if (event.isCanceled())
			return;
		
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		
		ScaledResolution scale = event.getResolution();
		
		int left = scale.getScaledWidth() / 2 + 27;
		int top = (scale.getScaledHeight() / 2) + 20;

		StaminaCapability stamina = StaminaHelper.GetPlayer(player);
		
		if (stamina.Get().stamina < stamina.Get().GetMaxStamina(player))
			disappearTicks=0;
		
		disappearTicks++;
		
		if (disappearTicks<150)
			drawStamina(stamina, mc, left-15, top, 1);
	}
	
	public static void drawStamina (StaminaCapability stamina, Minecraft mc, int left, int top, float alpha)
	{
		mc.getTextureManager().bindTexture(icons);
		
		float maxExhaustion = stamina.Get().GetMaxStamina(mc.player);
		float ratio = stamina.Get().stamina / maxExhaustion;
		
		int barMaxWidth = 30; // Used for length calculations.
		
		int height = 6;
		int startY = top;
		
		if ((ratio*60) <= 20 && updateCounter % (4 * 3 + 1) == 0)
            startY = top + (random.nextInt(3) - 1);

		enableAlpha(.65f);
		
		mc.ingameGUI.drawTexturedModalRect(left - barMaxWidth + 4 - 1, startY-10, 81 - barMaxWidth, 54, barMaxWidth + 2, ( height + 2 ) - 4);
		
		double drawWidth = stamina.Get().stamina;
		int drawTimes = 0;
		
		while (drawWidth > 0) {
			double drawRatio = (drawWidth > 100 ? 100 : drawWidth) / 100;
			int width = (int) (drawWidth > 100 ? 30 : drawRatio * 30);
			
			drawWidth -= 100;
			
			GlStateManager.color(0.5f + (0.1f * ( drawTimes + 1 )), 0.1f * ( drawTimes + 1 ), 0.1f * ( drawTimes + 1 ));
			mc.ingameGUI.drawTexturedModalRect(left - width + 4, startY-9, 81 - width, 45, width, height - 4 );
			GlStateManager.resetColor();
			
			drawTimes ++;
		}
		
		disableAlpha(.65f);

		// rebind default icons
		mc.getTextureManager().bindTexture(Gui.ICONS);
	}
	
    public static void enableAlpha(float alpha)
	{
		GlStateManager.enableBlend();

		if (alpha == 1f)
			return;

		GlStateManager.color(1.0F, 1.0F, 1.0F, alpha);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void disableAlpha(float alpha)
	{
		GlStateManager.disableBlend();

		if (alpha == 1f)
			return;

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

	
}
