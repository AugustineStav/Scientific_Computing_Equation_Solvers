/*
 * Shooting method and Numerov integration implemenation:
 *      UCSC Physics 242
 */

/**
 * Hw3Prob6c.java
 * Purpose: This Program numerically finds the two lowest energy eigenvalues of
 * the quantum mechanical oscillator with V(x) = (x^4)/4
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */
package hw3prob6;

public class Hw3Prob6 {
    //lowest energy solution: psi(x) = psi(-x) (even)
    //above condition goes to psi(h) - psi(-h) = 0
    //first excited energy solution: psi(0) = 0 (odd)
    //d^2/dx^2 (psi) + k^2 psi = 0, k = 2(E-V)
    public static double kSquared(double x, double energy) {
        //return (2.0*energy - x*x); //simple oscillator
        return (2.0*energy - x*x*x*x/2.0);
    }
    
    public static void numerov(double h, double[] psi, double[] x, 
            double energy) {
        double kSq0 = kSquared(x[0], energy);
        double kSq1 = kSquared(x[1], energy);
        double kSq2 = kSquared(x[1]+h, energy);
        double factor0 = 1.0 + (1.0/12)*h*h*kSq0;
        double factor1 = 2.0 - (10.0/12)*h*h*kSq1;
        double factor2 = 1.0 + (1.0/12)*h*h*kSq2;
        double psi0 = psi[0];
        double psi1 = psi[1];
        psi[0] = psi1;
        psi[1] = (factor1*psi1 - factor0*psi0)/factor2;
        x[0] = x[0] + h;
        x[1] = x[1] + h;        
    }
    
    //a "function" of energy to find the root psi(E, h) - psi(E, -h) = 0
    public static double f(double[] psi, double[] x, 
            double energy, boolean groundState) { 
        double h = 0.01;
        psi[0] = 0.0; 
        psi[1] = h;  
        x[0] = -1000*h;
        x[1] = x[0]+h;
        double psiTwoStepsPrev = psi[0];
        if (groundState) {
            while (x[1] < h) { //stop when x[1] is iterated to h (even case)
                psiTwoStepsPrev = psi[0];
                numerov(h, psi, x, energy);
            }
            return psi[1] - psiTwoStepsPrev; //root condition for even function
        } else {
            while (x[1] < 0) { //stop when x[1] is iterated to 0 (odd case)
                numerov(h, psi, x, energy);
            }
            return psi[1]; //root condition for an odd function
        }
    }
    
    public static void main(String[] args) {
        double[] psi = new double[2]; 
        double[] x = new double[2];
        double ener0 = 0.0;
        double ener1 = 4.0;
        double ener2;
        double enerDiffMin = 0.0001;
        boolean running = true;
        
        System.out.println("Lowest Energy States, Shooting Method:");
        while (running) {
            ener2 = 0.5*(ener0 + ener1);
            if ( ((f(psi, x, ener0, true) < 0) && (f(psi, x, ener2, true) < 0)) 
                    || ((f(psi, x, ener0, true) > 0) && 
                    (f(psi, x, ener2, true) > 0)) ) {
                ener0 = ener2;
            } else {
                ener1 = ener2;
            }
            if ((ener1 - ener0) < enerDiffMin) {
                running = false;
            }
        }
        System.out.format("Ground State Energy: %8.4f%n", (ener1+ener0)*0.5);
        ener0 = 0.0;
        ener1 = 3.0;
        running = true;
        while (running) {
            ener2 = 0.5*(ener0 + ener1);
            if ( ((f(psi, x, ener0, false) < 0) && (f(psi, x, ener2, false) < 0)) 
                    || ((f(psi, x, ener0, false) > 0) && 
                    (f(psi, x, ener2, false) > 0)) ) {
                ener0 = ener2;
            } else {
                ener1 = ener2;
            }
            if ((ener1 - ener0) < enerDiffMin) {
                running = false;
            }
        }
        System.out.format("1st Excited State Energy: %8.4f%n", 
                (ener1+ener0)*0.5);
    }  
}
