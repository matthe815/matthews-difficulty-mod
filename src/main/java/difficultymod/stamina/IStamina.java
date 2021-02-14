package difficultymod.stamina;

import difficultymod.api.stamina.ActionType;
import net.minecraft.entity.player.EntityPlayer;

public interface IStamina 
{
	public void SetStamina(float stamina);
	
	public float GetStamina();
	public float GetMaxStamina(EntityPlayer player);
	public float GetRegenerationRate();
	
	public boolean FireAction(String action, EntityPlayer player);
	public boolean FireAction(ActionType action, EntityPlayer player);
}
