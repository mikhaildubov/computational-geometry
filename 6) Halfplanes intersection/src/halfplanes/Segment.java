package halfplanes;


public class Segment {
    
    public Segment(Point left, Point right) {
        
        if(left.getX() > right.getX()) {
            Point temp = right;
            right = left;
            left = temp;
        }
        
        this.left = left;
        this.right = right;
    }
    
    public Point getLeft() {
        return left;
    }
    
    public Point getRight() {
        return right;
    }
    
    final private Point left;
    final private Point right;
}
