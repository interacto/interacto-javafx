package org.malai.ex.fileBrowser.instrument;

import java.nio.file.Paths;

import javax.swing.JTextArea;
import javax.swing.tree.TreePath;

import org.malai.ex.fileBrowser.action.ShowFileInformation;
import org.malai.instrument.Link;
import org.malai.swing.instrument.WidgetInstrument;
import org.malai.swing.interaction.library.TreeNodeSelected;
import org.malai.swing.ui.UIComposer;
import org.malai.swing.widget.MTree;

// This instrument interacts with the tree view to show pieces of 
// information regarding the selected file.
// This instrument is a widget instrument. It means that it provides and uses widgets.
public class InfoDisplayer extends WidgetInstrument {
	// An instrument can have parameters such as widgets or presentations required
	// for the behaviour of the instrument.
	
	// The widget that displays the information of the selected file.
	protected JTextArea infoField;
	
	// The tree view that the instrument interacts with.
	protected MTree fileTree;
	
	
	// The constructor of a widget instrument requires the GUI composer.
	public InfoDisplayer(final UIComposer<?> composer, final MTree tree) {
		super(composer);
		fileTree = tree;
		initialiseWidgets();
	}

	// A widget instrument has a special operation dedicated to the initialisation
	// of its widgets.
	@Override
	protected void initialiseWidgets() {
		infoField = new JTextArea(5, 20);
		infoField.setEditable(false);
	}

	// Each instrument has a special operation dedicated to the creation of its links.
	// This operation should not be called explicitly since it is called the first time
	// the instrument is activated (lazy instantiation).
	@Override
	protected void initialiseLinks() {
		try {
			addLink(new TreeSelection2ShowFileInfo(this));
		} catch (InstantiationException e) { e.printStackTrace(); } 
		catch (IllegalAccessException e) { e.printStackTrace();	}
	}
	
	/**
	 * @return The widget that displays the information of the selected file.
	 */
	public JTextArea getInfoField() {
		return infoField;
	}
}


// An instrument is composed of links.
// Each link is a class that can be defined as a nested class of its instrument or somewhere else.
// Extending the class Link implies the specification of the type of the produced action, the type
// of the interaction used, and the type of the instrument.
class TreeSelection2ShowFileInfo extends Link<ShowFileInformation, TreeNodeSelected, InfoDisplayer> {
	// The definition of the constructor of a link is boring thanks to some Java syntax sugars.
	public TreeSelection2ShowFileInfo(final InfoDisplayer ins) throws InstantiationException, IllegalAccessException {
		// Notably, the type (class) of the action and the interaction must be provided explicitly...
		super(ins, false, ShowFileInformation.class, TreeNodeSelected.class);
	}

	// This operation is called each time the interaction starts to initialise the action that may be executed.
	// Using interactions having more that one transitions (many of them in fact) implies the overriding of the 
	// operation 'updateAction' called each time the interaction updates.
	@Override
	public void initAction() {
		// In our case, the parameters of the action are specified.
		if(interaction.isSelectionAdded()) {
			TreePath[] selectedPath = interaction.getChangedPaths();
			
			if(selectedPath.length>0) {
				StringBuilder buf = new StringBuilder();
				for(int i=0, size=selectedPath[0].getPathCount(); i<size; i++)
					buf.append(selectedPath[0].getPathComponent(i).toString()).append('/');
				action.setPath(Paths.get(buf.toString()));
			}
		}
		
		action.setTextArea(instrument.infoField);
	}

	// This operation defines if the action can be executed or not.
	// In our case, we just check that the tree of the interaction is the same 
	// than the tree of the application (quite useless here since only one tree is used).
	@Override
	public boolean isConditionRespected() {
		return interaction.getTree()==instrument.fileTree;
	}
}
