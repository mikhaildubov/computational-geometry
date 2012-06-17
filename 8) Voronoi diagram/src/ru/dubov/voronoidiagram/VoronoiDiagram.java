package ru.dubov.voronoidiagram;

import java.util.ArrayList;
import java.util.List;
import ru.dubov.halfplanes.HalfplanesIntersection;
import ru.dubov.primitives.*;

/**
 * Computes Voronoi diagrams for point sets
 * using different algorithms.
 * 
 * @author Mikhail Dubov
 */
public class VoronoiDiagram {
    
    /**
     * Computes the Voronoi diagram for the given point set
     * in a straightforward way using halfplanes intersection.
     * 
     * Runs in O(n^3) (improvable to O(n^2*log(n)),
     * depends on halfplanes intersection algorithm complexity).
     * 
     * @param points Point set
     * @return Voronoi diagram for p
     */
    public static ArrayList<Polygon> viaHalfplanesIntersection(List<Point> points) {
        
        ArrayList<Polygon> res = new ArrayList<Polygon>();
        
        ArrayList<Halfplane> halfplanes;
        Segment p1p2;
        Line p1p2perp;
        
        // Bounding rectangle
        Halfplane left = new Halfplane(new Line(new Point(-10000, 0), new Point(-10000, 1)), true);
        Halfplane upper = new Halfplane(new Line(new Point(0, 10000), new Point(1, 10000)), true);
        Halfplane right = new Halfplane(new Line(new Point(10000, 0), new Point(10000, 1)), false);
        Halfplane bottom = new Halfplane(new Line(new Point(0, -10000), new Point(1, -10000)), false);
        
        // Calculating Voronoi cells for each point
        for (Point p1 : points) {
            
            // Initializing halfplanes set with the bounding ones
            halfplanes = new ArrayList<Halfplane>();
            halfplanes.add(left); halfplanes.add(upper);
            halfplanes.add(right); halfplanes.add(bottom);
            
            // Getting halfplanes
            for (Point p2 : points) {
                
                if (! p1.equals(p2)) {
                    
                    p1p2 = new Segment(p1, p2);
                    p1p2perp = p1p2.getLine().getPerpendicularLine
                                        (p1p2.getBisectionalPoint());
                    
                    if (p2.getX() < p1.getX() ||
                            p2.getX() == p1.getX() &&
                            p2.getY() > p1.getY()) {
                        halfplanes.add(new Halfplane(p1p2perp, true));
                    } else {
                        halfplanes.add(new Halfplane(p1p2perp, false));
                    }
                }
            }
            
            // The halfplanes intersection is a single Voronoi cell
            res.add(HalfplanesIntersection.intersectHalfplanes_Naive(halfplanes));
        }
        
        return res;
    }
}
