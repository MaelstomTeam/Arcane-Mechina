package com.maelstrom.arcanemechina.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityArray;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

public class BlockArray extends ExtendableBlock implements ITileEntityProvider
{
	
	public BlockArray()
	{
		super(Material.circuits, BlocksReference.arrayName, Reference.MOD_ID);
		this.setBlockBounds(0f, 0f, 0f, 1f, 0f, 1f);
	}
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float sideXFloat, float sideYFloat, float sideZFloat)
    {
    	return super.onBlockActivated(world, x, y, z, player, side, sideXFloat, sideYFloat, sideZFloat);
    }
    
    //tile entity
	@Override
	public TileEntity createNewTileEntity(World world, int metaValue)
	{
		TileEntity tile = new TileEntityArray();
		return tile;
	}
	
	//render stuff
    public boolean isOpaqueCube()
    {
        return false;
    }
	
    //render stuff
    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
    	//return no drops on block break
        return null;
    }
	
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	//if block cannot be placed
    	if(!this.canPlaceBlockAt(world, x, y, z))
    		//remove block
    		world.setBlockToAir(x, y, z);
    }
    public void breakBlock(World world, int x, int y, int z, Block block, int meta___maybe)
    {
//    	if(world.getTileEntity(x, y, z) instanceof TileEntityArray)
//    		((TileEntityArray)world.getTileEntity(x, y, z)).destroyStructure();
    	super.breakBlock(world, x, y, z, block, meta___maybe);
    }
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
    	//if block top is solid or equal to glowstone then it can be placed
    	return world.getBlock(x, y-1, z).isSideSolid(world, x, y, z, ForgeDirection.UP) || world.getBlock(x,y-1,z).equals(Blocks.glowstone);
    }
}
