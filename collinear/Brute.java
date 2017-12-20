public class Brute {
   public static void main(String[] args) {
	   String filename = args[0];
       In in = new In(filename);
       int N = in.readInt();
       Point pArray[] = new Point[N];
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       
       for (int i = 0; i < N; i++) {
           int x = in.readInt();
           int y = in.readInt();
           Point p = new Point(x, y);
           pArray[i] = p;
           //p.drawTo(new Point(0,0));
           p.draw();
       }
       StdDraw.setPenColor(StdDraw.CYAN);
       Insertion.sort(pArray);
       for (int i =0;i <N; i++) {
    	   for (int j =i+1; j<N; j++) {
    		   double slopeIJ = pArray[i].slopeTo(pArray[j]);
    		   for (int k = j+1 ; k <N; k++) {
    			   if (slopeIJ != pArray[j].slopeTo(pArray[k]))
    					   continue;
    			   for (int l = k+1 ; l <N; l++) {
    				   if (slopeIJ == pArray[k].slopeTo(pArray[l])) {
    					   //pArray[i].drawTo(pArray[j]);
    					   //pArray[j].drawTo(pArray[k]);
    					   pArray[i].drawTo(pArray[l]);	
    					   StdDraw.setPenRadius(0.01);
    					   pArray[i].draw();pArray[j].draw();pArray[k].draw();pArray[l].draw();
    					   StdDraw.setPenRadius(0.002);
    		
    					   StdOut.println(pArray[i] + "->"+pArray[j] +"->"+pArray[k] +"->"+ pArray[l]); 
    				   }
    			   }
    		   }
    	   }
       }
    	   
       // display to screen all at once
       StdDraw.show(0);
   }
   
}
