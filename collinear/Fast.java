import java.util.Arrays;
import java.util.TreeSet;
import java.util.Iterator;
public class Fast {
   public static void main(String[] args) {
	   String filename = args[0];
       In in = new In(filename);
       int N = in.readInt();
       Point refArray[] = new Point[N];
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       
         StdDraw.setPenRadius(0.02);
       for (int i = 0; i < N; i++) {
           int x = in.readInt();
           int y = in.readInt();
           Point p = new Point(x, y);
           refArray[i] = p;
           //p.drawTo(new Point(0,0));
           p.draw();
       }
       StdDraw.setPenRadius(0.002);
       Point[] pArray =  Arrays.copyOf(refArray, refArray.length);
       Insertion.sort(refArray);
       //for ( int i = 0; i< N; i++) 
       //     StdOut.print(refArray[i]);
       //StdOut.println();
       StdDraw.setPenColor(StdDraw.CYAN);
       TreeSet<String> lineset = new TreeSet<String> ();
       
       for ( int i = 0; i< N; i++)  {
          Point origin = refArray[i];
          Insertion.sort(pArray, origin.SLOPE_ORDER);
          //for ( int l = 0; l< N; l++) 
          //  StdOut.print(pArray[l]);
          //StdOut.println("***");
          int c = 1;
          int k = 1;
          boolean newLine = false;
          for (int j = 1; j<N-1; j++) { 
                //StdOut.println("c is "+c + " "+ pArray[j] + " " + pArray[j +1] + origin.slopeTo(pArray[j]) + " " +origin.slopeTo(pArray[j+1]));
                if ((origin.slopeTo(pArray[j]) ==Double.POSITIVE_INFINITY && origin.slopeTo(pArray[j+1])==Double.POSITIVE_INFINITY) ||
                   (origin.slopeTo(pArray[j]) == origin.slopeTo(pArray[j+1]))) {
                  
                    c++;
                  //  StdOut.println(j+"C is " +c);
                    
                }
                else {
                    newLine = true;
                }
                if (newLine || j==N-2) {
                    if (c > 2) {
                       
                       Point[] sp  = new Point[c+1] ;
                       int z=0;
                       for ( int m = k; m<k+c; m++) {
                            sp[z++] = pArray[m];
                       }
                       sp[z] = origin;
                       Insertion.sort(sp);
                       String spStr = "";
                       for( z = 0; z< sp.length; z++) {
                            spStr+=sp[z];
                           
                            spStr+= z < (sp.length-1) ? "->": "";
                            
                            
                       }
                       if (!lineset.contains(spStr))  {
                            lineset.add(spStr);
                            StdOut.println(spStr);
                            sp[0].drawTo(sp[c]);
                            
                       }
                   }
                       
                   c = 1;
                   k = j+1;
                   newLine = false;
                 
                }
        }
    }
    }
 
}