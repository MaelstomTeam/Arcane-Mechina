package com.maelstrom.snowcone;

import java.awt.Color;

public class RGB
{
	public static final RGB RED = new RGB(1,0,0);
	public static final RGB BLUE = new RGB(0,0,1);
	public static final RGB YELLOW = new RGB(0,1,1);
	
	public static final RGB GREEN = new RGB(0,1,0);
	public static final RGB ORANGE = new RGB(1,.5f, 0);
	public static final RGB PURPLE = new RGB(1f,0f,1f);
	
	public static final RGB BLACK = new RGB(0,0,0);
	public static final RGB WHITE = new RGB(1,1,1);
	
	public static final RGB ELECTRIC_BLUE = new RGB((125f/256f),(249f / 256f),1f);
	
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
