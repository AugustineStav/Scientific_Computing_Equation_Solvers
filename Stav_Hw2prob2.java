/*
 * Simpson's Rule integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/romberg.pdf
 */

/**
 * Hw2prob2.java
 * Purpose: This Program numerically finds the integral of f(double x) 
 *      at from x = 0.0 to 2.0  
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw2prob2;
import java.io.*;
import java.lang.Math;

public class Hw2prob2 {
    public static double f(double x) {
        return 1.0/(1.0 + x*x);
    }
    
    public static double simpsons(int n, double a, double b) {
        double h = (b-a)/n;
        double oddSum = 0;
        double evenSum = 0;
        double fA = f(a);
        double fB = f(b);
        double x = 0;

        for (int i = 1; i < n; i+=2) {
            x = a + i*h;
            oddSum += f(x);
        }

        for (int j = 2; j < n; j+=2) {
            x = a + j*h;
            evenSum += f(x);
        }
        
        return h*(fA + 4.0*oddSum + 2.0*evenSum + fB)/3.0;
    }

    public static void main(String[] args) {
        double a = 0.0;
        double b = 2.0;
        int n = 2;
        double h = (b-a)/n;
        double integral;
        double integPrev = simpsons(n, a, b);
        double diff;
        boolean running = true;
        int i = 1;
        
        System.out.println("Simpsons method: integral of 1/(1+x^2) "
                + "from 0 to 2");
        System.out.format("%10s    %15s    %15s    %15s    %15s%n", "trial", 
                "h", "integral", "|diff|", "|diff|/(h^4)");
        System.out.format("%10d    %15.13f    %15.13f    %15s    %15s%n", 
            i, h, integPrev, "no previous", "no previous");

        while (running) {
            n = 2*n;
            i++;
            h = (b-a)/n;
            integral = simpsons(n, a, b);
            diff = Math.abs(integral - integPrev);
            System.out.format("%10d    %15.13f    %15.13f    %15.13f    "
                    + "%15.9e%n", i, h, integral, diff, diff/(h*h*h*h));
            
            if (diff < 0.00000001 || i > 25) {
                running = false;
            }
            integPrev = integral;
        }
    }  
}
