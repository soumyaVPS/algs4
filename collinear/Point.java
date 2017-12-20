/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.lang.NullPointerException;
public class Point implements Comparable<Point> {

	private class SlopeOrder implements Comparator<Point> {
		public int compare(Point a, Point b) {
           if (a == null || b==null) throw new NullPointerException();
            if (slopeTo(a) == Double.POSITIVE_INFINITY && slopeTo(b) == Double.POSITIVE_INFINITY) 
                return 0;
			return slopeTo(a) - slopeTo(b) >0? 1 
            :slopeTo(a) - slopeTo(b) <0? -1
            : 0;
		}
	}
    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if (that == null) throw new NullPointerException();
        if (this.x == that.x && this.y == that.y) return Double.NEGATIVE_INFINITY;
        else if (this.x == that.x) return Double.POSITIVE_INFINITY;
        else if (this.y == that.y) return 0;
        return ((double)that.y - (double)this.y) / ((double)that.x - (double)this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if (this.y < that.y || (this.y == that.y && this.x < that.x)) {
            return -1;
        } else if (this.x == that.x && this.y==that.y) {
            return 0;
        }
        return 1;
           
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    /*public static void main(String[] args) {
        Point a = new Point(1000,2000);
        Point b = new Point(20,40);
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        a.draw();
        b.draw();
        b.drawTo(a);
        StdDraw.show(0);

        StdOut.println(a.compareTo(b));
        StdOut.println(a.slopeTo(b));
        StdOut.println(a.slopeTo(a));
    }*/
}