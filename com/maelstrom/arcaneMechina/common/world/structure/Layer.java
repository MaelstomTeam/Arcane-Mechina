package com.maelstrom.arcaneMechina.common.world.structure;

public class Layer {

	private Row[] rows;

	public Layer(Row[] r){
		rows = r;
	}
	
	public Row[] getRows(){
		return rows;
	}
}
