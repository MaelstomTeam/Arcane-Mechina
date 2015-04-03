package com.maelstrom.arcaneMechina.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

/**
 * @author Hybolic
 * this class needs to be reviewed and rewritten for a later date!
 *
 */
public class BlockAllSideArray extends ExtendableBlock {

	protected BlockAllSideArray(String local) {
		super(Material.circuits, "array/"+local, Reference.MOD_ID);
        this.setTickRandomly(true);
	}
	
	public boolean isOpaqueCube() {
		return false;
	}
	
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean isNormalCube() {
    	return false;
    }
    
    /*
     * fix this function to check meta and the block for correct placement,
     * if block is placeable return true otherwise return false
     * should stop the block being placed on none solid blocks
     */
    public boolean canPlaceBlockAt(World w, int x, int y, int z) {
        return true;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxfromPool(World w, int x, int y, int z) {
        return null;
    }
    
    public int onBlockPlaced(World w, int x, int y, int z, int side, float xf, float yf, float zf, int i) {
    	System.out.println(ForgeDirection.getOrientation(side) + " " + side);
        int meta = w.getBlockMetadata(x, y, z);
        int var11 = meta & 8;
        meta &= 7;
        switch(ForgeDirection.getOrientation(side)){
		case DOWN:{
			if(w.getBlock(x, y + 1, z).isNormalCube()){
				meta = side;
				break;
			}
		}
		case EAST:{
			if(w.getBlock(x - 1, y, z).isNormalCube()){
				meta = side;
				break;
			}
		}
		case NORTH:{
			if(w.getBlock(x, y, z + 1).isNormalCube()){
				meta = side;
				break;
			}
		}
		case SOUTH:{
			if(w.getBlock(x, y, z - 1).isNormalCube()){
				meta = side;
				break;
			}
		}
		case UP:{
			if(w.getBlock(x, y - 1, z).isNormalCube()){
				meta = side;
				break;
			}
		}
		case WEST:{
			if(w.getBlock(x + 1, y, z).isNormalCube()){
				meta = side;
				break;
			}
		}
		default:{
			meta = 6;
            break;
        }
        }
        return meta + var11;
    }
    
    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z) {
        int meta = iba.getBlockMetadata(x, y, z);
        this.setBlockBoundsBasedOnMeta(meta);
    }
    
    private void setBlockBoundsBasedOnMeta(int meta)
    {
        int var2 = meta & 7;
        boolean var3 = (meta & 8) > 0;
        float size = 0.025f;

        if (var3)
        {
            size = 0.025f;
        }
        switch(ForgeDirection.getOrientation(meta)){
		case DOWN:{
        	this.setBlockBounds(0, 1 - size, 0, 1, 1, 1);
			break;
		}
		case EAST:{
            this.setBlockBounds(0, 0, 0, size, 1, 1);
			break;
		}
		case NORTH:{
            this.setBlockBounds(0, 0, 1 - size, 1, 1, 1);
			break;
		}
		case SOUTH:{
            this.setBlockBounds(0, 0, 0, 1, 1, size);
			break;
		}
		case UP:{
        	this.setBlockBounds(0, 0, 0, 1, size, 1);
			break;
		}
		case WEST:{
            this.setBlockBounds(1 - size, 0, 0, 1, 1, 1);
			break;
		}
		default:
			break;
        }
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block block) {
        int meta = w.getBlockMetadata(x, y, z) & 7;
        boolean removeBlock = false;

        switch(ForgeDirection.getOrientation(meta)){
		case DOWN:{
			if(!w.getBlock(x, y + 1, z).isNormalCube())
				removeBlock = true;
			break;
		}
		case EAST:{
			if(!w.getBlock(x - 1, y, z).isNormalCube())
				removeBlock = true;
			break;
		}
		case NORTH:{
			if(!w.getBlock(x, y, z + 1).isNormalCube())
				removeBlock = true;
			break;
		}
		case SOUTH:{
			if(!w.getBlock(x, y, z - 1).isNormalCube())
				removeBlock = true;
			break;
		}
		case UP:{
			if(!w.getBlock(x, y - 1, z).isNormalCube())
				removeBlock = true;
			break;
		}
		case WEST:{
			if(!w.getBlock(x + 1, y, z).isNormalCube())
				removeBlock = true;
			break;
		}
		default:{
			removeBlock = true;
			break;
		}
        }

        if (removeBlock)
            w.setBlockToAir(x, y, z);
    }
}