package com.maelstrom.snowcone;

import net.minecraft.util.BlockPos;

public class MathHelper
{

	public static double getDistance(BlockPos pos1, BlockPos pos2)
	{
		if(pos1 == null || pos2 == null)
			return 0d;
		double x = (pos1.getX() + .5) - (pos2.getX() + .5);
		double y = (pos1.getY() + .5) - (pos2.getY() + .5);
		double z = (pos1.getZ() + .5) - (pos2.getZ() + .5);
		double distance = Math.sqrt(x*x+y*y+z*z);
		return distance;
	}
}
