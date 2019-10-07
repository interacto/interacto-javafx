package io.github.interacto.jfx.binding;

import io.github.interacto.command.AutoUnbind;
import io.github.interacto.command.Command;
import io.github.interacto.command.CommandImpl;
import io.github.interacto.fsm.CancelFSMException;
import io.github.interacto.fsm.FSM;
import io.github.interacto.interaction.InteractionData;
import io.github.interacto.jfx.interaction.JfxInteraction;
import java.util.Collections;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAutoUnbind {
	DoubleProperty val;
	JfxInteraction<InteractionData, FSM<Event>, Object> inter;
	FSM<Event> fsm;

	@BeforeEach
	public void setUp() {
		val = new SimpleDoubleProperty(2d);
		inter = Mockito.mock(JfxInteraction.class);
		fsm = Mockito.mock(FSM.class);
		Mockito.when(inter.isActivated()).thenReturn(true);
		Mockito.when(inter.getFsm()).thenReturn(fsm);
	}

	@Test
	public void testUnbindClassFields() throws CancelFSMException {
		final A aCmd = new A(val.multiply(10d), val.add(11d));
		final var binding = new JfXWidgetBinding<A, JfxInteraction<InteractionData, ?, ?>, InteractionData>(false, inter, i -> aCmd, Collections.emptyList(), false, null) {
			@Override
			public void first() {
			}
			@Override
			public boolean when() {
				return true;
			}
			@Override
			protected void executeCmdAsync(final Command cmd) {
			}
		};

		binding.setActivated(true);
		binding.fsmStarts();
		binding.fsmStops();
		assertFalse(aCmd.x.isBound());
		assertFalse(aCmd.y.isBound());
	}

	@Test
	public void testUnbindSuperClassFields() throws CancelFSMException {
		final B bCmd = new B(val.multiply(10d), val.add(11d), val.add(20d));

		final var binding = new JfXWidgetBinding<B, JfxInteraction<InteractionData, ?, ?>, InteractionData>(false, inter, i -> bCmd, Collections.emptyList(), false, null) {
			@Override
			public void first() {
			}
			@Override
			public boolean when() {
				return true;
			}
			@Override
			protected void executeCmdAsync(final Command cmd) {
			}
		};

		binding.setActivated(true);
		binding.fsmStarts();
		binding.fsmStops();
		assertFalse(bCmd.x.isBound());
		assertFalse(bCmd.y.isBound());
		assertFalse(bCmd.z.isBound());
	}
}

class B extends A {
	private double foo;
	@AutoUnbind final DoubleProperty z;

	B(final DoubleBinding x, final DoubleBinding y, final DoubleBinding z) {
		super(x, y);
		this.z = new SimpleDoubleProperty();
		this.z.bind(z);
	}
}

class A extends CommandImpl {
	private double bar;
	@AutoUnbind final SimpleDoubleProperty x;
	@AutoUnbind final DoubleProperty y;

	A(final DoubleBinding x, final DoubleBinding y) {
		this.x = new SimpleDoubleProperty();
		this.y = new SimpleDoubleProperty();
		this.x.bind(x);
		this.y.bind(y);
	}

	@Override
	protected void doCmdBody() {

	}

	@Override
	public boolean canDo() {
		return true;
	}
}
