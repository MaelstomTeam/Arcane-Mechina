package com.maelstrom.arcanemechina.client.gui.book;

public class Image
{
    
    public Image() {}
    public Image(String src, int h, int w, int X, int Y, double s)
    {
    	source = src;
    	height = h;
    	width = w;
    	x = X;
    	y = Y;
    	scale = s;
    }

	public String source;
	public int height;
	public int width;
	public double x;
	public double y;
	public double scale;
}
