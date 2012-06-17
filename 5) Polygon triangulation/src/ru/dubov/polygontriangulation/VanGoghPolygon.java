package ru.dubov.polygontriangulation;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Polygon;
import ru.dubov.primitives.Triangle;

/**
 * An advanced version of polygon (with "ears")
 * for O(n^2) Van Gogh implementation.
 * The implementation involves using double linked
 * cyclic lists for storing the vertices.
 */
public class VanGoghPolygon {
    
    public VanGoghPolygon() {
        vertices = new DoublyLinkedCyclicList<Point>();
        ears = new DoublyLinkedCyclicList<DoublyLinkedCyclicList<Point>.Node>();
    }
    
    public VanGoghPolygon(Polygon p) {
        this();
        
        for (int i = p.size()-1; i >= 0; i--) {
            vertices.insert(p.get(i));
        }
        
        constructEarList();
    }
    
    /*private void listVert() {
        DoublyLinkedCyclicList<Point>.Node cur = vertices.head();
        System.out.println();
        do {
            System.out.print(cur.value+" ");
            cur = cur.next;
        } while(cur != vertices.head());
        System.out.println();
    }*/
    
    public Triangle removeEar() {
        
        DoublyLinkedCyclicList<Point>.Node adjLeft = ears.head().value.prev;
        DoublyLinkedCyclicList<Point>.Node adjRight = ears.head().value.next;
        
        Triangle ear = new Triangle(adjLeft.value,
                ears.head().value.value, adjRight.value);      

        ears.head().value.delete(); // Delete in vertices
        ears.head().delete(); // Delete in ears
        
        if (isConvex(adjLeft, isClockwise)) {
            // not an ear anymore?
            if (adjLeft == ears.head().prev.value) { 
                if (! isEar(ears.head().prev.value, isClockwise)) {
                    ears.head().prev.delete();
                }
            // new ear?
            } else if (isEar(adjLeft, isClockwise)) {
                ears.insert(adjLeft);
                ears.next();
            }
        }
        
        // next has now become the head (after deleting)
        
        if (isConvex(ears.head().value, isClockwise)) {
            // not an ear anymore?
            if (adjRight == ears.head().value) { 
                if (! isEar(ears.head().value, isClockwise)) {
                    ears.head().delete();
                }
            // new ear?
            } else if (isEar(adjRight, isClockwise)) {
                ears.insert(adjRight);
            }
        }
        
        return ear;
    }
    
    public final void constructEarList() {
        DoublyLinkedCyclicList<Point>.Node cur = vertices.head();
        if (cur == null) return;
        
        isClockwise = isClockwise();
        
        // Building the ear list for O(n^2) time.
        
        cur = vertices.head().prev;
        do {
            if (isEar(cur, isClockwise)) {
                ears.insert(cur);
            }
            cur = cur.prev;
        } while(cur != vertices.head().prev);
    }
    
    private boolean isConvex(DoublyLinkedCyclicList<Point>.Node node, boolean isClockwise) {
        return Point.isLeftTurn(node.prev.value, node.value, node.next.value)
               ^ isClockwise;
    }
    
    /*private boolean isReflex(DoublyLinkedList<Point>.Node node, boolean isClockwise) {
        return ! isConvex(node, isClockwise);
    }*/
    
    /**
     * O(n)
     */
    private boolean isEar(DoublyLinkedCyclicList<Point>.Node node, boolean isClockwise) {
        
        if (! isConvex(node, isClockwise)) {
            return false;
        }
        
        Triangle tr = new Triangle(node.prev.value, node.value, node.next.value);
        
        DoublyLinkedCyclicList<Point>.Node cur = vertices.head();
        do {
            if (tr.pointInside(cur.value)) return false;
            cur = cur.next;
        } while(cur != vertices.head());
        
        return true;
    }
    
    public boolean isClockwise() {
        double sum = 0;
        
        DoublyLinkedCyclicList<Point>.Node cur = vertices.head();
        
        do {
            sum += (cur.next.value.getX() - cur.value.getX())*
                    (cur.next.value.getY() + cur.value.getY());
            
            cur = cur.next;
        } while(cur != vertices.head());
        
        return (sum > 0);
    }
    
    private DoublyLinkedCyclicList<Point> vertices;
    private DoublyLinkedCyclicList<DoublyLinkedCyclicList<Point>.Node> ears;
    private boolean isClockwise;
}
