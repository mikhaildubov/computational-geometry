package ru.dubov.delaunay.test;

import ru.dubov.delaunay.Delaunay;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import junit.framework.TestCase;
import ru.dubov.primitives.*;

/**
 * @author Mikhail Dubov
 */
public class DelaunayTest extends TestCase {
    
    public DelaunayTest(String testName) {
        super(testName);
    }

    /**
     * Test of randomizedIncremental algorithm, of class Delaunay.
     * Test the boundary case: a point falls on the line
     */
    public void testRandomizedIncrementalBoundaryCase() {
        
        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(1, 1));
        points.add(new Point(2, 2));
        points.add(new Point(2, 0));
        points.add(new Point(3, 1));
        points.add(new Point(2, 1)); // Falls on the line
        
        ArrayList<Triangle> triang = Delaunay.randomizedIncremental(points);
        
        // NB: Should be tested in the algorithm version
        // that uses no random shuffling
        
        for (Triangle t : triang) {
            System.out.println("("+t.getA()+", "+t.getB()+", "+t.getC()+")");
        }
        
        assertTrue(true);
    }
    
    
    public void testTimeBruteForce() {
        
        Random rand = new Random();
        
        for (int i = 20; i <= 5000; i += 20) {
            
            ArrayList<Point> pts = new ArrayList<Point>();
            
            for(int j = 0; j < i; j++) {
                pts.add(new Point(rand.nextDouble()*10, rand.nextDouble()*10));
            }
            
            double start = Calendar.getInstance().getTimeInMillis();
            
            Delaunay.bruteForce(pts);
            
            System.out.println(Calendar.getInstance().getTimeInMillis()-start);
        }
        
    }
        
    public void testTimeRandomized() {
        
        Random rand = new Random();
        
        for (int i = 5020; i <= 8000; i += 20) {
            
            ArrayList<Point> pts = new ArrayList<Point>();
            
            for(int j = 0; j < i; j++) {
                pts.add(new Point(rand.nextDouble()*10, rand.nextDouble()*10));
            }
            
            double start = Calendar.getInstance().getTimeInMillis();
            
            Delaunay.randomizedIncremental(pts);
            
            System.out.println(Calendar.getInstance().getTimeInMillis()-start);
        }
        
    }
}
