package org.malai.ex.draw.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MyDrawing {
	private final ObservableList<MyShape> shapes;

	public MyDrawing() {
		super();
		shapes = FXCollections.observableArrayList();
	}

	public ObservableList<MyShape> getShapes() {
		return shapes;
	}

	public void addShape(final MyShape sh) {
		shapes.add(sh);
	}
}
