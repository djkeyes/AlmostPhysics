import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;


public class ParticleMouseInput implements MouseListener, KeyListener{
	
	private Point lastMouseDown;

	private List<Particle> particles;
	
	public ParticleMouseInput(List<Particle> particles){
		this.particles = particles;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_SPACE){
			// toggle static / nonstatic
		}
	}
	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}
	@Override
	public void keyReleased(KeyEvent e) {
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		lastMouseDown = arg0.getPoint();
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// if the user clicked and dragged, give the point and initial velocity
		Point mouseUp = arg0.getPoint();
		double k = 1; // scalar to make input more user-friendly
		Vec3D v = new Vec3D(lastMouseDown.x - mouseUp.x, lastMouseDown.y - mouseUp.y, 0);
		v.mult(k);
		Particle p = new Particle(new Vec3D(arg0.getX(), arg0.getY(), 0), 5, 0.01, v);
		if(arg0.isAltDown()){
			p.setStationary(true);
		}
		if(arg0.isControlDown()){
			p.setDamped(true);
		}
		particles.add(p);
	}

}
