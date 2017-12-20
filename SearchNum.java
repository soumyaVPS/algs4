import java.util.Arrays;
public class SearchNum {
    public static int search (int a[], int l, int h, int num) {
        int m =  l + (h - l)/2;

        if (l>h) {
            return -1;
        }
        System.out.println(m + ""+ a[m]);
        if (a[m] == num) {
            return m;
        }

        else if (a[l] < a[m]) {
            if (num >= a[m] ) {
                return search(a,m+1, h, num);
            }
            else if(num>=a[l])
                return search(a,l,m-1,num);
            else //if (num<a[h])
                return search(a,m+1,h,num);
        }

        if ( num <= a[h]   ) {
            return search(a, m+1, h,num);
        }
        else {
            return search(a, l, m - 1, num);
        }


    }
    public static int search1 (int a[], int l, int h, int num) {
        int m = (l+h)/2;

         if (l>h) {
            return -1;
        }
        if (num == a[m]) return m;

        if (num < a[m]) {
            return search1(a, l, m - 1, num);
        }

        return search1 (a,m+1,h,num);
    }
    public static int search_nonrec (int a[], int num) {
        int h = a.length -1;
        int l = 0;
        while (h>=l) {
            int m = (l+h)/2;
            if (num == a[m]) return m;
            if (num < a[m]) {
                h = m-1;
            } else
                l = m+1;
        }
        return -1;
    }
    public static int search_nonrec_str (String a[], String str) {
        int h = a.length -1;
        int l = 0;

        while (h>=l) {
            int m = (l+h)/2;
            while (a[m].equals("") && m > l) {
                m--;
            }
            if (str.equals(a[m])) return m;

            if (a[m].compareTo(str)>0) {
                h = m-1;
                continue;
            }
            m =(l+h)/2;
            while (a[m].equals("") && m <h) {
                m++;
            }
            System.out.println("m "+m + "a of m" + a[m]);
            if (str.equals(a[m])) return m;
            if (a[m].compareTo(str)<0) {
                l = m+1;
                continue;
            }
            else
                return -1;

        }
        return -1;
    }

    public static void main(String[] str) {
        int a[] =  {9,10,11,12,13,14,15,16,1,2,3,4,5,6,7,8};
        int b[] = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        //System.out.println(search1(b,0,b.length-1,1));
        String c[] = {"at", "", "", "", "ball", "", "", "car", "", "", "dad", "", ""};
        System.out.println(search_nonrec_str(c, "car"));


    }
}