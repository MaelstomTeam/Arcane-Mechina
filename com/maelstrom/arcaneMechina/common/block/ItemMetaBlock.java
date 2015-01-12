package com.maelstrom.arcaneMechina.common.block;

import com.maelstrom.arcaneMechina.common.reference.Reference;

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
		else if(block instanceof BlockCraftingTable){
			this.group = "cTable";
			this.nameList = ((BlockCraftingTable) block).nameList;
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