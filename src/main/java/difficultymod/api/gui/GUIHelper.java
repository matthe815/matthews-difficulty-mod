package difficultymod.api.gui;

import difficultymod.gui.handlers.ThirstHandler;

public class GUIHelper {
	public void GUISetCancelled(DifficultyModGUI type, boolean cancelled)
	{
		switch (type) {
			case THIRST:
				ThirstHandler.thirstCanceled = cancelled;
			break;
			
			case HUNGER:
				ThirstHandler.hungerCanceled = cancelled;
			break;
		}
	}
}
