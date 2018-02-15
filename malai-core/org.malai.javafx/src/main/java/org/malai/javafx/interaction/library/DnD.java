package org.malai.javafx.interaction.library;

import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DnD extends PointInteraction<DnDFSM, Node> {
	private final DnDFSM.DnDFSMHandler handler;
	/** The ending local point of the dnd. */
	protected final ObjectProperty<Point3D> endLocalPt;
	/** The ending scene point of the dnd. */
	protected final ObjectProperty<Point3D> endScenePt;
	/** The object picked at the beginning of the dnd. */
	protected final ObjectProperty<Node> endObject;

	public DnD(final boolean updateSrcOnUpdate, final boolean cancellable) {
		super(new DnDFSM(cancellable));

		endLocalPt = new SimpleObjectProperty<>();
		endScenePt = new SimpleObjectProperty<>();
		endObject = new SimpleObjectProperty<>();

		handler = new DnDFSM.DnDFSMHandler() {
			@Override
			public void onPress(final MouseEvent event) {
				setPointData(event);
				setDropData(event);
			}

			@Override
			public void onDrag(final MouseEvent event) {
				if(updateSrcOnUpdate) {
					srcLocalPoint.set(endLocalPt.get());
					srcScenePoint.set(endScenePt.get());
					srcObject.set(endObject.get());
				}

				setDropData(event);
			}

			@Override
			public void onRelease(final MouseEvent event) {
				setDropData(event);
			}

			@Override
			public void reinitData() {
				DnD.this.reinitData();
			}
		};

		fsm.buildFSM(handler);
	}

	public DnD() {
		this(false, false);
	}

	protected void setDropData(final MouseEvent event) {
		endLocalPt.set(new Point3D(event.getX(), event.getY(), event.getZ()));
		endScenePt.set(new Point3D(event.getSceneX(), event.getSceneY(), event.getZ()));
		endObject.set(event.getPickResult().getIntersectedNode());
		setModifiersData(event);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		endLocalPt.setValue(null);
		endScenePt.setValue(null);
		endObject.set(null);
	}

	/**
	 * @return The ending local point of the dnd.
	 */
	public Point3D getEndLocalPt() {
		return endLocalPt.get();
	}

	/**
	 * @return The ending scene point of the dnd.
	 */
	public Point3D getEndScenePt() {
		return endScenePt.get();
	}

	/**
	 * @return The object picked at the end of the dnd.
	 */
	public Optional<Node> getEndObjet() {
		return Optional.ofNullable(endObject.get());
	}

	public ReadOnlyObjectProperty<Point3D> endLocalPtProperty() {
		return endLocalPt;
	}

	public ReadOnlyObjectProperty<Point3D> endScenePtProperty() {
		return endScenePt;
	}

	public Optional<Node> getEndObject() {
		return Optional.ofNullable(endObject.get());
	}

	public ReadOnlyObjectProperty<Node> endObjectProperty() {
		return endObject;
	}
}
