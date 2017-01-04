/*
 * Implementation of Monte Carlo Integration directions:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/homework/homework4.pdf
 */

/**
 * Hw4Prob2.java
 * Purpose: This Program computes a Monte Carlo Integration of ln(x) from 
 * x = 1 to 2.
 */
package hw5prob1a;
import java.util.Random;

public class Hw5Prob1a {
    static double function(double x) {
        return Math.log(x);
    }
    
    public static void main(String[] args) {
        double a = 1.0;
        double b = 2.0;
        double interv = b - a;
        int trials = 50000; //N
        Random rand = new Random();
        double x;
        double func;
        double funcSum = 0;
        double funcSquareSum = 0;
        double sumAve;
        double squareSumAve;
        double stdDev;
        
        for (int i = 0; i < trials; i++) {
            x = a + rand.nextDouble()*interv;
            func = function(x);
            funcSum += func;
            funcSquareSum += func*func;
        }
        sumAve = funcSum/trials;
        squareSumAve = funcSquareSum/(trials*trials);
        stdDev = Math.sqrt(Math.abs(squareSumAve - sumAve*sumAve)/(trials - 1));
        System.out.println("Monte Carlo Integration of ln(x) from x = 1 to 2:");
        System.out.format("Integral: %f ± %f%n", sumAve, stdDev);
    } 
}
