package plotting;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;

/**
 * A class which provides for a plotting window that takes real-time data updates. It's intentionally simple; it's just here to serve
 * as quick+dirty interface to a more complex plotting library.
 * 
 * Just create a RealtimePlot object, make it visible, set some names, and then send data to it. It's that easy!
 * 
 * See the main() function for examples. TODO: write an example class.
 * 
 * By default, JFreeChart lets you zoom (scroll to zoom, zoom to drawn region, right click and select zoom) using the mouse. TODO: also
 * allow for panning, which seems to be nontrivial
 */
public class RealtimePlot {
	private static final long serialVersionUID = 1L;

	private static int DEFAULT_WIDTH = 500, DEFAULT_HEIGHT = 270;

	private ApplicationFrame appFrame;
	private XYSeriesCollection dataset;
	
	private int maxValsToKeep;

	/**
	 * @param title chart title
	 * @param xAxisLabel x axis label
	 * @param yAxisLabel y axis label
	 * @param maxValsToKeep the number of values to keep in any dataset before discarding values. 0 to keep all values.
	 */
	public RealtimePlot(String title, String xAxisLabel, String yAxisLabel, int maxValsToKeep) {
		this.maxValsToKeep = maxValsToKeep;
		
		appFrame = new ApplicationFrame(title);

		dataset = new XYSeriesCollection();

		JFreeChart chart = ChartFactory.createXYLineChart(title,xAxisLabel,yAxisLabel,dataset);

		// so many options!
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0)); // what does this do?
		plot.setDomainCrosshairVisible(true); // and this?
		plot.setRangeCrosshairVisible(true); // and this?

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		ChartPanel panel = new ChartPanel(chart);
		// soooo many options!
		panel.setFillZoomRectangle(true); // do we need this?
		panel.setMouseWheelEnabled(true); // enables mouse wheel zoom
		panel.setPreferredSize(new java.awt.Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));
		appFrame.setContentPane(panel);

		appFrame.pack();
		RefineryUtilities.centerFrameOnScreen(appFrame);
	}
	public RealtimePlot(String title, String xAxisLabel, String yAxisLabel) {
		this(title, xAxisLabel, yAxisLabel, 0);
	}

	public void addDataSeries(String key) {
		XYSeries dataSeries = new XYSeries(key);
		dataset.addSeries(dataSeries);
		appFrame.repaint();
	}

	public void addData(String key, double x, double y) {
		dataset.getSeries(key).add(x, y);
		appFrame.repaint();
		if(maxValsToKeep > 0 && dataset.getSeries(key).getItemCount() > maxValsToKeep){
			dataset.getSeries(key).remove(0);
		}
	}

	public void setVisible(boolean isVisible) {
		// is this even necessary? why not dump it into the constructor?
		appFrame.setVisible(isVisible);
	}

	/**
	 * Starting point for the demonstration application.
	 * 
	 * @param args
	 *            ignored.
	 */
	public static void main(String[] args) {
		// fyi, some of these variables are final because I'm passing them to an anonymous class.

		final RealtimePlot demo = new RealtimePlot("Time Series Chart Demo 1", "X values", "Y values");
		demo.setVisible(true);

		// The datasets are keyed using a string. Don't forget yours!
		final String dataSeries1 = "Inverse function";
		final String dataSeries2 = "Linear function";
		demo.addDataSeries(dataSeries1);
		demo.addDataSeries(dataSeries2);

		// add some data
		for (int i = 0; i < 5; i++) {
			double x = ((double) i - 10.5) / 10;
			double y = 1 / x;
			demo.addData(dataSeries1, x, y);
		}
		for (int i = 0; i < 5; i++) {
			double x = ((double) i - 10.5) / 10;
			double y = x;
			demo.addData(dataSeries2, x, y);
		}

		// time ticks every 100 millis
		Timer t = new Timer(100, new ActionListener() {
			int i = 5;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				double x = ((double) i - 10.5) / 10;
				double y = 1 / x;
				demo.addData(dataSeries1, x, y);
				demo.addData(dataSeries2, x, x);
				i++;
				System.out.println("adding data and repainting");
			}
		});
		t.start();
	}

}
