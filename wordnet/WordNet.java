import java.lang.IllegalArgumentException;
import java.lang.NullPointerException;
import java.util.HashMap;
import java.io.IOException;

public class WordNet {

    private Digraph dg;
    private HashMap<String, Integer> nameToIdHash;
    private HashMap<Integer, String> idToNameHash;

    // constructor takes the name of the two input files
    public WordNet( String synsets, String hypernyms ) throws IOException {
        if (synsets == null || hypernyms == null) {
            throw new NullPointerException( "One of the rguments to WordNet Constructors is null." );
        }
        int numSynsets = uploadSysnetHashMap( synsets );
        uploadDigraph( hypernyms, numSynsets );

    }

    private void uploadDigraph( String file, int numSynsets ) throws IOException {
        In fin = new In( file );
        dg = new Digraph( numSynsets );


        while (fin.hasNextLine()) {
            String line = fin.readLine();
            String[] tokens = line.split( "," );
            if (tokens.length == 2) {

                dg.addEdge( Integer.parseInt( tokens[ 0 ] ), Integer.parseInt( tokens[ 1 ] ) );
            }

        }

    }

    private int uploadSysnetHashMap( String file ) throws IOException {
        nameToIdHash = new HashMap<String, Integer>();
        idToNameHash = new HashMap<Integer, String>();
        In fin = new In( file );
        int numSynsets = 0;
        while (fin.hasNextLine()) {
            numSynsets++;


            String line = fin.readLine();
            String[] tokens = line.split( "," );

            idToNameHash.put( Integer.parseInt( tokens[ 0 ] ), tokens[ 1 ] );
            String[] names = tokens[ 1 ].split( " " );
            if (names.length > 0) {
                nameToIdHash.put( names[ 0 ], Integer.parseInt( tokens[ 0 ] ) );
            }

        }
        return numSynsets;
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nameToIdHash.keySet();

    }

    // is the word a WordNet noun?
    public boolean isNoun( String word ) {
        return nameToIdHash.containsKey( word );
    }

    // distance between nounA and nounB (defined below)
    public int distance( String nounA, String nounB ) throws IllegalArgumentException {
        if (!nameToIdHash.containsKey( nounA )) {
            throw new IllegalArgumentException( nounA + " is not a valid synset in the wordnet" );
        }
        if (!nameToIdHash.containsKey( nounB )) {
            throw new IllegalArgumentException( nounB + " is not a valid synset in the wordnet" );
        }

        SAP inst = new SAP( dg );
        if (!nameToIdHash.containsKey( nounA )) {
            throw new IllegalArgumentException( nounA + " is not a valid synset in the wordnet" );
        }
        if (!nameToIdHash.containsKey( nounB )) {
            throw new IllegalArgumentException( nounB + " is not a valid synset in the wordnet" );
        }

        //return inst.distance(nameToIdHash.get(nounA).intValue(), nameToIdHash.get(nounB).intValue());
        //System.out.println("Id for nouns " + nounA + " " + nounB);
        //System.out.println(nameToIdHash.get(nounA) + " " + " " + nameToIdHash.get(nounB));

        int length = inst.length( nameToIdHash.get( nounA ), nameToIdHash.get( nounB ) );
        //System.out.println("Length or distance for above = " + length);
        return length;


    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap( String nounA, String nounB ) {
        if (!nameToIdHash.containsKey( nounA )) {
            throw new IllegalArgumentException( nounA + " is not a valid synset in the wordnet" );
        }
        if (!nameToIdHash.containsKey( nounB )) {
            throw new IllegalArgumentException( nounB + " is not a valid synset in the wordnet" );
        }

        SAP inst = new SAP( dg );
        int parent = inst.ancestor( nameToIdHash.get( nounA ), nameToIdHash.get( nounB ) );
        return idToNameHash.get( parent );
    }

    // do unit testing of this class
    public static void main( String[] args ) {
        try {
            WordNet wdNet = new WordNet( args[ 0 ], args[ 1 ] );
        } catch (IOException ex) {
            System.out.println( ex );
        }

    }

}