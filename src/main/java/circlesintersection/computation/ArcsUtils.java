package circlesintersection.computation;

import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.Logger;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.logging.Level;

import static circlesintersection.presentation.GraphicsSettings.DEBUG_ENABLED;

/**
 * Utils to assist circles' arcs computation.
 */
public class ArcsUtils {

    /**
     * Private constructor.
     */
    private ArcsUtils() {
        throw new RuntimeException("ArcsUtils should not be instantiated!");
    }

    /**
     * Remove the redundant arcs (the ones that overlap with others). I.e.
     * when there are arcs like [30,68] and [49, 105], method will have made a one arc - [30, 105].
     *
     * @param circlesList List of circles
     */
    public static void removeRedundantArcs(List<CircleWithArcs> circlesList) {
        List<Arc> arcList;
        for (CircleWithArcs circleWithArcs : circlesList) {
            arcList = circleWithArcs.getArcsList();
            if (arcList.size() > 1) {
                for (int j = 0; j < arcList.size() - 1; j++) {
                    if (arcList.get(j).getAngleEnd() >= arcList.get(j + 1).getAngleBegin()) {
                        if (arcList.get(j).getAngleEnd() >= arcList.get(j + 1).getAngleEnd()) {
                            arcList.remove(j + 1);
                            j--;
                        } else if (arcList.get(j).getAngleEnd() < arcList.get(j + 1).getAngleEnd()) {
                            arcList.get(j).setAngleEnd(arcList.get(j + 1).getAngleEnd());
                            arcList.remove(j + 1);
                            j--;
                        }
                    }
                }
            }
        }

        if (DEBUG_ENABLED) {
            java.util.logging.Logger.getGlobal().log(Level.INFO, "Redundant arcs have been removed:");
            Logger.printArcs(circlesList);
        }
    }

    /**
     * Invert the arcs, for there could be shown not the ones that intersect,
     * but the ones that free of intersection.
     *
     * @param circlesList List of circles
     * @param deltaX      Some shift of x ordinate (used to perform mouse dragging)
     * @param deltaY      Some shift of y ordinate (used to perform mouse dragging)
     */
    public static void invertArcsAndCreateArcs2D(List<CircleWithArcs> circlesList, int deltaX, int deltaY) {

        Arc arc;

        List<Arc> arcList;
        for (CircleWithArcs circleWithArcs : circlesList) {
            arcList = circleWithArcs.getArcsList();
            for (int j = 0; j < arcList.size(); j++) {
                arc = new Arc();

                if (arcList.get(j).getAngleBegin() == 0
                        && arcList.get(j).getAngleEnd() == 360) {
                    continue;
                }

                if (arcList.get(j).getAngleBegin() != 0
                        && arcList.get(j).getAngleEnd() == 360) {
                    if (arcList.size() > 1
                            && arcList.size() == j + 1) {
                        continue;
                    }
                    arc.setAngleBegin(0);
                    arc.setAngleEnd(arcList.get(j).getAngleBegin());
                    circleWithArcs.addArc2D(arc, deltaX, deltaY);
                    continue;
                }

                if (arcList.get(j).getAngleBegin() == 0
                        && arcList.get(j).getAngleEnd() != 360) {
                    if (arcList.size() > j
                            && arcList.size() > 1) {
                        arc.setAngleBegin(arcList.get(j).getAngleEnd());
                        arc.setAngleEnd(arcList.get(j + 1).getAngleBegin());
                        circleWithArcs.addArc2D(arc, deltaX, deltaY);
                        continue;
                    }
                    if (arcList.size() == 1
                            || arcList.size() == j) {
                        arc.setAngleBegin(arcList.get(j).getAngleEnd());
                        arc.setAngleEnd(360);
                        circleWithArcs.addArc2D(arc, deltaX, deltaY);
                        continue;
                    }
                }

                if (arcList.get(j).getAngleBegin() != 0
                        && arcList.get(j).getAngleEnd() != 360) {

                    if (j == 0) {
                        if (arcList.size() == 1) {
                            arc.setAngleBegin(0);
                            arc.setAngleEnd(arcList.get(j).getAngleBegin());
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                            arc = new Arc();
                            arc.setAngleBegin(arcList.get(j).getAngleEnd());
                            arc.setAngleEnd(360);
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                            continue;
                        }
                        if (arcList.size() > 1) {
                            arc.setAngleBegin(0);
                            arc.setAngleEnd(arcList.get(j).getAngleBegin());
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                            arc = new Arc();
                            arc.setAngleBegin(arcList.get(j).getAngleEnd());
                            arc.setAngleEnd(arcList.get(j + 1).getAngleBegin());
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                        }
                    } else {
                        if (arcList.size() == 2) {
                            arc.setAngleBegin(arcList.get(j).getAngleEnd());
                            arc.setAngleEnd(360);
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                            continue;
                        }
                        if (arcList.size() > 2
                                && arcList.size() != j + 1) {
                            arc.setAngleBegin(arcList.get(j).getAngleEnd());
                            arc.setAngleEnd(arcList.get(j + 1).getAngleBegin());
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                            continue;
                        }
                        if (arcList.size() == j + 1) {
                            arc.setAngleBegin(arcList.get(j).getAngleEnd());
                            arc.setAngleEnd(360);
                            circleWithArcs.addArc2D(arc, deltaX, deltaY);
                        }
                    }
                }
            }
            circleWithArcs.setArcsList(new ArrayList<>());
        }

        if (DEBUG_ENABLED) {
            Logger.printArcs2DAngles(circlesList);
        }
    }

    /**
     * Modify arc's angles so that they could be drawn correctly.
     *
     * @param arcsList list of arcs that new arc is to be added to.
     * @param arc      new arc
     */
    public static void createAndAddRefinedArcs(List<Arc> arcsList, Arc arc) {
        Arc arcNew = new Arc();
        /*
         * Negative angle cannot be operated with, thus one needs to get rid of
         * it, using the following way.
         */
        if (arc.getAngleBegin() < 0
                || arc.getAngleBegin() > arc.getAngleEnd()) {
            arcNew.setAngleBegin(0);
            arcNew.setAngleEnd(arc.getAngleEnd());
            arcsList.add(arcNew);
            arcNew = new Arc();
            if (arc.getAngleBegin() < 0) {
                arcNew.setAngleBegin(360 - Math.abs(arc.getAngleBegin()));
            } else {
                arcNew.setAngleBegin(Math.abs(arc.getAngleBegin()));
            }
            arcNew.setAngleEnd(360);
        } else {
            arcNew.setAngleBegin(arc.getAngleBegin());
            arcNew.setAngleEnd(arc.getAngleEnd());
        }
        arcsList.add(arcNew);
    }

    /**
     * @param circle Circle that is not to be intersected to any other circles
     * @return Circle that is not intersected to any other circles (in fact this is arc, but considered as circle)
     */
    public static Arc getNotIntersectedCircle(CircleWithArcs circle) {
        Arc arc = new Arc();
        arc.setAngleBegin(0);
        arc.setAngleEnd(0);
        if (circle.isExcluded()) {
            arc.setAngleEnd(360);
        }
        return arc;
    }

    /**
     * Sort arcs, to remove a redundant ones.
     *
     * @param circlesList List of circles with arcs
     */
    public static void sortArcs(List<CircleWithArcs> circlesList) {
        try {
            Arc.ArcComparator comparator = new Arc.ArcComparator();
            for (CircleWithArcs circleWithArcs : circlesList) {
                if (circleWithArcs.getArcsList().size() > 1) {
                    circleWithArcs.getArcsList().sort(comparator);
                }
            }
        } catch (ConcurrentModificationException ex) {
            java.util.logging.Logger.getGlobal().log(Level.SEVERE,ex.getMessage());
        }
    }
}
