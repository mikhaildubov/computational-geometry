package anysegmentsintersect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.TreeSet;


public class SegmentsIntersect {
    
    /**
     * Определяет, пересекаются ли какие-либо два отрезка из множества,
     * наивным методом за O(n^2).
     * 
     * @param segments Множество отрезков
     * @return true, если есть пересекающиеся отрезки, false иначе
     */
    public static boolean any_naive(ArrayList<Segment> segments) {
        for (int i = 0; i < segments.size() - 1; i++) {
            for (int j = i + 1; j < segments.size(); j++) {
                if (SegmentsIntersect.two(segments.get(i), segments.get(j))) {
                    
                    segm1 = segments.get(i);
                    segm2 = segments.get(j);
                    
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Определяет, пересекаются ли какие-либо два отрезка из множества,
     * методом "выметающей прямой" за O(n*lg(n)).
     * 
     * @param segments Множество отрезков
     * @return true, если есть пересекающиеся отрезки, false иначе
     */
    public static boolean any(ArrayList<Segment> segments) {
        
        // Красно-черное дерево, которое будет поддерживать
        // упорядоченное множество отрезков с затратами сложности O(n*lg(n))
        TreeSet<Segment> segmentsTree =
                new TreeSet<Segment>(segmentsComparator);
        
        // Массив точек - концов отрезков, отсортированных по X
        ArrayList<Point> points = new ArrayList<Point>();
        for (Segment s : segments) {
            points.add(s.getLeft());
            points.add(s.getRight());
        }
        Collections.sort(points, PointsComparatorX);
        
        foundBoundaryCase = false; // Для учета возможных граничных случаев
        
        Segment pSegm; // временная переменная
        
        for (Point p : points) { // Проход по упорядоченному списку точек
            
            // (!) Задаем компаратору координату X, в которой идет сравнение
            segmentsComparator.setX(p.getX());
            
            pSegm = p.getSegment();
            
            if (p.isLeft()) {
                
                segmentsTree.add(pSegm); // INSERT
                
                if ((segmentsTree.lower(pSegm) != null &&
                        SegmentsIntersect.two(segmentsTree.lower(pSegm), pSegm))) {
                    
                    segm1 = segmentsTree.lower(pSegm);
                    segm2 = pSegm;
                    
                    return true;
                }
                
                if ((segmentsTree.higher(pSegm) != null &&
                        SegmentsIntersect.two(segmentsTree.higher(pSegm), pSegm))) {
                    
                    segm1 = segmentsTree.higher(pSegm);
                    segm2 = pSegm;
                    
                    return true;
                }
                
                if(foundBoundaryCase) {
                    return true;
                }
                
            } else { // p.isRight()
                
                if(segmentsTree.lower(pSegm) != null &&
                    segmentsTree.higher(pSegm) != null &&
                    SegmentsIntersect.two(segmentsTree.higher(pSegm),
                                            segmentsTree.lower(pSegm))) {
                    
                    segm1 = segmentsTree.higher(pSegm);
                    segm2 = segmentsTree.lower(pSegm);
                    
                    return true;
                }
                
                segmentsTree.remove(pSegm);
            }
            
        }
        
        return false;
    }
    
    /**
     * Возвращает пару пересекающихся отрезков (одну из пар).
     * 
     * @return Объект ArrayList из двух элементов
     */
    public static ArrayList<Segment> intersectingSegments() {
        ArrayList<Segment> result = new ArrayList<Segment>();
        
        result.add(segm1);
        result.add(segm2);
        
        return result;
    }
    
    /**
     * Определяет, пересекаются ли два отрезка (за O(1)).
     * 
     * @param s1 Первый отрезок
     * @param s2 Второй отрезок
     * @return true, если отрезки пересекаются, false иначе
     */
    public static boolean two(Segment s1, Segment s2) {
        
        Point p1 = s1.getLeft();
        Point p2 = s1.getRight();
        Point p3 = s2.getLeft();
        Point p4 = s2.getRight();
        
        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);
        
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) &&
            ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        } else if (d1 == 0 && onSegment(p3, p4, p1)) {
            return true;
        } else if (d2 == 0 && onSegment(p3, p4, p2)) {
            return true;
        } else if (d3 == 0 && onSegment(p1, p2, p3)) {
            return true;
        } else if (d4 == 0 && onSegment(p1, p2, p4)) {
            return true;
        } else {
            return false;
        }
    }
    
    private static double direction(Point p0, Point p1, Point p2) {
        return ((p2.getX() - p0.getX()) * (p1.getY() - p0.getY()) -
                (p2.getY() - p0.getY()) * (p1.getX() - p0.getX()));
    }

    private static boolean onSegment(Point pi, Point pj, Point pk) {
        return (Math.min(pi.getX(), pj.getX()) <= pk.getX() &&
                pk.getX() <= Math.max(pi.getX(), pj.getX()) &&
                Math.min(pi.getY(), pj.getY()) <= pk.getY() &&
                pk.getY() <= Math.max(pi.getY(), pj.getY()));
    }
    
    // Инициализация компараторов
    static {
        segmentsComparator = new SegmentsComparator();
        
        PointsComparatorX = new Comparator<Point>() {
            
            public int compare(Point p1, Point p2) {
                if (p1.getX() < p2.getX()) {
                    return -1;
                } else if (p1.getX() > p2.getX()) {
                    return 1;
                } else {
                    // В случае совпадений иксов - распределение
                    // сначала левые, потом правые:
                    if (p1.isLeft() && p2.isRight()) {
                        return -1;
                    } else if (p1.isRight() && p2.isLeft()) {
                        return 1;
                    } else {
                        // В случае совпадений конечности -
                        // распределение по y
                        if (p1.getY() < p2.getY()) {
                            return -1;
                        } else if (p1.getY() > p2.getY()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
            }
        };
    }
    
    /**
     * Сравнивает два отрезка в некоторой координате x.
     */
    static class SegmentsComparator implements Comparator<Segment>  {

        public int compare(Segment s1, Segment s2) {

            // 1. Точки не сравнимы в координате x
            if (x < s1.getLeft().getX() || x > s1.getRight().getX() ||
                x < s2.getLeft().getX() || x > s2.getRight().getX()) {
                return 0;
            }

            // 2. Вычисляем координаты y для соответствующих координат x
            double y1 = yForX(s1, x);
            double y2 = yForX(s2, x);

            if (Double.isNaN(y1)) {
                if(s1.getLeft().getY() >= y2 && s1.getRight().getY() <= y2) {  // Граничный случай!
                    segm1 = s1;
                    segm2 = s2;
                    foundBoundaryCase = true;
                }
                if (s1.getLeft().getY() < y2) {
                    return -1;
                } else if (s1.getLeft().getY() > y2) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (Double.isNaN(y2)) {
                if(s2.getLeft().getY() >= y1 && s2.getRight().getY() <= y1) {  // Граничный случай!
                    segm1 = s1;
                    segm2 = s2;
                    foundBoundaryCase = true;
                }
                if (s2.getLeft().getY() < y1) {
                    return 1;
                } else if (s2.getLeft().getY() > y1) {
                    return -1;
                } else {
                    return 0;
                }
            } else if(y1 < y2) {
                return -1;
            } else if (y1 > y2) {
                return 1;
            } else {
                if(s1 != s2) { // Граничный случай!
                  segm1 = s1;
                  segm2 = s2;
                  foundBoundaryCase = true;
                }
                return 0;
            }
        }

        /**
         * Задает координату x, в которой происходит сравнение.
         * 
         * @param x Координата x
         */
        public void setX(double x) {
            this.x = x;
        }

        private double yForX(Segment s, double x) {
            return (s.getRight().getX()*s.getLeft().getY() -
                    s.getLeft().getX()*s.getRight().getY() -
                    x*(s.getLeft().getY() - s.getRight().getY())) /
                    (s.getRight().getX() - s.getLeft().getX());
        }

        private double x;
    }
    
    // Алгоритм требует двух компараторов:
    // для сравнения отрезков в координате x
    // (используется при упорядочении красно-черного дерева),
    // а также для начальной сортировки точек "слева направо".
    private static SegmentsComparator segmentsComparator;
    private static Comparator<Point> PointsComparatorX;
    
    // Для учета граничных случаев
    private static boolean foundBoundaryCase;
    
    // Будем запоминать отрезки, которые пересекаются
    private static Segment segm1, segm2;
}