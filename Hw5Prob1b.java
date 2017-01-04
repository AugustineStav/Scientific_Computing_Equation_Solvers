/*
 * Implementation of Monte Carlo Integration directions:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/homework/homework5.pdf
 */

/**
 * Hw5Prob1b.java
 * Purpose: This Program computes a Monte Carlo Integration of 1/(1 + sin^2(x)) 
 * from x = 0 to infinity.
 */
package hw5prob1b;
import java.util.Random;

public class Hw5Prob1b {
    static double function(double x) {
        return 1/(1 + (Math.sin(x)*Math.sin(x)));
    }
    static double prob(double y) {
        return -Math.log(1-y); //p(x) = e^(-x), so x = -ln(1-y) with 0<y<1
    }
    
    public static void main(String[] args) {
        int trials = 50000; //N
        Random rand = new Random();
        double x;
        double y;
        double funcSum = 0;
        double sumAve;
        
        for (int i = 0; i < trials; i++) {
            y = rand.nextDouble();
            x = prob(y);
            funcSum += function(x);
        }
        sumAve = funcSum/trials;
        System.out.println("Monte Carlo Integration of 1/(1 + sin^2(x))) "
                + "from x = 0 to infinity:");
        System.out.format("Integral: %f%n", sumAve);
    } 
}
