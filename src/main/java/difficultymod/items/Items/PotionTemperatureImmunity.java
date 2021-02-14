package difficultymod.items.Items;

import difficultymod.items.BasePotion;
import net.minecraft.entity.EntityLivingBase;

public class PotionTemperatureImmunity extends BasePotion {
	
	public PotionTemperatureImmunity(int id) {
		super(false, 0x0002b, 1, 1);
	}
	
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int time = 50 >> amplifier;
        return time > 0 ? duration % time == 0 : true;
    }
	
}
