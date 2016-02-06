package com.maelstrom.armech.common;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("all")
public class WorldAccess
{
	
	public static boolean isBlockEqual(World world, BlockPos pos, Block block)
	{
		return world.getBlockState(pos).getBlock() == block;
	}
	
	public static List getEntitiesInArea(World world, BlockPos pos1, BlockPos pos2, Class _class)
	{
		return world.getEntitiesWithinAABB(_class, AxisAlignedBB.fromBounds(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ()));
	}
	
	public static List<EntityItem> getItemsInWorld(World world, BlockPos pos1, BlockPos pos2)
	{
		return getEntitiesInArea(world, pos1, pos2, EntityItem.class);
	}
	
	public static void spawnEntityInWorldWithoutVelocity(Entity ent)
	{
		if(!ent.worldObj.isRemote)
		{
			ent.setVelocity(0, 0, 0);
			ent.worldObj.spawnEntityInWorld(ent);
		}
	}
	
	public static void spawnEntityInWorld(Entity ent)
	{
		if(!ent.worldObj.isRemote)
		{
			ent.worldObj.spawnEntityInWorld(ent);
		}
	}
}