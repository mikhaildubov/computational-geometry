package convexhull;

import java.util.*;
import ru.hse.se.primitives.Point;
import ru.hse.se.primitives.Polygon;

/**
 * Builds the convex hull of a given set of points
 * using different algorithms.
 * 
 * @author Mikhail Dubov
 */
public class ConvexHull {
    
    /**
     * Builds the convex hull of a given set of points
     * using the Graham's scan algorithm - O(n*log(n)).
     */ 
    public static Polygon Graham(ArrayList<Point> pointsList) {
        
        // Список точек будет меняться (предполагаем, что pointsList.size >= 3)
        
        ArrayList<Point> points = (ArrayList<Point>)pointsList.clone();
        Stack<Point> result = new Stack<Point>();
        
        // Получаем нижнюю левую точку - первую вершину оболочки - O(n)
        
        Point p0 = getLowestPoint(points);
        result.push(p0);
        points.remove(p0);
        
        // Оставшиеся вершины сортируем по возрастанию полярного угла - O(n*log(n))
        
        Collections.sort(points, new PointComparator(p0));
        
        // Перед началом сканирования заносим еще две точки в стек
        
        Iterator<Point> iter = points.iterator();
        
        Point p1 = iter.next();
        result.push(p1);
        
        Point p2 = iter.next();
        result.push(p2);
        
        // Совершаем проход по отсортированному списку,
        // определяя, какие вершины входят в оболочку - O(n)
        
        while (iter.hasNext()) {
            Point pi = iter.next();
            Point top = result.elementAt(result.size() - 1);
            Point nextToTop = result.elementAt(result.size() - 2);
            
            while (! isLeftTurn(nextToTop, top, pi)) {
                result.pop();
                
                // Не забываем обновить 
                top = nextToTop;
                nextToTop = result.elementAt(result.size() - 2);
            }
            
            result.push(pi);
        }
        
        // Оболочка построена, возвращаем список точек
        // в порядке обхода против часовой стрелки
        
        ArrayList<Point> polygon = new ArrayList<Point>();
        while(! result.empty()) {
            polygon.add(result.pop());
        }
        Collections.reverse(polygon);
        
        return new Polygon(polygon);
    }
    
    /**
     * Builds the convex hull of a given set of points
     * using the Jarvis' march algorithm - O(n*h),
     * where h is the number of vertises in the hull,
     * so the worst case is O(n^2).
     */
    public static Polygon Jarvis(ArrayList<Point> pointsList) {
        
        // Список точек будет меняться (предполагаем, что pointsList.size >= 3)
        
        ArrayList<Point> points = (ArrayList<Point>)pointsList.clone();
        ArrayList<Point> result = new ArrayList<Point>();
        
        // Получаем нижнюю левую точку - первую вершину оболочки - O(n)
        
        Point p0 = getLowestPoint(points);
        result.add(p0);
        points.remove(p0);
        
        // Получаем последнюю вершину оболочки - O(n)
        
        Point pE = points.get(0);
        Point candidate;
        for(int i = 1; i < points.size(); i++) {
                candidate = points.get(i);
                if(crossProduct(p0, candidate, pE) < 0) {
                    pE = candidate;
                }
            }
        
        // Строим цепь - O(h) шагов...
        
        Point next = null;
        Point last = p0;
        do {
            // ... на каждом - перебор O(n) точек, итого O(n*h) операций
            next = points.get(0);
            
            for(int i = 1; i < points.size(); i++) {
                candidate = points.get(i);
                if (crossProduct(last, candidate, next) > 0 ||
                        crossProduct(last, candidate, next) == 0 &&
                        dist(last, candidate) > dist(last, next)) {
                    next = candidate;
                }
            }
            
            result.add(next);
            points.remove(next);
            last = next;
            
        } while(next != pE);
        
        // Оболочка построена, возвращается список точек
        // в порядке обхода против часовой стрелки
        
        return new Polygon(result);
    }
    
    /**
     * Compares two points by their polar angles
     * using the cross product.
     */
    static class PointComparator implements Comparator<Point> {
        
        private Point p0;
        
        public PointComparator(Point p0) {
            this.p0 = p0;
        }
        
        // Сравнение точек по их полярному углу относительно p0;
        // если совпадает - то по удаленности от p0.
        public int compare(Point p1, Point p2) {
            
            double crossProduct = crossProduct(p0, p1, p2);
            
            if (crossProduct > 0) return -1;
            if (crossProduct < 0) return 1;
            
            // cross_product = 0, векторы коллинеарны -> нужен тот, что дальше
            double d1 = dist(p0, p1);
            double d2 = dist(p0, p2);
            
            if (d1 < d2) return -1;
            if (d1 > d2) return 1;
            
            return 0;
        }
    }
    
    private static double crossProduct(Point p0, Point p1, Point p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY()) -
                (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }
    
    private static boolean isLeftTurn(Point p0, Point p1, Point p2) {
        return (crossProduct(p0, p1, p2) > 0);
    }
        
    private static double dist(Point p0, Point p1) {
        return Math.sqrt((p1.getX() - p0.getX()) * (p1.getX() - p0.getX()) +
                         (p1.getY() - p0.getY()) * (p1.getY() - p0.getY()));
    }
    
    private static Point getLowestPoint(ArrayList<Point> points) {
        Point result = points.get(0);
        
        Point candidate;
        for (int i = 1; i < points.size(); i++) {
            candidate = points.get(i);
            
            if (candidate.getY() < result.getY() ||
                    candidate.getY() == result.getY() && candidate.getX() < result.getX()) {
                result = candidate;
            }
        }
        
        return result;
    }
    
    private static void printPointsList(ArrayList<Point> points) {
        for (Point p : points) {
            System.out.println(p);
        }
    }
}
