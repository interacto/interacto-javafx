package org.malai.javafx.interaction2.library;

import java.util.function.LongSupplier;
import javafx.event.Event;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.malai.fsm.CancellingState;
import org.malai.fsm.StdState;
import org.malai.fsm.SubFSMTransition;
import org.malai.fsm.TerminalState;
import org.malai.fsm.TimeoutTransition;
import org.malai.javafx.interaction2.FSMHandler;
import org.malai.javafx.interaction2.JfxFSM;
import org.malai.javafx.interaction2.MoveTransition;

public class DoubleClickFSM extends JfxFSM<FSMHandler> {
	/** The time gap between the two spinner events. */
	private static long timeGap = 300;
	/** The supplier that provides the time gap. */
	private static final LongSupplier SUPPLY_TIME_GAP = () -> getTimeGap();

	/**
	 * @return The time gap between the two spinner events.
	 */
	public static long getTimeGap() {
		return timeGap;
	}

	/**
	 * Sets The time gap between the two spinner events.
	 * @param timeGapBetweenClicks The time gap between the two spinner events. Not done if negative.
	 */
	public static void setTimeGap(final long timeGapBetweenClicks) {
		if(timeGapBetweenClicks > 0L) {
			timeGap = timeGapBetweenClicks;
		}
	}

	protected final ClickFSM firstClickFSM;
	protected MouseButton checkButton;

	public DoubleClickFSM() {
		super();
		firstClickFSM = new ClickFSM();
	}

	@Override
	protected void buildFSM(final FSMHandler handler) {
		super.buildFSM(handler);
		firstClickFSM.buildFSM(null);
		final ClickFSM sndClick = new ClickFSM();
		sndClick.buildFSM(null);
		final TerminalState<Event> dbleclicked = new TerminalState<>(this, "dbleclicked");
		final CancellingState<Event> cancelled = new CancellingState<>(this, "cancelled");
		final StdState<Event> clicked = new StdState<>(this, "clicked");

		addState(clicked);
		addState(dbleclicked);
		addState(cancelled);

		new SubFSMTransition<Event>(initState, clicked, firstClickFSM) {
			@Override
			protected void action(final Event event) {
				checkButton = firstClickFSM.checkButton;
				sndClick.checkButton = checkButton;
			}
		};
		new MoveTransition(clicked, cancelled) {
			@Override
			protected boolean isGuardOK(final Event event) {
				return super.isGuardOK(event) && (checkButton == null || event instanceof MouseEvent && ((MouseEvent) event).getButton() == checkButton);
			}
		};
		new TimeoutTransition<>(clicked, cancelled, SUPPLY_TIME_GAP);
		new SubFSMTransition<>(clicked, dbleclicked, sndClick);
	}

	@Override
	public void reinit() {
		super.reinit();
		checkButton = null;
	}
}
