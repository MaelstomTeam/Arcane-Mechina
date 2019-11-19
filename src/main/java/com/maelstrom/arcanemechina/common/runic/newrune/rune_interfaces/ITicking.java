package com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces;

import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ITicking
{
	public default void doAction(RuneTileEntity entity)
	{
		tick(entity.getWorld(), entity.getPos(), entity);
	}
	public void tick(World world, BlockPos blockPos, RuneTileEntity entity);
}
