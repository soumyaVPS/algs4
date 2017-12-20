import java.io.IOException;
import java.lang.IllegalArgumentException;

public class Outcast {
    private WordNet net;

    public Outcast( WordNet wordnet ) {        // constructor takes a WordNet object
        net = wordnet;
    }

    public String outcast( String[] nouns )   // given an array of WordNet nouns, return an outcast
    {
        int farthestDistance = -1;
        String outcastNoun = "oxo";
        for (String nounA : nouns) {
            int dt = 0;
            for (String nounB : nouns) {
                //System.out.println(nounA + " " + nounB);
                int dist = net.distance( nounA, nounB );
                if (dist != -1) {
                    dt += dist;
                }
            }

            if (dt > farthestDistance) {
                outcastNoun = nounA;
                farthestDistance = dt;

            }
        }
        return outcastNoun;

    }

    public static void main( String[] args ) {
        try {
            WordNet wordnet = new WordNet( args[ 0 ], args[ 1 ] );
            Outcast outcast = new Outcast( wordnet );
            for (int t = 2; t < args.length; t++) {
                In in = new In( args[ t ] );
                String[] nouns = in.readAllStrings();
                StdOut.println( args[ t ] + ": " + outcast.outcast( nouns ) );
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}