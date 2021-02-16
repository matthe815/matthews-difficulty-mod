package difficultymod.api.gui;

import difficultymod.gui.handlers.GUIController;

public class GUIHelper {
	public void GUISetCancelled(DifficultyModGUI type, boolean cancelled)
	{
		switch (type) {
			case THIRST:
				GUIController.thirstCanceled = cancelled;
			break;
			
			case HUNGER:
				GUIController.hungerCanceled = cancelled;
			break;
		}
	}
}
