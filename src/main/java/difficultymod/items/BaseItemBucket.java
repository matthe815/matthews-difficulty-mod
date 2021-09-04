package difficultymod.items;

import difficultymod.core.ConfigHandler;
import difficultymod.core.DifficultyMod;
import lieutenant.registry.RegisterHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BaseItemBucket extends ItemBucket 
{
	
	public BaseItemBucket(String name, CreativeTabs creativeTab)
	{
		super(null);
		
		setUnlocalizedName(DifficultyMod.MODID + "." + name)
			.setRegistryName(name)
			.setCreativeTab(creativeTab);
		
		RegisterHandler.AddItem(this);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if (!ConfigHandler.common.mechanics.finiteWater)
			return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItemMainhand());
		
		RayTraceResult trace = this.rayTrace(worldIn, playerIn, true);
		worldIn.setBlockToAir(trace.getBlockPos());
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItemMainhand());
	}
	
	
}
