package ru.dubov.anysegmentsintersect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;
import ru.dubov.primitives.Point;
import ru.dubov.primitives.Segment;


public class SegmentsIntersect {
    
    /**
     * Determines whether any two segments in the given set intersect
     * using the naive approach in O(n^2).
     * 
     * @param segments The set of segments
     * @return true, if any segments intersect, false otherwise
     */
    public static boolean any_Naive(ArrayList<Segment> segments) {
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
     * Determines whether any two segments in the given set intersect
     * using the "sweeping line" algorithm in O(n*lg(n)).
     * 
     * @param segments The set of segments
     * @return true, if any segments intersect, false otherwise
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
     * Returns a pair of intersecting segments.
     * (to be called after the algorithm finished its work).
     * 
     * @return ArrayList that contains the required pair.
     */
    public static ArrayList<Segment> intersectingSegments() {
        ArrayList<Segment> result = new ArrayList<Segment>();
        
        result.add(segm1);
        result.add(segm2);
        
        return result;
    }
    
    /**
     * Determines whether the two segments intersect in O(1).
     * 
     * @param s1 The first segment
     * @param s2 The second segment
     * @return true, if the segments intersect, false otherwise
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
    
    /**
     * Compares two segments in some X coordinate.
     */
    static class SegmentsComparator implements Comparator<Segment>  {

        @Override
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
         * sets the X coordinate to perform the comparison in.
         */
        public void setX(double x) {
            this.x = x;
        }

        /**
         * Calculates the Y coordinate of a point on the
         * segment by its X coordinate.
         */
        private double yForX(Segment s, double x) {
            return (s.getRight().getX()*s.getLeft().getY() -
                    s.getLeft().getX()*s.getRight().getY() -
                    x*(s.getLeft().getY() - s.getRight().getY())) /
                    (s.getRight().getX() - s.getLeft().getX());
        }

        private double x;
    }
    
    
    
    // Comparators initialization
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
    
    // The sweeping line algorithm requires two comparators:
    // the one for comparing two segments in some X coordinate
    // (user in the RB-tree), and the other for the 
    // initial points sorting "from the left to the right".
    private static SegmentsComparator segmentsComparator;
    private static Comparator<Point> PointsComparatorX;
    
    // Dealing with the boundary cases
    private static boolean foundBoundaryCase;
    
    // Here a pair of intersecting segments is stored
    private static Segment segm1, segm2;
}