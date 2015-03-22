package com.maelstrom.arcaneMechina.common.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.blocks.BlockChalkGlyph;
import com.maelstrom.arcaneMechina.common.blocks.ModBlocks;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class Chalk extends ExtendableItem {
	public int i = 0;
	public Chalk(String name) {
		super(name, Reference.MOD_ID);
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
	}
	
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat){
		Block temp = ModBlocks.glyphBlank;
		if(temp != null)
    		if(face == 1){
    			if(temp.canPlaceBlockAt(w, x, y + 1, z)){
		    		w.setBlock(x, y + 1, z, temp);
			        if(!ply.capabilities.isCreativeMode)
			        	is.damageItem(1, ply);
	        		if(is.getItemDamage() <= 64)
		        		is = null;
			    	return true;
    			}else if(w.getBlock(x, y, z) instanceof BlockChalkGlyph){
		    		w.setBlock(x, y, z, temp);
			        if(!ply.capabilities.isCreativeMode)
			        	is.damageItem(1, ply);
	        		if(is.getItemDamage() <= 64)
		        		is = null;
			    	return true;
	    		}
    		}else if(w.getBlock(x, y, z) instanceof BlockChalkGlyph){
	    		w.setBlock(x, y, z, temp);
		        if(!ply.capabilities.isCreativeMode)
		        	is.damageItem(1, ply);
        		if(is.getItemDamage() <= 64)
	        		is = null;
		    	return true;
    		}
		return false;
    }
}
