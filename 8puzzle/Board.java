import java.util.ArrayList;
public class Board {
    private int [][] blocks;
    private int N ;
    private int zeroi, zeroj;
    private int moves;
    //static final int[][] goal;
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        this.N =  blocks[0].length;
        this.blocks = new int[N][N];
        
        for (int i=0; i<N; i++) {
            for(int j=0;j<N;j++) {
                this.blocks[i][j] = blocks[i][j];
                if(blocks[i][j] ==0) {
                    this.zeroi =i; this.zeroj = j;        
                }           
            }
        }
      
        moves = 0;
    }           
    private  Board(Board b) { //make a copy of board
        this(b.blocks) ;
        this.moves = b.moves;
    }
    public int dimension()                 // board dimension N
    {
        return blocks[0].length;
    }
    
    public int hamming()                   // number of blocks out of place 
    { 
        int value = 0;//moves;
        
        for (int i = 0; i< N ;i++) 
            for (int j= 0;j<N;j++) 
                if (blocks[i][j] != 0 && i*N + j+1!= blocks[i][j]) 
                        value+=1;
        return value;
    }
    private int dist(int i,int j)  {
        int goali = (blocks[i][j]-1) / (N) -i;
        
        int goalj = (blocks[i][j]-1) % (N) -j;
        
        if (goali<0) goali =  -goali;
        if (goalj<0) goalj =  -goalj;
        int d = goali+goalj;
                //StdOut.println(locks[i][j], goali, goalj, d)

        return d;
    }
    
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int value = 0;
        for (int i = 0;i<N;i++) 
            for (int j=0; j<N; j++) {
                if (blocks[i][j] != 0) 
                    
                    value+= dist(i,j);
            }
        return value;
    }
    
    public boolean isGoal()                // is this board the goal board?
    { for (int i =0; i<N; i++) {
        for (int j =0; j<N; j++) {
          if (i==N-1 && j == N-1){ break;}
          if (this.blocks[i][j] != i*N + j+1 ) 
            return false;
    }}
      return true;
    }
    
    public Board twin()                    // a boadr that is obtained by exchanging two adjacent blocks in the same row
    {
        int x,y;
        y = StdRandom.uniform(N-1);
        if ( y == zeroi) {
            y = (y+1)%N;
        }
        
         x = StdRandom.uniform(N-1);
        
        Board b = new Board(this);
        int t = b.blocks[y][x]; 
        b.blocks[y][x] = b.blocks[y][x+1];
        b.blocks[y][x+1] = t;
        return b;
    }
    public boolean equals(Object y)      {  // does this board equal y?
        if (y==0) { throw new NullPointerException();}
        
        if ( y instanceof Board) {
            
            Board other = (Board)y;
            if (other.dimension()!= this.dimension()) {
                return false;
            }
            for (int i =0;i<N;i++)
                for(int j=0;j<N;j++) 
                    if (blocks[i][j] != other.blocks[i][j]) 
                        return false;
            return true;
        }
        if (y instanceof String) {
            String other  = (String) y;
            if (other.equals(this.toString())) 
                return true;
        }
        return false;
    }
    
    private void moveBlock(int i,int j){
        //StdOut.printf("moveBlock %d %d %d %d \n", zeroi, zeroj, i, j);
        blocks[zeroi][zeroj] =  blocks[zeroi+i][zeroj+j] ;
        blocks[zeroi+i][zeroj+j] =0;
        zeroi = zeroi+i;
        zeroj = zeroj+j;
        moves++;
    }
    
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        ArrayList<Board> nbors = new ArrayList<Board> ();
        //StdOut.println("this 1:: \n" +this);
        if (zeroi !=0) {
            Board b1 = new Board(this); 
            b1.moveBlock(-1,0) ;
          //  StdOut.println("b1 1:: \n" +b1);
            nbors.add(b1);
        }
        if (zeroi != N-1) {
            Board b1 = new Board(this); 
            b1.moveBlock(1,0) ;
            //StdOut.println("b1 2:: \n" +b1);
            nbors.add(b1);
        }
        if (zeroj != 0) {
            //StdOut.println("this 3:: \n" +this);
            //StdOut.printf("zeroi  %d zeroj %d \n", zeroi, zeroj);
            Board b1 = new Board(this); 
            //StdOut.println("b1 3:: \n" +b1);
            b1.moveBlock(0,-1) ;
            
            nbors.add(b1);
        }
        if (zeroj != N-1) {
            Board b1 = new Board(this); 
            b1.moveBlock(0,1) ;
            //StdOut.println("b1 4::\n " +b1);
            nbors.add(b1);
        }
        
        return nbors;
    }
    
    public String toString()               // string representation of this board (in the output format specified below)
    {
        String s = N + "\n";
        for (int i =0;i<N;i++) {
            for(int j=0;j<N;j++) 
                s +=blocks[i][j] + " ";
            s+= "\n";
        }
            
        return s;
    }
    public static void main(String[] args) // unit tests (not graded)
    {
    }
    
    
    
}