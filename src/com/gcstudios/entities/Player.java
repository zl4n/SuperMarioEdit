package com.gcstudios.entities;



import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.gcstudios.main.Game;
import com.gcstudios.world.Camera;
import com.gcstudios.world.World;




public class Player extends Entity{
	
	private BufferedImage[] rightPlayer;
	private BufferedImage[] leftPlayer;
	
	private int frames = 0, maxFrames = 5, index = 0, maxIndex = 3;
	
	private boolean moved = false;
	
	public boolean right,up,left,down;
	
	public static double life = 100;
	
	public static int currentCoins = 0;
	
	public static int maxCoins = 0;
	
	public int right_dir = 1, left_dir= -1;
	
	public int dir = 1;
	
	private double gravity = 0.4;
	private double vspd = 0;
	
	public boolean jump = false;
	
	public boolean isJumping = false;
	public int jumpHeight = 30;
	public int jumpFrames = 0;
	
	private int framesAnimation = 0;
	private int maxFrame = 15;
	private int maxSprites = 2;
	private int curSprites = 0;
	
	public Player(int x, int y, int width, int height,double speed,BufferedImage sprite) {
		super(x, y, width, height,speed,sprite);
		
		rightPlayer   = new BufferedImage[4];
		leftPlayer    = new BufferedImage[4];
		 
				for(int i = 0; i < 4; i++) {
					
					rightPlayer[i] = Game.spritesheet.getSprite(0 + (i*16), 48, 16, 16);
				}
				
				for(int i = 0; i < 4; i++) {
					
					leftPlayer[i] = Game.spritesheet.getSprite(0 + (i*16), 64, 16, 16);
				}
				
				
		
	}
	
			public void tick(){
				
				
				depth = 2;
				vspd+=gravity;
				
				if(!World.isFree((int)x,(int)(y+1)) && jump)
				{
					vspd = -6;
					jump = false;
				}
				
				if(!World.isFree((int)x,(int)(y+vspd))) {
					
					int signVsp = 0;
					if(vspd >= 0)
					{
						signVsp = 1;
					}else  {
						signVsp = -1;
					}
					while(World.isFree((int)x,(int)(y+signVsp))) {
						y = y+signVsp;
					}
					vspd = 0;
				}
				
				y = y + vspd;
				
				
				
				if(World.isFree((int)x,(int)(y+gravity)) && isJumping == false) {
					y+=gravity;
					for(int i = 0; i < Game.entities.size(); i++) {
						Entity e = Game.entities.get(i);
							if(e instanceof Enemy) {
								if(Entity.isColidding(this, e)) {
										//Smash enemy!							
									isJumping = true;
									jumpHeight = 32;
										//Remove life enemy
									((Enemy)e).life-=3;
									if(((Enemy)e).life == 0) {
										//Smash enemy!	
											Game.entities.remove(i);
											break;
									}
										
								}
							}else if(e instanceof Enemy2) {
								if(Entity.isColidding(this, e)) {
									//Smash enemy!							
								isJumping = true;
								jumpHeight = 32;
									//Remove life enemy
								((Enemy2)e).life-=3;
								if(((Enemy2)e).life == 0) {
									//Smash enemy!	
										Game.entities.remove(i);
										break;
								}
									
							}
						}else if(e instanceof Enemy3) {
							if(Entity.isColidding(this, e)) {
								//Smash enemy!							
							isJumping = true;
							jumpHeight = 32;
								//Remove life enemy
							((Enemy3)e).life-=3;
							if(((Enemy3)e).life == 0) {
								//Smash enemy!	
									Game.entities.remove(i);
									break;
							}
								
						}
					} 
					}
				}
				if(right && World.isFree((int)(x+speed), (int)y)) {
					x+=speed;
					dir = 1;
				}
				else if(left && World.isFree((int)(x-speed), (int)y)) {
					x-=speed;
					dir = -1;
				}
				
				if(moved) {
					frames++;
					if(frames == maxFrames) {
						frames = 0;
						index++;
						if(index > maxIndex)
							index = 0;
					}
				}
				
				
				if(jump) {
					if(!World.isFree(this.getX(),this.getY()+1)) {
						isJumping = true;
					}else {
						jump = false;
					}
				}
				
				if(isJumping) {
					if(World.isFree(this.getX(), this.getY()-2)) {
						y-=2;
						jumpFrames+=2;
						if(jumpFrames == jumpHeight) {
							isJumping = false;
							jump = false;
							jumpFrames = 0;
						}
					}else {
						isJumping = false;
						jump = false;
						jumpFrames = 0;
					}
				}
				
				for(int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
						if(e instanceof Enemy) {
							if(Entity.isColidding(this, e)) {
										if(Entity.rand.nextInt(100) < 5)
													life--;
						}							
						  }  else if(e instanceof Enemy2) {
							  if(Entity.isColidding(this, e)) {
								  		if(Entity.rand.nextInt(100) < 5)
								  				life--;
							  }
						  }  else if(e instanceof Enemy3) {
							  if(Entity.isColidding(this, e)) {
							  			if(Entity.rand.nextInt(100) < 5)
							  					life--;
						  }
					  }
				}
						
				
				for(int i = 0; i < Game.entities.size(); i++) {
					Entity e = Game.entities.get(i);
						if(e instanceof Coin) {
							if(Entity.isColidding(this, e)) {
										Game.entities.remove(i);
										Player.currentCoins++;
										break;
				       }
					}	
				}	
						
					Camera.x = Camera.clamp((int)x -  Game.WIDTH / 2, 0,World.WIDTH * 16 - Game.WIDTH);
					Camera.y = Camera.clamp((int)y -  Game.HEIGHT / 2, 0,World.HEIGHT * 16 - Game.HEIGHT);
			}
	

			public void render(Graphics g) {
				framesAnimation++;
				if(framesAnimation == maxFrame) {
					curSprites++;
					framesAnimation = 0;
						if(curSprites == maxSprites) {
							curSprites = 0;
						}
				}
					if(dir == right_dir) {
						
						sprite = Entity.player_Right[curSprites];
						
					}else if(dir == left_dir) {
					
						sprite = Entity.player_Left[curSprites];
					}
				super.render(g);	
			}

}
