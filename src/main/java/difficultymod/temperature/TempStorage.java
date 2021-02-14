package difficultymod.temperature;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class TempStorage implements IStorage<ITemp> 
{
    @Override
    public NBTBase writeNBT(Capability<ITemp> capability, ITemp instance, EnumFacing side) 
    {
        NBTTagCompound compound = new NBTTagCompound();

        return compound;
    }

    @Override
    public void readNBT(Capability<ITemp> capability, ITemp instance, EnumFacing side, NBTBase nbt) 
    {
        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Thirst must be read from an NBTTagCompound!");

    }
}
