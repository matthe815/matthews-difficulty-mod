package difficultymod.events;

import difficultymod.api.stamina.ActionType;
import difficultymod.capabilities.stamina.IStamina;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.capabilities.temperature.TemperatureCapability;
import difficultymod.capabilities.temperature.TemperatureProvider;
import difficultymod.capabilities.temperature.Temperature;
import difficultymod.capabilities.thirst.IThirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.core.init.PotionInit;
import difficultymod.networking.StaminaUpdatePacket;
import difficultymod.networking.ThirstUpdatePacket;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class LivingEvent {
	
	String[] depletion;
	
	public static short heat_res_id=0,
						cold_res_id=0;
	
	@SubscribeEvent
	public void livingUpdate(PlayerTickEvent event) 
	{
		if (!(event.player instanceof EntityPlayer))
			return;
		
		EntityPlayer player = event.player;
		boolean[] buffs = new boolean[] {false, false};
		
		if (heat_res_id == 0)
			heat_res_id = (short)Enchantment.getEnchantmentID(DifficultyMod.heat_resistance);
			
		if (cold_res_id == 0)
			cold_res_id = (short)Enchantment.getEnchantmentID(DifficultyMod.cold_resistance);
		
		for (ItemStack item : player.getArmorInventoryList()) {
            NBTTagList tagList = item.getEnchantmentTagList();
            
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound idTag = tagList.getCompoundTagAt(i);
                buffs[0] = idTag.getShort("id") == heat_res_id;
                buffs[1] = idTag.getShort("id") == cold_res_id;
            }
		}
	}
	
	@SubscribeEvent
	public void Update(PlayerTickEvent event)
	{		
		if (event.player.world.isRemote||event.player.isCreative())
			return;
		
		depletion = ConfigHandler.common.staminaSettings.actionDepletion;
		
		/* Register thirst processes in the case that it's enabled. */
		if (!ConfigHandler.common.thirstSettings.disableThirst) {
			if (event.player.hasCapability(ThirstProvider.THIRST, null)) 
			{
				ThirstCapability thirst = (ThirstCapability)event.player.getCapability(ThirstProvider.THIRST, null);
				thirst.SetPlayer(event.player);
				thirst.OnTick(event.phase);
				
				//if (thirst.HasChanged()) {
				//	thirst.onSendClientUpdate();
				//	DifficultyMod.network.sendTo(new ThirstUpdatePacket(thirst.Get()), (EntityPlayerMP) event.player);
				//}
			}

		}
		
		/* Register stamina processes if it's enabled. */
		if (!ConfigHandler.common.staminaSettings.disableStamina) {
			if (event.player.hasCapability(StaminaProvider.STAMINA, null))
			{
				StaminaCapability stamina = (StaminaCapability)event.player.getCapability(StaminaProvider.STAMINA, null);
				stamina.SetPlayer(event.player);
				stamina.OnTick(event.phase);
				
				if (stamina.HasChanged()) {
					stamina.onSendClientUpdate();
				}	
			}
		}
		
		// Temperature triggers.
		if (!ConfigHandler.common.temperatureSettings.disableTemperature) {
			if (event.player.hasCapability(TemperatureProvider.TEMPERATURE, null)) {
				TemperatureCapability temp = (TemperatureCapability)event.player.getCapability(TemperatureProvider.TEMPERATURE, null);
				
				temp.SetPlayer(event.player);
				temp.Get(); // Send an update tick.
				
				if (temp.HasChanged()) {
					temp.onSendClientUpdate();
				}
				
				if (temp.Get() == Temperature.FREEZING && !event.player.isPotionActive(PotionInit.HYPOTHERMIA))
					event.player.addPotionEffect(new PotionEffect(PotionInit.HYPOTHERMIA, ConfigHandler.common.temperatureSettings.climateDamageLength));
				else if (temp.Get() == Temperature.BURNING && !event.player.isPotionActive(PotionInit.HYPERTHERMIA))
					event.player.addPotionEffect(new PotionEffect(PotionInit.HYPERTHERMIA, ConfigHandler.common.temperatureSettings.climateDamageLength));
				
				// Effects applied if the effect is already active.
				if (event.player.isPotionActive(PotionInit.HYPOTHERMIA))
					PotionInit.HYPOTHERMIA.performEffect(event.player, 1);
				else if (event.player.isPotionActive(PotionInit.HYPERTHERMIA))
					PotionInit.HYPERTHERMIA.performEffect(event.player, 1);	
			}

		}
	}
	
	@SubscribeEvent
	public void mineBlock(BlockEvent.BreakEvent event)
	{
		if (event.getPlayer().world.isRemote || ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
		StaminaCapability stamina = (StaminaCapability) event.getPlayer().getCapability(StaminaProvider.STAMINA, null);
		
		if (!stamina.FireAction(ActionType.MINING, event.getPlayer()))
			event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void hitMob(AttackEntityEvent event)
	{
		if (event.getEntityPlayer().world.isRemote || ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
		StaminaCapability stamina = (StaminaCapability) event.getEntityPlayer().getCapability(StaminaProvider.STAMINA, null);
		
		if (!stamina.FireAction(ActionType.ATTACKING, event.getEntityPlayer()))
			event.setCanceled(true);
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void playerJump(LivingJumpEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) event.getEntity();
		StaminaCapability stamina = (StaminaCapability) player.getCapability(StaminaProvider.STAMINA, null);
		
        /* Stop the player from jumping in the case that they have no stamina. */
        if (!stamina.FireAction(ActionType.JUMPING, (EntityPlayer)event.getEntity()))
            player.motionY = 0.0;
        
		if (player.world.isRemote || player.isCreative() || ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
        ThirstCapability thirstStats = (ThirstCapability) player.getCapability(ThirstProvider.THIRST, null);
        thirstStats.AddExhaustion(player.isSprinting() ? 0.8F : 0.2F);
	}
	
	@SubscribeEvent
	public void blockInteract(PlayerInteractEvent.RightClickBlock event)
	{
		if (event.getWorld().isRemote)
			return;

		if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.CACTUS) {
			if (ConfigHandler.Debug_Options.showMiscMessages)
				System.out.println("Cactus right-clicked");
			
			if (!(event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ItemSword))
				return;
			
			if (event.getEntityPlayer().inventory.hasItemStack(new ItemStack(Items.GLASS_BOTTLE))) {
				event.getEntityPlayer().inventory.removeStackFromSlot(event.getEntityPlayer().inventory.getSlotFor(new ItemStack(Items.GLASS_BOTTLE)));
				event.getEntityPlayer().inventory.addItemStackToInventory(new ItemStack(Item.getByNameOrId("difficultymod:cactus_juice")));
			}
		} else if (event.getWorld().getBlockState(event.getPos()).getBlock() == Blocks.WATER) {
			if (ConfigHandler.Debug_Options.showMiscMessages)
				System.out.println("Water clicked");
		}
		
	}
	
	public static float clamp(float val, float min, float max) {
	    return Math.max(min, Math.min(max, val));
	}
}
