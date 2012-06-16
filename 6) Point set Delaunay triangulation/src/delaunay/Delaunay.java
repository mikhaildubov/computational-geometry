package delaunay;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ru.hse.se.primitives.*;

/**
 * Computes the Delaunay triangulations
 * of planar point sets using different
 * algorithms.
 * 
 * @author Mikhail Dubov
 */
public class Delaunay {
    
    /**
     * Computes the Delaunay triangulation of a point set
     * using a randomized incremental algorithm.
     * 
     * Expected running time is O(n*log(n)).
     * 
     * See the "DelaunayTriangulation" algorithm
     * in [deBerg] (section 9.3) for details.
     * 
     * @param p Set of points
     * @return Delaunay triangulation
     */
    public static ArrayList<Triangle> randomizedIncremental(List<Point> p) {
        
        // The triangle that embraces the set of points (p0 p_1 p_2)
        Triangle initTriangle = embracingTriangle(p);
        p.remove(initTriangle.getB());
        
        // Init the DAG
        TriangulationDAG triangulationDAG = new TriangulationDAG();
        triangulationDAG.init(initTriangle);
        
        // Randomized ...
        p = randomShuffle(p);
        
        // ... and incremental
        Point pI;
        Triangle tI, tI2, tAB, tBC, tAC, tI11, tI12, tI21, tI22;
        Triangle.Side s1, s2, s11, s12, s21, s22;
        while (! p.isEmpty()) {
            
            pI = p.get(0);
            p.remove(0);
            
            tI = triangulationDAG.locate(pI);
            
            if (tI.pointInTheInterior(pI)) {
                
                // Splitting into triangles
                tAB = new Triangle(tI.getA(), tI.getB(), pI);
                tBC = new Triangle(tI.getB(), tI.getC(), pI);
                tAC = new Triangle(tI.getC(), tI.getA(), pI);
                
                // Linking triangles
                tAB.link(tBC);
                tAB.link(tAC);
                tBC.link(tAC);
                tAB.link(tI.getAdjacent(Triangle.Side.AB));
                tBC.link(tI.getAdjacent(Triangle.Side.BC));
                tAC.link(tI.getAdjacent(Triangle.Side.AC));
                
                // Augmenting the DAG
                ((TriangulationDAG.Node)tI.getTag()).addChild(tAB);
                ((TriangulationDAG.Node)tI.getTag()).addChild(tBC);
                ((TriangulationDAG.Node)tI.getTag()).addChild(tAC);
                
                // Legalizing edges (careful with sides!)
                legalizeEdge(pI, tAB, Triangle.Side.AB);
                legalizeEdge(pI, tBC, Triangle.Side.AB);
                legalizeEdge(pI, tAC, Triangle.Side.AB);
                
            } else {
                
                // The adjacent triangle
                s1 = tI.getPointSide(pI);
                tI2 = tI.getAdjacent(s1);
                s2 = tI2.getPointSide(pI);
                
                tI11 = tI12 = tI21 = tI22 = null;
                s11 = s12 = s21 = s22 = null;
                
                // Splitting into triangles
                switch(s1) {
                    case AB:
                        tI11 = new Triangle(tI.getC(), tI.getA(), pI);
                        tI12 = new Triangle(tI.getB(), tI.getC(), pI);
                        s11 = Triangle.Side.AC;
                        s12 = Triangle.Side.BC;
                        break;
                    case BC:
                        tI11 = new Triangle(tI.getA(), tI.getB(), pI);
                        tI12 = new Triangle(tI.getC(), tI.getA(), pI);
                        s11 = Triangle.Side.AB;
                        s12 = Triangle.Side.AC;
                        break;
                    case AC:
                        tI11 = new Triangle(tI.getB(), tI.getC(), pI);
                        tI12 = new Triangle(tI.getA(), tI.getB(), pI);
                        s11 = Triangle.Side.BC;
                        s12 = Triangle.Side.AB;
                        break;
                }
                
                switch(s2) {
                    case AB:
                        tI21 = new Triangle(tI2.getC(), tI2.getA(), pI);
                        tI22 = new Triangle(tI2.getB(), tI2.getC(), pI);
                        s21 = Triangle.Side.AC;
                        s22 = Triangle.Side.BC;
                        break;
                    case BC:
                        tI21 = new Triangle(tI2.getA(), tI2.getB(), pI);
                        tI22 = new Triangle(tI2.getC(), tI2.getA(), pI);
                        s21 = Triangle.Side.AB;
                        s22 = Triangle.Side.AC;
                        break;
                    case AC:
                        tI21 = new Triangle(tI2.getB(), tI2.getC(), pI);
                        tI22 = new Triangle(tI2.getA(), tI2.getB(), pI);
                        s21 = Triangle.Side.BC;
                        s22 = Triangle.Side.AB;
                        break;
                }
                
                // Linking triangles
                tI11.link(tI.getAdjacent(s11));
                tI12.link(tI.getAdjacent(s12));
                tI21.link(tI.getAdjacent(s21));
                tI22.link(tI.getAdjacent(s22));
                tI11.link(tI12);
                tI21.link(tI22);
                tI11.link(tI21); tI11.link(tI22);
                tI12.link(tI21); tI12.link(tI22);
                
                // Augmenting the DAG
                ((TriangulationDAG.Node)tI.getTag()).addChild(tI11);
                ((TriangulationDAG.Node)tI.getTag()).addChild(tI12);
                ((TriangulationDAG.Node)tI2.getTag()).addChild(tI21);
                ((TriangulationDAG.Node)tI2.getTag()).addChild(tI22);
                
                // Legalizing edges
                legalizeEdge(pI, tI11, s11);
                legalizeEdge(pI, tI12, s12);
                legalizeEdge(pI, tI21, s21);
                legalizeEdge(pI, tI22, s22);
            }
        }
        
        ArrayList<Triangle> res = triangulationDAG.getTriangulation();
        
        // Remove p_0, p_1, p_2
        int i = 0;
        while (i < res.size()) {
            if (res.get(i).containsVertex(initTriangle.getA()) ||
                res.get(i).containsVertex(initTriangle.getB()) ||
                res.get(i).containsVertex(initTriangle.getC())) {
                res.remove(i);
                i--;
            }
            i++;
        }
        
        return res;
    }
    
    /**
     * Computes the Delaunay triangulation of a point set
     * by constructing some triangulation for that set
     * and modifying it by so-called "edge flipping".
     * This is a slow approach, but it is guaranteed to be finite.
     * 
     * See the "LegalTriangulation" algorithm
     * in [deBerg] (section 9.1) for details.
     * 
     * @param p Set of points
     * @return Delaunay triangulation
     */
    public static ArrayList<Triangle> bruteForce(List<Point> p) {
        
        ArrayList<Triangle> triangulation = PointsTriangulation.some(p);
        
        boolean illegalEdges = true;
        
        // Brute force
        while(illegalEdges) {
            
            illegalEdges = false; // assume it is the last step
            
            Triangle t2;
            for (Triangle t1: triangulation) {
                // tag <==> visited
                for (Triangle.Side s1 : Triangle.Side.values()) {
                    
                    if (t1.getAdjacent(s1) != null) {
                        
                        // get the adjacent side of another triangle
                        t2 = t1.getAdjacent(s1);
                        Triangle.Side s2 = t1.getAdjacentSide(s1);
                        
                        // check for legality and flip if needed
                        if (t1.areIllegal(s1, t2, s2)) {
                            illegalEdges = true;
                            t1.flipEdge(s1, t2, s2);
                        }
                    }
                }
            }
        }
        
        return triangulation;
    }
    
    /**
     * A helper procedure that "legalizes" edges.
     * 
     * See the "LegalizeEdge" algorithm
     * in [deBerg] (section 9.3) for details.
     * 
     * @param pI The poin just inserted
     * @param t1 The current triangle
     * @param along the edge that may have to be legalized
     */
    private static void legalizeEdge(Point pI, Triangle t1, Triangle.Side along) {
        
        Triangle t2 = t1.getAdjacent(along);
        Triangle.Side s2 = t1.getAdjacentSide(along);
        
        if (t2 != null && s2 != null && t1.areIllegal(along, t2, s2)) {
            
            // New nodes for DAG            
            Triangle oldT1 = (Triangle)t1.clone();
            Triangle oldT2 = (Triangle)t2.clone();

            // Flip edge
            t1.flipEdge(along, t2, s2);
            
            // Updating the DAG
            TriangulationDAG.Node ont1 = new TriangulationDAG.Node(oldT1);
            TriangulationDAG.Node ont2 = new TriangulationDAG.Node(oldT2);
            TriangulationDAG.Node nt1 = (TriangulationDAG.Node)t1.getTag();
            TriangulationDAG.Node nt2 = (TriangulationDAG.Node)t2.getTag();
            TriangulationDAG.Node parent10 = nt1.getParent(0);
            TriangulationDAG.Node parent11 = nt1.getParent(1);
            if (parent10 != null) {
                parent10.removeChild(nt1);
                parent10.addChild(ont1);
            }
            if (parent11 != null) {
                parent11.removeChild(nt1);
                parent11.addChild(ont1);
            }
            TriangulationDAG.Node parent20 = nt2.getParent(0);
            TriangulationDAG.Node parent21 = nt2.getParent(1);
            if (parent20 != null) {
                parent20.removeChild(nt2);
                parent20.addChild(ont2);
            }
            if (parent21 != null) {
                parent21.removeChild(nt2);
                parent21.addChild(ont2);
            }
            ont1.addChild(nt1);
            ont1.addChild(nt2);
            ont2.addChild(nt1);
            ont2.addChild(nt2);
            
            // Recursive calls
            for (Triangle.Side sd : Triangle.Side.values()) {
                if (! t1.getSideLine(sd).pointOnLine(pI)) {
                    legalizeEdge(pI, t1, sd);
                }
            }
            for (Triangle.Side sd : Triangle.Side.values()) {
                if (! t2.getSideLine(sd).pointOnLine(pI)) {
                    legalizeEdge(pI, t2, sd);
                }
            }
        }
    }
    
    /**
     * Forms and embracing triangle for a point set.
     * 
     * @param p Set of points
     * @return The embracing triangle
     */
    private static Triangle embracingTriangle(List<Point> p) {
        
        Point pTop = p.get(0); // the highest point, equals to p0
        Point pBottom = p.get(0); // the lowest point, used for finding p_1&p_2
        
        for (int i = 1; i < p.size(); i++) {
            if (p.get(i).getY() > pTop.getY() ||
                    p.get(i).getY() == pTop.getY() &&
                    p.get(i).getX() > pTop.getX()) {
                pTop = p.get(i);
            }
            if (p.get(i).getY() < pBottom.getY() ||
                    p.get(i).getY() == pBottom.getY() &&
                    p.get(i).getX() > pBottom.getX()) {
                pBottom = p.get(i);
            }
        }
        
        Line l_1 = new Line(pTop, new Point(pTop.getX(), pTop.getY()-10));
        Line l_2 = new Line(pTop, new Point(pTop.getX(), pTop.getY()-10));
        Line l_3;

        // Three points to form a big fake triangle
        Point p0 = new Point(pTop.getX(), pTop.getY()+10);
        Point p_1, p_2;
        
        for (int i = 0; i < p.size(); i++) {
            if (! p0.equals(p.get(i))) {
                
                l_3 = new Line(p0, p.get(i));

                if (l_3.getAngle() >= l_1.getAngle()) {
                    l_1 = new Line(p0, new Point(p.get(i).getX()+10, p.get(i).getY()));
                }
                if (l_3.getAngle() <= l_2.getAngle()) {
                    l_2 = new Line(p0, new Point(p.get(i).getX()-10, p.get(i).getY()));
                }
            }
        }
        
        
        p_1 = (l_1.isHorizontal()
                ? new Point(p0.getX()+10, pBottom.getY()-10)
                : new Point(l_1.XforY(pBottom.getY()-10), pBottom.getY()-10));
        
        p_2 = (l_2.isHorizontal()
                ? new Point(p0.getX()-10, pBottom.getY()-10)
                : new Point(l_2.XforY(pBottom.getY()-10), pBottom.getY()-10));
        
        
        // (Counterclockwise order)
        return new Triangle(p_1, p0, p_2);
    }
    
    /**
     * Fisher-Yates random shuffling algorithm
     * (Also known as Knuth shuffling,
     * see algorithm 3.4.2P in [TAOCP II]).
     * 
     * @param <T> ArrayList generic type
     * @param list List that needs to be shuffled
     * @return The shuffled list
     */
    private static <T> ArrayList<T> randomShuffle(List<T> list) {
        
        // Clone the list
        ArrayList<T> res = new ArrayList<T>();
        for (T t : list) {
            res.add(t);
        }
        
        // Algorithm 3.4.2P from [TAOCP II]
        int j = res.size();
        Random r = new Random();
        int k;
        T temp;
        
        do {
            k = (int)(j*r.nextDouble());
            
            temp = res.get(k);
            res.set(k, res.get(j-1));
            res.set(j-1, temp);
            
            j--;
            
        } while(j > 1);
        
        return res;
    }
    
}
