import java.util.*;

public class allSubsets {

    public static void main(String[] args) {
        int[] arr = {1,2,3};

        
        System.out.println(Arrays.deepToString(getS(arr))+"\n");
        printS(arr);
        printSIter(arr);
    }

    public static int[][] getS(int[] arr) {
        return getS(arr, new int[0]);
    }
    public static int[][] getS(int[] arr, int[] fromLeft) {

        int[][] t = new int[1][];
        t[0] = fromLeft;
        if (arr.length==0) return t;
        
        int[] rest;
        if (arr.length==1) rest = new int[0];
        else rest = Arrays.copyOfRange(arr, 1, arr.length);        // "to" index is exclusive

        int[] left1 = Arrays.copyOf(fromLeft, fromLeft.length);
        int[][] withFirst = getS(rest, left1);

        int[] left2 = Arrays.copyOf(fromLeft, fromLeft.length+1);
        left2[fromLeft.length] = arr[0];
        int[][] withoutFirst = getS(rest, left2);

        int[][] solution = new int[withFirst.length+withoutFirst.length][];
        System.arraycopy(withFirst, 0, solution, 0, withFirst.length);
        System.arraycopy(withoutFirst, 0, solution, withFirst.length, withoutFirst.length);

        return solution;
    }


// -------------------------------------------

    public static void printS(int[] arr) {
        printS(arr, new int[0]);
    }
    public static void printS(int[] arr, int[] fromLeft) {
        if (arr.length==0) {
            System.out.println(Arrays.toString(fromLeft));
            return;
        }

        int[] rest = Arrays.copyOfRange(arr, 1, arr.length);
        int[] newFromLeft = Arrays.copyOf(fromLeft, fromLeft.length+1);
        newFromLeft[fromLeft.length] = arr[0];

        printS(rest, fromLeft);
        printS(rest, newFromLeft);
    }


// -------------------------------------------


    public static void printSIter(int[] arr) {
        int n = arr.length;
        for (int i=0; i<Math.pow(2, n); i++) {

            for (int j=0; j<n; j++) {
                if ((i>>j) % 2 == 1) {
                    System.out.print(arr[j]+" ");
                }
            }
            
            System.out.println();
        }
    }
}