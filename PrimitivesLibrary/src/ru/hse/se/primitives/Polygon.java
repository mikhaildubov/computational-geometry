package ru.hse.se.primitives;

import java.util.ArrayList;


public class Polygon implements Cloneable {
    
    public Polygon() {
        vertices = new ArrayList<Point>();
    }
    
    public Polygon(ArrayList<Point> vertices) {
        this.vertices = (ArrayList<Point>)vertices.clone();
    }
    
    public void add(Point p) {
        if (p != null) {
            vertices.add(p);
        }
    }
    
    public Object clone() {
        
        Polygon copy = new Polygon();
        
        copy.vertices = (ArrayList<Point>)this.vertices.clone();
        
        return copy;
    }
    
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
    
    public void remove(int i) {
        if (i >= 0 && i < size()) {
            vertices.remove(i);
        }
    }
            
    public int size() {
        return vertices.size();
    }
    
    public Point get(int i) {
        if (i >= 0 && i < size()) {
            return vertices.get(i);
        } else {
            return null;
        }
    }
    
    public boolean isConvex(int i) {
        int prev = i - 1;
        if (prev < 0) prev += size();
        int next = (i + 1) % size();
        
        return (Point.isLeftTurn(get(prev), get(i), get(next))
                ^ isClockwise);
    }
    
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
