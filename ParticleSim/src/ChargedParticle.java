public class ChargedParticle extends MassiveParticle {

	private double q;

	private static final double COLOUMBS_CONSTANT = 8.9785517873681764E9;

	public ChargedParticle(Vec3D x, double mass, double charge) {
		super(x, mass);
		q = charge;
	}
	public ChargedParticle(Vec3D x, Vec3D v, double mass, double charge) {
		super(x, v, mass);
		q = charge;
	}

	public void interact(Particle other) {
		// apply gravity forces
		// this calculates the distance, direction, etc twice, which isn't
		// the greatest thing
		super.interact(other);

		// derpy non-polymorphic solution
		if (other instanceof ChargedParticle) {

			ChargedParticle cother = (ChargedParticle) other;

			Vec3D r = this.displacement(other);
			// F = u * q1 * q2 / r^2, in the direction of r
			// this is equivilant to
			// F = u * q1 * q2 / |r|^3
			Vec3D f = Vec3D.mult(r, COLOUMBS_CONSTANT * this.q * cother.q
					/ (Math.pow(r.mag(), 3)));
			
//			// experiment: let be Gaussian
//			// F = k e^-(x^2)
//			double c = 1000;
//			Vec3D f = Vec3D.mult(r, COLOUMBS_CONSTANT * this.q * cother.q * Math.exp(-(r.mag()*r.mag())/c));

			// calculate the acceleration on each object
			this.applyForce(f);
			f.invert(); // newton's third law: forces are equal and opposite
			cother.applyForce(f);
		}
	}

}
