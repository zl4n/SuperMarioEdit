package com.gcstudios.entities;

import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class Coin extends Entity {
	
	public boolean right = true, left = false;

	public Coin(double x, double y, int width, int height, double speed, BufferedImage sprite) {
		super(x, y, width, height, speed, sprite);
	}
	
	
	
	public void render(Graphics g) {
		if(right)
			sprite = Entity.Coin_Right;
		else if(left)
			sprite = Entity.Coin_Left;
		super.render(g);
	}
	

}
