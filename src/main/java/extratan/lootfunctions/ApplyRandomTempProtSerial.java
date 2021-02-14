package extratan.lootfunctions;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunction.Serializer;

public class ApplyRandomTempProtSerial extends Serializer<LootFunction> {
	protected ApplyRandomTempProtSerial(LootCondition[] conditionsIn) {
		super(new ResourceLocation("extratan:apply_random_temp_prot"), LootFunction.class);
	}

	@Override
	public void serialize(JsonObject object, LootFunction functionClazz,
			JsonSerializationContext serializationContext) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public LootFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext,
			LootCondition[] conditionsIn) {
		// TODO Auto-generated method stub
		return null;
	}
}
