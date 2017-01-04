/*
 * Fourth Order Runge Kutta ODE solver implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/ode_int1.pdf
 */

/**
 * Hw3prob3.java
 * Purpose: This Program uses the Fourth Order Runge Kutta Method to solve the
 * ordinary differential equation u' = u - v + 2, v' = v - u + 4t, with
 * initial conditions u(0) = -1, v(0) = 0 and interval 0 <= t <= 1.        
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob3;

public class Hw3Prob3 {
    public static double analytic(double t) {
        return -0.5*Math.exp(2.0*t) + t*t + 2*t - 0.5;
    }
    
    public static void calcK(double t, double[] y, double[] k) {
        k[0] = y[0] - y[1] + 2.0;   //u'
        k[1] = y[1] - y[0] + 4.0*t; //v'
    }
    
   public static void rungeKutta4(double h, double t, double[] y) {
        double[] k1 = new double[y.length];
        double[] k2 = new double[y.length];
        double[] k3 = new double[y.length];
        double[] k4 = new double[y.length];
        double[] yStep = new double[y.length];
        int i = 0;
        calcK(t, y, k1);
        for (i = 0; i < y.length; i++){
            yStep[i] = y[i] + 0.5 * h * k1[i];
        }
        calcK(t+0.5*h, yStep, k2);
        for (i = 0; i < y.length; i++){
            yStep[i] = y[i] + 0.5 * h * k2[i];
        }
        calcK(t+0.5*h, yStep, k3);
        for (i = 0; i < y.length; i++){
            yStep[i] = y[i] + h * k3[i];
        }
        calcK(t+h, yStep, k4);
        for (i = 0; i < y.length; i++){
            y[i] = y[i] + h*(k1[i] + 2*(k2[i] + k3[i]) + k4[i])/6.0;
        }
    }
   
    public static void printTable(double h, double h2, double tMin, double tMax, 
            double[] y, double[] y2) {
        double t = tMin;
        double t2 = tMin;
        double uAnalytic = analytic(t);
        System.out.format("%10s   %10s   %10s   %10s   %10s   %10s%n", 
                "t", "u_1", "u_2", "u_ana", "|u1-u_ana|", "|u2-u_ana|");
        while (t < tMax) {
            System.out.format("%10.7f   %10.7f   %10.7f   %10.7f   %10.7f   "
                    + "%10.7f%n", t, y[0], y2[0], uAnalytic, 
                    Math.abs(y[0]-uAnalytic), Math.abs(y2[0]-uAnalytic)); 
            rungeKutta4(h, t, y);
            t += h;
            
            rungeKutta4(h2, t2, y2);
            t2 += h2;
            rungeKutta4(h2, t2, y2);
            t2 += h2;
            
            uAnalytic = analytic(t);
        }
    }

    public static void main(String[] args) {
        //y[0] = u and y[1] = v
        double[] y = {-1.0, 0.0}; //initialize u(0) = -1, v(0) = 0 for h = 0.1
        double[] y2 = {-1.0, 0.0}; //for h2 = 0.05
        double tMin = 0.0;
        double tMax = 1.0;
        
        double h = 0.1;
        y[0] = -1.0; //u(0) = -1
        y[1] = 0.0;  //v(o) = 0
        
        double h2 = 0.05;
        y2[0] = -1.0; //u(0) = -1
        y2[1] = 0.0;  //v(o) = 0
        
        System.out.println("Runge-Kutta: u' = u - v + 2, v' = v - u + 4t");
        System.out.format("              h_1: %3.2f    h_1: %3.2f%n", h, h2);
        printTable(h, h2, tMin, tMax, y, y2);
    }   
}
