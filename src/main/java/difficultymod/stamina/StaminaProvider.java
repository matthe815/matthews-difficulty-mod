package difficultymod.stamina;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class StaminaProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IStamina.class)
    public static final Capability<IStamina> STAMINA = null;

    private IStamina instance = STAMINA != null ? STAMINA.getDefaultInstance() : null;
    
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == STAMINA;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == STAMINA ? STAMINA.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {		
		return STAMINA.getStorage().writeNBT(STAMINA, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		if (StaminaProvider.STAMINA == null)
			return;
		
		STAMINA.getStorage().readNBT(STAMINA, this.instance, null, nbt);
	}

}
