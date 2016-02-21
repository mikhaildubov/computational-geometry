package ru.dubov.polygontriangulation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeMap;
import ru.dubov.primitives.*;

public class MonotonePartitioningAlgorithm {
    
    /**
     * Calculates the polygon triangulation
     * using its partitioning into monotone pieces.
     * 
     * This implementation runs in O(n*log(n)) time.
     * 
     * See sections 3.2-3.3 in [deBerg] for details.
     * 
     * @param p Polygon.
     * @return The array of triangles.
     */
    /*public static ArrayList<Triangle> triangulate(Polygon p) {
        
        ArrayList<Polygon> monotonePartitioning = makeMonotone(p);
        
        ArrayList<Triangle> res = new ArrayList<Triangle>();
        
        for (Polygon m : monotonePartitioning) {
            res.addAll(triangulateMonotonePolygon(m));
        }
        
        return res;
    }*/
    
    public static ArrayList<Diagonal> makeMonotone(Polygon p) {
        
        // Algorithm requires the counterclockwise direction
        p.makeCounterClockwise();
        
        // Constructing the priority queue
        PriorityQueue<Vertex> Q = new PriorityQueue<Vertex>
                                    (p.size(), new VertexComparator());
        for (int i = 0; i < p.size(); i++) {
            Q.add(new Vertex(p, i));
        }
        
        // Initializing the binary tree
        TreeMap<Integer, Edge> T = new TreeMap<Integer, Edge>();
        
        // Partitioning as set of diagonals
        ArrayList<Diagonal> D = new ArrayList<Diagonal>();
        
        // Handling vertices
        while (! Q.isEmpty()) {
            try {
                handleVertex(Q.poll(), T, D);
            } catch(Exception e) {
                System.out.print("!");
            }
        }
        
        return D;
    }
    
    /*private static ArrayList<Triangle> triangulateMonotonePolygon(Polygon p) {
        
    }*/
    
    private static void handleVertex(Vertex v_i, TreeMap<Integer, Edge> T,
                                                    ArrayList<Diagonal> D) {
        
        int i = v_i.getIndex();
        int i_1 = (i == 0) ? (v_i.getPolygon().size()-1) : (i-1);
        
        Edge e_i, e_j, e_i_1, temp;
        Vertex helper_e_i_1, helper_e_j;
        
        switch (v_i.getVertexType()) {
            
            case START:
                e_i = new Edge(v_i.getPolygon(), i);
                e_i.setHelper(v_i);
                T.put(i, e_i);
                
                break;
                
            case END:
                
                e_i_1 = T.get(i_1);
                
                helper_e_i_1 = e_i_1.getHelper();
                if (helper_e_i_1.getVertexType() == VertexType.MERGE) {
                    D.add(new Diagonal(i, helper_e_i_1.getIndex()));
                }
                
                //T.remove(i_1);
                
                break;
                
            case SPLIT:
                e_j = null;
                // TODO: Improve to O(log(n))!
                for (int j : T.keySet()) {
                    temp = T.get(j);
                    if (temp.intersectsWithSweepLine(v_i.getY()) &&
                            temp.liesOnTheLeftSideof(v_i)) {
                        if (e_j == null) {
                            e_j = temp;
                        } else if (temp.liesOnTheRightSideof(e_j, v_i.getY())) {
                            e_j = temp;
                        }
                    }
                }
                
                D.add(new Diagonal(i, e_j.getHelper().getIndex()));
                
                e_j.setHelper(v_i);
                
                e_i = new Edge(v_i.getPolygon(), i);
                T.put(i, e_i);
                e_i.setHelper(v_i);
                
                break;
                
            case MERGE:
                
                e_i_1 = T.get(i_1);
                helper_e_i_1 = e_i_1.getHelper();
                
                if (helper_e_i_1.getVertexType() == VertexType.MERGE) {
                    D.add(new Diagonal(i, helper_e_i_1.getIndex()));
                }
                
                //T.remove(i_1);
                
                e_j = null;
                // TODO: Improve to O(log(n))!
                for (int j : T.keySet()) {
                    temp = T.get(j);
                    if (temp.intersectsWithSweepLine(v_i.getY()) &&
                            temp.liesOnTheLeftSideof(v_i)) {
                        if (e_j == null) {
                            e_j = temp;
                        } else if (temp.liesOnTheRightSideof(e_j, v_i.getY())) {
                            e_j = temp;
                        }
                    }
                }
                
                helper_e_j = e_j.getHelper();
                
                if (helper_e_j.getVertexType() == VertexType.MERGE) {
                    D.add(new Diagonal(i, helper_e_j.getIndex()));
                }
                
                e_j.setHelper(v_i);                
                
                break;
                
            case REGULAR:
                
                if (v_i.polygonInteriorLiesToTheRight()) {
                    
                    e_i_1 = T.get(i_1);
                    helper_e_i_1 = e_i_1.getHelper();

                    if (helper_e_i_1.getVertexType() == VertexType.MERGE) {
                        D.add(new Diagonal(i, helper_e_i_1.getIndex()));
                    }
                    
                    //T.remove(i_1);
                    
                    e_i = new Edge(v_i.getPolygon(), i);
                    T.put(i, e_i);
                    e_i.setHelper(v_i);
                    
                } else {
                    
                    e_j = null;
                    // TODO: Improve to O(log(n))!
                    for (int j : T.keySet()) {
                        temp = T.get(j);
                        if (temp.intersectsWithSweepLine(v_i.getY()) &&
                            temp.liesOnTheLeftSideof(v_i)) {
                            if (e_j == null) {
                                e_j = temp;
                            } else if (temp.liesOnTheRightSideof(e_j, v_i.getY())) {
                                e_j = temp;
                            }
                        }
                    }
                    
                    helper_e_j = e_j.getHelper();
                
                    if (helper_e_j.getVertexType() == VertexType.MERGE) {
                        D.add(new Diagonal(i, helper_e_j.getIndex()));
                    }

                    e_j.setHelper(v_i); 
                }
                
                break;
        }
    }
    
    static enum VertexType {
        START,
        END,
        REGULAR,
        SPLIT,
        MERGE
    }
    
    static class Vertex {
        
        public Vertex(Polygon p, int i) {
            polygon = p;
            index = i;
        }
        
        public int getIndex() {
            return index;
        }
        
        public double getX() {
            return polygon.get(index).getX();
        }
        
        public double getY() {
            return polygon.get(index).getY();
        }
        
        public Polygon getPolygon() {
            return polygon;
        }
        
        public VertexType getVertexType() {
            
            Point pCur = polygon.get(index);
            Point pPrev = polygon.get(index == 0 ? polygon.size()-1 : index-1);
            Point pNext = polygon.get((index+1) % polygon.size());
            
            if (pPrev.getY() < pCur.getY() &&
                 pNext.getY() < pCur.getY()) {
                if (polygon.isConvex(index)) {
                    return VertexType.START;
                } else {
                    return VertexType.SPLIT;
                }
            } else if (pPrev.getY() > pCur.getY() &&
                        pNext.getY() > pCur.getY()) {
                if (polygon.isConvex(index)) {
                    return VertexType.END;
                } else {
                    return VertexType.MERGE;
                }
            } else {
                return VertexType.REGULAR;
            }
        }
        
        public boolean polygonInteriorLiesToTheRight() {
            
            Point pCur = polygon.get(index);
            Point pPrev = polygon.get(index == 0 ? polygon.size()-1 : index-1);
            Point pNext = polygon.get((index+1) % polygon.size());
            
            if (pPrev.getY() > pCur.getY() &&
                 pNext.getY() < pCur.getY()) {
                return true;
            } else {
                return false;
            }
        }
        
        private Polygon polygon;
        private int index;
    }
    
    static class Edge {
        
        public Edge(Polygon p, int i) {
            polygon = p;
            index = i;
        }
        
        public void setHelper(Vertex v) {
            helper = v;
        }
        
        public Vertex getHelper() {
            return helper;
        }
        
        public int getIndex() {
            return index;
        }
        
        public Vertex getA() {
            return new Vertex(polygon, index);
        }
        
        public Vertex getB() {
            return new Vertex(polygon, (index+1)%polygon.size());
        }
        
        public boolean intersectsWithSweepLine(double sweepY) {
            return (sweepY >= getA().getY() && sweepY <= getB().getY()) ||
                    (sweepY >= getB().getY() && sweepY <= getA().getY());
        }
        
        public Line getLine() {
            return new Line(polygon.get(index),
                    polygon.get((index+1)%polygon.size()));
        }
        
        public boolean liesOnTheRightSideof(Edge e, double Y) {
            return this.getLine().XforY(Y) > e.getLine().XforY(Y);
        }
        
        public boolean liesOnTheLeftSideof(Vertex v) {
            return this.getLine().XforY(v.getY()) < v.getX();
        }
        
        private Polygon polygon;        
        private Vertex helper;
        private int index;
    }

    static class Diagonal {
        
        public Diagonal(int i1, int i2) {
            index1 = i1;
            index2 = i2;
        }
        
        public int getA() {
            return index1;
        }
        
        public int getB() {
            return index2;
        }
        
        int index1, index2;
    }
    
    static class VertexComparator implements Comparator<Vertex> {

        @Override
        public int compare(Vertex v1, Vertex v2) {

            if (v1.getY() > v2.getY() ||
                    v1.getY() == v2.getY() &&
                    v1.getX() > v2.getX())
                return -1;
            
            else return 1;
        }
    }
    
}
