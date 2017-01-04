/*
 * Implementation of velocity Verlet algorithm with thermal noise:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/homework/homework4.pdf
 * Gaussian random number generator implementation:
 *      http://numerical.recipes/
 */

/**
 * Hw4Prob4.java
 * Purpose: This Program simulates a harmonic oscillator using the velocity
 * Verlet algorithm with thermal noise and viscous damping.
 */
package hw4prob4;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hw4Prob4 {
    static double gasDev(ArrayList<Double> betas, Random rand) {
        double fac, rsq, v1, v2;
        if (betas.isEmpty()) {
            do {
               v1 = 2.0*rand.nextDouble() - 1.0;
               v2 = 2.0*rand.nextDouble() - 1.0;
               rsq = v1*v1 + v2*v2;
            } while (rsq >= 1.0 || rsq == 0.0);
            fac = Math.sqrt(-2.0*Math.log(rsq)/rsq);
            betas.add(v1*fac);
            return v2*fac;
        } else { 
            return betas.remove(0);
        }
    }
    
    public static double f(double x) { //force
        //V = x^2 /2, F = -dV/dx = -x
        return -x;
    }
    
    public static void velocityVerlet(double h, double[] y, double beta)
    {
        double xN = y[0]; //get current x before updating to n + 1 to calculate
                          //v_n+1
        y[0] += h*(y[1] + (h * f(xN) + beta)/2.0)/(1.0 + h/2.0); //x_n+1  
        y[1] += h*(f(xN) + f(y[0]))/2.0 - (y[0] - xN) + beta; //v_n+1
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter seed:");
        int seed = in.nextInt();
        Random rand = new Random(seed);
        ArrayList<Double> betas = new ArrayList<>();
        double[] y = {0.0, 0.0}; //y[0] = x, y[1] = v
        //initialize x(0)= 0 and v(0) = 0 (particle at rest at origin)
        double h = 0.1;
        double stdDevFac = Math.sqrt(2*h); //beta has variance 2h
        int trials = 10000000;
        //xEvenMom[0] = x^2 sum, xEvenMom[1] = x^4 sum, xEvenMom[2] = x^6 sum 
        double[] xEvenMom = new double[3]; //the first three even moments of x 
        
        System.out.format("%n%10s %10s %10s %10s %10s%n", "time step", "x(t)", 
                "x(t)^2", "x(t)^4", "x(t)^6");
        for (int i = 0; i <= trials; i++) {
            velocityVerlet(h, y, stdDevFac * gasDev(betas, rand));
            xEvenMom[0] += y[0]*y[0];
            xEvenMom[1] += y[0]*y[0]*y[0]*y[0];
            xEvenMom[2] += y[0]*y[0]*y[0]*y[0]*y[0]*y[0];
            if (i%1000000 == 0) {
                System.out.format("%10d %10.3f %10.3f %10.3f %10.3f%n", i, 
                        y[0], y[0]*y[0], y[0]*y[0]*y[0]*y[0], 
                        y[0]*y[0]*y[0]*y[0]*y[0]*y[0]);
            }
        }
        System.out.format("%n%9s %7s %7s %7s%n", "", "<x^2>", "<x^4>", "<x^6>");
        System.out.format("%9s %7.3f %7.3f %7.3f%n", "numerical",
                xEvenMom[0]/trials, xEvenMom[1]/trials, xEvenMom[2]/trials);
        
        //Boltzmann dist.: p(x) = A*exp(-x^2 /2)
        //normalization: A = 1/sqrt(2*pi)
        //<x^2> = integral x^2 * p(x) dx, x=-inf..inf and similarly for higher
        //moments. The analytic values for the even moments follow:
        System.out.format("%9s %7.3f %7.3f %7.3f%n", "analytic",
                1.0, 3.0, 15.0);
    }  
}
