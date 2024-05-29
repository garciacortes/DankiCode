package world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Game;

public class World {
	
	public static Tile[] tiles;
	public static int WIDTH, HEIGHT;
	public static final int TILE_SIZE = 128;
	
	public World(String path) {
		try {
			BufferedImage map = ImageIO.read(getClass().getResource(path));
			WIDTH = map.getWidth();
			HEIGHT = map.getHeight();
			int[] pixels = new int[WIDTH*HEIGHT];
			tiles = new Tile[WIDTH*HEIGHT];
			map.getRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);
			for(int xx = 0; xx < WIDTH; xx++) {
				for(int yy = 0; yy < HEIGHT; yy++) {
					int posAtual = xx + (yy * WIDTH);
					int pixelAtual = pixels[posAtual];
					tiles[posAtual] = new BackgroundTile(xx*128, yy*128, Tile.BACKGROUND_TILE);
					switch (pixelAtual) {
					case 0xff3dd413:
						tiles[posAtual] = new FloorTile(xx*128, yy*128, Tile.TILE_FLOOR);
						break;
					case 0xffffffff:
						Game.player.setX(xx*128);
						Game.player.setY(yy*128);
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean isFree(int xnext, int ynext) {
		int x1 = xnext / TILE_SIZE;
		int y1 = ynext / TILE_SIZE;
		
		int x2 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y2 = ynext / TILE_SIZE;
		
		int x3 = xnext / TILE_SIZE;
		int y3 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		int x4 = (xnext+TILE_SIZE-1) / TILE_SIZE;
		int y4 = (ynext+TILE_SIZE-1) / TILE_SIZE;
		
		return !(tiles[x1 + (y1 * World.WIDTH)] instanceof FloorTile ||
				tiles[x2 + (y2 * World.WIDTH)] instanceof FloorTile ||
				tiles[x3 + (y3 * World.WIDTH)] instanceof FloorTile ||
				tiles[x4 + (y4 * World.WIDTH)] instanceof FloorTile);
	}
	
	public void render(Graphics g) {
		int xstart = Camera.x >> 7;
		int ystart = Camera.y >> 7;
		
		int xfinal = xstart + (Game.WIDTH >> 7);
		int yfinal = ystart + (Game.HEIGHT >> 7) + 1;
		
		for(int xx = xstart; xx <= xfinal; xx++) {
			for(int yy = ystart; yy <= yfinal; yy++) {
				if(xx < 0 || yy < 0 || xx >= WIDTH || yy >= HEIGHT) {
					continue;
				}
				Tile tile = tiles[xx + 	(yy * WIDTH)];
				tile.render(g);
			}
		}
	}
}
