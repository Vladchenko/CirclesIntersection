package circlesintersection;

import circlesintersection.models.AnglePair;
import circlesintersection.models.Arc;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.util.*;

import static circlesintersection.GeometryUtils.*;
import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Represents physical model. Instantiates and computes intersections of {@link Arc}s.
 */
public class Arcs {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private final Arc[] arcsArray;
    private final Settings settings;
    private double[] intermediateAnglesArray;
    private ArrayList<AnglePair> anglePairsList;
    private ArrayList<AnglePair> anglePairsListFinal;
    private ArrayList<ArrayList<AnglePair>> anglePairsListArray;
    //</editor-fold>

    /**
     * Constructor that instantiates this class.
     *
     * @param settings different values to tune the program.
     */
    public Arcs(Settings settings) {
        this.settings = settings;
        arcsArray = new Arc[settings.getCirclesQuantity()];
        prepareArcsAccompanyingLists();

        for (Arc arc : arcsArray) {
            arc.setX(Math.random() * (settings.getCanvasWidth() - settings.getDiameterSpan() * 2) + settings.getDiameterSpan());
            arc.setY(Math.random() * (settings.getCanvasHeight() - settings.getDiameterSpan() * 2) + settings.getDiameterSpan());
            arc.setDiameter(Math.random() * settings.getDiameterSpan() + settings.getIncrement());
        }
        // Setting mouse cursor location to last arc in array
        setArcToMousePosition(arcsArray[arcsArray.length - 1]);
    }

    //region getters & setters
    public void setAnglePairsListArray(ArrayList<ArrayList<AnglePair>> anglePairsListArray) {
        this.anglePairsListArray = anglePairsListArray;
    }

    public Arc[] getArcsArray() {
        return arcsArray;
    }

    public void setAnglePairsList(ArrayList<AnglePair> anglePairsList) {
        this.anglePairsList = anglePairsList;
    }

    public ArrayList<AnglePair> getAnglePairsListFinal() {
        return anglePairsListFinal;
    }

    public void setAnglePairsListFinal(ArrayList<AnglePair> anglePairsListFinal) {
        this.anglePairsListFinal = anglePairsListFinal;
    }

    private void setArcAngles(Arc arc, double beginAngle, double validAngle) {
        arc.setAngleBegin(beginAngle);
        arc.setAngleSpan(validAngle);
    }
    //endregion getters & setters

    private void setArcToMousePosition(Arc arc) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        arc.setX((int) b.getX());
        arc.setY((int) b.getY());
    }

    private void prepareArcsAccompanyingLists() {
        intermediateAnglesArray = new double[settings.getCirclesQuantity()];
        anglePairsList = new ArrayList<>();
        anglePairsListFinal = new ArrayList<>();
        anglePairsListArray = new ArrayList<>(settings.getCirclesQuantity());
        if (arcsArray[0] == null) {
            for (int i = 0; i < arcsArray.length; i++) {
                arcsArray[i] = new Arc();
                arcsArray[i].setNumber(i);
            }
        }
    }

    /**
     * Create a new anglePair and add it to anglePairsList.
     */
    private void addNewPair(
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
     * This method creates a new instance of an anglePairsList and adds it to
     * anglePairsListArray object
     */
    private void addNewInstanceOfPairsList(
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
     * Debug method. Prints anglePairs to console.
     */
    private void printAnglePairs() {
        for (ArrayList<AnglePair> anglePairs : anglePairsListArray) {
//            System.out.println("Arc #" + i++ + " inters-s with arcs #"+ itArray.next());
            System.out.println(anglePairs);
        }

    }

    /**
     * Debug method. Prints resulting array of anglePairs, i.e. AnglePairsFinal to console.
     */
    private void printAnglePairsFinal() {
        for (int i = 0; i < anglePairsListFinal.size(); i++) {
            System.out.print(anglePairsListFinal.get(i) + "\t");
            if (i % 5 == 0 && i != 0) {
                System.out.println();
            }
        }

    }

    /**
     * Check if circles intersect with each other and creates a lists of arcs that reside in areas different to the
     * intersections.
     */
    private void checkIntersection() {

        double l;
        double l1;
        double radius1;
        double radius2;
        double alpha;
        double beta;
        double gamma;
        boolean intersected;

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

        /*
         * Adding a new pair of angles that arc is to be drawn by, i.e.
         * [20,50] - that means an arc is to be drawn from 20 to 50 degrees.
         */
        AnglePair anglePair = new AnglePair();

        // Passing through all the circles */
        for (int i = 0; i < settings.getCirclesQuantity(); i++) {
            intersected = false;
            // If circle(arc) is not excluded */
            if (!arcsArray[i].isExcluded()) {
                for (int j = 0; j < settings.getCirclesQuantity(); j++) {
                    if (i != j && !arcsArray[j].isExcluded()) {
                        l = computeDistance(arcsArray[i], arcsArray[j]);
                        /*
                         * If a sum of radiuses of two regarded circles less
                         * than distance among them, i.e. if a circles intersect
                         */
                        if ((arcsArray[i].getDiameter() / 2 + arcsArray[j].getDiameter() / 2 > l)) {
                            radius1 = arcsArray[i].getDiameter() / 2;
                            radius2 = arcsArray[j].getDiameter() / 2;
                            // Computing angle between circles oArcs[i] and oArcs[j]
                            intermediateAnglesArray[i] = computeAngle(arcsArray[i], arcsArray[j], AngleKind.RAD);
                            /*
                             * Computing a beginning and an ending angles of an
                             * arc to be later drawn instead of a circle.
                             */
                            l1 = (Math.pow(radius1, 2) - Math.pow(radius2, 2) + l * l) / (2 * l);
                            alpha = Math.acos(l1 / (arcsArray[i].getDiameter() / 2));
                            beta = intermediateAnglesArray[i] - alpha;
                            gamma = intermediateAnglesArray[i] + alpha;
                            if (intermediateAnglesArray[i] + alpha >= Math.PI * 2) {
                                gamma -= Math.PI * 2;
                            }
                            anglePair.setAngleBegin(convertRadToGrad(beta));
                            anglePair.setAngleEnd(convertRadToGrad(gamma));

                            // To see the number of an intersecting circle */
                            //anglePair.number = oArcs[j].getNumber();
                            // To see the number of own circle */
                            anglePair.setNumber(arcsArray[i].getNumber());

                            if (!Double.isNaN(anglePair.getAngleBegin())) {
                                addNewPair(anglePairsList, anglePair);
                            }
                            intersected = true;
                        }

                    }
                }

            }

            /*
             * Checking if a circle intersects with any other circle and if it
             * doesn't, then it draws or doesn't draw it as an intersected area.
             */
            if (!intersected
                    && Settings.isINCLUDE_NOT_INTERSECTED()) {
                // To see the number of an intersecting circle
                // anglePair.number = oArcs[j].getNumber();
                // To see the number of own circle
                anglePair.setNumber(arcsArray[i].getNumber());
                anglePair.setAngleBegin(0);
                anglePair.setAngleEnd(0);
                if (arcsArray[i].isExcluded()) {
                    anglePair.setAngleEnd(360);
                }
                addNewPair(anglePairsList, anglePair);
            }
            if (!anglePairsList.isEmpty()) {
                addNewInstanceOfPairsList(anglePairsListArray, anglePairsList);
            }
            anglePairsList.clear();
        }

        if (DEBUG_ENABLED) {
            System.out.println("List of an intersecting circles:");
            printAnglePairs();
            System.out.println();
        }
        try {
            AnglePair.AnglePairComparator comparator = new AnglePair.AnglePairComparator();
            for (ArrayList<AnglePair> anglePairsListArray1 : anglePairsListArray) {
                if (anglePairsListArray1.size() > 1) {
                    anglePairsListArray1.sort(comparator);
                }
            }
        } catch (ConcurrentModificationException ex) {
            System.out.println(ex.getMessage());
        }
        if (DEBUG_ENABLED) {
            System.out.println("List of a sorted intersecting circles:");
            printAnglePairs();
            System.out.println();
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

    /**
     * Removing the redundant arcs (the ones that overlap with others). I.e.
     * when there are arcs like [30,68] and [49, 105], method will have made a one arc - [30, 105].
     */
    private void removeRedundancy() {
        for (int i = 0; i < anglePairsListArray.size(); i++) {
            if (anglePairsListArray.get(i).size() > 1) {
                for (int j = 0; j < anglePairsListArray.get(i).size() - 1; j++) {
                    if (anglePairsListArray.get(i).get(j).getAngleEnd() >= anglePairsListArray.get(i).get(j + 1).getAngleBegin()) {
                        if (anglePairsListArray.get(i).get(j).getAngleEnd() >= anglePairsListArray.get(i).get(j + 1).getAngleEnd()) {
                            anglePairsListArray.get(i).remove(j + 1);
                            j--;
                        } else if (anglePairsListArray.get(i).get(j).getAngleEnd() < anglePairsListArray.get(i).get(j + 1).getAngleEnd()) {
                            anglePairsListArray.get(i).get(j).setAngleEnd(anglePairsListArray.get(i).get(j + 1).getAngleEnd());
                            anglePairsListArray.get(i).remove(j + 1);
                            j--;
                        }
                    }
                }
            }
        }

        if (DEBUG_ENABLED) {
            System.out.println("Redundant circles have been removed:");
            printAnglePairs();
            System.out.println();
        }
    }

    /**
     * Inverting the arcs, for there could be shown not the ones that intersect,
     * but the ones that free of intersection.
     */
    private void prepareArcs() {

        AnglePair anglePair;

        for (ArrayList<AnglePair> anglePairs : anglePairsListArray) {
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
                    anglePairsListFinal.add(anglePair);
                    continue;
                }

                if (anglePairs.get(j).getAngleBegin() == 0
                        && anglePairs.get(j).getAngleEnd() != 360) {
                    if (anglePairs.size() > j
                            && anglePairs.size() > 1) {
                        anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                        anglePair.setAngleEnd(anglePairs.get(j + 1).getAngleBegin());
                        anglePair.setNumber(anglePairs.get(j).getNumber());
                        anglePairsListFinal.add(anglePair);
                        continue;
                    }
                    if (anglePairs.size() == 1
                            || anglePairs.size() == j) {
                        anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                        anglePair.setAngleEnd(360);
                        anglePair.setNumber(anglePairs.get(j).getNumber());
                        anglePairsListFinal.add(anglePair);
                        continue;
                    }
                }

                if (anglePairs.get(j).getAngleBegin() != 0
                        && anglePairs.get(j).getAngleEnd() != 360) {

                    if (j == 0) {
                        if (anglePairs.size() == 1) {
                            anglePair.setAngleBegin(0);
                            anglePair.setAngleEnd(anglePairs.get(j).getAngleBegin());
                            anglePairsListFinal.add(anglePair);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePair = new AnglePair();
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(360);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePairsListFinal.add(anglePair);
                            continue;
                        }
                        if (anglePairs.size() > 1) {
                            anglePair.setAngleBegin(0);
                            anglePair.setAngleEnd(anglePairs.get(j).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePairsListFinal.add(anglePair);
                            anglePair = new AnglePair();
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(anglePairs.get(j + 1).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePairsListFinal.add(anglePair);
                        }
                    } else {
                        if (anglePairs.size() == 2) {
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(360);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePairsListFinal.add(anglePair);
                            continue;
                        }
                        if (anglePairs.size() > 2
                                && anglePairs.size() != j + 1) {
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(anglePairs.get(j + 1).getAngleBegin());
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePairsListFinal.add(anglePair);
                            continue;
                        }
                        if (anglePairs.size() == j + 1) {
                            anglePair.setAngleBegin(anglePairs.get(j).getAngleEnd());
                            anglePair.setAngleEnd(360);
                            anglePair.setNumber(anglePairs.get(j).getNumber());
                            anglePairsListFinal.add(anglePair);
                        }
                    }
                }
            }
        }

        if (DEBUG_ENABLED) {
            System.out.println("Arcs to be drawn are:");
            printAnglePairsFinal();
            System.out.println();
        }
    }

    /**
     * When circles rotated, arcs have to be recomputed.
     */
    public void prepareArcsWhenCirclesRotated() {
        prepareArcsAccompanyingLists();
        setArcToMousePosition(arcsArray[arcsArray.length - 1]);
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
        removeRedundancy();
        prepareArcs();
    }
}
