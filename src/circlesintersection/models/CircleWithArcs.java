package circlesintersection.models;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

/**
 * Circle with a list of arcs that do not intersect with other circles.
 */
public class CircleWithArcs {

    // region Fields
    private int mX = 0;
    private int mY = 0;
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
    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
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

    /**
     * Draw a center of a circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void drawCircleCenter(Graphics2D g2d, int deltaX, int deltaY) {
        g2d.drawOval(mX - deltaX, mY - deltaY, 1, 1);
    }

    /**
     * Draw a circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void drawCircle(Graphics2D g2d, int mouseX, int mouseY) {
        g2d.drawOval(mX - mouseX - (int) (mDiameter / 2),
                mY - mouseY - (int) (mDiameter / 2),
                (int) mDiameter, (int) mDiameter);
    }

    /**
     * Drawing a circled gradient at the center of circle
     *
     * @param g2d    Graphics component to perform drawing.
     * @param deltaX Extra X ordinate pixels.
     * @param deltaY Extra Y ordinate pixels.
     * @param color  Circle's gradient color
     */
    public void drawCircleGradient(Graphics2D g2d,
                                   int deltaX, int deltaY,
                                   Color color) {
        RadialGradientPaint gradientPaint = new RadialGradientPaint(
                (float) mX - deltaX,
                (float) mY - deltaY,
                (float) (mDiameter / 2),
                new float[]{(float) 0.0, (float) 1.0},
                new Color[]{color, new Color(0, 0, 0, 0)});
        g2d.setPaint(gradientPaint);
        g2d.fillOval(mX - deltaX - (int) (mDiameter / 2),
                mY - deltaY - (int) (mDiameter / 2),
                (int) mDiameter, (int) mDiameter);
    }
    // endregion Methods
}
