/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package convexhull;

/**
 *
 * @author MSDubov
 */
public class Point {
    
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public double getX() {
        return x;
    }
    
    
    public double getY() {
        return y;
    }
    
    public boolean equals(Object b) {
        return (b instanceof Point) &&
                this.getX() == ((Point) b).getX() &&
                this.getY() == ((Point) b).getY();
    }
    
    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }
    
    private double x, y;
}
