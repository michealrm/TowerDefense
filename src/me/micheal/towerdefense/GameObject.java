package me.micheal.towerdefense;

import java.awt.Graphics;

public abstract class GameObject {
	private int x,y,velX,velY;
	private ID id;
	public GameObject(int x, int y, ID id){
		this.x = x;
		this.y = y;
		this.id = id;
	}
	public GameObject(ID id){
		this.x = 0;
		this.y = 0;
		this.id = id;
	}
	public abstract void tick();
	public abstract void render(Graphics g);
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public int getX(){
		return this.x;
	}
	public int getY(){
		return this.y;
	}
	public void setID(ID id){
		this.id = id;
	}
	public ID getID(){
		return this.id;
	}
	public void setVelX(int velX){
		Log.log("Object ["+id+"] set velX "+this.velX+"->"+velX);
		this.velX = velX;
	}
	public void setVelY(int velY){
		Log.log("Object ["+id+"] set velY "+this.velY+"->"+velY);
		this.velY = velY;
	}
	public int getVelX(){
		return velX;
	}
	public int getVelY(){
		return velY;
	}
}