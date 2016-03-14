package com.maelstrom.armech.common.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.pleasesortthis.WorldAccess;
import com.maelstrom.armech.common.registry.AMBlocks;
import com.maelstrom.armech.common.registry.AMItems;
import com.maelstrom.snowcone.MathHelper;
import com.maelstrom.snowcone.RGB;
import com.maelstrom.snowcone.api.energy.library.IEnergyBase;

@SuppressWarnings("all")
public class TileEntityCrystalConduit extends TileEntity implements IEnergyBase, ITickable
{
	protected TileEntity parent = null;
	protected TileEntity child = null;
	protected BlockPos parentPos = null;
	protected BlockPos childPos = null;
	protected long storedEnergy = 0;
	protected long maxStoredEnergy = 0;
	public double rotation = 0;
	
	public TileEntityCrystalConduit()
	{
		super();
		setMaxEnergy(256);
	}
	
	@Override
	public void update()
	{
		if(worldObj.getBlockState(pos.up()).getBlock() == AMBlocks.creativeBlock)
		{
			worldObj.destroyBlock(pos.down(), true);
			worldObj.setBlockState(pos.down(), AMBlocks.dust_ore.getDefaultState());
		}
//		setEnergy(0);
		if(parentPos != null)
		{
			parent = worldObj.getTileEntity(parentPos);
			parentPos = null;
		}
		
		if(childPos != null)
		{
			child = worldObj.getTileEntity(childPos);
			childPos = null;
		}
		if(child != null)
		{
			if(child.isInvalid())// || !(child instanceof EnergyBase)
			{
				child = null;
			}
			else if(getEnergy() != ((IEnergyBase)child).getEnergy())
			{
				long test1 = getEnergy() + ((IEnergyBase)child).getEnergy();
				((IEnergyBase)child).setEnergy(test1/2);
				setEnergy(test1/2 + test1%2);
				if(worldObj.rand.nextInt(100) == 0)
				{
					addEnergy(-1);
					for(int i = 0; i < 5; i++)
						FMLClientHandler.instance().getClient().theWorld.spawnParticle(EnumParticleTypes.SPELL_INSTANT, pos.getX()+.5, pos.getY()+1, pos.getZ()+.5, pos.getX()+.5, pos.getY()+1, pos.getZ()+.5, new int[0]);
				}
				ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + 1, pos.getZ() +.5, child.getPos().getX() + .5, child.getPos().getY() + 1, child.getPos().getZ() + .5, MathHelper.getDistance(getPos(), child.getPos()) / 10d, RGB.ELECTRIC_BLUE);
				child.markDirty();
				this.markDirty();
			}
			else if(worldObj.rand.nextInt(64) == 0)
				ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + 1, pos.getZ() +.5, child.getPos().getX() + .5, child.getPos().getY() + .2, child.getPos().getZ() + .5, MathHelper.getDistance(getPos(), child.getPos()) / 10d, RGB.ELECTRIC_BLUE).setArcSize(.05f);
			
		}
		if(parent != null)
		{
			if(parent.isInvalid())
			{
				parent = null;
			}
			else if(getEnergy() != ((IEnergyBase)parent).getEnergy())
			{
				System.out.println(getEnergy() + ((IEnergyBase)parent).getEnergy());
				System.out.println(((IEnergyBase)parent).getEnergy());
				long test1 = getEnergy() + ((IEnergyBase)parent).getEnergy();
				((IEnergyBase)parent).setEnergy(test1/2);
				setEnergy(test1/2 + test1%2);
				if(worldObj.rand.nextInt(100) == 0)
				{
					addEnergy(-1);
					for(int i = 0; i < 5; i++)
						FMLClientHandler.instance().getClient().theWorld.spawnParticle(EnumParticleTypes.SPELL_INSTANT, pos.getX()+.5, pos.getY()+1, pos.getZ()+.5, pos.getX()+.5, pos.getY()+1, pos.getZ()+.5, new int[0]);
				}
				ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + 1, pos.getZ() +.5, parent.getPos().getX() + .5, parent.getPos().getY() + 1, parent.getPos().getZ() + .5, MathHelper.getDistance(getPos(), parent.getPos()) / 10d, RGB.ELECTRIC_BLUE);
				parent.markDirty();
				this.markDirty();
			}
		}
		if(worldObj.isRemote && child != null)
			if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null && Minecraft.getMinecraft().thePlayer.getHeldItem().getItem() == AMItems.help_book)
				ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY() + 1, pos.getZ() +.5, child.getPos().getX() + .5, child.getPos().getY() + .2, child.getPos().getZ() + .5, MathHelper.getDistance(getPos(), child.getPos()) / 10d, RGB.ELECTRIC_BLUE).setArcSize(.05f);

	}

	@Override
	public long getEnergy() {
		return storedEnergy;
	}
	@Override
	public void setEnergy(long amount)
	{
		storedEnergy = 0;
		addEnergy(amount);
	}
	@Override
	public long getMaxEnergy()
	{
		return maxStoredEnergy;
	}
	protected void setMaxEnergy(long amount)
	{
		maxStoredEnergy = amount;
	}
	@Override
	public void addEnergy(long amount)
	{
		if (amount + storedEnergy <= maxStoredEnergy)
			storedEnergy += amount;
		else if (amount + storedEnergy < 0)
			storedEnergy = 0;
		else
			storedEnergy = maxStoredEnergy;
	}

	@Override
	public TileEntity getParent()
	{
		return parent;
	}
	@Override
	public TileEntity getChild()
	{
		return child;
	}

	@Override
	public void addParent(TileEntity tile)
	{
			parent = tile;
	}
	@Override
	public void addChild(TileEntity tile)
	{
		child = tile;
	}
	

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		writeNBT(syncData);
		return new S35PacketUpdateTileEntity(this.pos, 1, syncData);
	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
	{
		readNBT(packet.getNbtCompound());
		if(worldObj.isRemote)
			worldObj.markBlockForUpdate(pos);
	}
	
	public void readNBT(NBTTagCompound compound)
    {
		storedEnergy = compound.getInteger("storedEnergy");
    	if(compound.hasKey("TileParent"))
    	{
    		int x,y,z;
    		x = compound.getCompoundTag("TileParent").getInteger("X");
    		y = compound.getCompoundTag("TileParent").getInteger("Y");
    		z = compound.getCompoundTag("TileParent").getInteger("Z");
    		parentPos = new BlockPos(x,y,z);
    	}
    	if(compound.hasKey("TileChild"))
    	{
    		int x,y,z;
    		x = compound.getCompoundTag("TileChild").getInteger("X");
    		y = compound.getCompoundTag("TileChild").getInteger("Y");
    		z = compound.getCompoundTag("TileChild").getInteger("Z");
    		childPos = new BlockPos(x,y,z);
    	}
    }

    public void writeNBT(NBTTagCompound compound)
    {
    	compound.setLong("storedEnergy", storedEnergy);
    	if(parent != null)
    	{
        	NBTTagCompound parentNBT = new NBTTagCompound();
	    	parentNBT.setInteger("X", parent.getPos().getX());
	    	parentNBT.setInteger("Y", parent.getPos().getY());
	    	parentNBT.setInteger("Z", parent.getPos().getZ());
	    	compound.setTag("TileParent", parentNBT);
    	}
    	if(child != null)
    	{
        	NBTTagCompound childNBT = new NBTTagCompound();
	    	childNBT.setInteger("X", child.getPos().getX());
	    	childNBT.setInteger("Y", child.getPos().getY());
	    	childNBT.setInteger("Z", child.getPos().getZ());
	    	compound.setTag("TileChild", childNBT);
    	}
    }

	@Override
	public void readFromNBT(NBTTagCompound compound)
    {
    	super.readFromNBT(compound);
    	readNBT(compound);
    	if(compound.hasKey("TileParent"))
    	{
    		int x,y,z;
    		x = compound.getCompoundTag("TileParent").getInteger("X");
    		y = compound.getCompoundTag("TileParent").getInteger("Y");
    		z = compound.getCompoundTag("TileParent").getInteger("Z");
    		parentPos = new BlockPos(x,y,z);
    	}
    	if(compound.hasKey("TileChild"))
    	{
    		int x,y,z;
    		x = compound.getCompoundTag("TileChild").getInteger("X");
    		y = compound.getCompoundTag("TileChild").getInteger("Y");
    		z = compound.getCompoundTag("TileChild").getInteger("Z");
    		childPos = new BlockPos(x,y,z);
    	}
    }

	@Override
    public void writeToNBT(NBTTagCompound compound)
    {
    	super.writeToNBT(compound);
    	writeNBT(compound);
    	if(parent != null)
    	{
        	NBTTagCompound parentNBT = new NBTTagCompound();
	    	parentNBT.setInteger("X", parent.getPos().getX());
	    	parentNBT.setInteger("Y", parent.getPos().getY());
	    	parentNBT.setInteger("Z", parent.getPos().getZ());
	    	compound.setTag("TileParent", parentNBT);
    	}
    	if(child != null)
    	{
        	NBTTagCompound childNBT = new NBTTagCompound();
	    	childNBT.setInteger("X", child.getPos().getX());
	    	childNBT.setInteger("Y", child.getPos().getY());
	    	childNBT.setInteger("Z", child.getPos().getZ());
	    	compound.setTag("TileChild", childNBT);
    	}
    }
}
