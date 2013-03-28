package org.malai.ex.fileBrowser.ui;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import org.malai.ex.fileBrowser.model.FileModel;
import org.malai.presentation.ConcretePresentation;
import org.malai.swing.widget.MTree;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

// The view of the files.
/*
 * It extends MTree which is a JTree adapted for Malai.
 * Each Swing widget that have to be used with Malai (i.e.
 * to be used with instruments and interactions) must extends
 * the Malai widget class corresponding to the expected 
 * Swing widget. Here, we want FileBrowserTreeView to be a JTree.
 * So, its corresponding Malai class, MTree is used.
 * 
 * Moreover, this view is a concrete presentation, i.e. the
 * representation of the model of the application (FileModel).
 */
public class FileBrowserTreeView extends MTree implements ConcretePresentation {
	private static final long serialVersionUID = 1L;
	// The root node.
	protected DefaultMutableTreeNode rootNode;
	
	// The abstract presentation.
	protected FileModel model;
	
	
	public FileBrowserTreeView(final FileModel fileModel) {
		super(new DefaultTreeModel(new DefaultMutableTreeNode(), false), true, true);
		rootNode = (DefaultMutableTreeNode) getModel().getRoot();
		model = fileModel;
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	@Override
	public void save(boolean generalPreferences, String nsURI, Document document, Element root) {
		//
	}

	@Override
	public void load(boolean generalPreferences, String nsURI, Element meta) {
		//
	}

	@Override
	public void setModified(boolean modified) {
		//
	}

	@Override
	public boolean isModified() {
		return false;
	}

	@Override
	public void reinit() {
		//
	}

	// Updates the concrete presentation when the abstract one is modified.
	@Override
	public void update() {
		List<Path> paths = model.getCurrentFiles();
		
		rootNode.removeAllChildren();
		
		if(paths!=null && !paths.isEmpty()) {
			rootNode.setUserObject(paths.get(0));
			for(int i=1, size=paths.size(); i<size; i++)
				rootNode.add(new DefaultMutableTreeNode(paths.get(i).getFileName(), Files.isDirectory(paths.get(i))));
			expandRow(0);
		}
		updateUI();
	}
}
