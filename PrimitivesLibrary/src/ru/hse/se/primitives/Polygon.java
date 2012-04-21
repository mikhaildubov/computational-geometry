package ru.hse.se.primitives;

import java.util.ArrayList;

/**
 * Represents a polygon.
 * 
 * @author Mikhail Dubov
 */
public class Polygon implements Cloneable {
    
    /**
     * Initializes an empty polygon.
     */
    public Polygon() {
        vertices = new ArrayList<Point>();
    }
    
    /**
     * Initializes a polygon by the list of its vertices
     * (either in clockwise or in counterclockwise order).
     * 
     * @param vertices The list of polygon vertices
     */
    public Polygon(ArrayList<Point> vertices) {
        this.vertices = (ArrayList<Point>)vertices.clone();
    }
    
    /**
     * Adds a point to the list of vertices (as the last one).
     * 
     * @param p The added vertex
     */
    public void add(Point p) {
        if (p != null) {
            vertices.add(p);
        }
    }
    
    /**
     * Clones the polygon.
     * 
     * @return The clone
     */
    public Object clone() {
        
        Polygon copy = new Polygon();
        
        copy.vertices = (ArrayList<Point>)this.vertices.clone();
        
        return copy;
    }
    
    /**
     * Creates a subpolygon of the current polygon.
     * 
     * @param start The index of the new "first" vertex in the list of vertices
     * @param end The index of the new "last" vertex in the list of vertices
     * @return The subpolygon
     */
    public Polygon subPolygon(int start, int end) {
        Polygon p = new Polygon();
        if (start < end) {
            for(int i = start; i <= end; i++) {
                p.add(get(i));
            }
        } else if (start > end) {
            for(int i = start; i < size(); i++) {
                p.add(get(i));
            }
            for(int i = 0; i <= end; i++) {
                p.add(get(i));
            }
        }
        
        p.isClockwise = this.isClockwise;
        return p;
    }
    
    /**
     * Removes a point from the list of vertices.
     * 
     * @param i The index of the removed point.
     */
    public void remove(int i) {
        if (i >= 0 && i < size()) {
            vertices.remove(i);
        }
    }
            
    /**
     * Returns the number of vertices in the polygon.
     * 
     * @return The number of vertices
     */
    public int size() {
        return vertices.size();
    }
    
    /**
     * Determines whether the list of polygon vertices is empty.
     * 
     * @return true, if there are no vertices in the list, false otherwise
     */
    public boolean isEmpty() {
        return vertices.isEmpty();
    }
    
    /**
     * Returns the ith vertex of the polygon.
     * 
     * @param i The index of the vertex
     * @return The vertex
     */
    public Point get(int i) {
        if (i >= 0 && i < size()) {
            return vertices.get(i);
        } else {
            return null;
        }
    }
    
    /**
     * Determines whether the vertex is convex
     * (and makes an "ear" of the polygon).
     * 
     * @param i The index of the vertex
     * @return true, if the vertex is convex, false otherwise
     */
    public boolean isConvex(int i) {
        int prev = i - 1;
        if (prev < 0) prev += size();
        int next = (i + 1) % size();
        
        return (Point.isLeftTurn(get(prev), get(i), get(next))
                ^ isClockwise);
    }
    
    /**
     * Determines whether the polygon is convex.
     * 
     * @return true, if the polygon is convex, false otherwise
     */
    public boolean isConvex() {
        
        for (int i = 0; i < size(); i++) {
            if (! isConvex(i)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Determines, whether the order of vertices in the list is clockwise.
     * 
     * @return true, if the order is clockwise, false if counterclockwise
     */
    public boolean isClockwise() {
        double sum = 0;
        
        for (int i = 0; i < size(); i++) {
            sum += (vertices.get((i+1)%size()).getX() - vertices.get(i).getX())*
                    (vertices.get((i+1)%size()).getY() + vertices.get(i).getY());
        }
        
        isClockwise = (sum > 0);
        return isClockwise;
    }
    
    private ArrayList<Point> vertices;
    private boolean isClockwise;
}
