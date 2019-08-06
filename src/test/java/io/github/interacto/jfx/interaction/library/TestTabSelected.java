package io.github.interacto.jfx.interaction.library;

import io.github.interacto.fsm.CancelFSMException;
import java.util.Collections;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class TestTabSelected extends BaseWIMPWidgetTest<TabPane, TabSelected> {
	Tab tab1;
	Tab tab2;

	@Override
	@BeforeEach
	void setUp() {
		super.setUp();
		tab1 = new Tab();
		tab2 = new Tab();
		wimpWidget.getTabs().addAll(tab1);
		wimpWidget.getTabs().addAll(tab2);
		wimpWidget.getSelectionModel().select(0);
	}

	@Override
	TabPane createWidget() {
		return new TabPane();
	}

	@Override
	void triggerWidget() {
		wimpWidget.fireEvent(new TabEvent(wimpWidget, null));
	}

	@Override
	protected TabSelected createInteraction() {
		return new TabSelected();
	}

	@Override
	@Test
	void testProcessEventGoodState() throws CancelFSMException {
		interaction.processEvent(new TabEvent(wimpWidget, null));
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}

	@Override
	@Test
	void testRegister() throws CancelFSMException {
		interaction.registerToNodes(Collections.singletonList(wimpWidget));
		wimpWidget.getSelectionModel().select(1);
		Mockito.verify(handler, Mockito.times(1)).fsmStops();
		Mockito.verify(handler, Mockito.times(1)).fsmStarts();
	}
}
