/*
 * Simpson's Rule integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/romberg.pdf
 */

/**
 * Hw3Prob5.java
 * Purpose: This Program numerically finds the period of the anharmonic
 * oscillator E = p^2/2 + cosh(x) where v(0) = 0 and x(0) = 4
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob5;

public class Hw3Prob5 {
    public static double f(double x) {
        //dt = dx/v(x)
        return -1.0/Math.sqrt(2*(Math.cosh(4) - Math.cosh(x)));
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
    
    public static double simpsons(int n, double a, double b) {
        double h = (b-a)/n;
        double oddSum = 0;
        double evenSum = 0;
        double fA = f(a);
        double fB = f(b);
        double x = 0;

        for (int i = 1; i < n; i+=2) {
            x = a + i*h;
            oddSum += f(x);
        }

        for (int j = 2; j < n; j+=2) {
            x = a + j*h;
            evenSum += f(x);
        }
        
        return h*(fA + 4.0*oddSum + 2.0*evenSum + fB)/3.0;
    }

    public static void main(String[] args) {
        double a = 3.99; //integrate over half period, not at x=4.0 because
                             //that would divide by zero (see f(double x))
        double b = -3.99;
        int n = 2;
        double h = (b-a)/n;
        double integral = 0;
        double integPrev = simpsons(n, a, b);
        double diff;
        boolean running = true;
        int i = 1;
        
        System.out.println("Simpsons method: integral of dt = "
                + "-1/sqrt[2cosh4 - 2coshx] from 3.9 to -3.9");
        System.out.format("%10s    %15s    %15s%n", "trial", 
                "h", "Half Period");
        System.out.format("%10d    %15.11f    %15.11f%n", 
            i, h, integPrev);

        while (running) {
            n = 2*n;
            i++;
            h = (b-a)/n;
            integral = simpsons(n, a, b);
            diff = Math.abs(integral - integPrev);
            System.out.format("%10d    %15.11f    %15.11f%n", i, h, integral);
            
            if (diff < 0.000001 || i > 25) {
                running = false;
            }
            integPrev = integral;
        }
        double simpIntegral = integral;
        
        double c = 4.0;
        double d = 3.99;
        n = 2;
        integPrev = midpoinInteg(n, c, d);
        running = true;
        i = 1;
        while (running) {
            n = 2*n;
            i++;
            integral = midpoinInteg(n, c, d);
            diff = Math.abs(integral - integPrev);
            if (diff < 0.000001 || i > 25) {
                running = false;
            }
            integPrev = integral;
        }
        double midIntegral1 = integral; 
        System.out.println("\nMidpoint Integration from 4.0 to 3.9: " 
                + midIntegral1);
        
        c = -3.99;
        d = -4.0;
        n = 2;
        integPrev = midpoinInteg(n, c, d);
        running = true;
        i = 1;
        while (running) {
            n = 2*n;
            i++;
            integral = midpoinInteg(n, c, d);
            diff = Math.abs(integral - integPrev);            
            if (diff < 0.000001 || i > 25) {
                running = false;
            }
            integPrev = integral;
        }
        double midIntegral2 = integral; 
        System.out.println("\nMidpoint Integration from -3.9 to 4.0: " 
                + midIntegral2);        
        
//integrated over a half period: add up all integral pieces and mult*2        
        System.out.format("\nPeriod: %8.5f%n", 
                (simpIntegral + midIntegral1 + midIntegral2)*2);
    }  
}