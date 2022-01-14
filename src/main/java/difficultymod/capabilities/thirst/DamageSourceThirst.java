package difficultymod.capabilities.thirst;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceThirst extends DamageSource {
	
	public DamageSourceThirst() {
		super("source.thirstDamage");
		
		this.setDamageBypassesArmor();
		this.setDamageIsAbsolute();
	}
	
	@Override
	public float getHungerDamage() {
		return 1.0f;
	}
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entity) {
		return new TextComponentString(String.format("%s died to hydration.", entity.getName()));
	}
	
	@Override
	public boolean isUnblockable() {
		return true;
	}
	
}
