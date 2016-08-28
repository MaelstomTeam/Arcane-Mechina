package com.maelstrom.armech.common.worldgen;

import java.util.Random;

import com.maelstrom.armech.common.reference.ModBlocks;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class WorldGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch(world.provider.getDimension())
		{
		case -1: break;
		case 0: generateOverworld(world, random, chunkX * 16, chunkZ * 16);break;
		case 1: break;
		default: {
			//check a config file for generation name and what's generated "world.provider.getDimensionType().getName()"
			break;}
		}
	}
	
	
	private void generateOverworld(World world, Random rand, int x, int z)
	{
		worldGenMinable(ModBlocks.copperOre, 16).generate(world, rand, blockPosition(x,z,16,128,rand));
		worldGenMinable(ModBlocks.dustOre, 4).generate(world, rand, blockPosition(x,z,16,64,rand));
	}
	
	
	
	public static WorldGenMinable worldGenMinable(Block block, int blockCount)
	{
		return new WorldGenMinable(block.getDefaultState(), blockCount);
	}
	
	public static BlockPos blockPosition(int x, int z, int minYLevel, int maxYLevel, Random random)
	{
		int xInWorld = x + random.nextInt(16);
		int yInWorld = minYLevel + random.nextInt(maxYLevel - minYLevel);
		int zInWorld = z + random.nextInt(16);
		return new BlockPos(xInWorld, yInWorld, zInWorld);
	}

}
