package com.maelstrom.arcaneMechina.common.item;

import com.maelstrom.arcaneMechina.common.block.BlockChalkGlyph;
import com.maelstrom.arcaneMechina.common.init.InitBlock;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemGems extends ItemMetadataBased {

	public ItemGems(String name, String[] nameList) {
		super(name, nameList);
	}
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat){
    	if(is.getItemDamage() == 5)
		    if(face == 1)
				if(InitBlock.glyphblank.canPlaceBlockAt(w, x, y + 1, z)){
		    		w.setBlock(x, y + 1, z, InitBlock.glyphblank);
			        if(!ply.capabilities.isCreativeMode)
			        	is.stackSize--;
			    	return true;
				}
    	return super.onItemUse(is, ply, w, x, y, z, face, xFloat, yFloat, zFloat);
    }
}