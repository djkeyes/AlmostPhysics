package particles;

import java.util.List;
import java.util.ListIterator;

public class InteractionNetwork {
	private List<Particle> particles; 
	public InteractionNetwork(List<Particle> particles) {
		this.particles = particles;
	}
	
	public void applyInteractions(){

		int index = 0;
		for (ListIterator<Particle> i = particles.listIterator(); i.hasNext(); index++) {
			Particle a = i.next();
			for (ListIterator<Particle> j = particles.listIterator(); j
					.nextIndex() < index;) {
				// Create an Interaction object to calculate forces resulting from the interactions
				Particle b = j.next();
				
				Interaction interaction = new Interaction(a, b);
				interaction.calculate();
			}
		}
	}
}
