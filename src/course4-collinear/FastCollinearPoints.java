import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    private Point[] points;

    private static class SlopeOrder implements Comparator<Point> {
        private Point p;

        public SlopeOrder(Point p) {
            this.p = p;
        }

        @Override
        public int compare(Point p1, Point p2) {
            return Double.compare(p.slopeTo(p1), p.slopeTo(p2));
        }
    }

    /**
     * finds all line segments containing 4 or more points
     *
     * @param points points
     */
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        this.points = new Point[points.length];
        // any point in the array is null
        for (int i = 0; i < points.length; i++) {
            Point point = points[i];
            if (point == null) {
                throw new IllegalArgumentException();
            }

            this.points[i] = point;
        }

        //  the argument to the constructor contains a repeated point
    }

    /**
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return segments().length;
    }

    /**
     * @return the line segments
     */
    public LineSegment[] segments() {
        int pointsLen = points.length;
        if (pointsLen <= 3) {
            return new LineSegment[]{};
        }

        Arrays.sort(points);

        int maxLen = pointsLen * 2;

        Point[] startPoints = new Point[maxLen];
        Point[] endPoints = new Point[maxLen];
        int k = 1, n = 0;
        for (Point point : points) {
            Comparator<Point> so = new SlopeOrder(point);

            // sort
            Arrays.sort(points, so);

            Point[] tmpPoints = new Point[maxLen];
            tmpPoints[0] = point;
            for (int j = 1; j < points.length - 1; j++) {
                if (Double.compare(points[j].slopeTo(point), points[j + 1].slopeTo(point)) == 0) {
                    tmpPoints[k++] = points[j];
                    if (j + 1 == points.length - 1) {
                        tmpPoints[k++] = points[j + 1];
                    }
                } else {
                    if (k > 1) {
                        tmpPoints[k++] = points[j];
                    }

                    n = addPoints(startPoints, endPoints, k, n, tmpPoints);

                    // reset k and tmpPoints
                    k = 1;
                    tmpPoints = new Point[points.length];
                    tmpPoints[0] = point;
                }
            }

            n = addPoints(startPoints, endPoints, k, n, tmpPoints);

            // reset k
            k = 1;

            Arrays.sort(points);
        }

        LineSegment[] lineSegments = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            if (startPoints[i] != null) {
                lineSegments[i] = new LineSegment(startPoints[i], endPoints[i]);
            }
        }

        return lineSegments;
    }

    private static int addPoints(Point[] startPoints, Point[] endPoints, int k, int n, Point[] tmpPoints) {
        if (k >= 4) {
            Arrays.sort(tmpPoints, 0, k);
            Point tmpPointStart = tmpPoints[0];
            Point tmpPointEnd = tmpPoints[k - 1];

            boolean add = true;
            for (int i = 0; i < startPoints.length; i++) {
                Point startPoint = startPoints[i];
                Point endPoint = endPoints[i];
                if (startPoint != null && endPoint != null && tmpPointStart.compareTo(startPoint) == 0 && tmpPointEnd.compareTo(endPoint) == 0) {
                    add = false;
                    break;
                }
            }

            if (add) {
                startPoints[n] = tmpPointStart;
                endPoints[n] = tmpPointEnd;
                n++;
            }
        }
        return n;
    }

    public static void main(String[] args) {
        // read the n points from a file
//        Integer[] inputArr = new Integer[]{6,8,10,11,20,40,50,56,80,100,
//        equidistant,grid6x6,horizontal50,vertical100,kw1260,mystery10089,
//        rs1423};

        In in = new In(args[0]);
//        In in = new In("inputFiles/grid8x8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
