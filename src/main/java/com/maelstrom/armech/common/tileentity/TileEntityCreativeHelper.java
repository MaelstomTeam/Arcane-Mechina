package com.maelstrom.armech.common.tileentity;

import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.IMob;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

public class TileEntityCreativeHelper extends TileEntity implements ITickable
{
	World world;
	@Override
	public void update()
	{
		if(world == null)
			world = this.worldObj;
		int additionalUp = 0;
		
		while(world.getBlockState(getPos().up(additionalUp++)).getBlock() != Blocks.air)
		{
			if(world.getBlockState(getPos().up(additionalUp)).getBlock() == Blocks.lit_pumpkin)
			{
				if(world.getEntitiesWithinAABB(EntityItemFrame.class, AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY() + additionalUp, this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + additionalUp + 1, this.pos.getZ() + 1).expand(1,0,1)).size()>0)
				{
					for(EntityItem item : world.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1).expand(128, 256, 128)))
						item.setDead();
				}
				for(EntityLiving entity : world.getEntitiesWithinAABB(EntityLiving.class, AxisAlignedBB.fromBounds(this.pos.getX(), this.pos.getY(), this.pos.getZ(), this.pos.getX() + 1, this.pos.getY() + 1, this.pos.getZ() + 1).expand(128, 256, 128)))
					if(entity instanceof IMob)
						entity.setDead();
			}
			else if(world.getBlockState(getPos().up(additionalUp)).getBlock() == Blocks.lapis_block)
			{
				boolean shouldTick = false;
				for(EnumFacing face : EnumFacing.VALUES)
					if(world.getRedstonePower(getPos().up(additionalUp), face) > 0)
					{
						shouldTick = true;
						break;
					}
				if(shouldTick)
					world.setWorldTime((world.getWorldTime() / 24000) * 24000 + 18000);
			}
			else if(world.getBlockState(getPos().up(additionalUp)).getBlock() == Blocks.gold_block)
			{
				boolean shouldTick = false;
				for(EnumFacing face : EnumFacing.VALUES)
					if(world.getRedstonePower(getPos().up(additionalUp), face) > 0)
					{
						shouldTick = true;
						break;
					}
				if(shouldTick)
					world.setWorldTime((world.getWorldTime() / 24000) * 24000 + 6000);
			}
		}
	
	}

}