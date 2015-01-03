package com.maelstrom.arcaneMechina.common.item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemMetadataBased extends ExtendableItem {
	public final String[] itemNames;
	public IIcon[] icons;
	public ItemMetadataBased(String name,String[] nameList) {
		super(name, Reference.MOD_ID);
		this.itemNames = nameList;
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	@Override
    public void registerIcons(IIconRegister iicon){
		icons = new IIcon[itemNames.length];
		for(int i = 0; i < itemNames.length; i++)
			icons[i] = iicon.registerIcon(Reference.MOD_ID+":"+getUnlocalizedName().substring(19)+"_"+itemNames[i]);
	}
	
	@Override
	public IIcon getIconFromDamage(int i){
		if(i >= itemNames.length)
			return icons[0];
		return icons[i];
	}

	@Override
    public void getSubItems(Item item, CreativeTabs tab, List l) {
		for(int i = 0; i < itemNames.length; i++)
			l.add(new ItemStack(item, 1, i));
	}

	@Override
    public String getUnlocalizedName(ItemStack is) {
    	if(is.getItemDamage() >= itemNames.length)
        	return this.getUnlocalizedName()+"_"+itemNames[0];
    	return this.getUnlocalizedName()+"_"+itemNames[is.getItemDamage()];
    }
}