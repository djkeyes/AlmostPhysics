package particles;

import plotting.RealtimePlot;
import util.Vec3D;

public class Interaction {

	private static Force defaultForceModel = Force.M_SQUARE_INVERSE;

	// TODO: figure out a better way to handle the how-to-calculate-potential-energy problem. Should integrate-able forces be a special
	// subclass? should they handle PE metadata?
	private final static boolean trackPotentialEnergy = true;

	private Particle first, second;

	public Interaction(Particle first, Particle second) {
		// TODO do some sort of dispatch based on the dynamic types of the particles
		// this might involve having specific types of forces for each interaction type
		this.first = first;
		this.second = second;
	}

	public void calculate() {
		Vec3D f = defaultForceModel.calcForce(first, second);
		// System.out.println(f.mag());

		// calculate the acceleration on each object
		// F = ma; a = F / m
		Vec3D a1 = new Vec3D(f);
		a1.mult(1 / Math.abs(first.m));
		Vec3D a2 = new Vec3D(f);
		a2.mult(1 / Math.abs(second.m));
		a2.invert(); // newton's third law: forces are equal and opposite

		first.a.add(a1);
		second.a.add(a2);

		if (trackPotentialEnergy) {
			double pe = defaultForceModel.calcPotentialEnergy(first, second);
			first.pe += pe;
			second.pe += pe;
		}
	}
}
