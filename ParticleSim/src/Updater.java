import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import particles.InteractionNetwork;
import particles.Particle;
import plotting.RealtimePlot;
import util.Vec3D;

public class Updater implements ActionListener {
	private Timer timer;
	private IParticleDisplay display;
	private List<Particle> particles;

	private int nSteps;
	// this plots two things
	// one: the "lag" time--the time it takes between calling a function and calling it again
	// two: the "processing" time--the time it takes from the start of a single update to the end
	// these should be fairly similar--any difference in required time is a result of any other processing that's going on (like
	// plotting)--except when the processing time is greater than the refresh rate. In that case, the lag time should remain fairly
	// stable, but the time to process a single function should increase, since it must wait for the previous
	// function to finish running.
	private static final boolean plotTimestepVsRealtime = false;
	private long lastTime;
	private RealtimePlot timeLagPlot;
	private final String lagDataSeriesName = "Time Lag";
	private final String updateTimeDataSeriesName = "Time Required";
	
	// hopefully this plot is straightforward
	private static final boolean plotTotalMomentum = true;
	private RealtimePlot momentumPlot;
	private final String momentumXDataSeriesName = "X Momentum";
	private final String momentumYDataSeriesName = "Y Momentum";
	private final String momentumZDataSeriesName = "Z Momentum";

	public Updater(int refreshRate, List<Particle> particles) {
		timer = new Timer(refreshRate, this);
		this.particles = particles;


		nSteps = 0;
		if (plotTimestepVsRealtime) {
			lastTime = System.currentTimeMillis();
			timeLagPlot = new RealtimePlot("Key time metrics for each timestep", "Number of simulation timesteps",
					"Time elapsed (ms)", 300);
			timeLagPlot.addDataSeries(lagDataSeriesName);
			timeLagPlot.addDataSeries(updateTimeDataSeriesName);
			timeLagPlot.setVisible(true);
		}
		if (plotTotalMomentum) {
			momentumPlot = new RealtimePlot("Momentum vs time", "Number of simulation timesteps",
					"Total momtum of particle system", 100);
			momentumPlot.addDataSeries(momentumXDataSeriesName);
			momentumPlot.addDataSeries(momentumYDataSeriesName);
			momentumPlot.addDataSeries(momentumZDataSeriesName);
			momentumPlot.setVisible(true);
		}
	}

	public Updater(int refreshRate, List<Particle> particles, IParticleDisplay display) {
		this(refreshRate, particles);
		this.display = display;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		long functionStartTime = System.currentTimeMillis();

		// update all the positions
		synchronized (particles) {
			InteractionNetwork network = new InteractionNetwork(particles);
			network.applyInteractions();

			for (Particle p : particles) {
				// timer stores the delay in ms, but our simulation runs in seconds.
				// consider standardizing this, or moving the conversion to a more
				// obvious location?
				p.update(timer.getDelay() / 1000.);
			}

			// call a redraw
			if (display != null) {
				display.redraw(particles);
			}
		}

		if (plotTimestepVsRealtime) {
			long curTime = System.currentTimeMillis();
			long diff = curTime - lastTime;
			timeLagPlot.addData(lagDataSeriesName, nSteps, diff);

			diff = curTime - functionStartTime;
			timeLagPlot.addData(updateTimeDataSeriesName, nSteps, diff);

			lastTime = curTime;
		}
		if(plotTotalMomentum){
			double momentumX = 0, momentumY = 0, momentumZ = 0;
			for(Particle p : particles){
				Vec3D momentumVec = p.getMomentum();
				momentumX += momentumVec.v1;
				momentumY += momentumVec.v2;
				momentumZ += momentumVec.v3;
			}
			momentumPlot.addData(momentumXDataSeriesName, nSteps, momentumX);
			momentumPlot.addData(momentumYDataSeriesName, nSteps, momentumY);
			momentumPlot.addData(momentumZDataSeriesName, nSteps, momentumZ);
		}
		nSteps++;
	}

	public void start() {
		timer.start();
	}

}
