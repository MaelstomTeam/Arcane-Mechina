package com.maelstrom.arcanemechina.common.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class TileWardTest extends TileEntity
{
	public void updateEntity()
	{
		int x, y, z;
		x = this.xCoord;
		y = this.yCoord;
		z = this.zCoord;
		if(worldObj.getBlock(x, y, z) == Blocks.air)
		{
			this.invalidate();
			return;
		}
		pushAwayEntities(worldObj, x, y, z);
	}
	Random rand = new Random();
	private void pushAwayEntities(World world, int x, int y, int z)
	{
		int height = -1;
		for (int y2 = 5; y2 > 0; y2--)
			if(world.getBlock(x, y + 1 + y2, z) != Blocks.air)
				height = y2;
		if(height <= 0)
		{
			this.invalidate();
			return;
		}
		
        for (int k = 0; k < 2 * height; ++k)
        {
            this.worldObj.spawnParticle("portal", (x + .5) + (this.rand.nextDouble() - 0.5D), y + 1 + this.rand.nextDouble() * height, (z + .5) + (this.rand.nextDouble() - 0.5D), (this.rand.nextDouble() - 0.5D), -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D));
        }
		AxisAlignedBB point = AxisAlignedBB.getBoundingBox(x, y, z, x+1, y+5, z+1);
		List<Entity> entity = world.getEntitiesWithinAABB(Entity.class, point);
		for(Entity ent : entity)
		{
			if(!(ent instanceof EntityPlayer))
				ent.setVelocity((ent.posX - (x + .5)) / 10, (ent.posY - (y + .5)) / 50, (ent.posZ - (z + .5)) / 10);
		}
	}
}