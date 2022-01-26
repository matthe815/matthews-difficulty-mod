package difficultymod.items.Items;

import difficultymod.core.DifficultyMod;
import difficultymod.items.BasePotion;
import net.minecraft.entity.EntityLivingBase;

public class PotionHypoThermia extends BasePotion {
	
	public PotionHypoThermia(int id) {
		super(false, 0x0002b, 2, 0);
	}
	
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
		entity.attackEntityFrom(DifficultyMod.SOURCE_HYPO, 1f * amplifier);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
	
}
