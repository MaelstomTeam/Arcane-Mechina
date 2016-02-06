package com.maelstrom.armech.common.blocks;

import java.awt.Color;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.common.tileentity.TileEntityPurifier;

public class BlockTest extends Block implements ITileEntityProvider {
	public BlockTest(Material mat) {
		super(mat);
		this.fullBlock = false;
//		this.translucent = true;
		this.setBlockBounds(0f, 0f, 0f, 1f, (1f / 16) * 11, 1f);
        this.setLightOpacity(255);
	}
	
	public boolean isFullCube()
	{
		return false;
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer()
    {
        return EnumWorldBlockLayer.CUTOUT;
    }
    
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		return getGlassColorFromTile(world.getTileEntity(pos),renderPass).hashCode();
	}
	
	public Color getGlassColorFromTile(TileEntity tile, int renderPass)
	{
		if(tile != null && tile instanceof TileEntityPurifier)
			if(((TileEntityPurifier) tile).getJarID(renderPass) == 0)
				return Color.YELLOW;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 1)
				return Color.GREEN;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 2)
				return Color.RED;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 3)
				return Color.BLUE;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 4)
				return Color.BLACK;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 5)
				return Color.WHITE;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 6)
				return new Color(255,255,144);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 7)
				return new Color(144,255,144);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 8)
				return new Color(255,104,104);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 9)
				return new Color(144,144,255);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 10)
				return new Color(144,144,0);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 11)
				return new Color(0,144,0);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 12)
				return new Color(144,0,0);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 13)
				return new Color(0,0,144);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 14)
				return Color.PINK;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 15)
				return new Color(70,95,10);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 16)
				return Color.ORANGE;
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 17)
				return new Color(192,192,192);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 18)
				return new Color(255,69,0);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 19)
			{
				if(new Random().nextInt(10) >= 5)
					return Color.GREEN;
				else
					return new Color(85,205,47);
			}
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 20)
				return new Color(220,20,60);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 21)
				return new Color(0,100,0);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 22)
				return new Color(220,20,60);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 23)
				return new Color(125, 249, 255);
			else if(((TileEntityPurifier) tile).getJarID(renderPass) == 24)
				return new Color(8, 232, 222);
		return Color.WHITE;
		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityPurifier();
	}
	

    public boolean hasComparatorInputOverride()
    {
        return true;
    }
    
    public int getComparatorInputOverride(World worldIn, BlockPos pos)
    {
    	if(worldIn.getTileEntity(pos) == null)
    		return 15;
        return ((TileEntityPurifier)worldIn.getTileEntity(pos)).getJarCount();
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
    {
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (tileentity instanceof IInventory)
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
        worldIn.updateComparatorOutputLevel(pos, this);

        super.breakBlock(worldIn, pos, state);
    }
    
    public boolean hasTileEntity(IBlockState state)
    {
    	return true;
    }
}