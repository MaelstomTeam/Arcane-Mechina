package com.maelstrom.arcaneMechina.common.world;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcaneMechina.common.init.InitBlock;
import com.maelstrom.arcaneMechina.common.world.custom.NoEffect;
import com.maelstrom.arcaneMechina.common.world.structure.Layer;
import com.maelstrom.arcaneMechina.common.world.structure.Row;

public class Structure {
	private Layer[] layers;
	private String name;
	private Effect effect;
	public Structure(String n, Layer[] l, Effect e){
		name = n;
		layers = l;
		effect = e;
	}

	public Structure(String n, Layer[] l){
		name = n;
		layers = l;
		effect = new NoEffect();
	}
	
	
	public boolean checkStructure(World w, int x, int y, int z, ForgeDirection direction){
		for(int y2 = 0; y2 < layers.length; y2++){
			Layer l = layers[y2];
			for(int i2 = 0; i2 < l.getRows().length; i2++){
				Row r = l.getRows()[i2];
				int center = (r.getBlocks().length / 2);
				for(int i = 0; i < r.getBlocks().length; i++){
					if(r.getBlocks()[i] != Blocks.air)
						switch(direction){
						case NORTH : { 
							if(w.getBlock(x+(i-center), y, z+(i2-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case SOUTH : { 
							if(w.getBlock(x-(i-center), y, z-(i2-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case EAST : { 
							if(w.getBlock(x-(i2-center), y, z+(i-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case WEST : { 
							if(w.getBlock(x+(i2-center), y, z-(i-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case UNKNOWN :{
							if(w.getBlock(x+(i-center), y, z+(i2-center)) != r.getBlocks()[i])
								if(w.getBlock(x-(i-center), y, z-(i2-center)) != r.getBlocks()[i])
									if(w.getBlock(x-(i2-center), y, z+(i-center)) != r.getBlocks()[i])
										if(w.getBlock(x+(i2-center), y, z-(i-center)) != r.getBlocks()[i])
											return false;
							break; //return
						}
						default : { return false; }
						}
				}
			}

		}
		if(!effect.effectCheck(w, x, y, z))
			return false;
		else
			effect.effect(w, x, y, z);
		return true;
	}

	public boolean checkStructure(World w, int x, int y, int z, ForgeDirection direction, boolean air){
		for(int y2 = 0; y2 < layers.length; y2++){
			Layer l = layers[y2];
			for(int i2 = 0; i2 < l.getRows().length; i2++){
				Row r = l.getRows()[i2];
				int center = (r.getBlocks().length / 2);
				for(int i = 0; i < r.getBlocks().length; i++){
					if(r.getBlocks()[i] != Blocks.air)
						switch(direction){
						case NORTH : { 
							if(w.getBlock(x+(i-center), y, z+(i2-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case SOUTH : { 
							if(w.getBlock(x-(i-center), y, z-(i2-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case EAST : { 
							if(w.getBlock(x-(i2-center), y, z+(i-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case WEST : { 
							if(w.getBlock(x+(i2-center), y, z-(i-center)) != r.getBlocks()[i])
								return false;
							break;
							}
						case UNKNOWN :{
							if(w.getBlock(x+(i-center), y, z+(i2-center)) != r.getBlocks()[i])
								if(w.getBlock(x-(i-center), y, z-(i2-center)) != r.getBlocks()[i])
									if(w.getBlock(x-(i2-center), y, z+(i-center)) != r.getBlocks()[i])
										if(w.getBlock(x+(i2-center), y, z-(i-center)) != r.getBlocks()[i])
											return false;
							break; //return
						}
						default : { return false; }
						}
				}
			}
		}
		if(!effect.effectCheck(w, x, y, z))
			return false;
		else
			effect.effect(w, x, y, z);
		if(air)
			for(int y2 = 0; y2 < layers.length; y2++){
				Layer l = layers[y2];
				for(int i2 = 0; i2 < l.getRows().length; i2++){
					Row r = l.getRows()[i2];
					int center = (r.getBlocks().length / 2);
					for(int i = 0; i < r.getBlocks().length; i++)
						if(r.getBlocks()[i] != Blocks.air)
							switch(direction){
								case NORTH : { 
									if(w.getBlock(x+(i-center), y, z+(i2-center)) == r.getBlocks()[i])
										w.setBlock(x+(i-center), y, z+(i2-center), Blocks.air);
									break;
									}
								case SOUTH : { 
									if(w.getBlock(x-(i-center), y, z-(i2-center)) == r.getBlocks()[i])
										w.setBlock(x-(i-center), y, z-(i2-center), Blocks.air);
									break;
									}
								case EAST : { 
									if(w.getBlock(x-(i2-center), y, z+(i-center)) == r.getBlocks()[i])
										w.setBlock(x-(i2-center), y, z+(i-center), Blocks.air);
									break;
									}
								case WEST : { 
									if(w.getBlock(x+(i2-center), y, z-(i-center)) == r.getBlocks()[i])
										w.setBlock(x+(i2-center), y, z-(i-center), Blocks.air);
									break;
									}
								case UNKNOWN :{
									if(w.getBlock(x+(i-center), y, z+(i2-center)) == r.getBlocks()[i])
										w.setBlock(x+(i-center), y, z+(i2-center), Blocks.air);
									else if(w.getBlock(x-(i-center), y, z-(i2-center)) == r.getBlocks()[i])
											w.setBlock(x-(i-center), y, z-(i2-center), Blocks.air);
									else if(w.getBlock(x-(i2-center), y, z+(i-center)) == r.getBlocks()[i])
										w.setBlock(x-(i2-center), y, z+(i-center), Blocks.air);
									else if(w.getBlock(x+(i2-center), y, z-(i-center)) == r.getBlocks()[i])
										w.setBlock(x+(i2-center), y, z-(i-center), Blocks.air);
									break; //return
								}
								default : { break; }
							}
				}
			}
		return true;
	}
	public void printStructure(){
		for(Layer l : layers)
			for(Row r : l.getRows()){
				for(Block b : r.getBlocks())
					if(b == Blocks.air)
						System.out.print(" ");
					else if(b == InitBlock.glyphenergy)
						System.out.print("E");
					else if(b == InitBlock.glyphblank)
						System.out.print("B");
					else if(b == InitBlock.glyphCenter)
						System.out.print("C");
				System.out.println();
			}
	}
	public String getName(){
		return name;
	}

	public Layer[] getLayers(){
		return layers;
	}
	
}