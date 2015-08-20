package com.maelstrom.arcanemechina.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityResearch;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockResearchStand extends ExtendableBlock implements ITileEntityProvider
{

	private Random rand = new Random();
	
	
	public BlockResearchStand()
	{
		super(Material.wood, BlocksReference.rStandName, Reference.MOD_ID);
	}
	@Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
    {
		onBlockDestroyedByPlayer(world, x, y, z, world.getBlockMetadata(x, y, z));
        return super.removedByPlayer(world, player, x, y, z, willHarvest);
	}
    public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int metaData)
    {
		int x3 = 0, z3 = 0;
		if(metaData != 0)
			for(int x2 = -1; x2 <= 1; x2++)
	    		for(int z2 = -1; z2 <= 1; z2++)
	    			if(world.getBlock(x + x2, y, z + z2) == this && world.getBlockMetadata(x + x2, y, z + z2) == 0)
	    			{
	    				x3 = x2;
	    				z3 = z2;
	    				break;
	    			}
		if(world.getTileEntity(x + x3, y, z + z3) != null && world.getTileEntity(x + x3, y, z + z3) instanceof TileEntityResearch)
		{
			TileEntityResearch tile = (TileEntityResearch) world.getTileEntity(x + x3, y, z + z3);
			for(int slot = 0; slot < tile.getSizeInventory(); slot++)
			{
				ItemStack itemStack = tile.getStackInSlot(slot);
				if(itemStack != null && !world.isRemote){
	                float secondX = rand.nextFloat() * 0.8F + 0.1F;
	                float secondY = rand.nextFloat() * 0.8F + 0.1F;
	                float secondZ = rand.nextFloat() * 0.8F + 0.1F;
	                EntityItem itemEntity = new EntityItem(world, x + secondX + x3, y + secondY, z + secondZ + z3, itemStack);
	                itemEntity.motionX = rand.nextGaussian() * .05f;
	                itemEntity.motionY = rand.nextGaussian() * .05f + .2f;
	                itemEntity.motionZ = rand.nextGaussian() * .05f;
	                world.spawnEntityInWorld(itemEntity);
				}
			}
		}
		for(int x2 = -1; x2 <= 1; x2++)
    		for(int z2 = -1; z2 <= 1; z2++)
    			world.setBlock(x + x2 + x3, y, z + z2 + z3, Blocks.stonebrick);
		if(!world.isRemote)
			world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(world.getBlock(x, y, z), 1, world.getBlockMetadata(x, y, z))));
		world.setBlockToAir(x, y, z);
    }
    
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float xFloat, float yFloat, float zFloat)
    {
    	if(player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemBlock)
    		return false;
    	if(world.getBlockMetadata(x, y, z) == 0 && yFloat > .875f) //.9375 top 1 pixel current is top 2 pixels
    	{
    		//open gui
    		return true;
    	}
    	else if(world.getBlockMetadata(x, y, z) != 0)
    	{

    		int offsetX = 0, offsetZ = 0;
    		for(int x2 = -1; x2 <= 1; x2++)
        		for(int z2 = -1; z2 <= 1; z2++)
        			if(world.getBlock(x + x2, y, z + z2) == this && world.getBlockMetadata(x + x2, y, z + z2) == 0)
        			{
        				offsetX = x2;
        				offsetZ = z2;
        				break;
        			}
    		return onBlockActivated(world, x + offsetX, y, z + offsetZ, player, side, xFloat, yFloat, zFloat);
    	}
		return false;
    }
    
	@Override
	public TileEntity createNewTileEntity(World world, int metaData) {
		return metaData == 0 ? new TileEntityResearch() : null;
	}

    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return ItemBlock.getItemFromBlock(Blocks.stonebrick);
    }
    
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
    {
        return new ItemStack(Blocks.stonebrick);
    }
    
    public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
    {
		int x3 = 0, z3 = 0, metaData = world.getBlockMetadata(x, y, z);
		if(metaData != 0)
			for(int x2 = -1; x2 <= 1; x2++)
	    		for(int z2 = -1; z2 <= 1; z2++)
	    			if(world.getBlock(x + x2, y, z + z2) == this && world.getBlockMetadata(x + x2, y, z + z2) == 0)
	    			{
	    				x3 = x2;
	    				z3 = z2;
	    				break;
	    			}
		if(world.getTileEntity(x + x3, y, z + z3) != null && world.getTileEntity(x + x3, y, z + z3) instanceof TileEntityResearch)
		{
			TileEntityResearch tile = (TileEntityResearch) world.getTileEntity(x + x3, y, z + z3);
			for(int slot = 0; slot < tile.getSizeInventory(); slot++)
			{
				ItemStack itemStack = tile.getStackInSlot(slot);
				if(itemStack != null && !world.isRemote){
	                float secondX = rand.nextFloat() * 0.8F + 0.1F;
	                float secondY = rand.nextFloat() * 0.8F + 0.1F;
	                float secondZ = rand.nextFloat() * 0.8F + 0.1F;
	                EntityItem itemEntity = new EntityItem(world, x + secondX + x3, y + secondY, z + secondZ + z3, itemStack);
	                itemEntity.motionX = rand.nextGaussian() * .05f;
	                itemEntity.motionY = rand.nextGaussian() * .05f + .2f;
	                itemEntity.motionZ = rand.nextGaussian() * .05f;
	                world.spawnEntityInWorld(itemEntity);
				}
			}
		}

		for(int x2 = -1; x2 <= 1; x2++)
    		for(int z2 = -1; z2 <= 1; z2++)
    			world.setBlock(x + x2 + x3, y, z + z2 + z3, Blocks.stonebrick);
		
        Blocks.stonebrick.onBlockDestroyedByExplosion(world, x, y, z, explosion);
    }
    
    public boolean isOpaqueCube()
    {
    	return false;
    }
    
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int i, int i2, int i3, int i4)
    {
    	return true;
    }
    
    
    
    
    
    private IIcon TTL, TTC, TTR, TCL, TCC, TCR, TBL, TBC, TBR;
    private IIcon SL, SC, SR;
    private IIcon bottom;

    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side)
    {
    	if(ForgeDirection.getOrientation(side).equals(ForgeDirection.DOWN))
            return bottom;
    	if(world.getBlock(x, y, z + 1) == this && world.getBlock(x, y, z - 1) == this && world.getBlock(x + 1, y, z) == this && world.getBlock(x - 1, y, z) == this)
    	{
    		if(ForgeDirection.getOrientation(side) == ForgeDirection.UP)
    			return TCC;
    	}
    	else
    	{
    		if(ForgeDirection.getOrientation(side) != ForgeDirection.DOWN)
    		{
    			if(world.getBlock(x + 1, y, z) == this && world.getBlock(x - 1, y, z) == this)
    			{
    				if(ForgeDirection.getOrientation(side) == ForgeDirection.UP)
    				{
    					if(world.getBlock(x, y, z +1) == this)
    						return TTC;
    					else
    						return TBC;
    				}
					else
						return SC;
    			}
    			else if(world.getBlock(x, y, z + 1) == this && world.getBlock(x, y, z - 1) == this)
    			{
    				if(ForgeDirection.getOrientation(side) == ForgeDirection.UP)
    				{
    					if(world.getBlock(x + 1, y, z) == this)
    						return TCL;
    					else
    						return TCR;
    				}
					else
						return SC;
    			}
    			else if((world.getBlock(x, y, z + 1) == this && world.getBlock(x + 1, y, z) == this) || (world.getBlock(x, y, z - 1) == this && world.getBlock(x - 1, y, z) == this))
    			{
    				if(ForgeDirection.getOrientation(side).equals(ForgeDirection.NORTH))
    					return SR;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.WEST))
    					return SL;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.EAST))
    					return SL;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.UP) && (world.getBlock(x, y, z - 1) == this && world.getBlock(x - 1, y, z) == this))
    					return TBR;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.UP))
    					return TTL;
    				else
    					return SR;
    			}
    			else if((world.getBlock(x, y, z - 1) == this && world.getBlock(x + 1, y, z) == this) || (world.getBlock(x, y, z + 1) == this && world.getBlock(x - 1, y, z) == this))
    			{
    				if(ForgeDirection.getOrientation(side).equals(ForgeDirection.NORTH))
    					return SL;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.WEST))
    					return SR;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.EAST))
    					return SR;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.UP) && (world.getBlock(x, y, z + 1) == this && world.getBlock(x - 1, y, z) == this))
    					return TTR;
    				else if(ForgeDirection.getOrientation(side).equals(ForgeDirection.UP))
    					return TBL;
					else
    					return SL;
    			}
    		}
    	}
        return bottom;
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister registery)
    {
    	TTL = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TTL");
    	TTC = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TTC");
    	TTR = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TTR");
    	TCL = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TCL");
    	TCC = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TCC");
    	TCR = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TCR");
    	TBL = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TBL");
    	TBC = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TBC");
    	TBR = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "TBR");
    	SL = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "SL");
    	SC = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "SC");
    	SR = registery.registerIcon(Reference.MOD_ID + ":rTable/" + "SR");
    	bottom = registery.registerIcon("minecraft:planks_oak");
    }
    
    
}