package org.malai.javafx.interaction;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TreeTableView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.RotateEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.input.ZoomEvent;

/**
 * Defines all the events JavaFX can trigger.<br>
 * <br>
 * This file is part of libMalai.<br>
 * Copyright (c) 2005-2014 Arnaud BLOUIN<br>
 * <br>
 * libMalan is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.<br>
 * <br>
 * libMalan is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * @author Arnaud BLOUIN
 * @date 2014-09-19
 * @version 2.0
 */
public interface JfxEventHandler{
	/**
	 * Invoked whenever a button is fired.
	 * @param event The produced event.
	 */
    void onAction(final ActionEvent event);
    
    /**
     * When a context menu has been requested.
     * @param event
     */
    void onContextMenuRequested(final ContextMenuEvent event);
    
    /**
     * When drag gesture has been detected.
     * @param event
     */
    void onDragDetected(final MouseEvent event) ;
    
    /**
     * When a DnD ends: data has been dropped.
     * @param event
     */
    void onDragDone(final MouseEvent event);
    
    /**
     * When the mouse button is released during a DnD.
     * @param event
     */
    void onDragDropped(final DragEvent event);
    
    /**
     * When DnD interaction enters the widget.
     * @param event
     */
    void onDragEntered(final DragEvent event);
    
    /**
     * When drag gesture exits the widget.
     * @param event
     */
    void onDragExited(final DragEvent event);
    
    /**
     * When a DnD progresses with the widget.
     * @param event
     */
    void onDragOver(final DragEvent event);
    
    /**
     * When the widget has input focus and the input method text has changed.
     * @param event
     */
    void onInputMethodTextChanged(final InputMethodEvent event);
    
    /**
     * When a key has been pressed.
     * @param event
     */
    void onKeyPressed(final KeyEvent event);
    
    /**
     * When a key has been released.
     * @param event
     */
    void onKeyReleased(final KeyEvent event);
    
    /**
     * When a key has been typed.
     * @param event
     */
    void onKeyTyped(final KeyEvent event);
    
    /**
     * When a mouse button has been clicked.
     * @param event
     */
    void onMouseClicked(final MouseEvent event);
    
    /**
     * When a DnD enters the widget.
     * @param event
     */
    void onMouseDragEntered(final MouseDragEvent event);
    
    /**
     * When a DnD exists the widget.
     * @param event
     */
    void onMouseDragExited(final MouseDragEvent event);
    
    /**
     * When a mouse button is pressed and then dragged.
     * @param event
     */
    void onMouseDragged(final MouseEvent event);
    
    /**
     * When a DnD progresses within the widget.
     * @param event
     */
    void onMouseDragOver(final MouseDragEvent event);
    
    /**
     * When a DnD ends (by releasing mouse button) within the widget.
     * @param event
     */
    void onMouseDragReleased(final MouseDragEvent event);
    
    /**
     * When the mouse enters the widget.
     * @param event
     */
    void onMouseEntered(final MouseEvent event);
    
    /**
     * When the mouse exits the widget.
     * @param event
     */
    void onMouseExited(final MouseEvent event);
    
    /**
     * When mouse cursor moves within the widget but no buttons have been pushed.
     * @param event
     */
    void onMouseMoved(final MouseEvent event);
    
    /**
     * When a mouse button has been pressed.
     * @param event
     */
    void onMousePressed(final MouseEvent event);
    
    /**
     * When a mouse button has been released.
     * @param event
     */
    void onMouseReleased(final MouseEvent event);
    
    /**
     * When user performs a rotation action.
     * @param event
     */
    void onRotate(final RotateEvent event);
    
    /**
     * When a rotation gesture ends.
     * @param event
     */
    void onRotationFinished(final RotateEvent event);
    
    /**
     * When a rotation gesture is detected.
     * @param event
     */
    void onRotationStarted(final RotateEvent event);
    
    /**
     * When user performs a scrolling action.
     * @param event
     */
    void onScroll(final ScrollEvent event);
    
    /**
     * When a scrolling gesture ends.
     * @param event
     */
    void onScrollFinished(final ScrollEvent event);
    
    /**
     * When a scrolling gesture is detected.
     * @param event
     */
    void onScrollStarted(final ScrollEvent event);
    
    /**
     * When a downward swipe gesture centered over this node happens.
     * @param event
     */
    void onSwipeDown(final SwipeEvent event);
    
    /**
     * When a leftward swipe gesture centered over this node happens.
     * @param event
     */
    void onSwipeLeft(final SwipeEvent event);
    
    /**
     * When an rightward swipe gesture centered over this node happens.
     * @param event
     */
    void onSwipeRight(final SwipeEvent event);
    
    /**
     * When an upward swipe gesture centered over this node happens.
     * @param event
     */
    void onSwipeUp(final SwipeEvent event);
    
    /**
     * When a new touch point is pressed.
     * @param event
     */
    void onTouchPressed(final TouchEvent event);

    /**
     * When a touch point is released.
     * @param event
     */
    void onTouchReleased(final TouchEvent event);
    
    /**
     * When a new touch point is moved.
     * @param event
     */
    void onTouchMoved(final TouchEvent event);
    
    /**
     * When a touch point stays pressed and still.
     * @param event
     */
    void onTouchStationary(final TouchEvent event);
    
    /**
     * When user performs a zooming action.
     * @param event
     */
    void onZoom(final ZoomEvent event);
    
    /**
     * When a zooming gesture ends.
     * @param event
     */
    void onZoomFinished(final ZoomEvent event);
    
    /**
     * When a zooming gesture is detected.
     * @param event
     */
    void onZoomStarted(final ZoomEvent event);
    
    /**
     * Events produced by a tab when closed.
     * @param event
     */
    void onClosed(final ActionEvent event);

    /**
     * Events produced by a tab when wanted to be closed.
     * @param event
     */
    void onCloseRequest(final ActionEvent event);

    /**
     * Events produced by tabs when the current selected tab changed.
     * @param event
     */
    void onSelectionChanged(final ActionEvent event);
    
    /**
     * Called just after the widget popup/display has been hidden.
     * @param event
     */
    void onHidden(final Event event);
    
    /**
     * Called just prior to the widget popup/display being hidden.
     * @param event
     */
    void onHiding(final Event event);
    
    /**
     * Called just prior to the widget popup/display being shown.
     * @param event
     */
    void onShowing(final Event event);
    
    /**
     * Called just after the widget popup/display is shown.
     * @param event
     */
    void onShown(final Event event);
    
    /**
     * When there is a request to scroll a column into view using scrollToColumn(TreeTableColumn) or scrollToColumnIndex(int)
     * @param event
     */
    void onScrollToColumn(final ScrollToEvent<?> event);
    
    /**
     * When there is a request to scroll an index into view using scrollTo(int)
     * @param event
     */
    void onScrollTo(final ScrollToEvent<Integer> event);
    
    /**
     * When there is a request to sort the control.
     * @param event
     */
    void onSort(final SortEvent<TreeTableView<?>> event);
    
    /**
     * When the user cancels editing a cell.
     * @param event
     */
    void onEditCancel(final ListView.EditEvent<?> event);
    
    /**
     * When the user performs an action that should result in their editing input being persisted.
     * @param event
     */
    void onEditCommit(final ListView.EditEvent<?> event);
    
    /**
     * When the user successfully initiates editing.
     * @param event
     */
    void onEditStart(final ListView.EditEvent<?> event);
}
