public class MassiveParticle extends Particle {

	// i forget what this value is. meh
	public static final double G = 6.6E-11;

	// mass
	private double m;

	public MassiveParticle(Vec3D x, double mass) {
		super(x);
		m = mass;
	}

	public MassiveParticle(Vec3D x, Vec3D v, double mass) {
		super(x, v);
		m = mass;
	}

	public void interact(Particle other) {
		// derpy non-polymorphic solution
		if (other instanceof MassiveParticle) {
			MassiveParticle mother = (MassiveParticle) other;
			
			Vec3D r = this.displacement(mother);
			// F = - G * m1 * m2 / r^2, in the direction of r (the minus sign
			// makes it point inward)
			// this is equivilant to
			// F = - G * m1 * m2 * r / |r|^3
			Vec3D f = Vec3D.mult(r,
					-G * this.m * mother.m / (Math.pow(r.mag(), 3)));

			// calculate the acceleration on each object
			// F = ma; a = F / m
			Vec3D a1 = new Vec3D(f);
			a1.mult(1 / this.m);
			Vec3D a2 = new Vec3D(f);
			a2.mult(1 / mother.m);
			a2.invert(); // newton's third law: forces are equal and opposite

			this.a.add(a1);
			mother.a.add(a2);
		}
	}

	// because MassiveParticles have mass, we can apply a force to them and
	// alter the acceleration, according to F = ma
	// for this reason, a massless class with wave-like properties could have
	// its momentum altered by an increase in energy
	// which could be a reason to make a momentum-related interface
	protected void applyForce(Vec3D force) {
		Vec3D acc = Vec3D.mult(force, 1 / this.m);
		a.add(acc);
	}

}
