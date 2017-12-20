import java.util.NoSuchElementException;
import java.util.Iterator;

public class Subset {
	public static void main(String[] args) {
		RandomizedQueue<Integer> dq = new RandomizedQueue<Integer>();
		int k = Integer.parseInt(args[0]);
		int n = 0;
		
        for (int i=0;i<k;i++) {            
        try {
            int num = StdRandom.uniform(2);
            if (num == 0) {
                StdOut.println("Enque " + i);
                dq.enqueue(new Integer(i));
            }else 
                StdOut.println("!!!Dequeued " + dq.dequeue());
            
            n++;
            }catch ( NoSuchElementException ex) {
                StdOut.println("No such Elem");
            } catch (Exception ex1) {
                StdOut.println(ex1);
            }
		}
        
		/*Iterator<Integer> it = dq.iterator();
        StdOut.println ("Iterator output  *******");
		
        while (it.hasNext())
			StdOut.println("elem " +it.next());
			
        StdOut.println ("Dequeue output *******");
        
		for (int i = 0; ; i++ ) {
        
			StdOut.println(dq.dequeue());
		}*/
	}
    
	/*public static void main(String[] args) {
		Deque<Integer> dq = new Deque<Integer>();
		int k = Integer.parseInt(args[0]);
		int n = 0;
		StdOut.println(k);
        
		//while ( !StdIn.isEmpty()  && n++<k) {
		//	String str = StdIn.readString(); 
		//	dq.addFirst(str);
		//}
        for (int i=0;i<k;i++) {
           dq.addFirst(new Integer(i));
           dq.addLast(new Integer(i+k));
           }
        
		Iterator<Integer> it = dq.iterator();
		while (it.hasNext())
			StdOut.println("elem " +it.next());
			
		for (int i = 0; ; i++ ) {
            StdOut.println(dq.removeFirst());
			StdOut.println(dq.removeLast());
		}
	}*/
	
}