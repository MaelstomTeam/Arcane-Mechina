package com.maelstrom.armech.common.tileentity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.registry.AMBlocks;
import com.maelstrom.snowcone.RGB;

public class OLD_TileEntityCrystalRelay extends OLD_TileEntityCrystal implements ITickable
{
	int particleCountDown = 0;
	int paricleCountDownEnd = 20;
	
	
	@Override
	public void update()
	{
		this.setMaxStoredEnergy(64);
		if(random == null)
		{
			random = new Random(worldObj.getSeed());
			rotation = random.nextDouble() * 60d;
		}
		World world = worldObj;
		List<OLD_TileEntityCrystal> crystalLink = new ArrayList<OLD_TileEntityCrystal>();
		List<OLD_TileEntityCrystal> crystalLink2 = new ArrayList<OLD_TileEntityCrystal>();
		for(int x = -6; x <= 6; x++)
			for(int z = -6; z <= 6; z++)
				for(int y = -6; y <= 6; y++)
				{
					TileEntity temp = world.getTileEntity(new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z));
					if(temp != null && temp instanceof OLD_TileEntityCrystalRelay && !(x == 0 && y == 0 && z == 0))
						crystalLink.add((OLD_TileEntityCrystal) temp);
					else if(temp != null && temp instanceof OLD_TileEntityCrystal && !(x == 0 && y == 0 && z == 0))
						crystalLink2.add((OLD_TileEntityCrystal) temp);
				}
		
		if(!world.isRemote)
		{
			paricleCountDownEnd = 0;
			if(particleCountDown++ > paricleCountDownEnd)
			{
				for(OLD_TileEntityCrystal tile : crystalLink2)
				{
					if(tile.getBlockType() == AMBlocks.power_crystal && worldObj.getBlockState(tile.getPos().up().up()).getBlock() == Blocks.air)
					{
						if(getEnergy() > 0)
						{
							tile.addEnergy(getEnergy() - (-getEnergy()/200) );
							addEnergy(-getEnergy() - (-getEnergy()/200));
							worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), Reference.MODID + ":crystal.zap", .0125f, 1f);
							ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + .75, pos.getZ() +.5, tile.getPos().getX()+.5, tile.getPos().getY() + 1.5, tile.getPos().getZ() +.5, random.nextDouble() / 2 + .25, RGB.WHITE).setArcSize(.75f);
						}
					}
				}
				for(OLD_TileEntityCrystal tile : crystalLink)
				{
					if(getEnergy() > tile.getEnergy())
					{
						long test1 = getEnergy() - tile.getEnergy();

						if(getEnergy() == 1)
						{
							test1++;
						}
						addEnergy(-(test1 / 2));
						tile.addEnergy(test1 / 2);
						worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), Reference.MODID + ":crystal.zap", .0125f, 1f);
						ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + .75, pos.getZ() +.5, tile.getPos().getX()+.5, tile.getPos().getY() + .75, tile.getPos().getZ() +.5, random.nextDouble() / 2 + .25, RGB.ELECTRIC_BLUE);
						world.markBlockForUpdate(tile.getPos());
						tile.markDirty();
					}
				}
				particleCountDown = 0;
			}
			if(getEnergy() > getMaxStoredEnergy())
			{
				List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(3, 3, 3));
				if(!worldObj.isRemote)
				{
					for(EntityLivingBase entity : list)
					{
						if(entity.hurtResistantTime == 0 && !entity.isDead && !(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) && getDistance(pos, entity) < 3)
						{
								float multiplier = getEnergy() - getMaxStoredEnergy();
								multiplier /= 10f;
								worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), Reference.MODID + ":crystal.zap", 1f, 1f);
								ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + .75, pos.getZ() +.5, entity.posX, entity.posY + (entity.height), entity.posZ, .25d, RGB.BLUE);
								entity.attackEntityFrom(Reference.CustomDamageTypes.electrocute, multiplier);
								addEnergy(getMaxStoredEnergy() - getEnergy());
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
