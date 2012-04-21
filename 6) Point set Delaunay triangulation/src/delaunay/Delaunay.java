package delaunay;

import java.util.ArrayList;
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
        
        ArrayList<Triangle> triangulation = PointsTriangulation.some(p);
        
        boolean illegalEdges = true;
        
        // Brute force
        while(illegalEdges) {
            
            illegalEdges = false; // assume it is the last step
            
            Triangle t2;
            for (Triangle t1: triangulation) {
                // tag <==> visited
                for (Triangle.Side s1 : Triangle.Side.values()) {
                    
                    if (t1.getAdjacent(s1) != null) {
                        
                        // get the adjacent side of another triangle
                        t2 = t1.getAdjacent(s1);
                        Triangle.Side s2 = null;
                        for (Triangle.Side s : Triangle.Side.values()) {
                            if (t2.isAdjacent(t1, s)) {
                                s2 = s;
                                break;
                            }
                        }
                        
                        // check for legality and flip if needed
                        if (t1.areIllegal(s1, t2, s2)) {
                            illegalEdges = true;
                            t1.flipEdge(s1, t2, s2);
                        }
                    }
                }
            }
        }
        
        return triangulation;
    }
}
