package difficultymod.capabilities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;

public interface IBaseCapability 
{
	void OnTick(Phase phase);

	void SetPlayer(EntityPlayer player);

	boolean HasChanged();

	void onSendClientUpdate();
}
