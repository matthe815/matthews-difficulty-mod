package difficultymod.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class EnchantmentHelper {

	/**
	 * Check if the player has specified enchantment on any of the gear.
	 * @param player
	 * @param id
	 * @return
	 */
	public static boolean PlayerHasEnchantment (EntityPlayer player, int id) {
		for (ItemStack item : player.getArmorInventoryList()) {
            NBTTagList tagList = item.getEnchantmentTagList();
            
            for (int i = 0; i < tagList.tagCount(); i++) {
                NBTTagCompound idTag = tagList.getCompoundTagAt(i);
                if (idTag.getShort("id") == id) return true;
            }
		}
		
		return false;
	}
	
}
