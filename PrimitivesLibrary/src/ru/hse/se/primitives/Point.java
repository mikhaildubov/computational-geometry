package ru.hse.se.primitives;


public class Point {
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void setSegment(Segment segment) {
        this.segment = segment;
    }
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    public Segment getSegment() {
        return segment;
    }
    
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }
    
    public static boolean isLeftTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) > 0);
    }
    
    public static boolean isRightTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) < 0);
    }
    
    @Override
    public boolean equals(Object o2) {
        Point p2 = (Point) o2;
        
        return (this.getX() == p2.getX() && this.getY() == p2.getY());
    }
    
    @Override
    public String toString() {
        return ("(" + this.getX() + ", " + this.getY() + ")");
    }
    
    public boolean isLeft() {
        return this.equals(segment.getLeft());
    }
    
    public boolean isRight() {
        return this.equals(segment.getRight());
    }
    
    
    public final static Point NaP = new Point(Double.NaN, Double.NaN);
    
    final private double x;
    final private double y;
    
    private Segment segment;
}
