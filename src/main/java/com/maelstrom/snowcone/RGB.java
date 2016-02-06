package com.maelstrom.snowcone;

import java.awt.Color;

public class RGB
{
	public static RGB RED = new RGB(Color.RED);
	public static RGB BLUE = new RGB(Color.BLUE);
	public static RGB YELLOW = new RGB(Color.YELLOW);
	
	public static RGB GREEN = new RGB(Color.GREEN);
	public static RGB ORANGE = new RGB(Color.ORANGE);
	public static RGB PURPLE = new RGB(1f,0f,1f);
	
	public static RGB BLACK = new RGB(Color.BLACK);
	public static RGB WHITE = new RGB(Color.WHITE);
	
	private float red;
	private float green;
	private float blue;
	private float alpha;

	public RGB(float red, float green, float blue, float alpha)
	{
		this.setRed(red);
		this.setGreen(green);
		this.setBlue(blue);
		this.setAlpha(alpha);
	}
	public RGB(float red, float green, float blue)
	{
		this(red, green, blue, 1f);
	}
	public RGB(Color color)
	{
		red = color.getRed();
		green = color.getGreen();
		blue = color.getBlue();
		alpha = color.getAlpha();
	}
	
	public float getAlpha() {
		return alpha;
	}
	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}
	public float getBlue() {
		return blue;
	}
	public void setBlue(float blue) {
		this.blue = blue;
	}
	public float getGreen() {
		return green;
	}
	public void setGreen(float green) {
		this.green = green;
	}
	public float getRed() {
		return red;
	}
	public void setRed(float red) {
		this.red = red;
	}
}
