package particles;

import util.Vec3D;

public enum Force {
	// the naming scheme for forces should be along the lines of:
	// C_<name of charged force function>_M_<name of mass force function>
	C_GAUSSIAN_M_SQUARE_INVERSE(new ForceFunction() {
		public Vec3D calcForce(Particle first, Particle second) {
			double c = 1000;
			Vec3D r = Vec3D.subtract(first.x, second.x);
			Vec3D f = Vec3D.add(Vec3D.mult(r, -first.m * second.m / (Math.pow(r.mag(), 3))),
					Vec3D.mult(r, first.q * second.q * Math.exp(-(r.mag() * r.mag()) / c)));

			return f;
		}
	}), C_4TH_INVERSE_M_SQUARE_INVERSE(new ForceFunction() {
		public Vec3D calcForce(Particle first, Particle second) {
			Vec3D r = Vec3D.subtract(first.x, second.x);
			Vec3D f = Vec3D.add(Vec3D.mult(r, -first.m * second.m / (Math.pow(r.mag(), 3))),
					Vec3D.mult(r, first.q * second.q / (Math.pow(r.mag(), 4))));
			return f;
		}
	});

	// the minimum distance to treat two particles
	// this gives a finite cap the the acceleration of two particles
	private final double MIN_DISTANCE = 0.0001; // Double.MIN_VALUE;

	private final ForceFunction myFunction;

	private Force(final ForceFunction func) {
		myFunction = func;
	}

	public Vec3D calcForce(Particle first, Particle second) {
		return myFunction.calcForce(first, second);
	}

	private interface ForceFunction {
		public Vec3D calcForce(Particle first, Particle second);
	}

}
