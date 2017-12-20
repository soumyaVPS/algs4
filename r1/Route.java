import java.lang.String;
import java.lang.IllegalArgumentException;
import java.util.StringTokenizer;
public class Route {
    //just make them public to avoid getters.
    public String id;
    public int type;
    public String short_name, long_name;
    public String fare_id;

    public Route (String csv) throws IllegalArgumentException {
        String[] attrs = RouteReporter.splitter(csv);
        if (attrs.length != 9) {
            System.out.println("Attrs length" + attrs.length);
            throw new IllegalArgumentException("MalFormed CSV string: " +csv);
        }
        id  = attrs[0];
        short_name= attrs[2];
        long_name = attrs[3];
        type = Integer.parseInt( attrs[5]);
    }






}