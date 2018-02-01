package org.malai.fsm;

public interface InputState<E> extends State<E> {
	void enter() throws CancelFSMException;
}
