package difficultymod.core.init;

import difficultymod.stamina.IStamina;
import difficultymod.stamina.Stamina;
import difficultymod.stamina.StaminaStorage;
import difficultymod.temperature.ITemp;
import difficultymod.temperature.Temp;
import difficultymod.temperature.TempStorage;
import difficultymod.thirst.IThirst;
import difficultymod.thirst.Thirst;
import difficultymod.thirst.ThirstStorage;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityCore {
	public static void init ()
	{
		CapabilityManager.INSTANCE.register(IThirst.class, new ThirstStorage(), Thirst::new);
		CapabilityManager.INSTANCE.register(IStamina.class, new StaminaStorage(), Stamina::new);
		CapabilityManager.INSTANCE.register(ITemp.class, new TempStorage(), Temp::new);
	}
}
