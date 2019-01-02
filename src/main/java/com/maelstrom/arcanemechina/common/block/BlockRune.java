package com.maelstrom.arcanemechina.common.block;

import java.util.Random;

import com.maelstrom.arcanemechina.api.RuneTypes;
import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
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

public class BlockRune extends Block implements IHasName {
	
	public static PropertyEnum<RuneTypes> property_enum = PropertyEnum.create("rune_type", RuneTypes.class);

	int subBlocks = 1;
	String[] nameList;
	
    public BlockRune(String[] list) {
		super(Material.CLOTH);//, list.length, list
		setLightOpacity(0);
		setDefaultState(blockState.getBaseState().withProperty(property_enum, RuneTypes.RUNIC));
		nameList = list;
	}
    
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
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
    public int quantityDropped(Random random)
    {
        return 0;
    }
	
    ///////////////////////////////////////
    
     
    public int getMetaFromState(IBlockState state)
    {
        if (state.getPropertyKeys().isEmpty())
        {
            return 0;
        }
        else
        {
            return state.getValue(property_enum).getIndex();
        }
    }
	
    public IBlockState getStateFromMeta(int value)
    {
    	if(value < RuneTypes.values().length)
    		return this.getDefaultState().withProperty(property_enum, RuneTypes.values()[value]);
    	return this.getDefaultState().withProperty(property_enum, RuneTypes.RUNIC);
    }
    
    public int damageDropped(IBlockState state)
    {
    	return state.getValue(property_enum).getIndex();
    }
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {property_enum});
    }
	@Override
	public String getNameFromMeta(int meta)
	{
		if(meta < subBlocks && meta >= 0)
			return nameList[meta];
		else
			return "error";
	}
}
