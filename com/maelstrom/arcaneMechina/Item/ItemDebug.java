package com.maelstrom.arcaneMechina.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.handler.ArcaneMechinaNbt;
import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemDebug extends ExtendableItem {
	
	private IIcon icon;
	
	public ItemDebug(String name) {
		super(name, Reference.MOD_ID);
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	//#really bad elvish
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".lore"));
    }
    
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat){
    	if(!w.isRemote && w.getTileEntity(x, y, z) != null){
			ply.addChatComponentMessage(new ChatComponentText("TileEntity: "+w.getTileEntity(x, y, z).toString().split("@")[0]));
    			return true;
    	}
        return false;
    }
    
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ply)
    {
		ArcaneMechinaNbt temp = new ArcaneMechinaNbt(ply);
    	if(ply.isSneaking() && ply.rotationPitch == -90f){
			if(w.isRemote){
		    	ply.addChatComponentMessage(new ChatComponentText("§2Begining NBT Debug: "));
		    	ply.addChatComponentMessage(new ChatComponentText("     §5Server: "));
			}
			else
		    	ply.addChatComponentMessage(new ChatComponentText("     §3Client: "));
			ply.addChatComponentMessage(new ChatComponentText("          hasLogged: "+temp.hasLoggedPreviously()));
    	}
    	else if(ply.isSneaking() && ply.rotationPitch == 90f){
    		temp.setBoolean("hasLogged", false);
    	}
    	else if(!ply.isSneaking() && ply.rotationPitch == 90f){
    		temp.setBoolean("hasLogged", true);
    	}
    	return is;
    }
}