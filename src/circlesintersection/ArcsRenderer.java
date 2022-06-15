package circlesintersection;

import circlesintersection.listeners.ArcsRendererListener;
import circlesintersection.listeners.UiUpdateListener;
import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.util.ArrayList;

import static circlesintersection.GeometryUtils.*;
import static circlesintersection.GeometryUtils.computeAndSetDekartCoordinates;

/**
 * Performs operations on arcs, such as creation, update, rotation, scaling and notifies UI about it.
 */
public class ArcsRenderer implements ArcsRendererListener {

    private Arcs arcs;
    private final Settings settings;
    private final UiUpdateListener uiUpdateListener;

    /**
     * Public constructor
     *
     * @param arcs              an arcs to be drawn on a canvas.
     * @param settings          different values to tune the program.
     * @param uiUpdateListener  listener to update UI, when some operation is performed.
     */
    public ArcsRenderer(Arcs arcs, Settings settings, UiUpdateListener uiUpdateListener) {
        this.arcs = arcs;
        this.settings = settings;
        this.uiUpdateListener = uiUpdateListener;
    }

    @Override
    public void createNewArcsAndRepaint() {
        settings.setTimeBegin(System.nanoTime());
        arcs = new Arcs(settings);
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void updateArcsAndRepaint(@Nullable Point point) {
        settings.setTimeBegin(System.nanoTime());
        arcs.setAnglePairsListArray(new ArrayList<>(settings.getCirclesQuantity()));
        arcs.setAnglePairsListFinal(new ArrayList<>());
        arcs.setAnglePairsList(new ArrayList<>());
//        SwingUtilities.convertPointToScreen(point, this);
        if (point != null) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setX(point.getX());
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setY(point.getY());
        }
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
        }
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void updateArcsAndRepaint(Point point, int wheelRotationValue) {
        settings.setTimeBegin(System.nanoTime());
        arcs.setAnglePairsListArray(new ArrayList<>(settings.getCirclesQuantity()));
        arcs.setAnglePairsListFinal(new ArrayList<>());
        arcs.setAnglePairsList(new ArrayList<>());
//        SwingUtilities.convertPointToScreen(point, this);
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setX(point.getX());
        arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setY(point.getY());
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
        }
        if (wheelRotationValue < 0
                && arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() > 20) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setDiameter(
                    arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() - 10);
        }
        if (wheelRotationValue > 0) {
            arcs.getArcsArray()[settings.getCirclesQuantity() - 1].setDiameter(
                    arcs.getArcsArray()[settings.getCirclesQuantity() - 1].getDiameter() + 10);
        }
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void rotateArcsAndRepaint(int wheelRotation) {
        settings.setTimeBegin(System.nanoTime());
        double newAngle;
        double extraAngle;
        double distance;
        arcs.prepareArcsWhenCirclesRotated();
        if (wheelRotation > 0) {
            extraAngle = 0.04;
        } else {
            extraAngle = -0.04;
        }
        for (int i = 0; i < arcs.getArcsArray().length - 1; i++) {
            newAngle = computeAngle(arcs.getArcsArray()[arcs.getArcsArray().length - 1], arcs.getArcsArray()[i], AngleKind.RAD) + extraAngle;
            distance = computeDistance(arcs.getArcsArray()[i], arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    arcs.getArcsArray()[i],
                    arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
        }
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void scaleArcsAndRepaint(int wheelRotation) {
        settings.setTimeBegin(System.nanoTime());
        double newAngle;
        double distance;
        arcs.prepareArcsWhenCirclesRotated();
        for (int i = 0; i < arcs.getArcsArray().length - 1; i++) {
            arcs.getArcsArray()[i].setExcluded(false);
            newAngle = computeAngle(arcs.getArcsArray()[arcs.getArcsArray().length - 1], arcs.getArcsArray()[i], AngleKind.RAD);
            distance = computeDistance(arcs.getArcsArray()[i], arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
            if (wheelRotation > 0) {
                distance = distance + distance / 15;
            } else {
                distance = distance - distance / 15;
            }
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    arcs.getArcsArray()[i],
                    arcs.getArcsArray()[arcs.getArcsArray().length - 1]);
        }
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void dragArcsAndRepaint(Point point) {
        settings.setTimeBegin(System.nanoTime());
        arcs.prepareArcsWhenCirclesDragged();
        arcs.setMouseDraggedDeltaX(arcs.getArcsArray()[arcs.getArcsArray().length - 1].getX() - point.getX());
        arcs.setMouseDraggedDeltaY(arcs.getArcsArray()[arcs.getArcsArray().length - 1].getY() - point.getY());
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void dropArcsAndRepaint() {
        settings.setTimeBegin(System.nanoTime());
        for (int i = 0; i < arcs.getArcsArray().length; i++) {
            arcs.getArcsArray()[i].setX(arcs.getArcsArray()[i].getX() - arcs.getMouseDraggedDeltaX());
            arcs.getArcsArray()[i].setY(arcs.getArcsArray()[i].getY() - arcs.getMouseDraggedDeltaY());
        }
        arcs.setMouseDraggedDeltaX(0);
        arcs.setMouseDraggedDeltaY(0);
        arcs.computeIntersections();
        uiUpdateListener.updateArcsAndRepaint(arcs);
    }

    @Override
    public void toggleFullScreen() {
        uiUpdateListener.toggleFullScreen();
    }
}
