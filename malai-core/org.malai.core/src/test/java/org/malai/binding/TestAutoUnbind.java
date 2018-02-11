package org.malai.binding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.malai.action.Action;
import org.malai.action.ActionImpl;
import org.malai.action.AutoUnbind;
import org.malai.fsm.CancelFSMException;
import org.malai.fsm.FSM;
import org.malai.instrument.Instrument;
import org.malai.interaction2.Interaction;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class TestAutoUnbind {
	DoubleProperty val;
	Instrument<?> ins;
	Interaction<Object, FSM<Object>> inter;
	FSM<Object> fsm;

	@BeforeEach
	public void setUp() {
		val = new SimpleDoubleProperty(2d);
		ins = Mockito.mock(Instrument.class);
		inter = Mockito.mock(Interaction.class);
		fsm = Mockito.mock(FSM.class);
		Mockito.when(ins.isActivated()).thenReturn(true);
		Mockito.when(inter.isActivated()).thenReturn(true);
		Mockito.when(inter.getFsm()).thenReturn(fsm);
	}

	@Test
	public void testUnbindClassFields() throws CancelFSMException {
		final A act = new A(val.multiply(10d), val.add(11d));
		WidgetBindingImpl<A, Interaction<?,?>, Instrument<?>> binding = new WidgetBindingImpl<A, Interaction<?,?>, Instrument<?>>(ins, false, A.class, inter) {
			@Override
			public void first() {
			}
			@Override
			protected A map() {
				return act;
			}
			@Override
			public boolean when() {
				return true;
			}

			@Override
			protected void executeActionAsync(final Action act) {

			}
		};

		binding.setActivated(true);
		binding.fsmStarts();
		binding.fsmStops();
		assertFalse(act.x.isBound());
		assertFalse(act.y.isBound());
	}

	@Test
	public void testUnbindSuperClassFields() throws CancelFSMException {
		final B act = new B(val.multiply(10d), val.add(11d), val.add(20d));

		WidgetBindingImpl<B, Interaction<?,?>, Instrument<?>> binding = new WidgetBindingImpl<B, Interaction<?,?>, Instrument<?>>(ins, false, B.class, inter) {
			@Override
			public void first() {
			}
			@Override
			protected B map() {
				return act;
			}
			@Override
			public boolean when() {
				return true;
			}

			@Override
			protected void executeActionAsync(final Action act) {

			}
		};

		binding.setActivated(true);
		binding.fsmStarts();
		binding.fsmStops();
		assertFalse(act.x.isBound());
		assertFalse(act.y.isBound());
		assertFalse(act.z.isBound());
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

class A extends ActionImpl {
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
	protected void doActionBody() {

	}

	@Override
	public boolean canDo() {
		return true;
	}
}