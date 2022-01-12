package me.micheal.towerdefense;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
/*import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;*/

public class Window extends Canvas{

	private static final long serialVersionUID = 7336167744756578914L;
	
	public Window(int w, int h, String title, Game game){
		
		
		
		JFrame frame = new JFrame(title);
		
		frame.getContentPane().setPreferredSize(new Dimension(w,h));
		frame.getContentPane().setMaximumSize(new Dimension(w,h));
		frame.getContentPane().setMinimumSize(new Dimension(w,h));
		frame.pack();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.add(game);/*
		Log.log("Setting frame(main)'s visibility to false");
		frame.setVisible(false);
		Log.log("Starting game.");*/
		game.start();
		/*
		Log.log("Starting splashscreen.");
		JWindow splashWindow = new JWindow();
		try {
			splashWindow.getContentPane().add(new JLabel("", new ImageIcon(new URL("http://docs.oracle.com/javase/tutorial/uiswing/examples/misc/SplashDemoProject/src/misc/images/splash.gif")), SwingConstants.CENTER));
		} catch (MalformedURLException e) {
			Log.err(e);
		}
		splashWindow.setBounds(500, 150, 300, 200);
		splashWindow.setVisible(true);
		try {
		    Thread.sleep(5000);
		} catch (InterruptedException e) {
		    e.printStackTrace();
		}
		splashWindow.setVisible(false);
		Log.log("Disposing splashscreen");
		splashWindow.dispose();
*/
		Log.log("Setting frame(main)'s visibility to true");
		frame.setVisible(true);
	}
	
}