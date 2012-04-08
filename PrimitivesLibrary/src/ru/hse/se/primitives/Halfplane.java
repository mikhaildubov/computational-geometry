package ru.hse.se.primitives;

/**
 * Represents a halfplane by means of the boundary line
 * ax + by = c, where one accepts that
 * for the halfplane ax + by + c <= 0 holds.
 * 
 * @author Mikhail Dubov
 */
public class Halfplane {
    
    public Halfplane(Line line, boolean rightSide) {
        this.line = line;
        this.rightSide = rightSide;
    }
    
    public Line getLine() {
        return line;
    }
    
    public boolean isLeftBoundary() {
        return rightSide;
    }
    
    public boolean isRightBoundary() {
        return ! rightSide;
    }
    
    private Line line;
    private boolean rightSide;
}
