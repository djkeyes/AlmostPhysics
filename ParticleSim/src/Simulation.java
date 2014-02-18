import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;


public class Simulation {
	
	public static void main(String[] args){
		ArrayList<Particle> particles = new ArrayList<Particle>();

//		particles.add(new ChargedParticle(new Vec3D(100,100,0), 5, 0.01));
//		particles.add(new ChargedParticle(new Vec3D(300,280,0), 50000000000000000d, 0.15));
//		particles.add(new ChargedParticle(new Vec3D(100, 400, 0), 5, 0.01));
		
//		particles.add(new ChargedParticle(new Vec3D(100,100,0), 5, 0.01));
//		particles.add(new ChargedParticle(new Vec3D(300,280,0), 50000000000000000d, 0.15));

		particles.add(new ChargedParticle(new Vec3D(180,120,0), 5, 0.01));
		particles.add(new ChargedParticle(new Vec3D(300,140,0), 5, 0.01));
		particles.add(new ChargedParticle(new Vec3D(240,300,0), 50000000000000000d, 0.15));

//		particles.add(new ChargedParticle(new Vec3D(300,360,0), 50000, 0.15));
//		particles.add(new ChargedParticle(new Vec3D(180,240,0), 50000, 0.15));
//		particles.add(new ChargedParticle(new Vec3D(300,240,0), 50000, 0.15));
//		particles.add(new ChargedParticle(new Vec3D(180,360,0), 50000, 0.15));
//		particles.add(new ChargedParticle(new Vec3D(240,300,0), 5000000000000000d, 11));
		

		int vo = 110;
//		particles.add(new ChargedParticle(new Vec3D(280,286,0), new Vec3D(-vo,0,0), 5000000000000000d, 5));
//		particles.add(new ChargedParticle(new Vec3D(280,274,0), new Vec3D(vo,0,0), 5000000000000000d, -5));
//		particles.add(new ChargedParticle(new Vec3D(680,288,0), new Vec3D(-vo,0,0), 5000000000000000d, -5));
//		particles.add(new ChargedParticle(new Vec3D(680,272,0), new Vec3D(vo,0,0), 5000000000000000d, 5));
//		particles.add(new ChargedParticle(new Vec3D(480,288,0), new Vec3D(-vo,0,0), 5000000000000000d, 5));
//		particles.add(new ChargedParticle(new Vec3D(480,272,0), new Vec3D(vo,0,0), 5000000000000000d, -5));
		
		JFrame frame = new JFrame();

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(480, 640));
		frame.setIgnoreRepaint(true);

		int refreshRate = 10; // redraw every 10 milliseconds
		ParticleDisplay displayPanel = new ParticleDisplay();
		Updater updater = new Updater(refreshRate, particles, displayPanel);

		ParticleMouseInput input = new ParticleMouseInput(particles);
		displayPanel.addMouseListener(input);
		displayPanel.addKeyListener(input);
		frame.getContentPane().add(displayPanel);
		frame.pack();
		frame.setVisible(true);
		
		displayPanel.createBufferStrategy(2);
		
		updater.start();
	}
	


	private class MouseInput extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			
		}
	}
}
