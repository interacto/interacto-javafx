package org.malai.fsm;

public interface State<E> {
	String getName();
	FSM<E> getFSM();
}
