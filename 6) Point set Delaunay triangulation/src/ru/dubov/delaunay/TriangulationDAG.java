package ru.dubov.delaunay;

import java.util.ArrayList;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Triangle;

/**
 * Represents the directed acyclic graph data structure
 * used in the randomized incremental triangulation algorithm.
 * 
 * @author Mikhail Dubov
 */
public class TriangulationDAG {
    
    public void init(Triangle t) {
        root = new Node(t);
    }
    
    public Node getRoot() {
        return root;
    }
    
    /**
     * Looks in the DAG for the smallest triangle
     * containing the given point.
     * 
     * @param p The point
     * @return The smallest triangle that contains p
     */
    public Triangle locate(Point p) {
        return _locate(root, p);
    }
    
    /**
     * Recursive procedure for locate(p). 
     */
    private Triangle _locate(Node n, Point p) {
        
        if (n.getChild(0) != null &&
             n.getChild(0).getTriangle().pointInside(p, false)) {
            return _locate(n.getChild(0), p);
            
        } else if (n.getChild(1) != null &&
             n.getChild(1).getTriangle().pointInside(p, false)) {
            return _locate(n.getChild(1), p);
            
        } else if (n.getChild(2) != null &&
             n.getChild(2).getTriangle().pointInside(p, false)) {
            return _locate(n.getChild(2), p);
            
        } else { // Leaf
            return n.getTriangle();
        }
    }
    
    /**
     * Traverses the DAG and collects the triangulation from its leafs.
     * 
     * @return The set of triangles
     */
    public ArrayList<Triangle> getTriangulation() {
        ArrayList<Triangle> res = new ArrayList<Triangle>();
        traverseAndFill(root, res);
        return res;
    }
    
    /**
     * Recursive procedure for getTriangulation().
     */
    private void traverseAndFill(Node n, ArrayList<Triangle> t) {
        
        boolean isLeaf = true;
        
        // to visit vertices no more than once
        n.setVisited(true);
        
        if (n.getChild(0) != null) {
            isLeaf = false;
            if (! n.getChild(0).getVisited()) {
                traverseAndFill(n.getChild(0), t);
            }
        }
        if (n.getChild(1) != null) {
            isLeaf = false;
            if (! n.getChild(1).getVisited()) {
                traverseAndFill(n.getChild(1), t);
            }
        }
        if (n.getChild(2) != null) {
            isLeaf = false;
            if (! n.getChild(2).getVisited()) {
                traverseAndFill(n.getChild(2), t);
            }
        }
        
        if (isLeaf) {
            t.add(n.getTriangle());
        }
    }
 
    private Node root;
    
    
    
    /**
     * Represents a node of the DAG.
     */
    public static class Node {
        
        public Node(Triangle t) {
            triangle = t;
            triangle.setTag(this);
            visited = false;
        }
        
        public void addChild(Triangle t) {
            addChild(new Node(t));
        }
        
        public void addChild(Node n) {
            if (ch0 == null) {
                ch0 = n;
                ch0.addParent(this);
            } else if (ch1 == null) {
                ch1 = n;
                ch1.addParent(this);
            } else if (ch2 == null) {
                ch2 = n;
                ch2.addParent(this);
            }
        }
        
        public void addParent(Node n) {
            if (parent0 == null) {
                parent0 = n;
            } else if (parent1 == null) {
                parent1 = n;
            }
        }
        
        public void removeChild(Triangle t) {
            
            if (t.equals(ch0.triangle)) {
                ch0.removeParent(this);
                ch0 = null;
            } else if (t.equals(ch1.triangle)) {
                ch1.removeParent(this);
                ch1 = null;
            } else if (t.equals(ch2.triangle)) {
                ch2.removeParent(this);
                ch2 = null;
            }
        }
        
        public void removeChild(Node n) {
            
            if (n == ch0) {
                ch0.removeParent(this);
                ch0 = null;
            } else if (n == ch1) {
                ch1.removeParent(this);
                ch1 = null;
            } else if (n == ch2) {
                ch2.removeParent(this);
                ch2 = null;
            }
        }
        
        public void removeParent(Node n) {
            
            if (parent0 == n) {
                parent0 = null;
            } else if (parent1 == n) {
                parent1 = null;
            }
        }
        
        public void removeChildren() {
            ch0 = null;
            ch1 = null;
            ch2 = null;
        }
        
        public boolean hasChildren() {
            if (ch0 != null) {
                return true;
            }
            if (ch1 != null) {
                return true;
            }
            if (ch2 != null) {
                return true;
            }
            return false;
        }
        
        public int getChildrenCount() {
            
            int res = 0;
            
            if (ch0 != null) {
                res++;
            }
            if (ch1 != null) {
                res++;
            }
            if (ch2 != null) {
                res++;
            }
            
            return res;
        }
        
        public int getParentsCount() {
            
            int res = 0;
            
            if (parent0 != null) {
                res++;
            }
            if (parent1 != null) {
                res++;
            }
            
            return res;
        }
        
        public Triangle getTriangle() {
            return triangle;
        }
        
        public Node getChild(int i) {
            switch(i) {
                case 0: return ch0;
                case 1: return ch1;
                case 2: return ch2;
                default: return null;
            }
        }
        
        public Node getParent(int i) {
            switch(i) {
                case 0: return parent0;
                case 1: return parent1;
                default: return null;
            }
        }
        
        public boolean getVisited() {
            return visited;
        }
        
        public void setVisited(boolean v) {
            visited = v;
        }
        
        private Triangle triangle;
        private Node parent0, parent1;
        private Node ch0, ch1, ch2;
        private boolean visited;
    }
}
