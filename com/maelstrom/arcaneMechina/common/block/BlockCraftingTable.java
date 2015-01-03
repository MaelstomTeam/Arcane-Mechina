package com.maelstrom.arcaneMechina.common.block;

import java.util.List;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.api.power.IMagicAcceptor;
import com.maelstrom.arcaneMechina.common.init.InitBlock;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

public class BlockCraftingTable extends ExtendableBlock implements ITileEntityProvider {

	public String[] nameList;
	private IIcon[] icon;

	public BlockCraftingTable(String[] list, String local) {
		super(Material.wood, local, Reference.MOD_ID);
		this.nameList = list;
	}

	public BlockCraftingTable(String local) {
		super(Material.wood, local, Reference.MOD_ID);
	}
	
	@Override
	public int damageDropped(int meta) {
	    return meta;
	}
	
	@Override
	public IIcon getIcon(int side, int meta){
		if (this.equals(InitBlock.cTable)){
			if(meta > nameList.length)
				meta = 0;
			return icon[meta];
		}
		else
			return super.getIcon(side, meta);
	}
	
	public void getSubBlocks(Item item, CreativeTabs tab, List list){
		if (this.equals(InitBlock.cTable))
			for(int i = 0; i < this.nameList.length; i++)
				list.add(new ItemStack(item, 1, i));
		else
			super.getSubBlocks(item, tab, list);
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg){
		if (this.equals(InitBlock.cTable)){
			icon = new IIcon[nameList.length];
			for(int i = 0; i < nameList.length; i++)
				icon[i] = reg.registerIcon(Reference.MOD_ID+":"+nameList[i]);
		}
		else
			super.registerBlockIcons(reg);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
//		if (this.equals(InitBlock.cTable))
//			switch(meta){
//				case 0: return TileEntityCraftingMagic();
//				case 1: return TileEntityCraftingTech();
//				default : return null;
//			}
//		else if (this.equals(InitBlock.magiTechTable))
//			return TileEntityCraftingMagiTech();
		return null;
	}

}
