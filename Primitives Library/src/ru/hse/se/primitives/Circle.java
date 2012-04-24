package ru.hse.se.primitives;

/**
 * Represents a circle.
 * 
 * @author Mikhail Dubov
 */
public class Circle {
    
    /**
     * Initializes a circle by its center and radius.
     * 
     * @param center The center of the circle
     * @param radius The radius of the circle, must be positive
     */
    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }
    
    /**
     * Initializes a circle by three different
     * points lying on its boundary.
     * 
     * @param a A point that lies on the circle boundary
     * @param b A point that lies on the circle boundary
     * @param c A point that lies on the circle boundary
     */
    public Circle(Point a, Point b, Point c) {
        Line l1 = new Line(2*a.getX() - 2*b.getX(),
                           2*a.getY() - 2*b.getY(),
                           b.getX()*b.getX() - a.getX()*a.getX() +
                           b.getY()*b.getY() - a.getY()*a.getY());
        Line l2 = new Line(2*a.getX() - 2*c.getX(),
                           2*a.getY() - 2*c.getY(),
                           c.getX()*c.getX() - a.getX()*a.getX() +
                           c.getY()*c.getY() - a.getY()*a.getY());
        
        // TODO: throw an Exception?
        center = Line.intersection(l1, l2); // may be NaP (if a, b, c are collinear).
        radius = center.dist(a);
    }
    
    /**
     * Returns the circle center.
     * 
     * @return The center point
     */
    public Point getCenter() {
        return center;
    }
    
    /**
     * Returns the circle radius.
     * 
     * @return The radius
     */
    public double getRadius() {
        return radius;
    }
    
    /**
     * Determines whether the given point lies inside the circle.
     * 
     * @param p The point
     * @return true, if the point is inside, false otherwise
     */
    public boolean isInside(Point p) {
        return center.dist(p) < radius;
    }
    
    /**
     * Determines whether the given point lies outside the circle.
     * 
     * @param p The point
     * @return true, if the point is outside, false otherwise
     */
    public boolean isOutside(Point p) {
        return center.dist(p) > radius;
    }
    
    private Point center;
    private double radius;
}
