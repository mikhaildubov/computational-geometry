package delaunay;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;
import ru.hse.se.primitives.Point;
import ru.hse.se.primitives.Triangle;


public class PointsTriangulation {
    
    /**
     * Computes some [bad] points triangulation to be used
     * in the brute force edge flipping algorithm.
     * 
     * @return Triangulation
     */
    public static ArrayList<Triangle> some(List<Point> points) {
        
        Point p0 = getLowestPoint(points);
        points.remove(p0);
        
        Collections.sort(points, new PointComparator(p0));
        
        ArrayList<Triangle> result = new ArrayList<Triangle>();
        
        Stack<Point> S = new Stack<Point>();
        S.push(p0);
        S.push(points.get(0));
        
        Point top, nextToTop;
        Triangle lastAdded = null, temp = null,
                 lastAddedInStack = null, tempInStack = null;
        
        for (int i = 0; i < points.size(); i++) {
            
            if (i < points.size() - 1) {
                
                // Adding new "narrow" triangle with p0 as one of its vertices
                temp = new Triangle(p0, points.get(i), points.get(i+1));
                
                 // Linking the triangles (!)
                temp.link(lastAdded);
                
                result.add(temp);
                lastAdded = temp;
            }
            
            if (i > 0) {
                
                // using the idea of Graham's scan
                // to make the triangulation "convex" (!)
                
                top = S.peek();
                nextToTop = S.elementAt(S.size() - 2);
                
                while (isRightTurn(nextToTop, top, points.get(i))) {
                    
                    tempInStack = new Triangle(nextToTop, top, points.get(i));
                    
                    // Linking the triangles (!)
                    tempInStack.link(lastAddedInStack);
                    int k = 0;
                    for (int j = result.size()-1; j >= 0; j--) {
                        if (tempInStack.link(result.get(j))) {
                            k++;
                        }
                        if (k == 2) {
                            break;
                        }
                    }
                    
                    result.add(tempInStack);
                    lastAddedInStack = tempInStack;
                    
                    S.pop();
                    
                    top = nextToTop;
                    nextToTop = S.elementAt(S.size() - 2);                    
                }
                
                S.push(points.get(i));
            }
        }
        
        return result;
    }
    
    /**
     * Compares two points by their polar angles
     * using the cross product.
     */
    static class PointComparator implements Comparator<Point> {
        
        private Point p0;
        
        public PointComparator(Point p0) {
            this.p0 = p0;
        }
        
        // Сравнение точек по их полярному углу относительно p0;
        // если совпадает - то по удаленности от p0.
        @Override
        public int compare(Point p1, Point p2) {
            
            double crossProduct = crossProduct(p0, p1, p2);
            
            if (crossProduct > 0) return -1;
            if (crossProduct < 0) return 1;
            
            // cross_product = 0, векторы коллинеарны -> нужен тот, что дальше
            double d1 = p0.dist(p1);
            double d2 = p0.dist(p2);
            
            if (d1 < d2) return -1;
            if (d1 > d2) return 1;
            
            return 0;
        }
    }
    
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }
    
    private static boolean isRightTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) < 0);
    }
    
    private static Point getLowestPoint(List<Point> points) {
        Point result = points.get(0);
        
        Point candidate;
        for (int i = 1; i < points.size(); i++) {
            candidate = points.get(i);
            
            if (candidate.getY() < result.getY() ||
                    candidate.getY() == result.getY() && candidate.getX() < result.getX()) {
                result = candidate;
            }
        }
        
        return result;
    }
}
