package me.micheal.towerdefense;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JOptionPane;

public class Log {
	
	
	/*
	 * @TODO
	 * Need to make logging destination w/ try/catch
	 * Minimalize errors. while(){try{}catch(){}}
	 */
	
	private static int logFpsWait = 0;
	private static int logFpsWaitThreshold = 0;
	
	public Log(String dest){
		
	}
	
	public static void log(String n){
		Date date = new Date();
		System.out.println(new SimpleDateFormat("[dd/MM/yy|HH'h'mm'm'ss's'z]").format(date)+" "+n);
	}
	public static void log(int n){
		Date date = new Date();
		System.out.println(new SimpleDateFormat("[dd/MM/yy|HH'h'mm'm'ss's'z]").format(date)+" "+n);
	}
	public static void logFps(int fps, int threshold){
		logFpsWaitThreshold = threshold;
		Date date = new Date();
		if(logFpsWait==logFpsWaitThreshold){
			System.out.println(new SimpleDateFormat("[dd/MM/yy|HH'h'mm'm'ss's'z]").format(date)+" "+fps+" FPS");
			logFpsWait = 0;
		}
		else
			if(logFpsWait<logFpsWaitThreshold)
				logFpsWait++;
			else
				logFpsWait = 0;
		
	}
	public static void err(Exception e){
		e.printStackTrace();
		Writer writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		e.printStackTrace(printWriter);
		JOptionPane.showMessageDialog(null, writer.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
		printWriter.close();
		System.exit(0);
	}
	public static void err(Exception e, boolean quit){
		e.printStackTrace();
		if(quit){
			Writer writer = new StringWriter();
			PrintWriter printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			JOptionPane.showMessageDialog(null, writer.toString(), "Fatal Error", JOptionPane.ERROR_MESSAGE);
			printWriter.close();
			System.exit(0);
		}
	}
}