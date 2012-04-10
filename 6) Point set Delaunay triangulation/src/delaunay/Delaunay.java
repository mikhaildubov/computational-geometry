package delaunay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import ru.hse.se.primitives.*;

/**
 * Computes the Delaunay triangulations
 * of planar point sets using different
 * algorithms.
 * 
 * @author Mikhail Dubov
 */
public class Delaunay {
    
    /**
     * Computes the Delaunay triangulation of a point set
     * by modifying some bad triangulation by "edge flipping".
     * This is a slow approach, but it is guaranteed to be finite.
     * 
     * See the "LegalTriangulation" algorithm
     * in [deBerg] (section 9.1) for details.
     * 
     * @param p The set of points
     * @return The Delaunay triangulation
     */
    public static ArrayList<Triangle> bruteForce(ArrayList<Point> p) {
        
        ArrayList<Triangle> triangulation = PolygonTriangulation.some(p);
        
        boolean illegalEdges = true;
        
        // Brute force
        while(illegalEdges) {
            
            illegalEdges = false; // assume it is the last step
            
            for (int i = 0; i < triangulation.size() - 1; i++) {
                for (int j = i+1; j < triangulation.size(); j++) {
                    if (areAdjacentAndIllegal(triangulation.get(i),
                                                triangulation.get(j))) {
                        
                        // Another edge to be flipped
                        edgeFlip(triangulation, i, j);
                        illegalEdges = true;
                    }
                }
            }
        }
        
        return triangulation;
    }
    
    /**
     * Defines whether the two triangles have a shared adge
     * and whether this edge is "illegal"
     * 
     * (see [deBerg] (chapter 9) for details)
     */
    private static boolean areAdjacentAndIllegal(Triangle t1, Triangle t2) {
        
        Segment s1, s2;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                s1 = new Segment(t1.getIth(i), t1.getIth((i+1) % 3));
                s2 = new Segment(t2.getIth(j), t2.getIth((j+1) % 3));
                
                if (s1.equals(s2)) {
                    Circle c = new Circle(t1.getA(), t1.getB(), t1.getC());
                    return c.isInside(t2.getIth((j+2) % 3));
                }
            }
        }
        
        return false;
    }
    
    /**
     * Flips the common edge of the a-th and b-th triangles
     * in the triangulation and changes these triangles properly.
     */
    private static void edgeFlip(ArrayList<Triangle> t, int a, int b) {
        
        Segment s1, s2;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                s1 = new Segment(t.get(a).getIth(i), t.get(a).getIth((i+1) % 3));
                s2 = new Segment(t.get(b).getIth(j), t.get(b).getIth((j+1) % 3));
                
                if (s1.equals(s2)) {
                    
                    // Edge flip
                    Point temp = t.get(a).getIth((i+1) % 3);
                    
                    t.set(a, new Triangle(t.get(a).getIth(i),
                                          t.get(a).getIth((i+2) % 3),
                                          t.get(b).getIth((j+2) % 3)));
                    t.set(b, new Triangle(t.get(a).getIth(1),
                                          t.get(a).getIth(2),
                                          temp));
                }
            }
        }
    }
}
