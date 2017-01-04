/*
 * Midpoint integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/integral_error.pdf
 */

/**
 * Hw2prob4.java
 * Purpose: This Program finds the integral of -exp[-(1/y - 1)^2]/(y^2) 
 *          dy from y = 1 to 0") or integral of exp[-x^2] dx from x = 0 
 *          to infinity with a change of variables
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw2prob4;
import java.io.*;
import java.lang.Math;

public class Hw2prob4 {
    public static double f(double y) {
        return -(Math.exp(-(1/y - 1)*(1/y - 1)))/(y*y);
    }
    
    public static double midpoinInteg(int n, double a, double b) {
        double h = (b-a)/n;
        double integral = 0;
        double x = 0;
        
        for (int i = 0; i < n; i++) {
            x = a + (i + 0.5)*h;
            integral += f(x);
        }
        
        return h*integral;
    }

    public static void main(String[] args) {
        double a = 1.0;
        double b = 0.0;
        int n = 2;
        double h = (b-a)/n;
        double integral;
        double integPrev = midpoinInteg(n, a, b);
        double diff;
        boolean running = true;
        int i = 1;
        
        System.out.println("Midpoint method: integral of -exp[-(1/y - 1)^2]/"
                + "(y^2) dy from y = 1 to 0");
        System.out.println("or integral of exp[-x^2] dx from x = 0 "
                + "to infinity with a change of variables");
        System.out.format("%10s    %15s    %15s    %15s%n", "trial", 
                "h", "integral", "|diff|");
        System.out.format("%10d    %15.13f    %15.13f    %15s%n", 
            i, h, integPrev, "no previous");

        while (running) {
            n = 2*n;
            i++;
            h = (b-a)/n;
            integral = midpoinInteg(n, a, b);
            diff = Math.abs(integral - integPrev);
            System.out.format("%10d    %15.13f    %15.13f    %15.13f%n"
                    , i, h, integral, diff);
            
            if (diff < 0.0000001 || i > 25) {
                running = false;
            }
            integPrev = integral;
        }
    }   
}
