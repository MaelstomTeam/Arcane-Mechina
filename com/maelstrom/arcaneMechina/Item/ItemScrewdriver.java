package com.maelstrom.arcaneMechina.Item;

import java.util.List;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;

import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemScrewdriver extends ExtendableItem {

private IIcon icon;
	
	public ItemScrewdriver(String name) {
		super(name, Reference.MOD_ID);
		this.maxStackSize = 1;
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".line1.lore"));
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".line2.lore"));
    	if(ply.capabilities.isCreativeMode && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
    		l.add("§dPointless in Creative Mode");
    }

	@Override
	public void registerIcons(IIconRegister iicon) {
		icon = iicon.registerIcon(Reference.MOD_ID+":"+getIconString());
	}
	
	
    public IIcon getIconIndex(ItemStack is)
    {
        return icon;
    }
    
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
    {
        return icon;
    }

}
