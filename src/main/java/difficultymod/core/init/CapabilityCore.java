package difficultymod.core.init;

import difficultymod.capabilities.stamina.IStamina;
import difficultymod.capabilities.stamina.Stamina;
import difficultymod.capabilities.stamina.StaminaCapability;
import difficultymod.capabilities.temperature.ITemperature;
import difficultymod.capabilities.temperature.TemperatureCapability;
import difficultymod.capabilities.thirst.IThirst;
import difficultymod.capabilities.thirst.Thirst;
import difficultymod.capabilities.thirst.ThirstCapability;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityCore {
	public static void init ()
	{
		CapabilityManager.INSTANCE.register(IThirst.class, new Capability.IStorage<IThirst>() {
		    @Override
		    public NBTBase writeNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side) 
		    {
		        NBTTagCompound compound = new NBTTagCompound();
		        
		        compound.setInteger("thirst", instance.Get().thirst);
		        compound.setDouble("hydration", ((ThirstCapability)instance).Get().hydration);

		        return compound;
		    }

		    @Override
		    public void readNBT(Capability<IThirst> capability, IThirst instance, EnumFacing side, NBTBase nbt) 
		    {
		        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Capabilities must be read from an NBTTagCompound!");
		        
		        NBTTagCompound compound = (NBTTagCompound)nbt;
		        
		        if (compound.hasKey("thirst"))
		        	((ThirstCapability)instance).Set(new Thirst().SetThirst(compound.getInteger("thirst")));
		        
		        if (compound.hasKey("hydration"))
		        	((ThirstCapability)instance).Add(new Thirst().SetHydration(compound.getDouble("hydration")));
		    }
		}, ThirstCapability::new);
		
		// Stamina
		CapabilityManager.INSTANCE.register(IStamina.class, new Capability.IStorage<IStamina>() {
		    @Override
		    public NBTBase writeNBT(Capability<IStamina> capability, IStamina instance, EnumFacing side) 
		    {
		        NBTTagCompound compound = new NBTTagCompound();
		        
		        compound.setDouble(capability.getName(), ((StaminaCapability)instance).Get().stamina);
		        compound.setFloat("fitness", instance.Get().fitness);

		        return compound;
		    }

		    @Override
		    public void readNBT(Capability<IStamina> capability, IStamina instance, EnumFacing side, NBTBase nbt) 
		    {
		        if (!(nbt instanceof NBTTagCompound)) throw new IllegalArgumentException("Capabilities must be read from an NBTTagCompound!");
		        
		        NBTTagCompound compound = (NBTTagCompound)nbt;
		        
		        if (compound.hasKey(capability.getName()))
		        	((StaminaCapability)instance).Set(new Stamina().SetStamina(compound.getFloat(capability.getName())));
		        
		        if (compound.hasKey("fitness"))
		        	((StaminaCapability)instance).Set(new Stamina().SetFitness(compound.getFloat("fitness")));
		        	
		    }
		}, StaminaCapability::new);
		
		// Temperatures
		CapabilityManager.INSTANCE.register(ITemperature.class, new Capability.IStorage<ITemperature>() {
		    @Override
		    public NBTBase writeNBT(Capability<ITemperature> capability, ITemperature instance, EnumFacing side) 
		    {
		        NBTTagCompound compound = new NBTTagCompound();
		        return compound;
		    }

		    @Override
		    public void readNBT(Capability<ITemperature> capability, ITemperature instance, EnumFacing side, NBTBase nbt) 
		    {
		    }
		}, TemperatureCapability::new);
	}
}