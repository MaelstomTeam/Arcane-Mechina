package com.maelstrom.arcanemechina.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

public class BlockChalkGlyph extends ExtendableBlock {
	
	private IIcon[] icons;
	public BlockChalkGlyph() {
		super(Material.circuits, BlocksReference.glyphName, Reference.MOD_ID);
		this.setBlockBounds(0f, 0f, 0f, 1f, 0f, 1f);
	}
	
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
    	//if block cannot be placed
    	if(!this.canPlaceBlockAt(world, x, y, z))
    		//remove block
    		world.setBlockToAir(x, y, z);
    }
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
    	//if block top is solid or equal to glowstone then it can be placed
    	return world.getBlock(x, y-1, z).isSideSolid(world, x, y, z, ForgeDirection.UP) || world.getBlock(x,y-1,z).equals(Blocks.glowstone);
    }
	
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float sideXFloat, float sideYFloat, float sideZFloat)
    {
    	//if player is sneaking continue
    	if(player.getCurrentEquippedItem() != null)
    	{
	    	if(player.getCurrentEquippedItem().getItem() == ItemsReference.chalk)
	    	{
	    		//if meta is zero then change it to one/center block metadata else revert it to 0
	    		if(world.getBlockMetadata(x, y, z) == 0)
	    			world.setBlockMetadataWithNotify(x, y, z, 1, 3);
	    		else
	    			world.setBlockMetadataWithNotify(x, y, z, 0, 3);
				return true;
	    	}
	    	else
	    	{
		    	int centerX = 0;
		    	int centerZ = 0;
		    	boolean found = false;
		    	
		    	//run through a 9x9 area
		    	breakTheLoop : for(int x2 = -4; x2 < 4; x2++)
		        	for(int z2 = -4; z2 < 4; z2++)
		        		//if block is this and meta is equal to 1
		        		if(world.getBlock(x + x2, y, z + z2) == this && world.getBlockMetadata(x + x2, y, z + z2) == 1)
		        		{
		        			centerX = x + x2;
		        			centerZ = z + z2;
		        			found = true;
		        			break breakTheLoop;
		        		}
		    	//if array center specified and is valid then remove it and place a new center piece
		    	if(found && checkBeginingArray(world,centerX,y,centerZ))
		    	{
		    			removeBeginingArray(world, centerX, y, centerZ);
		    			return true;
		    	}
	    	}
    	}
        return false;
    }
    //register the two icons for this blocl
    public void registerBlockIcons(IIconRegister register)
    {
        icons = new IIcon[2];
        icons[0] = register.registerIcon(this.getTextureName());
        icons[1] = register.registerIcon(this.getTextureName()+"Center");
    }
    //return icon for meta and default
    public IIcon getIcon(int side, int meta)
    {
        switch(meta){
	        case 1 :
	        	return icons[1];
	        case 0: default:
	        	return icons[0];
        }
    }
    
    //===================================================================
    //==       Ignore functions bellow and just trust me on this       ==
    //===================================================================
    
    public boolean isOpaqueCube()
    {
        return false;
    }

    public boolean renderAsNormalBlock()
    {
        return false;
    }
    
    public Item getItemDropped(int metadata, Random random, int fortune)
    {
        return null;
    }
    
    private boolean isBlockThis(World world, int x, int y, int z)
    {
    	return world.getBlock(x, y, z) == this;
    }
    
    private void removeThis(World world, int x, int y, int z)
    {
    	if(world.getBlock(x, y, z) == this)
    		world.setBlockToAir(x, y, z);
    }
    
    private boolean checkBeginingArray(World world, int x, int y, int z)
    {
    	return isBlockThis(world,x+3,y,z) &&
		isBlockThis(world,x+3,y,z+1) &&
		isBlockThis(world,x+3,y,z-1) &&
		isBlockThis(world,x-3,y,z) &&
		isBlockThis(world,x-3,y,z+1) &&
		isBlockThis(world,x-3,y,z-1) &&
		isBlockThis(world,x-1,y,z+3) &&
		isBlockThis(world,x+1,y,z+3) &&
		isBlockThis(world,x,y,z+3) &&
		isBlockThis(world,x-1,y,z-3) &&
		isBlockThis(world,x+1,y,z-3) &&
		isBlockThis(world,x,y,z-3) &&
		isBlockThis(world,x-2,y,z-2) &&
		isBlockThis(world,x-2,y,z+2) &&
		isBlockThis(world,x+2,y,z+2) &&
		isBlockThis(world,x+2,y,z-2);
    }
    
    private void removeBeginingArray(World world, int x, int y, int z)
    {
		world.setBlock(x, y, z, BlocksReference.arrayBlock);
    	removeThis(world,x+3,y,z);
    	removeThis(world,x+3,y,z+1);
		removeThis(world,x+3,y,z-1);
		removeThis(world,x-3,y,z);
		removeThis(world,x-3,y,z+1);
		removeThis(world,x-3,y,z-1);
		removeThis(world,x-1,y,z+3);
		removeThis(world,x+1,y,z+3);
		removeThis(world,x,y,z+3);
		removeThis(world,x-1,y,z-3);
		removeThis(world,x+1,y,z-3);
		removeThis(world,x,y,z-3);
		removeThis(world,x-2,y,z-2);
		removeThis(world,x-2,y,z+2);
		removeThis(world,x+2,y,z+2);
		removeThis(world,x+2,y,z-2);
    }
    
}
