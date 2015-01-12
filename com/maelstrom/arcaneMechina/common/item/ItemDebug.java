package com.maelstrom.arcaneMechina.common.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.block.BlockOre;
import com.maelstrom.arcaneMechina.common.handler.ArcaneMechinaNbt;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemDebug extends ExtendableItem {
	
	private IIcon icon;
	
	public ItemDebug(String name) {
		super(name, Reference.MOD_ID);
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	//#really bad elvish
    	l.add("�2"+StatCollector.translateToLocal(getUnlocalizedName() + ".lore"));
    }
    
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat){
    	if(!w.isRemote)
	    	if(w.getTileEntity(x, y, z) != null && !ply.isSneaking()){
				ply.addChatComponentMessage(new ChatComponentText("TileEntity: "+w.getTileEntity(x, y, z).toString().split("@")[0]));
	    	}else{
	    		ply.addChatComponentMessage(new ChatComponentText(w.getBlock(x, y, z).getUnlocalizedName()));
	    	}
//    	else{
//    		if(!w.isRemote)
//    			ply.addChatComponentMessage(new ChatComponentText("working..."));
//    		for(int i = 0; i < 256; i++)
//        		for(int x2 = -8; x2 < 8; x2++)
//            		for(int z2 = -8; z2 < 8; z2++)
//		    			if(!(w.getBlock(x + x2, i, z + z2) instanceof BlockOre || w.getBlock(x + x2, i, z + z2) == Blocks.bedrock))
//		    				w.setBlock(x + x2, i, z + z2, Blocks.air);
//    		if(!w.isRemote)
//    			ply.addChatComponentMessage(new ChatComponentText("Done!"));
//    	}
        return false;
    }
    
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ply)
    {
		ArcaneMechinaNbt temp = new ArcaneMechinaNbt(ply);
    	if(ply.isSneaking() && ply.rotationPitch == -90f){
			if(w.isRemote){
		    	ply.addChatComponentMessage(new ChatComponentText("�2Begining NBT Debug: "));
		    	ply.addChatComponentMessage(new ChatComponentText("     �5Server: "));
			}
			else
		    	ply.addChatComponentMessage(new ChatComponentText("     �3Client: "));
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