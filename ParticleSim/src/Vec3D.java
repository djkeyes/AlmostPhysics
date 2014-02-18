/**
 * A class representing a 3D vector. It contains 3 double-precision attributes
 * to represent 3 orthogonal dimensions.
 * 
 * This is specifically NOT immutable; any instance operator functions WILL
 * MODIFY THE STATE of an object. To perform an operation on two objects without
 * modifying either, you should either make a copy or use the class methods.
 * 
 * @author daniel
 * 
 */
public class Vec3D {

	public double v1, v2, v3;

	public double magsq() {
		return v1 * v1 + v2 * v2 + v3 * v3;
	}

	public double mag() {
		return Math.sqrt(magsq());
	}

	public Vec3D(double v1, double v2, double v3) {
		this.v1 = v1;
		this.v2 = v2;
		this.v3 = v3;
	}

	public Vec3D() {
		this(0, 0, 0);
	}

	public Vec3D(Vec3D other) {
		this(other.v1, other.v2, other.v3);
	}

	public void mult(double scalar) {
		this.v1 *= scalar;
		this.v2 *= scalar;
		this.v3 *= scalar;
	}

	public void add(Vec3D other){
		this.v1 += other.v1;
		this.v2 += other.v2;
		this.v3 += other.v3;
	}
	public void subtract(Vec3D other) {
		this.v1 -= other.v1;
		this.v2 -= other.v2;
		this.v3 -= other.v3;
	}

	public void clear() {
		v1 = v2 = v3 = 0;
	}

	public void invert() {
		this.v1 *= -1;
		this.v2 *= -1;
		this.v3 *= -1;
	}
	
	public String toString(){
		return "[" + v1 + ", " + v2 + ", " + v3 + "]";
	}

	// class functions
	public static Vec3D mult(Vec3D a, double d) {
		Vec3D copy = new Vec3D(a);
		copy.mult(d);
		return copy;
	}

	public static Vec3D add(Vec3D ... additors) {
		Vec3D sum = new Vec3D();
		for(Vec3D v : additors){
			sum.add(v);
		}
		return sum;
	}
	
	public static Vec3D subtract(Vec3D a, Vec3D b){
		Vec3D copy = new Vec3D(a);
		copy.subtract(b);
		return copy;
	}

}
