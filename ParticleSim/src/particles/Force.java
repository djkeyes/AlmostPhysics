package particles;

import util.Vec3D;

public enum Force {
	// the naming scheme for forces should be along the lines of:
	// C_<name of charged force function>_M_<name of mass force function>
	C_GAUSSIAN_M_SQUARE_INVERSE(new ForceFunction() {
		public Vec3D calcForce(Particle first, Particle second) {
			Vec3D r = Vec3D.subtract(first.x, second.x);
			Vec3D f = Vec3D.add(Vec3D.mult(r, -first.m * second.m / (Math.pow(r.mag(), 3))),
					Vec3D.mult(r, first.q * second.q * Math.exp(-(r.mag() * r.mag()))));

			return f;
		}
	}), C_4TH_INVERSE_M_SQUARE_INVERSE(new ForceFunction() {
		public final double g = 1900;
		public final double c = 16000000000d;

		public Vec3D calcForce(Particle first, Particle second) {
			Vec3D r = Vec3D.subtract(first.x, second.x);
			Vec3D f = Vec3D.add(Vec3D.mult(r, -g * first.m * second.m / (Math.pow(r.mag(), 3))),
					Vec3D.mult(r, c * first.q * second.q / (Math.pow(r.mag(), 5))));
			return f;
		}

		public double calcPotential(Particle first, Particle second) {
			Vec3D r = Vec3D.subtract(first.x, second.x);
			return -g * first.m * second.m / r.mag() + c * first.q * second.q / (3 * Math.pow(r.mag(), 3));
		}
	}), M_SQUARE_INVERSE(new ForceFunction() {
		public final double g = 1900;

		public Vec3D calcForce(Particle first, Particle second) {
			// only gravity
			Vec3D r = Vec3D.subtract(first.x, second.x);
			Vec3D f = Vec3D.mult(r, -g * first.m * second.m / (Math.pow(r.mag(), 3)));
			return f;
		}

		public double calcPotential(Particle first, Particle second) {
			Vec3D r = Vec3D.subtract(first.x, second.x);
			return -g * first.m * second.m / r.mag();
		}
	});

	// global limits to particle forces
	// TODO: what if we want to throttle cpu and do numerical integration? in that case, we'd want the exact force values
	private static final boolean ENABLE_MAX_FORCE = true;
	private static final double MAX_FORCE = 500;

	private final ForceFunction myFunction;

	private Force(final ForceFunction func) {
		myFunction = func;
	}

	public Vec3D calcForce(Particle first, Particle second) {
		Vec3D force = myFunction.calcForce(first, second);
		if (ENABLE_MAX_FORCE && force.mag() > MAX_FORCE) {
			force.mult(MAX_FORCE / force.mag());
		}
//		System.out.println(force.mag());
		return force;
	}
	
	public double calcPotential(Particle first, Particle second) {
		return myFunction.calcPotential(first, second);
	}

	private static abstract class ForceFunction {
		public abstract Vec3D calcForce(Particle first, Particle second);

		/**
		 * Calculated the potential energy stored between two particles as a result of this force. By default, this throws an
		 * UnsupportedOperationException. Many forces might be able to calculate their potential using numerical integration, but some
		 * (such as gravitational potential energy) requires different integration schemes; therefore, rather than providing a default
		 * implementation, the behavior is left for the implementor to define.
		 * 
		 * @return
		 */
		public double calcPotential(Particle first, Particle second) {
			throw new UnsupportedOperationException();
		}
	}

}
