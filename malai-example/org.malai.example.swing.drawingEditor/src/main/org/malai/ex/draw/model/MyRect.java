package org.malai.ex.draw.model;

public class MyRect extends MyShape {
	protected double x;
	protected double y;
	protected double width;
	protected double height;
	
	
	public MyRect() {
		super();
		x = 0;
		y = 0;
		width = 1;
		height = 1;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;
	}


	public double getWidth() {
		return width;
	}


	public void setWidth(double width) {
		this.width = width;
	}


	public double getHeight() {
		return height;
	}


	public void setHeight(double height) {
		this.height = height;
	}
}
