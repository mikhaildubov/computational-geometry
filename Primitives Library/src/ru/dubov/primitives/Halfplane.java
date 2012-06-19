package ru.dubov.primitives;

import java.util.ArrayList;

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
    
    /**
     * Initializes a set of halfplanes with a bounding rectangle.
     * 
     * @param x1 left X coordinate
     * @param x2 right X coordinate
     * @param y1 bottom Y coordinate
     * @param y2 upper Y coordinate
     * @return Set of bounding halfplanes
     */
    public static ArrayList<Halfplane> boundingRectangle(double x1, double x2,
                                                         double y1, double y2) {
        
        ArrayList<Halfplane> res = new ArrayList<Halfplane>();
        
        if (x1 > x2) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        
        if (y1 > y2) {
            double temp = y1;
            y1 = y2;
            y2 = temp;
        }
        
        res.add(new Halfplane(new Line(new Point(x1, 0), new Point(x1, 1)), true));
        res.add(new Halfplane(new Line(new Point(0, y2), new Point(1, y2)), true));
        res.add(new Halfplane(new Line(new Point(x2, 0), new Point(x2, 1)), false));
        res.add(new Halfplane(new Line(new Point(0, y1), new Point(1, y1)), false));
        
        return res;
    }
    
    private Line line;
    private boolean rightSide;
}
