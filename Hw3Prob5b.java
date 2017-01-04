/*
 * Velocity Verlet ODE integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/ode_int1.pdf
 */

/**
 * Hw3Prob5b.java
 * Purpose: This Program numerically finds the energy deviation using the 
 * velocity Verlet algorithm of the anharmonic oscillator E = p^2/2 + cosh(x) 
 * where v(0) = 0 and x(0) = 4
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob5b;

public class Hw3Prob5b {
    public static double f(double x) {
        //E = T + V
        //E = p^2/2 + cosh(x) 
        //V = cosh(x), F = -dV/dx = -sinh(x)
        return -Math.sinh(x);
    }
    
    public static double energy(double[] y) {
        return (y[1]*y[1])/2.0 + Math.cosh(y[0]);
    }
    
    public static void velocityVerlet(double h, double[] y)
    {
        y[1] += 0.5 * h * f(y[0]); //v_n+1/2
        y[0] += h * y[1]; //x_n+1 
        y[1] += 0.5* h * f(y[0]); //v_n+1
    }

    public static void main(String[] args) {
        double[] y = {4.0, 0.0}; //y[0] = x, y[1] = v
        //initialize x(0)= 4 and v(0) = 0
        double period = 2.91601;
        int[] periodNum = {1, 10, 100};
        double energyExact = Math.cosh(4.0);
        double h = period * 0.02; //50 time steps per period
        double energyDev = 0.0;
        double energ;
        
        System.out.println("Energy deviation using Velocity Verlet:");
        for (int periods: periodNum) {
            y[0] = 4.0;
            y[1] = 0.0;
            for (int i = 0; i <= periods*50.0; i++) {
                //if(periods == 1) {
                //    System.out.format("t: %6.3f, x: %6.3f, v: %6.3f, E: %6.3f%n", 
                //    h*i, x[0], v[0], energy(x, v));
                //}
                velocityVerlet(h, y);
                energ = energy(y);
                energyDev = Math.max(energyDev, 
                        Math.abs(energ - energyExact));
            }
            System.out.format("Periods: %2d, Max Energy Deviation: %7.5f%n", 
                    periods, energyDev);
        }
    }  
}