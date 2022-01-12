package me.micheal.towerdefense;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable{

	private static final long serialVersionUID = -1141643122093296153L;
	public static final int WIDTH = 1152, HEIGHT = 648, HDim = 16, VDim = 9;
	//private static final String AUTHOR = "Micheal";
	private static final String VERSION = "0.1A";
	private Thread thread;
	private boolean running = false;
	private Handler handler = new Handler();
	public static boolean showFps = true;
	protected static String mapDest; // This is the map dir. Will make user functional @TODO.
	public static int enterX = 0;
	public static int enterY = 0;
	public static int exitX = 0;
	public static int exitY = 0;
	protected static char[][] entityGrid;
	
	public static void main(String[] args){
		new Game();
	}
	
	public Game(){
		Log.log("Creating window.");
		new Window(WIDTH, HEIGHT, "Tower Defense ["+VERSION+"]", this);
		
		//Log.log("Loading working directory");
		//Save.checkSavePath();
		//Save.saveValues();
		//Log.log("Loading file destination.");
		mapDest = "e_test/";
		
		Log.log("Loading map...");
		handler.addObject(new Map(ID.Map,mapDest));
		Log.log("Processing objects.");
		if(showFps)
			handler.addObject(new Fps(5,10,ID.Fps));
	}

	public synchronized void start(){
		thread = new Thread(this);
		Log.log("Framework(Game) starting threads.");
		thread.start();
		running = true;
	}
	public synchronized void stop(){
		try{
			thread.join();
			Log.log("Shutting down.");
			running = false;
		}
		catch(Exception e){
			Log.err(e);
		}
	}
	public void run(){
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(this.running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(running)
				render();
			frames++;
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				Fps.setFps(frames);
				Log.logFps(frames,5);
				frames = 0;
			}
		}
		stop();
	}
	private void tick(){
		handler.tick();	
	}
	private void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null){
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		g.dispose();
		bs.show();
	}
}
