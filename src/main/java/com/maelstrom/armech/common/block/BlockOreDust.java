package com.maelstrom.armech.common.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.maelstrom.armech.common.reference.ModItems;
import com.maelstrom.armech.common.utils.CrystalDetails;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockOreDust extends BlockBase {

	public BlockOreDust() {
		super(Material.ROCK);
	}
	
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> ret = new ArrayList<ItemStack>();

        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        for(int i = 0; i < count; i++)
        {
            ret.add(CrystalDetails.writeRandom(new ItemStack(ModItems.dustCrystal, 1),rand));
        }
        return ret;
    }
}
