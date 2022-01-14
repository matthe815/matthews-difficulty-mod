package difficultymod.items.Items;

import difficultymod.creativetabs.CreativeTabHandler;
import difficultymod.items.BaseItemBucket;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EmptyCanteen extends BaseItemBucket 
{
	
	public EmptyCanteen() 
	{
		super("emptycanteen", CreativeTabHandler.DifficultyModTab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		RayTraceResult result = this.rayTrace(worldIn, playerIn, true); 
		ItemStack      stack  = playerIn.getHeldItemMainhand();
		
		// Give up if there's no results from raytracing the block position.
		if (result == null) return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		
		BlockPos  pos   = result.getBlockPos();
		Block     block = worldIn.getBlockState(pos).getBlock();
		
		// If there's no block at that position, just give up there.
		if (block == null) return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		
		// If you're not interacting with water, you can stop here.
		if (block.getRegistryName() != Blocks.WATER.getRegistryName()) return new ActionResult<ItemStack>(EnumActionResult.FAIL, stack);
		
		// Play a bottle filling sound.
		playerIn.playSound(new SoundEvent(new ResourceLocation("minecraft:item.bottle.fill")), 1f, 1f);
		
		super.onItemRightClick(worldIn, playerIn, handIn);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, new ItemStack(Item.getByNameOrId("difficultymod:canteen1")));
	}
}
