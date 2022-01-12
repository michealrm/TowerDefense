package me.micheal.towerdefense;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Map extends GameObject{
	
	private char[][] grid;
	private BufferedImage[] sprites;
	private final int c = 4;
	private final int r = 4;
	private BufferedImage[][] finalSprites = new BufferedImage[9][16];
	
	public Map(ID id, String path){
		super(id);
		this.grid = Worker.map(path, Game.HDim, Game.VDim);
		this.sprites = Worker.getSpritesheet(path+"ss_tiles.png",72,72,c,r);
		generate(Handler.getGraphics(), sprites);
	}
	private void generate(Graphics g, BufferedImage[] sprites){
		for (int row = 0; row < Game.VDim; row++)
		    for (int col = 0; col < Game.HDim; col++){
		    	boolean left = false;
				boolean right = false;
				boolean up = false;
				boolean down = false;
				int sides = 0;
					if((row-1>=0&&grid[row-1][col]=='r') || (row-1>=0&&grid[row-1][col]=='e') || (row-1>=0&&grid[row-1][col]=='x'))
						up = true;
					if((col+1<=15&&grid[row][col+1]=='r') || (col+1<=15&&grid[row][col+1]=='e') || (col+1<=15&&grid[row][col+1]=='x'))
						right = true;
					if((row+1<=8&&grid[row+1][col]=='r') || (row+1<=8&&grid[row+1][col]=='e') || (row+1<=8&&grid[row+1][col]=='x'))
						down = true;
					if((col-1>=0&&grid[row][col-1]=='r') || (col-1>=0&&grid[row][col-1]=='e') || (col-1>=0&&grid[row][col-1]=='x'))
						left = true;
				if(left)
					sides++;
				if(right)
					sides++;
				if(up)
					sides++;
				if(down)
					sides++;
		    	/*
		    	 * 0-5 = Border (b)
		    	 * 6-9 = Roads (r)
		    	 * 10-13 = Turns (^,v,<,>)
		    	 * 14-15 = Can't place (!,@)
		    	 * Enter (e)
		    	 * Exit (x)
		    	 */
				if(grid[row][col]=='b'){
					Random r = new Random();
					finalSprites[row][col] = sprites[r.nextInt(3)];
				}
				else if(grid[row][col]=='r'){
					if(sides == 0)
						Log.err(new MapLoadException("Isolated required object"));
					else if(sides == 4)
						Log.err(new MapLoadException("Quad-side not implemented yet."));
					else if (sides == 3)
						Log.err(new MapLoadException("Tri-side not implemented yet."));

					if(left&&right){
						Random r = new Random();
						finalSprites[row][col] = sprites[r.nextInt(7-6)+6];
					}
					else if(up&&down){
						Random r = new Random();
						finalSprites[row][col] = sprites[r.nextInt(5-4)+4];
					}
					else if(left&&down)
						finalSprites[row][col] = sprites[8];
					else if(left&&up)
						finalSprites[row][col] = sprites[9];
					else if(up&&right)
						finalSprites[row][col] = sprites[10];
					else if(down&&right)
						finalSprites[row][col] = sprites[11];
					
				}
				else if(grid[row][col]=='!')
					finalSprites[row][col] = sprites[14];
				else if(grid[row][col]=='@')
					finalSprites[row][col] = sprites[15];
				else if(grid[row][col]=='e'){
					Random r = new Random();
					finalSprites[row][col] = left||right ? sprites[r.nextInt(7-6)+6] : sprites[r.nextInt(5-4)+4];
					Game.enterX = col;
					Game.enterY = row;
					Log.log("Updating entry point to ["+Game.enterX+","+Game.enterY+"]");
				}
				else if(grid[row][col]=='x'){
					Random r = new Random();
					finalSprites[row][col] = left||right ? sprites[r.nextInt(7-6)+6] : sprites[r.nextInt(5-4)+4];
					Game.exitX = col;
					Game.exitY = row;
					Log.log("Updating exit point to ["+Game.exitX+","+Game.exitY+"]");
				}
				else
					Log.err(new MapLoadException("\'"+grid[row][col]+"\' Row:"+row+" Col:"+col+"\n\tat "+Game.mapDest));
			}
	}
	public void tick() {

	}

	public void render(Graphics g) {
		for (int row= 0; row< Game.VDim; row++)
		    for (int col = 0; col < Game.HDim; col++){
		    	/*
		    	 * 0-5 = Border (b)
		    	 * 6-9 = Roads (r)
		    	 * 10-13 = Turns (^,v,<,>)
		    	 * 14-15 = Can't place (!,@)
		    	 * Enter (e)
		    	 * Exit (x)
		    	 */
				g.drawImage(finalSprites[row][col], col*72, row*72, null);
			}
	}
}
