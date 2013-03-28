package org.malai.ex.fileBrowser.ui;

import javax.swing.JPanel;

import org.malai.ex.fileBrowser.instrument.InfoDisplayer;

// A simple panel that displays the widget that displays
// information of the selected file.
public class InfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public InfoPanel(final InfoDisplayer infoDisplayer) {
		super();
		add(infoDisplayer.getInfoField());
	}
}
