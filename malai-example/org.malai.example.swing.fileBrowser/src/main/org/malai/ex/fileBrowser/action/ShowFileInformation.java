package org.malai.ex.fileBrowser.action;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;

import javax.swing.JTextArea;

import org.malai.action.Action;
/*
 * This action displays pieces of information into a text area.
 * The displayed pieces of information are some characteristics of the current file. 
 */
public class ShowFileInformation extends Action {
	// An action can have parameters. These parameters are usually the data to set (or used to), or some
	// temporary data used for the undo/redo process.
	
	// The file to consider.
	protected Path path;
	
	// The widget that shows the information.
	protected JTextArea textArea;

	// The constructor of an action must NEVER takes an argument.
	// Instead, mutators must be used.
	public ShowFileInformation() {
		super();
	}

	// Defines whether the action will be registered into the pool of executed action to do some processes.
	// In this case there is no need for that.
	@Override
	public boolean isRegisterable() {
		return false;
	}
	
	// The operation that executes the action.
	@Override
	protected void doActionBody() {
		if(path==null)
			textArea.setText("");
		else {
			// Showing some properties of the selected file.
			final BasicFileAttributeView attrV = Files.getFileAttributeView(path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			String txt = path.toString();
			
			if(attrV!=null) {
				try {
					BasicFileAttributes attr = attrV.readAttributes();
					txt+= "\n"+attr.size()+" bytes";
				} catch (IOException e) { e.printStackTrace(); }
			}
			
			textArea.setText(txt);
		}
	}

	// A very important operation:
	// It defines whether the action can be executed.
	// Usually, the parameters are checked.
	// In this case, we just check that the text area exists.
	@Override
	public boolean canDo() {
		return textArea!=null;
	}
	

	/**
	 * @param path The selected path to focus on.
	 */
	public void setPath(final Path path) {
		this.path = path;
	}
	

	/**
	 * @param textArea The widget that displays the information of the selected file.
	 */
	public void setTextArea(final JTextArea textArea) {
		this.textArea = textArea;
	}
}
