package circlesintersection.models;

/**
 * Class represents a circle.
 */
public class Arc {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private int mNumber = 0;
    
    private double mX = 0;
    private double mY = 0;
    private double mDiameter = 0;
    /*
     * Flag tells whether current circle is to be excluded from checking if it intersects with other circles around.
     * If a circle is inside any other circle, this flag is set to true.
     */
    private boolean mExcluded = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

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
    
    public void setDiameter(double r) {
        mDiameter = r;
    }
    
    public boolean isExcluded() {
        return mExcluded;
    }

    public void setExcluded(boolean excluded) {
        mExcluded = excluded;
    }
    //</editor-fold>
}
