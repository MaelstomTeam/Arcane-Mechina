package com.maelstrom.armech.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.AMBlocks;
import com.maelstrom.armech.common.WorldAccess;
import com.maelstrom.snowcone.RGB;

public class BlockRuneCenter extends Block
{

	public BlockRuneCenter()
	{
		super(Material.rock);
		this.setUnlocalizedName("rune_block");
		this.setBlockBounds(0.25f, 0.0f, 0.25f, 0.75f, 0.25f, .75f);
	}

	public int quantityDropped(Random random)
	{
		return 0;
	}

	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side)
	{
		return canBlockStay(world, pos);
	}

	public boolean canBlockStay(World worldIn, BlockPos pos)
	{
		return !worldIn.isAirBlock(pos.down()) && worldIn.isSideSolid(pos.down(), EnumFacing.UP);
	}

	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		if (!canBlockStay(worldIn, pos))
			worldIn.destroyBlock(pos, false);
	}

	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT;
	}

	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		this.onBlockClicked(worldIn, pos, playerIn);
		return !playerIn.isSneaking();
	}
	
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		boolean level1 = false, level2 = false, level3 = false;
		if(getIsChalk(world, pos.east().east()) &&
			getIsChalk(world, pos.east().east().north()) &&
			getIsChalk(world, pos.east().east().south()) &&
			getIsChalk(world, pos.west().west()) &&
			getIsChalk(world, pos.west().west().north()) &&
			getIsChalk(world, pos.west().west().south()) &&
			getIsChalk(world, pos.north().north()) &&
			getIsChalk(world, pos.north().north().east()) &&
			getIsChalk(world, pos.north().north().west()) &&
			getIsChalk(world, pos.south().south()) &&
			getIsChalk(world, pos.south().south().east()) &&
			getIsChalk(world, pos.south().south().west()))
		{
			level1 = true;
		}
		if(getIsChalk(world, pos.east().east().east().east().east().east()) &&
			getIsChalk(world, pos.east().east().east().east().east()) &&
			getIsChalk(world, pos.east().east().east()) &&
			getIsChalk(world, pos.east().east().east().east().east().east().north()) &&
			getIsChalk(world, pos.east().east().east().east().east().east().south()) &&
			getIsChalk(world, pos.west().west().west().west().west().west()) &&
			getIsChalk(world, pos.west().west().west().west().west()) &&
			getIsChalk(world, pos.west().west().west()) &&
			getIsChalk(world, pos.west().west().west().west().west().west().north()) &&
			getIsChalk(world, pos.west().west().west().west().west().west().south()) &&
			getIsChalk(world, pos.north().north().north().north().north().north()) &&
			getIsChalk(world, pos.north().north().north().north().north()) &&
			getIsChalk(world, pos.north().north().north()) &&
			getIsChalk(world, pos.north().north().north().north().north().north().east()) &&
			getIsChalk(world, pos.north().north().north().north().north().north().west()) &&
			getIsChalk(world, pos.south().south().south().south().south().south()) &&
			getIsChalk(world, pos.south().south().south().south().south()) &&
			getIsChalk(world, pos.south().south().south()) &&
			getIsChalk(world, pos.south().south().south().south().south().south().east()) &&
			getIsChalk(world, pos.south().south().south().south().south().south().west()) &&
			getIsChalk(world, pos.south().south().south().east().east().east()) &&
			getIsChalk(world, pos.south().south().south().east().east().east().east().east()) &&
			getIsChalk(world, pos.south().south().south().east().east().east().east().south()) &&
			getIsChalk(world, pos.south().south().south().east().east().east().south().south())&&
			getIsChalk(world, pos.south().south().south().west().west().west()) &&
			getIsChalk(world, pos.south().south().south().west().west().west().west().west()) &&
			getIsChalk(world, pos.south().south().south().west().west().west().west().south()) &&
			getIsChalk(world, pos.south().south().south().west().west().west().south().south()) &&
			getIsChalk(world, pos.north().north().north().east().east().east()) &&
			getIsChalk(world, pos.north().north().north().east().east().east().east().east()) &&
			getIsChalk(world, pos.north().north().north().east().east().east().east().north()) &&
			getIsChalk(world, pos.north().north().north().east().east().east().north().north())&&
			getIsChalk(world, pos.north().north().north().west().west().west()) &&
			getIsChalk(world, pos.north().north().north().west().west().west().west().west()) &&
			getIsChalk(world, pos.north().north().north().west().west().west().west().north()) &&
			getIsChalk(world, pos.north().north().north().west().west().west().north().north()))
		{
			level2 = true;
		}
		if(WorldAccess.isBlockEqual(world, pos.north().north().north().north().north().east().east(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.north().north().north().north().north().west().west(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.south().south().south().south().south().east().east(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.south().south().south().south().south().west().west(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.east().east().east().east().east().south().south(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.east().east().east().east().east().north().north(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.west().west().west().west().west().south().south(), AMBlocks.rune_block) &&
				WorldAccess.isBlockEqual(world, pos.west().west().west().west().west().north().north(), AMBlocks.rune_block))
		{
			level3 = true;
		}
		if(!world.isRemote)
			if(level1)
			{
				List<EntityItem> itemList = WorldAccess.getItemsInWorld(world, pos.south().south().west().west().down(), pos.north().north().east().east().up());
				if(itemList.size() > 0)
				{
					for(EntityItem item : itemList)
					{
						if(item.getEntityItem().isItemEqual(new ItemStack(Items.golden_apple, 1, 0)))
						{
							for(int i = 0; i < 8; i++)
								WorldAccess.spawnEntityInWorld(new EntityItem(world, item.posX, item.posY, item.posZ, new ItemStack(Items.gold_ingot)));
							WorldAccess.spawnEntityInWorld(new EntityItem(world, item.posX, item.posY, item.posZ, new ItemStack(Items.apple)));
							if(item.getEntityItem().stackSize > 1)
								item.getEntityItem().stackSize--;
							else
								item.setDead();
							break;
						}
						else if(item.getEntityItem().isItemEqual(new ItemStack(Items.golden_apple, 1, 1)))
						{
							for(int i = 0; i < 8; i++)
								WorldAccess.spawnEntityInWorld(new EntityItem(world, item.posX, item.posY, item.posZ, new ItemStack(Blocks.gold_block)));
							WorldAccess.spawnEntityInWorld(new EntityItem(world, item.posX, item.posY, item.posZ, new ItemStack(Items.apple)));
							if(item.getEntityItem().stackSize > 1)
								item.getEntityItem().stackSize--;
							else
								item.setDead();
							break;
						}
					}
				}
			}
		if(!player.isSneaking() && !world.isRemote)
			ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY(), pos.getZ() +.5, pos.getX()+((world.rand.nextDouble() * 4) - 2), pos.getY(), pos.getZ()+.5+((world.rand.nextDouble() * 4) - 2), new RGB(world.rand.nextFloat(),world.rand.nextFloat(),world.rand.nextFloat()));
		if(level1 && level2 && level3)
			ArMechMain.proxy.getVFX().arcLightning(pos.getX()+.5, pos.getY(), pos.getZ() +.5, pos.getX()+((world.rand.nextDouble() * 4) - 2), pos.getY(), pos.getZ()+.5+((world.rand.nextDouble() * 4) - 2), new RGB(world.rand.nextFloat(),world.rand.nextFloat(),world.rand.nextFloat()));
	}
	
	public boolean getIsChalk(World world, BlockPos pos)
	{
		return WorldAccess.isBlockEqual(world, pos, AMBlocks.block_chalk);
	}

	public boolean isSideSolid(IBlockAccess world, BlockPos pos, EnumFacing side)
	{
		return EnumFacing.DOWN == side;
	}
	
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		return null;
	}
}