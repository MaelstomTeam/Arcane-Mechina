package com.maelstrom.arcanemechina.common.block;

import java.util.Random;

import com.maelstrom.arcanemechina.client.BasicDustColorHandler;
import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.snowcone.block.IItemColored;
import com.maelstrom.snowcone.block.MetaBlock;
import com.maelstrom.snowcone.client.BasicColorHandler;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockColoredMeta extends MetaBlock  implements IItemColored, IBlockColor{

	public BlockColoredMeta(Material material, int subs, String[] list) {
		super(material, subs, list);
	}

	public BlockColoredMeta(Material material, String[] list) {
		super(material, list.length, list);
	}
	
	
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        Random rand = world instanceof World ? ((World)world).rand : RANDOM;

        int count = quantityDropped(state, fortune, rand);
        
        if(this == BlockList.CrystalOre) {
        	drops.add(new ItemStack(ItemList.Crystal, count, this.getMetaFromState(state)));
        }
        
    }
    
    public int quantityDropped(IBlockState state, int fortune, Random rand)
    {
    	int i = fortune > 0 ? rand.nextInt(fortune + 2) - 1 : 1;
    	if(this == BlockList.CrystalOre)
    		return i;
    	return 1;
    }


    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
	public BasicColorHandler getColorHandler()
	{
		return new BasicDustColorHandler();
	}

	@Override
	public int colorMultiplier(IBlockState state, IBlockAccess worldIn, BlockPos pos, int tintIndex) {
		return getColorHandler().colorMultiplier(this.getMetaFromState(state), tintIndex);
	}
}
