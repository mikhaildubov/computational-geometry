package closestpair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ClosestPair {
    
    /**
     * Поиск пары ближайших точек наивным методом за O(n^2).
     */
    public static ArrayList<Point> Naive(ArrayList<Point> points) {
        Point p0 = points.get(0);
        Point p1 = points.get(1);
        
        for (int i = 0; i < points.size() - 1; i++) {
            for (int j = i + 1; j < points.size(); j++) {
                if(dist(points.get(i), points.get(j)) < dist(p0, p1)) {
                    p0 = points.get(i);
                    p1 = points.get(j);
                }
            } 
        }
        
        ArrayList<Point> resultPair = new ArrayList<Point>();
        resultPair.add(p0);
        resultPair.add(p1);
        
        return resultPair;
    }

    /**
     * Поиск пары ближайших точек методом "разделяй и властвуй" за O(n*log(n)).
     */
    public static ArrayList<Point> Fast(ArrayList<Point> points) {
        ArrayList<Point> X = (ArrayList<Point>)points.clone();
        ArrayList<Point> Y = (ArrayList<Point>)points.clone();

        Collections.sort(X, new PointComparatorX());
        Collections.sort(Y, new PointComparatorY());

        return ClosestPair(X, Y);
    }

    private static ArrayList<Point> ClosestPair(ArrayList<Point> X, ArrayList<Point> Y) {
                
        ArrayList<Point> resultPair = new ArrayList<Point>();

        // База рекурсии - случай |P| <= 3
        if (X.size() <= 1) {
            resultPair = null;
        } else if (X.size() == 2) {
            resultPair = X;
        } else if (X.size() == 3) {
            // |P| == 3 => Грубый перебор
            double dist1 = dist(X.get(0), X.get(1));
            double dist2 = dist(X.get(0), X.get(2));
            double dist3 = dist(X.get(1), X.get(2));

            if (dist2 < dist3) {
                if (dist1 < dist2) {
                    resultPair.add(X.get(0));
                    resultPair.add(X.get(1));
                } else {
                    resultPair.add(X.get(0));
                    resultPair.add(X.get(2));
                }
            } else {
                if (dist1 < dist3) {
                    resultPair.add(X.get(0));
                    resultPair.add(X.get(1));
                } else {
                    resultPair.add(X.get(1));
                    resultPair.add(X.get(2));
                }
            }
        }
        // Шаг рекурсии - разделяй и властвуй
        else {
            double lX = (X.get(X.size() / 2)).getX();

            // Разделяй ...

            ArrayList<Point> XL = new ArrayList<Point>();
            ArrayList<Point> XR = new ArrayList<Point>();

            for (int i = 0; i < X.size(); i++) {
                if ((X.get(i)).getX() <= lX) {
                    XL.add(X.get(i));
                } else {
                    XR.add(X.get(i));
                }
            }

            ArrayList<Point> YL = new ArrayList<Point>();
            ArrayList<Point> YR = new ArrayList<Point>();

            for (int i = 0; i < Y.size(); i++) {
                if ((Y.get(i)).getX() <= lX) {
                    YL.add(Y.get(i));
                } else {
                    YR.add(Y.get(i));
                }
            }
            
            // Приходится специально обрабатывать вырожденный случай, когда
            // все точки уходят налево (например, если у них равны координаты X)
            // TODO: Потенциально деградация до O(n^2), если все точки на оси Y.
            if (XR.size() == 0) {
                // Пока что решение - просто перекидываем
                // крайнюю справа точку из XL направо, в XR
                Point repl = XL.get(XL.size()-1);
                
                XL.remove(repl);
                XR.add(repl);
                
                YL.remove(repl);
                YR.add(repl);
            }
            

            // ... Властвуй ...

            ArrayList<Point> ClosestLeft = ClosestPair(XL, YL);
            ArrayList<Point> ClosestRight = ClosestPair(XR, YR);
            ArrayList<Point> ClosestBetween = ClosestBetweenSubsets(X, Y,
                                                Math.min(dist(ClosestLeft), dist(ClosestRight)));

            double distLeft = dist(ClosestLeft);
            double distRight = dist(ClosestRight);
            double distBetween = dist(ClosestBetween);

            // ... Объединяй.

            if (distLeft < distRight) {
                if (distBetween < distLeft) {
                    resultPair = ClosestBetween;
                } else {
                    resultPair = ClosestLeft;
                }
            } else {
                if (distBetween < distRight) {
                    resultPair = ClosestBetween;
                } else {
                    resultPair = ClosestRight;
                }
            }
        }

        return resultPair;
    }

    private static ArrayList<Point> ClosestBetweenSubsets(ArrayList<Point> X, ArrayList<Point> Y, double d) {
        double lX = (X.get(X.size() / 2)).getX();

        // Заполняем Y2
        ArrayList<Point> Y2 = new ArrayList<Point>();
        for (int i = 0; i < Y.size(); i++) {
            if (Math.abs((Y.get(i)).getX() - lX) <= d) {
                Y2.add(Y.get(i));
            }
        }

        // Проходимся по Y2 в поисках ближайших точек
        if (Y2.size() < 2) {
            return null;
        }

        Point p1 = Y2.get(0);
        Point p2 = Y2.get(1);

        // Достаточно просмотреть 7 точек
        for (int i = 0; i < Y2.size() - 1; i++) {
            for (int j = i + 1; j <= Math.min(i + 8, Y2.size() - 1); j++)
                if (dist(Y2.get(i), Y2.get(j)) < dist(p1, p2)) {
                    p1 = Y2.get(i);
                    p2 = Y2.get(j);
                }
        }

        ArrayList resultPair = new ArrayList();
        resultPair.add(p1);
        resultPair.add(p2);

        return resultPair;
    }

    public static double dist(Point p0, Point p1) {
        return Math.sqrt((p1.getX() - p0.getX()) * (p1.getX() - p0.getX()) +
                         (p1.getY() - p0.getY()) * (p1.getY() - p0.getY()));
    }

    private static double dist(ArrayList<Point> pair) {
        if (pair == null || pair.size() != 2) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Math.sqrt(((pair.get(1)).getX() - (pair.get(0)).getX()) * ((pair.get(1)).getX() - (pair.get(0)).getX()) +
                         ((pair.get(1)).getY() - (pair.get(0)).getY()) * ((pair.get(1)).getY() - (pair.get(0)).getY()));
        }
    }


    static class PointComparatorX implements Comparator<Point> {

         public int compare(Point p1, Point p2) {
             
             if (p1.getX() < p2.getX()) return -1;
             if (p1.getX() > p2.getX()) return 1;
             return 0;
         }
    }

    static class PointComparatorY implements Comparator<Point> {

         public int compare(Point p1, Point p2) {
             
             if (p1.getY() < p2.getY()) return -1;
             if (p1.getY() > p2.getY()) return 1;
             return 0;
         }
    }
}