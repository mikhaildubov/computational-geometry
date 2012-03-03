package closestpair;

public class Point {
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return this.x; }
    public double getY() { return this.y; }

    public String toString() {
        return "(" + this.getX() + ", " + this.getY() + ")";
    }

    private double x, y;
}
