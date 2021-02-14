package extratan.gui.handlers;

import java.util.Random;

import org.lwjgl.opengl.GL11;
import difficultymod.api.stamina.StaminaHelper;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.stamina.*;
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

		IStamina stamina = StaminaHelper.GetPlayer(player);
		
		if (stamina.GetStamina() < stamina.GetMaxStamina(player))
			disappearTicks=0;
		
		disappearTicks++;
		
		if (disappearTicks<150)
			drawStamina(stamina, mc, left-15, top, 1);
	}
	
	public static void drawStamina (IStamina stamina, Minecraft mc, int left, int top, float alpha)
	{
		mc.getTextureManager().bindTexture(icons);
		
		float maxExhaustion = stamina.GetMaxStamina(mc.player);
		float ratio = stamina.GetStamina() / maxExhaustion;
		int width = (int) (ratio * 30);
		int height = 6;
		int startY = top;
		
		if ((ratio*60) <= 20 && updateCounter % (4 * 3 + 1) == 0)
            startY = top + (random.nextInt(3) - 1);

		enableAlpha(.75f);
		mc.ingameGUI.drawTexturedModalRect(left - 30+4-1, ConfigHandler.client.useOldGUI ? startY-10 : startY-1, 81 - 30, 54, 32, (height+2)-4);
		mc.ingameGUI.drawTexturedModalRect(left - width+4, ConfigHandler.client.useOldGUI ? startY-9 : startY, 81 - width, 45, width, height-4);
		disableAlpha(.75f);

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
