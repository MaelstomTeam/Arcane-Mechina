package com.maelstrom.arcanemechina.common.block;

import java.util.Random;

import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.snowcone.block.MetaBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOre extends MetaBlock {

	public BlockOre(Material material, int subs, String[] list) {
		super(material, subs, list);
	}

	public BlockOre(Material material, String[] list) {
		super(material, list.length, list);
	}
	
	
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        
        if(this == BlockList.CrystalOre) {
        	drops.add(new ItemStack(ItemList.Crystal, count, this.getMetaFromState(state)));
        }
        else if(this == BlockList.Ore) {
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
    	if(this == BlockList.CrystalOre)
    		return i;
    	else if(this == BlockList.Ore && this.getMetaFromState(state) > 2)
    		return i;
    	return 1;
    }
}
