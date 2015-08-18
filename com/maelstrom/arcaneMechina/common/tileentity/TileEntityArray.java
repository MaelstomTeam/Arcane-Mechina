package com.maelstrom.arcanemechina.common.tileentity;

import ibxm.Player;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.ItemsReference;

public class TileEntityArray extends TileEntity
{
	

	Block blck, air_;
	int centerX = 7, centerZ = 7;
	Block[][] array;
	

	boolean init = true;
	private void initialize()
	{
		if(init)
		{
			init = false;
			blck = BlocksReference.glyphBlock;
			air_ = Blocks.air;
			array =
				new Block[][]{
					{air_, air_, air_, air_, air_, blck, blck, blck, blck, blck, air_, air_, air_, air_, air_},
					{air_, air_, air_, blck, blck, air_, air_, air_, air_, air_, blck, blck, air_, air_, air_},
					{air_, air_, blck, air_, air_, air_, air_, air_, air_, air_, air_, air_, blck, air_, air_},
					{air_, blck, air_, blck, blck, blck, blck, blck, blck, blck, blck, blck, air_, blck, air_},
					{air_, blck, air_, blck, air_, air_, air_, blck, air_, air_, air_, blck, air_, blck, air_},
					{blck, air_, air_, blck, air_, air_, blck, air_, blck, air_, air_, blck, air_, air_, blck},
					{blck, air_, air_, blck, air_, blck, air_, air_, air_, blck, air_, blck, air_, air_, blck},
					{blck, air_, air_, blck, blck, air_, air_, BlocksReference.arrayBlock, air_, air_, blck, blck, air_, air_, blck},
					{blck, air_, air_, blck, air_, blck, air_, air_, air_, blck, air_, blck, air_, air_, blck},
					{blck, air_, air_, blck, air_, air_, blck, air_, blck, air_, air_, blck, air_, air_, blck},
					{air_, blck, air_, blck, air_, air_, air_, blck, air_, air_, air_, blck, air_, blck, air_},
					{air_, blck, air_, blck, blck, blck, blck, blck, blck, blck, blck, blck, air_, blck, air_},
					{air_, air_, blck, air_, air_, air_, air_, air_, air_, air_, air_, air_, blck, air_, air_},
					{air_, air_, air_, blck, blck, air_, air_, air_, air_, air_, blck, blck, air_, air_, air_},
					{air_, air_, air_, air_, air_, blck, blck, blck, blck, blck, air_, air_, air_, air_, air_},
				};
		}
	}

	
	
	public TileEntityArray()
	{
		initializeTile();
		initialize();
	}
	
	public void activateArray(World world)
	{
		if(!world.isRemote){
			ArrayList<ItemStack> amulet = new ArrayList();
				amulet.add(new ItemStack(ItemsReference.rings, 15, 2));
				amulet.add(new ItemStack(Items.feather, 60));
				amulet.add(new ItemStack(Items.quartz, 32));
				amulet.add(new ItemStack(Items.nether_star));
			
			List<EntityItem> entityItems = world.getEntitiesWithinAABB(EntityItem.class,  AxisAlignedBB.getBoundingBox(xCoord + .5, yCoord + .5, zCoord + .5, xCoord + .5, yCoord + .5, zCoord + .5).expand(4, 1, 4));
			
			ArrayList<ItemStack> worldItems = new ArrayList();
			
			for(EntityItem item : entityItems)
				worldItems.add(item.getEntityItem());
			boolean craftable = true;
			boolean shouldEnd = false;
			for(ItemStack item : worldItems)
			{
				shouldEnd = false;
				for(ItemStack crafting : amulet)
					if(!item.isItemEqual(crafting))
						shouldEnd = true;
				if(shouldEnd)
				{
					craftable = false;
					break;
				}
			}
			if(craftable)
				world.spawnEntityInWorld(new EntityItem(world, xCoord, yCoord, zCoord, new ItemStack(ItemsReference.amuletWing)));
	//		markDirty();
		}
	}
	
	boolean arrayComplete = true;
    public void updateEntity()
    {
    	arrayComplete = true;
		loop:for(int x2 = 0; x2 < array.length; x2++)
			for(int z2 = 0; z2 < array[x2].length; z2++)
				if(worldObj.getBlock(xCoord + x2 - centerX, yCoord, zCoord + z2 - centerZ) != array[x2][z2])
				{
					arrayComplete  = false;
					break loop;
				}
    	if(arrayComplete)
    	{
//    		activateArray(worldObj);
    	}
    }
    
	private void initializeTile()
	{
		
	}

    @Override
    public void readFromNBT(NBTTagCompound nbt)
    {
    	super.readFromNBT(nbt);
    	System.out.println(nbt);
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nbt)
    {
    	super.writeToNBT(nbt);
    	System.out.println(nbt);
    }
}
