package difficultymod.capabilities.temperature;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceHypothermia extends DamageSource {
	
	public DamageSourceHypothermia() {
		super("source.hypothermia");
		
		this.setDamageBypassesArmor();
		this.setDamageIsAbsolute();
	}
	
	@Override
	public float getHungerDamage() {
		return 1.0f;
	}
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entity) {
		return new TextComponentString(String.format("%s froze to death.", entity.getName()));
	}
	
	@Override
	public boolean isUnblockable() {
		return true;
	}
	
}
