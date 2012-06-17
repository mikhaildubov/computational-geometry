package ru.dubov.primitives;

/**
 * Represents a point in two dimensional space.
 * 
 * @author Mikhail Dubov
 */
public class Point {
    
    /**
     * Initializes a point by its coordinates.
     * 
     * @param x The X coordinate.
     * @param y The Y coordinate.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Attaches a segment to the point.
     * 
     * @param segment The attached segment
     */
    public void setSegment(Segment segment) {
        this.segment = segment;
    }
    
    /**
     * Returns the X coordinate of the point.
     * 
     * @return The X coordinate
     */
    public double getX() {
        return x;
    }
    
    /**
     * Returns the Y coordinate of the point.
     * 
     * @return The Y coordinate
     */
    public double getY() {
        return y;
    }
    
    /**
     * Returns the segment attached to the point.
     * @return The attached segment
     */
    public Segment getSegment() {
        return segment;
    }
    
    /**
     * Calculates the cross product
     * of two vectors (p0, p1) and (p1, p2)
     * defined by three points p0, p1 and p2.
     * 
     * @param p0 The first point
     * @param p1 The second point
     * @param p2 The third point
     * @return The cross product
     */
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }
    
    /**
     * Calculates the remoteness of another point.
     * 
     * @param p0 The point
     * @return The distance between the current point and the given point
     */
    public double dist(Point p0) {
        return Math.sqrt((this.getX() - p0.getX()) * (this.getX() - p0.getX()) +
                         (this.getY() - p0.getY()) * (this.getY() - p0.getY()));
    }
    
    /**
     * Determines whether two vectors (p0, p1) and (p1, p2)
     * defined by three points p0, p1 and p2 make a left turn.
     * 
     * @param p0 The first point
     * @param p1 The second point
     * @param p2 The third point
     * @return true, if the turn is left, false otherwise
     */
    public static boolean isLeftTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) > 0);
    }
    
    /**
     * Determines whether two vectors (p0, p1) and (p1, p2)
     * defined by three points p0, p1 and p2 make a right turn.
     * 
     * @param p0 The first point
     * @param p1 The second point
     * @param p2 The third point
     * @return true, if the turn is right, false otherwise
     */
    public static boolean isRightTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) < 0);
    }
    
    @Override
    public boolean equals(Object o2) {
        
        if (! (o2 instanceof Point)) {
            return false;
        }
        
        Point p2 = (Point) o2;
        
        return (this.getX() == p2.getX() && this.getY() == p2.getY());
    }
    
    @Override
    public String toString() {
        return ("(" + this.getX() + ", " + this.getY() + ")");
    }
    
    /**
     * Determines whether the point is the left vertex of the attached segment.
     * 
     * @return true, if the point is the left vertex, false otherwise
     */
    public boolean isLeft() {
        return (segment != null && this.equals(segment.getLeft()));
    }
    
    /**
     * Determines whether the point is the right vertex of the attached segment.
     * 
     * @return true, if the point is the right vertex, false otherwise
     */
    public boolean isRight() {
        return (segment != null && this.equals(segment.getRight()));
    }
    
    /**
     * Determines whether the point is equal to Point.NaP (not a point).
     * 
     * @return true, if the point is NaP, false otherwise
     */
    public boolean isNaP() {
        return x == Double.NaN || y == Double.NaN;
    }
    
    /**
     * The "not a point" object.
     */
    public final static Point NaP = new Point(Double.NaN, Double.NaN);
    
    final private double x;
    final private double y;
    
    private Segment segment;
}
