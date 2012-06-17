package ru.dubov.anysegmentsintersect.test;

import ru.dubov.anysegmentsintersect.SegmentsIntersect;
import java.util.ArrayList;
import java.util.Random;
import junit.framework.TestCase;
import ru.dubov.anysegmentsintersect.SegmentsIntersect;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Segment;

public class SegmentsIntersectTest extends TestCase {
    
    public void testAnySegmentsIntersect_Prepared() {
        
        ArrayList<Segment> segments;
        
        // Test case 1
        
        segments = new ArrayList<Segment>();
        
        segments.add(new Segment(new Point(0, 0), new Point(1, 0)));
        segments.add(new Segment(new Point(2, 0), new Point(3, 0)));
        segments.add(new Segment(new Point(4, 0), new Point(5, 0)));
        segments.add(new Segment(new Point(4.3, 1), new Point(4.7, -1)));
        segments.add(new Segment(new Point(6, 0), new Point(7, 0)));
        
        assertTrue(SegmentsIntersect.any(segments));
        
        // Test case 2
        
        segments = new ArrayList<Segment>();
        
        segments.add(new Segment(new Point(0, 0), new Point(1, 0)));
        segments.add(new Segment(new Point(2, 0), new Point(3, 0)));
        segments.add(new Segment(new Point(4, 0), new Point(5, 0)));
        
        assertTrue(! SegmentsIntersect.any(segments));
    }
    
    public void testAnySegmentsIntersect_Boundary() {
        
        ArrayList<Segment> segments;
        
        // Test case 1
        
        segments = new ArrayList<Segment>();
        
        segments.add(new Segment(new Point(0, 0), new Point(2, 1)));
        segments.add(new Segment(new Point(2, 1), new Point(3, 0)));
        segments.add(new Segment(new Point(4, 0), new Point(5, 0)));
        
        assertTrue(SegmentsIntersect.any(segments));
        
        // Test case 2
        
        segments = new ArrayList<Segment>();
        
        segments.add(new Segment(new Point(0, 0), new Point(2, 1)));
        segments.add(new Segment(new Point(2, 2), new Point(3, 0)));
        segments.add(new Segment(new Point(2, 2), new Point(5, 0)));
        
        assertTrue(SegmentsIntersect.any(segments));
    }

    public void testAnySegmentsIntersect_Randomized() {
        
        Random rand = new Random();
        
        for (int repeat = 0; repeat < 1000; repeat++) {
            for (int i = 2; i <= 20; i++) {
                ArrayList<Segment> segments = new ArrayList<Segment>();

                for(int j = 0; j < i; j++) {
                    segments.add(new Segment(new Point(rand.nextDouble()*100, rand.nextDouble()*100),
                                             new Point(rand.nextDouble()*100, rand.nextDouble()*100)));
                }

                assertTrue(SegmentsIntersect.any(segments) ==
                            SegmentsIntersect.any_Naive(segments));
            }
        }
        
    }
}
