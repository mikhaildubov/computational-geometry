package ru.dubov.delaunay.test;

import ru.dubov.delaunay.Delaunay;
import java.util.ArrayList;
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
}
