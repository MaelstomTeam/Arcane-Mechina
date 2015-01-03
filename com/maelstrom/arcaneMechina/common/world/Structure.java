package com.maelstrom.arcaneMechina.common.world;

import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcaneMechina.common.world.structure.Layer;

public class Structure {
	private Layer[] layers;
	private String name;
	public Structure(String n, Layer[] l){
		name = n;
		layers = l;
	}
	
	
	
	public boolean checkStructure(World w, int x, int y, int z, ForgeDirection direction){
		for(int y2 = 0; y2 < layers.length; y2++){
			Layer currentLayer = layers[y2];
			//check direction
			//for loop through checking blocks
			switch(direction){
			case NORTH : { break; }
			case SOUTH : { break; }
			case EAST : { break; }
			case WEST : { break; }
			case UNKNOWN :{
				//check all directions
				break; //return
			}
			default : { return false; }
			}
		}
		return false;
	}
	public String getName(){
		return name;
	}
	
}