import java.util.ArrayList;
import java.lang.NullPointerException;


public class KdTree {

    //private class PointNode implements iterable<Point2D> {
    private class PointNode {
        PointNode left;
        PointNode right;
        PointNode parent;
        Point2D point;
        boolean odd;
        int rank;

        private PointNode( Point2D p, boolean isOdd ) {
            point = p;
            odd = isOdd;
            rank = 1;
            parent = null;
            left = null;
            right = null;
           // StdOut.println( "New Point " + point + " " + odd );
        }

        public PointNode( Point2D p ) {
            this( p, true );
        }

        public double y() {
            return point.y();
        }

        public double x() {
            return point.x();
        }

        public void draw() {
            point.draw();
        }

        public boolean add( Point2D p ) {
            if(p.equals(this.point)) {
                return false;
            }
            boolean added = true;
            if (this.odd) {
                if (p.x() < point.x()) {
                  // StdOut.println( p + "Added to left of " + this.point );
                    if (left != null) {
                        added = left.add( p );
                    } else {
                        left = new PointNode( p, false );
                        left.parent = this;
                    }
                } else {
                    //StdOut.println( p + "Added to right of " + this.point );
                    if (right != null) {
                       added= right.add( p );
                    } else {
                        right = new PointNode( p, false );
                        right.parent = this;
                    }
                }
            } else if (!this.odd) {
                if (p.y() < point.y()) {
                    //StdOut.println( p + "Added to left of " + this.point );
                    if (left != null) {
                        added = left.add( p );
                    } else {
                        left = new PointNode( p, true );
                        left.parent = this;
                    }
                } else {
                    //StdOut.println( p + "Added to right of " + this.point );
                    if (right != null) {
                        added = right.add( p );
                    } else {
                        right = new PointNode( p, true );
                        right.parent = this;
                    }
                }
            }
            this.rank++;
            return true;

        }

        public int size() {
            return rank;
        }

        public int compareTo( Point2D p ) {
            if (this.odd) {
                if (this.point.x() < p.x()) {
                    return -1;
                } else if (this.point.x() > p.x()) {
                    return 1;
                } else
                    return 0;
            } else {
                if (this.point.y() < p.y()) {
                    return -1;
                } else if (this.point.y() > p.y()) {
                    return 1;
                } else
                    return 0;
            }
        }

        public boolean contains( Point2D p ) {
            if (pTree == null) {
                return false;
            }
            if (this.point.equals( p )) {
                return true;
            }
            if (this.odd) {
                if (p.x() < point.x()) {
                    if (left != null)
                        return left.contains( p );
                } else if (right != null) {
                    return right.contains( p );
                }
            } else {
                if (p.y() < point.y()) {
                    if (left != null)
                        return left.contains( p );
                } else if (right != null) {
                    return right.contains( p );
                }
            }
            return false;
        }
    }

    private PointNode pTree;

    /*private KdIterator implements Iterator<Point2D> {
    PointNode currNode;
    public KdIterator () {
    currNode = null;
    }
    public boolean hasNext() {
    if (pTree = null)
    return false;
    else if (currNode == null)
    return true;

    if (currNode != null) {
    if (currNode.left != null || currentNode.right != null) {
    return true;
    } else if  (currNode.parent !=null) {
    if (currNode == currNode.parent.left) {
        if (currNode.parent.right != null) {
            return true;
        }
    }
    }

    }
    return false;

    }

    public Object next() {
    if (pTree = null)
    return null;
    else if (currNode == null)
    currNode=pTree;

    else if (currNode != null) {
    if (currNode.left != null ) {
    currNode = currNode.left;
    } else if (currNode.right != null) {
    currNode = currNode.right;

    }
    else if  (currNode.parent !=null) {
    if (currNode == currNode.parent.left) {
        if (currNode.parent.right != null) {
            currNode = currNode.parent.right;
        }
    }
    }

    }
    return currNode;

    return null;
    }

    public void remove() {
    throw new UnSupportedException();
    }
    }
    */
    public KdTree()                               // construct an empty set of points
    {

    }

    public boolean isEmpty()                      // is the set empty?
    {
        if (pTree == null)
            return true;
        return false;
    }

    public int size()                         // number of points in the set
    {
        if (pTree != null)
            return pTree.size();
        return 0;

    }

    public void insert( Point2D p )              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new NullPointerException();
        if (pTree == null) {
            pTree = new PointNode( p );
        } else {
            pTree.add( p );
        }

    }

    public boolean contains( Point2D p )            // does the set contain point p?
    {
        if (p == null) throw new NullPointerException();
        return pTree.contains( p );
    }

    public void draw()                         // draw all points to standard draw
    {
        PointNode currNode;
        if (pTree == null)
            return;

        currNode = pTree;
        StdDraw.clear();
        StdDraw.setPenColor( StdDraw.BLACK );
        StdDraw.setPenRadius( .01 );


        while (currNode != null) {
            StdDraw.setPenRadius( .01 );
            StdDraw.setPenColor( StdDraw.BLACK );
           // StdOut.println( "CurrNode: " + currNode.point + " " + currNode.odd );

            currNode.draw();
/*
            try {
                Thread.sleep( 1000 );
            } catch (Exception e) {
            }
            */
            StdDraw.setPenColor( StdDraw.BLACK );

            if (currNode.odd) {

                StdDraw.setPenRadius( .001 );
                StdDraw.setPenColor( StdDraw.RED );
                double x = currNode.x();
                double y1 = 0.0;
                double y2 = 1.0;

                if (currNode.parent == null) y1 = 0;
                else {
                    if (currNode.parent.right == currNode) {
                        y1 = currNode.parent.y();
                        //if (currNode.parent.parent !=null && currNode.parent.parent.parent != null) {
                        //    y2 = currNode.parent.parent.parent.y();
                        //}
                    } else {
                        y2 = currNode.parent.y();
                        //if (currNode.parent.parent !=null && currNode.parent.parent.parent != null) {
                        //    y1 = currNode.parent.parent.parent.y();
                        //}
                    }

                }
                StdDraw.line( x, y2, x, y1 );
             //   StdOut.printf( "%f %f %f %f", x, y2, x, y1 );
            } else {
                StdDraw.setPenRadius( 0.001 );
                StdDraw.setPenColor( StdDraw.RED );
                double y = currNode.y();
                double x1 = 0.0;
                double x2 = 1.0;


                if (currNode.parent.right == currNode) {
                    x1 = currNode.parent.x();
                    //if (currNode.parent.parent != null && currNode.parent.parent.parent != null) {
                    //  x2 = currNode.parent.parent.parent.x();
                    //}
                } else {
                    x2 = currNode.parent.x();
                    //if (currNode.parent.parent != null && currNode.parent.parent.parent != null) {
                    //    x1 = currNode.parent.parent.parent.x();
                    //}
                }

                StdDraw.line( x1, y, x2, y );
            }
            StdDraw.show( 0 );
            if (currNode.left != null) {
                currNode = currNode.left;
            } else if (currNode.right != null) {
                currNode = currNode.right;
            } else if (currNode.parent != null && currNode == currNode.parent.left) {
                    while (currNode.parent.right == null) {
                        //StdOut.println("In loop 1 .. ");
                        currNode = currNode.parent;
                        if (currNode == null || currNode.parent == null)
                            currNode = null;
                            break;
                    }
                    if (currNode != null)
                        currNode = currNode.parent.right;
            } else if (currNode.parent != null && currNode == currNode.parent.right) {
                    currNode = currNode.parent;
                    while (currNode.parent == null || currNode.parent.right == currNode) {
                        //StdOut.println("In loop 1 .. ");
                        currNode = currNode.parent;
                        if (currNode == null || currNode.parent == null) {
                            currNode = null;
                            break;
                        }
                    }
                    if (currNode != null)
                        currNode = currNode.parent.right;

            } else { //After processing all node. Null currNode and exit.
                    currNode =null;
            }

        }
    }

    private void range2( RectHV rect, PointNode pnode, ArrayList<Point2D> rangeList ) {
        if (pnode==null) return;
        if (rect.contains(pnode.point)) {
            rangeList.add(pnode.point);
        }

            if (pnode.left !=null){
                range2( rect, pnode.left,rangeList );
            }

            if (pnode.right !=null) {
                range2( rect, pnode.right,rangeList );
            }

    }


    public Iterable<Point2D> range( RectHV rect )             // all points that are inside the rectangle
    {
        ArrayList <Point2D> rangeList = new ArrayList<Point2D> ();
        range2(rect, pTree, rangeList);

        return rangeList;

    }

private Double maxDist;
private PointNode nearestNode;

    private void nearest2( Point2D p, PointNode pnode ) {
        if (pnode==null) return;
        if (p.distanceTo( pnode.point ) < maxDist) {
            maxDist = p.distanceTo( pnode.point );
            nearestNode = pnode;
           // StdOut.println("maxDist " + maxDist + " point "+ nearestNode.point  );
        }
        //if (pnode.compareTo( p ) > 0 ) {
            if (pnode.left !=null){
                if (pnode.odd) {
                    if (pnode.x()<p.x() && p.x()-pnode.x() < maxDist || pnode.x()>p.x()) {
                           nearest2(p, pnode.left);
                    }
                } else {
                    if (pnode.y()<p.y() && p.y()-pnode.y() < maxDist || pnode.y()>p.y()) {
                        nearest2(p, pnode.left);
                    }
                }

            }

        //} else {
            if (pnode.right !=null) {
                if (pnode.odd) {
                    if (pnode.x()>p.x() && pnode.x()-p.x() < maxDist || pnode.x()<p.x()) {
                        nearest2(p, pnode.right);
                    }
                } else {
                    if (pnode.y()>p.y() && pnode.y()-p.y() < maxDist || pnode.y()<p.y()) {
                        nearest2(p, pnode.right);
                    }
                }
            }
        //}
    }

    public Point2D nearest( Point2D p )             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) throw new NullPointerException();

        maxDist = Double.POSITIVE_INFINITY;
        nearestNode = null;
        nearest2( p, pTree );
        return nearestNode==null?  null:  nearestNode.point;


    }

    public static void main( String[] args )                  // unit testing of the methods (optional)
    {
        String filename = args[ 0 ];
        In in = new In( filename );

        StdDraw.show( 0 );
        StdDraw.clear();

        KdTree kd = new KdTree();
        int count = 0;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D( x, y );

            kd.insert( p );
            count ++;
            if (kd.size() != count) {
                StdOut.println( " kd size" + kd.size() );
                StdOut.println( "contains " + kd.contains( p ) );
            }

        }
        StdOut.println( " After insert" );

        kd.draw();

        StdOut.println("nearest neighbor" + kd.nearest(new Point2D(0.25, 0.25)));
    }

}