package com.maelstrom.arcaneMechina.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.init.InitBlock;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.Util;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemChalk extends ExtendableItem {

	public ItemChalk(String name) {
		super(name, Reference.MOD_ID);
		this.setMaxDamage(64);
	}
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat){
    	if(w.getBlock(x, y, z).equals(Blocks.furnace) 
    		&& w.getBlock(x, y+1, z).equals(Blocks.furnace)
			&& w.getBlock(x+1,y,z+1).equals(InitBlock.glyph) 
			&& w.getBlock(x+1,y,z).equals(InitBlock.glyph)
			&& w.getBlock(x,y,z+1).equals(InitBlock.glyph)
			&& w.getBlock(x+1,y,z-1).equals(InitBlock.glyph)
			&& w.getBlock(x-1,y,z+1).equals(InitBlock.glyph)
			&& w.getBlock(x-1,y,z-1).equals(InitBlock.glyph)
			&& w.getBlock(x,y,z-1).equals(InitBlock.glyph)
			&& w.getBlock(x-1,y,z).equals(InitBlock.glyph)){
    			((BlockFurnace)w.getBlock(x, y, z)).breakBlock(w, x, y, z, ((BlockFurnace)w.getBlock(x, y, z)), w.getBlockMetadata(x, y, z));;
    			((BlockFurnace)w.getBlock(x, y+1, z)).breakBlock(w, x, y+1, z, ((BlockFurnace)w.getBlock(x, y+1, z)), w.getBlockMetadata(x, y+1, z));;
    			w.setBlock(x+1, y, z+1, Blocks.air);
    			w.setBlock(x+1, y, z, Blocks.air);
    			w.setBlock(x+1, y, z-1, Blocks.air);
    			w.setBlock(x, y, z+1, Blocks.air);
    			w.setBlock(x, y, z-1, Blocks.air);
    			w.setBlock(x-1, y, z+1, Blocks.air);
    			w.setBlock(x-1, y, z-1, Blocks.air);
    			w.setBlock(x-1, y, z, Blocks.air);
    			w.setBlock(x, y, z, InitBlock.arrayFurnace, 0, 3);
    			w.setBlock(x, y+1, z, InitBlock.arrayFurnace, 1, 3);
    			return true;
    	} else {
    		Block temp = InitBlock.glyph;
    		if(face == 1){
    			if(temp.canPlaceBlockAt(w, x, y + 1, z)){
		    		w.setBlock(x, y + 1, z, InitBlock.glyph);
		        	if(Util.isObfuscatedEnv())
			        if(!ply.capabilities.isCreativeMode)
			        	is.damageItem(1, ply);
	        		if(is.getItemDamage() <= 64)
		        		is = null;
			    	return true;
    			}
    		}
    	}
    	return false;
    }
}
