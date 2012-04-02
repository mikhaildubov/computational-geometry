package halfplanes;

/**
 * A point.
 * 
 * @author Mikhail Dubov
 */
public class Point {
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public static Point NaP = new Point(Double.NaN, Double.NaN);
    
    private double x, y;
}
