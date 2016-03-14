package com.maelstrom.armech.common.tileentity.clientonly;

import java.util.Random;

import net.minecraft.tileentity.TileEntity;

public class TileEntityWorldGenCrystal extends TileEntity
{
	public static Random rand = new Random();
	public int META = 0;
	public int red = rand.nextInt(255), green = rand.nextInt(255), blue = rand.nextInt(255);
	public boolean switchred = rand.nextBoolean(), switchgreen = rand.nextBoolean(), switchblue = rand.nextBoolean();
	public TileEntityWorldGenCrystal(int meta)
	{
		META = meta;
	}
}