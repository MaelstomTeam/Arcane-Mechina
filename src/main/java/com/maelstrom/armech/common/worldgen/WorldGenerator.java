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
		//switch over dimension cases
		switch(world.provider.getDimension())
		{
		case -1: break;
		case 0: generateOverworld(world, random, chunkX, chunkZ);break;
		case 1: break;
		default: {
			//TODO: add a way to add generation via a config file of some description
			//check a config file for generation name and what's generated "world.provider.getDimensionType().getName()"
			break;}
		}
	}
	
	
	private void generateOverworld(World world, Random rand, int x, int z)
	{
		//add mineable copper ore
		worldGenMinable(ModBlocks.copperOre, 16).generate(world, rand, blockPosition(x,z,16,128,rand));
		//add mineable dust crystal ore
		worldGenMinable(ModBlocks.dustOre, 4).generate(world, rand, blockPosition(x,z,16,48,rand));
		//add mineable dust crystal ore with a larger generation amount
		worldGenMinable(ModBlocks.dustOre, 10).generate(world, rand, blockPosition(x,z,0,16,rand));
	}
	
	
	
	public static WorldGenMinable worldGenMinable(Block block, int blockCount)
	{
		return new WorldGenMinable(block.getDefaultState(), blockCount);
	}
	
	public static BlockPos blockPosition(int chunkX, int chunkZ, int minYLevel, int maxYLevel, Random random)
	{
		int xInWorld = ( chunkX * 16 ) + random.nextInt(16);
		int yInWorld = minYLevel + random.nextInt(maxYLevel - minYLevel);
		int zInWorld = ( chunkZ  * 16 ) + random.nextInt(16);
		return new BlockPos(xInWorld, yInWorld, zInWorld);
	}

}
