package ru.hse.se.primitives;

/**
 *
 * @author Mikhail Dubov
 */
public class Circle {
    
    public Circle(Point center, double radius) {
        this.center = center;
        this.radius = radius;
    }
    
    public Circle(Point a, Point b, Point c) {
        Line l1 = new Line(2*a.getX() - 2*b.getX(),
                           2*a.getY() - 2*b.getY(),
                           b.getX()*b.getX() - a.getX()*a.getX() +
                           b.getY()*b.getY() - a.getY()*a.getY());
        Line l2 = new Line(2*a.getX() - 2*c.getX(),
                           2*a.getY() - 2*c.getY(),
                           c.getX()*c.getX() - a.getX()*a.getX() +
                           c.getY()*c.getY() - a.getY()*a.getY());
        
        // TODO: throw an Excetption?
        center = Line.intersection(l1, l2); // may be NaP (if a, b, c are collinear).
        radius = center.dist(a);
    }
    
    
    public Point getCenter() {
        return center;
    }
    
    public double getRadius() {
        return radius;
    }
    
    public boolean isInside(Point p) {
        return center.dist(p) < radius;
    }
    
    public boolean isOutside(Point p) {
        return center.dist(p) > radius;
    }
    
    private Point center;
    private double radius;
}
