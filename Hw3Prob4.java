/*
 * Second Order Runge Kutta ODE solver implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/ode_int1.pdf
 */

/**
 * Hw3prob4.java
 * Purpose: This Program uses the Second Order Runge Kutta Method to find the
 * period of oscillations for a particle in the potential V(x) = (x^4)/4
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob4;

public class Hw3Prob4 {    
    public static void calcK(double t, double[] y, double[] k) {
        k[0] = y[1]; // dx/dt = v 
        k[1] = -y[0]*y[0]*y[0]; // dp/dt = dv/dt (unit mass) = f(x) = -dV/dx
    }
    
    public static void rungeKutta2(double h, double t, double[] y) {
        double[] k1 = new double[y.length];
        double[] k2 = new double[y.length];
        double[] yStep = new double[y.length];
        int i = 0;
        calcK(t, y, k1);
        for (i = 0; i < y.length; i++){
            yStep[i] = y[i] + 0.5 * h * k1[i];
        }
        calcK(t+0.5*h, yStep, k2);
        for (i = 0; i < y.length; i++){
            y[i] = y[i] + h * k2[i];
        }
    }
   
    public static double period(double h, double tMin, double[] y) {
        double t = tMin;
        int count = 0;
        double y1 = 0.0; //y1 will be the velocity at the step before last
        System.out.format("%10s   %10s   %10s%n", 
                "half T", "x", "v");
        do {
            y1 = y[1];
            if (count % 250 == 0) {
                System.out.format("%10.7f   %10.7f   %10.7f%n", t, y[0], y[1]);
            }
            rungeKutta2(h, t, y);
            t += h;
            count++;
        } while (y[1] < 0); //stop the loop when the velocity crosses 0 again
        
        //Let the time of the half period be t0. At t0, v0 = 0. vNow is y[1]
        //at this stage of the program (overshot v0 = 0). vPrev = is y[1] at
        //the time step immediately before this.
        //linear interp: v0-vNow = -vNow = (vNow - vPrev)(t0 - tNow)/h
        //so, t0 = tNow - h*vNow/(vNow - vPrev)
        double halfPeriod = t - h*y[1]/(y[1] - y1);
        return 2*(halfPeriod);
    }

    public static void main(String[] args) {
        double tMin = 0.0;
        //error is O(h^2), so pick h ~ sqrt(0.001)
        double h = 0.05;
        double amp = 0.1;
        double v0 = 0.0;
        //y[0] = x and y[1] = v
        double[] y = {amp, v0}; //initialize x(0) = amp, v(0) = v0
        y[0] = amp; 
        y[1] = v0;  
        
        System.out.println("Runge-Kutta Second Order: Period of V(x) = (x^4)/4");
        double period1 = period(h, tMin, y);
        System.out.printf("Period with h = %6.4f: %7.4f%n",h, period1);
        System.out.println();
        
        //reset values to find the period for a h = h/2
        h = h/2.0;
        y[0] = amp; 
        y[1] = v0; 
        double period2 = period(h, tMin, y);
         System.out.printf("Period with h = %6.4f: %7.4f%n",h, period2);
        if (Math.abs(period2-period1) < 0.00075) {
            //Err(h)~O(h^2), so abs(Err(h/2) - Err(h))~3/4 of target 0.001 error
            System.out.println("The period is correct to three sig figs.");
        } else {
            System.out.println("Error is too large. Choose a smaller h.");
        }       
    }   
}

