package com.maelstrom.snowcone.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CreativeTabCustom extends CreativeTabs {
	private ItemStack icon;
	
	public CreativeTabCustom(String lablel, ItemStack tabIcon) {
		super(lablel);
		icon = tabIcon;
	}

	@Override
	public ItemStack createIcon() {
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
