package com.ider.objects;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Position {
	private int x,y;
	
	public Position()
	{
		x=0;
		y=0;
	}
	
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	

	
	public void goUp()
	{
		y--;
	}
	public void goDown()
	{
		y++;
	}
	
	public void goRight()
	{
		x++;
	}
	
	public void goLeft()
	{
		x--;
	}
	
	@JsonIgnore
	public Position getUpPosition()
	{
		return new Position(x, y-1);
	}
	@JsonIgnore
	public Position getDownPosition()
	{
		return new Position(x, y+1);
	}
	@JsonIgnore
	public Position getRightPosition()
	{
		return new Position(x+1, y);
	}
	
	@JsonIgnore
	public Position getLeftPosition()
	{
		return new Position(x-1, y);
	}
	
	
	public boolean isNeighbor(Position position)
	{
		return (getRightPosition().equals(position) 
				|| getUpPosition().equals(position)
				|| getLeftPosition().equals(position)
				|| getDownPosition().equals(position));
	}
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Position other) {
		
		return (this.x == other.x) && (this.y == other.y);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "("+getX()+","+getY()+")";
	}
	
	
	
	
	
}
