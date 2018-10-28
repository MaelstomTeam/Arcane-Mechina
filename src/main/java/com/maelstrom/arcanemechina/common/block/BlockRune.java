package com.maelstrom.arcanemechina.common.block;

import com.maelstrom.snowcone.block.MetaBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRune extends MetaBlock {

    public BlockRune(String[] list) {
		super(Material.CLOTH, list.length, list);
		setLightOpacity(0);
	}
    
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
	
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);
	}
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(world.getTileEntity(pos) != null)
    	{
    		
    	}
        return false;
    }
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
    {
        return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP) && worldIn.getBlockState(pos).getBlock().isReplaceable(worldIn, pos);
    }
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
    	if(worldIn.getTileEntity(pos) != null)
    	{
    		worldIn.removeTileEntity(pos);
    	}
    }
}
