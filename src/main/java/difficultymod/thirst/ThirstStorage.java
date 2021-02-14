package difficultymod.thirst;

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
        
        compound.setInteger("thirstLevel", instance.GetThirst());
        compound.setInteger("thirstTimer", instance.GetChangeTime());
        compound.setFloat("thirstHydrationLevel", instance.GetHydration());
        compound.setFloat("thirstExhaustionLevel", instance.GetExhaustion());

        return compound;
    }

    @Override
    public void readNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side, NBTBase nbt) 
    {
        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Thirst must be read from an NBTTagCompound!");
        
        NBTTagCompound compound = (NBTTagCompound)nbt;
        
        if (compound.hasKey("thirstLevel"))
        {
            instance.SetThirst(compound.getInteger("thirstLevel"));
            instance.SetHydration(compound.getInteger("thirstHydrationLevel"));
            instance.SetExhaustion(compound.getInteger("thirstExhaustionLevel"));
            instance.SetChangeTime(compound.getInteger("thirstTimer"));
        }

    }
}
