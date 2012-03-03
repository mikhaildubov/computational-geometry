package triangulation;


public class Triangle {
    
    public Triangle (Point A, Point B, Point C) {
        a = A;
        b = B;
        c = C;
    }
    
    public boolean pointInside(Point p) {
        
        boolean l1 = Point.isLeftTurn(a, b, p);
        boolean l2 = Point.isLeftTurn(b, c, p);
        boolean l3 = Point.isLeftTurn(c, a, p);
        
        if (l1 && l2 && l3) {
            return true;
        }
        
        boolean r1 = Point.isRightTurn(a, b, p);
        boolean r2 = Point.isRightTurn(b, c, p);
        boolean r3 = Point.isRightTurn(c, a, p);
        
        if (r1 && r2 && r3) {
            return true;
        }
            
        return false;
    }
    
    public void setA(Point A) {
        a = A;
    }
    
    public void setB(Point B) {
        b = B;
    }
    
    public void setC(Point C) {
        c = C;
    }
    
    public Point getA() {
        return a;
    }
    
    public Point getB() {
        return b;
    }
    
    public Point getC() {
        return c;
    }
    
    private Point a, b, c;
}
