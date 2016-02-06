package com.maelstrom.armech.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChalk extends Block {

	public BlockChalk()
	{
		super(Material.circuits);
		this.setUnlocalizedName("block_chalk");
		this.setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.0104166666666667f,
				.9375f);
	}

	public int quantityDropped(Random random) {
		return 0;
	}

	public boolean canPlaceBlockOnSide(World world, BlockPos pos,
			EnumFacing side) {
		return canBlockStay(world, pos);
	}

	public boolean canBlockStay(World worldIn, BlockPos pos) {
		BlockPos down = pos.down();
		return !worldIn.isAirBlock(down)
				&& worldIn.isSideSolid(down, EnumFacing.UP);
	}

	public void onNeighborBlockChange(World worldIn, BlockPos pos,
			IBlockState state, Block neighborBlock) {
		if (!canBlockStay(worldIn, pos))
			worldIn.destroyBlock(pos, false);
	}

	public boolean isFullCube() {
		return false;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer() {
		return EnumWorldBlockLayer.CUTOUT;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		return super.onBlockActivated(worldIn, pos, state, playerIn, side, hitX, hitY, hitZ);
	}

	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		
		//THIS IS DEBUG STUFF!
		EntityItem ent = new EntityItem(world, pos.getX() + .5d, pos.getY(), pos.getZ() + .5d, new ItemStack(Items.cookie));
		ent.setVelocity(0, .35d, 0);
		ent.setDefaultPickupDelay();
		world.spawnEntityInWorld(ent);
		
	}

	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side) {
		return EnumFacing.DOWN == side;
	}

	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos,
			IBlockState state) {
		return null;
	}
}