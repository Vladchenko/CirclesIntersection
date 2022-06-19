package circlesintersection.models;

import circlesintersection.Settings;
import circlesintersection.utils.Logger;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.*;

import static circlesintersection.utils.GeometryUtils.*;
import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Represents physical model. Instantiates and computes intersections of {@link Arc}s.
 * TODO Several methods to be reduced into smaller ones.
 */
public class Arcs {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private final Arc[] mArcsArray;
    private final Settings mSettings;
    private ArrayList<Arc2D> mArcs2D;
    private double mMouseDraggedDeltaX;
    private double mMouseDraggedDeltaY;
    private ArrayList<AnglePair> mAnglePairsList;
    private ArrayList<ArrayList<AnglePair>> mAnglePairsListArray;
    //</editor-fold>

    /**
     * Constructor that instantiates this class.
     *
     * @param settings different values to tune the program.
     */
    public Arcs(Settings settings) {
        mSettings = settings;
        mArcsArray = new Arc[settings.getCirclesQuantity()];
        prepareArcsAccompanyingLists();
        randomizeArcs(mArcsArray);
        // Setting mouse cursor location to last arc in array
        setArcToMousePosition(mArcsArray[mArcsArray.length - 1]);
    }

    //region getters & setters
    public void setAnglePairsListArray(ArrayList<ArrayList<AnglePair>> anglePairsListArray) {
        mAnglePairsListArray = anglePairsListArray;
    }

    public Arc[] getArcsArray() {
        return mArcsArray;
    }

    public void setAnglePairsList(ArrayList<AnglePair> anglePairsList) {
        mAnglePairsList = anglePairsList;
    }

    public ArrayList<Arc2D> getArcs2D() {
        return mArcs2D;
    }

    public void setAnglePairsListFinal(ArrayList<Arc2D> anglePairsListFinal) {
        mArcs2D = anglePairsListFinal;
    }

    public double getMouseDraggedDeltaX() {
        return mMouseDraggedDeltaX;
    }

    public void setMouseDraggedDeltaX(double mouseDraggedDeltaX) {
        mMouseDraggedDeltaX = mouseDraggedDeltaX;
    }

    public double getMouseDraggedDeltaY() {
        return mMouseDraggedDeltaY;
    }

    public void setMouseDraggedDeltaY(double mouseDraggedDeltaY) {
        mMouseDraggedDeltaY = mouseDraggedDeltaY;
    }
    //endregion getters & setters

    private void randomizeArcs(Arc[] arcsArray) {
        for (Arc arc : arcsArray) {
            arc.setX(Math.random() * (mSettings.getCanvasWidth() - mSettings.getDiameterSpan() * 2) + mSettings.getDiameterSpan());
            arc.setY(Math.random() * (mSettings.getCanvasHeight() - mSettings.getDiameterSpan() * 2) + mSettings.getDiameterSpan());
            arc.setDiameter(Math.random() * mSettings.getDiameterSpan() + mSettings.getIncrement());
        }
    }

    private void setArcToMousePosition(Arc arc) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        arc.setX((int) b.getX());
        arc.setY((int) b.getY());
    }

    private void prepareArcsAccompanyingLists() {
        mAnglePairsList = new ArrayList<>();
        mArcs2D = new ArrayList<>();
        mAnglePairsListArray = new ArrayList<>(mSettings.getCirclesQuantity());
        if (mArcsArray[0] == null) {
            for (int i = 0; i < mArcsArray.length; i++) {
                mArcsArray[i] = new Arc();
                mArcsArray[i].setNumber(i);
            }
        }
    }

    /**
     * Create a new anglePair and add it to anglePairsList.
     */
    private void addAnglePair(
            ArrayList<AnglePair> anglePairsList,
            AnglePair anglePair) {
        AnglePair anglePairNew = new AnglePair();
        /*
         * Negative angle cannot be operated with, thus one needs to get rid of
         * it, using the following way.
         */
        if (anglePair.getAngleBegin() < 0
                || anglePair.getAngleBegin() > anglePair.getAngleEnd()) {
            anglePairNew.setAngleBegin(0);
            anglePairNew.setAngleEnd(anglePair.getAngleEnd());
            anglePairNew.setNumber(anglePair.getNumber());
            anglePairsList.add(anglePairNew);
            anglePairNew = new AnglePair();
            if (anglePair.getAngleBegin() < 0) {
                anglePairNew.setAngleBegin(360 - Math.abs(anglePair.getAngleBegin()));
            } else {
                anglePairNew.setAngleBegin(Math.abs(anglePair.getAngleBegin()));
            }
            anglePairNew.setAngleEnd(360);
        } else {
            anglePairNew.setAngleBegin(anglePair.getAngleBegin());
            anglePairNew.setAngleEnd(anglePair.getAngleEnd());
        }
        anglePairNew.setNumber(anglePair.getNumber());
        anglePairsList.add(anglePairNew);
    }

    /**
     * Create a new instance of an anglePairsList and adds it to anglePairsListArray object
     */
    private void addAnglePairsList(
            ArrayList<ArrayList<AnglePair>> anglePairsListArray,
            ArrayList<AnglePair> anglePairsList) {
        ArrayList<AnglePair> anglePairsListNew = new ArrayList<>(anglePairsList);
        try {
            Collections.copy(anglePairsListNew, anglePairsList);
        } catch (IndexOutOfBoundsException ex) {
            System.out.println("anglePairsListNew size is: " + anglePairsListNew.size());
            System.out.println("anglePairsList size is: " + anglePairsList.size());
        }
        anglePairsListArray.add(anglePairsListNew);
    }

    /**
     * Check if circles intersect with each other and creates a lists of arcs that reside in areas different to the
     * intersections.
     */
    private void checkIntersection() {

        double distance;
        boolean intersected;

        excludeInnerCircles(mSettings, mArcsArray);

        /*
         * Adding a new pair of angles that arc is to be drawn by, i.e.
         * [20,50] - that means an arc is to be drawn from 20 to 50 degrees.
         */
        AnglePair anglePair = new AnglePair();

        // Passing through all the circles
        for (int i = 0; i < mSettings.getCirclesQuantity(); i++) {
            intersected = false;
            // If circle(arc) is not excluded
            if (!mArcsArray[i].isExcluded()) {
                for (int j = 0; j < mSettings.getCirclesQuantity(); j++) {
                    if (i != j && !mArcsArray[j].isExcluded()) {
                        distance = computeDistance(mArcsArray[i], mArcsArray[j]);
                        /*
                         * If a sum of radii of two regarded circles less
                         * than distance among them, i.e. if a circles intersect
                         */
                        if ((mArcsArray[i].getDiameter() / 2 + mArcsArray[j].getDiameter() / 2 > distance)) {

                            convertAnglesRadToGrad(anglePair,
                                    mArcsArray[i],
                                    mArcsArray[j],
                                    distance);

                            anglePair.setNumber(mArcsArray[i].getNumber());

                            if (!Double.isNaN(anglePair.getAngleBegin())) {
                                addAnglePair(mAnglePairsList, anglePair);
                            }
                            intersected = true;
                        }

                    }
                }

            }

            /*
             * If a circle doesn't intersect with any other, then make it to be a circle, not an arc, i.e. make its
             * angles to be [0;360] (full circle).
             */
            if (!intersected
                    && Settings.DRAW_NOT_INTERSECTED_SOLID) {
                addAnglePair(mAnglePairsList, getNotIntersectedCircle(anglePair, mArcsArray[i]));
            }
            if (!mAnglePairsList.isEmpty()) {
                addAnglePairsList(mAnglePairsListArray, mAnglePairsList);
            }
            mAnglePairsList.clear();
        }

        if (DEBUG_ENABLED) {
            System.out.println("List of an intersecting circles:");
            Logger.printAnglePairs(mAnglePairsListArray);
            System.out.println();
        }
        try {
            AnglePair.AnglePairComparator comparator = new AnglePair.AnglePairComparator();
            for (ArrayList<AnglePair> anglePairsListArray1 : mAnglePairsListArray) {
                if (anglePairsListArray1.size() > 1) {
                    anglePairsListArray1.sort(comparator);
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println(ex.getMessage());
        }
        if (DEBUG_ENABLED) {
            System.out.println("List of a sorted intersecting circles:");
            Logger.printAnglePairsFinal(mArcs2D);
            System.out.println();
        }

    }

    /**
     * Exclude circles that are placed inside any other circles from a list of regarded circles
     *
     * @param settings  {@link Settings}
     * @param arcsArray array of {@link Arc}
     */
    private void excludeInnerCircles(Settings settings, Arc[] arcsArray) {
        /*
         * This O(n^2) loop checks if there is any circle that thoroughly immersed
         * inside any of the other circles and marks it as an excluded from
         * being treated as valid for checking an intersection with it
         */
        for (int i = 0; i < settings.getCirclesQuantity(); i++) {
            for (int j = 0; j < settings.getCirclesQuantity(); j++) {
                excludeCircleIfInsideAnotherOne(i, j, arcsArray);
            }
        }
    }

    private void excludeCircleIfInsideAnotherOne(int circleIndex1, int circleIndex2, Arc[] arcs) {
        if (circleIndex1 != circleIndex2) {
            /*
             * If a half of a diameter of circle [circleIndex1] is larger than
             * half of a diameter of circle [circleIndex2] + computeDistance among these
             * circles, then it means that a circle [circleIndex2] is immersed
             * into (or placed inside) of a circle [circleIndex1]. If that is so,
             * then circle [circleIndex2] is marked "Excluded". It also means that
             * this circle will not be regarded as the one to be checked
             * an intersections with.
             */
            if (arcs[circleIndex1].getDiameter() / 2 > computeDistance(arcs[circleIndex1], arcs[circleIndex2])
                    + arcs[circleIndex2].getDiameter() / 2) {
                arcs[circleIndex2].setExcluded(true);
            }
        }
    }

    private AnglePair getNotIntersectedCircle(AnglePair anglePair, Arc arc) {
        anglePair.setNumber(arc.getNumber());
        anglePair.setAngleBegin(0);
        anglePair.setAngleEnd(0);
        if (arc.isExcluded()) {
            anglePair.setAngleEnd(360);
        }
        return anglePair;
    }

    /**
     * Convert a beginning and ending angles of an intersected circles from RAD to GRAD and put them to {@link AnglePair}.
     *
     * @param anglePair model to keep computed angles
     * @param arc       circle(arc) #1
     * @param arc2      circle(arc) #2 to compute
     * @param distance  between circles(arcs)
     */
    private void convertAnglesRadToGrad(AnglePair anglePair, Arc arc, Arc arc2, double distance) {
        double angle = computeAngle(arc, arc2, AngleKind.RAD);
        double l1 = (Math.pow(arc.getDiameter() / 2, 2) - Math.pow(arc2.getDiameter() / 2, 2) + distance * distance) / (2 * distance);
        double alpha = Math.acos(l1 / (arc.getDiameter() / 2));
        double beta = angle - alpha;
        double gamma = angle + alpha;
        if (angle + alpha >= Math.PI * 2) {
            gamma -= Math.PI * 2;
        }
        anglePair.setAngleBegin(convertRadToGrad(beta));
        anglePair.setAngleEnd(convertRadToGrad(gamma));
    }

    /**
     * Remove the redundant arcs (the ones that overlap with others). I.e.
     * when there are arcs like [30,68] and [49, 105], method will have made a one arc - [30, 105].
     */
    private void removeRedundantArcs() {
        for (int i = 0; i < mAnglePairsListArray.size(); i++) {
            if (mAnglePairsListArray.get(i).size() > 1) {
                for (int j = 0; j < mAnglePairsListArray.get(i).size() - 1; j++) {
                    if (mAnglePairsListArray.get(i).get(j).getAngleEnd() >= mAnglePairsListArray.get(i).get(j + 1).getAngleBegin()) {
                        if (mAnglePairsListArray.get(i).get(j).getAngleEnd() >= mAnglePairsListArray.get(i).get(j + 1).getAngleEnd()) {
                            mAnglePairsListArray.get(i).remove(j + 1);
                            j--;
                        } else if (mAnglePairsListArray.get(i).get(j).getAngleEnd() < mAnglePairsListArray.get(i).get(j + 1).getAngleEnd()) {
                            mAnglePairsListArray.get(i).get(j).setAngleEnd(mAnglePairsListArray.get(i).get(j + 1).getAngleEnd());
                            mAnglePairsListArray.get(i).remove(j + 1);
                            j--;
                        }
                    }
                }
            }
        }

        if (DEBUG_ENABLED) {
            System.out.println("Redundant circles have been removed:");
            Logger.printAnglePairs(mAnglePairsListArray);
            System.out.println();
        }
    }

    /**
     * Invert the arcs, for there could be shown not the ones that intersect,
     * but the ones that free of intersection.
     */
    private void invertArcs() {

        AnglePair anglePair;

        for (ArrayList<AnglePair> anglePairs : mAnglePairsListArray) {
            for (int j = 0; j < anglePairs.size(); j++) {
                anglePair = new AnglePair();

                if (anglePairs.get(j).getAngleBegin() == 0
                        && anglePairs.get(j).getAngleEnd() == 360) {
                    continue;
                }

                if (anglePairs.get(j).getAngleBegin() != 0
                        && anglePairs.get(j).getAngleEnd() == 360) {
                    if (anglePairs.size() > 1
                            && anglePairs.size() == j + 1) {
                        continue;
                    }
                    anglePair.setAngleBegin(0);
                    anglePair.setAngleEnd(anglePairs.get(j).getAngleBegin());
                    anglePair.setNumber(anglePairs.get(j).getNumber());
                    mArcs2D.add(
                            createArc2D(anglePair)
                    );
                    continue;
                }

                if (anglePairs.get(j).getAngleBegin() == 0
                        && anglePairs.get(j).getAngleEnd() != 360) {
                    if (anglePairs.size() > j
                            && anglePairs.size() > 1) {
                        anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                        anglePair.setAngleEnd(anglePairs.get(j + 1).getAngleBegin());
                        anglePair.setNumber(anglePairs.get(j).getNumber());
                        mArcs2D.add(
                                createArc2D(anglePair)
                        );
                        continue;
                    }
                    if (anglePairs.size() == 1
                            || anglePairs.size() == j) {
                        anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                        anglePair.setAngleEnd(360);
                        anglePair.setNumber(anglePairs.get(j).getNumber());
                        mArcs2D.add(
                                createArc2D(anglePair)
                        );
                        continue;
                    }
                }

                if (anglePairs.get(j).getAngleBegin() != 0
                        && anglePairs.get(j).getAngleEnd() != 360) {

                    if (j == 0) {
                        if (anglePairs.size() == 1) {
                            anglePair.setAngleBegin(0);
                            anglePair.setAngleEnd(anglePairs.get(j).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                            anglePair = new AnglePair();
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(360);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                            continue;
                        }
                        if (anglePairs.size() > 1) {
                            anglePair.setAngleBegin(0);
                            anglePair.setAngleEnd(anglePairs.get(j).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                            anglePair = new AnglePair();
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(anglePairs.get(j + 1).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                        }
                    } else {
                        if (anglePairs.size() == 2) {
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(360);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                            continue;
                        }
                        if (anglePairs.size() > 2
                                && anglePairs.size() != j + 1) {
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(anglePairs.get(j + 1).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                            continue;
                        }
                        if (anglePairs.size() == j + 1) {
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(360);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            mArcs2D.add(
                                    createArc2D(anglePair)
                            );
                        }
                    }
                }
            }
        }

        if (DEBUG_ENABLED) {
            System.out.println("Arcs to be drawn are:");
            Logger.printAnglePairsFinal(mArcs2D);
            System.out.println();
        }
    }

    private Arc2D createArc2D(AnglePair anglePair) {
        return new Arc2D.Double(
                mArcsArray[anglePair.getNumber()].getX()
                        - mMouseDraggedDeltaX
                        - mArcsArray[anglePair.getNumber()].getDiameter() / 2,
                mArcsArray[anglePair.getNumber()].getY()
                        - mMouseDraggedDeltaY
                        - mArcsArray[anglePair.getNumber()].getDiameter() / 2,
                mArcsArray[anglePair.getNumber()].getDiameter(),
                mArcsArray[anglePair.getNumber()].getDiameter(),
                anglePair.getAngleBegin(),
                (anglePair.getAngleEnd()
                        - anglePair.getAngleBegin()),
                Arc2D.OPEN);
    }

    /**
     * When circles rotated, arcs have to be recomputed.
     */
    public void prepareArcsWhenCirclesRotated() {
        prepareArcsAccompanyingLists();
        setArcToMousePosition(mArcsArray[mArcsArray.length - 1]);
    }

    /**
     * When circles dragged, arcs have to be recomputed.
     */
    public void prepareArcsWhenCirclesDragged() {
        prepareArcsAccompanyingLists();
    }

    /**
     * Perform required computations to draw an arcs.
     */
    public void computeIntersections() {
        checkIntersection();
        removeRedundantArcs();
        invertArcs();
    }
}
