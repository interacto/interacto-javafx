package org.malai.swing.widget;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreeModel;

import org.malai.interaction.EventManager;
import org.malai.interaction.Eventable;
import org.malai.picking.Pickable;
import org.malai.picking.Picker;
import org.malai.swing.interaction.SwingEventManager;

public class MTree extends JTree implements Picker, Eventable, ScrollableWidget {
	private static final long	serialVersionUID	= 1L;

	/** The possible scrollpane that contains the panel. */
	protected MScrollPane scrollpane;

	/** The event manager that listens events produced by the panel. May be null. */
	protected SwingEventManager eventManager;
	

	/**
	 * Creates and initialises a tree.
	 * @param withScrollPane True: a scrollpane will be created and will contain the tree.
	 * @param withEvtManager True: the tree will have an event manager.
	 * @since 0.1
	 */
	public MTree(final TreeModel model, final boolean withScrollPane, final boolean withEvtManager) {
		super(model);

		if(withScrollPane) {
			scrollpane = new MScrollPane();
			scrollpane.getViewport().add(this);
		}

		if(withEvtManager) {
			eventManager = new SwingEventManager();
			eventManager.attachTo(this);
		}
	}
	
	
	@Override
	public boolean isHorizontalScrollbarVisible() {
		return getScrollbar(true).isVisible();
	}


	@Override
	public boolean isVerticalScrollbarVisible() {
		return getScrollbar(true).isVisible();
	}
	
	
	/**
	 * @param vertical True: the vertical scrollbar is returned. Otherwise, the horizontal scroll bar.
	 * @return The required scroll bar or null if the panel has no scrollpane.
	 * @since 0.1
	 */
	protected JScrollBar getScrollbar(final boolean vertical) {
		if(hasScrollPane())
			return vertical ? getScrollpane().getVerticalScrollBar() : getScrollpane().getHorizontalScrollBar();

		return null;
	}


	@Override
	public void scrollHorizontally(final int increment) {
		scroll(increment, false);
	}


	@Override
	public void scrollVertically(final int increment) {
		scroll(increment, true);
	}


	/**
	 * Scroll the vertical or horizontal scroll bar, if possible, using the given increment.
	 * @param increment The increment to apply on the vertical scroll bar.
	 * @param vertical True: the vertical scroll bar is
	 * @since 0.2
	 */
	protected void scroll(final int increment, final boolean vertical) {
		JScrollBar scrollbar = getScrollbar(vertical);

		if(scrollbar!=null && scrollbar.isVisible())
			scrollbar.setValue(scrollbar.getValue() - increment);
	}


	@Override
	public boolean hasScrollPane() {
		return scrollpane!=null;
	}

	@Override
	public JScrollPane getScrollpane() {
		return scrollpane;
	}

	@Override
	public Pickable getPickableAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickableAt(this, getComponents(), x, y);
	}

	@Override
	public Picker getPickerAt(final double x, final double y) {
		return WidgetUtilities.INSTANCE.getPickerAt(this, getComponents(), x, y);
	}

	@Override
	public boolean contains(final Object obj) {
		return WidgetUtilities.INSTANCE.contains(getComponents(), obj);
	}


	@Override
	public boolean hasEventManager() {
		return eventManager!=null;
	}


	@Override
	public EventManager<?> getEventManager() {
		return eventManager;
	}
}
