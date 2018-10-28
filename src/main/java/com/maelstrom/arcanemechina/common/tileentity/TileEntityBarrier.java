package com.maelstrom.arcanemechina.common.tileentity;

import java.util.List;

import com.maelstrom.arcanemechina.common.block.BlockList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityBarrier extends TileEntity implements ITickable
{
	public boolean isActive()
	{
		return false;
	}

	@Override
	public void update() {
		if(world.getBlockState(pos).getBlock() != BlockList.Rune)
			this.invalidate();
		if(!isInvalid())
		{
			if(world.getTileEntity(pos.up()) instanceof IInventory)
			{
				IInventory inv = (IInventory)world.getTileEntity(pos.up());
				boolean hostile = false;
				boolean neutral = false;
				boolean passive = false;
				int distance = 32;
				for(int i = 0; i < inv.getSizeInventory(); i++)
				{
					if(inv.getStackInSlot(i).getItem() == Items.ROTTEN_FLESH)
						hostile = true;
					if(inv.getStackInSlot(i).getItem() == Items.GOLD_NUGGET)
						neutral = true;
					if(inv.getStackInSlot(i).getItem() == Items.WHEAT)
						passive = true;
					List<Entity> list;
					if(hostile)
					{
						list = world.getEntitiesWithinAABB(EntityMob.class,new AxisAlignedBB(pos.add(distance - 1, distance - 1, distance - 1),pos.add(-distance, -distance, -distance)));
						if(list.size() > 0)
							for(Entity e : list)
							{
								if(!(e instanceof EntityPlayer)) {
									double x = (pos.getX() + 0.5) - e.posX;
									x = x * x;
									double y = (pos.getY() + 0.5) - e.posY;
									y = y * y;
									double z = (pos.getZ() + 0.5) - e.posZ;
									z = z * z;
									double root = Math.sqrt(x + y + z);
									if(root < distance)
										e.move(MoverType.SELF, 0 - ((pos.getX() + 0.5) - e.posX) / distance, 0 - ((pos.getY() + 0.5) - e.posY) / distance, 0 - ((pos.getZ() + 0.5) - e.posZ) / distance);
								}
							}
					}
					if(neutral)
					{
						list = world.getEntitiesWithinAABB(EntityAnimal.class,new AxisAlignedBB(pos.add(distance - 1, distance - 1, distance - 1),pos.add(-distance, -distance, -distance)));
						if(list.size() > 0)
							for(Entity e : list)
							{
								if(!(e instanceof EntityPlayer)) {
									double x = (pos.getX() + 0.5) - e.posX;
									x = x * x;
									double y = (pos.getY() + 0.5) - e.posY;
									y = y * y;
									double z = (pos.getZ() + 0.5) - e.posZ;
									z = z * z;
									double root = Math.sqrt(x + y + z);
									if(root < distance)
										e.move(MoverType.SELF, 0 - ((pos.getX() + 0.5) - e.posX) / distance, 0 - ((pos.getY() + 0.5) - e.posY) / distance, 0 - ((pos.getZ() + 0.5) - e.posZ) / distance);
								}
							}
					}
					if(passive)
					{
						list = world.getEntitiesWithinAABB(EntityLiving.class,new AxisAlignedBB(pos.add(distance - 1, distance - 1, distance - 1),pos.add(-distance, -distance, -distance)));
						if(list.size() > 0)
							for(Entity e : list)
							{
								if(!(e instanceof EntityAnimal) && !(e instanceof EntityMob)) {
									double x = (pos.getX() + 0.5) - e.posX;
									x = x * x;
									double y = (pos.getY() + 0.5) - e.posY;
									y = y * y;
									double z = (pos.getZ() + 0.5) - e.posZ;
									z = z * z;
									double root = Math.sqrt(x + y + z);
									if(root < distance)
										e.move(MoverType.SELF, 0 - ((pos.getX() + 0.5) - e.posX) / distance, 0 - ((pos.getY() + 0.5) - e.posY) / distance, 0 - ((pos.getZ() + 0.5) - e.posZ) / distance);
								}
							}
					}					
				}
				
			}
			else {
				List<Entity> list = world.getEntitiesWithinAABB(Entity.class,new AxisAlignedBB(pos.add(5, 2, 5),pos.add(-4, -2, -4)));
				if(list.size() > 0)
					for(Entity e : list)
					{
						if(!(e instanceof EntityPlayer)) {
							double x = (pos.getX() + 0.5) - e.posX;
							x = x * x;
							double y = (pos.getY() + 0.5) - e.posY;
							y = y * y;
							double z = (pos.getZ() + 0.5) - e.posZ;
							z = z * z;
							double root = Math.sqrt(x + y + z);
							if(root < 4.5)
								e.move(MoverType.SELF, 0 - ((pos.getX() + 0.5) - e.posX) / 20, 0 - ((pos.getY() + 0.5) - e.posY) / 20, 0 - ((pos.getZ() + 0.5) - e.posZ) / 20);
						}
					}
			}
		}
	}
}
