import java.util.NoSuchElementException;
import java.lang.UnsupportedOperationException;
import java.lang.NullPointerException;
import java.lang.Iterable;
import java.util.Iterator;
public class Deque<Item> implements Iterable<Item> {
   public Iterator<Item> iterator() {
   // return an iterator over items in order from front to end
	   return new QueIterator();
   }
   
   private class Node<Item> {
	   Node<Item> next;
	   Node<Item> prev;
	   Item item;
	   Node (Item i) {
		   item = i;
		   next = null;
		   prev = null;
	   }
   }
   private Node<Item> queue;
   private Node<Item> queTail;
   private int queLen = 0;
   
   private  class  QueIterator implements Iterator<Item> {
       private Node<Item> current;

       public QueIterator() {

            current=queue;
        }
	   public boolean hasNext() {
		   return current !=null;
	   }
	   public Item next() throws NoSuchElementException{
		   if (current == null) { 
			   throw new NoSuchElementException();
		   }
		   Item item = current.item;
		   current = current.next; 
		   return item;
	   }
	   public void remove() {
		   throw new UnsupportedOperationException();
	   }
   }
  
   public Deque(){                     // construct an empty deque
	   queue = null;
	   queTail = null;
	   queLen = 0;
   }
   public boolean isEmpty() {          // is the deque empty?
	   if (queLen > 0) {
		   return false;
	   }
	   return true;
   }
   
   public int size() {                 // return the number of items on the deque
	   return queLen;
   }
   public void addFirst(Item item){    // insert the item at the front
       if (item == null) {
            throw new NullPointerException();
       }
	   Node<Item> node = new Node<Item>(item);
	   
	   if (queue == null) {
		   queTail = node;
		   queue = node;
           queTail.next = null;
           queue.prev = null;
	   }else {
		    node.next = queue;
		    queue.prev = node;
		   	queue = node;   
	   }
	   
	   queLen ++;
   }

   public Item removeFirst()  {        // delete and return the item at the front
	   if (queLen <=0) {
		   throw new NoSuchElementException();
	   }	
	   Node<Item> node = queue; 
       if (queue.next == null ) {
            queue = null;
            queTail = null;
        } else {
            
            queue = queue.next;
           queue.prev = null;
        }
    
	   queLen --;
	   Item item = node.item;
	   return item;
   }
    public void addLast(Item item)  {   // insert the item at the end
        if (item == null) {
            throw new NullPointerException();
        }
        Node<Item> node = new Node<Item>(item);
        if (queue == null ){
            queue = node;
            queTail = node;
            queTail.next = null;
            queue.prev = null;
        }
        else {
            queTail.next = node;
            node.prev = queTail;
            queTail = queTail.next;
            queTail.next = null;
        }

        queLen ++;
        //StdOut.println("Quelen" +queLen);
    }
   public Item removeLast()  {         // delete and return the item at the end
	   if (queLen <=0) {
		   throw new NoSuchElementException();
	   }
	   Node<Item> node = queTail;
       if (queTail.prev == null ) {
            queTail = null;
            queue = null;
       } else {
            queTail = queTail.prev;
            queTail.next = null;
       }
	   queLen --;
	   Item item = node.item;
	   return item;
   }
   
   
}