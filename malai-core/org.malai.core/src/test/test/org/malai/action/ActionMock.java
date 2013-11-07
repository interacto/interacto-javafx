package test.org.malai.action;

import org.malai.action.Action;

public class ActionMock extends Action {
	public ActionMock() {
		super();
	}

	@Override
	public boolean isRegisterable() {
		return false;
	}

	@Override
	protected void doActionBody() {
		//
	}

	@Override
	public boolean canDo() {
		return false;
	}
}