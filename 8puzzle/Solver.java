import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeMap;
public class Solver {
    private Board initial;
    private ArrayList <Board> soln;
    private int  solvable;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        this.initial = initial;
        
        /*StdOut.println(initial);
        StdOut.println(initial.hamming());
       
        Iterator<Board> it = initial.neighbors().iterator();
        while(it.hasNext()) {
            Board b1 = it.next();
            StdOut.println(b1);
            StdOut.println(b1.hamming());
        }
        System.exit(0);
        */
        this.soln = null;
        this.solvable = 0;
    }
    
    
    private class ManhattanComparator implements Comparator<Board>{ 
                            public int compare(Board a, Board b) {
                                int aval = a.manhattan();int bval = b.manhattan();
                                if (aval>bval) {
                                    return 1;
                                } else if (bval > aval) {
                                    return -1;
                                }
                                return 0;
                            }}
    
                            
    public boolean isSolvable()            // is the initial board solvable?
    {
        ArrayList <Board> soln = new ArrayList<Board>();
        TreeMap<String,Character> twinBHash = new TreeMap<String,Character>();
        TreeMap<String,Character> origHash = new TreeMap<String,Character>();
        
       
        MinPQ<Board> boardsPQ = new MinPQ<Board>(new ManhattanComparator());
        Board twinB = initial.twin();
        boardsPQ.insert(initial);
        origHash.put(initial.toString(),null);
        
        
        MinPQ<Board> twinPQ =  new MinPQ<Board>(new ManhattanComparator());                               
        twinPQ.insert(twinB);
        
        //StdOut.println("initial \n"+initial);
        //StdOut.println("twinB \n"+twinB);
        twinBHash.put(twinB.toString(),null);
        
        while (!boardsPQ.isEmpty() ){//&& !twinPQ.isEmpty()) {
            Board b = boardsPQ.delMin();
            soln.add(b);
            //StdOut.println("Manhattan value " + b.manhattan());
            if (b.isGoal()) {
                solvable = 2;
                break;
            }
            Iterator<Board> it = b.neighbors().iterator();
            while(it.hasNext()) {
                Board b1 = it.next();
                if (!origHash.containsKey(b1.toString())) {
                    boardsPQ.insert(b1);
                    origHash.put(b1.toString(),null);
                }
                    
                
            }
            
            twinB = twinPQ.delMin();
            if (twinB.isGoal()) {
                solvable = 1;
                break;
            }
            it = twinB.neighbors().iterator();
            while(it.hasNext()) {
                Board b1 = it.next();
                if (!twinBHash.containsKey(b1.toString())) {
                    twinPQ.insert(b1);
                    twinBHash.put(b1.toString(),null);
                }
            }
            //StdOut.println(" twin stage: \n" + twinB);
            
            
        /*if (m==12) {
            StdOut.println("boards after 12 cycle ");
            it = boardsPQ.iterator();
            while (it.hasNext()) StdOut.println(it.next());
            break;
        }*/
        }
        
        if (solvable==2){
            soln.remove(soln.get(0));
            this.soln = soln;
        }
        return solvable==2?true : false;
    }
    
    
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        
        if (solvable == 0){ 
            if (isSolvable()) {
                return soln.size();
            }
        } else if (solvable ==2 ){
            return soln.size();
        }
           
        
        return -1;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {   
         if (solvable == 0){ 
            if (isSolvable()) {
                return soln;
            }
        } else if (solvable ==2 ){
            return soln;
        }
        return null;
        
    }
    public static void main(String[] args) // solve a slider puzzle (given below)
    {

    // create initial board from file
    In in = new In(args[0]);
    int N = in.readInt();
    int[][] blocks = new int[N][N];
    for (int i = 0; i < N; i++)
        for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        for (Board board : solver.solution())
            StdOut.println(board);
    }
}
}