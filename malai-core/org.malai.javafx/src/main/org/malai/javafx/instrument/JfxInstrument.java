package org.malai.javafx.instrument;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SortEvent;
import javafx.scene.control.TextField;
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

import org.malai.instrument.InstrumentImpl;
import org.malai.javafx.interaction.JfxEventHandler;
import org.malai.javafx.interaction.JfxInteraction;

/**
 * Base of an instrument for JavaFX applications.<br>
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
public abstract class JfxInstrument extends InstrumentImpl<JfxInteractor<?, ? extends JfxInteraction, ? extends JfxInstrument>> implements JfxEventHandler {

	public JfxInstrument() {
		super();
	}

	
    @FXML @Override
	public void onAction(final ActionEvent e) {
		if(e==null || !isActivated()) return;

		final Object src = e.getSource();

//		if(src instanceof JMenuItem) {
//			final JMenuItem mi = (JMenuItem) e.getSource();
//
//    		for(final SwingEventHandler handler : swingHandlers)
//				handler.onMenuItemPressed(mi);
//		}
//		else if(src instanceof JCheckBox) {
//			final JCheckBox cb = (JCheckBox) e.getSource();
//
//    		for(final SwingEventHandler handler : swingHandlers)
//				handler.onCheckBoxModified(cb);
//		}
//		else 
		if(src instanceof ButtonBase) {
			final ButtonBase widg = (ButtonBase)e.getSource();
			interactors.forEach(inter -> inter.getInteraction().onJfxButtonPressed(widg));
		}
		else if(src instanceof ComboBoxBase<?>) {
			final ComboBoxBase<?> widg = (ComboBoxBase<?>)e.getSource();
			interactors.forEach(inter -> inter.getInteraction().onJfxComboBoxSelected(widg));
		}
		else if(src instanceof TextField) {
			final TextField widg = (TextField)e.getSource();
			interactors.forEach(inter -> inter.getInteraction().onTextChanged(widg));
		}
    }


	@FXML @Override
	public void onContextMenuRequested(ContextMenuEvent event) {
		//
		
	}


	@FXML @Override
	public void onDragDetected(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onDragDone(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onDragDropped(DragEvent event) {
		//
		
	}


	@FXML @Override
	public void onDragEntered(DragEvent event) {
		//
		
	}


	@FXML @Override
	public void onDragExited(DragEvent event) {
		//
		
	}


	@FXML @Override
	public void onDragOver(DragEvent event) {
		//
		
	}


	@FXML @Override
	public void onInputMethodTextChanged(InputMethodEvent event) {
		//
		
	}


	@FXML @Override
	public void onKeyPressed(KeyEvent event) {
		//
		
	}


	@FXML @Override
	public void onKeyReleased(KeyEvent event) {
		//
		
	}


	@FXML @Override
	public void onKeyTyped(KeyEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseClicked(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseDragEntered(MouseDragEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseDragExited(MouseDragEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseDragged(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseDragOver(MouseDragEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseDragReleased(MouseDragEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseEntered(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseExited(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseMoved(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onMousePressed(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onMouseReleased(MouseEvent event) {
		//
		
	}


	@FXML @Override
	public void onRotate(RotateEvent event) {
		//
		
	}


	@FXML @Override
	public void onRotationFinished(RotateEvent event) {
		//
		
	}


	@FXML @Override
	public void onRotationStarted(RotateEvent event) {
		//
		
	}


	@FXML @Override
	public void onScroll(ScrollEvent event) {
		//
		
	}


	@FXML @Override
	public void onScrollFinished(ScrollEvent event) {
		//
		
	}


	@FXML @Override
	public void onScrollStarted(ScrollEvent event) {
		//
		
	}


	@FXML @Override
	public void onSwipeDown(SwipeEvent event) {
		//
		
	}


	@FXML @Override
	public void onSwipeLeft(SwipeEvent event) {
		//
		
	}


	@FXML @Override
	public void onSwipeRight(SwipeEvent event) {
		//
		
	}


	@FXML @Override
	public void onSwipeUp(SwipeEvent event) {
		//
		
	}


	@FXML @Override
	public void onTouchPressed(TouchEvent event) {
		//
		
	}


	@FXML @Override
	public void onTouchReleased(TouchEvent event) {
		//
		
	}


	@FXML @Override
	public void onTouchMoved(TouchEvent event) {
		//
		
	}


	@FXML @Override
	public void onTouchStationary(TouchEvent event) {
		//
		
	}


	@FXML @Override
	public void onZoom(ZoomEvent event) {
		//
		
	}


	@FXML @Override
	public void onZoomFinished(ZoomEvent event) {
		//
		
	}


	@FXML @Override
	public void onZoomStarted(ZoomEvent event) {
		//
		
	}


	@FXML @Override
	public void onClosed(ActionEvent event) {
		//
		
	}


	@FXML @Override
	public void onCloseRequest(ActionEvent event) {
		//
		
	}


	@FXML @Override
	public void onSelectionChanged(ActionEvent event) {
		//
		
	}


	@FXML @Override
	public void onHidden(Event event) {
		//
		
	}


	@FXML @Override
	public void onHiding(Event event) {
		//
		
	}


	@FXML @Override
	public void onShowing(Event event) {
		//
		
	}


	@FXML @Override
	public void onShown(Event event) {
		//
		
	}


	@FXML @Override
	public void onScrollToColumn(ScrollToEvent<?> event) {
		//
		
	}


	@FXML @Override
	public void onScrollTo(ScrollToEvent<Integer> event) {
		//
		
	}


	@FXML @Override
	public void onSort(SortEvent<TreeTableView<?>> event) {
		//
		
	}


	@FXML @Override
	public void onEditCancel(EditEvent<?> event) {
		//
		
	}


	@FXML @Override
	public void onEditCommit(EditEvent<?> event) {
		//
		
	}


	@FXML @Override
	public void onEditStart(EditEvent<?> event) {
		//
		
	}


	@FXML @Override
	protected void initialiseInteractors() {
		//
		
	}
}
