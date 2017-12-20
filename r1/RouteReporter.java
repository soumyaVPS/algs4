import java.io.BufferedReader;
import java.io.FileNotFoundException;

import java.io.IOException;
import java.lang.IllegalArgumentException;
import java.lang.System;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;

public class RouteReporter {
    public static String[] splitter(String csv) {
        return csv.split(",",-1);
    }
   private static String[] routeTypeMap = {
     "Tram-Streetcar-Lightrail",
     "Subway-Metro",
    "Rail",
    "Bus",
    "Ferry",
    "Cable car",
    "Gondola-Suspended cable car",
    "Funicular" };

    private TreeMap<String, Route> routeMap;
    private HashMap<String, FareAttributes> fattrMap;

    public RouteReporter(String rtFile, String faFile, String frFile)  throws   IOException, FileNotFoundException, IllegalArgumentException {

        routeMap = new TreeMap<String,Route>();
        fattrMap = new HashMap<String,FareAttributes>();
        BufferedReader brRoutes = new BufferedReader( new FileReader( rtFile ) );
        //Read routes.txt
        //Read the header and ignore it
        if (brRoutes.readLine() ==null) {
            throw new IllegalArgumentException("Missing header line in " + rtFile);
        }
        String ln;
        while ((ln= brRoutes.readLine())!=null) {
            Route rt = new Route( ln );
            routeMap.put( rt.id, rt );
        }

        readFareAttributes(faFile);
        updateRoutesWithFare(frFile);
    }
   private void readFareAttributes(String fname)  throws IOException, FileNotFoundException, IllegalArgumentException{
        BufferedReader brFAttr = new BufferedReader( new FileReader( fname ) );
        if (brFAttr.readLine() == null) {
            throw new IllegalArgumentException( "Missing header line in " + fname );
        }
        String ln;
        while ((ln= brFAttr.readLine())!=null) {
            FareAttributes fattr = new FareAttributes(ln);
            fattrMap.put(fattr.fare_id,fattr);
        }
       brFAttr.close();
    }


   private void updateRoutesWithFare(String fname)  throws IOException, FileNotFoundException, IllegalArgumentException{

        BufferedReader brFRules = new BufferedReader( new FileReader( fname ) );

        if (brFRules.readLine() == null) {
            throw new IllegalArgumentException( "Missing header line in " + fname );
        }
        //Read fare_rules and update routes
        //A route appears to have only one Fare_id associated with it. Other Fields  do not matter,
        // hence use of Routes to hold the fare_id will suffice.
        String ln;
        while ((ln = brFRules.readLine()) != null) {
            String[] vals = splitter( ln );
            //check if route_id is valid
            Route rt = routeMap.get( vals[ 1 ] );
            if (rt == null) {
                throw new IllegalArgumentException( "Route specified here does not correspond to entry in routes file." + ln );
            }
            FareAttributes fattr = fattrMap.get( vals[ 0 ] );
            //check if fare_id is valid
            if (fattr == null) {
                throw new IllegalArgumentException( "Fare id specified here does not correspond to entry in fare attributes file." + ln );
            }

            rt.fare_id = vals[ 0 ];
        }
       brFRules.close();
    }


   private void printTypes(String typesFile) throws IOException{
        int route_types_arr[] = new int[ routeTypeMap.length ];
        for (int i = 0; i < route_types_arr.length; i++) {
            route_types_arr[ i ] = 0;
        }
        for (Route rt : routeMap.values()) {
            route_types_arr[ rt.type ] = route_types_arr[ rt.type ] + 1;
        }
        FileWriter fw = new FileWriter(typesFile);
        fw.write("route_type, num_routes\n");
        for (int i =0;i<route_types_arr.length;i++) {
            fw.write(routeTypeMap[i] +","+ route_types_arr[i] +"\n");
        }
       fw.close();
   }

   private void printRouteCosts(String costFile)  throws IOException{
       FileWriter fw = new FileWriter(costFile);
       fw.write("route_short_name, route_long_name, price\n");
       for (Route rt: routeMap.values()) {
           String line = rt.short_name +"," + rt.long_name +"," + fattrMap.get(rt.fare_id).price +"\n";
            fw.write(line);
       }
       fw.close();

   }

   public void printReports(String typesFile, String costFile)  throws IOException{
        printTypes(typesFile);
        printRouteCosts(costFile);
   }

   public static void main(String[] args) {
       try {
           String outFile1 = "route_types.txt";
           String outFile2 = "route_costs.txt";
           if (args.length < 3) {
               System.out.println( "Usage: java routes.txt fare_attributes.txt fare_rules.txt" );
               System.out.println( "Usage: java routes.txt fare_attributes.txt fare_rules.txt " + outFile1 + " " + outFile2 );
               System.exit(0);
           }
           RouteReporter report = new RouteReporter( args[ 0 ], args[ 1 ], args[ 2 ] );

           if (args.length == 5) {
               outFile1 = args[ 3 ];
               outFile2 = args[ 4 ];
           }

           report.printReports( outFile1, outFile2 );
       } catch (Exception e) {
           e.printStackTrace();
       }


    }
}