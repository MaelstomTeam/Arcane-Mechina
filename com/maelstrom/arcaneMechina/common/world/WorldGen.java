package com.maelstrom.arcaneMechina.common.world;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.world.OreGenBlock;

import cpw.mods.fml.common.IWorldGenerator;

public class WorldGen implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.dimensionId) {
		case -1: generateHell(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 0: generateOverworld(world, random, chunkX * 16, chunkZ * 16);
			break;
		case 1: generateEnd(world, random, chunkX * 16, chunkZ * 16);
			break;
		default: //just in case something bad happens ~ hybolic <3
			break;
		}
	}

	private void generateEnd(World world, Random random, int x, int z) {
		//in case of End ores
	}

	private void generateOverworld(World world, Random random, int x, int z) {
		for(OreGenBlock vain : Reference.worldGenOverworld)
			vain.generateOre(world, random, x, z);
	}

	private void generateHell(World world, Random random, int z, int x) {
		//in case of Hell ores
	}
}
