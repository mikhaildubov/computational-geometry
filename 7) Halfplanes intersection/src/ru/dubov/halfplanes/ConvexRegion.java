package ru.dubov.halfplanes;

import java.util.ArrayList;
import ru.dubov.primitives.*;

/**
 * Represents a convex region.
 * 
 * @author Mikhail Dubov
 */
public class ConvexRegion {
    
    public ConvexRegion() {
        left = new ArrayList<Halfplane>();
        right = new ArrayList<Halfplane>();
    }
    
    /**
     * Initializes the ConvexRegion by one halfplane.
     */
    public ConvexRegion(Halfplane h) {
        this();
        if (h.isLeftBoundary()) {
            left.add(h);
        } else {
            right.add(h);
        }
    }
    
    /**
     * Returns the Y coordinate of the topmost vertex in the region;
     * Double.POSITIVE_INFINITY if there is no top vertex.
     */
    public double topmostVertexY() {
        if (right != null && ! right.isEmpty() &&
                left != null && ! left.isEmpty() &&
                left.get(0).getLine().isAscending() &&
                ! right.get(0).getLine().isAscending()) {
            return Line.intersection(right.get(0).getLine(),
                                        left.get(0).getLine()).getY();
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }
    
    private static double max(double... values) {
        double res = Double.NEGATIVE_INFINITY;
        for (double d : values) {
            if (d > res) res = d;
        }
        return res;
    }
    
    public ArrayList<Halfplane> getLeft() {
        return left;
    }
    
    public ArrayList<Halfplane> getRight() {
        return right;
    }
    
    /**
     * Intersects two convex regions, using a plane sweep algorithm, in O(n).
     * (See [de Berg], section 4.2).
     * 
     * TODO: Handling horizontal lines
     */
    /*public static ConvexRegion intersectConvexRegions(ConvexRegion c1, ConvexRegion c2) {
        
        ConvexRegion C = new ConvexRegion();
        
        System.out.println("!!!!!!!!!! Input: !!!!!!!!!!!!!!");
        for(int i = 0; i < c1.getLeft().size(); i++) {
            System.out.println("C1 left: "+ c1.getLeft().get(i).getLine().getA()+" "+
                    c1.getLeft().get(i).getLine().getB()+" "+c1.getLeft().get(i).getLine().getC());
        }for(int i = 0; i < c1.getRight().size(); i++) {
            System.out.println("C1 right: "+ c1.getRight().get(i).getLine().getA()+" "+
                    c1.getRight().get(i).getLine().getB()+" "+c1.getRight().get(i).getLine().getC());
        }for(int i = 0; i < c2.getLeft().size(); i++) {
            System.out.println("C2 left: "+ c2.getLeft().get(i).getLine().getA()+" "+
                    c2.getLeft().get(i).getLine().getB()+" "+c2.getLeft().get(i).getLine().getC());
        }for(int i = 0; i < c2.getRight().size(); i++) {
            System.out.println("C2 right: "+ c2.getRight().get(i).getLine().getA()+" "+
                    c2.getRight().get(i).getLine().getB()+" "+c2.getRight().get(i).getLine().getC());
        }
        System.out.println("____________________");
        
        
        boolean gotLeftC1 = false,
                gotRightC1 = false,
                gotLeftC2 = false,
                gotRightC2 = false;
        
        double y1 = c1.topmostVertexY(); //System.out.println(y1+"!");
        double y2 = c2.topmostVertexY(); //System.out.println(y2+"!");
        double sweepingLineY = Math.min(y1, y2);
        
        // Iterators initializing
        EdgeIterator leftC1 = new EdgeIterator(c1.left);
        EdgeIterator rightC1 = new EdgeIterator(c1.right);
        EdgeIterator leftC2 = new EdgeIterator(c2.left);
        EdgeIterator rightC2 = new EdgeIterator(c2.right);
        
        if (Double.isInfinite(sweepingLineY)) {
            if (c1.getLeft().size() > 0) {
                gotLeftC1 = true;
            }
            if (c1.getRight().size() > 0) {
                gotRightC1 = true;
            }
            if (c2.getLeft().size() > 0) {
                gotLeftC2 = true;
            }
            if (c2.getRight().size() > 0) {
                gotRightC2 = true;
            }
        } else {
            if (sweepingLineY == y1) {
                gotLeftC1 = gotRightC1 = true;
            }
            if (sweepingLineY == y2) {
                gotLeftC2 = gotRightC2 = true;
            }
        }
        System.out.println(sweepingLineY +": "+gotLeftC1+"|"+gotRightC1+"|"+gotLeftC2+"|"+gotRightC2);
        
        while (sweepingLineY > Double.NEGATIVE_INFINITY) {
            
            // Defining which edge is the next in the "event queue"
            // (See [de Berg], page 69)
            
            if (gotLeftC1) { // handle the left edge of C1 
                System.out.println(" ===== GOT LEFT C1 =====");
                if (Double.isInfinite(leftC1.upperVertexX()) ||
                    (leftC2.currentEdge() == null ||
                     leftC1.upperVertexX() >= leftC2.currentEdge().getLine().XforY(sweepingLineY)) &&
                    (rightC2.currentEdge() == null ||
                     leftC1.upperVertexX() <= rightC2.currentEdge().getLine().XforY(sweepingLineY))) {
                    C.left.add(leftC1.currentEdge());System.out.println("@ added left c1");
                }
                if (leftC1.intersects(rightC2)) {
                    if (rightC2.currentEdge() == null ||
                        leftC1.upperVertexX() >= rightC2.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.left.add(leftC1.currentEdge());System.out.println("# added left c1");
                        C.right.add(rightC2.currentEdge());System.out.println("# added right c2");
                    }
                }
                if (leftC1.intersects(leftC2)) {
                    if (leftC2.currentEdge() == null ||
                        leftC1.upperVertexX() <= leftC2.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.left.add(leftC1.currentEdge());System.out.println("$ added left c1");
                    } else {
                        C.left.add(leftC2.currentEdge());System.out.println("$ added left c2");
                    }
                }
            }
            
            if (gotRightC1) { // handle the right edge of C1 
                System.out.println(" ===== GOT RIGHT C1 =====");
                if (Double.isInfinite(rightC1.upperVertexX()) ||
                    (leftC2.currentEdge() == null ||
                     rightC1.upperVertexX() >= leftC2.currentEdge().getLine().XforY(sweepingLineY)) &&
                    (rightC2.currentEdge() == null ||
                     rightC1.upperVertexX() <= rightC2.currentEdge().getLine().XforY(sweepingLineY))) {
                    C.right.add(rightC1.currentEdge());System.out.println("@ added right c1");
                }
                if (rightC1.intersects(leftC2)) {
                    if (leftC2.currentEdge() == null || 
                        rightC1.upperVertexX() <= leftC2.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.right.add(rightC1.currentEdge());System.out.println("# added right c1");
                        C.left.add(leftC2.currentEdge());System.out.println("# added left c2");
                    }
                }
                if (rightC1.intersects(rightC2)) {
                    if (rightC2.currentEdge() == null ||
                        rightC1.upperVertexX() >= rightC2.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.right.add(rightC1.currentEdge());System.out.println("$ added right c1");
                    } else {
                        C.right.add(rightC2.currentEdge());System.out.println("$ added right c2");
                    }
                }                
            }
            
            if (gotLeftC2) { // handle the left edge of C1 
                System.out.println(" ===== GOT LEFT C2 =====");
                if (Double.isInfinite(leftC2.upperVertexX()) ||
                    (leftC1.currentEdge() == null ||
                     leftC2.upperVertexX() >= leftC1.currentEdge().getLine().XforY(sweepingLineY)) &&
                    (rightC1.currentEdge() == null ||
                     leftC2.upperVertexX() <= rightC1.currentEdge().getLine().XforY(sweepingLineY))) {
                    C.left.add(leftC2.currentEdge());System.out.println("@ added left c2");
                }
                if (leftC2.intersects(rightC1)) {
                    if (rightC1.currentEdge() == null ||
                        leftC2.upperVertexX() >= rightC1.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.left.add(leftC2.currentEdge());System.out.println("# added left c2");
                        C.right.add(rightC1.currentEdge());System.out.println("# added right c1");
                    }
                }
                if (leftC2.intersects(leftC1)) {
                    if (leftC1.currentEdge() == null ||
                        leftC2.upperVertexX() <= leftC1.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.left.add(leftC2.currentEdge());System.out.println("$ added left c2");
                    } else {
                        C.left.add(leftC1.currentEdge());System.out.println("$ added left c2");
                    }
                }
            }
            
            if (gotRightC2) { // handle the right edge of C1 
                System.out.println(" ===== GOT RIGHT C2 =====");
                if (Double.isInfinite(rightC2.upperVertexX()) ||
                    (leftC1.currentEdge() == null ||
                     rightC2.upperVertexX() >= leftC1.currentEdge().getLine().XforY(sweepingLineY)) &&
                    (rightC1.currentEdge() == null ||
                     rightC2.upperVertexX() <= rightC1.currentEdge().getLine().XforY(sweepingLineY))) {
                    C.right.add(rightC2.currentEdge());System.out.println("@ added right c2");
                }
                if (rightC2.intersects(leftC2)) {
                    if (leftC1.currentEdge() == null ||
                        rightC2.upperVertexX() <= leftC1.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.right.add(rightC2.currentEdge());System.out.println("# added right c2");
                        C.left.add(leftC1.currentEdge());System.out.println("# added left c1");
                    }
                }
                if (rightC2.intersects(rightC1)) {
                    if (rightC1.currentEdge() == null ||
                        rightC2.upperVertexX() >= rightC1.currentEdge().getLine().XforY(sweepingLineY)) {
                        C.right.add(rightC2.currentEdge());System.out.println("$ added right c2");
                    } else {
                        C.right.add(rightC1.currentEdge());System.out.println("$ added right c1");
                    }
                }                
            }
            
            
            
            // TODO: Endpoints with the same y-coordinate are handled
            // from left to right. If two endpoints coincide then the
            // leftmost edge is treated first.
            
            // Moving further
            sweepingLineY = max(leftC1.lowerVertexY(), 
                                rightC1.lowerVertexY(),
                                leftC2.lowerVertexY(),
                                rightC2.lowerVertexY());
            
            // Going down to the sweeping line Y coordinate
            gotLeftC1 = leftC1.toY(sweepingLineY);
            gotRightC1 = rightC1.toY(sweepingLineY);
            gotLeftC2 = leftC2.toY(sweepingLineY);
            gotRightC2 = rightC2.toY(sweepingLineY);
            System.out.println("\n"+sweepingLineY +": "+gotLeftC1+"|"+gotRightC1+"|"+gotLeftC2+"|"+gotRightC2);
        }
        
        return C;
    }*/
    
    public Polygon toPolygon() {
        return null;
    }
    
    private ArrayList<Halfplane> left;
    private ArrayList<Halfplane> right;
    
    /**
     * A helper that acts like an iterator over the edges
     * and is used in the plane sweep algorithm.
     */
    static class EdgeIterator {
        
        public EdgeIterator(ArrayList<Halfplane> edges) {
            this.edges = edges;
            lowerVertex = null;
            i = -1;
            
            next();
        }
        
        public void next() {
            i++;
            upperVertex = lowerVertex;
            if (i >= edges.size() - 1) {
                lowerVertex = null;
            } else {
                lowerVertex = Line.intersection(edges.get(i).getLine(),
                                                    edges.get(i+1).getLine());
            }
        }
        
        public boolean intersects(EdgeIterator edge) {
            
            if (edge.currentEdge() == null ||
                this.currentEdge() == null) {
                return false;
            }
            
            Point intersection = Line.intersection(this.currentEdge().getLine(),
                                                   edge.currentEdge().getLine());
            
            if (intersection.isNaP()) {
                return false;
            }
            
            return intersection.getY() <= edge.upperVertexY() &&
                   intersection.getY() >= edge.lowerVertexY() &&
                   intersection.getY() <= this.upperVertexY() &&
                   intersection.getY() >= this.lowerVertexY();
        }
        
        /**
         * Updates the iterator so that the sweeping line is
         * on the required Y coordinate.
         * Returns true, if the current edge has changed;
         * false otherwise. 
         */
        public boolean toY(double Y) {
            boolean change = (lowerVertex != null) && (lowerVertex.getY() == Y);
            
            if(change) {
                next();
            }
            
            return change;
        }
        
        public Point upperVertex() {
            return upperVertex;
        }
        
        public double upperVertexX() {
            return upperVertex == null ?
                    Double.NEGATIVE_INFINITY : upperVertex.getX();
        }
        
        public double upperVertexY() {
            return upperVertex == null ?
                    Double.NEGATIVE_INFINITY : upperVertex.getY();
        }
        
        public Point lowerVertex() {
            return lowerVertex;
        }
        
        public double lowerVertexX() {
            return lowerVertex == null ?
                    Double.NEGATIVE_INFINITY : lowerVertex.getX();
        }
        
        public double lowerVertexY() {
            return lowerVertex == null ?
                    Double.NEGATIVE_INFINITY : lowerVertex.getY();
        }
        
        public Halfplane currentEdge() {
            if (i >= edges.size()) {
                return null;
            } else {
                return edges.get(i);
            }
        }
        
        public Halfplane nextEdge() {
            if (i >= edges.size() - 1) {
                return null;
            } else {
                return edges.get(i+1);
            }
        }
        
        private int i;
        private Point lowerVertex, upperVertex;
        private ArrayList<Halfplane> edges;
        
    }
    
}
