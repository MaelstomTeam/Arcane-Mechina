package com.maelstrom.arcanemechina.common.block;

import com.maelstrom.arcanemechina.common.items.ItemList;

import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockCustomLog extends BlockLog {
	public BlockCustomLog()
	{
		super();
		this.setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, EnumAxis.Y));
	}
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn)
    {
    }
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(this == BlockList.paperLog && playerIn.getHeldItem(hand).isEmpty())
    	{
    		if(!worldIn.isRemote)
    			if(worldIn.rand.nextInt(5) == 0)
    				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX()+hitX, pos.getY()+hitY, pos.getZ()+hitZ, new ItemStack(ItemList.Drops,1,1)));
    			else if(worldIn.rand.nextInt(3) == 0)
    				worldIn.spawnEntity(new EntityItem(worldIn, pos.getX()+hitX, pos.getY()+hitY, pos.getZ()+hitZ, new ItemStack(Items.STICK,worldIn.rand.nextInt(3))));
    		worldIn.setBlockState(pos, BlockList.paperLogDebarked.getStateFromMeta(getMetaFromState(state)));
    		return true;
    	}
    	else
    		return false;
    }
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState();

        switch (meta & 0b1100)
        {
            case 0b0000:
                state = state.withProperty(LOG_AXIS, EnumAxis.Y);
                break;

            case 0b0100:
                state = state.withProperty(LOG_AXIS, EnumAxis.X);
                break;

            case 0b1000:
                state = state.withProperty(LOG_AXIS, EnumAxis.Z);
                break;

            case 0b1100:
                state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
                break;
        }

        return state;
    }
    public int getMetaFromState(IBlockState state)
    {
        switch ((EnumAxis)state.getValue(LOG_AXIS))
        {
            case X: return 0b0100;
            case Y: return 0b0000;
            case Z: return 0b1000;
            case NONE: return 0b1100;
            default: return 0;
        }
    }
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {LOG_AXIS});
    }

    
    @SuppressWarnings("deprecation")
	public boolean isOpaqueCube(IBlockState state)
    {
    	if(this == BlockList.paperLogDebarked)
    		return false;
        return super.isOpaqueCube(state);
    }
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
    	if(this == BlockList.paperLogDebarked)
    		return false;
        return super.isFullCube(state);
    }
}
