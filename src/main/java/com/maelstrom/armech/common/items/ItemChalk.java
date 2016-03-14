package com.maelstrom.armech.common.items;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.pleasesortthis.WorldAccess;
import com.maelstrom.armech.common.registry.AMBlocks;
import com.maelstrom.armech.common.registry.AMItems;
//       XxX
//     XO X OX
//    X       X
//   X X  X  X X
//   O   ZZZ   O
//  X   Z   Z   X
//  XX XZ O ZX XX
//  X   Z   Z   X
//   O   ZZZ   O
//   X X  X  X X
//    X       X
//     XO X OX
//       XxX

public class ItemChalk extends Item
{
	public ItemChalk()
	{
		setUnlocalizedName("chalk");
		setCreativeTab(ArMechMain.tab_armech);
		setMaxDamage(120);
		setMaxStackSize(1);
	}
	
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float floatX, float floatY, float floatZ)
	{
		if(!player.isSneaking())
		{
			if(world.getBlockState(pos).getBlock().isReplaceable(world, pos) && AMBlocks.block_chalk.canPlaceBlockOnSide(world, pos, side))
			{
				world.setBlockState(pos, AMBlocks.block_chalk.getDefaultState());
				world.playSoundEffect(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, Reference.MODID + ":chalk", .25f, 1f);
				damageItem(world, itemStack, player);
				return true;
			}
			else if(WorldAccess.getBlockFromPosFace(world, pos, side).getBlock().isReplaceable(world, WorldAccess.getFaceOffset(pos, side)) && AMBlocks.block_chalk.canPlaceBlockOnSide(world, WorldAccess.getFaceOffset(pos, side), side))
			{
				WorldAccess.setBlockOnFace(world, pos, side, AMBlocks.block_chalk);
				world.playSoundEffect(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, Reference.MODID + ":chalk", .25f, 1f);
				damageItem(world, itemStack, player);
				return true;
			}
		}
		else
		{
			if(world.getBlockState(pos).getBlock() == Blocks.redstone_block)
			{
				if(world.getBlockState(pos.east().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.east().north().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.east().north().north().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.east().east().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.east().east().east().north()).getBlock() == AMBlocks.block_chalk)
					if(world.getBlockState(pos.east().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.east().south().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.east().south().south().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.east().east().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.east().east().east().south()).getBlock() == AMBlocks.block_chalk)
				if(world.getBlockState(pos.west().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.west().north().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.west().north().north().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.west().west().north()).getBlock() == AMBlocks.block_chalk &&
						world.getBlockState(pos.west().west().west().north()).getBlock() == AMBlocks.block_chalk)
					if(world.getBlockState(pos.west().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.west().south().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.west().south().south().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.west().west().south()).getBlock() == AMBlocks.block_chalk &&
					world.getBlockState(pos.west().west().west().south()).getBlock() == AMBlocks.block_chalk)
					{
						boolean one = false, two = false, three = false, four = false;
						List<EntityItemFrame> frames = world.getEntitiesWithinAABB(EntityItemFrame.class, AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1).expand(1, 0, 1));
						if(frames.size() == 4)
							for(EntityItemFrame frame : frames)
								if(frame.getDisplayedItem().getItem() == AMItems.dust_crystal)
								{
									if(frame.getDisplayedItem().getItemDamage() == 3)
										one = true;
									if(frame.getDisplayedItem().getItemDamage() == 2)
										two = true;
									if(frame.getDisplayedItem().getItemDamage() == 0)
										three = true;
									if(frame.getDisplayedItem().getItemDamage() == 5)
										four = true;
								}
						if(one && two && three & four)
						{
							for(EntityItemFrame frame : frames)
								frame.setDead();
							WorldAccess.spawnEntityInWorldWithoutVelocity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(AMBlocks.power_crystal)));
							world.setBlockToAir(pos);
						}
					}
			}
		}
		return false;
	}
	
	void damageItem(World world, ItemStack itemStack, EntityPlayer player)
	{
		if(!player.capabilities.isCreativeMode)
		{
			itemStack.setItemDamage(itemStack.getItemDamage()+1);
			if(itemStack.getItemDamage() >= itemStack.getMaxDamage())
			{
				player.renderBrokenItemStack(itemStack);
				player.destroyCurrentEquippedItem();
				if(!player.inventory.addItemStackToInventory(new ItemStack(Items.paper)))
					WorldAccess.spawnEntityInWorld(new EntityItem(world, player.posX, player.posY, player.posZ, new ItemStack(Items.paper)));
			}
		}
	}
}
