package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;

public class Tile {

	public static BufferedImage TILE_FLOOR = Game.spritesheet.getSprit(0, 0, 128, 128, "/GrassTop.png");
	public static BufferedImage BACKGROUND_TILE = Game.spritesheet.getSprit(128, 0, 128, 128, "/GrassTop.png");

	private BufferedImage sprite;
	private int x,y;
	
	public Tile(int x, int y, BufferedImage sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void render(Graphics g) {
		g.drawImage(sprite, x - Camera.x, y - Camera.y, null);
	}
}
