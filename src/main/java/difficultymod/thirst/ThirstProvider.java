package difficultymod.thirst;
import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ThirstProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(IThirst.class)
    public static final Capability<IThirst> THIRST = null;

    private IThirst instance = THIRST.getDefaultInstance();
    
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == THIRST;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == THIRST ? THIRST.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return THIRST.getStorage().writeNBT(THIRST, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		THIRST.getStorage().readNBT(THIRST, this.instance, null, nbt);
	}

}
