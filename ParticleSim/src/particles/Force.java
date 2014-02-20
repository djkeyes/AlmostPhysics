package particles;

public enum Force {
	// the naming scheme for forces should be along the lines of:
	// C_<name of charged force function>_M_<name of mass force function>
	C_GAUSSIAN_M_SQUARE_INVERSE(new ForceFunction(){
		public Vec3D calcForce(Particle first, Particle second){
			Vec3D r = Vec3D.subtract(first.)
			Vec3D f = Vec3D.add(Vec3D.mult(r, -G * first.m * second.m / (Math.pow(r.mag(), 3))),
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
	}),
	C_4TH_INVERSE_M_SQUARE_INVERSE(new ForceFunction(){
		public Vec3D calcForce(Particle first, Particle second){
			return new Vec3D(1,2,3);
		}
	});

	
	private final ForceFunction myFunction;
	
	private Force(final ForceFunction func){
		myFunction = func;
	}
	public Vec3D calcForce(Particle first, Particle second){
		return myFunction.calcForce(first, second);
	}
	
	private interface ForceFunction {
		public Vec3D calcForce(Particle first, Particle second);
	}
	
}
