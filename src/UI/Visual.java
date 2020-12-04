package UI;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import Life.Camera;
import Life.Mover;
import Life.Vector;

public class Visual extends JPanel implements KeyListener{ // keylistener is jsut temporary
	
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Timer t;
	private Camera camera;
	static int numOfMovers = 100;
	static Mover[] movers = new Mover[numOfMovers];
	public static int size = 5000;
	
	private int currentMover = 0;
	
	ActionListener gameLoop = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			tick();
			render();
			
		}
		
	};
	
	public void tick() {
		camera.tick(movers[currentMover].position.x, movers[currentMover].position.y);
		
		for(Mover m : movers) {
			for(Mover mm : movers) {
				if(m != mm) {
					Vector force = mm.attract(m);
					m.applyForce(force);
				}
			}
			m.update();
		}
	}
	
	public void render() {
		repaint();
	}
	
 	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		// g2d.translate(-camera.getX(), -camera.getY());
		
		int tileWidth = 64;
		// System.out.println(camera.getX());
		int xStart = (int) (camera.getX() / tileWidth);
		int xEnd = (int) xStart + (int) (camera.getHeight() / tileWidth);
		int yStart = (int) (camera.getY() / tileWidth);
		int yEnd = yStart + (int) (camera.getWidth() / tileWidth);
		
		int offsetX = (int)(-camera.getX() + xStart * tileWidth);
		int offsetY = (int)(-camera.getY() + yStart * tileWidth);
		
		for(int c = yStart; c < yEnd; c++) {
			for(int r = xStart; r < xEnd; r++) {
				
				int x = (c - yStart) * tileWidth + offsetX;
				int y = (r - xStart) * tileWidth + offsetY;
				
				g2d.drawRect(x, y, tileWidth, tileWidth);
			}
		}
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		for(Mover m : movers) {
			m.paintComponent(g);
		}
		
		g2d.translate(camera.getX(), camera.getY());
	}
 	

	
	
	public Visual() {
		addKeyListener(this);
		this.setFocusable(true);
        this.requestFocusInWindow();
        
		camera = new Camera();
		
		// center rect
		int width = 500;
		int height = 500;
		
		int maxWidth = (int) screenSize.getWidth()/2 + width / 2;
		int minWidth = (int) screenSize.getWidth()/2 - width /2;
		
		int maxHeight = (int) screenSize.getHeight()/2 + height /2;
		int minHeight = (int) screenSize.getHeight()/2 - height /2;
		
		double minMass = .2;
		double maxMass = 5;
		
		
		


	
		movers[0] = new Mover(500, 500, 2);
		movers[1] = new Mover(0, 500, 20);
		for(int i = 2; i < 100; i++) {
			Random rand = new Random();
			double d1 = rand.nextInt(maxWidth - minWidth + 1) + minWidth;
			double d2 = rand.nextInt(maxHeight - minHeight + 1) + minHeight;
			double d3 = rand.nextDouble() * (maxMass - minMass + 1) + minMass;
			movers[i] = new Mover(d1, d2, d3);
			
			
		}
		
		t = new Timer(17, gameLoop);
		t.start();
		
	
	}
	
	
	
	public static void createAndShowGui() {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new Visual());
		frame.setLocationRelativeTo(null);
		frame.setBounds(0, 0, (int) screenSize.getWidth(), (int) screenSize.getHeight());
		frame.setPreferredSize(new Dimension((int) screenSize.getWidth(), (int) screenSize.getHeight()));
		frame.pack();
		
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGui();
            }
        });

	}
	
	public void incrementMover() {
		if(currentMover + 1 >= numOfMovers) {
			this.currentMover = 0;
		} else {
			this.currentMover += 1;
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char key = e.getKeyChar();
		if(key == 'a') {
			movers[0].applyForce(new Vector(-1,0));
		} 
		if(key == 'd') {
			movers[0].applyForce(new Vector(1, 0));
		}
		
		if(key == 'w') {
			movers[0].applyForce(new Vector(0, -1));
		}
		if(key == 's') {
			movers[0].applyForce(new Vector(0, 1));
		}
		
		if(key == 'p') {
			for(int i = 0; i < movers.length; i++) {
				movers[i].velocity.x = 0;
				movers[i].velocity.y = 0;
			}
			
		}
		
		if(key == 'o') {
			incrementMover();
			
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
