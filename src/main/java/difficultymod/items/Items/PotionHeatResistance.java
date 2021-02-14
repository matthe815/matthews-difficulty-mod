package difficultymod.items.Items;

import difficultymod.items.BasePotion;
import net.minecraft.entity.EntityLivingBase;

public class PotionHeatResistance extends BasePotion {
	
	public PotionHeatResistance(int id) {
		super(false, 0xFFA500, 4, 1);
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
