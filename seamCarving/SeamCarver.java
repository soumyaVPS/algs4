import java.lang.IndexOutOfBoundsException;
import java.lang.NullPointerException;
import java.lang.IllegalArgumentException;
import java.lang.System;
import java.util.Arrays;

public class SeamCarver {
    private Picture pic;
    private final double bEnergy = 195075.0;

    public SeamCarver( Picture picture )                // create a seam carver object based on the given picture
    {
        if (picture == null) {
            throw new NullPointerException( "No picture passed to constructor." );
        }
        pic = picture;

    }

    public Picture picture()                          // current picture
    {

        return pic;
    }

    public int width()                            // width of current picture
    {
        return pic.width();
    }

    public int height()                           // height of current picture
    {
        return pic.height();
    }

    public double energy( int x, int y )               // energy of pixel at column x and row y
    {
        if (y > pic.height() || y < 0) {
            throw new IndexOutOfBoundsException( "y value invalid" );
        }
        if (x > pic.width() || x < 0) {
            throw new IndexOutOfBoundsException( "x value invalid" );
        }
        if (x == 0 || x == pic.width()-1 || y == 0 || y == pic.height()-1) {
            return bEnergy;
        }
        double rX = pic.get( x + 1, y ).getRed() - pic.get( x - 1, y ).getRed();
        double bX = pic.get( x + 1, y ).getBlue() - pic.get( x - 1, y ).getBlue();
        double gX = pic.get( x + 1, y ).getGreen() - pic.get( x - 1, y ).getGreen();

        double rY = pic.get( x, y + 1 ).getRed() - pic.get( x, y - 1 ).getRed();
        double bY = pic.get( x, y + 1 ).getBlue() - pic.get( x, y - 1 ).getBlue();
        double gY = pic.get( x, y + 1 ).getGreen() - pic.get( x, y - 1 ).getGreen();

        double deltaSquareX = rX * rX + bX * bX + gX * gX;
        double deltaSquareY = rY * rY + bY * bY + gY * gY;

        double energy = deltaSquareX + deltaSquareY;
        return energy;

    }

    public int[] findHorizontalSeam()               // sequence of indices for horizontal seam
    {
        if (pic.width() < 2) {
            throw new IllegalArgumentException( "Cannot remove any more seams. Picture width is  1" );
        }
        if (pic.height() < 2) {
            throw new IllegalArgumentException( "Cannot remove any more seams. Picture height is  1" );
        }
        if (pic.height() < 3) {
            int[] seam = new int[ pic.width() ];
            Arrays.fill( seam, 0 );
            return seam;
        }

        final int matNumColumns = pic.width() - 2;
        final int matNumRows = pic.height() - 2;
        double[] prevColAggrEnergy = new double[ matNumRows ];
        int[] pathTo = new int[ matNumColumns * matNumRows + 1 ];
        //ignore the edges when finding pathTo or distTo.
        //initialize energy for the first column in matrix(i.e the second column in pic)
        for (int row = 0; row < matNumRows; row++) {
            pathTo[ row ] = -1;
            prevColAggrEnergy[ row ] = energy( 1, row + 1 );
        }

        //done initializing
        //find shortest path from vertex -1 (not in array) width*height+1 st vertex.
        //
        for (int col = 1; col < matNumColumns; col++) {
            double[] shortestAggrDistTo = new double[ matNumRows ];
            //compute lowest engergy for 0th column
            int  row = 0;
            int pathToMe = (col -1)*matNumRows + row;
            double lowestEBeforeMe = prevColAggrEnergy[row];
            if  (lowestEBeforeMe > prevColAggrEnergy[row+1]) {
                pathToMe = (col -1)*matNumRows + row+1;
                lowestEBeforeMe = prevColAggrEnergy[row+1];
            }
            pathTo[col *matNumRows + row] = pathToMe;
            shortestAggrDistTo[row] = lowestEBeforeMe + energy(col+1,row+1);
            //compute lowest energy for the rest of the columns but the last
            for ( row = 1; row < matNumRows -1; row++) {
                pathToMe = (col -1)*matNumRows + row -1;
                lowestEBeforeMe = prevColAggrEnergy[row -1];
                if (lowestEBeforeMe > prevColAggrEnergy[ row]) {
                    pathToMe = (col -1)*matNumRows + row;
                    lowestEBeforeMe = prevColAggrEnergy[row];
                }
                if  (lowestEBeforeMe > prevColAggrEnergy[row+1]) {
                    pathToMe = (col -1)*matNumRows + row+1;
                    lowestEBeforeMe = prevColAggrEnergy[row+1];
                }
                pathTo[col *matNumRows + row] = pathToMe;
                shortestAggrDistTo[row] = lowestEBeforeMe + energy(col+1, row+1);
            }
            //compute shortest energy for the last column
            pathToMe = (col -1)*matNumRows + row -1;
            lowestEBeforeMe = prevColAggrEnergy[row -1];
//            System.out.println("1Lowest Energy = " + lowestEBeforeMe);
//            System.out.println("1pathToMe = " +pathToMe);

            if (lowestEBeforeMe > prevColAggrEnergy[row]) {
                pathToMe = (col -1)*matNumRows + row;
                lowestEBeforeMe = prevColAggrEnergy[row];
                // System.out.println("2Lowest Energy = " + lowestEBeforeMe);
                //System.out.println("2pathToMe = " +pathToMe);
            }
            pathTo[col *matNumRows + row] = pathToMe;
            shortestAggrDistTo[row] = lowestEBeforeMe + energy(col+1, row+1);

            prevColAggrEnergy = shortestAggrDistTo;
            //System.out.println("Distances for row " +row+" "+ Arrays.toString(prevRowDists));
        }


        int[] horSeam = new int[pic.width()];
        int col = pic.width()-1;
        int row = findLowestELoc( prevColAggrEnergy );
        horSeam[col] = row+1; //last col in pic - static energy
        horSeam[--col] = row+1; // last non trivial col.
        col --;
        int myPrecursor = pathTo[ matNumRows * (matNumColumns-1)  + row ];

        do {

            horSeam[col] = myPrecursor%matNumRows +1;
            myPrecursor = pathTo[myPrecursor];
            col--;
        } while (myPrecursor != -1);


        horSeam[0]= horSeam[1];
        horSeam[pic.width() -1] = horSeam[pic.width()-2];


        //debugging
        //System.out.println("Vertical Seam" + Arrays.toString(vertSeam));
/*
        int m=0;
        for (int j = 0; j<matrixRows; j++) {
            for (int i = 0; i < matrixColumns; i++) {
                System.out.print( pathTo[ m++ ] + "[" + i + "]" );
            }
            System.out.println( "\n" );
        }
*/
        return horSeam;
    }

    public int[] findVerticalSeam()                 // sequence of indices for vertical seam
    {
        if (pic.width()<2) {
            throw new IllegalArgumentException("Cannot remove any more seams. Picture width is  1");
        }
        if (pic.height()<2) {
            throw new IllegalArgumentException("Cannot remove any more seams. Picture height is  1");
        }
        if (pic.width() < 3 ) {
            int[] seam = new int[pic.height()];
            Arrays.fill(seam,0);
            return seam;
        }
//ignore the edges when finding pathTo or distTo.

        final int matNumColums = pic.width() - 2;
        final int matNumRows = pic.height() - 2;
        double[] prevRowDists = new double[ matNumColums ];
        int[] pathTo = new int[ matNumColums * matNumRows + 1 ];

        //initialize

        for (int col = 0; col < matNumColums; col++) {
            pathTo[ col ] = -1;
            prevRowDists[ col ] = energy( col + 1, 1 );
        }

        //done initializing


        //find shortest path from vertex -1 (not in array) width*height+1 st vertex.
        for (int row = 1; row < matNumRows; row++) {
            double[] shortestDistTo = new double[ matNumColums ];
            //compute lowest engergy for 0th column
            int  col = 0;
            int pathToMe = (row -1)*matNumColums + col;
            double lowestEBeforeMe = prevRowDists[col];
            if  (lowestEBeforeMe > prevRowDists[col+1]) {
                pathToMe = (row -1)*matNumColums + col+1;
                lowestEBeforeMe = prevRowDists[col+1];
            }
            pathTo[row *matNumColums + col] = pathToMe;
            shortestDistTo[col] = lowestEBeforeMe + energy(col+1, row+1);
            //compute lowest energy for the rest of the columns but the last
            for ( col = 1; col < matNumColums -1; col++) {
                 pathToMe = (row -1)*matNumColums + col -1;
                lowestEBeforeMe = prevRowDists[col -1];
                if (lowestEBeforeMe > prevRowDists[ col]) {
                    pathToMe = (row -1)*matNumColums + col;
                    lowestEBeforeMe = prevRowDists[col];
                }
                if  (lowestEBeforeMe > prevRowDists[col+1]) {
                    pathToMe = (row -1)*matNumColums + col+1;
                    lowestEBeforeMe = prevRowDists[col+1];
                }
                pathTo[row *matNumColums + col] = pathToMe;
                shortestDistTo[col] = lowestEBeforeMe + energy(col+1, row+1);
            }
            //compute shortest energy for the last column
            pathToMe = (row -1)*matNumColums + col -1;
            lowestEBeforeMe = prevRowDists[col -1];
//            System.out.println("1Lowest Energy = " + lowestEBeforeMe);
//            System.out.println("1pathToMe = " +pathToMe);

            if (lowestEBeforeMe > prevRowDists[col]) {
                pathToMe = (row -1)*matNumColums + col;
                lowestEBeforeMe = prevRowDists[col];
               // System.out.println("2Lowest Energy = " + lowestEBeforeMe);
               //System.out.println("2pathToMe = " +pathToMe);
            }
            pathTo[row *matNumColums + col] = pathToMe;
            shortestDistTo[col] = lowestEBeforeMe + energy(col+1, row+1);

            prevRowDists = shortestDistTo;
            //System.out.println("Distances for row " +row+" "+ Arrays.toString(prevRowDists));
        }


        int[] vertSeam = new int[pic.height()];
        int row = pic.height()-1;
        int col = findLowestELoc( prevRowDists );
        vertSeam[row] = col+1; //last row in pic - static energy
        vertSeam[--row] = col+1; // last non trivial row.
        row --;
        int myPrecursor = pathTo[ matNumColums * (matNumRows-1)   + col ];

        do {

            vertSeam[row] = myPrecursor%matNumColums +1;
            myPrecursor = pathTo[myPrecursor];
            row--;
        } while (myPrecursor != -1);


        vertSeam[0]= vertSeam[1];
        vertSeam[pic.height() -1] = vertSeam[pic.height()-2];


        //debugging
        //System.out.println("Vertical Seam" + Arrays.toString(vertSeam));
/*
        int m=0;
        for (int j = 0; j<matrixRows; j++) {
            for (int i = 0; i < matrixColumns; i++) {
                System.out.print( pathTo[ m++ ] + "[" + i + "]" );
            }
            System.out.println( "\n" );
        }
*/
        return vertSeam;
    }
    private int findLowestELoc( double[] shortestDistTo ) {
        int len = shortestDistTo.length;
        int low_index = 0;
        double smallest = shortestDistTo[0];
        for (int i = 0;i < len; i++) {
            if (shortestDistTo[i] < smallest) {
                low_index = i;
                smallest= shortestDistTo[i];
            }

        }
        //System.out.println("Lowest weight and col in last row "+ smallest + " " + low_index);
        return low_index;

    }

    public void removeHorizontalSeam( int[] seam )   // remove horizontal seam from current picture
    {
        if (pic.width()<2) {
            throw new IllegalArgumentException("Cannot remove any more seams. Picture width is  1");
        }
        if (pic.height()<2) {
            throw new IllegalArgumentException("Cannot remove any more seams. Picture height is  1");
        }
        Picture updatedPic = new Picture (pic.width(), pic.height()-1);
        for (int i = 0; i < pic.width(); i++) {
            for (int j=0; j<seam[i]; j++) {
                updatedPic.set(i,j,pic.get(i,j));
            }
        }
        for(int i=0; i<pic.width();i++) {
            for (int j=seam[i]; j<updatedPic.height(); j++) {
                updatedPic.set(i,j,pic.get(i,j+1));
            }
        }
        pic = updatedPic;
    }

    public void removeVerticalSeam( int[] seam )   throws IllegalArgumentException   // remove vertical seam from current picture
    {
        if (pic.width()<2) {
            throw new IllegalArgumentException("Cannot remove any more seams. Picture width is  1");
        }
        if (pic.height()<2) {
            throw new IllegalArgumentException("Cannot remove any more seams. Picture height is  1");
        }

        Picture updatedPic = new Picture (pic.width()-1, pic.height());
        for (int i = 0; i < pic.height(); i++) {
            for (int j=0; j<seam[i]; j++) {
                updatedPic.set(j,i,pic.get(j,i));
            }
        }
        for(int i=0; i<pic.height();i++) {
            for (int j=seam[i]; j<updatedPic.width(); j++) {
                updatedPic.set(j,i,pic.get(j+1,i));
            }
        }
        pic = updatedPic;
    }
}