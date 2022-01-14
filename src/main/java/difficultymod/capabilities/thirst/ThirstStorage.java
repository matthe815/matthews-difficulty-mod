package difficultymod.capabilities.thirst;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ThirstStorage implements IStorage<IThirst> 
{
    @Override
    public NBTBase writeNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side) 
    {
        NBTTagCompound compound = new NBTTagCompound();
        
        compound.setDouble(capability.getName(), instance.Get().thirst);

        return compound;
    }

    @Override
    public void readNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side, NBTBase nbt) 
    {
        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Capabilities must be read from an NBTTagCompound!");
        
        NBTTagCompound compound = (NBTTagCompound)nbt;
        
        if (compound.hasKey(capability.getName()))
            instance.Set(new Thirst().SetThirst((int)compound.getDouble(capability.getName())));
    }
}
