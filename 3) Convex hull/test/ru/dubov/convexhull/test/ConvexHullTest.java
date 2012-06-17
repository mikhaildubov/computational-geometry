package ru.dubov.convexhull.test;

import ru.dubov.convexhull.ConvexHull;
import java.util.ArrayList;
import java.util.Random;
import junit.framework.TestCase;

import ru.dubov.convexhull.ConvexHull;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Polygon;

public class ConvexHullTest extends TestCase {
    
    private void checkConvexHull(ArrayList<Point> points) {
        
        // printPointsList(points); System.out.println();
        
        Polygon CH_Graham = ConvexHull.Graham(points);
        Polygon CH_Jarvis = ConvexHull.Jarvis(points);
        
         //printPointsList(CH_Graham); System.out.println();
         //printPointsList(CH_Jarvis); System.out.println();
         //System.out.println("---------------------");
        
        if (CH_Graham.size() != CH_Jarvis.size()) {
            fail("CH's are of different size!");
        }
        
        for (int i = 0; i < CH_Graham.size(); i++) {
            if(! (CH_Graham.get(i)).equals(CH_Jarvis.get(i))) {
                fail("Different points: " + (CH_Graham.get(i)) +
                        " in Graham and " + (CH_Jarvis.get(i)) +
                        " in Jarvis.");
            }
        }
        
        assertTrue(true);
    }
    
    public void testConvexHull_Example() {
        
        ArrayList<Point> points = new ArrayList<Point>();
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
            ArrayList<Point> points = new ArrayList<Point>();
            
            for(int i = 0; i < n; i++) {
                points.add(new Point(rand.nextDouble()*100,
                                     rand.nextDouble()*100));
            }
        
            checkConvexHull(points);
        }
    }
    
    private static void printPointsList(ArrayList<Point> points) {
        for (Point p : points) {
            System.out.println(p);
        }
    }
}
