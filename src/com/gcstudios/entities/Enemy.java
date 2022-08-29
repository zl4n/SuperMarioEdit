package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.world.World;

public class Enemy extends Entity {
	
	public boolean right = false, left = true;
	
	public int life = 3;

	public Enemy(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	public void tick() {
		if(World.isFree((int)x,(int)(y+1))) {
			y+=1;
		}else {
		
		if(right) {
			if(World.isFree((int)(x+speed),(int)y)) {
				x+=speed;
					if(World.isFree((int)(x+16),(int)y+1)) {
							right = false;
							left  = true;
					}		
			}else {
				right = false;
				left  = true;
			}
		}
		
		if(left) {
			if(World.isFree((int)(x-speed),(int)y)) {
				x-=speed;
					if(World.isFree((int)(x-16),(int)y+1)) {
							right = true;
							left  = false;
					}		
			}else {
				right = true;
				left  = false;
			}
		}
	}
}
	public void render(Graphics g) {
		if(right)
			sprite = Entity.Enemy1_right;
		else if(left)
			sprite = Entity.Enemy1_left;
		super.render(g);
	}
	
}
