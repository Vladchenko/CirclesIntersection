package circlesintersection.models;

/**
 * Class represents a part of a circle.
 */
public class Arc {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    private int number = 0;
    
    private double x = 0;
    private double y = 0;
    private double diameter = 0;
    private double angleBegin = 0;
    /*
     * Value for "span" of angle that begins with "angleBegin" and ends with "angleBegin + angleSpan".
     */
    private double angleSpan = 0;
    /*
     * Flag tells whether current circle is to be excluded from checking if it intersects with other circles around.
     * If a circle is inside any other circle, this flag is set to true.
     */
    private boolean excluded = false;
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getX() {
        return x;
    }
    
    public void setX(double x) {
        this.x = x;
    }
    
    public double getY() {
        return y;
    }
    
    public void setY(double y) {
        this.y = y;
    }
    
    public double getDiameter() {
        return diameter;
    }
    
    public void setDiameter(double r) {
        this.diameter = r;
    }
    
    public double getAngleBegin() {
        return angleBegin;
    }
    
    public void setAngleBegin(double angleBegin) {
        this.angleBegin = angleBegin;
    }
    
    public double getAngleSpan() {
        return angleSpan;
    }
    
    public void setAngleSpan(double AngleValid) {
        this.angleSpan = AngleValid;
    }
    
    public boolean isExcluded() {
        return excluded;
    }

    public void setExcluded(boolean excluded) {
        this.excluded = excluded;
    }
    //</editor-fold>
}
