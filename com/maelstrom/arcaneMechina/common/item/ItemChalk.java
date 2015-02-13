package com.maelstrom.arcaneMechina.common.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.block.BlockChalkGlyph;
import com.maelstrom.arcaneMechina.common.init.InitBlock;
import com.maelstrom.arcaneMechina.common.init.InitItem;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.world.Structure;
import com.maelstrom.arcaneMechina.common.world.StructureRegistery;
import com.maelstrom.arcaneMechina.common.world.structure.Layer;
import com.maelstrom.arcaneMechina.common.world.structure.Row;
import com.maelstrom.snowcone.Util;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemChalk extends ExtendableItem {
	public int i = 0;
	public ItemChalk(String name) {
		super(name, Reference.MOD_ID);
		this.setMaxDamage(64);
		this.setMaxStackSize(1);
	}
	
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat){
    	//test
    	if(ply.isSneaking()){
			Structure s = StructureRegistery.getStructuresByName("AM")[i];
			Layer l = s.getLayers()[0];
			for(int i2 = 0; i2 < l.getRows().length; i2++){
				int center = (l.getRows()[i2].getBlocks().length / 2);
				for(int i = 0; i < l.getRows()[i2].getBlocks().length; i++){
					w.setBlock(x-(i - center), y+1, z+(i2 - center), l.getRows()[i2].getBlocks()[i]);
				}
			}
    	}
    	else
    	if(w.getBlock(x, y, z).equals(Blocks.furnace) 
    		&& w.getBlock(x, y+1, z).equals(Blocks.furnace)
			&& w.getBlock(x+1,y,z+1).equals(InitBlock.glyphfire) 
			&& w.getBlock(x+1,y,z).equals(InitBlock.glyphfire)
			&& w.getBlock(x,y,z+1).equals(InitBlock.glyphfire)
			&& w.getBlock(x+1,y,z-1).equals(InitBlock.glyphfire)
			&& w.getBlock(x-1,y,z+1).equals(InitBlock.glyphfire)
			&& w.getBlock(x-1,y,z-1).equals(InitBlock.glyphfire)
			&& w.getBlock(x,y,z-1).equals(InitBlock.glyphfire)
			&& w.getBlock(x-1,y,z).equals(InitBlock.glyphfire)){
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
    		Block temp = null;
    		if(this == InitItem.chalkfire)
    			temp = InitBlock.glyphfire;
    		else if(this == InitItem.chalkair)
    			temp = InitBlock.glyphair;
    		else if(this == InitItem.chalkearth)
    			temp = InitBlock.glyphearth;
    		else if(this == InitItem.chalkenergy)
    			temp = InitBlock.glyphenergy;
    		else if(this == InitItem.chalkice)
    			temp = InitBlock.glyphice;
    		else if(this == InitItem.chalk)
    			temp = InitBlock.glyphblank;
    		if(temp != null){
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
    		}
    	}
    	return false;
    }
    public boolean justRan = false;
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ply) {
    	if(!justRan)
	    	if(this == InitItem.chalk){
		    	i++;
		    	if(i > StructureRegistery.getStructuresByName("AM").length - 1)
		    		i = 0;
		    	ply.addChatMessage(new ChatComponentText(i + ""));
	    	}
    	justRan = !justRan;
        return is;
    }
}
