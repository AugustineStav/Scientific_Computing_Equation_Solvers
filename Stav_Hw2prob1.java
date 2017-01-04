/*
 * Midpoint derivative implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/diff.pdf
 */

/**
 * Hw2prob1.java
 * Purpose: This Program numerically finds the derivative of f(double x) 
 *      at x = 0.0    
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw2prob1;
import java.io.*;
import java.lang.Math;

public class Hw2prob1 {
    public static double f(double x) {
        return Math.exp(x) + Math.sin(x);
    }
    
    public static double midpoint(double x, double h) {
        return (f(x + h/2.0) - f(x - h/2))/h;
    }

    public static void main(String[] args) {
        double exact = 2.0;
        double h = 2.0;
        double deltaPrev = 0.0;
        
        System.out.println("Midpoint method: derivative of e^x + sin(x)");
        System.out.format("%16s    %16s    %16s    %16s%n", "h", 
                "derivative", "delta", "deltaPrev/delta");
        for (int i = 0; i < 25; i++) {
            double deriv = midpoint(0.0, h);
            double delta = Math.abs(deriv - exact);
            double ratio = deltaPrev/delta;
            System.out.format("%15.14f    %15.14f    %15.10e    %15.14f%n", 
                    h, deriv, delta, ratio);
            h /= 2.0;
            deltaPrev = delta;
        }
    }  
}
