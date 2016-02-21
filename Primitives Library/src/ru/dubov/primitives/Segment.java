package ru.dubov.primitives;

/**
 * Represents a segment.
 * 
 * @author Mikhail Dubov
 */
public class Segment {
    
    /**
     * Initializes a segment by its vertices.
     * 
     * @param left The left vertex
     * @param right The right vertex
     */
    public Segment(Point left, Point right) {
        
        if(left.getX() > right.getX()) {
            Point temp = right;
            right = left;
            left = temp;
        }
        
        left.setSegment(this);
        right.setSegment(this);
        
        this.left = left;
        this.right = right;
    }
    
    /**
     * Returns the left vertex of the segment.
     * 
     * @return The left vertex
     */
    public Point getLeft() {
        return left;
    }
    
    /**
     * Returns the right vertex of the segment.
     * 
     * @return The right vertex
     */
    public Point getRight() {
        return right;
    }
    
    /**
     * Returns the bisectional point of the segment.
     * 
     * @return The bisectional point
     */
    public Point getBisectionalPoint() {
        return new Point((left.getX() + right.getX()) / 2,
                          (left.getY() + right.getY()) / 2);
    }
    
    /**
     * Returns the line the segment lies on.
     */
    public Line getLine() {
        return new Line(left, right);
    }
    
    
    @Override
    public boolean equals(Object o2) {
        
        if (! (o2 instanceof Segment)) {
            return false;
        }
        
        Segment s2 = (Segment) o2;
        
        return ((this.getLeft().equals(s2.getLeft()) &&
                    this.getRight().equals(s2.getRight())) ||
                (this.getLeft().equals(s2.getRight()) &&
                    this.getRight().equals(s2.getLeft())));
    }
    
    final private Point left;
    final private Point right;
}
