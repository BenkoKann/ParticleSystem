package Life;

import UI.Visual;

public class Camera {
	private double x, y;
	private double maxX, maxY; // minx and miny are 0,0
	
	public Camera() {
		this(0, 0);
	}
	
	public Camera(double x, double y) {
		this.x = x;
		this.y = y;
		this.maxX = Visual.size - getWidth();
		this.maxY = Visual.size - getHeight();
	}
	
	public void tick(double centerX, double centerY) { // the desired final position
		this.x += ((centerX - x) - Visual.screenSize.getWidth()/2) * .1; // * 0.05;
		this.y += ((centerY - y) - Visual.screenSize.getHeight()/2) * .1; // * 0.05;
	}

	
	
	public double getWidth() {
		return Visual.screenSize.getWidth();
	}
	
	public double getHeight() {
		return Visual.screenSize.getHeight();
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getMaxX() {
		return maxX;
	}

	public double getMaxY() {
		return maxY;
	}


	

}
