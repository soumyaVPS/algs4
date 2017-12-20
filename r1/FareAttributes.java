import java.lang.IllegalArgumentException;
import java.lang.NumberFormatException;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
//fare_id,price,currency_type,payment_method,transfers,transfer_duration

public class FareAttributes{
    public String fare_id,currency_type,payment_method,transfers;
    public double price;

    public FareAttributes (String csv) throws IllegalArgumentException, NumberFormatException {
        String[] attrs = RouteReporter.splitter(csv);
        if (attrs.length != 6) {
              throw new IllegalArgumentException("MalFormed CSV string:" +csv);
        }
        fare_id  = attrs[0];
        price = Double.parseDouble(attrs[1]);
        currency_type = attrs[2];
        payment_method = attrs[3];


    }
}