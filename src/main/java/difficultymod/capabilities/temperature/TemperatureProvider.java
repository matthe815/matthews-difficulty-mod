
package difficultymod.capabilities.temperature;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class TemperatureProvider implements ICapabilitySerializable<NBTBase>
{
    @CapabilityInject(ITemperature.class)
    public static final Capability<ITemperature> TEMPERATURE = null;

    private ITemperature instance = TEMPERATURE.getDefaultInstance();
    
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == TEMPERATURE;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == TEMPERATURE ? TEMPERATURE.<T> cast(this.instance) : null;
	}

	@Override
	public NBTBase serializeNBT() {
		return TEMPERATURE.getStorage().writeNBT(TEMPERATURE, this.instance, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		TEMPERATURE.getStorage().readNBT(TEMPERATURE, this.instance, null, nbt);
	}

}