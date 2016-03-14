package com.maelstrom.armech.client.fx;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.maelstrom.armech.client.fx.lightning.FXLightning;
import com.maelstrom.snowcone.RGB;

public class ParticleDispatcher
{
	
	public static int particleCount = 0;
	public static final int particleAllowance = 256; // sorta broken
	
	public FXLightning arcLightning(double x, double y, double z, double tx, double ty, double tz, double arc_height, RGB color)
	{
		FXLightning lightning = new FXLightning(getMinecraftWorld(), x, y, z, tx, ty, tz, arc_height);
		lightning.setRGB(color.getRed(), color.getGreen(), color.getBlue());
		lightning.setArcSize(.25f);
		addEffect(lightning);
		return lightning;
	}
	
	public FXLightning arcLightningDefault(double x, double y, double z, double tx, double ty, double tz)
	{
		return this.arcLightning(x, y, z, tx, ty, tz, 1d, RGB.WHITE);
	}
	
	private void addEffect(EntityFX entFX)
	{
//		if(particleCount++ <= particleAllowance) //broken
			FMLClientHandler.instance().getClient().effectRenderer.addEffect(entFX);
	}

	private World getMinecraftWorld()
	{
		return FMLClientHandler.instance().getClient().theWorld;
	}
}
