package difficultymod.helpers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class EnchantmentHelper {

	/**
	 * Check if the player has specified enchantment on any of the gear.
	 * @param player The player to check for an enchantment.
	 * @param id The ID of the enchantment to check for.
	 * @return Whether or not the enchantment is applied to any armor.
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
