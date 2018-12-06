package com.maelstrom.snowcone.libraryAPI;

import com.maelstrom.snowcone.SnowCone;

import net.minecraft.util.ResourceLocation;

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
    private ResourceLocation image;
    public ResourceLocation getImage()
    {
    	if(image == null)
	    	if(source.contains(":"))
	    		image = new ResourceLocation(source);
	    	else
	    		image = new ResourceLocation(SnowCone.MODID, "textures/gui/pages/"+source);
    	return image;
    }

	public String source;
	public int height;
	public int width;
	public double x;
	public double y;
	public double scale;
}
