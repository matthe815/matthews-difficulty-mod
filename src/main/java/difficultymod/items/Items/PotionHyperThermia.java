package difficultymod.items.Items;

import difficultymod.items.BasePotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;

public class PotionHyperThermia extends BasePotion {
	public PotionHyperThermia(int id) {
		super(false, 0x0002b, 3, 0);
	}
	
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
		entity.attackEntityFrom(DamageSource.MAGIC, 1);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        return true;
    }
}
