package com.maelstrom.arcaneMechina.common.other;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.blocks.ModBlocks;

public class Structures {
	
	public static void dragonRebirth(World w, int x, int y, int z){
		if(w.getBlock(x+-8, y+8, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-8, y+8, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-7, y+8, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-7, y+8, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+5, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+5, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+5, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+5, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+5, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+8, z+-8) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+8, z+-7) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+8, z+7) == ModBlocks.glyphBlank &&
		w.getBlock(x+-6, y+8, z+8) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+5, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+5, z+-3) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+5, z+3) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+5, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-5, y+8, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+1, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+1, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+1, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+1, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+4, z+0) == Blocks.diamond_block &&
		w.getBlock(x+-4, y+5, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+5, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+8, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+8, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+8, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-4, y+8, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-3, y+3, z+-1) == Blocks.bedrock &&
		w.getBlock(x+-3, y+3, z+0) == Blocks.bedrock &&
		w.getBlock(x+-3, y+3, z+1) == Blocks.bedrock &&
		w.getBlock(x+-3, y+4, z+-3) == Blocks.diamond_block &&
		w.getBlock(x+-3, y+4, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-3, y+4, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+-3, y+4, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-3, y+4, z+3) == Blocks.diamond_block &&
		w.getBlock(x+-3, y+5, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-3, y+5, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+1, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+1, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+1, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+2, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+2, z+-1) == Blocks.bedrock &&
		w.getBlock(x+-2, y+2, z+0) == Blocks.bedrock &&
		w.getBlock(x+-2, y+2, z+1) == Blocks.bedrock &&
		w.getBlock(x+-2, y+2, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+3, z+-2) == Blocks.bedrock &&
		w.getBlock(x+-2, y+3, z+2) == Blocks.bedrock &&
		w.getBlock(x+-2, y+4, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+4, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+5, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+5, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+7, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+7, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+7, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+8, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-2, y+8, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+1, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+1, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+2, z+-2) == Blocks.bedrock &&
		w.getBlock(x+-1, y+2, z+-1) == Blocks.bedrock &&
		w.getBlock(x+-1, y+2, z+0) == Blocks.bedrock &&
		w.getBlock(x+-1, y+2, z+1) == Blocks.bedrock &&
		w.getBlock(x+-1, y+2, z+2) == Blocks.bedrock &&
		w.getBlock(x+-1, y+3, z+-3) == Blocks.bedrock &&
		w.getBlock(x+-1, y+3, z+3) == Blocks.bedrock &&
		w.getBlock(x+-1, y+4, z+-3) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+4, z+3) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+5, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+5, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+7, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+7, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+-1, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+0, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+1, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+1, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+2, z+-2) == Blocks.bedrock &&
		w.getBlock(x+0, y+2, z+-1) == Blocks.bedrock &&
		w.getBlock(x+0, y+2, z+0) == Blocks.bedrock &&
		w.getBlock(x+0, y+2, z+1) == Blocks.bedrock &&
		w.getBlock(x+0, y+2, z+2) == Blocks.bedrock &&
		w.getBlock(x+0, y+3, z+-3) == Blocks.bedrock &&
		w.getBlock(x+0, y+3, z+0) == Blocks.bedrock &&
		w.getBlock(x+0, y+3, z+3) == Blocks.bedrock &&
		w.getBlock(x+0, y+4, z+-4) == Blocks.diamond_block &&
		w.getBlock(x+0, y+4, z+-3) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+4, z+0) == Blocks.bedrock &&
		w.getBlock(x+0, y+4, z+3) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+4, z+4) == Blocks.diamond_block &&
		w.getBlock(x+0, y+5, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+5, z+0) == Blocks.bedrock &&
		w.getBlock(x+0, y+5, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+6, z+0) == Blocks.bedrock &&
		w.getBlock(x+0, y+7, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+7, z+0) == Blocks.dragon_egg &&
		w.getBlock(x+0, y+7, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+0, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+1, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+1, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+2, z+-2) == Blocks.bedrock &&
		w.getBlock(x+1, y+2, z+-1) == Blocks.bedrock &&
		w.getBlock(x+1, y+2, z+0) == Blocks.bedrock &&
		w.getBlock(x+1, y+2, z+1) == Blocks.bedrock &&
		w.getBlock(x+1, y+2, z+2) == Blocks.bedrock &&
		w.getBlock(x+1, y+3, z+-3) == Blocks.bedrock &&
		w.getBlock(x+1, y+3, z+3) == Blocks.bedrock &&
		w.getBlock(x+1, y+4, z+-3) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+4, z+3) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+5, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+5, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+7, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+7, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+1, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+1, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+1, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+1, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+2, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+2, z+-1) == Blocks.bedrock &&
		w.getBlock(x+2, y+2, z+0) == Blocks.bedrock &&
		w.getBlock(x+2, y+2, z+1) == Blocks.bedrock &&
		w.getBlock(x+2, y+2, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+3, z+-2) == Blocks.bedrock &&
		w.getBlock(x+2, y+3, z+2) == Blocks.bedrock &&
		w.getBlock(x+2, y+4, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+4, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+5, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+5, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+7, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+7, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+7, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+8, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+2, y+8, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+3, y+3, z+-1) == Blocks.bedrock &&
		w.getBlock(x+3, y+3, z+0) == Blocks.bedrock &&
		w.getBlock(x+3, y+3, z+1) == Blocks.bedrock &&
		w.getBlock(x+3, y+4, z+-3) == Blocks.diamond_block &&
		w.getBlock(x+3, y+4, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+3, y+4, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+3, y+4, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+3, y+4, z+3) == Blocks.diamond_block &&
		w.getBlock(x+3, y+5, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+3, y+5, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+1, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+1, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+1, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+1, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+4, z+0) == Blocks.diamond_block &&
		w.getBlock(x+4, y+5, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+5, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+8, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+8, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+8, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+4, y+8, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+5, z+-4) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+5, z+-3) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+5, z+3) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+5, z+4) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+5, y+8, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+5, z+-2) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+5, z+-1) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+5, z+0) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+5, z+1) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+5, z+2) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+8, z+-8) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+8, z+-7) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+8, z+-5) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+8, z+5) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+8, z+7) == ModBlocks.glyphBlank &&
		w.getBlock(x+6, y+8, z+8) == ModBlocks.glyphBlank &&
		w.getBlock(x+7, y+8, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+7, y+8, z+6) == ModBlocks.glyphBlank &&
		w.getBlock(x+8, y+8, z+-6) == ModBlocks.glyphBlank &&
		w.getBlock(x+8, y+8, z+6) == ModBlocks.glyphBlank){
			w.setBlock(x, y, z, ModBlocks.glyphBlank);
			for(int i = -5; i < 5; i++)
				for(int i2 = 0; i2 < 15; i2++)
					for(int i3 = -5; i3 < 5; i3++)
						if(w.getBlock(x+i, y+i2, z+i3) == Blocks.end_portal)
							w.setBlock(x+i, y+i2, z+i3, Blocks.air);
			w.setBlock(x+-8, y+8, z+-6, Blocks.air);
			w.setBlock(x+-8, y+8, z+6, Blocks.air);
			w.setBlock(x+-7, y+8, z+-6, Blocks.air);
			w.setBlock(x+-7, y+8, z+6, Blocks.air);
			w.setBlock(x+-6, y+5, z+-2, Blocks.air);
			w.setBlock(x+-6, y+5, z+-1, Blocks.air);
			w.setBlock(x+-6, y+5, z+0, Blocks.air);
			w.setBlock(x+-6, y+5, z+1, Blocks.air);
			w.setBlock(x+-6, y+5, z+2, Blocks.air);
			w.setBlock(x+-6, y+8, z+-8, Blocks.air);
			w.setBlock(x+-6, y+8, z+-7, Blocks.air);
			w.setBlock(x+-6, y+8, z+-5, Blocks.air);
			w.setBlock(x+-6, y+8, z+5, Blocks.air);
			w.setBlock(x+-6, y+8, z+7, Blocks.air);
			w.setBlock(x+-6, y+8, z+8, Blocks.air);
			w.setBlock(x+-5, y+5, z+-4, Blocks.air);
			w.setBlock(x+-5, y+5, z+-3, Blocks.air);
			w.setBlock(x+-5, y+5, z+3, Blocks.air);
			w.setBlock(x+-5, y+5, z+4, Blocks.air);
			w.setBlock(x+-5, y+8, z+-6, Blocks.air);
			w.setBlock(x+-5, y+8, z+-5, Blocks.air);
			w.setBlock(x+-5, y+8, z+-1, Blocks.air);
			w.setBlock(x+-5, y+8, z+0, Blocks.air);
			w.setBlock(x+-5, y+8, z+1, Blocks.air);
			w.setBlock(x+-5, y+8, z+5, Blocks.air);
			w.setBlock(x+-5, y+8, z+6, Blocks.air);
			w.setBlock(x+-4, y+1, z+-2, Blocks.air);
			w.setBlock(x+-4, y+1, z+-1, Blocks.air);
			w.setBlock(x+-4, y+1, z+1, Blocks.air);
			w.setBlock(x+-4, y+1, z+2, Blocks.air);
			w.setBlock(x+-4, y+4, z+0, Blocks.air);
			w.setBlock(x+-4, y+5, z+-5, Blocks.air);
			w.setBlock(x+-4, y+5, z+5, Blocks.air);
			w.setBlock(x+-4, y+8, z+-4, Blocks.air);
			w.setBlock(x+-4, y+8, z+-2, Blocks.air);
			w.setBlock(x+-4, y+8, z+2, Blocks.air);
			w.setBlock(x+-4, y+8, z+4, Blocks.air);
			w.setBlock(x+-3, y+3, z+-1, Blocks.air);
			w.setBlock(x+-3, y+3, z+0, Blocks.air);
			w.setBlock(x+-3, y+3, z+1, Blocks.air);
			w.setBlock(x+-3, y+4, z+-3, Blocks.air);
			w.setBlock(x+-3, y+4, z+-1, Blocks.air);
			w.setBlock(x+-3, y+4, z+0, Blocks.air);
			w.setBlock(x+-3, y+4, z+1, Blocks.air);
			w.setBlock(x+-3, y+4, z+3, Blocks.air);
			w.setBlock(x+-3, y+5, z+-5, Blocks.air);
			w.setBlock(x+-3, y+5, z+5, Blocks.air);
			w.setBlock(x+-2, y+1, z+-4, Blocks.air);
			w.setBlock(x+-2, y+1, z+0, Blocks.air);
			w.setBlock(x+-2, y+1, z+4, Blocks.air);
			w.setBlock(x+-2, y+2, z+-2, Blocks.air);
			w.setBlock(x+-2, y+2, z+-1, Blocks.air);
			w.setBlock(x+-2, y+2, z+0, Blocks.air);
			w.setBlock(x+-2, y+2, z+1, Blocks.air);
			w.setBlock(x+-2, y+2, z+2, Blocks.air);
			w.setBlock(x+-2, y+3, z+-2, Blocks.air);
			w.setBlock(x+-2, y+3, z+2, Blocks.air);
			w.setBlock(x+-2, y+4, z+-2, Blocks.air);
			w.setBlock(x+-2, y+4, z+2, Blocks.air);
			w.setBlock(x+-2, y+5, z+-6, Blocks.air);
			w.setBlock(x+-2, y+5, z+6, Blocks.air);
			w.setBlock(x+-2, y+7, z+-1, Blocks.air);
			w.setBlock(x+-2, y+7, z+0, Blocks.air);
			w.setBlock(x+-2, y+7, z+1, Blocks.air);
			w.setBlock(x+-2, y+8, z+-4, Blocks.air);
			w.setBlock(x+-2, y+8, z+4, Blocks.air);
			w.setBlock(x+-1, y+1, z+-4, Blocks.air);
			w.setBlock(x+-1, y+1, z+4, Blocks.air);
			w.setBlock(x+-1, y+2, z+-2, Blocks.air);
			w.setBlock(x+-1, y+2, z+-1, Blocks.air);
			w.setBlock(x+-1, y+2, z+0, Blocks.air);
			w.setBlock(x+-1, y+2, z+1, Blocks.air);
			w.setBlock(x+-1, y+2, z+2, Blocks.air);
			w.setBlock(x+-1, y+3, z+-3, Blocks.air);
			w.setBlock(x+-1, y+3, z+3, Blocks.air);
			w.setBlock(x+-1, y+4, z+-3, Blocks.air);
			w.setBlock(x+-1, y+4, z+3, Blocks.air);
			w.setBlock(x+-1, y+5, z+-6, Blocks.air);
			w.setBlock(x+-1, y+5, z+6, Blocks.air);
			w.setBlock(x+-1, y+7, z+-2, Blocks.air);
			w.setBlock(x+-1, y+7, z+2, Blocks.air);
			w.setBlock(x+-1, y+8, z+-5, Blocks.air);
			w.setBlock(x+-1, y+8, z+5, Blocks.air);
			w.setBlock(x+0, y+0, z+0, Blocks.air);
			w.setBlock(x+0, y+1, z+-2, Blocks.air);
			w.setBlock(x+0, y+1, z+2, Blocks.air);
			w.setBlock(x+0, y+2, z+-2, Blocks.air);
			w.setBlock(x+0, y+2, z+-1, Blocks.air);
			w.setBlock(x+0, y+2, z+0, Blocks.air);
			w.setBlock(x+0, y+2, z+1, Blocks.air);
			w.setBlock(x+0, y+2, z+2, Blocks.air);
			w.setBlock(x+0, y+3, z+-3, Blocks.air);
			w.setBlock(x+0, y+3, z+0, Blocks.air);
			w.setBlock(x+0, y+3, z+3, Blocks.air);
			w.setBlock(x+0, y+4, z+-4, Blocks.air);
			w.setBlock(x+0, y+4, z+-3, Blocks.air);
			w.setBlock(x+0, y+4, z+0, Blocks.air);
			w.setBlock(x+0, y+4, z+3, Blocks.air);
			w.setBlock(x+0, y+4, z+4, Blocks.air);
			w.setBlock(x+0, y+5, z+-6, Blocks.air);
			w.setBlock(x+0, y+5, z+0, Blocks.air);
			w.setBlock(x+0, y+5, z+6, Blocks.air);
			w.setBlock(x+0, y+6, z+0, Blocks.air);
			w.setBlock(x+0, y+7, z+-2, Blocks.air);
			w.setBlock(x+0, y+7, z+0, Blocks.air);
			w.setBlock(x+0, y+7, z+2, Blocks.air);
			w.setBlock(x+0, y+8, z+-5, Blocks.air);
			w.setBlock(x+0, y+8, z+5, Blocks.air);
			w.setBlock(x+1, y+1, z+-4, Blocks.air);
			w.setBlock(x+1, y+1, z+4, Blocks.air);
			w.setBlock(x+1, y+2, z+-2, Blocks.air);
			w.setBlock(x+1, y+2, z+-1, Blocks.air);
			w.setBlock(x+1, y+2, z+0, Blocks.air);
			w.setBlock(x+1, y+2, z+1, Blocks.air);
			w.setBlock(x+1, y+2, z+2, Blocks.air);
			w.setBlock(x+1, y+3, z+-3, Blocks.air);
			w.setBlock(x+1, y+3, z+3, Blocks.air);
			w.setBlock(x+1, y+4, z+-3, Blocks.air);
			w.setBlock(x+1, y+4, z+3, Blocks.air);
			w.setBlock(x+1, y+5, z+-6, Blocks.air);
			w.setBlock(x+1, y+5, z+6, Blocks.air);
			w.setBlock(x+1, y+7, z+-2, Blocks.air);
			w.setBlock(x+1, y+7, z+2, Blocks.air);
			w.setBlock(x+1, y+8, z+-5, Blocks.air);
			w.setBlock(x+1, y+8, z+5, Blocks.air);
			w.setBlock(x+2, y+1, z+-4, Blocks.air);
			w.setBlock(x+2, y+1, z+0, Blocks.air);
			w.setBlock(x+2, y+1, z+4, Blocks.air);
			w.setBlock(x+2, y+2, z+-2, Blocks.air);
			w.setBlock(x+2, y+2, z+-1, Blocks.air);
			w.setBlock(x+2, y+2, z+0, Blocks.air);
			w.setBlock(x+2, y+2, z+1, Blocks.air);
			w.setBlock(x+2, y+2, z+2, Blocks.air);
			w.setBlock(x+2, y+3, z+-2, Blocks.air);
			w.setBlock(x+2, y+3, z+2, Blocks.air);
			w.setBlock(x+2, y+4, z+-2, Blocks.air);
			w.setBlock(x+2, y+4, z+2, Blocks.air);
			w.setBlock(x+2, y+5, z+-6, Blocks.air);
			w.setBlock(x+2, y+5, z+6, Blocks.air);
			w.setBlock(x+2, y+7, z+-1, Blocks.air);
			w.setBlock(x+2, y+7, z+0, Blocks.air);
			w.setBlock(x+2, y+7, z+1, Blocks.air);
			w.setBlock(x+2, y+8, z+-4, Blocks.air);
			w.setBlock(x+2, y+8, z+4, Blocks.air);
			w.setBlock(x+3, y+3, z+-1, Blocks.air);
			w.setBlock(x+3, y+3, z+0, Blocks.air);
			w.setBlock(x+3, y+3, z+1, Blocks.air);
			w.setBlock(x+3, y+4, z+-3, Blocks.air);
			w.setBlock(x+3, y+4, z+-1, Blocks.air);
			w.setBlock(x+3, y+4, z+0, Blocks.air);
			w.setBlock(x+3, y+4, z+1, Blocks.air);
			w.setBlock(x+3, y+4, z+3, Blocks.air);
			w.setBlock(x+3, y+5, z+-5, Blocks.air);
			w.setBlock(x+3, y+5, z+5, Blocks.air);
			w.setBlock(x+4, y+1, z+-2, Blocks.air);
			w.setBlock(x+4, y+1, z+-1, Blocks.air);
			w.setBlock(x+4, y+1, z+1, Blocks.air);
			w.setBlock(x+4, y+1, z+2, Blocks.air);
			w.setBlock(x+4, y+4, z+0, Blocks.air);
			w.setBlock(x+4, y+5, z+-5, Blocks.air);
			w.setBlock(x+4, y+5, z+5, Blocks.air);
			w.setBlock(x+4, y+8, z+-4, Blocks.air);
			w.setBlock(x+4, y+8, z+-2, Blocks.air);
			w.setBlock(x+4, y+8, z+2, Blocks.air);
			w.setBlock(x+4, y+8, z+4, Blocks.air);
			w.setBlock(x+5, y+5, z+-4, Blocks.air);
			w.setBlock(x+5, y+5, z+-3, Blocks.air);
			w.setBlock(x+5, y+5, z+3, Blocks.air);
			w.setBlock(x+5, y+5, z+4, Blocks.air);
			w.setBlock(x+5, y+8, z+-6, Blocks.air);
			w.setBlock(x+5, y+8, z+-5, Blocks.air);
			w.setBlock(x+5, y+8, z+-1, Blocks.air);
			w.setBlock(x+5, y+8, z+0, Blocks.air);
			w.setBlock(x+5, y+8, z+1, Blocks.air);
			w.setBlock(x+5, y+8, z+5, Blocks.air);
			w.setBlock(x+5, y+8, z+6, Blocks.air);
			w.setBlock(x+6, y+5, z+-2, Blocks.air);
			w.setBlock(x+6, y+5, z+-1, Blocks.air);
			w.setBlock(x+6, y+5, z+0, Blocks.air);
			w.setBlock(x+6, y+5, z+1, Blocks.air);
			w.setBlock(x+6, y+5, z+2, Blocks.air);
			w.setBlock(x+6, y+8, z+-8, Blocks.air);
			w.setBlock(x+6, y+8, z+-7, Blocks.air);
			w.setBlock(x+6, y+8, z+-5, Blocks.air);
			w.setBlock(x+6, y+8, z+5, Blocks.air);
			w.setBlock(x+6, y+8, z+7, Blocks.air);
			w.setBlock(x+6, y+8, z+8, Blocks.air);
			w.setBlock(x+7, y+8, z+-6, Blocks.air);
			w.setBlock(x+7, y+8, z+6, Blocks.air);
			w.setBlock(x+8, y+8, z+-6, Blocks.air);
			w.setBlock(x+8, y+8, z+6, Blocks.air);
			if(!w.isRemote){
				Entity e = new EntityDragon(w);
				e.setPosition(x, y+20, z);
				w.spawnEntityInWorld(e);
			}
		}
	}

	public static void allBlankGlyph(World w, int x, int y, int z) {
		dragonRebirth(w,x,y,z);
	}

}
