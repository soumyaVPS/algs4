import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;
import java.lang.Iterable;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item> {
	private Item array[];
	private int numItems;
	private int beg;// redundant
	private int end;
   private void debug(String msg) {
       StdOut.println(msg +" "+ numItems + " " + beg + " " + end +" "+ array.length);
   }
   public RandomizedQueue()    {       // construct an empty randomized queue
	   array = (Item[]) new Object[1];
       beg = 0; end =0; numItems = 0;
   }
   public boolean isEmpty()      {     // is the queue empty?
	   return numItems==0;
   }
   public int size()          {        // return the number of items on the queue
	   return numItems;
   }
   
   public void enqueue(Item item)  {   // add the item
      
       if (item == null) {
            throw new NullPointerException();
       }

	   if (numItems == array.length) {
		   resize(2*numItems);
	   }
       if (numItems != 0) {
           end ++;
       }

       array[end] = item;
       numItems++;
       debug("enqueue out");


       //StdOut.println("!!!!numItems "+ numItems);

       //for (int i=0;i<array.length;i++) StdOut.print(" " +array[i]);

   }


   
   private void resize(int capacity)
   {
	   Item[] copy =(Item[]) new Object[capacity];
	   
	   for (int i = 0; i < numItems; i++) {
          copy[i] = array[i];
       }
       
	   array = copy;
	   beg = 0;
	   end = numItems -1;
   }
   public Item dequeue() {             // delete and return a random item
       
       if (numItems ==0) {
            throw new NoSuchElementException();
       }

       int r = 0;

       if (numItems !=0) {
           r = StdRandom.uniform( numItems );
       }
       
       
       
	   Item item = array[r];
	   array[r] = array[end];
       array[end] = null;

       if (end !=0)
        end--;
	   numItems --;
	   if (numItems > 0 && numItems == array.length/4)
		   resize(array.length/2);
	   debug("dequeue out");
	   return item;
   }
   
   public Item sample(){               // return (but do not delete) a random item
       if (numItems == 0) return null;
       
       int index = StdRandom.uniform(numItems);
	   return array[index];
   }
   
   private class  QueIterator implements Iterator<Item> {
	   int currIndex, end, size;
	   int randIndex[];
	   QueIterator (int currIndex, int end, int size) {
		   this.currIndex = currIndex;
		   this.end = end;
		   this.size = size;
           randIndex = new int[size];
           //rand shuffle
           for (int i = 0;i<size;i++) randIndex[i] = i;
           for (int i = 1; i< size; i++) {
               int r = StdRandom.uniform( i );
               int y = randIndex[r];
               randIndex[r] = randIndex[i];
               randIndex[i] = y;
           }
           //StdOut.println ("QueIterator currIndex, end,size "+currIndex + " " +this.end + " " + this.size);
	   }
	   
	   public boolean hasNext() {
		   return  (currIndex != size); 
		   
	   }
	   public Item next() throws NoSuchElementException{
		   if (size == 0 || currIndex >= size)
			   throw new NoSuchElementException();		   
               
           return array[currIndex++]; 
           
		   
	   }
	   
	   public void remove() {
		   throw new UnsupportedOperationException();
	   }
   }
   public Iterator<Item> iterator() {  // return an independent iterator over items in random order
	   return new QueIterator(beg, end, numItems);
   }
}