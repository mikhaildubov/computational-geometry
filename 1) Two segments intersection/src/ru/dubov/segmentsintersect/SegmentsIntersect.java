/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.dubov.segmentsintersect;


import ru.dubov.primitives.Point;
import ru.dubov.primitives.Segment;

/**
 * Determines whether the two given segments intersect in O(1),
 * as described in "Introduction to Algorithms" by Cormen et al.
 * 
 * @author Mikhail Dubov
 */
public class SegmentsIntersect {
    
    private static double direction(Point p0, Point p1, Point p2) {
        return ((p2.getX() - p0.getX()) * (p1.getY() - p0.getY()) -
                (p2.getY() - p0.getY()) * (p1.getX() - p0.getX()));
    }

    private static boolean onSegment(Point pi, Point pj, Point pk) {
        return (Math.min(pi.getX(), pj.getX()) <= pk.getX() &&
                pk.getX() <= Math.max(pi.getX(), pj.getX()) &&
                Math.min(pi.getY(), pj.getY()) <= pk.getY() &&
                pk.getY() <= Math.max(pi.getY(), pj.getY()));
    }
    
    public static boolean two(Segment s1, Segment s2) {
        
        Point p1 = s1.getLeft();
        Point p2 = s1.getRight();
        Point p3 = s2.getLeft();
        Point p4 = s2.getRight();
        
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);
        
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        } else if (d1 == 0 && onSegment(p3, p4, p1)) {
            return true;
        } else if (d2 == 0 && onSegment(p3, p4, p2)) {
            return true;
        } else if (d3 == 0 && onSegment(p1, p2, p3)) {
            return true;
        } else if (d4 == 0 && onSegment(p1, p2, p4)) {
            return true;
        } else {
            return false;
        }
    }
}
