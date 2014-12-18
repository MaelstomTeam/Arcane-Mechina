package com.maelstrom.arcaneMechina.block;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.maelstrom.arcaneMechina.reference.Reference;


//make class meta base
public class BlockOre extends Block {
	public static final String[] gemStones = { "opal", "amber", "zircon", "tourmaline", "sapphire" };
	public static final String[] preciousMetals = {"silver", "lead", "tin", "copper", "zinc"};
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
	
	@Override
	public void registerBlockIcons(IIconRegister reg){
		icon = new IIcon[nameList.length];
		for(int i = 0; i < nameList.length; i++)
			icon[i] = reg.registerIcon(Reference.MOD_ID+":"+"ore_"+nameList[i]);
	}
	
	@Override
	public IIcon getIcon(int side, int meta){
		if(meta > nameList.length)
			meta = 0;
		return icon[meta];
	}
	
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		for(int i = 0; i < this.nameList.length; i++)
			list.add(new ItemStack(item, 1, i));
	}
}
