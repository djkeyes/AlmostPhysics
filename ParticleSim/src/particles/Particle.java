package particles;

import java.util.LinkedList;
import java.util.List;

import util.Vec3D;

public class Particle {

	// instance variables
	private boolean isDamped = false;
	private boolean isStatic = false;

	private final double DAMPING_COEFFCIENT = 0.1;

	// position, velocity, acceleration
	protected Vec3D x, v, a; // package+protected access
	private LinkedList<Vec3D> xOld;
	// TODO: shit crashes if this is false
	private static final boolean STORE_OLD_POSITIONS = true;
	private static final int MAX_XOLD_TO_KEEP = 100;

	// mass
	protected double m; // package+protected
	protected double q; // package+protected

	// potential energy
	protected double pe; // package+protected
	private double lastPotentialEnergy;

	// constructors
	public Particle(Vec3D x, double mass) {
		this(x, mass, new Vec3D());
	}

	// constructors
	public Particle(Vec3D x, double mass, double charge) {
		this(x, mass, charge, new Vec3D());
	}

	// constructors
	public Particle(Vec3D x, double mass, Vec3D v) {
		this(x, mass, 0, v);
	}

	public Particle(Vec3D x, double mass, double charge, Vec3D v) {
		this.x = x;
		this.v = v;
		this.a = new Vec3D();
		this.m = mass;
		this.q = charge;

		if (STORE_OLD_POSITIONS) {
			this.xOld = new LinkedList<Vec3D>();
			xOld.add(x);
		}
		
		pe = 0;
	}

	// public member functions
	public void update(double stepSize) {
		if (!isStatic) {
			if (STORE_OLD_POSITIONS) {
				xOld.addLast(x);
				if (xOld.size() > MAX_XOLD_TO_KEEP) {
					xOld.removeFirst();
				}
			}

			x = Vec3D.add(x, Vec3D.mult(v, stepSize), Vec3D.mult(a, stepSize * stepSize));
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
		
		lastPotentialEnergy = pe;
		pe = 0;
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

	public Vec3D getMomentum() {
		return Vec3D.mult(v, m);
	}

	public double getEnergy() {
		// sum of potential and kinetic
		return getKineticEnergy() + getPotentialEnergy();
	}

	public double getKineticEnergy() {
		return m * v.magsq();
	}

	public double getPotentialEnergy(){
		return lastPotentialEnergy;
	}
}
