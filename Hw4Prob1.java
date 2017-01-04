/*
 * X = sqrt(12/N) sum(i=1 to N) of (x_i -1/2) where x_i is a random number with
 * a uniform distribution between 0 and 1.
 */

/**
 * Hw4Prob1.java
 * Purpose: This Program verifies the first 6 moments of the variable X.
 */

package hw4prob1;
import java.util.Random;

public class Hw4Prob1 {    
    public static void calcMoments(double[] moments, Random rand) {
        double sum = -6.0; //-1/2 times 12
        for (int i = 0; i < 12; i++) {
            sum += rand.nextDouble();
        }
        for (int i = 0; i < moments.length; i++) {
            moments[i] += Math.pow(sum, i+1);
        }
    }
    
    public static void main(String[] args) {
        double[] moments = new double[6];
        double x;
        int trials = 50000;
        Random rand = new Random();
        for (int i = 0; i < trials; i++) {
            calcMoments(moments, rand);
        }
        System.out.println("The first 6 moments of X:");
        for (int i = 0; i < moments.length; i++) {
            System.out.format("<X^%d> = %f%n", i+1, moments[i]/trials);
        }
    }  
}