import java.util.LinkedList;
import java.util.List;

public class Particle {

	// constants

	// the minimum distance to treat two particles
	// this gives a finite cap the the acceleration of two particles
	private final double MIN_DISTANCE = 0.0001; // Double.MIN_VALUE;
	private final double DAMPING_COEFFCIENT = 0.1;

	// instance variables
	private boolean isDamped = false;
	private boolean isStatic = false;

	// position, velocity, acceleration
	protected Vec3D x, v, a;
	private LinkedList<Vec3D> xOld;

	// constructors
	public Particle(Vec3D x) {
		this(x, new Vec3D());
	}

	public Particle(Vec3D x, Vec3D v) {
		this.x = x;
		this.xOld = new LinkedList<Vec3D>();
		xOld.add(x);
		this.v = v;
		this.a = new Vec3D();
	}

	// public member functions
	public void update(double stepSize) {
		if (!isStatic) {
			// xOld = new Vec3D(x);
			xOld.add(x);

			x = Vec3D.add(x, Vec3D.mult(v, stepSize),
					Vec3D.mult(a, stepSize * stepSize));
		}

		if (isDamped) {
			// include damping: F = f - kv, the force is reduced by an amount
			// proportional to the velocity
			// so a = f/m - kv/m
			// the damping constant is proportional to the mass of the particle
			// so this calculation should occur in MassiveParticle
			// (but it doesn't really matter right now, because they're all
			// equal mass)
			a.subtract(Vec3D.mult(v, DAMPING_COEFFCIENT));
		}
		v.add(Vec3D.mult(a, stepSize));

		a.clear();
	}

	public double getX() {
		return x.v1;
	}

	public double getY() {
		return x.v2;
	}

	public double getZ() {
		return x.v3;
	}

	// public double getXOld(){
	// return xOld.v1;
	// }
	// public double getYOld(){
	// return xOld.v2;
	// }
	// public double getZOld(){
	// return xOld.v3;
	// }
	public LinkedList<Vec3D> getXOld() {
		return xOld;
	}

	// @Override
	public void interact(Particle other) {
		// do nothing

	}

	public void setStationary(boolean isStatic) {
		this.isStatic = isStatic;
	}

	public void setDamped(boolean isDamped) {
		this.isDamped = isDamped;
	}

	// helper methods
	protected Vec3D displacement(Particle other) {
		return Vec3D.subtract(this.x, other.x);
	}
}
