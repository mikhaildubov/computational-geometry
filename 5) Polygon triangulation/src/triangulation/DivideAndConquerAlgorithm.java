package triangulation;

import java.util.ArrayList;
import ru.hse.se.primitives.Polygon;
import ru.hse.se.primitives.Triangle;

public class DivideAndConquerAlgorithm {
    
    /**
     * O(n^4) worst case (!)
     */
    public static ArrayList<Triangle> triangulate(Polygon p) {
        
        result = new ArrayList<Triangle>();
        
        isClockwise = p.isClockwise();
        
        divideAndConquer(p);
        
        return result;
    }
    
    private static void divideAndConquer(Polygon p) {
        
        if(p.size() < 3) {
            return;
        }
        if(p.size() == 3) {
            result.add(new Triangle(p.get(0), p.get(1), p.get(2)));
            return;
        }
        
        boolean diag = true;
        int start = 0, finish = 0;
        int prev = 0, next = 0;
        
        // Looking for a diagonal - O(n^3)
        outer: for(int i = 0; i < p.size()-1; i++) {
            for (int j = i+2; j < p.size(); j++) {
                
                if (i == j || Math.abs(i-j) == 1 ||
                   (i == p.size()-1 && j == 0) ||
                   (j == p.size()-1 && i == 0)) {
                    continue; // not even a candidate to be a diagonal
                }
                
                start = i;
                finish = j;
                diag = true;
                
                // Test for intersections
                for (int k = 0; k < p.size(); k++) {
                                    
                    if (i != k && j != k && i != (k+1)%p.size() && j != (k+1)%p.size() &&
                        SegmentsIntersect.segmentsIntersect(p.get(i), p.get(j),
                                            p.get(k), p.get((k+1)%p.size()))) {
                        diag = false; break;
                    }
                }
                
                
                // Test whether it is not an outer diagonal
                // See http://stackoverflow.com/questions/4380479/finding-the-diagonals-of-a-polygon
                
                prev = i - 1;
                if (prev < 0) prev += p.size();
                next = (i + 1) % p.size();
                    
                if (p.isConvex(i)) {
                    if (! (SegmentsIntersect.isRightTurn(p.get(i), p.get(j), p.get(next)) ^ isClockwise &&
                            SegmentsIntersect.isRightTurn(p.get(i), p.get(prev), p.get(j)) ^ isClockwise)) {
                        diag = false;
                    }
                } else {
                    if (! (SegmentsIntersect.isLeftTurn(p.get(i), p.get(j), p.get(prev)) ^ isClockwise ||
                            SegmentsIntersect.isLeftTurn(p.get(i), p.get(next), p.get(j)) ^ isClockwise)) {
                        diag = false;
                    }
                }
                
                if (diag) {
                    break outer;
                }
            }
        }
        
        
        divideAndConquer(p.subPolygon(start, finish));
        divideAndConquer(p.subPolygon(finish, start));
    }
    
    static boolean isClockwise;
    private static ArrayList<Triangle> result;
}
