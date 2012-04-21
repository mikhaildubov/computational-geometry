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
    
    private Line line;
    private boolean rightSide;
}
