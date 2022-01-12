package me.micheal.towerdefense;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Fps extends GameObject{

	private static int fps = 0;
	
	public Fps(int x, int y, ID id) {
		super(x, y, id);
	}
	public static void setFps(int n){
		fps = n;
	}
	public Fps(ID id){
		super(id);
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier", Font.PLAIN, 12));
		g.drawString(String.valueOf((fps = fps > 1000 ? 999 : fps)+" FPS"), super.getX(), super.getY());
	}
}
