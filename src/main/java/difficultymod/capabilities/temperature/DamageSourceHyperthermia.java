package difficultymod.capabilities.temperature;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

public class DamageSourceHyperthermia extends DamageSource {
	
	public DamageSourceHyperthermia() {
		super("source.hyperthermia");
		
		this.setDamageBypassesArmor();
		this.setDamageIsAbsolute();
	}
	
	@Override
	public float getHungerDamage() {
		return 1.0f;
	}
	
	@Override
	public ITextComponent getDeathMessage(EntityLivingBase entity) {
		return new TextComponentString(String.format("%s overheated.", entity.getName()));
	}
	
	@Override
	public boolean isUnblockable() {
		return true;
	}
	
}
