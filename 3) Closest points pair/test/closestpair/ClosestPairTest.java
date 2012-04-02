package closestpair;

import java.util.ArrayList;
import java.util.Random;
import junit.framework.TestCase;
import ru.hse.se.primitives.Point;


public class ClosestPairTest extends TestCase {
    
    private void checkClosestPair(ArrayList points) {
        ArrayList<Point> resNaive = ClosestPair.Naive(points);
        ArrayList<Point> resFast = ClosestPair.Fast(points);
        
        if(resNaive.size() != 2) {
            fail("Naive method returns not a pair of points.");
        }
        if(resFast.size() != 2) {
            fail("Fast method returns not a pair of points.");
        }
        
        // Используем расстояния, а не конкретные точки: может быть несколько пар!
        if (ClosestPair.dist(resNaive.get(0), resNaive.get(1)) !=
            ClosestPair.dist(resFast.get(0), resFast.get(1))) {
            fail("Pairs returned by the naive and fast methods are different.");
        }
        
        assertTrue(true);
    }

    
    public void testClosestPair_Example() {
        // Boundary case - X coordinates are equal
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(100, 100));
        points.add(new Point(100, 102));
        points.add(new Point(100, 103));
        points.add(new Point(100, 105));
        points.add(new Point(100, 107));
        
        checkClosestPair(points);
        
        // Boundary case - Y coordinates are equal
        points = new ArrayList();
        points.add(new Point(100, 100));
        points.add(new Point(102, 100));
        points.add(new Point(103, 100));
        points.add(new Point(105, 100));
        points.add(new Point(107, 100));
        
        checkClosestPair(points);
        
    }

    public void testClosestPair_Randomised() {
        Random rand = new Random();
        
        for (int n = 3; n <= 100; n++) {
            ArrayList<Point> points = new ArrayList<Point>();
            
            for(int i = 0; i < n; i++) {
                points.add(new Point(rand.nextDouble()*100,
                                     rand.nextDouble()*100));
            }
        
            checkClosestPair(points);
        }
    }
}
