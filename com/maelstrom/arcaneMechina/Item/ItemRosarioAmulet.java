package com.maelstrom.arcaneMechina.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemRosarioAmulet extends ExtendableItem implements IBauble, IBaubleRenderer {

	public ItemRosarioAmulet(String name) {
		super(name, Reference.MOD_ID);
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".line1.lore"));
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".line2.lore"));
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
	public void onPlayerBaubleRenderer(EntityPlayer player, Post event) {
	}

	@Override
	public RenderLocation getRenderLocation() {
		return RenderLocation.FLOOR;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		// TODO Auto-generated method stub
		
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

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
