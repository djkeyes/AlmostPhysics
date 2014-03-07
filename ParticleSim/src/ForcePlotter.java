import particles.Force;
import particles.Particle;
import plotting.RealtimePlot;
import util.Vec3D;

public class ForcePlotter {

	public static void main(String[] args) {
		RealtimePlot forceVsDistance = new RealtimePlot("Force at varying distances", "Distance between particles", "Force Magnitude");
		String key = "Force";
		Force f = Force.C_4TH_INVERSE_M_SQUARE_INVERSE;
		forceVsDistance.addDataSeries(key);
		for (double i = 0.5; i < 200; i += 0.1) {
			Particle a = new Particle(new Vec3D(0, 0, 0), 5, 0.01);
			Particle b = new Particle(new Vec3D(i, 0, 0), 5, 0.01);
			double mag = f.calcForce(b, a).v1; // this should only be in x direction
			forceVsDistance.addData(key, i, mag);
		}
		forceVsDistance.setVisible(true);
		
		
		RealtimePlot potentialEnergyVsDistance = new RealtimePlot("Potential Energy at varying distances", "Distance between particles", "Potential Energy");
		String keyPE = "PE";
		potentialEnergyVsDistance.addDataSeries(keyPE);
		for (double i = 0.5; i < 200; i += 0.1) {
			Particle a = new Particle(new Vec3D(0, 0, 0), 5, 0.01);
			Particle b = new Particle(new Vec3D(i, 0, 0), 5, 0.01);
			double pe = f.calcPotential(b, a); // this should only be in x direction
			potentialEnergyVsDistance.addData(keyPE, i, pe);
		}
		potentialEnergyVsDistance.setVisible(true);
	}
}
