public class Interaction {


	// constants

	// the minimum distance to treat two particles
	// this gives a finite cap the the acceleration of two particles
	private final double MIN_DISTANCE = 0.0001; // Double.MIN_VALUE;
	// i forget what this value is. meh
	public static final double G = 6.6E-11;
	private static final double COLOUMBS_CONSTANT = 8.9785517873681764E9;
	
	private Particle first, second;

	public Interaction(Particle first, Particle second) {
		// TODO do some sort of dispatch based on the dynamic types of the
		// particles
		this.first = first;
		this.second = second;
	}

	public void calculate() {
		Vec3D r = first.displacement(second);

		Vec3D f = Vec3D
				.add(Vec3D.mult(r,
						-G * first.m * second.m / (Math.pow(r.mag(), 3))),
						Vec3D.mult(r, COLOUMBS_CONSTANT * first.q * second.q
								/ (Math.pow(r.mag(), 3))));

		// calculate the acceleration on each object
		// F = ma; a = F / m
		Vec3D a1 = new Vec3D(f);
		a1.mult(1 / first.m);
		Vec3D a2 = new Vec3D(f);
		a2.mult(1 / second.m);
		a2.invert(); // newton's third law: forces are equal and opposite

		first.a.add(a1);
		second.a.add(a2);

		// F = u * q1 * q2 / r^2, in the direction of r
		// this is equivilant to
		// F = u * q1 * q2 / |r|^3

		// // experiment: let be Gaussian
		// // F = k e^-(x^2)
		// double c = 1000;
		// Vec3D f = Vec3D.mult(r, COLOUMBS_CONSTANT * this.q * cother.q *
		// Math.exp(-(r.mag()*r.mag())/c));
	}
}
