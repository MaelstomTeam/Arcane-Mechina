package com.maelstrom.arcaneMechina.Item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.common.container.InventoryBaubles;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.arcaneMechina.client.model.ModelGhostWings;
import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemPegasusWingAmulet extends ExtendableItem implements IBauble, IBaubleRenderer {
	
	private IIcon icon;
	
	public ItemPegasusWingAmulet(String name) {
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
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}
	
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat)
    {
        return false;
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
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			if(!ply.capabilities.isCreativeMode && !ply.capabilities.allowFlying && PlayerHandler.getPlayerBaubles(ply).getStackInSlot(0) == itemstack)
				ply.capabilities.allowFlying = true;
			else if(!ply.capabilities.isCreativeMode)
		        if (!ply.onGround && ply.fallDistance > 1f) {
		        	if(ply.isSneaking()){
		        		ply.fallDistance = 1f; 
		        		ply.motionY += .09999999D;
		        		if(ply.motionY > -0.1D)
		        			ply.motionY = -0.1D;
		        	}
		        }
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		if(!player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player, "arcanemechina:equipBauble", 0.1F, 1.3F);
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			ply.capabilities.allowFlying = true;
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			if(!ply.capabilities.isCreativeMode)
				ply.capabilities.allowFlying = false;
		}
		if(!player.worldObj.isRemote)
			player.worldObj.playSoundAtEntity(player, "arcanemechina:unequipBauble", 0.1F, 1.3F);
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public void onPlayerBaubleRenderer(EntityPlayer player, Post event) {
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("Minecraft:textures/blocks/stone.png"));
//		System.out.println("Test");
		GL11.glScaled(0.1, 0.1, 0.1);
		try{ModelGhostWings.wingsTemp.render(player, 0, 0, event.partialRenderTick, 0, 0, 1);}
		catch(Error e){System.out.println("Error");}
	}

	@Override
	public RenderLocation getRenderLocation() {
		return RenderLocation.BODY;
	}
}
