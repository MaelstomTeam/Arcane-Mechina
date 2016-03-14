package com.maelstrom.armech.common.tileentity;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.snowcone.RGB;

public class OLD_TileEntityCrystal extends TileEntity implements ITickable {

	protected static Random random;
	public double rotation = 0d;
	public long storedEnergy = 0;
	public long maxStoredEnergy = 2048;
	public int loss = 1;
	public long getMaxStoredEnergy() {
		return maxStoredEnergy;
	}
	public void setMaxStoredEnergy(long maxStoredEnergy) {
		this.maxStoredEnergy = maxStoredEnergy;
	}
	public void setEnergy(long amount)
	{
		if(amount < 0)
			amount = 0;
		storedEnergy = amount;
	}
	public void addEnergy(long amount)
	{
		if(storedEnergy + amount < 0)
			storedEnergy = 0;
		else
			storedEnergy += amount;
	}
	public long getEnergy()
	{
		return storedEnergy;
	}
	
	@Override
	public void update()
	{
//		this.setMaxStoredEnergy(100000000);
		if(random == null)
		{
			random = new Random(worldObj.getSeed());
			rotation = random.nextDouble() * 60d;
		}
		worldObj.markBlockForUpdate(pos);
		List<EntityLivingBase> list = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX()+1, pos.getY()+1, pos.getZ()+1).expand(10, 12, 10));
		int hit = 0;
		if(!worldObj.isRemote)
		{
			for(EntityLivingBase entity : list)
			{
				if(!(entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) && getDistance(pos, entity) < 10 && entity.hurtResistantTime == 0 && !entity.isDead)
				{
					if(random.nextInt((16 / (worldObj.isRaining() && worldObj.canBlockSeeSky(entity.getPosition()) ? 4 : 1)) * list.size()) == 0)
					{
						float multiplier = ((float)storedEnergy / (float)maxStoredEnergy);
						if(multiplier > 1f)
							multiplier = 1f;
						worldObj.playSoundEffect(pos.getX(), pos.getY(), pos.getZ(), Reference.MODID + ":crystal.zap", 1f, 1f);
						ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + ((0d * 1.5) -.75) +.5, pos.getZ() +.5, entity.posX, entity.posY + (entity.height), entity.posZ, .25d, new RGB(random.nextFloat(), random.nextFloat(), random.nextFloat()));
						entity.attackEntityFrom(Reference.CustomDamageTypes.electrocute, (multiplier * 5f) / (worldObj.isRaining() ? 2 : 1) + 1); //increase damage as power goes up
						if(hit++ == 2)
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
		double y = (pos1.getY() + .5) - (pos2.posY + pos2.height);
		double z = (pos1.getZ() + .5) - pos2.posZ;
		double distance = Math.sqrt(x*x+y*y+z*z);
		return distance;
	}
	
	public double getDistance(BlockPos pos1, BlockPos pos2)
	{
		double x = (pos1.getX() + .5) - (pos2.getX() + .5);
		double y = (pos1.getY() + .5) - (pos2.getY() + .5);
		double z = (pos1.getZ() + .5) - (pos2.getZ() + .5);
		double distance = Math.sqrt(x*x+y*y+z*z);
		return distance;
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeToNBT(syncData);
		return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readNBT(packet.getNbtCompound());
	}
	
	public void readNBT(NBTTagCompound compound)
    {
		storedEnergy = compound.getInteger("storedEnergy");
    }

    public void writeNBT(NBTTagCompound compound)
    {
    	compound.setLong("storedEnergy", storedEnergy);
    }

	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
    	super.readFromNBT(compound);
    	readNBT(compound);
    }

	@Override
    public void writeToNBT(NBTTagCompound compound)
    {
    	super.writeToNBT(compound);
    	writeNBT(compound);
    }
}
