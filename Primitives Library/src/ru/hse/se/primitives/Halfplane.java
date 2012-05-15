package ru.hse.se.primitives;

/**
 * Represents a halfplane by means of the boundary line
 * ax + by = c, where one accepts that
 * for the halfplane ax + by + c <= 0 holds.
 * 
 * @author Mikhail Dubov
 */
public class Halfplane {
    
    /**
     * Initializes a halfplane by its boundary line
     * and by the side where the halfplane lies.
     * 
     * @param line The boundary line
     * @param rightSide true, if the halfplanes lies on the right side
     *                  of the line, false otherwise
     */
    public Halfplane(Line line, boolean rightSide) {
        this.line = line;
        this.rightSide = rightSide;
    }
    
    /**
     * Returns the boundary line for the halfplane.
     * 
     * @return The boundary line
     */
    public Line getLine() {
        return line;
    }
    
    /**
     * Determines whether the boundary line
     * lies on the left side of the halfplane.
     * 
     * @return true, if the boundary line is on the left, flase otherwise
     */
    public boolean isLeftBoundary() {
        return rightSide;
    }
    
    /**
     * Determines whether the boundary line
     * lies on the right side of the halfplane.
     * 
     * @return true, if the boundary line is on the right, flase otherwise
     */
    public boolean isRightBoundary() {
        return ! rightSide;
    }
    
    
    
    /**
     * Determines whether the point lies
     * in the halfplane.
     * 
     * @param p The point
     * @return true, if the point lies in the left halfplane, false otherwise
     */
    public boolean includes(Point p) {
        
        if (isLeftBoundary()) {
            
            if (line.isHorizontal()) { // == upper boundary
                return (p.getY() < line.YforX(0));
            } else {
                return (p.getX() > line.XforY(p.getY()));
            }
            
        } else {
            
            if (line.isHorizontal()) { // == lower boundary
                return (p.getY() > line.YforX(0));
            } else {
                return (p.getX() < line.XforY(p.getY()));
            }
        }
    }
    
    private Line line;
    private boolean rightSide;
}
