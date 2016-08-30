package com.maelstrom.armech.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public abstract class BlockBase extends Block {

	public BlockBase(Material materialIn) {
		super(materialIn);
		this.setHardness(3f);
	}

}
