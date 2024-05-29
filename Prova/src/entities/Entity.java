package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import world.Camera;

public class Entity {
	
	protected double x, y, speed;
	protected int width, height;
	
	private BufferedImage sprite;
	
	public static BufferedImage PLAYER_SPRITE = Game.spritesheet.getSprit(0, 0, 128, 128, "/PlayerIdle.png");

	public Entity(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.sprite = sprite;
		this.speed = speed;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, this.getX() - Camera.x, this.getY() - Camera.y, null);
	}
	
	public void tick() {
		
	}

	public int getX() {
		return (int)this.x;
	}

	public int getY() {
		return (int)this.y;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}
}
