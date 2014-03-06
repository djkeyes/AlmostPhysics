import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Timer;

import particles.InteractionNetwork;
import particles.Particle;
import plotting.RealtimePlot;

public class Updater implements ActionListener {
	private Timer timer;
	private IParticleDisplay display;
	private List<Particle> particles;

	// this plots two things
	// one: the "lag" time--the time it takes between calling a function and calling it again
	// two: the "processing" time--the time it takes from the start of a single update to the end
	// these should be fairly similar--any difference in required time is a result of any other processing that's going on (like
	// plotting)--except when the processing time is greater than the refresh rate. In that case, the lag time should remain fairly
	// stable, but the time to process a single function should increase, since it must wait for the previous
	// function to finish running.
	private static final boolean plotTimestepVsRealtime = true;
	private long lastTime;
	private int nSteps;
	private RealtimePlot timeLagPlot;
	private final String lagDataSeriesName = "Time Lag";
	private final String updateTimeDataSeriesName = "Time Required";

	public Updater(int refreshRate, List<Particle> particles) {
		timer = new Timer(refreshRate, this);
		this.particles = particles;

		if (plotTimestepVsRealtime) {
			lastTime = System.currentTimeMillis();
			nSteps = 0;
			timeLagPlot = new RealtimePlot("Key time metrics for each timestep", "Number of simulation timesteps",
					"Time elapsed (ms)", 300);
			timeLagPlot.addDataSeries(lagDataSeriesName);
			timeLagPlot.addDataSeries(updateTimeDataSeriesName);
			timeLagPlot.setVisible(true);
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
			nSteps++;
		}
	}

	public void start() {
		timer.start();
	}

}
