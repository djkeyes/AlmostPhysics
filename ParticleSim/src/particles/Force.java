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
		public Vec3D calcForce(Particle first, Particle second) {
			Vec3D r = Vec3D.subtract(first.x, second.x);
			double g = 100000;
			double c = 5000000;
			Vec3D f = Vec3D.add(Vec3D.mult(r, -g * first.m * second.m / (Math.pow(r.mag(), 3))),
					Vec3D.mult(r, c*first.q * second.q / (Math.pow(r.mag(), 4))));
			return f;
		}
	}), M_SQUARE_INVERSE(new ForceFunction() {
		public Vec3D calcForce(Particle first, Particle second) {
			// only gravity
			Vec3D r = Vec3D.subtract(first.x, second.x);
			double g = 100000;
			Vec3D f = Vec3D.mult(r, -g * first.m * second.m / (Math.pow(r.mag(), 3)));
			return f;
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
		if(ENABLE_MAX_FORCE && force.mag() > MAX_FORCE){
			force.mult(MAX_FORCE/force.mag());
		}
		return force;
	}

	private interface ForceFunction {
		public Vec3D calcForce(Particle first, Particle second);
	}

}
