package circlesintersection.models;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Model for a pair of arc angles of some circle.
 *
 * [angleBegin] initial angle of an arc.
 * [angleEnd] ending angle of an arc.
 */
public class Arc {

    private double mAngleEnd;
    private double mAngleBegin;
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    @Override
    public String toString() {
        return (NUMBER_FORMAT.format(mAngleBegin) + " " + NUMBER_FORMAT.format(mAngleEnd) + "  ");
    }

    // region Getters&Setters
    public double getAngleEnd() {
        return mAngleEnd;
    }

    public void setAngleEnd(double angleEnd) {
        this.mAngleEnd = angleEnd;
    }

    public double getAngleBegin() {
        return mAngleBegin;
    }

    public void setAngleBegin(double angleBegin) {
        this.mAngleBegin = angleBegin;
    }
    // endregion Getters&Setters

    /**
     * Comparator for instances of this class.
     */
    public static class ArcComparator implements Comparator<Arc> {

        @Override
        public int compare(Arc o1, Arc o2) {
            int value = 0;
            try {
                value = Double.compare(o1.mAngleBegin, o2.mAngleBegin);
            } catch (NullPointerException exception) {
                Logger.getGlobal().log(Level.SEVERE,exception.getMessage());
            }
            return value;
        }
    }
}
