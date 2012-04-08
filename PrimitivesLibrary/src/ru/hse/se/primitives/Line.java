package ru.hse.se.primitives;

/**
 * Represents a line by means of three coefficients
 * a, b and c, where ax + by + c = 0 holds.
 * 
 * @author Mikhail Dubov
 */
public class Line {
        
    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
        
    public Line(Point p1, Point p2) {
        this.a = p1.getY() - p2.getY();
        this.b = p2.getX() - p1.getX();
        this.c = p1.getX()*p2.getY() - p2.getX()*p1.getY();
    }
        
    public Line(Segment s) {
        this(s.getLeft(), s.getRight());
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
        return (-c - b*y)/a;
    }
    
    public double YforX(double x) {
        return (-c - a*x)/b;
    }
    
    public boolean isAscending() {
        return (b == 0 || (-a/b) > 0);
    }
    
    public boolean isVertical() {
        return b == 0;
    }
    
    public boolean isDescending() {
        return (b == 0 || (-a/b) < 0);
    }
    
    public double getAngle() {
        if (isVertical()) return Math.PI/2;
        
        double atan = Math.atan(-a/b);
        if (atan < 0) atan += Math.PI;
        
        return atan;
    }
    
    public boolean isLeftPoint(Point p) {
        return p.getX() < XforY(p.getY());
    }
    
    public boolean isRightPoint(Point p) {
        return p.getX() > XforY(p.getY());
    }
    
    public static Point intersection(Line l1, Line l2) {
        if (l1.b != 0) {
            double f = l2.a - l2.b*l1.a/l1.b;
            if (f == 0) {
                return Point.NaP;
            } else {
                double x = (-l2.c + l2.b*l1.c/l1.b) / f;
                double y = (-l1.c - l1.a*x) / l1.b;
                
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
                    double y = (-l2.c + l2.a*l1.c/l1.a) / f;
                    double x = (-l1.c - l1.b*y) / l1.a;
                
                    return new Point(x, y);
                }
            }
        }
    }
    
    private double a, b, c;
}
