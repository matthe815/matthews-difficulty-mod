package difficultymod.capabilities.stamina;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class StaminaStorage implements IStorage<IStamina> 
{
    @Override
    public NBTBase writeNBT(Capability<IStamina> capability, IStamina instance, EnumFacing side) 
    {
        NBTTagCompound compound = new NBTTagCompound();
        
        compound.setDouble("stamina", instance.Get().GetTotalStamina());

        return compound;
    }

    @Override
    public void readNBT(Capability<IStamina> capability, IStamina instance, EnumFacing side, NBTBase nbt) 
    {
        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Capabilities must be read from an NBTTagCompound!");
        
        NBTTagCompound compound = (NBTTagCompound)nbt;
        
        if (compound.hasKey(capability.getName()))
            instance.Set(new Stamina().SetStamina((int)compound.getDouble("stamina")));
    }
}
