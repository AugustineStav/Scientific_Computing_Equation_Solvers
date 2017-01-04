/*
 * Implementation of Heap Sort Method:
 *      http://apps.nrbook.com/empanel/index.html# page 428
 * Time elapsed calculation:
 *      http://stackoverflow.com/questions/5204051/
 *           how-to-calculate-the-running-time-of-my-program
 */

/**
 * Hw5Prob2.java
 * Purpose: This Program generates 10^6 random numbers in the range 0 to 1 and 
 * uses the heapsort routine to sort the numbers in ascending order.
 */
package hw5prob2;
import java.util.Random;

public class Hw5Prob2 {
    static void siftDown (double[] ra, int l, int r) { //ra is the heap
        int i, iOld;
        iOld = l;
        double a = ra[l];
        i = 2*l+1;
        while (i <= r) {
            if (i < r && ra[i] < ra[i+1]){
                i++;
            }
            if (a >= ra[i]){
                break;
            }
            ra[iOld] = ra[i];
            iOld = i;
            i = 2*i+1;
        }
        ra[iOld] = a;
    }
    
    static void heapSort(double[] ra){
        int i, n=ra.length;
        for (i = n/2-1; i >= 0; i--){
            siftDown(ra, i, n-1);
        }
        for (i = n-1; i > 0; i--) {
            double raI = ra[i];
            ra[i] = ra[0];
            ra[0] = raI;
            siftDown(ra, 0, i-1);
        }
    }
    
    //return the index of the value immediately before searchVal
    static int binarySearch(double[] heap, double searchVal) { 
        int i0 = 0;
        int i1 = heap.length;
        int i2;
        
        while (i1 - i0 > 1){
            i2 = (i0 + i1)/2;
            if (heap[i2] > searchVal){
                i1 = i2;
            } else {
                i0 = i2;
            }
        }
        return i0;
    }
    
    public static void main(String[] args) {
        int indexPrev, N = 1000000;
        double searchVal = 0.7;
        double[] heap = new double[N];
        Random rand = new Random();
        long startTime, endTime, totalTime1, totalTime2;
        
        for (int i = 0; i < N; i++){
            heap[i] = rand.nextDouble();
        }
        
        startTime = System.currentTimeMillis();
        heapSort(heap);
        endTime   = System.currentTimeMillis();
        totalTime1 = endTime - startTime; //calculate time elapsed to sort
                                          //N-sized list
        
        System.out.println("i.");
        System.out.println("Heap sort: first 5 sorted:");
        for (int i = 0; i < 5; i++){
            System.out.format("%10.8f%n", heap[i]);
        }
        System.out.println("\nHeap sort: last 5 sorted:");
        for (int i = N-5; i < N; i++){
            System.out.format("%10.8f%n", heap[i]);
        }
        System.out.println("\nii.");
        indexPrev = binarySearch(heap, searchVal);
        System.out.format("%.1f lies between elements %d and %d%n", 
                searchVal, indexPrev, indexPrev+1);
        
        //calculate the time elapsed for sorting a list 10 times as large
        int N2 = N*10;
        double[] heap2 = new double[N2];
        
        for (int i = 0; i < N2; i++){
            heap2[i] = rand.nextDouble();
        }
        
        startTime = System.currentTimeMillis();
        heapSort(heap2);
        endTime   = System.currentTimeMillis();
        totalTime2 = endTime - startTime;
        System.out.println("\niii.");
        System.out.format("%10s %20s%n", "N", "sorting time (ms)");
        System.out.format("%10d %20d%n", N, totalTime1);
        System.out.format("%10d %20d%n", N2, totalTime2);
    } 
}
