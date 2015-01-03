package com.maelstrom.arcaneMechina.common.block;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

public class BlockResearchTable extends ExtendableBlock implements ITileEntityProvider {

	public BlockResearchTable(String local) {
		super(Material.wood, local, Reference.MOD_ID);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return null;
	}

}
