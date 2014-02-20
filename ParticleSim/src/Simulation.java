import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;

import particles.Particle;
import util.Vec3D;


public class Simulation {
	
	public static void main(String[] args){
		ArrayList<Particle> particles = new ArrayList<Particle>();

		particles.add(new Particle(new Vec3D(180,120,0), 5000d, 5));
//		particles.add(new Particle(new Vec3D(300,140,0), 5, 5));
//		particles.add(new Particle(new Vec3D(240,300,0), 50000d, 0.15));

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
