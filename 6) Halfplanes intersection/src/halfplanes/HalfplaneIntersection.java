package halfplanes;

import java.util.ArrayList;
import ru.hse.se.primitives.*;

/**
 * Calculates the intersection of halfplanes
 * using different algorithms.
 * 
 * @author Mikhail Dubov
 */
public class HalfplaneIntersection {
    
    /**
     * Calculates the intersection of halfplanes
     * using a naive method in O(n^2).
     */
    public static Polygon intersectHalfplanes_Naive
                            (ArrayList<Halfplane> halfplanes) {
        
        // Separating the halfplanes into the left
        // and into the right ones (O(n))
        
        ArrayList<Halfplane> left = new ArrayList<Halfplane>();
        ArrayList<Halfplane> right = new ArrayList<Halfplane>();
        
        for (Halfplane h : halfplanes) {
            if (h.isLeftBoundary()) {
                left.add(h);
            } else {
                right.add(h);
            }
        }
        
        // Constructing the convex left and right chains
        ArrayList<Halfplane> convLeft = new ArrayList<Halfplane>();
        ArrayList<Halfplane> convRight = new ArrayList<Halfplane>();
        
        // ** LEFT CHAIN **
        
        if (! left.isEmpty()) {
            convLeft.add(left.get(0));
            left.remove(0);
        }
        
        for (Halfplane h : left) {
            
            // Searching for the insertion position by the angle
            int insertPos = 0;
            while (insertPos < convLeft.size() &&
                    h.getLine().getAngle() >
                    convLeft.get(insertPos).getLine().getAngle()) {
                insertPos++;
            }
            
            // 1. One of boundary angles => add
            if (insertPos == 0) {
                while(convLeft.size() > 1 && h.getLine().isLeftPoint (Line.intersection(
                        convLeft.get(0).getLine(),
                        convLeft.get(1).getLine()))) {
                    convLeft.remove(0);
                }
                convLeft.add(insertPos, h);
            } else if (insertPos == convLeft.size()) {
                while(convLeft.size() > 1 && h.getLine().isLeftPoint (Line.intersection(
                        convLeft.get(convLeft.size()-1).getLine(),
                        convLeft.get(convLeft.size()-2).getLine()))) {
                    convLeft.remove(convLeft.size()-1);
                    insertPos--;
                }
                convLeft.add(insertPos, h);
            }
            
            // 2. Add, only if there are some vertices to the left.
            //    Some edges may be deleted.
            else {
                                
                int vertices = 0;
                int i = 0;
                boolean intersection1, intersection2;
                while (i < convLeft.size() - 1) {
                    intersection1 = intersection2 = false;
                    
                    if (h.getLine().isLeftPoint (Line.intersection(
                        convLeft.get(i).getLine(),
                        convLeft.get(i+1).getLine()))) {
                        
                        vertices ++;
                        intersection1 = true;
                    }
                    
                    if (i < convLeft.size() - 2 &&
                        h.getLine().isLeftPoint (Line.intersection(
                        convLeft.get(i+1).getLine(),
                        convLeft.get(i+2).getLine()))) {
                        
                        vertices ++;
                        intersection2 = true;
                    }
                    
                    if (intersection1 && intersection2) {
                        convLeft.remove(i+1);
                        if (i+1 < insertPos) {
                            insertPos--;
                        }
                    } else {
                        i++;
                    }
                }
                
                if (vertices > 0) {
                    convLeft.add(insertPos, h);
                }
            }
        }
        
        // ** RIGHT CHAIN **
        
        if (! right.isEmpty()) {
            convRight.add(right.get(0));
            right.remove(0);
        }
        
        for (Halfplane h : right) {
            
            // Searching for the insertion position by the angle
            int insertPos = 0;
            while (insertPos < convRight.size() &&
                    h.getLine().getAngle() <
                    convRight.get(insertPos).getLine().getAngle()) {
                insertPos++;
            }
            
            // 1. One of boundary angles => add
            if (insertPos == 0) {
                while(convRight.size() > 1 && h.getLine().isRightPoint (Line.intersection(
                        convRight.get(0).getLine(),
                        convRight.get(1).getLine()))) {
                    convRight.remove(0);
                }
                convRight.add(insertPos, h);
            } else if (insertPos == convRight.size()) {
                while(convRight.size() > 1 && h.getLine().isRightPoint (Line.intersection(
                        convRight.get(convRight.size()-1).getLine(),
                        convRight.get(convRight.size()-2).getLine()))) {
                    convRight.remove(convRight.size()-1);
                    insertPos--;
                }
                convRight.add(insertPos, h);
            }
            
            // 2. Add, only if there are some vertices to the right.
            //    Some edges may be deleted.
            else {
                                
                int vertices = 0;
                int i = 0;
                boolean intersection1, intersection2;
                while (i < convRight.size() - 1) {
                    intersection1 = intersection2 = false;
                    
                    if (h.getLine().isRightPoint (Line.intersection(
                        convRight.get(i).getLine(),
                        convRight.get(i+1).getLine()))) {
                        
                        vertices ++;
                        intersection1 = true;
                    }
                    
                    if (i < convRight.size() - 2 &&
                        h.getLine().isRightPoint (Line.intersection(
                        convRight.get(i+1).getLine(),
                        convRight.get(i+2).getLine()))) {
                        
                        vertices ++;
                        intersection2 = true;
                    }
                    
                    if (intersection1 && intersection2) {
                        convRight.remove(i+1);
                        if (i+1 < insertPos) {
                            insertPos--;
                        }
                    } else {
                        i++;
                    }
                }
                
                if (vertices > 0) {
                    convRight.add(insertPos, h);
                }
            }
        }
        
        // Building the final chains
        ArrayList<Halfplane> resLeft = new ArrayList<Halfplane>();
        ArrayList<Halfplane> resRight = new ArrayList<Halfplane>();
        
        
        int startLeft = 0, endLeft = convLeft.size() - 1,
            startRight = 0, endRight = convRight.size() - 1;
        Point intersection;
        
        for (int i = 0; i < convLeft.size(); i++) {
            for (int j = 0; j < convRight.size(); j++) {
                
                if (((j == 0) || convLeft.get(i).getLine().isLeftPoint
                        (Line.intersection(convRight.get(j-1).getLine(), convRight.get(j).getLine()))) &&
                    ((j == convRight.size()-1) || convLeft.get(i).getLine().isRightPoint
                        (Line.intersection(convRight.get(j).getLine(), convRight.get(j+1).getLine()))) &&
                    ((i == 0) || convRight.get(j).getLine().isRightPoint
                        (Line.intersection(convLeft.get(i-1).getLine(), convLeft.get(i).getLine()))) &&
                    ((i == convLeft.size()-1) || convRight.get(j).getLine().isLeftPoint
                        (Line.intersection(convLeft.get(i).getLine(), convLeft.get(i+1).getLine())))) {
                    
                    startLeft = i;
                    startRight = j;
                    
                } else if (((j == 0) || convLeft.get(i).getLine().isRightPoint
                        (Line.intersection(convRight.get(j-1).getLine(), convRight.get(j).getLine()))) &&
                    ((j == convRight.size()-1) || convLeft.get(i).getLine().isLeftPoint
                        (Line.intersection(convRight.get(j).getLine(), convRight.get(j+1).getLine()))) &&
                    ((i == 0) || convRight.get(j).getLine().isLeftPoint
                        (Line.intersection(convLeft.get(i-1).getLine(), convLeft.get(i).getLine()))) &&
                    ((i == convLeft.size()-1) || convRight.get(j).getLine().isRightPoint
                        (Line.intersection(convLeft.get(i).getLine(), convLeft.get(i+1).getLine())))) {
                    
                    endLeft = i;
                    endRight = j;
                }
            }
        }
        
        for (int i = startLeft; i <= endLeft; i++) {
            resLeft.add(convLeft.get(i));
        }
        for (int j = startRight; j <= endRight; j++) {
            resRight.add(convRight.get(j));
        }
        
        Polygon res = createPolygonFromHalfplaneChains(resLeft, resRight);
        
        return res;        
    }
    
    /**
     * Calculates the intersection of halfplanes in O(n*lg(n))
     * 
     * See "Computational Geometry: Algorithms and Applications"
     * by Mark de Berg et al. for details (chapter 4).
     */
    /*public static Polygon intersectHalfplanes_DivideAndConquer
                            (ArrayList<Halfplane> halfplanes) {
        
        // TODO: add "boundary halfplanes" parameter?
        
        ConvexRegion C = _intersectHalfplanes_DivideAndConquer(halfplanes);
        
        // Building up the resulting polygon
        // from the left and from the right chain
        Polygon res = createPolygonFromHalfplaneChains(C.getLeft(), C.getRight());
        
        return res;
    }*/
    
    /**
     * Recursive procedure for the divide and conquer
     * halfplanes intersection algorithm.
     */
    /*private static ConvexRegion _intersectHalfplanes_DivideAndConquer
                            (ArrayList<Halfplane> halfplanes) {
        
        // Recursion base
        if (halfplanes.size() == 1) {
            return new ConvexRegion(halfplanes.get(0));
        }
        
        // Divide ... 
        ArrayList<Halfplane> h1 = new ArrayList<Halfplane>();
        ArrayList<Halfplane> h2 = new ArrayList<Halfplane>();
        
        for (int i = 0; i < halfplanes.size()/2; i++) {
            h1.add(halfplanes.get(i));
        }
        for (int i = halfplanes.size()/2; i < halfplanes.size(); i++) {
            h2.add(halfplanes.get(i));
        }
        System.out.println("Before: " + h1.size() + " " + h2.size());
        
        // ... and conquer,
        ConvexRegion c1 = _intersectHalfplanes_DivideAndConquer(h1);
        ConvexRegion c2 = _intersectHalfplanes_DivideAndConquer(h2);
        
        System.out.println("After: " + c1.getLeft().size()+"+"+c1.getRight().size() + " " + c2.getLeft().size()+"+"+c2.getRight().size());
        
        // merge solutions:
        ConvexRegion c  = ConvexRegion.intersectConvexRegions(c1, c2);
        System.out.println("Merged: " + c.getLeft().size()+"+"+c.getRight().size());
        System.out.println();
        
        return c;
    }*/
    
    private static Polygon createPolygonFromHalfplaneChains
                    (ArrayList<Halfplane> left, ArrayList<Halfplane> right) {
        
        Polygon res = new Polygon();
        
        for(int i = 0; i < left.size() - 1; i++) {
            res.add(Line.intersection(left.get(i).getLine(),
                                      left.get(i+1).getLine()));
        }
        
        if (! left.isEmpty() && ! right.isEmpty()) {
            Point bottom = Line.intersection(left.get(left.size()-1).getLine(),
                                             right.get(right.size()-1).getLine());

            Point lowestExistent = new Point(0, Double.MAX_VALUE);
            if (left.size() > 1) {
                lowestExistent = Line.intersection(left.get(left.size()-1).getLine(),
                                             left.get(left.size()-2).getLine());
            }
            if (right.size() > 1) {
                Point temp = Line.intersection(right.get(right.size()-1).getLine(),
                                             right.get(right.size()-2).getLine());
                if (temp.getY() < lowestExistent.getY()) {
                    lowestExistent = temp;
                }
            }
            System.out.println(bottom + " - "+lowestExistent);
            if (! bottom.isNaP() && bottom.getY() < lowestExistent.getY()) {
                res.add(bottom);
            }
        }
        
        for(int i = right.size()-1; i > 0; i--) {
            res.add(Line.intersection(right.get(i).getLine(),
                                      right.get(i-1).getLine()));
        }
        
        
        if (! left.isEmpty() && ! right.isEmpty()) {
            Point upper = Line.intersection(left.get(0).getLine(),
                                             right.get(0).getLine());

            Point highestExistent = new Point(0, Double.MIN_VALUE);
            if (left.size() > 1) {
                highestExistent = Line.intersection(left.get(0).getLine(),
                                             left.get(1).getLine());
            }
            if (right.size() > 1) {
                Point temp = Line.intersection(right.get(0).getLine(),
                                             right.get(1).getLine());
                if (temp.getY() > highestExistent.getY()) {
                    highestExistent = temp;
                }
            }
            System.out.println(upper + " - "+highestExistent);
            if (! upper.isNaP() && upper.getY() > highestExistent.getY()) {
                res.add(upper);
            }
        }
        return res;
    }
    
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }
}
