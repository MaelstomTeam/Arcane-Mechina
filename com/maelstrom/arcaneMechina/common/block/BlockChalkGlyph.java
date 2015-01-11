package com.maelstrom.arcaneMechina.common.block;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcaneMechina.common.init.InitBlock;
import com.maelstrom.arcaneMechina.common.init.InitItem;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.world.Structure;
import com.maelstrom.arcaneMechina.common.world.StructureRegistery;
import com.maelstrom.arcaneMechina.common.world.structure.Layer;
import com.maelstrom.arcaneMechina.common.world.structure.Row;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChalkGlyph extends ExtendableBlock {

	public BlockChalkGlyph(String local) {
		super(Material.circuits, local, Reference.MOD_ID);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.03125F, 1.0F);
        this.setTickRandomly(true);
        this.bounds(0);
	}
	
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int face, float xf, float yf, float zf) {
		if(w.isRemote){
			System.out.println("StructuresRegistered");
			for(Structure s : StructureRegistery.getList())
				s.printStructure();
		}
		if(this == InitBlock.glyphblank){
			if(ply.inventory.getCurrentItem() != null && ply.inventory.getCurrentItem().isItemEqual(new ItemStack(InitItem.gem, 1, 3))){
				Structure s = StructureRegistery.getStructuresByName("SmallArrayActivator")[0];
				if(!s.checkStructure(w, x, y, z, ForgeDirection.UNKNOWN, true))
					return false;
				w.setBlock(x,y,z,InitBlock.glyphCenter);
				ply.getCurrentEquippedItem().stackSize = ply.getCurrentEquippedItem().stackSize - 1;
				return true;
			}
		}
		else if(this == InitBlock.glyphCenter){
			Structure s = StructureRegistery.getStructuresByName("arrayCompression")[0];
			if(!s.checkStructure(w, x, y, z, ForgeDirection.NORTH, false))
				return false;
			return true;

		}
		return false;
	}
	
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
