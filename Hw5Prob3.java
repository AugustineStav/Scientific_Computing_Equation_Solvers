/* 
 * Implementation of Metropolis Algorithm and Ising Model from:
 *      Physic 242: Computational Physics
 */

/**
 * Hw5Prob3.java
 * Purpose: This Program builds an Ising Model circular spin chain and uses a 
 * Monte Carlo simulation to calculate the probability of being in a given spin 
 * state. This is called the Metropolis Algorithm.
 * 
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw5prob3;
import java.util.Random;

public class Hw5Prob3 {
    public static void main(String[] args) {
        int N = 100; //the number of spins
        int[] spins = new int[N];
        int correlSize = 10;
        double stdDev;
        //correl[j+1] = 1/n sum(S_i * S_(i+j))
        double[] correl = new double[correlSize]; 
        double[] correlTot = new double[correlSize]; 
        double[] correlSqTot = new double[correlSize]; 
        int flipsInitital = 100*N; //the number of flips to discard
        int flipsCalcCorrel = 1000000;
        double J = 1.0; //positive number, ferromagnetic
        double temp = 1.0; //the temperature (with k_boltzmann = 1)
        Random rand = new Random();
        //the frequency of having total spin == key
        
        for (int i = 0; i < spins.length; i++) {
                spins[i] = 1;
        }
        
        for (int i = 0; i < flipsInitital; i++) {
            flipAccept(spins, temp, rand, J);
        }
        
        int trials = 0;
        for (int i = 1; i < flipsCalcCorrel+1; i++) {
            flipAccept(spins, temp, rand, J);
            if (i%(10*N) == 0) {
                trials++;
                calcCorrel(spins, correl);
                for (int j = 0; j < correl.length; j++) {
                    correlTot[j] += correl[j];
                    correlSqTot[j] += correl[j]*correl[j];
                }
            }
        }
        
        //average the correlations and squares of correlations
        System.out.println("C(j) for a circle of Ising Spins:");
        for (int j = 0; j < correl.length; j++) {
            correlTot[j] = correlTot[j]/trials;
            correlSqTot[j] = correlSqTot[j]/(trials*trials);
            stdDev = Math.sqrt(Math.abs(correlSqTot[j] - 
                    correlTot[j]*correlTot[j])/(trials - 1));
            System.out.format("C(%2d) = %8.6f ± %8.6f%n", j+1, 
                    correlTot[j], stdDev);
        }
    }
    
    //return false if the flip is rejected
    //return true if the flip is accepted
    public static boolean flipAccept(int[] spins, double temp, Random rand, 
            double J){
        double energy1 = energy(spins, J);
        double energy2;
        int i = rand.nextInt(spins.length);
        spins[i] *= -1; //flip a random spin
        energy2 = energy(spins, J);
        if (energy2 > energy1) {
            if (rand.nextDouble() >= Math.exp((energy1-energy2)/temp)) {
                spins[i] *= -1; //the spin change is rejected
                return false;
            }
        }
        return true;
    }
    
    public static double energy(int[] spins, double J){
        //initialize energy to the end-beginning ring connection contribution
        double ener = -J* spins[spins.length-1] * spins[0];
        for (int i = 1; i < spins.length; i++) {
            ener += -(J * spins[i] * spins[i-1]); 
        }
        return ener;
    }
    
    public static void calcCorrel(int[] spins, double[] correl) {
        int n = spins.length;
        for (int j = 0; j < correl.length; j++) { //j=1 is really 0 here
           double correlRun = 0.0; //running totals for the correlations at
                                   //different j
           for (int i = 0; i < n; i++) {
               correlRun += spins[i] * spins[(i+j+1)%n];
           }
           correl[j] = correlRun/n;
        }
    }
}
