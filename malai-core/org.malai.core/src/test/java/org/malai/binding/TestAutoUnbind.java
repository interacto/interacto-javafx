package org.malai.binding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.command.Command;
import org.malai.command.CommandImpl;
import org.malai.command.AutoUnbind;
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.FSM;
import org.malai.instrument.Instrument;
import org.malai.interaction.InteractionData;
import org.malai.interaction.InteractionImpl;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAutoUnbind {
	DoubleProperty val;
	Instrument<?> ins;
	InteractionImpl<InteractionData, Object, FSM<Object>> inter;
	FSM<Object> fsm;

	@BeforeEach
	public void setUp() {
		val = new SimpleDoubleProperty(2d);
		ins = Mockito.mock(Instrument.class);
		inter = Mockito.mock(InteractionImpl.class);
		fsm = Mockito.mock(FSM.class);
		Mockito.when(ins.isActivated()).thenReturn(true);
		Mockito.when(inter.isActivated()).thenReturn(true);
		Mockito.when(inter.getFsm()).thenReturn(fsm);
	}

	@Test
	public void testUnbindClassFields() throws CancelFSMException {
		final A aCmd = new A(val.multiply(10d), val.add(11d));
		WidgetBindingImpl<A, InteractionImpl<InteractionData,?,?>, Instrument<?>,InteractionData> binding =
			new WidgetBindingImpl<A, InteractionImpl<InteractionData,?,?>, Instrument<?>,InteractionData>(ins, false, i -> aCmd, inter) {
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

		final WidgetBindingImpl<B, InteractionImpl<InteractionData, ?,?>, Instrument<?>, InteractionData> binding =
			new WidgetBindingImpl<B, InteractionImpl<InteractionData,?,?>, Instrument<?>, InteractionData>(ins, false, i -> bCmd, inter) {
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