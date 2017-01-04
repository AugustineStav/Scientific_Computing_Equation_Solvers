/*
 * Runga Kutta 2nd order integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/ode_int1.pdf
 */

/**
 * Hw3Prob5c.java
 * Purpose: This Program numerically finds the energy deviation using the 
 * Runga Kutta 2nd order algorithm of the anharmonic oscillator 
 * E = p^2/2 + cosh(x) where v(0) = 0 and x(0) = 4
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob5c;

public class Hw3Prob5c {    
    public static double energy(double x, double v) {
        return (v*v)/2.0 + Math.cosh(x);
    }
    
    public static void calcK(double[] y, double[] k) {
        k[0] = y[1]; // dx/dt = v 
        k[1] = -Math.sinh(y[0]); // dp/dt = dv/dt (unit mass) = f(x) = -dV/dx
    }
    
    public static void rungeKutta2(double h, double[] y) {
        double[] k1 = new double[y.length];
        double[] k2 = new double[y.length];
        double[] yStep = new double[y.length];
        int i = 0;
        calcK(y, k1);
        for (i = 0; i < y.length; i++){
            yStep[i] = y[i] + 0.5 * h * k1[i];
        }
        calcK(yStep, k2);
        for (i = 0; i < y.length; i++){
            y[i] = y[i] + h * k2[i];
        }
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
        
        System.out.println("Energy deviation using Runga Kutta 2:");
        for (int periods: periodNum) {
            y[0] = 4.0; 
            y[1] = 0.0;
            if (periods == 100) {
                System.out.println("100 Periods Table:");
                System.out.format("%9s   %9s   %9s   %9s%n", 
                    "t", "x", "v", "Energy");
            }
            int count = 0;
            for (int i = 0; i <= periods*50.0; i++) {
                if ((periods == 100) && (h*i < 260) && (count%1043 == 0)) {
                    System.out.format("%9.3f   %9.3f   %9.3f   %9.3g%n", 
                    h*i, y[0], y[1], energy(y[0], y[1]));
                }
                rungeKutta2(h, y);
                energ = energy(y[0], y[1]);
                energyDev = Math.max(energyDev, 
                        Math.abs(energ - energyExact));
                count++;
            }
            System.out.format("Periods: %2d, Max Energy Deviation: %7.5f%n", 
                    periods, energyDev);
        }
    }  
}