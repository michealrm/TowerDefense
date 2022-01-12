package me.micheal.towerdefense;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;

public class Worker {
	
	public static BufferedImage[] getSpritesheet(String path, int width, int height, int rows, int cols){
		BufferedImage[] sprites = null;
		BufferedImage bigImg = null;
		try {
			bigImg = ImageIO.read(new File(path));
		} catch (IOException e) {
			Log.err(e);
		}

		sprites = new BufferedImage[rows * cols];

		for (int r= 0; r< rows; r++)
		    for (int c = 0; c < cols; c++)
		        sprites[(r*cols)+c] =bigImg.getSubimage(c * width,r * height, width, height);
		
		return sprites;
	}
	
	public static char[][] map(String path, int width, int height){
		Scanner dest = null;
		char[][] grid = new char[width][height];
		try{
			dest = new Scanner(new File(path+"map.dat"));
			for(int r = 0;r<height;r++)
				grid[r] = dest.nextLine().toCharArray();
		}
		catch(Exception e){
			Log.err(e);
		}
		return grid;
	}
	public String[] importDat(String file){ // Needs full destination
		Scanner in = null;
		try {
			in = new Scanner(new File(file));
		} catch (FileNotFoundException e) {
			Log.err(e);
		}
		ArrayList<String> list = new ArrayList<String>();
		while(in.hasNextLine()){
			list.add(in.nextLine());
		}
		String[] array = new String[list.size()];
		for(int i = 0;i<list.size();i++)
			array[i] = list.get(i);
		return array;
	}
}
