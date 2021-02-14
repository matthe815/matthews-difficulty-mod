package difficultymod.temperature;

import net.minecraft.entity.player.EntityPlayer;

public interface ITemp 
{
	public Temperature GetTemperature(EntityPlayer player);
	
	public int GetTemperaturePoint();
	public void AddTemperaturePoint();
	public void ResetTemperaturePoint();
	public void SetTemperature(Temperature temp);
	
	public void Update();
	public boolean HasChanged();
	public void onSendClientUpdate();
	
	public int GetPlayerArmorPenalty(EntityPlayer player);
}
