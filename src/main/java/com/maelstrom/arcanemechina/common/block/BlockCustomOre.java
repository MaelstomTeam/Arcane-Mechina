package com.maelstrom.arcanemechina.common.block;

import java.util.Random;

import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCustomOre extends Block implements IHasName {
	private static final String[] names = new String[] {"Copper", "Lead","Silver","Sapphire","Ruby", "Amethyst","WDiamond", "PDiamond", "YDiamond","Quartz"};
	private static final int subBlocks = names.length;
	
	private static final PropertyInteger meta = PropertyInteger.create("ore_id", 0, subBlocks);

	public BlockCustomOre(Material material) {
		super(material);
		setDefaultState(blockState.getBaseState().withProperty(meta, 0));
	}
	
	
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        
        if(this == BlockList.Ore) {
        	if(this.getMetaFromState(state) > 2)
        		drops.add(new ItemStack(ItemList.Gems, count, this.getMetaFromState(state)-3));
        	else
        	{
                Item item = this.getItemDropped(state, rand, fortune);
                if (item != Items.AIR)
                {
                    drops.add(new ItemStack(item, 1, this.damageDropped(state)));
                }
        	}
        }
        else
        {
            Item item = this.getItemDropped(state, rand, fortune);
            if (item != Items.AIR)
            {
                drops.add(new ItemStack(item, 1, this.damageDropped(state)));
            }
        }
        
    }
    
    public int quantityDropped(IBlockState state, int fortune, Random rand)
    {
    	int i = fortune > 0 ? rand.nextInt(fortune + 2) - 1 : 1;
		return i;
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
		if(meta < subBlocks && meta >= 0)
			return names[meta];
		else
			return "error";
	}
}
