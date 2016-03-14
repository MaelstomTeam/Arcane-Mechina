package com.maelstrom.armech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.tileentity.TileEntityCreativeHelper;

public class BlockCreativeHelper extends Block implements ITileEntityProvider
{

	public BlockCreativeHelper()
	{
		super(Material.iron);
		setUnlocalizedName("lightning_rod");
		setCreativeTab(ArMechMain.tab_armech);
	}

	@Override
	public TileEntity createNewTileEntity(World arg0, int arg1)
	{
		return new TileEntityCreativeHelper();
	}

}
