package io.interacto.jfx.interaction.library;

import java.util.Optional;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class DnD extends PointInteraction<SrcTgtPointsData, DnDFSM, Node> implements SrcTgtPointsData {
	private final DnDFSM.DnDFSMHandler handler;
	/** The ending local point of the dnd. */
	protected final ObjectProperty<Point3D> tgtLocalPt;
	/** The ending scene point of the dnd. */
	protected final ObjectProperty<Point3D> tgtScenePt;
	/** The object picked at the beginning of the dnd. */
	protected final ObjectProperty<Node> tgtObject;

	public DnD(final boolean updateSrcOnUpdate, final boolean cancellable) {
		super(new DnDFSM(cancellable));

		tgtLocalPt = new SimpleObjectProperty<>();
		tgtScenePt = new SimpleObjectProperty<>();
		tgtObject = new SimpleObjectProperty<>();

		handler = new DnDFSM.DnDFSMHandler() {
			@Override
			public void onPress(final MouseEvent event) {
				setPointData(event);
				setTgtData(event);
			}

			@Override
			public void onDrag(final MouseEvent event) {
				if(updateSrcOnUpdate) {
					srcLocalPoint.set(tgtLocalPt.get());
					srcScenePoint.set(tgtScenePt.get());
					srcObject.set(tgtObject.get());
				}

				setTgtData(event);
			}

			@Override
			public void onRelease(final MouseEvent event) {
				setTgtData(event);
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

	protected void setTgtData(final MouseEvent event) {
		tgtLocalPt.set(new Point3D(event.getX(), event.getY(), event.getZ()));
		tgtScenePt.set(new Point3D(event.getSceneX(), event.getSceneY(), event.getZ()));
		tgtObject.set(event.getPickResult().getIntersectedNode());
		pointData.setModifiersData(event);
	}

	@Override
	public void reinitData() {
		super.reinitData();
		tgtLocalPt.setValue(null);
		tgtScenePt.setValue(null);
		tgtObject.set(null);
	}

	@Override
	public SrcTgtPointsData getData() {
		return this;
	}

	@Override
	public Optional<Node> getTgtObject() {
		return Optional.ofNullable(tgtObject.get());
	}

	@Override
	public ReadOnlyObjectProperty<Point3D> tgtLocalPointProperty() {
		return tgtLocalPt;
	}

	@Override
	public ReadOnlyObjectProperty<Point3D> tgtScenePointProperty() {
		return tgtScenePt;
	}

	@Override
	public ReadOnlyObjectProperty<Node> tgtObjectProperty() {
		return tgtObject;
	}

	@Override
	public Point3D getTgtLocalPoint() {
		return tgtLocalPt.get();
	}

	@Override
	public Point3D getTgtScenePoint() {
		return tgtScenePt.get();
	}
}
