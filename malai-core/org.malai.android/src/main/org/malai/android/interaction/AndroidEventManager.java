package org.malai.android.interaction;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.malai.interaction.BasicEventManager;
import org.malai.interaction.EventHandler;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * An android event manager gathers Android events produces by widgets and transfers them handlers.<br>
 * <br>
 * This file is part of Malai.<br>
 * Copyright (c) 2009-2013 Arnaud BLOUIN<br>
 * <br>
 * Malai is free software; you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version.
 * <br>
 * Malai is distributed without any warranty; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.<br>
 * <br>
 * 2013-02-26<br>
 * @author Arnaud BLOUIN
 * @since 0.2
 */
public class AndroidEventManager extends BasicEventManager<View> implements OnClickListener {
	/** A subset of the set 'handlers' corresponding to the Android Handlers. */
	private List<AndroidEventHandler> androidHandlers;
	
	
	/**
	 * Creates a event manager that gathers Android events and transfers them to handlers.
	 * @since 0.2
	 */
	public AndroidEventManager() {
		super();
		androidHandlers = null;
	}
	
	
	/**
	 * Attaches the SwingEventManager to the Java component to listen.
	 * @param comp The Java Component to listen.
	 */
	public void attachTo(final View view) {
		if(view!=null) {
			view.setOnClickListener(this);
		}
	}
	
	
	@Override
	public void detachForm(final View view) {
		if(view!=null) {
			view.setOnClickListener(null);
		}
	}
	
	
	@Override
	public void addHandlers(final EventHandler h) {
		super.addHandlers(h);
		if(h instanceof AndroidEventHandler) {
			if(androidHandlers==null) androidHandlers = new CopyOnWriteArrayList<AndroidEventHandler>();
			androidHandlers.add((AndroidEventHandler)h);
		}
	}


	@Override
	public void removeHandler(final EventHandler h) {
		super.removeHandler(h);
		if(h!=null && androidHandlers!=null) 
			androidHandlers.remove(h);
	}
	
	
	@Override
	public void onClick(final View view) {
		if(androidHandlers!=null && view instanceof Button) {
			Button button = (Button) view;
			for(final AndroidEventHandler handler : androidHandlers)
				handler.onButtonPressed(button);
		}
	}
}
