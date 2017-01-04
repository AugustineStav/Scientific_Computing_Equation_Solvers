/*
 * Implementation of Random Walk directions:
 *      http://research.pbsci.ucsc.edu/physics/narayan/Courses/
 *           115_242_2016/homework/homework4.pdf
 */

/**
 * Hw4Prob3.java
 * Purpose: This Program computes the result of a random walk along the x-axis.
 */
package hw4prob3;
import java.util.Random;
import java.awt.Color; 
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

public class Hw4Prob3 extends ApplicationFrame {
    public Hw4Prob3(String applicationTitle, String chartTitle, 
            XYSeriesCollection dataset) {
        super(applicationTitle);
        JFreeChart xylineChart = ChartFactory.createScatterPlot(
                chartTitle , "time step" , 
                "red: x distance, blue: x dist squared" , dataset, 
                PlotOrientation.VERTICAL, true ,true ,false);
        ChartPanel chartPanel = new ChartPanel( xylineChart );
        chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
        final XYPlot plot = xylineChart.getXYPlot( );
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
        renderer.setSeriesPaint( 0 , Color.RED );
        renderer.setSeriesPaint( 1 , Color.BLUE );
        plot.setRenderer( renderer ); 
        setContentPane( chartPanel ); 
    }  
    public static void main(String[] args) {
        int trials = 50000;
        int tMax = 100;
        double x;
        double xSquared;
        double[] xAve = new double[tMax];
        double[] xSquaredAve = new double[tMax];
        Random rand = new Random();
        
        for (int i = 0; i < trials; i++) {
            x = 0;
            for (int t = 1; t < tMax; t++) {
                if (rand.nextDouble() < 0.5) {
                    x += 1;
                } else {
                    x -= 1;
                }
                xAve[t] += x;
                xSquaredAve[t] += x*x;
            }
        }
        
        XYSeries xPoints = new XYSeries("<x(t)>");
        XYSeries xSquaredPoints = new XYSeries("<x(t)^2>");
        
        System.out.println("x-axis Random Walk:");
        System.out.format("%5s %10s %10s%n", "t", "<x(t)>", "<x(t)^2>");
        for (int t = 0; t < tMax; t++) {
            x = xAve[t]/trials;
            xSquared = xSquaredAve[t]/trials;
            xPoints.add(t, x);
            xSquaredPoints.add(t, xSquared);
            if (t%10 == 0) {
                System.out.format("%5d %10.3f %10.3f%n", t, x, xSquared);
            }
        }
        
        XYSeriesCollection dataset = new XYSeriesCollection( );
        dataset.addSeries(xPoints);
        dataset.addSeries(xSquaredPoints);
        Hw4Prob3 chart = new Hw4Prob3("<x> and <x^2> vs t", 
                "<x> and <x^2> vs t", dataset);
        chart.pack( );          
        RefineryUtilities.centerFrameOnScreen( chart );          
        chart.setVisible( true ); 
    } 
}
