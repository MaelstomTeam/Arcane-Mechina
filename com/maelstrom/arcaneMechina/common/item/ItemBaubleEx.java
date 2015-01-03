package com.maelstrom.arcaneMechina.common.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.snowcone.extendables.ExtendableItem;

public abstract class ItemBaubleEx extends ExtendableItem implements IBauble {

	public ItemBaubleEx(String name, String modid) {
		super(name, modid);
	}
	
	public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ply)
    {
    	InventoryBaubles bauble = PlayerHandler.getPlayerBaubles(ply);
    	for (int i = 0; i < bauble.getSizeInventory(); i++){
    		if(bauble.isItemValidForSlot(i, is)){
    			ItemStack baubleIS = bauble.getStackInSlot(i);
    			if((baubleIS == null || ((IBauble) baubleIS.getItem()).canUnequip(baubleIS, ply)) && !w.isRemote){
					bauble.setInventorySlotContents(i, is.copy());
					if(!ply.capabilities.isCreativeMode)
						ply.inventory.setInventorySlotContents(ply.inventory.currentItem, null);
				}
    			onEquipped(is, ply);
    			if(baubleIS != null){
    				((IBauble) baubleIS.getItem()).onUnequipped(baubleIS, ply);
    				return baubleIS.copy();
    			}
    			break;
    		}
    	}
        return is;
    }
	
	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if(!player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player, "arcanemechina:equipBauble", 1F, 1.3F);
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		if(!player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player, "arcanemechina:unequipBauble", 1F, 1.3F);
	}
}
