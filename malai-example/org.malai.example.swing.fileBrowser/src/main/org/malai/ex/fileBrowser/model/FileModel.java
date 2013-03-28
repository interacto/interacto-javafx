package org.malai.ex.fileBrowser.model;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import org.malai.presentation.AbstractPresentation;

// The business of the application.
// In Malai such model is called an abstract presentation.
// So, this class implements the Malai interface AbstractPresentation.
public class FileModel implements AbstractPresentation {
	protected boolean modified;
	
	protected List<Path> currentFiles;
	
	protected Path currentFolder;
	
	
	public FileModel() {
		super();
		currentFiles = new ArrayList<>();
	}
	
	public void setCurrentFolder(final Path folder) throws IOException {
		if(folder==null || !Files.isDirectory(folder) || currentFolder==folder) return ;
		currentFolder = folder;
		Files.walkFileTree(currentFolder, new FileVisitor());
	}
	
	public Path getCurrentFolder() {
		return currentFolder;
	}
	
	public List<Path> getCurrentFiles() {
		return currentFiles;
	}

	@Override
	public void setModified(final boolean modified) {
		this.modified = modified;
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void reinit() {
		//
	}
	
	
	private class FileVisitor extends SimpleFileVisitor<Path> {
		private int deep;
		
		public FileVisitor() {
			super();
			deep = 0;
		}

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			if(deep>1) return FileVisitResult.SKIP_SUBTREE;
			if(dir!=null)
				currentFiles.add(dir);
			deep++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if(file!=null)
				currentFiles.add(file);
			return FileVisitResult.CONTINUE;
		}
	}
}
