package com.maelstrom.arcaneMechina.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import cofh.api.energy.EnergyStorage;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.tile.TileEntityArraySolar;
import com.maelstrom.arcaneMechina.common.tile.TileEntityGlyph;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCompressedArray extends ExtendableBlock implements ITileEntityProvider {

	private EnergyStorage storage;

	protected BlockCompressedArray(String local, EnergyStorage e) {
		super(Material.circuits, "array/"+local, Reference.MOD_ID);
		storage = e;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.03125F, 1.0F);
        this.setTickRandomly(true);
        this.bounds(0);
	}
	
	public TileEntity createNewTileEntity(World w, int meta) {
//		if(this == ModBlocks.solar){
//			return new TileEntityArraySolar(storage);	
//		}
//		if(this == ModBlocks.lunar){
//			return new TileEntityArraySolar();	
//		}
//		if(this == ModBlocks.wind){
//			return new TileEntityArraySolar();	
//		}
//		if(this == ModBlocks.geo){
//			return new TileEntityArraySolar();	
//		}
		return null;
	}
	

//    public void setBlockBoundsBasedOnState(IBlockAccess iba, int x, int y, int z)
//    {
//        int var5 = iba.getBlockMetadata(x, y, z);
//        this.function(var5);
//    }
//    private void function(int meta)
//    {
//        int var2 = meta & 7;
//        boolean var3 = (meta & 8) > 0;
//        float var4 = 0F;
//        float var5 = 1F;
//        float var7 = 0.025F;
//
//        if (var3)
//        {
//            var7 = 0.0625F;
//        }
//
//        if (var2 == 1)
//        {
//            this.setBlockBounds(0.0F, 0f, 0.0F, var7, 1f, 1F);
//        }
//        else if (var2 == 2)
//        {
//            this.setBlockBounds(1.0F - var7, 0f, 0F, 1.0F, 1f, 1F);
//        }
//        else if (var2 == 3)
//        {
//            this.setBlockBounds(0.0F, 0f, 0.0F, 1F, 1f, var7);
//        }
//        else if (var2 == 4)
//        {
//            this.setBlockBounds(0F, 0f, 1.0F - var7, 1F, 1f, 1.0F);
//        }
//    }
//    private boolean func_150044_m(World p_150044_1_, int p_150044_2_, int p_150044_3_, int p_150044_4_)
//    {
//        if (!this.canPlaceBlockAt(p_150044_1_, p_150044_2_, p_150044_3_, p_150044_4_))
//        {
//            p_150044_1_.setBlockToAir(p_150044_2_, p_150044_3_, p_150044_4_);
//            return false;
//        }
//        else
//        {
//            return true;
//        }
//    }
//    public void onNeighborBlockChange(World w, int x, int y, int z, Block block)
//    {
//        if (this.func_150044_m(w, x, y, z))
//        {
//            int var6 = w.getBlockMetadata(x, y, z) & 7;
//            boolean var7 = false;
//
//            if (!w.getBlock(x - 1, y, z).isNormalCube() && var6 == 1)
//            {
//                var7 = true;
//            }
//
//            if (!w.getBlock(x + 1, y, z).isNormalCube() && var6 == 2)
//            {
//                var7 = true;
//            }
//
//            if (!w.getBlock(x, y, z - 1).isNormalCube() && var6 == 3)
//            {
//                var7 = true;
//            }
//
//            if (!w.getBlock(x, y, z + 1).isNormalCube() && var6 == 4)
//            {
//                var7 = true;
//            }
//
//            if (var7)
//            {
//                this.dropBlockAsItem(w, x, y, z, w.getBlockMetadata(x, y, z), 0);
//                w.setBlockToAir(x, y, z);
//            }
//        }
//    }
//
//    public int onBlockPlaced(World w, int x, int y, int z, int side, float xf, float yf, float zf, int unknown)
//    {
//    	System.out.println(ForgeDirection.getOrientation(side));
//        int meta = w.getBlockMetadata(x, y, z);
//        int var11 = meta & 8;
//        meta &= 7;
//
//        if (side == 2 && w.getBlock(x, y, z + 1).isNormalCube())
//        {
//            meta = 4;
//        }
//        else if (side == 3 && w.getBlock(x, y, z - 1).isNormalCube())
//        {
//            meta = 3;
//        }
//        else if (side == 4 && w.getBlock(x + 1, y, z).isNormalCube())
//        {
//            meta = 2;
//        }
//        else if (side == 5 && w.getBlock(x - 1, y, z).isNormalCube())
//        {
//            meta = 1;
//        }
//        else
//        {
//            meta = this.func_150045_e(w, x, y, z);
//        }
//
//        return meta + var11;
//    }
    
    private int func_150045_e(World w, int x, int y, int z)
    {
        return w.getBlock(x - 1, y, z).isNormalCube() ? 1 : (w.getBlock(x + 1, y, z).isNormalCube() ? 2 : (w.getBlock(x, y, z - 1).isNormalCube() ? 3 : (w.getBlock(x, y, z + 1).isNormalCube() ? 4 : 1)));
    }
	
	
	
	
	
	
	
	
	//=================================================================================
	
	
	
	
	
	
	
    public int quantityDropped(Random r) {
        return 0;
    }
	
    public void setBlockBoundsForItemRender() {
        this.bounds(0);
    }
    
    private void bounds(int i) {
        byte b0 = 0;
        float f = (float)(1 * (1 + b0)) / 16.0F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean canPlaceBlockAt(World w, int x, int y, int z)
    {
        return super.canPlaceBlockAt(w, x, y, z) && this.canBlockStay(w, x, y, z);
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block block)
    {
        this.func_150090_e(w, x, y, z);
    }

    private boolean func_150090_e(World w, int x, int y, int z)
    {
        if (!this.canBlockStay(w, x, y, z)){
            w.setBlockToAir(x, y, z);
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean canBlockStay(World w, int x, int y, int z) {
        return w.getBlock(x, y - 1, z).isOpaqueCube();
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iblock, int x, int y, int z, int i) {
        return i == 1 ? true : super.shouldSideBeRendered(iblock, x, y, z, i);
    }

}
