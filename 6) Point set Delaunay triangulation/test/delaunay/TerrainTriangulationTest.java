package delaunay;

import java.util.ArrayList;
import java.util.Random;
import junit.framework.TestCase;
import ru.hse.se.primitives.*;

/**
 *
 * @author MSDubov
 */
public class TerrainTriangulationTest extends TestCase {
    
    public TerrainTriangulationTest(String testName) {
        super(testName);
    }

    
    
    public void testVRMLTerrainTriangulation() {
        Random rand = new Random();
        ArrayList<Point> points = new ArrayList<Point>();

        points.add(new Point(-3, -3));
        points.add(new Point(-3, 3));
        points.add(new Point(3, -3));
        points.add(new Point(3, 3));

        for (int i = 0; i < 1000; i++) {
            points.add(new Point(rand.nextDouble()*6-3, rand.nextDouble()*6-3));
        }

        // Triangulate (Delaunay)
        ArrayList<Triangle> triangulation = Delaunay.randomizedIncremental(points);
    }
}
