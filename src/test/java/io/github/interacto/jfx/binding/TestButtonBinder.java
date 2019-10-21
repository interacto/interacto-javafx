package io.github.interacto.jfx.binding;

import io.github.interacto.jfx.interaction.help.HelpAnimation;
import io.github.interacto.logging.LogLevel;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(ApplicationExtension.class)
public class TestButtonBinder extends TestNodeBinder<Button> {
	@Override
	@Start
	public void start(final Stage stage) {
		widget1 = new Button("button1");
		widget2 = new Button("button2");
		super.start(stage);
	}

	@Test
	public void testCommandExecutedOnSingleButtonConsumer(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.toProduce(() -> cmd)
			.on(widget1)
			.bind();
		disposable = binding.produces().subscribe(producedCmds::add);

		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
		assertEquals(1, producedCmds.size());
		assertEquals(cmd, producedCmds.get(0));
	}

	@Test
	public void testCommandExecutedOnSingleButtonFunction(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.on(widget1)
			.toProduce(i -> cmd)
			.bind();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testIsOnUIThread(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.on(widget1)
			.toProduce(() -> cmd)
			.end(c -> assertTrue(Platform.isFxApplicationThread()))
			.bind();
		robot.clickOn(widget1);
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoButtons(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.toProduce(() -> cmd)
			.on(widget1, widget2)
			.bind();
		disposable = binding.produces().subscribe(producedCmds::add);

		robot.clickOn(widget2);
		assertEquals(1, cmd.exec.get());
		cmd = new StubCmd();
		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertNotNull(binding);
		assertEquals(2, producedCmds.size());
		assertNotSame(producedCmds.get(0), producedCmds.get(1));
	}

	@Test
	public void testCollectionButtons(final FxRobot robot) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1, widget2);
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(buttons)
			.end(i -> cpt.incrementAndGet())
			.bind();

		robot.clickOn(widget2);
		robot.clickOn(widget1);
		assertEquals(2, cpt.get());
		assertNotNull(binding);
	}

	@Test
	public void testCollectionButtonsRemove(final FxRobot robot) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1, widget2);
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.buttonBinder()
			.toProduce(StubCmd::new)
			.on(buttons)
			.end(i -> cpt.incrementAndGet())
			.bind();

		buttons.remove(widget1);

		robot.clickOn(widget2);
		robot.clickOn(widget1);
		assertEquals(1, cpt.get());
		assertNotNull(binding);
	}

	@Test
	public void testCollectionButtonsAdd(final FxRobot robot) {
		final ObservableList<Button> buttons = FXCollections.observableArrayList(widget1);
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.buttonBinder()
			.on(buttons)
			.toProduce(StubCmd::new)
			.end(i -> cpt.incrementAndGet())
			.bind();

		buttons.add(widget2);

		robot.clickOn(widget2);
		assertEquals(1, cpt.get());
		assertNotNull(binding);
	}


	@Test
	public void testInit1Executed(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.on(widget1)
			.toProduce(() -> cmd)
			.first(c -> c.exec.setValue(10))
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testInit2Executed(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.toProduce(i -> cmd)
			.first((i, c) -> c.exec.setValue(10))
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(11, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCheckFalse(final FxRobot robot) {
		binding = Bindings.buttonBinder()
			.toProduce(i -> cmd)
			.when(i -> false)
			.on(widget1)
			.bind();
		robot.clickOn(widget1);
		assertEquals(0, cmd.exec.get());
		assertNotNull(binding);
	}

	@Test
	public void testCommandExecutedOnTwoButtonsSame(final FxRobot robot) {
		final AtomicInteger cpt = new AtomicInteger();

		binding = Bindings.buttonBinder()
			.on(widget1, widget1)
			.toProduce(() -> cmd)
			.end(i -> cpt.incrementAndGet())
			.bind();

		robot.clickOn(widget1);
		assertEquals(1, cmd.exec.get());
		assertEquals(1, cpt.get());
		assertNotNull(binding);
	}


	@Test
	public void testBuilderCloned() {
		final var binder = Bindings.buttonBinder();
		assertNotSame(binder, Bindings.buttonBinder());
		assertNotSame(binder, Bindings.buttonBinder().toProduce(i -> cmd));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(() -> cmd));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(() -> cmd).first((i, c) -> { }));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(() -> cmd).first(c -> { }));
		assertNotSame(binder, Bindings.buttonBinder().on(widget1));
		assertNotSame(binder, Bindings.buttonBinder().on(FXCollections.observableArrayList(widget1)));
		assertNotSame(binder, Bindings.buttonBinder().when(() -> false));
		assertNotSame(binder, Bindings.buttonBinder().when(i -> false));
		assertNotSame(binder, Bindings.buttonBinder().toProduce(i -> cmd).end(c -> { }));
		assertNotSame(binder, Bindings.buttonBinder().help((Pane) null));
		assertNotSame(binder, Bindings.buttonBinder().help((HelpAnimation) null));
		assertNotSame(binder, Bindings.buttonBinder().async(widget1, null, null));
		assertNotSame(binder, Bindings.buttonBinder().log(LogLevel.COMMAND));
	}

	@Test
	void testClonedBuildersSameWidgetCmdOK(final FxRobot robot) {
		final AtomicInteger cpt1 = new AtomicInteger();
		final AtomicInteger cpt2 = new AtomicInteger();
		final var binder = Bindings
			.buttonBinder()
			.toProduce(StubCmd::new)
			.on(widget1);
		final var binding1 = binder
			.end(i -> cpt1.incrementAndGet())
			.bind();
		final var binding2 = binder
			.end(i -> cpt2.incrementAndGet())
			.bind();

		robot.clickOn(widget1);

		assertNotSame(binding1, binding2);
		assertEquals(1, cpt1.get());
		assertEquals(1, cpt2.get());
	}

	@Test
	void testClonedBuildersDiffWidgetsCmdOK(final FxRobot robot) {
		final AtomicInteger cpt1 = new AtomicInteger();
		final AtomicInteger cpt2 = new AtomicInteger();
		final var binder = Bindings
			.buttonBinder()
			.toProduce(StubCmd::new);
		final var binding1 = binder
			.on(widget1)
			.end(i -> cpt1.incrementAndGet())
			.bind();
		final var binding2 = binder
			.on(widget2)
			.end(i -> cpt2.incrementAndGet())
			.bind();

		robot.clickOn(widget1);

		assertNotSame(binding1, binding2);
		assertEquals(1, cpt1.get());
		assertEquals(0, cpt2.get());
	}
}
