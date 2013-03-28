package org.malai.ex.fileBrowser.ui;

import javax.swing.JSplitPane;

import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MProgressBar;

// The GUI composer of the application.
// Its uses all the widgets provided by the instruments and the presentations
// to compose the final user interface.
public class FileBrowserComposer extends UIComposer<FileBrowserFrame> {

	// The parameter here is the frame (already created) that will be filled
	// of widgets and presentations.
	public FileBrowserComposer(final FileBrowserFrame frame) {
		super();
		widget = frame;
	}

	// The operation that executes the composition.
	@Override
	public void compose(final MProgressBar progressBar) {
		InfoPanel infoPanel = new InfoPanel(widget.getInfoDisplayer());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		splitPane.add(widget.getFileBrowserTreeView().getScrollpane());
		splitPane.add(infoPanel);
		
		widget.getContentPane().add(splitPane);
		splitPane.setDividerLocation(350);
		
		// A very important step:
		// Each instrument must be registered to the widgets that they interact with.
		// In this case, the instrument infoDisplayer interacts with the tree view.
		widget.infoDisplayer.addEventable(widget.getFileBrowserTreeView());
	}
}
