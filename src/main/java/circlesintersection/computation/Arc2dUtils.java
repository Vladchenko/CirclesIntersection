package circlesintersection.computation;

import circlesintersection.IllegalInstantiationException;
import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;

import java.util.List;

/**
 * Helper methods to create Arc2D instances.
 */
public class Arc2dUtils {

    private Arc2dUtils() {
        throw new IllegalInstantiationException("Arc2dUtils should not be instantiated!");
    }

    public static void createArcs2D(int deltaX, int deltaY, List<Arc> arcList, CircleWithArcs circleWithArcs) {
        Arc arc;
        for (int j = 0; j < arcList.size(); j++) {
            arc = new Arc();

            if (skipArcWhenItIsFullCircle(arcList, j)
                    || addArc2DWhenAngleBeginNotZeroAngleEnd360(
                    circleWithArcs,
                    arcList,
                    arc,
                    j,
                    deltaX,
                    deltaY)
                    || addArc2DWhenAngleBegin0AngleEndNot360(
                    deltaX,
                    deltaY,
                    arc,
                    arcList,
                    circleWithArcs,
                    j
            )) continue;

            addArc2DWhenAngleBeginNot0AngleEndNot360(deltaX, deltaY, arcList, circleWithArcs, arc, j);
        }
    }

    private static boolean skipArcWhenItIsFullCircle(List<Arc> arcList, int j) {
        return arcList.get(j).getAngleBegin() == 0
                && arcList.get(j).getAngleEnd() == 360;
    }

    private static void addArc2DWhenAngleBeginNot0AngleEndNot360(int deltaX, int deltaY, List<Arc> arcList, CircleWithArcs circleWithArcs, Arc arc, int j) {
        if (arcList.get(j).getAngleBegin() != 0
                && arcList.get(j).getAngleEnd() != 360) {
            if (j == 0) {
                if (addArc2DWhenArcSize1(deltaX, deltaY, arc, arcList, circleWithArcs, j))
                    return;
                addArc2DWhenArcsSizeBigger1(deltaX, deltaY, arc, arcList, circleWithArcs, j);
            } else {
                if (addArc2DWhenArcsSize2(deltaX, deltaY, arc, arcList, circleWithArcs, j)
                        || addArc2DWhenArcSizeBigger2AndNotequalToNext(deltaX, deltaY, arc, arcList, circleWithArcs, j))
                    return;
                addArc2DWhenSizeSizeEqualsToNextOne(deltaX, deltaY, arc, arcList, circleWithArcs, j);
            }
        }
    }

    private static boolean addArc2DWhenAngleBeginNotZeroAngleEnd360(
            CircleWithArcs circleWithArcs, List<Arc> arcList, Arc arc, int position, int deltaX, int deltaY) {
        if (arcList.get(position).getAngleBegin() != 0
                && arcList.get(position).getAngleEnd() == 360) {
            if (arcList.size() > 1
                    && arcList.size() == position + 1) {
                return true;
            }
            arc.setAngleBegin(0);
            arc.setAngleEnd(arcList.get(position).getAngleBegin());
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
            return true;
        }
        return false;
    }

    private static void addArc2DWhenSizeSizeEqualsToNextOne(int deltaX, int deltaY, Arc arc, List<Arc> arcList, CircleWithArcs circleWithArcs, int j) {
        if (arcList.size() == j + 1) {
            arc.setAngleBegin(arcList.get(j).getAngleEnd());
            arc.setAngleEnd(360);
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
        }
    }

    private static boolean addArc2DWhenArcSizeBigger2AndNotequalToNext(int deltaX, int deltaY, Arc arc, List<Arc> arcList, CircleWithArcs circleWithArcs, int j) {
        if (arcList.size() > 2
                && arcList.size() != j + 1) {
            arc.setAngleBegin(arcList.get(j).getAngleEnd());
            arc.setAngleEnd(arcList.get(j + 1).getAngleBegin());
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
            return true;
        }
        return false;
    }

    private static boolean addArc2DWhenArcsSize2(int deltaX, int deltaY, Arc arc, List<Arc> arcList, CircleWithArcs circleWithArcs, int j) {
        if (arcList.size() == 2) {
            arc.setAngleBegin(arcList.get(j).getAngleEnd());
            arc.setAngleEnd(360);
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
            return true;
        }
        return false;
    }

    private static void addArc2DWhenArcsSizeBigger1(int deltaX, int deltaY, Arc arc, List<Arc> arcList, CircleWithArcs circleWithArcs, int j) {
        if (arcList.size() > 1) {
            arc.setAngleBegin(0);
            arc.setAngleEnd(arcList.get(j).getAngleBegin());
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
            arc = new Arc();
            arc.setAngleBegin(arcList.get(j).getAngleEnd());
            arc.setAngleEnd(arcList.get(j + 1).getAngleBegin());
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
        }
    }

    private static boolean addArc2DWhenArcSize1(int deltaX, int deltaY, Arc arc, List<Arc> arcList, CircleWithArcs circleWithArcs, int j) {
        if (arcList.size() == 1) {
            arc.setAngleBegin(0);
            arc.setAngleEnd(arcList.get(j).getAngleBegin());
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
            arc = new Arc();
            arc.setAngleBegin(arcList.get(j).getAngleEnd());
            arc.setAngleEnd(360);
            circleWithArcs.addArc2D(arc, deltaX, deltaY);
            return true;
        }
        return false;
    }

    private static boolean addArc2DWhenAngleBegin0AngleEndNot360(int deltaX, int deltaY, Arc arc, List<Arc> arcList, CircleWithArcs circleWithArcs, int j) {
        if (arcList.get(j).getAngleBegin() == 0
                && arcList.get(j).getAngleEnd() != 360) {
            if (arcList.size() > j
                    && arcList.size() > 1) {
                arc.setAngleBegin(arcList.get(j).getAngleEnd());
                arc.setAngleEnd(arcList.get(j + 1).getAngleBegin());
                circleWithArcs.addArc2D(arc, deltaX, deltaY);
                return true;
            }
            if (arcList.size() == 1
                    || arcList.size() == j) {
                arc.setAngleBegin(arcList.get(j).getAngleEnd());
                arc.setAngleEnd(360);
                circleWithArcs.addArc2D(arc, deltaX, deltaY);
                return true;
            }
        }
        return false;
    }
}
