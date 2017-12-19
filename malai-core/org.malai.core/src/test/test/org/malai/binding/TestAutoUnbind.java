package test.org.malai.binding;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import org.junit.Before;
import org.junit.Test;
import org.malai.action.ActionImpl;
import org.malai.action.AutoUnbind;
import org.malai.binding.WidgetBindingImpl;
import org.malai.instrument.Instrument;
import org.malai.interaction.Interaction;
import org.malai.stateMachine.MustAbortStateMachineException;
import org.mockito.Mockito;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

public class TestAutoUnbind {
	DoubleProperty val;
	Instrument<?> ins;
	Interaction inter;

	@Before
	public void setUp() {
		val = new SimpleDoubleProperty(2d);
		ins = Mockito.mock(Instrument.class);
		inter = Mockito.mock(Interaction.class);
		when(ins.isActivated()).thenReturn(true);
		when(inter.isActivated()).thenReturn(true);
	}

	@Test
	public void testUnbindClassFields() throws MustAbortStateMachineException, IllegalAccessException, InstantiationException {
		final A act = new A(val.multiply(10d), val.add(11d));
		WidgetBindingImpl<A, Interaction, Instrument<?>> binding = new WidgetBindingImpl<A, Interaction, Instrument<?>>(ins, false, A.class, inter) {
			@Override
			public void initAction() {
			}
			@Override
			protected A createAction() {
				return act;
			}
			@Override
			public boolean isConditionRespected() {
				return true;
			}
		};

		binding.setActivated(true);
		binding.interactionStarts(inter);
		binding.interactionStops(inter);
		assertFalse(act.x.isBound());
		assertFalse(act.y.isBound());
	}

	@Test
	public void testUnbindSuperClassFields() throws MustAbortStateMachineException, IllegalAccessException, InstantiationException {
		final B act = new B(val.multiply(10d), val.add(11d), val.add(20d));

		WidgetBindingImpl<B, Interaction, Instrument<?>> binding = new WidgetBindingImpl<B, Interaction, Instrument<?>>(ins, false, B.class, inter) {
			@Override
			public void initAction() {
			}
			@Override
			protected B createAction() {
				return act;
			}
			@Override
			public boolean isConditionRespected() {
				return true;
			}
		};

		binding.setActivated(true);
		binding.interactionStarts(inter);
		binding.interactionStops(inter);
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