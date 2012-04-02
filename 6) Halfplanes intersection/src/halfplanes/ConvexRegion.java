package halfplanes;

import java.util.ArrayList;

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
    
    public ConvexRegion(Halfplane h) {
        this();
        if ((h.getLine().isPositive())) {
            left.add(h);
        } else {
            right.add(h);
        }
    }
    
    public double topmostVertexY() {
        if (right != null && right.size() != 0 &&
                left != null && left.size() != 0) {
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
    
    /**
     * Intersects two convex regions, using a plane sweep algorithm.
     * (See [de Berg], section 4.2).
     * 
     * TODO: Handling horizontal lines
     */
    public static ConvexRegion intersectConvexRegions(ConvexRegion c1, ConvexRegion c2) {
        
        ConvexRegion C = new ConvexRegion();
        
        Halfplane leftC1, rightC1, leftC2, rightC2;
        boolean gotLeftC1, gotRightC1, gotLeftC2, gotRightC2;
        
        double y1 = c1.topmostVertexY();
        double y2 = c2.topmostVertexY();
        double sweepingLineY = Math.min(y1, y2);
        
        // Iterators initializing
        VertexIterator leftVerticesC1 = new VertexIterator(c1.left);
        VertexIterator rightVerticesC1 = new VertexIterator(c1.right);
        VertexIterator leftVerticesC2 = new VertexIterator(c2.left);
        VertexIterator rightVerticesC2 = new VertexIterator(c2.right);
        
        while (sweepingLineY > Double.NEGATIVE_INFINITY) {
            
            // Going down to the sweeping line Y coordinate
            gotLeftC1 = leftVerticesC1.toY(sweepingLineY);
            gotRightC1 = rightVerticesC1.toY(sweepingLineY);
            gotLeftC2 = leftVerticesC2.toY(sweepingLineY);
            gotRightC2 = rightVerticesC2.toY(sweepingLineY);

            // Pointers initializing
            leftC1 = leftVerticesC1.upper();
            rightC1 = rightVerticesC1.upper();
            leftC2 = leftVerticesC2.upper();
            rightC2 = rightVerticesC2.upper();
            
            //..................
            
            // Defining which edge is the next in the "event queue"
            // (See de Berg, page 69)
            
            if (gotLeftC1) { // handle the left edge of C1 
                if (leftVerticesC1.upperVertex().getX() > leftC2.getLine().XforY(sweepingLineY) &&
                    leftVerticesC1.upperVertex().getX() < rightC2.getLine().XforY(sweepingLineY)) {
                    // Case 1, upper endpoint lies between leftC2 and rightC2
                }
            }
            
            
            
            // TODO: Endpoints with the same y-coordinate are handled
            // from left to right. If two endpoints coincide then the
            // leftmost edge is treated first.
            
            // Moving further
            sweepingLineY = max(leftVerticesC1.lowerVertexY(), 
                                rightVerticesC1.lowerVertexY(),
                                leftVerticesC2.lowerVertexY(),
                                rightVerticesC2.lowerVertexY());
        }
    }
    
    private ArrayList<Halfplane> left;
    private ArrayList<Halfplane> right;
    
    
    static class VertexIterator {
        
        public VertexIterator(ArrayList<Halfplane> edges) {
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
        
        public boolean toY(double Y) {
            boolean changed = lowerVertex != null && lowerVertex.getY() > Y;
            
            if(changed) {
                next();
            }
            
            return changed;
        }
        
        public Point upperVertex() {
            return upperVertex;
        }
        
        public Point lowerVertex() {
            return lowerVertex;
        }
        
        public double lowerVertexY() {
            return lowerVertex == null ? Double.NEGATIVE_INFINITY : lowerVertex.getY();
        }
        
        public Halfplane upper() {
            if (i >= edges.size()) {
                return null;
            } else {
                return edges.get(i);
            }
        }
        
        public Halfplane lower() {
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
