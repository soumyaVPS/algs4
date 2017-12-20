import java.io.FileNotFoundException;
import java.lang.IllegalArgumentException;
import java.lang.Integer;
import java.lang.Iterable;
import java.lang.String;
import java.lang.System;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Comparator;

public class BaseballElimination {
    private final int numTeams;


    private class Team {
        int id, wins, losses, left;
        private ArrayList<Integer> gLWOthers=new ArrayList<Integer>( );


    }
    private HashMap<String,Team> teamsMap = new HashMap<String, Team>(  );
    private HashMap<Integer,String> idToNameMap = new HashMap<Integer, String>(  );

    public BaseballElimination( String filename )  throws FileNotFoundException, IllegalArgumentException                   // create a baseball division from given filename in format specified below
    {
        //Read The data
        In in = new In(filename);
        this.numTeams = in.readInt();
        if (numTeams < 0) throw new IllegalArgumentException("Number of teams cannot be 0");
        for (int i=0;i<numTeams; i++) {
            Team t = new Team();
            String name = in.readString();
            t.id = i;
            t.wins = in.readInt();
            t.losses = in.readInt();
            t.left = in.readInt();
            //System.out.println("\n" + name + " " + t.wins + " " + t.losses + " " + t.left);
            for(int j=0; j<numTeams;j++ ) {
                int l = in.readInt();
                //System.out.print( l + " " );
                t.gLWOthers.add( l );
            }
            teamsMap.put(name, t);
            idToNameMap.put(i,name);
        }

    }

    public int numberOfTeams()                        // number of teams
    {
        return numTeams;
    }

        public Iterable<String> teams()                                // all teams
    {
        return teamsMap.keySet();
    }

    public int wins( String team )                      // number of wins for given team
    {
        if (teamsMap.containsKey( team ))
            return teamsMap.get(team).wins;
        else
            throw new IllegalArgumentException("team "+ team + " not in the list. "  );

    }

    public int losses( String team )                    // number of losses for given team
    {
        if (teamsMap.containsKey( team )) {
           return teamsMap.get( team ).losses;
        }
        else {
            throw new IllegalArgumentException("team "+ team + " not in the list. "  );
        }
    }

    public int remaining( String team )                 // number of remaining games for given team
    {
        if (teamsMap.containsKey( team ))
            return teamsMap.get(team).left;
        else
            throw new IllegalArgumentException("team "+ team + " not in the list. "  );
    }

    public int against( String team1, String team2 )    // number of remaining games between team1 and team2
    {
        if (teamsMap.containsKey( team1 ) && teamsMap.containsKey(team2)){
            Team t1 = teamsMap.get(team1);
            Team t2 = teamsMap.get(team2);
            return t1.gLWOthers.get(t2.id).intValue();
        } else {
            throw new IllegalArgumentException("team not in the list. "  );
        }


    }
    private class CompTypeComparator implements Comparator<Team> {
        public int compare(Team o1, Team o2) {
            return (o1.id < o2.id ? -1 : (o1.id == o2.id ? 0 : 1));
        }

    }
    private FordFulkerson ff = null;
    public boolean isEliminated( String team )              // is given team eliminated?
    {   int n = numTeams; //total teams
        int numVertices = n + 2 + (n-1)*n/2;

        FlowNetwork fNetwork = new FlowNetwork( numVertices );
        //Fill in edges from teams to target
        // target:numVertices-1 source:numVertices-1
        int t = numVertices -1;
        int s = numVertices -2;
        Team teamX;
        if ( teamsMap.containsKey( team ) ) {
            teamX = teamsMap.get( team );
        }else {
            throw new IllegalArgumentException( "Team " + team + " not a valid team in the dataset." );
        }
        Iterator<Team> teamsIter = teamsMap.values().iterator();
        while (teamsIter.hasNext()) {

            Team teamI = teamsIter.next();
            if (teamI.id != teamX.id){
                //System.out.println("\n teamI =" + teamI.id + " teamX =" + teamX.id);
                int cap = teamX.wins + teamX.left - teamI.wins;
                cap = cap<0? 0: cap;
                FlowEdge fe = new FlowEdge( teamI.id, t, cap );
                fNetwork.addEdge(fe);

            }
        }
        //Fill in edges from s to team combinations
        Team[] allTeams = teamsMap.values().toArray( new Team[0] );
        Arrays.sort(allTeams,new CompTypeComparator());
        int max_cap_from_s = 0;
        int matchVert = 0;
        for (int i =0; i<n;i++) {
            for (int j=i+1; j<n;j++) {
                //System.out.println(i  + " " + allTeams[i].id + " " + j + " " +teamX.id );
                if (allTeams[i].id != teamX.id && j != teamX.id && i!=j) {
                    int cap = allTeams[ i ].gLWOthers.get( j );
                    FlowEdge fe = new FlowEdge( s, n + matchVert, cap );
                    //System.out.println(i +" Flow Edge  " + fe.toString());
                    fNetwork.addEdge( fe );
                    max_cap_from_s += cap;

                    FlowEdge feIn = new FlowEdge(n  + matchVert, allTeams[i].id, Integer.MAX_VALUE);
                    FlowEdge feOut = new FlowEdge(n  + matchVert, j, Integer.MAX_VALUE);
                    //System.out.println(i +" Flow Edge in " + feIn.toString());
                    //System.out.println(i + " Flow Edge out " + feOut.toString());
                    fNetwork.addEdge( feIn );
                    fNetwork.addEdge( feOut );

                }
                matchVert++;
            }
        }
        //System.out.println("Network " +fNetwork.toString() + "\n");

        ff = new FordFulkerson( fNetwork, s, t );
        double flowVal = ff.value();
        //System.out.println("max_cap_from_s " + max_cap_from_s );
        //System.out.println("flowVal " + flowVal);
        if (flowVal == max_cap_from_s)
            return false;
        else
            return true;


    }

    public Iterable<String> certificateOfElimination( String team )  // subset R of teams that eliminates given team; null if not eliminated
    {

        if (!teamsMap.containsKey(team)) {
            throw new IllegalArgumentException( "Team " + team + " not in the dataset." );
        }
        if (!isEliminated( team) ) {
            return null;
        }
        Bag<String> rSubset = new Bag<String>();
        for (int v=0; v < numTeams ; v++) {
            if (isEliminated( team )) {
                if (ff!=null)
                if (ff.inCut( v )) {
                    rSubset.add( idToNameMap.get( v ) );
                }
            }

        }
        return rSubset;
    }

    public static void main( String[] args ) {
        try {
            BaseballElimination division = new BaseballElimination( args[ 0 ] );
            /*String team = "Montreal";
            if (division.isEliminated( team )) {
                StdOut.print( team + " is eliminated by the subset R = { " );
                for (String t : division.certificateOfElimination( team )) {
                    StdOut.print( t + " " );
                }
                StdOut.println( "}" );
            }*/
            for (String team : division.teams()) {
                if (division.isEliminated( team )) {
                    StdOut.print( team + " is eliminated by the subset R = { " );
                    for (String t : division.certificateOfElimination( team )) {
                        StdOut.print( t + " " );
                    }
                    StdOut.println( "}" );
                } else {
                    StdOut.println( team + " is not eliminated" );
                }
            }
        }catch (FileNotFoundException ex){
        ex.printStackTrace();
    }

}

}