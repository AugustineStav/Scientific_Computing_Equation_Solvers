/*
 * Midpoint integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/integral_error.pdf
 * Newton Raphson method of root finding implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/root1.pdf
 */

/**
 * Hw3prob2.java
 * Purpose: This Program finds the root of the f(a) = 2/3, where f(a) = integral
 * of (1/24)(x^4)exp(-x)dx from x = 0 to a
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw3prob2;

public class Hw3Prob2 {
    public static double f(double x) { //fPrime in Newton-Raphson (NR)
        return (x*x*x*x)*Math.exp(-x)/24.0;
    }
    
    public static double midpoinInteg(int n, double a, double b) { //f in NR
        double h = (b-a)/n;
        double integral = 0;
        double x = 0;
        
        for (int i = 0; i < n; i++) {
            x = a + (i + 0.5)*h;
            integral += f(x);
        }
        return h*integral;
    }
    
    //use midpoint method to find integral to 3 decimal places given the upper
    //limit of integration
    public static double integ3Dec(double b) { 
        double a = 0.0;
        int n = 2;
        double integral = 0;
        double integPrev = midpoinInteg(n, a, b);
        double diff;
        boolean running = true;
        int i = 1;
        while (running) {
               n = 2*n;
               i++;
               integral = midpoinInteg(n, a, b);
               diff = Math.abs(integral - integPrev);
               if (diff < 0.001 || i > 25) {
                   running = false;
               }
               integPrev = integral;
        }
        return integral;
    }

    public static void main(String[] args) {
        double x0 = 0.0;
        double nrAcc = 0.001;
        double a = 3.0; //here, 'a' is the *upper* limit of integration, to
                        //match the problem set
        double integral = 0;
        
        System.out.println("Newton-Raphson: f(a) = integral of "
                + "(1/24)(x^4)exp(-x)dx from x = 0 to a, root where "
                + "f(a) = 2/3");
        System.out.format("%15s   %15s    %15s    %15s%n", 
                "a", "x1", "f'(a)", "f(a)");
        
        while (Math.abs(a-x0) > nrAcc) {
            integral = integ3Dec(a);
            x0 = a;
            a = a - (integral - 2.0/3.0)/f(a);
            System.out.format("%15.7f   %15.7f    %15.7f    %15.7f%n", 
                    x0, a, f(a), integral); 
        }
        System.out.format("Newton-Raphson root: %7.4f%n", a);
    }   
}
