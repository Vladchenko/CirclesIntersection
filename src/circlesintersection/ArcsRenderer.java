package circlesintersection;

import circlesintersection.listeners.ArcsRendererListener;
import circlesintersection.listeners.UiUpdateListener;
import circlesintersection.models.AngleKind;
import circlesintersection.models.Arcs;
import com.sun.istack.internal.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static circlesintersection.utils.GeometryUtils.*;
import static circlesintersection.utils.GeometryUtils.computeAndSetDekartCoordinates;

/**
 * Perform operations on arcs, such as creation, update, rotation, scaling and notify UI about it.
 */
public class ArcsRenderer implements ArcsRendererListener {

    private Arcs mArcs;
    private final Settings mSettings;
    private final UiUpdateListener mUiUpdateListener;

    /**
     * Public constructor
     *
     * @param arcs              an arcs to be drawn on a canvas.
     * @param settings          different values to tune the program.
     * @param uiUpdateListener  listener to update UI, when some operation is performed.
     */
    public ArcsRenderer(Arcs arcs, Settings settings, UiUpdateListener uiUpdateListener) {
        mArcs = arcs;
        mSettings = settings;
        mUiUpdateListener = uiUpdateListener;
    }

    @Override
    public void createNewArcsAndRepaint() {
        mSettings.setTimeBegin(System.nanoTime());
        mArcs = new Arcs(mSettings);
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void updateArcsAndRepaint(@Nullable Point point) {
        mSettings.setTimeBegin(System.nanoTime());
        mArcs.setAnglePairsListArray(new ArrayList<>(mSettings.getCirclesQuantity()));
        mArcs.setAnglePairsListFinal(new ArrayList<>());
        mArcs.setAnglePairsList(new ArrayList<>());
        if (point != null) {
            mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].setX(point.getX());
            mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].setY(point.getY());
        }
        for (int i = 0; i < mArcs.getArcsArray().length; i++) {
            mArcs.getArcsArray()[i].setExcluded(false);
        }
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void updateArcsAndRepaint(Point point, int wheelRotationValue) {
        mSettings.setTimeBegin(System.nanoTime());
        mArcs.setAnglePairsListArray(new ArrayList<>(mSettings.getCirclesQuantity()));
        mArcs.setAnglePairsListFinal(new ArrayList<>());
        mArcs.setAnglePairsList(new ArrayList<>());
        mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].setX(point.getX());
        mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].setY(point.getY());
        for (int i = 0; i < mArcs.getArcsArray().length; i++) {
            mArcs.getArcsArray()[i].setExcluded(false);
        }
        if (wheelRotationValue < 0
                && mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].getDiameter() > 20) {
            mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].setDiameter(
                    mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].getDiameter() - 10);
        }
        if (wheelRotationValue > 0) {
            mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].setDiameter(
                    mArcs.getArcsArray()[mSettings.getCirclesQuantity() - 1].getDiameter() + 10);
        }
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void rotateArcsAndRepaint(int wheelRotation) {
        mSettings.setTimeBegin(System.nanoTime());
        double newAngle;
        double extraAngle;
        double distance;
        mArcs.prepareArcsWhenCirclesRotated();
        if (wheelRotation > 0) {
            extraAngle = 0.04;
        } else {
            extraAngle = -0.04;
        }
        for (int i = 0; i < mArcs.getArcsArray().length - 1; i++) {
            newAngle = computeAngle(mArcs.getArcsArray()[mArcs.getArcsArray().length - 1], mArcs.getArcsArray()[i], AngleKind.RAD) + extraAngle;
            distance = computeDistance(mArcs.getArcsArray()[i], mArcs.getArcsArray()[mArcs.getArcsArray().length - 1]);
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    mArcs.getArcsArray()[i],
                    mArcs.getArcsArray()[mArcs.getArcsArray().length - 1]);
        }
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void scaleArcsAndRepaint(int wheelRotation) {
        mSettings.setTimeBegin(System.nanoTime());
        double newAngle;
        double distance;
        mArcs.prepareArcsWhenCirclesRotated();
        for (int i = 0; i < mArcs.getArcsArray().length - 1; i++) {
            mArcs.getArcsArray()[i].setExcluded(false);
            newAngle = computeAngle(mArcs.getArcsArray()[mArcs.getArcsArray().length - 1], mArcs.getArcsArray()[i], AngleKind.RAD);
            distance = computeDistance(mArcs.getArcsArray()[i], mArcs.getArcsArray()[mArcs.getArcsArray().length - 1]);
            if (wheelRotation > 0) {
                distance = distance + distance / 15;
            } else {
                distance = distance - distance / 15;
            }
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    mArcs.getArcsArray()[i],
                    mArcs.getArcsArray()[mArcs.getArcsArray().length - 1]);
        }
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void dragArcsAndRepaint(Point point) {
        mSettings.setTimeBegin(System.nanoTime());
        mArcs.prepareArcsWhenCirclesDragged();
        mArcs.setMouseDraggedDeltaX(mArcs.getArcsArray()[mArcs.getArcsArray().length - 1].getX() - point.getX());
        mArcs.setMouseDraggedDeltaY(mArcs.getArcsArray()[mArcs.getArcsArray().length - 1].getY() - point.getY());
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void dropArcsAndRepaint() {
        mSettings.setTimeBegin(System.nanoTime());
        for (int i = 0; i < mArcs.getArcsArray().length; i++) {
            mArcs.getArcsArray()[i].setX(mArcs.getArcsArray()[i].getX() - mArcs.getMouseDraggedDeltaX());
            mArcs.getArcsArray()[i].setY(mArcs.getArcsArray()[i].getY() - mArcs.getMouseDraggedDeltaY());
        }
        mArcs.setMouseDraggedDeltaX(0);
        mArcs.setMouseDraggedDeltaY(0);
        mArcs.computeIntersections();
        mUiUpdateListener.updateArcsAndRepaint(mArcs);
    }

    @Override
    public void toggleFullScreen() {
        mUiUpdateListener.toggleFullScreen();
    }
}
