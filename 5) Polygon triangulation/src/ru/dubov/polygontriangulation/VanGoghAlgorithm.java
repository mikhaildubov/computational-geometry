package ru.dubov.polygontriangulation;

import java.util.ArrayList;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Polygon;
import ru.dubov.primitives.Triangle;


public class VanGoghAlgorithm {
    
    /**
     * Calculates the Van Gogh (ear-clipping)
     * triangulation for the given polygon.
     * This implementation runs in O(n^2),
     * as the ears are stored at once in a
     * separate list, that updates each step.
     * 
     * @param p Polygon.
     * @return The array of triangles.
     */
    public static ArrayList<Triangle> fast(Polygon p) {
        
        ArrayList<Triangle> result = new ArrayList<Triangle>();
        
        VanGoghPolygon ap = new VanGoghPolygon(p);
        
        for(int i = 0; i < p.size() - 2; i++) {
            result.add(ap.removeEar());
        }
        
        return result;
    }
    
    /**
     * Calculates the Van Gogh (ear-clipping)
     * triangulation for the given polygon.
     * This implementation runs in O(n^3), 
     * as the algorithm searches each step
     * for an ear, which takes O(n) time.
     * 
     * There is another implementation
     * ensuring the O(n^2) time.
     * 
     * @param p Polygon.
     * @return The array of triangles.
     */
    public static ArrayList<Triangle> slow(Polygon p) {
        
        ArrayList<Triangle> result = new ArrayList<Triangle>();
        
        boolean isClockwise = p.isClockwise();
        
        while (p.size() > 3) {
            
            int li = -1;
            Point l, v, r;
            Triangle tr;
            boolean isEar;
            int tryings = 0;
            
            do {
                tryings ++;
                
                if (tryings >= p.size()) {
                    // Endless loop; bad input
                    return null;
                }
                
                li ++;
                l = p.get(li % p.size());
                v = p.get((li+1) % p.size());
                r = p.get((li+2) % p.size());
                tr = new Triangle(l, v, r);
                
                isEar = Point.isLeftTurn(l, v, r) ^ isClockwise;
                
                if (isEar) { // Further analysis required 
                    for (int i = 0; i < p.size(); i++) { // (there shoud be 
                        if (tr.pointInside(p.get(i))) {  // no points inside)
                            isEar = false;
                            break;
                        }
                    }
                }
                
            } while(! isEar); // Until we've discovered an ear
            
            // Guaranteed that we got an ear here
            
            result.add(tr);
            p.remove((li+1) % p.size());
            
        }
        
        if (p.size() == 3) { // The last triangle
            result.add(new Triangle(p.get(0), p.get(1), p.get(2)));
        }
        
        return result;
    }
}
