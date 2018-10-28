package com.maelstrom.snowcone.block;

import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

/**
 * something is wrong with meta when placing in world, needs fixing
 */
//@Deprecated
public class MetaBlock extends Block implements IHasName {
	
	public int getSubCount()
	{
		return subBlocks;
	}
	
	int subBlocks = 1;

	public static final PropertyInteger meta = PropertyInteger.create("meta", 0, 15);

	String[] nameList;
	
	public MetaBlock(Material material, int subs) {
		super(material);
		subBlocks = subs;
		setDefaultState(blockState.getBaseState().withProperty(meta, 0));
		nameList = new String[] {"UNNAMED"};
	}
	
	public MetaBlock(Material material, int subs, String[] list) {
		this(material,subs);
		nameList = list;
	}
	
    public int getMetaFromState(IBlockState state)
    {
        if (state.getPropertyKeys().isEmpty())
        {
            return 0;
        }
        else
        {
            return state.getValue(meta).intValue();
        }
    }
	
    public IBlockState getStateFromMeta(int value)
    {
    	return this.getDefaultState().withProperty(meta, value);
    }
    
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
			for(int i = 0; i < subBlocks; i++)
				items.add(new ItemStack(this, 1, i));
	}

    public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, net.minecraftforge.common.IPlantable plantable)
    {
    	return getMetaFromState(state) == 1;
    }

	//fix this
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {meta});
    }
    
    public int damageDropped(IBlockState state)
    {
    	return state.getValue(meta).intValue();
    }

	@Override
	public String getNameFromMeta(int meta)
	{
		if(meta <= subBlocks - 1)
			return nameList[meta];
		else
			return "error";
	}
    
}
