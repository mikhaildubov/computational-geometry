package ru.dubov.convexhull;

import java.util.*;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Polygon;

/**
 * Builds the convex hull of a given set of points
 * using different algorithms.
 * 
 * @author Mikhail Dubov
 */
public class ConvexHull {
    
    /**
     * Builds the convex hull of a given set of points
     * using the Graham's scan algorithm - O(n*log(n)).
     */ 
    public static Polygon Graham(ArrayList<Point> pointsList) {

        // Clone the list of points as it will be changed during the algorithm.
        // We assume that pointsList.size >= 3.
        
        ArrayList<Point> points = (ArrayList<Point>)pointsList.clone();
        Stack<Point> result = new Stack<Point>();

        // Get the lowest most left point - the first vertex of the convex hull - in O(n)
        
        Point p0 = getLowestPoint(points);
        result.push(p0);
        points.remove(p0);
        
        // Sort the rest of the points by their polar angle - in O(n*log(n))
        
        Collections.sort(points, new PointComparator(p0));
        
        // Add two more points to the stack before starting the Graham's scan
        
        Iterator<Point> iter = points.iterator();
        
        Point p1 = iter.next();
        result.push(p1);
        
        Point p2 = iter.next();
        result.push(p2);
        
        // Traverse the sorted point list, determining which points
        // should go to the convex hull - in O(n)
        
        while (iter.hasNext()) {
            Point pi = iter.next();
            Point top = result.elementAt(result.size() - 1);
            Point nextToTop = result.elementAt(result.size() - 2);
            
            while (! isLeftTurn(nextToTop, top, pi)) {
                result.pop();
                
                // Update the "top" vertex
                top = nextToTop;
                nextToTop = result.elementAt(result.size() - 2);
            }
            
            result.push(pi);
        }

        // The convex hull has been constructed, now return the list
        // of its vertices in CCW order
        
        ArrayList<Point> polygon = new ArrayList<Point>();
        while(! result.empty()) {
            polygon.add(result.pop());
        }
        Collections.reverse(polygon);
        
        return new Polygon(polygon);
    }
    
    /**
     * Builds the convex hull of a given set of points
     * using the Jarvis' march algorithm - O(n*h),
     * where h is the number of vertises in the hull,
     * so the worst case is O(n^2).
     */
    public static Polygon Jarvis(ArrayList<Point> pointsList) {

        // Clone the list of points as it will be changed during the algorithm.
        // We assume that pointsList.size >= 3.
        
        ArrayList<Point> points = (ArrayList<Point>)pointsList.clone();
        ArrayList<Point> result = new ArrayList<Point>();

        // Get the lowest most left point - the first vertex of the convex hull - in O(n)
        
        Point p0 = getLowestPoint(points);
        result.add(p0);
        points.remove(p0);

        // Get the last vertex of the convex hull - in O(n)
        
        Point pE = points.get(0);
        Point candidate;
        for(int i = 1; i < points.size(); i++) {
            candidate = points.get(i);
            if(crossProduct(p0, candidate, pE) < 0) {
                pE = candidate;
            }
        }
        
        // Construct the chain - O(h) steps...
        
        Point next = null;
        Point last = p0;
        do {
            // ... with each step scanning O(n) points,
            // so O(n*h) operations in total
            next = points.get(0);
            
            for(int i = 1; i < points.size(); i++) {
                candidate = points.get(i);
                if (crossProduct(last, candidate, next) > 0 ||
                        crossProduct(last, candidate, next) == 0 &&
                        last.dist(candidate) > last.dist(next)) {
                    next = candidate;
                }
            }
            
            result.add(next);
            points.remove(next);
            last = next;
            
        } while(next != pE);

        // The convex hull has been constructed, now return the list
        // of its vertices in CCW order
        
        return new Polygon(result);
    }
    
    /**
     * Compares two points by their polar angles
     * using the cross product.
     */
    static class PointComparator implements Comparator<Point> {
        
        private Point p0;
        
        public PointComparator(Point p0) {
            this.p0 = p0;
        }

        // Compare two points by their polar angle with respect to p0;
        // in case the angle is the same, the distance to p0 is used
        @Override
        public int compare(Point p1, Point p2) {
            
            double crossProduct = crossProduct(p0, p1, p2);
            
            if (crossProduct > 0) return -1;
            if (crossProduct < 0) return 1;

            // cross_product = 0 => the vectors are collinear, and the vertex
            // that lies further from p0 should be considered "larger"
            double d1 = p0.dist(p1);
            double d2 = p0.dist(p2);
            
            if (d1 < d2) return -1;
            if (d1 > d2) return 1;
            
            return 0;
        }
    }
    
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }
    
    private static boolean isLeftTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) > 0);
    }
    
    private static Point getLowestPoint(ArrayList<Point> points) {
        Point result = points.get(0);
        
        Point candidate;
        for (int i = 1; i < points.size(); i++) {
            candidate = points.get(i);
            
            if (candidate.getY() < result.getY() ||
                    candidate.getY() == result.getY() && candidate.getX() < result.getX()) {
                result = candidate;
            }
        }
        
        return result;
    }
    
    private static void printPointsList(ArrayList<Point> points) {
        for (Point p : points) {
            System.out.println(p);
        }
    }
}
