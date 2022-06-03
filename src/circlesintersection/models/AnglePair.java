package circlesintersection.models;

import java.text.NumberFormat;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Pair of arc angles of some circle.
 *
 * [angleBegin] initial angle of an arc.
 * [angleEnd] ending angle of an arc.
 */
public class AnglePair {

    private int number;
    private double angleEnd;
    private double angleBegin;
    private static final NumberFormat numberFormat = NumberFormat.getNumberInstance();

    @Override
    public String toString() {
        return ("  " + number + ":" + numberFormat.format(angleBegin) + " " + numberFormat.format(angleEnd) + "  ");
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public double getAngleEnd() {
        return angleEnd;
    }

    public void setAngleEnd(double angleEnd) {
        this.angleEnd = angleEnd;
    }

    public double getAngleBegin() {
        return angleBegin;
    }

    public void setAngleBegin(double angleBegin) {
        this.angleBegin = angleBegin;
    }

    /**
     * Comparator for angle pairs.
     */
    public static class AnglePairComparator implements Comparator<AnglePair> {

        @Override
        public int compare(AnglePair o1, AnglePair o2) {
            int value = 0;
            try {
                value = Double.compare(o1.angleBegin, o2.angleBegin);
            } catch (NullPointerException exception) {
                Logger.getGlobal().log(Level.SEVERE,exception.getMessage());
            }
            return value;
        }
    }
}
