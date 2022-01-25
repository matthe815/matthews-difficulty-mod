package extratan.gui.handlers;

import java.util.Random;

import difficultymod.api.stamina.StaminaHelper;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

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
		if ( ConfigHandler.common.staminaSettings.disableStamina ) return; // Handle disabled stamina.
		if ( event.getType ( ) != ElementType.AIR ) return; // Only render on the air handler.
		if ( event.isCanceled ( ) ) return; // Do not proceed if cancelled.

		Minecraft mc = Minecraft.getMinecraft ( );
		ScaledResolution scale = event.getResolution ( );
		
		int left = scale.getScaledWidth ( ) / 2 + 27;
		int top = ( scale.getScaledHeight ( ) / 2 ) + 20;

		StaminaCapability stamina = StaminaHelper.GetPlayer ( mc.player );
		
		if ( stamina.Get( ).GetTotalStamina() != stamina.Get( ).GetMaxStamina ( mc.player ) ) disappearTicks = 0; // When there's stamina depleted, reset the disappear ticks.
		
		disappearTicks ++;
		
		if ( disappearTicks < 150 )
			drawStamina ( stamina, mc, left-15, top );
	}
	
	public static void drawStamina ( StaminaCapability stamina, Minecraft mc, int left, int top )
	{
		mc.getTextureManager().bindTexture ( icons ); // Bind the icon textures
		
		float maxExhaustion = stamina.Get().GetMaxStamina ( mc.player );
		float ratio = stamina.Get().GetTotalStamina() / maxExhaustion;
		int barMaxWidth = (int) maxExhaustion / 4; // Used for length calculations.
		int startY = top;
		
		if ( ( ratio * 60 ) <= 20 && updateCounter % ( 4 * 3 + 1 ) == 0 ) startY = top + ( random.nextInt ( 3 ) - 1 ); // Randomly decide on a new position for the vibrations.
		
		mc.ingameGUI.drawTexturedModalRect ( // Draw the background for the bar.
				left - ( barMaxWidth + ( ( 30 - barMaxWidth ) / 2 ) ) + 4 - 1, startY - 10, 81 - barMaxWidth, 54, barMaxWidth + 2, 4 );
		
		double drawWidth = stamina.Get().GetTotalStamina(); int drawTimes = 0;

		while ( drawWidth > 0 ) { // Begin drawing bars one after another.
			double drawRatio = Math.min(drawWidth, maxExhaustion) / maxExhaustion;
			int width = (int) ( ( drawWidth > 100 ) ? barMaxWidth : barMaxWidth * drawRatio); // Determine the actual width for drawing the bar.
			
			drawWidth -= Math.min(maxExhaustion, 100); // Deincrement the drawable range.
			
			GlStateManager.color(0.5f + (0.1f * ( drawTimes + 1 )), 0.2f * ( drawTimes + 1 ), 0.1f * ( drawTimes + 1 ), 0.65f); // Apply a dynamic color.
			mc.ingameGUI.drawTexturedModalRect( left - (width + ( ( 30 - barMaxWidth ) / 2 ) ) + 4, startY - 9, 81 - width, 45, width, 2 );
			GlStateManager.resetColor();
			
			drawTimes ++;
		}
		
		GlStateManager.color(1, 1, 1, 1);
		mc.getTextureManager().bindTexture(Gui.ICONS); // rebind default icons
	}
	
}
