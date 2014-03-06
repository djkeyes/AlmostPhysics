import particles.Force;
import particles.Particle;
import plotting.RealtimePlot;
import util.Vec3D;

public class ForcePlotter {

	public static void main(String[] args) {
		RealtimePlot forceVsDistance = new RealtimePlot("Force at varying distances", "Distance between particles", "Force Magnitude");
		String key = "Force";
		forceVsDistance.addDataSeries(key);
		for (double i = 0.5; i < 200; i += 0.1) {
			Particle a = new Particle(new Vec3D(0, 0, 0), 5, 0.01);
			Particle b = new Particle(new Vec3D(i, 0, 0), 5, 0.01);
			Force f = Force.M_SQUARE_INVERSE;
			double mag = f.calcForce(b, a).v1; // this should only be in x direction
			forceVsDistance.addData(key, i, mag);
		}
		forceVsDistance.setVisible(true);
	}
}
