package difficultymod.events;

import difficultymod.api.capability.CapabilityHelper;
import difficultymod.api.stamina.ActionType;
import difficultymod.capabilities.stamina.IStamina;
import difficultymod.capabilities.stamina.Stamina;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.capabilities.stamina.StaminaProvider;
import difficultymod.capabilities.temperature.TemperatureCapability;
import difficultymod.capabilities.temperature.TemperatureProvider;
import difficultymod.capabilities.temperature.ITemperature;
import difficultymod.capabilities.temperature.Temperature;
import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import difficultymod.capabilities.thirst.ThirstProvider;
import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import difficultymod.core.init.PotionInit;
import difficultymod.networking.PlayerLeftClickAirPacket;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class LivingEvent {
	
	String[] depletion;
	
	public static short heat_res_id=0,
						cold_res_id=0;
	
	@SubscribeEvent
	public void onSpawn (PlayerLoggedInEvent event)
	{
		if (event.player.world.getBiome( event.player.world.getSpawnPoint() ).getDefaultTemperature() >= 0.8) return;
		
		// Only give gear on first spawn.
		NBTTagCompound compound = new NBTTagCompound();
		compound.setBoolean("firstJoin", true);
		event.player.writeToNBT(compound);
		
		event.player.inventory.armorInventory.set(3, (new ItemStack ( difficultymod.core.init.Items.WOOL_HAT ) ));
		event.player.inventory.armorInventory.set(2, (new ItemStack ( difficultymod.core.init.Items.WOOL_CHESTPLATE ) ));
		event.player.inventory.armorInventory.set(1, (new ItemStack ( difficultymod.core.init.Items.WOOL_LEGGINGS ) ));
		event.player.inventory.armorInventory.set(0, (new ItemStack ( difficultymod.core.init.Items.WOOL_BOOTS ) ));
		
		event.player.inventory.addItemStackToInventory(new ItemStack ( difficultymod.core.init.Items.HOT_DRINK ));
	}
	
	@SubscribeEvent
	public void onEnchantmentUpdate (PlayerTickEvent event) 
	{
		if (!(event.player instanceof EntityPlayer))
			return;
		
		EntityPlayer player = event.player;
		
		if (heat_res_id == 0)
			heat_res_id = (short)Enchantment.getEnchantmentID(DifficultyMod.heat_resistance);
			
		if (cold_res_id == 0)
			cold_res_id = (short)Enchantment.getEnchantmentID(DifficultyMod.cold_resistance);
	}
	
	@SubscribeEvent
	public void onFoodEat (LivingEntityUseItemEvent.Finish event)
	{
		if (!(event.getEntity() instanceof EntityPlayer) || ! (event.getItem().getItem() instanceof ItemFood) ) return;
		IStamina stamina =  CapabilityHelper.GetStamina((EntityPlayer) event.getEntity( ) );
		stamina.Add(new Stamina( ).SetStamina( ( (ItemFood) event.getItem().getItem() ).getHealAmount( event.getItem( ) ) * 10 ) );
	}
	
	@SubscribeEvent
	public void onGameTick (PlayerTickEvent event)
	{		
		if ( event.player.world.isRemote || event.player.isCreative ( ) )
			return;
		
		// Allow a game-tick to occur when the player has the capability and the server has it enabled.
		if (CapabilityHelper.GetThirst(event.player) != null) {
			CapabilityHelper.GetThirst(event.player).OnTick(event.phase);
		}

		
		if (CapabilityHelper.GetStamina(event.player) != null) {
			CapabilityHelper.GetStamina(event.player).OnTick(event.phase);
		}
		
		// Temperature triggers.
		if (!ConfigHandler.common.temperatureSettings.disableTemperature) {
			if (event.player.hasCapability(TemperatureProvider.TEMPERATURE, null)) {
				TemperatureCapability temp = (TemperatureCapability)event.player.getCapability(TemperatureProvider.TEMPERATURE, null);
				Temperature currentTemperature = temp.Get();
				
				temp.SetPlayer(event.player);
				
				if (currentTemperature.ordinal() < 2 && !event.player.isPotionActive(PotionInit.HYPOTHERMIA))
					event.player.addPotionEffect(new PotionEffect(PotionInit.HYPOTHERMIA, ConfigHandler.common.temperatureSettings.climateDamageLength));
				else if (currentTemperature.ordinal() > 2 && !event.player.isPotionActive(PotionInit.HYPERTHERMIA))
					event.player.addPotionEffect(new PotionEffect(PotionInit.HYPERTHERMIA, ConfigHandler.common.temperatureSettings.climateDamageLength));
				
				// Effects applied if the effect is already active.
				if (event.player.isPotionActive(PotionInit.HYPOTHERMIA) && temp.damageTicks > 49 * ( currentTemperature == Temperature.FREEZING ? 2 : 1 ))
					PotionInit.HYPOTHERMIA.performEffect(event.player,  ( currentTemperature == Temperature.FREEZING ? 2 : 1 ));
				else if (event.player.isPotionActive(PotionInit.HYPERTHERMIA) && temp.damageTicks > 49  * ( currentTemperature == Temperature.BURNING ? 2 : 1 ))
					PotionInit.HYPERTHERMIA.performEffect(event.player,  ( currentTemperature == Temperature.BURNING ? 2 : 1 ));
				
				if (temp.damageTicks > 50) temp.damageTicks = 0;
				temp.damageTicks ++;
			}

		}
	}
	
	@SubscribeEvent
	public void mineBlock(BlockEvent.BreakEvent event)
	{
		if (event.getPlayer().world.isRemote || ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
		StaminaCapability stamina = (StaminaCapability) event.getPlayer().getCapability(StaminaProvider.STAMINA, null);
		
		if (!stamina.FireAction(ActionType.MINING, 8f))
			event.setCanceled(true);
	}
	
	@SubscribeEvent
	public void hitMob(AttackEntityEvent event)
	{
		if (event.getEntityPlayer().world.isRemote || ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
		StaminaCapability stamina = (StaminaCapability) event.getEntityPlayer().getCapability(StaminaProvider.STAMINA, null);
		
		if (!stamina.FireAction(ActionType.ATTACKING, 5f))
			event.setCanceled(true);
	}
	
	@SubscribeEvent(priority=EventPriority.LOWEST)
	public void playerJump(LivingJumpEvent event)
	{
		if (!(event.getEntity() instanceof EntityPlayer))
			return;
		
		EntityPlayer player = (EntityPlayer) event.getEntity();
		IStamina stamina = CapabilityHelper.GetStamina(player);
		
        /* Stop the player from jumping in the case that they have no stamina. */
        if (stamina.Get().GetTotalStamina() < 15)
            player.motionY = 0.0;
        
		if (player.world.isRemote || player.isCreative() || ConfigHandler.common.staminaSettings.disableStamina)
			return;
		
		stamina.FireAction(ActionType.JUMPING, 15f);
        ThirstCapability thirstStats = (ThirstCapability) player.getCapability(ThirstProvider.THIRST, null);
        thirstStats.Add(new Thirst().SetExhaustion(player.isSprinting() ? 0.8F : 0.2F));
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
				System.out.println("Water clicked");
		}
		
	}
	
	public static float clamp(float val, float min, float max) {
	    return Math.max(min, Math.min(max, val));
	}
}
