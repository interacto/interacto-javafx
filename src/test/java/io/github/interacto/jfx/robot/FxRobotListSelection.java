package io.github.interacto.jfx.robot;

import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import org.testfx.api.FxRobot;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * TestFX does not provide all the required routines to test GUIs. This trait defines routines for
 * selecting registeredItems in combo boxes and lists.
 */
public interface FxRobotListSelection {
	default <T> void selectGivenComboBoxItem(final FxRobot robot, final ComboBox<T> combo, final T item) {
		final int index = combo.getItems().indexOf(item);
		final int indexSel = combo.getSelectionModel().getSelectedIndex();

		if(index == -1) {
			fail("The item " + item + " is not in the combo box " + combo);
		}

		robot.clickOn(combo);

		if(index > indexSel) {
			for(int i = indexSel; i < index; i++) {
				robot.type(KeyCode.DOWN);
			}
		}else {
			if(index < indexSel) {
				for(int i = indexSel; i > index; i--) {
					robot.type(KeyCode.UP);
				}
			}
		}

		robot.type(KeyCode.ENTER);
	}
}
