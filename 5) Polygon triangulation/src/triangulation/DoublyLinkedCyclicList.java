package triangulation;

/**
 * Doubly linked list structure
 * for O(n^2) Van Gogh implementation
 */
public class DoublyLinkedCyclicList<T> {
    public DoublyLinkedCyclicList() {
        head = null;
    }
    
    public void insert(T value) {
        
        Node node = new Node(value);
        
        if (head == null) {
            head = node;
            
            head.next = head;
            head.prev = head;
            
            return;
        }
        
        head.prev.next = node;
        node.prev = head.prev;
        
        node.next = head;
        head.prev = node;
        
        head = node;
    }
    
    public void next() {
        head = head.next;
    }
    
    public void prev() {
        head = head.prev;
    }
    
    public Node head() {
        return head;
    }
    
    private Node head;
    
    public class Node {

        public Node() {
            next = prev = null;
        }

        public Node(T val) {
            next = prev = null;
            value = val;
        }
        
        public void delete() {
        
            if (this.prev != null) {
                this.prev.next = this.next;
            }
            if (this.next != null) {
                this.next.prev = this.prev;
            }

            if (head == this) {
                head = this.next;
            }
    }

        public Node next, prev;
        public T value;
    }
}
