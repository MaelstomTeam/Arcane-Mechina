package com.maelstrom.armech.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.tileentity.TileEntityCrystalConduit;
import com.maelstrom.snowcone.api.energy.library.IEnergyBase;

public class BlockRelayCrystal extends Block implements ITileEntityProvider
{

	public BlockRelayCrystal() {
		super(Material.glass);
		setUnlocalizedName("crystal.relay");
		setCreativeTab(ArMechMain.tab_armech);
		setHardness(1f);
		setBlockBounds(.4f, 0f, .4f, .6f, 1f, .6f);
	}

	public boolean canBeReplacedByLeaves(IBlockAccess world, BlockPos pos)
	{
		return false;
	}
	
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCrystalConduit();
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing face, float x, float y, float z)
	{
		//do nothing
//		System.out.println(((TileEntityCrystalConduit)world.getTileEntity(pos)).getParent());
		if(!world.isRemote)
		{
			world.getTileEntity(pos).markDirty();
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.diamond)
				((IEnergyBase)world.getTileEntity(pos)).addEnergy(256);
			else if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.gold_ingot)
				((IEnergyBase)world.getTileEntity(pos)).setEnergy(0);
			else if(player.getHeldItem() == null && !world.isRemote)
				player.addChatComponentMessage(new ChatComponentText(((IEnergyBase)world.getTileEntity(pos)).getEnergy() + ""));
		}else
			world.getTileEntity(pos).markDirty();
		return false;
	}

}
