package com.maelstrom.armech.common.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.snowcone.RGB;

public class TileEntityCrystal extends TileEntity implements ITickable {

	private static Random random;
	
	@Override
	public void update()
	{
		if(random == null)
			random = new Random(worldObj.getSeed());
		
		List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(6, 6, 6));
		int hit = 0;
		if(!worldObj.isRemote)
		{
			for(EntityLivingBase entity : list)
			{
				if(!(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) && getDistance(pos, entity) < 4.5)
				{
					if(random.nextInt((16 / (worldObj.isRaining() && worldObj.canBlockSeeSky(entity.getPosition()) ? 4 : 1)) * list.size()) == 0)
					{
							ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + ((0d * 1.5) -.75) +.5, pos.getZ() +.5, entity.posX, entity.posY + entity.height, entity.posZ, new RGB(1f,1f,1f));
							entity.attackEntityFrom(DamageSource.lightningBolt, 1f); //increase as power goes up
							entity.setVelocity(0, 0, 0);
							if(hit++ == 3)
							return;
					}
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return new AxisAlignedBB(getPos().add(0, -1, 0), getPos().add(1,2,1));
	}
	
	public double getDistance(BlockPos pos1, Entity pos2)
	{
		double x = (pos1.getX() + .5) - pos2.posX;
		double y = (pos1.getY() + .5) - pos2.posY;
		double z = (pos1.getZ() + .5) - pos2.posZ;
		double distance = Math.sqrt(x*x+y*y+z*z);
		return distance;
	}
}
