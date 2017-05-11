package test.org.malai.action;

import org.malai.action.ActionImpl;

public class ActionImplMock extends ActionImpl {
	public ActionImplMock() {
		super();
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