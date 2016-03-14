package com.maelstrom.armech.common.pleasesortthis;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
//import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;
import net.minecraftforge.oredict.OreDictionary;

import com.maelstrom.armech.common.ConfigurationArcaneMechina;
import com.maelstrom.armech.common.registry.AMBlocks;

public class OreGen implements IWorldGenerator
{

	@Override
	public void generate(Random random, int chunkX, int chunkz, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider)
	{
		switch (world.provider.getDimensionId())
		{
		case 0:
			{
				for (int i = 0; i < 8; i++)
				{
					genCrystals(world, random, chunkX * 16, chunkz * 16);
				}
			}
		}
	}

	private void genCrystals(World world, Random random, int x, int z)
	{
		for(int i = 0; i < ConfigurationArcaneMechina.crystalGenAmount; i++)
		{
			int firstX = x + random.nextInt(16);
			int firstY = random.nextInt(64);
			int firstZ = z + random.nextInt(16);
			
			BlockPos pos = new BlockPos(firstX, firstY, firstZ);
			if(OreDictionary.containsMatch(false, OreDictionary.getOres("stone"), new ItemStack(world.getBlockState(pos).getBlock())))
				for (EnumFacing face : EnumFacing.VALUES)
				{
					boolean oppisite = random.nextBoolean();
					if (world.getBlockState(WorldAccess.getFaceOffset(pos, oppisite ? face.getOpposite() : face)).getBlock() == Blocks.air)
					{
						world.setBlockState(WorldAccess.getFaceOffset(pos, oppisite ? face.getOpposite() : face), AMBlocks.dust_ore.getStateFromMeta(oppisite ? face.getOpposite().getIndex() : face.getIndex()));
//						world.scheduleUpdate(WorldAccess.getFaceOffset(pos, oppisite ? face.getOpposite() : face), AMBlocks.testBlockToo, 1);
						break;
					}
				}
		}
	}

}
