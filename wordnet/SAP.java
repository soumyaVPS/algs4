import java.lang.Character;
import java.lang.Integer;
import java.util.HashMap;
import java.util.Iterator;

public class SAP {
	Digraph G;
   // constructor takes a digraph (not necessarily a DAG)
   public SAP(Digraph G) {
	   this.G = G;
       System.out.println(G.toString());
   }

   // length of shortest ancestral path between v and w; -1 if no such path
   public int length(int v, int w) {
       int pathToV[] = new int[G.V()];
       int pathToW[] = new int[G.V()];
       int ancestor = shortestAncestor(v, w, pathToV, pathToW);
       int index = ancestor;
       int distance = 0;
       while (pathToV[index] != -1) {
           index = pathToV[index];
           distance ++;
       }
       index = ancestor;
       while (pathToW[index] != -1) {
           index = pathToW[index];
           distance++;
       }
       return distance;
       //return shortestAncestor2(v,w, 2,-1);
   }

   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
   public int ancestor(int v, int w) {
       int pathToV[] = new int[G.V()];
       int pathToW[] = new int[G.V()];
       return shortestAncestor(v, w, pathToV, pathToW);
       //return shortestAncestor2(v,w, 1, -1);
   }


   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
   public int length(Iterable<Integer> v, Iterable<Integer> w) {
       int shortestPath = -1;
       for (Integer vI : v) {
           for (Integer wI: w) {
               int path = shortestAncestor2(vI,wI,2,shortestPath);
               if (path != -1 && shortestPath < path) {

                   shortestPath=path;
               }
           }
       }
       return shortestPath;
   }

   // a common ancestor that participates in shortest ancestral path; -1 if no such path
   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
       int shortestPath = -1;
       int vNearest = -1,wNearest = -1;
       for (Integer vI : v) {
           for (Integer wI: w) {
               //int path = shortestAncestor2(vI,wI,2,shortestPath);
               int pathToV[] = new int[G.V()];
               int pathToW[] = new int[G.V()];
               int path = return shortestAncestor(v, w, pathToV, pathToW);

               if (path != -1 && shortestPath < path) {
                   vNearest = vI; wNearest = wI;
                   shortestPath=path;
               }
           }
       }
       if (shortestPath != -1) {
           return shortestAncestor2(vNearest,wNearest,1,shortestPath);
       }
       return -1;
   }
    private int shortestAncestor2(int v, int w, int whatToReturn, int shortestPath) {
        Queue<Integer> queueV = new Queue<Integer>();
        Queue<Integer> queueW = new Queue<Integer>();
        HashMap<Integer,Character> visited = new HashMap<Integer,Character>();

        queueV.enqueue(v);
        queueW.enqueue(w);
        visited.put(v,'v');
        visited.put(w,'w');
        int depthV=-1, depthW =-1;
        int countBeforeDIncrV =queueV.size();
        int countBeforeDIncrW =queueW.size();
        int anc = -1;
        boolean found = false;
        while (true) {
            Iterator<Integer> itr = queueV.iterator();
            System.out.print("QueueV ");
            while (itr.hasNext()) {

                System.out.print(itr.next() + " ");
            }
            System.out.println("\n");
            if (!queueV.isEmpty()) {
                Integer vNode = queueV.dequeue();
                countBeforeDIncrV--;
                if (countBeforeDIncrV == 0) {
                    depthV++;
                }
                for (int vG : G.adj(vNode)) {

                    if (!visited.containsKey(vG)) {
                        visited.put(vNode, 'v');
                        queueV.enqueue(vG);
                    } else {
                        if (visited.get(vG) == 'w') {
                            anc = vG;
                            found = true;
                            break;
                        }
                    }
                }
                if (countBeforeDIncrV == 0) {
                    countBeforeDIncrV = queueV.size();
                }
            }
            Iterator<Integer> itr1 = queueW.iterator();
            System.out.print("QueueW ");
            while (itr1.hasNext()) {
                System.out.print(itr1.next() + " ");
            }
            System.out.println("\n");
            if (!queueW.isEmpty()) {
                Integer wNode = queueW.dequeue();
                countBeforeDIncrW--;
                if (countBeforeDIncrW == 0) {
                    depthW++;
                }
                for (int wG : G.adj(wNode)) {
                    System.out.print("wG = " + wG + "is adjacent to " + wNode + "\n");
                    if (!visited.containsKey(wG)) {
                        System.out.println(wG + "node not visited ");
                        visited.put(wG, 'w');
                        queueW.enqueue(wG);

                    } else {
                        if (visited.get(wG) == 'v') {
                            System.out.println("Found Ancestor at "+ wG);
                            anc = wG;
                            found = true;
                            break;
                        }
                    }
                }
                if (found) break;
                if (countBeforeDIncrW == 0) {
                    countBeforeDIncrW = queueW.size();
                }

            }
            if ((queueW.isEmpty() && queueV.isEmpty()) || found) {
                break;
            }
            if (shortestPath != -1) {
                //if we are finding shortest of many paths dont compute further if depth exceeds previous shortest path
                if ((depthV + depthW) > shortestPath) {
                    return -1;

                }


            }
            System.out.println("depthv and depthw " + depthV + " " + depthW);
        }
        if (! found) {
            return -1;
        }
        if (whatToReturn == 1 ) {
            return anc;
        }
        else
            return depthV + depthW;


    }
    private int shortestAncestor(int v, int w, int[] pathToV, int[] pathToW) {
        Queue<Integer> queueV = new Queue<Integer>();
        Queue<Integer> queueW = new Queue<Integer>();
        HashMap<Integer,Character> visited = new HashMap<Integer,Character>();

        queueV.enqueue(v);
        queueW.enqueue(w);
        visited.put(v,'v');
        visited.put(w,'w');
        pathToV[v] = -1;
        pathToW[w] = -1;

        while (true)  {
            if (!queueV.isEmpty()) {
                Integer vNode = queueV.dequeue();

                for (int vG : G.adj(vNode)) {
                    if (!visited.containsKey(vG)) {
                        visited.put(vNode, 'v');
                        queueV.enqueue(vG);
                        pathToV[vG] = vNode.intValue();
                    } else {
                        if (visited.get(vG) == 'w') {

                            pathToV[vG] = vNode.intValue();
                            return vG;
                        }
                    }
                }
            }

            if(!queueW.isEmpty()) {
                Integer wNode = queueW.dequeue();
                for (int wG : G.adj(wNode)) {
                    if (!visited.containsKey(wG)) {
                        visited.put(wG, 'w');
                        queueW.enqueue(wG);
                        pathToW[wG] = wNode.intValue();

                    } else {
                        if (visited.get(wG) == 'v') {

                            pathToW[wG] = wNode.intValue();
                            return wG;
                        }
                    }
                }

            }
            if (queueW.isEmpty() && queueV.isEmpty()) {
                break;
            }

        }
        return -1;

    }

   // do unit testing of this class
   public static void main(String[] args) {
       In in = new In(args[0]);
       Digraph G = new Digraph(in);
       SAP sap = new SAP(G);
       while (!StdIn.isEmpty()) {
           int v = StdIn.readInt();
           int w = StdIn.readInt();
           int length   = sap.length(v, w);
           int ancestor = sap.ancestor(v, w);
           StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
       }
   }
}