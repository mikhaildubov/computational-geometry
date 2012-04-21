package ru.hse.se.primitives;

/**
 * Represents a triangle.
 * Supports links to the adjacent triangles
 * (useful for triangulations).
 * 
 * @author Mikhail Dubov
 */
public class Triangle {
    
    /**
     * Initializes a triangle by its vertices.
     * 
     * @param A The first vertex
     * @param B The second vertex
     * @param C The third vertex
     */
    public Triangle (Point A, Point B, Point C) {
        a = A;
        b = B;
        c = C;
        adjAB = adjAC = adjBC = null;
        
        // Make the triangle counterclockwise
        // essential for triangulating!
        
        double sum = (b.getX() - a.getX())*
                     (b.getY() + a.getY()) + 
                     (c.getX() - b.getX())*
                     (c.getY() + b.getY()) +
                     (a.getX() - c.getX())*
                     (a.getY() + c.getY());
        
        if (sum > 0) {
            Point temp = a;
            a = b;
            b = temp;
        }
    }
    
    /**
     * Determines whether a point lies inside the triangle.
     * 
     * @param p The point
     * @return true, whether the point lies inside the triangle, false otherwise
     */
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
    
    /**
     * Returns the 'A' vertex of the triangle ABC.
     * 
     * @return The vertex
     */
    public Point getA() {
        return a;
    }
    
    /**
     * Returns the 'B' vertex of the triangle ABC.
     * 
     * @return The vertex
     */
    public Point getB() {
        return b;
    }
    
    /**
     * Returns the 'C' vertex of the triangle ABC.
     * 
     * @return The vertex
     */
    public Point getC() {
        return c;
    }
    
    /**
     * Returns the ith vertex of the triangle ABC,
     * where 'A' is the 0-th vertex, 'B' - the first and 'C' - the second.
     * 
     * @param i The index
     * @return The vertex
     */
    public Point getIth(int i) {
        switch (i) {
            case 0: return a; 
            case 1: return b;
            case 2: return c;
            default: return null;
        }
    }
    
    
    private void setAdjacent(Side thisSide, Triangle t) {
        switch (thisSide) {
            case AB: this.adjAB = t; break;
            case BC: this.adjBC = t; break;
            case AC: this.adjAC = t; break;
        }
    }
    
    /**
     * Updates the links to adjacent triangles given another triangle.
     * The links in both triangles are updated.
     * 
     * @param t Another triangle
     * @return true, if the triangle is adjacent, false otherwise
     */
    public boolean link(Triangle t) {
        
        if (t == null) {
            return false;
        }
        
        Segment s1, s2;
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                s1 = new Segment(this.getIth(i), this.getIth((i+1) % 3));
                s2 = new Segment(t.getIth(j), t.getIth((j+1) % 3));
                
                if (s1.equals(s2)) {
                    
                    Side sThis = Side.valueOf(i);
                    Side sT = Side.valueOf(j);
                    
                    this.setAdjacent(sThis, t);
                    t.setAdjacent(sT, this);
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
        
    private void setIth(int i, Point p) {
        switch (i) {
            case 0: a = p; break;
            case 1: b = p; break;
            case 2: c = p; break;
        }
    }
    
    /*public void setTag(Side s, boolean value) {
        switch (s) {
            case AB: tagAB = value; break;
            case BC: tagBC = value; break;
            case AC: tagAC = value; break;
        }
    }*/
    
    /**
     * Returns an adjacent triangle.
     * 
     * @param s The side where the adjacent triangle lies
     * @return The adjacent triangle, null if there is no triangle
     */
    public Triangle getAdjacent(Side s) {
        switch (s) {
            case AB: return adjAB;
            case BC: return adjBC;
            case AC: return adjAC;
            default: return null;
        }
    }
    
    /**
     * Determines whether the given triangle
     * is adjacent for the given side.
     * 
     * @param t The triangle
     * @param s The side
     * @return true, if the triangle is adjacent, false otherwise
     */
    public boolean isAdjacent(Triangle t, Side s) {
        return this.getAdjacent(s) == t;
    }
    
    /*public boolean getTag(Side s) {
        switch (s) {
            case AB: return tagAB;
            case BC: return tagBC;
            case AC: return tagAC;
            default: return false;
        }
    }*/
    
    /**
     * Flips the common edge of the two triangles.
     * Assumes that the adjacend sides are given correctly.
     * 
     * @param s1 The side of the current triangle to be flipped
     * @param t2 The adjacent triangle
     * @param s2 The side of the adjacent triangle to be flipped
     */
    public void flipEdge(Side s1, Triangle t2, Side s2) {
        
        if (! (this.getAdjacent(s1).equals(t2) &&
            t2.getAdjacent(s2).equals(this))) {
            
            return;
        }
        
        // Edge flip
        int i = s1.ordinal();
        int j = s2.ordinal();
               
        Triangle adjTemp1 = this.getAdjacent(Side.valueOf((i+1) % 3));
        Triangle adjTemp2 = t2.getAdjacent(Side.valueOf((j+1) % 3));
        
        Point c1 = this.getIth((i+1) % 3);

        Point a2 = this.getIth(i);
        Point b2 = this.getIth((i+2) % 3);
        Point c2 = t2.getIth((j+2) % 3);
        
        // Update points (this)
        
        this.setIth(i, a2);
        this.setIth((i+1) % 3, c2);
        this.setIth((i+2) % 3, b2);
        
        //Update adjacent triangles (!) 
        
        this.setAdjacent(Side.valueOf(i), adjTemp2);
        this.setAdjacent(Side.valueOf((i+1) % 3), t2);
        //this.setAdjacent(Side.valueOf((i+2) % 3), this.getAdjacent(Side.valueOf((i+2) % 3)));

        // Update points (t2)
        
        t2.setIth(j, c1);
        t2.setIth((j+1) % 3, b2);
        t2.setIth((j+2) % 3, c2);
        
        //Update adjacent triangles (!) 
        
        t2.setAdjacent(Side.valueOf(j), adjTemp1);
        t2.setAdjacent(Side.valueOf((j+1) % 3), this);
        //t2.setAdjacent(Side.valueOf((j+2) % 3), t2.getAdjacent(Side.valueOf((j+2) % 3)));
        
        // Some other changes
        
        if (adjTemp1 != null) {
            adjTemp1.link(t2);
        }
        if (adjTemp2 != null) {
            adjTemp2.link(this);
        }
    }
    
    /**
     * Defines whether the triangle has a shared adge
     * with another triangle and whether this edge is "illegal".
     * Assumes that the adjacend sides are given correctly.
     * 
     * (see [deBerg] (chapter 9) for details)
     * 
     * @param s1 The side of the current triangle to be checked for illegality
     * @param t2 The adjacent triangle
     * @param s2 The side of the adjacent triangle to be checked for illegality
     * @return true, if the edge is "illegal", false otherwise
     */
    public boolean areIllegal(Side s1, Triangle t2, Side s2) {
        
        if (! (this.getAdjacent(s1).equals(t2) &&
            t2.getAdjacent(s2).equals(this))) {
            
            return false;
        }
            
        Circle cr = new Circle(this.getA(), this.getB(), this.getC());
        return cr.isInside(t2.getIth((s2.ordinal()+2) % 3));
    }
    
    /**
     * The enum for the triangle sides.
     */
    public enum Side {
        AB(0), BC(1), AC(2);
        
        Side(int i) {
            this.i = i;
        }
        
        /**
         * Returns the triangle side for its index,
         * where AB is the 0-th side,
         * BC - the first and AC the third.
         * 
         * @param i The index
         * @return The side or null, if the index is not correct
         */
        public static Side valueOf(int i) {
            switch(i) {
                case 0: return AB;
                case 1: return BC;
                case 2: return AC;
                default: return null;
            }
        }
        
        int i;
    }
    
    /** The vertices **/
    protected Point a, b, c;
    
    /** The pointers to adjacent triangles **/
    protected Triangle adjAB, adjBC, adjAC;
    
    //protected boolean tagAB, tagBC, tagAC;
}