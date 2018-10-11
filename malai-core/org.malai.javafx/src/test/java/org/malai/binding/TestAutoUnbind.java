package org.malai.binding;

import java.util.Collections;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.command.AutoUnbind;
import org.malai.command.Command;
import org.malai.command.CommandImpl;
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.FSM;
import org.malai.interaction.InteractionData;
import org.malai.javafx.binding.JfXWidgetBinding;
import org.malai.javafx.instrument.JfxInstrument;
import org.malai.javafx.interaction.JfxInteraction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAutoUnbind {
	DoubleProperty val;
	JfxInstrument ins;
	JfxInteraction<InteractionData, FSM<Event>, Object> inter;
	FSM<Event> fsm;

	@BeforeEach
	public void setUp() {
		val = new SimpleDoubleProperty(2d);
		ins = Mockito.mock(JfxInstrument.class);
		inter = Mockito.mock(JfxInteraction.class);
		fsm = Mockito.mock(FSM.class);
		Mockito.when(ins.isActivated()).thenReturn(true);
		Mockito.when(inter.isActivated()).thenReturn(true);
		Mockito.when(inter.getFsm()).thenReturn(fsm);
	}

	@Test
	public void testUnbindClassFields() throws CancelFSMException {
		final A aCmd = new A(val.multiply(10d), val.add(11d));
		final JfXWidgetBinding<A, JfxInteraction<InteractionData, ?, ?>, JfxInstrument, InteractionData> binding =
			new JfXWidgetBinding<A, JfxInteraction<InteractionData, ?, ?>, JfxInstrument, InteractionData>(ins, false, inter, i -> aCmd, Collections.emptyList(), false, null) {
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

		final JfXWidgetBinding<B, JfxInteraction<InteractionData, ?, ?>, JfxInstrument, InteractionData> binding =
			new JfXWidgetBinding<B, JfxInteraction<InteractionData, ?, ?>, JfxInstrument, InteractionData>(ins, false, inter, i -> bCmd, Collections.emptyList(), false, null) {
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
