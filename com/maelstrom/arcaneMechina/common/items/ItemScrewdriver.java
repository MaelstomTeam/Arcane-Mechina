package com.maelstrom.arcaneMechina.common.items;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import cofh.api.energy.IEnergyStorage;

import com.maelstrom.arcaneMechina.common.blocks.BlockCompressedArray;
import com.maelstrom.arcaneMechina.common.blocks.ModBlocks;
import com.maelstrom.arcaneMechina.common.interfaces.IPower;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemScrewdriver extends ExtendableItem {

private IIcon icon;
	IPower tile;
	public ItemScrewdriver(String name) {
		super(name, Reference.MOD_ID);
		this.maxStackSize = 1;
	}
	
	public boolean listContainsStack(List<EntityItem> list, ItemStack is, boolean consume){
		for(EntityItem item : list)
			if(item.getEntityItem().isItemEqual(is)){
				if(consume)
					item.getEntityItem().stackSize -= is.stackSize;
				return true;
			}
		return false;
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".line1.lore"));
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".line2.lore"));
    	if(ply.capabilities.isCreativeMode && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)))
    		l.add("§dPointless in Creative Mode");
    }
    
    @SuppressWarnings("unused")
	public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat)
    {
    	if(ply.isSneaking()){
    		if(w.getBlock(x, y, z).equals(Blocks.furnace) && w.getBlock(x, y+1, z).equals(Blocks.furnace) && w.getBlock(x, y+2, z).equals(Blocks.heavy_weighted_pressure_plate)){
    			w.setBlock(x, y, z, ModBlocks.basicPowerGen, 0, 2);
    			w.setBlock(x, y+1, z, ModBlocks.basicPowerGen, 1, 2);
    			w.setBlock(x, y+2, z, Blocks.air);
    			return true;
    		}else if(w.getBlock(x, y, z).equals(Blocks.quartz_block)){
    			w.setBlock(x, y, z, ModBlocks.rfStorageArray);
    			return true;
//    		}else if(w.getBlock(x, y, z).equals(ModBlocks.glyphBlank)){
//    			w.setBlockMetadataWithNotify(x, y, z, 1, 2);
//    			return true;
    		}
    	}else if(w.getTileEntity(x, y, z) instanceof IEnergyStorage){
			IEnergyStorage tile = (IEnergyStorage) w.getTileEntity(x, y, z);
			if(!w.isRemote)
				ply.addChatComponentMessage(new ChatComponentText(convert(tile.getEnergyStored()) + "/" + convert(tile.getMaxEnergyStored()) + " RF"));
		}
    	
    	
    	
    	if(false){
    		for (int i = -9; i <= 9; i++){
    			for (int i2 = -9; i2 <= 9; i2++){
    				if(w.getBlock(x + i, y, z + i2).equals(ModBlocks.glyphBlank))
    					System.out.print("#");
    				else
    					System.out.print(" ");
    			}
    			System.out.println();
    		}
    	}
        return super.onItemUse(is, ply, w, x, y, z, face, xFloat, yFloat, zFloat);
    }
    private String convert(int i){
    	if(i >= 1000000){
    		int temp = i / 1000000;
    		return temp + "M";
    	}else if(i >= 1000){
    		int temp = i / 1000;
    		return temp + "K";
    	}
    	return "" + i;
    }
    
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ply)
    {
    	/*if(ply.isSneaking())
    		tile = null;*/
        return is;
    }

}
