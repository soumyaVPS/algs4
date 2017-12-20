import java.util.TreeSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.NullPointerException;


public class PointSET {
    private TreeSet <Point2D> pSet = new TreeSet <Point2D>();
   public         PointSET()                               // construct an empty set of points 
   {
       pSet = new TreeSet<Point2D> ();
   }
   public           boolean isEmpty()                      // is the set empty? 
   {
       return (pSet.size()==0)? true: false;
       
   }
   public               int size()                         // number of points in the set 
   {
       return pSet.size();
       
   }
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       if (p==null) throw new NullPointerException();
       pSet.add(p);
   }
   public           boolean contains(Point2D p)            // does the set contain point p? 
   {
       if (p==null) throw new NullPointerException();
       return pSet.contains(p);
       
   }
   public              void draw()                         // draw all points to standard draw 
   {
         Iterator<Point2D> it = pSet.iterator();
       while(it.hasNext()) {
           Point2D p = it.next();
           p.draw();
       }
       
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
       ArrayList <Point2D> rangeList = new ArrayList<Point2D> ();
       Iterator<Point2D> it = pSet.iterator();
       
       while(it.hasNext()) {
           Point2D p = it.next();
           if(rect.contains(p)) {
                rangeList.add(p);
           }
       }
       return rangeList;
   }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       if(p==null) throw new NullPointerException();
       Iterator<Point2D> it = pSet.iterator();
       double dist = Double.POSITIVE_INFINITY;
       Point2D nearestP = null;
       
       while(it.hasNext()) {
           
           Point2D p1 = it.next();
           //StdOut.println(p1 +" " + p);
           if (p1.distanceTo(p)<dist) {
                nearestP = p1;
                dist = p1.distanceTo(p);
           }
       }
       return nearestP;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
          String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            
            brute.insert(p);
        }
         StdOut.println(brute.nearest(new Point2D(0.25, 0.25)));
   }
}