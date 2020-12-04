package Life;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class Mover extends JPanel{
	
	double radius;
	double mass;
	public Vector position;
	public Vector velocity;
	Vector acceleration;
	
	
	public Mover(double x, double y, double mass) {
		this.mass = mass;
		this.radius = this.mass * 16;
		this.position = new Vector(x, y);
		this.velocity = new Vector();
		this.acceleration = new Vector((int) (Math.random() * 10 - 5), (int) (Math.random() * 10 - 5));
	}
	
	public void applyForce(Vector force) {
		Vector f = force.divide(this.mass);
		this.acceleration.addTo(f);
	}
	
	public void update() {
		this.velocity.addTo(this.acceleration);
		this.position.addTo(this.velocity);
		this.acceleration.multiplyBy(0);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
//		g2d.setStroke(new BasicStroke(3));
		g2d.fillOval((int) this.position.x, (int) this.position.y, (int) this.radius, (int) this.radius);
		
	}
	
	public Vector attract(Mover other) {
		double G = 1;
		
		Vector force = this.position.subtract(other.position);
		double distance = force.getMagnitude();
		if(distance < 5) distance = 5;
		if(distance > 25) distance = 25;
		double strength = (G * this.mass * other.mass) / (distance * distance);
		force.setMagnitude(strength);
		return force;
	}
}
