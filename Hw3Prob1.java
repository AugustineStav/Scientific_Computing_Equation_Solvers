/*
 * Bisection and Newton Raphson methods of root finding implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/root1.pdf
 * 
 */

/**
 * Hw3Prob1.java
 * Purpose: This Program numerically finds the roots of x - 4cos(x)
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob1;
import java.io.*;
import java.lang.Math;

public class Hw3Prob1 {
    public static double f(double x) {
        return x - 4*Math.cos(x);
    }
    
    public static double fPrime(double x) {
        return 1 + 4*Math.sin(x);
    } 

    public static void main(String[] args) {
        double x0 = -5.0;
        double x1 = -3.0;
        double x2 = 0.5*(x0 + x1);
        double xMin = 0.001;
        double fMin = 0.00001;
        double nrAcc = 0.00000001;
        boolean running = true;
        
        System.out.println("Bisection method: negative roots of x - 4cos(x)");
        System.out.format("%15s   %15s    %15s    %15s%n", 
                "x0", "x1", "x2", "f(x2)");

        while (running) {
            x2 = 0.5*(x0 + x1);
            System.out.format("%15.7f   %15.7f    %15.7f    %15.7f%n", 
                    x0, x1, x2, f(x2));
            if (  ((f(x0) < 0) && (f(x2) < 0)) || 
                    ((f(x0) > 0) && (f(x2) > 0)) ) {
                x0 = x2;
            } else {
                x1 = x2;
            }
            if ((x1 - x0) < xMin || Math.abs(f(x1)) < fMin) {
                running = false;
            }
        }
        
        x0 = (x1+x0)*0.5; //set x0 to the bisection root as input for Newton-
                          //Raphson
        System.out.format("bisection root: %10.5f%n", x0);
        System.out.println();
        System.out.println("Newton-Raphson:");
        System.out.format("%15s   %15s    %15s%n", 
                "x0", "x1", "f(x1)");
        
        while (Math.abs(x1-x0) > nrAcc) {
            x0 = x1;
            x1 = x1 - f(x1)/fPrime(x1);
            System.out.format("%15.7f   %15.7f    %15.7f%n", 
                    x0, x1, f(x1)); 
        }
        System.out.format("Newton-Raphson root: %11.8f%n", x1);
    }  
}
