package anysegmentsintersect;


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
    
    
    final private double x;
    final private double y;
    
    private Segment segment;
}
