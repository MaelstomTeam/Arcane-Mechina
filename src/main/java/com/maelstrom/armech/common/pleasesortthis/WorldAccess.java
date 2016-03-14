package com.maelstrom.armech.common.pleasesortthis;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
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
	
	public static List<EntityItem> getItemsFromWorld(World world, BlockPos pos1, BlockPos pos2)
	{
		return getEntitiesInArea(world, pos1, pos2, EntityItem.class);
	}
	
	public static List<EntityItem> getExactItemsFromWorld(World world, Item item, BlockPos pos1, BlockPos pos2)
	{
		List<EntityItem> list = getEntitiesInArea(world, pos1, pos2, EntityItem.class);
		//removes unwanted entities
		for(EntityItem entItem : list)
			if(entItem.getEntityItem() != null && entItem.getEntityItem().getItem() != item)
				list.remove(entItem);
		
		return list;
	}
	
	public static Entity spawnEntityInWorldWithoutVelocity(Entity ent)
	{
		if(!ent.worldObj.isRemote)
		{
			ent.setVelocity(0, 0, 0);
			ent.worldObj.spawnEntityInWorld(ent);
		}
		return ent;
	}
	
	public static Entity spawnEntityInWorld(Entity ent)
	{
		if(!ent.worldObj.isRemote)
			ent.worldObj.spawnEntityInWorld(ent);
		return ent;
	}
	
	public static BlockPos getFaceOffset(BlockPos pos, EnumFacing face)
	{
		return new BlockPos(pos.getX() + face.getFrontOffsetX(), pos.getY() + face.getFrontOffsetY(), pos.getZ() + face.getFrontOffsetZ());
	}
	
	public static IBlockState getBlockFromPosFace(World world, BlockPos pos, EnumFacing face)
	{
		return world.getBlockState(getFaceOffset(pos, face));
	}
	
	public static void setBlockOnFace(World world, BlockPos pos, EnumFacing face, Block block)
	{
		if(getBlockFromPosFace(world,pos,face).getBlock().canBeReplacedByLeaves(world, getFaceOffset(pos, face)))
			world.setBlockState(getFaceOffset(pos,face), block.getDefaultState());
	}
	
	public static void setBlockOnFace(World world, BlockPos pos, EnumFacing face, Block block, int meta)
	{
		if(getBlockFromPosFace(world,pos,face).getBlock().canBeReplacedByLeaves(world, getFaceOffset(pos, face)))
			world.setBlockState(getFaceOffset(pos,face), block.getStateFromMeta(meta));
	}
	
	public static void setBlockOnFace(World world, BlockPos pos, EnumFacing face, IBlockState state)
	{
		if(getBlockFromPosFace(world,pos,face).getBlock().canBeReplacedByLeaves(world, getFaceOffset(pos, face)))
			world.setBlockState(getFaceOffset(pos,face), state);
	}
}