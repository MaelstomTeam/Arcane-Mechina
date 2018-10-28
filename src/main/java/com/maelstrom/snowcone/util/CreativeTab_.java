package com.maelstrom.snowcone.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTab_ extends CreativeTabs {
	private ItemStack icon;
	
	public CreativeTab_(String lablel, ItemStack tabIcon) {
		super(lablel);
		icon = tabIcon;
	}

	@Override
	public ItemStack getTabIconItem() {
    	if(icon !=null)
    		return icon;
    	else
    		return new ItemStack(Blocks.BARRIER);
	}
	
    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel(){
		return "itemGroup." + this.getTabLabel() + ".name";
    }
    
}
