/*
 * Trapezium integration implemenation:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/handouts/integral_error.pdf
 * JFreeChart implementation:
 *      http://www.tutorialspoint.com/jfreechart/jfreechart_xy_chart.htm
 */

/**
 * Hw2prob3.java
 * Purpose: This Program finds the integral of 1/(1 + cos^2(x))
 *          from x = 0 to pi
 * Author:  Augustine Stav (astav@ucsc.edu) 
 */

package hw2prob3;
import java.lang.Math;
import java.awt.Color; 
import java.awt.BasicStroke; 
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


public class Hw2prob3 extends ApplicationFrame {
    public Hw2prob3( String applicationTitle, String chartTitle, 
            XYSeriesCollection dataset) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createScatterPlot(
                chartTitle , "n (number of intervals)" , "Integral Error" , 
                dataset, PlotOrientation.VERTICAL, true ,true ,false);
        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        plot.setRenderer( renderer ); 
        setContentPane( chartPanel ); 
    }
    
    public static double f(double x) {
        return 1/(1 + Math.cos(x)*Math.cos(x));
    }
    
    public static double trapInteg(int n, double a, double b) {
        double h = (b-a)/n;
        double sumNoEndpoints = 0;
        double x = 0;
        double fA = f(a);
        double fB= f(b);
        
        for (int i = 1; i < n; i++) {
            x = a + i*h;
            sumNoEndpoints += f(x);
        }
        
        return h*(0.5*(fA + fB) + sumNoEndpoints);
    }

    public static void main(String[] args) {
        XYSeries dataPoints = new XYSeries("Integral Error vs. n");
        
        double a = 0.0;
        double b = Math.PI;
        int n = 2;
        double h = (b-a)/n;
        double integral;
        double integPrev = trapInteg(n, a, b);
        double diff;
        double diffPrev = 0;
        boolean running = true;
        int i = 1;
        
        System.out.println("Trapezium method: integral of 1/(1 + cos^2(x))"
                + " dx from x = 0 to pi");
        System.out.format("%10s    %10s    %10s    %10s   %13s%n", "n", 
                "h", "integral", "|I_n-I_n+2|", "diffPrev/diff");
        System.out.format("%10d    %10.8f    %10.8f    %10s    %10s%n", 
            n, h, integPrev, "no prev", "no prev");

        while (running) {
            n += 2;
            i++;
            h = (b-a)/n;
            integral = trapInteg(n, a, b);
            diff = Math.abs(integral - integPrev);
            
            dataPoints.add((double)n, diff);
            System.out.format("%10d    %10.8f    %10.8f    %10.8f    "
                    + "%10.6f%n", n, h, integral, diff, diffPrev/diff);
            
            if (diff < 0.0000001 || i > 25) {
                running = false;
            }
            integPrev = integral;
            diffPrev = diff;
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries( dataPoints );
        Hw2prob3 chart = new Hw2prob3("Integral Error vs n", 
                "Integral Error vs n", dataset);
        chart.pack( );          
        RefineryUtilities.centerFrameOnScreen( chart );          
        chart.setVisible( true ); 
    }   
}