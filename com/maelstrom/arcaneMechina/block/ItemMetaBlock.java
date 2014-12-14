package com.maelstrom.arcaneMechina.block;

import com.maelstrom.arcaneMechina.reference.Reference;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemMetaBlock extends ItemBlockWithMetadata {

	private String[] nameList;
	private String group;

	public ItemMetaBlock(Block block) {
		super(block, block);
		if(block instanceof BlockOre){
			BlockOre temp = (BlockOre) block;
			this.group = temp.group;
			this.nameList = temp.nameList;
		}
		else
			throw new Error();
	}
	@Override
	public String getUnlocalizedName(ItemStack is){
		int meta = is.getItemDamage();
		if(meta > nameList.length)
			meta = 0;
		return Reference.MOD_ID + "." + group + "." + nameList[meta];
	}
	
	

}
