package com.maelstrom.arcaneMechina.block;

import com.maelstrom.arcaneMechina.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockArraySmeltery extends Block implements ITileEntityProvider {

	public BlockArraySmeltery(Material material, String unlocal) {
		super(material);
		this.setBlockName(Reference.MOD_ID+"."+unlocal);
	}
	

    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int face, float xf, float yf, float zf)
    {
    	//open gui
        return false;
    }


	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		//tile entity for Array smeltery
		switch(meta){
		case 0: return null; //base array
		case 1: return null; // quarry/miner
		case 2: return null; // upgrade
		case 3: return null; // upgrade
		case 4: return null; // upgrade
		case 5: return null; // upgrade
		case 6: return null; // invisible
		default: return null;
		}
	}

}
