import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private Point[] points;

    /**
     * finds all line segments containing 4 points
     *
     * @param points points
     */
    public BruteCollinearPoints(Point[] points) {
        // the argument to the constructor is null
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
        int len = points.length;
        if (len <= 3) {
            return new LineSegment[]{};
        }

        // sort
        Arrays.sort(points);

        LineSegment[] segments = new LineSegment[len * len * len * len];
        int n = 0;
        for (int i = 0; i < len; i++) {
            for (int i1 = i + 1; i1 < len; i1++) {
                for (int i2 = i1 + 1; i2 < len; i2++) {
                    for (int i3 = i2 + 1; i3 < len; i3++) {
                        Point p0 = points[i];
                        Point p1 = points[i1];
                        Point p2 = points[i2];
                        Point p3 = points[i3];

                        double v1 = p0.slopeTo(p1);
                        double v2 = p0.slopeTo(p2);
                        if (v1 != v2) {
                            continue;
                        }

                        double v3 = p0.slopeTo(p3);
                        if (v1 != v3) {
                            continue;
                        }

                        segments[n++] = new LineSegment(p0, p3);
                    }
                }
            }
        }

        LineSegment[] lineSegments = new LineSegment[n];
        for (int i = 0; i < n; i++) {
            if (segments[i] != null) {
                lineSegments[i] = segments[i];
            }
        }

        return lineSegments;
    }

    public static void main(String[] args) {
        // true
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(2, 2),
//                new Point(3, 3),
//                new Point(4, 4)
//        }).segments()));
//
//        // true and vertical
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(1, 2),
//                new Point(1, 3),
//                new Point(1, 4)
//        }).segments()));
//
//        // true and horizontal
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(2, 1),
//                new Point(3, 1),
//                new Point(4, 1)
//        }).segments()));
//
//        // true and self
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(1, 1),
//                new Point(1, 1),
//                new Point(1, 1)
//        }).segments()));
//
//        // true and asc
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(2, 3),
//                new Point(3, 5),
//                new Point(4, 7)
//        }).segments()));
//
//        // true and shuffle
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(4, 7),
//                new Point(3, 5),
//                new Point(2, 3)
//        }).segments()));
//
//        // false
//        System.out.println(Arrays.toString(new BruteCollinearPoints(new Point[]{
//                new Point(1, 1),
//                new Point(2, 3),
//                new Point(3, 5),
//                new Point(4, 6)
//        }).segments()));

        // read the n points from a file
        In in = new In("inputFiles/input48.txt");
//        input8,horizontal5,equidistant,input40
//        In in = new In(args[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
