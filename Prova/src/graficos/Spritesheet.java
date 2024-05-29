package graficos;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Spritesheet {
	
	private BufferedImage spritsheet;

	public Spritesheet() {
	}

	public BufferedImage getSprit(int x, int y, int width, int height, String path) {
		try {
			spritsheet = ImageIO.read(getClass().getResource(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return spritsheet.getSubimage(x, y, width, height);
	}
}
