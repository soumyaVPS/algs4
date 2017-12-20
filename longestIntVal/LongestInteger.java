import java.lang.Integer;
import java.lang.StringBuffer;
import java.util.Arrays;

public class LongestInteger {

    private static int max_val_len = Integer.toString(Integer.MAX_VALUE).length();
    public static String padWithSS(String s) {

        if (max_val_len > s.length()) {
            char[]  filler = new char[max_val_len - s.length()];
            Arrays.fill(filler, 'S');
            s = s+ new String(filler);
        }
        return s;
    }
    public static String removePad(String s) {
        int idxS = s.indexOf("S");
        if (idxS >0) {
            s = s.substring(0,idxS);
        }
        return s;
    }
    public static String getLongestInt(Integer[] intArray) {

         String[] strArray = new String[intArray.length];
         for (int i = 0; i<strArray.length; i++) {
             strArray[i] = padWithSS(intArray[i].toString());
         }
        Arrays.sort(strArray);
        StringBuffer retBuf = new StringBuffer();
        for (int i = strArray.length -1; i>=0; i--) {
            retBuf.append(removePad(strArray[i]));
        }
        return retBuf.toString();
     }

    public static void main(String[]  str) {
        if (str.length < 2 )
            System.out.println("java LongestInteger <num of ints> int1 int2 int3");
        int numInts = new Integer(str[0]);
        Integer[] intArray  = new Integer[numInts];
        for (int i=0; i<numInts; i++) {
            intArray[i] = new Integer(str[1+i]);

        }
        //getLongestInt(intArray)
       System.out.println(getLongestInt(intArray));

    }
}