package org.malai.ex.fileBrowser.ui;

import java.io.IOException;
import java.nio.file.Paths;

import org.malai.ex.fileBrowser.instrument.InfoDisplayer;
import org.malai.ex.fileBrowser.model.FileModel;
import org.malai.instrument.Instrument;
import org.malai.presentation.Presentation;
import org.malai.swing.instrument.library.Scroller;
import org.malai.swing.ui.UI;

/*
 * The main GUI of the application.
 * Each GUI of the application should inherit
 * of the Malai class UI that represents a GUI.
 * A Malai UI is a JFrame. 
 */
public class FileBrowserFrame extends UI {
	private static final long serialVersionUID = 1L;

	// The different instruments of the UI.
	// The scroller to scroll into the tree view.
	protected Scroller scroller;
	// The instrument that shows information of the selected file.
	protected InfoDisplayer infoDisplayer;
	

	public FileBrowserFrame() throws IOException {
		super();
		/* Setting the name of the application. */
		setName("A simple file browser");
		composer = new FileBrowserComposer(this);
		getFileModel().setCurrentFolder(Paths.get(System.getProperty("user.home")));
		final FileBrowserTreeView view = getFileBrowserTreeView();
		
		// Creation of the instruments.
		infoDisplayer = new InfoDisplayer(composer, view);
		infoDisplayer.setActivated(true);
		view.update();
		scroller = new Scroller(view);
		scroller.addEventable(view);
		scroller.setActivated(true);
	}


	/**
	 * @return The instrument that shows information of the selected file.
	 */
	protected InfoDisplayer getInfoDisplayer() {
		return infoDisplayer;
	}

	@Override
	public Instrument[] getInstruments() {
		return new Instrument[]{scroller, infoDisplayer};
	}
	
	
	@Override
	public void initialisePresentations() {
		/*
		 * A UI is composed of presentations.
		 * Each presentation is a couple abstract presentation
		 * and concrete presentation.
		 * In this example, the abstract presentation is the drawing
		 * and its concrete presentation the Swing canvas representing it.
		 */
		final FileModel model = new FileModel();  // Creating the model.
		final FileBrowserTreeView treeView = new FileBrowserTreeView(model); // Creating the view.
		/*
		 * Creating a presentation composed of these two elements.
		 * Adding this presentation to the set of presentations of the UI.
		 */
		presentations.add(new Presentation<>(model, treeView));
	}
	

	public FileModel getFileModel() {
		/*
		 * It is possible to obtain an abstract or concrete presentation using this process:
		 */
		return getPresentation(FileModel.class, FileBrowserTreeView.class).getAbstractPresentation();
		/*
		 * Of course, storing this value into an attribute would improve the performances when
		 * many access to it would be done. 
		 */
	}
	
	
	public FileBrowserTreeView getFileBrowserTreeView() {
		/*
		 * It is possible to obtain an abstract or concrete presentation using this process:
		 */
		return getPresentation(FileModel.class, FileBrowserTreeView.class).getConcretePresentation();
		/*
		 * Of course, storing this value into an attribute would improve the performances when
		 * many access to it would be done. 
		 */
	}
}
