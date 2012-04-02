package halfplanes;

import java.util.ArrayList;


public class HalfplaneIntersection {
    
    /**
     * Calculates the intersection of halfplanes in O(n^2)
     * 
     * @author Mikhail Dubov
     */
    
    public static ConvexRegion intersectHalfplanes_Incremental
                            (ArrayList<Halfplane> halfplanes) {
        return null;
    }
    
    /**
     * Calculates the intersection of halfplanes in O(n*lg(n))
     * 
     * See "Computational Geometry: Algorithms and Applications"
     * by Mark de Berg et al. for details (chapter 4).
     * 
     * @author Mikhail Dubov
     */
    public static ConvexRegion intersectHalfplanes_DivideAndConquer
                            (ArrayList<Halfplane> halfplanes, ArrayList<Halfplane> boundary) {
        
        // Merge halfplanes and boundary
        
        // Make ConvexRegion
        
        // Calculate the output polygon
    }
    
    public static ConvexRegion _intersectHalfplanes_DivideAndConquer
                            (ArrayList<Halfplane> halfplanes) {
        
        // Recursion base
        if (halfplanes.size() == 1) {
            return new ConvexRegion(halfplanes.get(0));
        }
        
        // Divide ... 
        ArrayList<Halfplane> h1 = new ArrayList<Halfplane>();
        ArrayList<Halfplane> h2 = new ArrayList<Halfplane>();
        
        for (int i = 0; i < halfplanes.size()/2; i++) {
            h1.add(halfplanes.get(i));
        }
        for (int i = halfplanes.size()/2; i < halfplanes.size(); i++) {
            h2.add(halfplanes.get(i));
        }
        
        // ... and conquer,
        ConvexRegion c1 = _intersectHalfplanes_DivideAndConquer(h1);
        ConvexRegion c2 = _intersectHalfplanes_DivideAndConquer(h2);
        
        // merge solutions:
        ConvexRegion c  = ConvexRegion.intersectConvexRegions(c1, c2);
        
        return c;
    }
}
