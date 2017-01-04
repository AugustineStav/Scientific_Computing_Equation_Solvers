/*
 * Bisection method of root finding implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/root1.pdf
 */

/**
 * Hw2prob5.java
 * Purpose: This Program numerically finds the roots of x - 4cos(x)
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw2prob5;
import java.io.*;
import java.lang.Math;

public class Hw2prob5 {
    public static double f(double x) {
        return x - 4*Math.cos(x);
    }

    public static void main(String[] args) {
        double x0 = -5.0;
        double x1 = -3.0;
        double x2;
        double xMin = 0.001;
        double fMin = 0.00001;
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
        
        System.out.format("root: %10.5f%n", (x1+x0)*0.5);
    }  
}
