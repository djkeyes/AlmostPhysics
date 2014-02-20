import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.ListIterator;

import javax.swing.Timer;

import particles.Interaction;
import particles.InteractionNetwork;
import particles.Particle;

public class Updater implements ActionListener {
	private Timer timer;
	private IParticleDisplay display;
	private List<Particle> particles;
	
	int i=0;

	public Updater(int refreshRate, List<Particle> particles) {
		timer = new Timer(refreshRate, this);
		this.particles = particles;
	}

	public Updater(int refreshRate, List<Particle> particles,
			IParticleDisplay display) {
		this(refreshRate, particles);
		this.display = display;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// update all the positions
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

	public void start() {
		timer.start();
	}

}
