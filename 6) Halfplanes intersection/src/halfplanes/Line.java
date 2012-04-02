package halfplanes;

/**
 * Represents a line by means of three coefficients
 * a, b and c, where ax + by = c holds.
 * 
 * @author Mikhail Dubov
 */
public class Line {
        
    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public double getA() {
        return a;
    }
    
    public double getB() {
        return b;
    }
    
    public double getC() {
        return c;
    }
    
    public double XforY(double y) {
        return (c - b*y)/a;
    }
    
    public boolean isPositive() {
        return (b == 0 || (-a/b) > 0);
    }
    
    private double a, b, c;
    
    public static Point intersection(Line l1, Line l2) {
        if (l1.b != 0) {
            double f = l2.a - l2.b*l1.a/l1.b;
            if (f == 0) {
                return Point.NaP;
            } else {
                double x = (l2.c - l2.b*l1.c/l1.b) / f;
                double y = (l1.c - l1.a*x) / l1.b;
                
                return new Point(x, y);
            }
        } else {
            if (l1.a == 0) {
                return Point.NaP;
            } else {
                double f = l2.b - l2.a*l1.b/l1.a;
                if (f == 0) {
                    return Point.NaP;
                } else {
                    double y = (l2.c - l2.a*l1.c/l1.a) / f;
                    double x = (l1.c - l1.b*y) / l1.a;
                
                    return new Point(x, y);
                }
            }
        }
    }
}
