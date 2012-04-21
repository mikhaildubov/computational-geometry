package ru.hse.se.primitives;

/**
 * Represents a line by means of three coefficients
 * a, b and c, where ax + by + c = 0 holds.
 * 
 * @author Mikhail Dubov
 */
public class Line {
       
    /**
     * Initializes a line by the three coefficients
     * a, b and c, where ax + by + c = 0 holds.
     * @param a The a coefficient
     * @param b The b coefficient
     * @param c The c coefficient
     */
    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
        
    /**
     * Initializes a line by two different points lying on it.
     * 
     * @param p1 The first point
     * @param p2 The second point
     */
    public Line(Point p1, Point p2) {
        this.a = p1.getY() - p2.getY();
        this.b = p2.getX() - p1.getX();
        this.c = p1.getX()*p2.getY() - p2.getX()*p1.getY();
    }
        
    /**
     * Initializes a line by a segment lying on it.
     * 
     * @param s The segment
     */
    public Line(Segment s) {
        this(s.getLeft(), s.getRight());
    }
    
    /**
     * Returns the a coefficient for the line,
     * where ax + by + c = 0 holds.
     * 
     * @return The a coefficient
     */
    public double getA() {
        return a;
    }
    /**
     * Returns the b coefficient for the line,
     * where ax + by + c = 0 holds.
     * 
     * @return The b coefficient
     */
    
    public double getB() {
        return b;
    }
    
    /**
     * Returns the c coefficient for the line,
     * where ax + by + c = 0 holds.
     * 
     * @return The c coefficient
     */
    public double getC() {
        return c;
    }
    
    /**
     * Calculates the X coordinate of a point on the line
     * by its Y coordinate.
     * 
     * @param y The Y coordinate
     * @return The X coordinate
     */
    public double XforY(double y) {
        return (-c - b*y)/a;
    }
    
    /**
     * Calculates the Y coordinate of a point on the line
     * by its X coordinate.
     * 
     * @param x The X coordinate
     * @return The Y coordinate
     */
    public double YforX(double x) {
        return (-c - a*x)/b;
    }
    
    /**
     * Determines whether the line is ascending
     * (that is, makes an angle with the positive
     * direction of the X axis that lies in (0, pi/2).
     * 
     * @return true, if the line is ascending, false otherwise
     */
    public boolean isAscending() {
        return (b == 0 || (-a/b) > 0);
    }
    
    /**
     * Determines whether the line is vertical
     * (that is, makes an angle with the positive
     * direction of the X axis that is equal to pi/2.
     * 
     * @return true, if the line is vertical, false otherwise
     */
    public boolean isVertical() {
        return (b == 0 && a != 0);
    }
    
    /**
     * Determines whether the line is descending
     * (that is, makes an angle with the positive
     * direction of the X axis that lies in (pi/2, pi).
     * 
     * @return true, if the line is descending, false otherwise
     */
    public boolean isDescending() {
        return (b == 0 || (-a/b) < 0);
    }
    
    /**
     * Determines whether the line is horizontal
     * (that is, makes an angle with the positive
     * direction of the X axis that is equal to pi.
     * 
     * @return true, if the line is horizontal, false otherwise
     */
    public boolean isHorizontal() {
        return (a == 0 && b != 0);
    }
    
    /**
     * Calculates the angle that the line makes
     * with the positive direction of the X axis.
     * 
     * @return The angle in radians
     */
    public double getAngle() {
        if (isVertical()) return Math.PI/2;
        
        double atan = Math.atan(-a/b);
        if (atan < 0) atan += Math.PI;
        
        return atan;
    }
    
    /**
     * Determines whether the point lies
     * on the left side of the line.
     * 
     * @param p The point
     * @return true, if the point lies in the left halfplane, false otherwise
     */
    public boolean isLeftPoint(Point p) {
        return p.getX() < XforY(p.getY());
    }
    
    /**
     * Determines whether the point lies
     * on the right side of the line.
     * 
     * @param p The point
     * @return true, if the point lies in the right halfplane, false otherwise
     */
    public boolean isRightPoint(Point p) {
        return p.getX() > XforY(p.getY());
    }
    
    /**
     * Calculates the intersection of two lines.
     * 
     * @param l1 The first line
     * @param l2 The second line
     * @return The intersection point or Point.NaP, if there is no intersection
     */
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
