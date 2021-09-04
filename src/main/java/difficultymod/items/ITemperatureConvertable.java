package difficultymod.items;

import net.minecraft.item.Item;

public interface ITemperatureConvertable {
	public Item GetWarmItem();
	public Item GetColdItem();
	public Item GetNeutralItem();
}
