package com.maelstrom.arcaneMechina.common.world;

import java.util.ArrayList;
import java.util.List;

public class StructureRegistery {
	private static List<Structure> list = new ArrayList<Structure>();
	
	public static void register(Structure s){
		list.add(s);
	}
	
	public static List<Structure> getList(){
		return list;
	}
	
	public static Structure[] getStructuresByName(String name){
		List<Structure> temp = new ArrayList<Structure>();
		for(Structure s : list)
			if(s.getName().contains(name))
				temp.add(s);
		return temp.toArray(new Structure[temp.size()]);
	}
	
	public static List<Structure> getStructuresByName2(String name){
		List<Structure> temp = new ArrayList<Structure>();
		for(Structure s : list)
			if(s.getName().contains(name))
				temp.add(s);
		return temp;
	}
}
