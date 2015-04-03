package com.maelstrom.arcaneMechina.common.blocks;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.tile.TileEntityRFStorageArray;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cofh.api.energy.IEnergyStorage;

public class BlockRFStorageArray extends ExtendableBlock implements ITileEntityProvider {

	public BlockRFStorageArray(String local) {
		super(Material.rock, local, Reference.MOD_ID);
	}
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int face, float xf, float yf, float zf) {
		TileEntityRFStorageArray tile = (TileEntityRFStorageArray) w.getTileEntity(x, y, z);
		System.out.println(tile.getEnergyStored());
		return super.onBlockActivated(w, x, y, z, ply, face, xf, yf, zf);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEntityRFStorageArray();
	}

}
