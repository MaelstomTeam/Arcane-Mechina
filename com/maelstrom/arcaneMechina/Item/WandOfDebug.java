package com.maelstrom.arcaneMechina.Item;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WandOfDebug extends ExtendableItem {
	
	private IIcon icon;
	
	public WandOfDebug(String name) {
		super(name, Reference.MOD_ID);
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	//really bad elvish
    	l.add(StatCollector.translateToLocal(getUnlocalizedName() + ".lore"));
    }

	@Override
	public void registerIcons(IIconRegister iicon) {
		icon = iicon.registerIcon(Reference.MOD_ID+":"+getIconString());
	}
	
	
	//because for some reason icons are different?
    public IIcon getIconIndex(ItemStack is)
    {
        return icon;
    }
    
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        return icon;
    }

}