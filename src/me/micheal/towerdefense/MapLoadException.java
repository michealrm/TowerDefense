package me.micheal.towerdefense;

public class MapLoadException extends Exception{
	private static final long serialVersionUID = -8123065131207695735L;
	public MapLoadException(){
		super();
	}
	public MapLoadException(String message){
		super(message);
	}
	public MapLoadException(String message, Throwable cause){
		super(message,cause);
	}
	public MapLoadException(Throwable cause){
		super(cause);
	}
}
