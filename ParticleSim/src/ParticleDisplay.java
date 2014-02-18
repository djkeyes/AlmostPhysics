import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.util.Iterator;
import java.util.List;

/**
 * Displays particles on a 2D screen. Uses an orthogonal projection, so the
 * z-component of position is discarded when displaying the particle. Any new
 * particles added are
 * 
 * @author daniel
 * 
 */
@SuppressWarnings("serial")
public class ParticleDisplay extends Canvas implements IParticleDisplay {

	private boolean drawPaths = true;
	
	public ParticleDisplay() {
		// this.bufferStrategy = bufferStrategy;
		this.setBackground(Color.WHITE);
	}

	@Override
	public void redraw(List<Particle> particles) {
		final BufferStrategy bufferStrategy = this.getBufferStrategy();

		Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();// bufferStrategy.getDrawGraphics();
		g.setColor(Color.white);
		// Graphics2D g = (Graphics2D)this.getGraphics();

		for (Particle p : particles) {
			// consider adding a draw() method to Particle
			// or, to maintain single-responsibility, maintain old positions
			// locally, instead of in the particle class
			int x = (int) Math.round(p.getXOld().getLast().v1);
			int y = (int) Math.round(p.getXOld().getLast().v2);
			int r = 4;
			if (this.getBounds().contains(x, y)) {
				g.setColor(Color.WHITE);
				g.drawOval(x - r, y - r, 2 * r - 1, 2 * r - 1);
			}

			if(drawPaths){
			g.setColor(Color.BLACK);
			Vec3D next = null;
			// only draw the last few
			Iterator<Vec3D> it = p.getXOld().descendingIterator();
			for (int i = 0; i < 100; i++) {
				if (it.hasNext()) {
					Vec3D oldPos = it.next();
					if (next != null)
						g.drawLine((int) next.v1, (int) next.v2,
								(int) oldPos.v1, (int) oldPos.v2);
					next = oldPos;
				}
			}
			}

			x = (int) Math.round(p.getX());
			y = (int) Math.round(p.getY());
			if (this.getBounds().contains(x, y)) {
				g.setColor(Color.WHITE);
				g.fillOval(x - r, y - r, 2 * r - 1, 2 * r - 1);
				g.setColor(Color.RED);
				g.drawOval(x - r, y - r, 2 * r - 1, 2 * r - 1);
				// System.out.println(x + ", " + y);
			}
		}
		g.dispose();
		bufferStrategy.show();
	}
}
