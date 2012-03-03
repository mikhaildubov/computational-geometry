package convexhull;

import java.util.ArrayList;
import java.util.Random;
import junit.framework.TestCase;

public class ConvexHullTest extends TestCase {
    
    private void checkConvexHull(ArrayList points) {
        
        // printPointsList(points); System.out.println();
        
        ArrayList CH_Graham = ConvexHull.Graham(points);
        ArrayList CH_Jarvis = ConvexHull.Jarvis(points);
        
         //printPointsList(CH_Graham); System.out.println();
         //printPointsList(CH_Jarvis); System.out.println();
         //System.out.println("---------------------");
        
        if (CH_Graham.size() != CH_Jarvis.size()) {
            fail("CH's are of different size!");
        }
        
        for (int i = 0; i < CH_Graham.size(); i++) {
            if(! ((Point) CH_Graham.get(i)).equals((Point) CH_Jarvis.get(i))) {
                fail("Different points: " + ((Point) CH_Graham.get(i)) +
                        " in Graham and " + ((Point) CH_Jarvis.get(i)) +
                        " in Jarvis.");
            }
        }
        
        assertTrue(true);
    }
    
    public void testConvexHull_Example() {
        
        ArrayList points = new ArrayList();
        points.add(new Point(1, 0));
        points.add(new Point(2, 7));
        points.add(new Point(20, 10)); // !!!
        points.add(new Point(10, 10)); // Граничный случай
        points.add(new Point(0, 10));  // !!!
        points.add(new Point(1, 1));
        points.add(new Point(-5, 5));
        
        checkConvexHull(points);
    }
    
    public void testConvexHull_Randomized() {
        
        Random rand = new Random();
        
        for (int n = 3; n <= 20; n++) {
            ArrayList points = new ArrayList();
            
            for(int i = 0; i < n; i++) {
                points.add(new Point(rand.nextDouble()*100,
                                     rand.nextDouble()*100));
            }
        
            checkConvexHull(points);
        }
    }
    
    private static void printPointsList(ArrayList points) {
        for (Object o : points) {
            System.out.println((Point) o);
        }
    }
}
