package difficultymod.items.Items;

import difficultymod.core.ConfigHandler;
import difficultymod.items.BasePotion;
import net.minecraft.entity.EntityLivingBase;

public class PotionThirstQuench extends BasePotion {
	
	public PotionThirstQuench(int id) {
		super(false, 0x0002b, 1, 1);
	}
	
    @Override
    public void performEffect(EntityLivingBase entity, int amplifier)
    {
    	if (ConfigHandler.Debug_Options.showMiscMessages)
    		System.out.println("Potion event fired");
    	
    	entity.setHealth(entity.getHealth()+1);
    }
    
    @Override
    public boolean isReady(int duration, int amplifier)
    {
        int time = 150 >> amplifier;
        return time > 0 ? duration % time == 0 : true;
    }
	
}
