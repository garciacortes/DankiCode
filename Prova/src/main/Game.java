package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import entities.Entity;
import entities.Player;
import graficos.Spritesheet;
import world.World;

public class Game extends Canvas implements Runnable, KeyListener {
	
	private static final long serialVersionUID = 1L;
	
	public static JFrame frame;
	private Thread thread;
	private boolean isRunning = true;
	public static final int WIDTH = 640;
	public static final int HEIGHT = 345;
	public final int SCALE = 2;
	
	private BufferedImage image;
	
	public List<Entity> entities;
	public static Spritesheet spritesheet;
	
	public static World world;
	
	public static Player player;
	
	public Game() {
		addKeyListener(this);
		setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		initFrame();
		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		entities = new ArrayList<Entity>();
		spritesheet = new Spritesheet();
		player = new Player(0, 0, 0, 0, 2.8, Entity.PLAYER_SPRITE);
		entities.add(player);
		world = new World("/Map.png");
	}
	
	public void initFrame() {
		frame = new JFrame("GAME ENGINE");
		frame.add(this);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	public synchronized void start() {
		thread = new Thread(this);
		isRunning = true;
		thread.start();
	}
	
	public synchronized void stop() {
		isRunning = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		
	}

	public static void main(String[] args) {
		Game game  = new Game();
		game.start();
	}
	
	public void tick() {
		for(int  i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.tick();
		}
	}
	
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = image.getGraphics();
		g.setColor(new Color(0,0,255));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		world.render(g);
		for(int  i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			e.render(g);
		}
		g.dispose();
		g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
		
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		final double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int frames = 0;
		double timer = System.currentTimeMillis();
		while(isRunning) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if(delta >= 1) {
				tick();
				render();
				frames++;
				delta--;
			}
			if(System.currentTimeMillis() - timer >= 1000) {
				//System.out.println("FPS: " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
		
		stop();
	}


	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = true;
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = true;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
			player.jump = true;
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_D) {
			player.right = false;
		} else if(e.getKeyCode() == KeyEvent.VK_A) {
			player.left = false;
		}
	}
	
	public void keyTyped(KeyEvent e) {
	}

}
