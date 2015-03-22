package com.maelstrom.arcaneMechina.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

import com.maelstrom.arcaneMechina.common.reference.Reference;

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
		return "tile." + Reference.MOD_ID + "." + group + "." + nameList[meta];
	}
	
	

}
