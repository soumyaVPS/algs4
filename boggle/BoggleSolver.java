
import java.lang.String;
import java.util.ArrayList;
import java.util.HashSet;

public class BoggleSolver {
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    private static int R = 26;

    private class DictNode {
        private Integer val;
        private DictNode[] next = new DictNode[ R ];
    }
    private DictNode rootDict;


    private Integer  get( String key ) {

        DictNode x = get( rootDict, key, 0 );

        if (x == null) return null;
        return x.val;
    }

    private DictNode get( DictNode x, String key, int d ) {
        //StdOut.println( "d is " + d );
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt( d );
        return get( x.next[ c-'A' ], key, d + 1 );
    }


    private DictNode put( DictNode x, String key, int val, int d ) {
        if (x == null) x = new DictNode();
        if (d == key.length()) {
            x.val = val;
            return x;
        }
        char c = key.charAt( d );
        x.next[ c-'A' ] = put( x.next[ c-'A' ], key, val, d + 1 );
        return x;
    }

    public BoggleSolver( String[] dictionary ) {
        rootDict = new DictNode();

        for (int i = 0; i < dictionary.length; i++) {
            put( rootDict, dictionary[ i ],dictionary[i].length(), 0 );

        }

    }


    private boolean visitedToggle[][];
    private void traverseBoard( int i,int j, BoggleBoard board,DictNode dNode,  HashSet<String> list,String word) {

        char letter = board.getLetter(i,j);
         int COLS = board.cols();
         int ROWS = board.rows();
        if (dNode.next[letter-'A'] ==null) {
            //StdOut.printf ("null node at %d %d for letter=%c \n",x,y,letter);
            return;
        }
        visitedToggle[i][j] = true;
        String curStr=word+letter;
        DictNode curNode = dNode.next[letter-'A'];
        if (letter =='Q') {
            if(curNode.next['U'-'A'] != null) {
                curNode = curNode.next[ 'U' - 'A' ];
                curStr += 'U';
            }
        }

        //StdOut.printf( "i = %d j = %d letter= %c curStr = %s \n",i,j,letter,curStr);
        if (curNode.val!=null){
          //  StdOut.println(curStr + " " +curNode.val);
            if (curStr.length() > 2 )
                list.add(curStr);
        }

        if (i<ROWS-1 && !visitedToggle[i+1][j]) {
            traverseBoard( i+1, j, board,curNode, list,curStr );
        }
        if (i<ROWS-1 && j<COLS-1 && !visitedToggle[i+1][j+1]) {
                traverseBoard( i+1, j+1, board,curNode, list,curStr );
        }
        if (j<COLS-1)
            if(!visitedToggle[i][j+1])
        {
            traverseBoard( i, j + 1, board,curNode,list, curStr);
        }
        if (j<COLS-1 && i>0 && !visitedToggle[i-1][j+1]) {
            traverseBoard( i - 1, j + 1, board, curNode,list, curStr);
        }
        if ( i>0 && !visitedToggle[i-1][j]) {
            traverseBoard( i - 1, j, board, curNode,list, curStr);
        }
        if (i>0 && j>0 && !visitedToggle[i-1][j-1]) {
            traverseBoard( i - 1, j - 1, board, curNode,list, curStr);
        }
        if(j>0 && !visitedToggle[i][j-1]) {
            traverseBoard( i, j - 1, board,curNode,list, curStr);
        }
        if(j>0 && i<ROWS - 1 && !visitedToggle[i+1][j-1]) {
                traverseBoard( i+1, j - 1, board,curNode,list, curStr);
        }
        visitedToggle[i][j] = false;
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords( BoggleBoard board ) {
        visitedToggle = new boolean[board.rows()][board.cols()];
        HashSet<String> list = new HashSet<String>();
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j<board.cols(); j++) {
                traverseBoard( i, j, board,rootDict,list,"" );

            }
        }
        return list;


    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)

    public int scoreOf(String word) {
        return get(word);
    }

    public static void main(String[] args)
    {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board))
        {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }

}