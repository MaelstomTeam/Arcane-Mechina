package com.maelstrom.arcanemechina.common.runic.rune_interfaces;

import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITicking
{
	public default void doAction(RuneTileEntity entity, int state)
	{
		switch(state)
		{
		case 0: 
			pretick(entity.getWorld(), entity.getPos(), entity);
			break;
		case 1: 
			tick(entity.getWorld(), entity.getPos(), entity);
			break;
		case 2:
			posttick(entity.getWorld(), entity.getPos(), entity);
			break;
		}
	}
	public void pretick(World world, BlockPos blockPos, RuneTileEntity entity);
	public void tick(World world, BlockPos blockPos, RuneTileEntity entity);
	public void posttick(World world, BlockPos blockPos, RuneTileEntity entity);
}
