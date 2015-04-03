package com.maelstrom.arcaneMechina.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.interfaces.IPower;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.tile.TileEntityFurnaceBasic;
import com.maelstrom.arcaneMechina.common.tile.TileEntityFurnaceBasicPower;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

public class BlockPowerFurnace extends ExtendableBlock implements ITileEntityProvider {
	
	public BlockPowerFurnace(String local) {
		super(Material.circuits, local, Reference.MOD_ID);
	}
	
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int side, float xf, float yf, float zf) {
    	switch(w.getBlockMetadata(x, y, z)){
	    	case 0: {
	    		if(w.isRemote)
	    		ply.addChatComponentMessage(new ChatComponentText("Furnace"));
	    		return true;
	    	}
	    	case 1 : {
	    		if(w.isRemote)
	    		ply.addChatComponentMessage(new ChatComponentText("Storage"));
	    		return true;
	    	}
    	}
        return false;
    }
//	public int getRenderType(){
//		return -1;
//	}
//	public boolean isOpaqueCube() {
//		return false;
//	}
//	public boolean renderAsNormalBlock() {
//		return false;
//	}
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		switch(meta){
			case 0: {new TileEntityFurnaceBasic(); break;}
			case 1: {new TileEntityFurnaceBasicPower();  break;}
		}
		//meta 0 = bottom aka furnace
		//meta 1 = storage
		return null;
	}
	
    public MapColor getMapColor(int p_149728_1_)
    {
        return MapColor.getMapColorForBlockColored(p_149728_1_);
    }
	

}
