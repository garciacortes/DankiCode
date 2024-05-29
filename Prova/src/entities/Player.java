package entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import main.Game;
import world.Camera;
import world.World;

public class Player extends Entity{
	
	public boolean right,left;
	public int right_dir = 0, left_dir = 1;
	public int dir = right_dir;
	public double gravity = 6;
	
	public boolean jump = false, isJumping = false;
	public int jumpHeight = 260;
	public int jumpFrames = 0; 
	
	private int frames = 0, maxFrames = 50, indexIdle = 0, maxIndexIdle = 12;
	private int indexRun = 0, maxIndexRun = 7;
	private boolean moved = false;
	private BufferedImage[] RunRightPlayer, RunLeftPlayer, idlePlayer;

	public Player(int x, int y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
		
		RunRightPlayer = new BufferedImage[8];
		RunLeftPlayer = new BufferedImage[8];
		idlePlayer = new BufferedImage[13];
		for(int i = 0; i < 8; i++) {
			RunRightPlayer[i] = Game.spritesheet.getSprit(0 + (i*128), 0, 128, 128, "/PlayerRunRight.png");
			RunLeftPlayer[i] = Game.spritesheet.getSprit(0 + (i*128), 0, 128, 128, "/PlayerRunLeft.png");
		}
		for(int i = 0; i < 13; i++) {
			idlePlayer[i] = Game.spritesheet.getSprit(0 + (i*128), 0, 128, 128, "/PlayerIdle.png");
		}
	}
	
	public void tick() {
		moved = false;
		if(!isJumping && World.isFree(this.getX(),(int)(y+gravity))) {
			y += gravity;
		}
		
		if(right && World.isFree((int)(x+speed), this.getY())) {
			if(dir == left_dir) {
				indexRun = 0;
				frames = 0;
			}
			moved = true;
			indexIdle = 0;
			dir = right_dir;
			x += speed;
		} else if(left && World.isFree((int)(x-speed), this.getY())) {
			if(dir == right_dir) {
				indexRun = 0;
				frames = 0;
			}
			moved = true;
			indexIdle = 0;
			dir = left_dir;
			x -= speed;
		}
		if(jump && !World.isFree(this.getX(), this.getY()+1)) {
			isJumping = true;
		} else {
			jump = false;
		}
		
		if(isJumping) {
			if(World.isFree(this.getX(), this.getY()-2)) {
				y -= 2;
				jumpFrames += 2;
				if(jumpFrames == jumpHeight) {
					isJumping = false;
					jump = false;
					jumpFrames = 0;
				}
			} else {
				isJumping = false;
				jump = false;
				jumpFrames = 0;
			}
		}
		
		
		
		Camera.x = Camera.clamp(this.getX() - (Game.WIDTH/2), 0, World.WIDTH*128 - Game.WIDTH);
		Camera.y = Camera.clamp(this.getY() - (Game.HEIGHT/2), 0, World.HEIGHT*128 - Game.HEIGHT);
	}
	
	public void render(Graphics g) {
		frames++;
		if(frames == maxFrames) {
			frames = 0;
			if(moved) {
				indexRun++;
				if(indexRun > maxIndexRun) {
					indexRun = 0;	
				}
			} else {
				indexIdle++;
				if(indexIdle > maxIndexIdle) {
					indexIdle = 0;
				}
			}
		}
			
		if(dir == right_dir) {
			if(moved) {
				g.drawImage(RunRightPlayer[indexRun], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else {
				g.drawImage(idlePlayer[indexIdle], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		} else if(dir == left_dir) {
			if(moved) {
				g.drawImage(RunLeftPlayer[indexRun], this.getX() - Camera.x, this.getY() - Camera.y, null);
			} else {
				g.drawImage(idlePlayer[indexIdle], this.getX() - Camera.x, this.getY() - Camera.y, null);
			}
		}
	}

}