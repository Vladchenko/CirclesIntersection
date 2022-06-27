package circlesintersection.models;

import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Circle with a list of arcs that do not intersect with other circles.
 */
public class CircleWithArcs {

    // region Fields
    private double mX = 0;
    private double mY = 0;
    private double mDiameter = 0;
    /*
     * Flag tells whether current circle is to be excluded from checking if it intersects with other circles around.
     * If a circle is inside any other circle, this flag is set to true.
     */
    private boolean mExcluded = false;

    private ArrayList<Arc2D> mArcs2D;
    private ArrayList<Arc> mArcsList;
    // endregion fields

    /**
     * Public constructor.
     */
    public CircleWithArcs() {
        mArcs2D = new ArrayList<>();
        mArcsList = new ArrayList<>();
    }

    // region Getters & Setters
    public double getX() {
        return mX;
    }

    public void setX(double x) {
        mX = x;
    }

    public double getY() {
        return mY;
    }

    public void setY(double y) {
        mY = y;
    }

    public double getDiameter() {
        return mDiameter;
    }

    public void setDiameter(double diameter) {
        mDiameter = diameter;
    }

    public boolean isExcluded() {
        return mExcluded;
    }

    public void setExcluded(boolean excluded) {
        mExcluded = excluded;
    }

    public ArrayList<Arc2D> getArcs2D() {
        return mArcs2D;
    }

    public void setArcs2D(ArrayList<Arc2D> arcs2D) {
        mArcs2D = arcs2D;
    }

    public ArrayList<Arc> getArcsList() {
        return mArcsList;
    }

    public void setArcsList(ArrayList<Arc> arcsList) {
        mArcsList = arcsList;
    }
    // endregion getters & setters

    // region Methods
    /**
     * Add new {@link Arc} for current circle.
     *
     * @param newArc to be added
     */
    public void addArc(Arc newArc) {
        mArcsList.add(newArc);
    }

    public void addArc2D(Arc arc, int deltaX, int deltaY) {
        mArcs2D.add(new Arc2D.Double(
                mX
                        - deltaX
                        - mDiameter / 2,
                mY
                        - deltaY
                        - mDiameter / 2,
                mDiameter,
                mDiameter,
                arc.getAngleBegin(),
                (arc.getAngleEnd()
                        - arc.getAngleBegin()),
                Arc2D.OPEN)
        );
    }
    // endregion Methods
}
