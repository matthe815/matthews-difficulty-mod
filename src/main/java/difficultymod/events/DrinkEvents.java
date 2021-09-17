package difficultymod.events;

import difficultymod.api.thirst.Drink;
import difficultymod.api.thirst.ThirstHelper;
import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class DrinkEvents 
{		
	@SubscribeEvent
	public void livingEntityUseItem(LivingEntityUseItemEvent.Finish event)
	{	
		if (!(event.getEntity() instanceof EntityPlayer) || event.getEntity().world.isRemote)
			return;
		
		EntityPlayer player = (EntityPlayer) event.getEntity();
			
		ItemStack item = event.getItem();
		
		if (!(item.getItem() instanceof ItemBucketMilk || item.getItem() instanceof ItemPotion)) // Only allow this to go through if it's a drinkable item.
			return;
		
		ThirstCapability thirst = ThirstHelper.GetPlayer(player);
		Drink drink = ThirstHelper.GetDrink(item.getItem().getRegistryName());
		
		thirst.Add(new Thirst().SetThirst((drink != null ? drink.GetThirstValue() : 3))); // Restore thirst based on the item drank.
	}
}
