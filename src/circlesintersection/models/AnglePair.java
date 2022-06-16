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

    private int mNumber;
    private double mAngleEnd;
    private double mAngleBegin;
    private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance();

    @Override
    public String toString() {
        return ("  " + mNumber + ":" + NUMBER_FORMAT.format(mAngleBegin) + " " + NUMBER_FORMAT.format(mAngleEnd) + "  ");
    }

    public void setNumber(int number) {
        this.mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

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

    /**
     * Comparator for instances of this class.
     */
    public static class AnglePairComparator implements Comparator<AnglePair> {

        @Override
        public int compare(AnglePair o1, AnglePair o2) {
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
