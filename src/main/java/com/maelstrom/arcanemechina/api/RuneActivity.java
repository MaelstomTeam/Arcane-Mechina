package com.maelstrom.arcanemechina.api;

import com.maelstrom.arcanemechina.common.event.EventRuneBackfire;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class RuneActivity
{
	public EventRuneBackfire getBackfireResult(BlockPos pos, EntityLivingBase entity)
	{
		return getBackfireResult(entity.world, pos, entity);
	}
	public abstract EventRuneBackfire getBackfireResult(World w, BlockPos pos, EntityLivingBase entity);
	public abstract void runActivity(World w, BlockPos pos);
	public abstract boolean isValid(World w, BlockPos pos);
}