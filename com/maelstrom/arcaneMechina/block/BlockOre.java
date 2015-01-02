package com.maelstrom.arcaneMechina.block;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.maelstrom.arcaneMechina.init.InitBlock;
import com.maelstrom.arcaneMechina.init.InitItem;
import com.maelstrom.arcaneMechina.reference.Reference;


//make class meta base
public class BlockOre extends Block {
	public String[] nameList;
	public String group;
	private IIcon[] icon;
	
	public BlockOre(String[] list, String group) {
		super(Material.iron);
		this.nameList = list;
		this.group = group;
		this.setBlockName(Reference.MOD_ID+"."+"ore_");
		this.setCreativeTab(Reference.MOD_TAB);
		this.setHardness(2.0f);
		this.setResistance(6.0f);
		this.setStepSound(soundTypeStone);
	}
	
	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
	
    public int quantityDropped(Random r){
        return this == InitBlock.gemOre ? 1 : 1;
    }
    
	@Override
	public void registerBlockIcons(IIconRegister reg){
		icon = new IIcon[nameList.length];
		for(int i = 0; i < nameList.length; i++)
			icon[i] = reg.registerIcon(Reference.MOD_ID+":"+capitalize(nameList[i])+"_Ore");
	}
	
	@Override
	public IIcon getIcon(int side, int meta){
		if(meta > nameList.length)
			meta = 0;
		return icon[meta];
	}
	
	public String capitalize(String s){
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		for(int i = 0; i < this.nameList.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
	
    public Item getItemDropped(int m, Random r, int f)
    {
    	if(this == InitBlock.gemOre)
    		return InitItem.gem;
    	else if (this == InitBlock.metalOre)
    		return Item.getItemFromBlock(this);
    	else return Item.getItemFromBlock(Blocks.cobblestone);
    }
}
