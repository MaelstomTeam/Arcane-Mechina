package com.maelstrom.arcaneMechina.Item;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.maelstrom.arcaneMechina.client.model.ModelGhostWings;
import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer.RenderLocation;
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

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			if(!ply.capabilities.isCreativeMode && !ply.capabilities.allowFlying)
				ply.capabilities.allowFlying = true;
			if(!ply.capabilities.isCreativeMode)
		        if (!ply.onGround && ply.fallDistance > 10f) {
		        	if(!ply.isSneaking()){
		        		ply.motionY += .09999999999D;
		        		if(ply.motionY > -0.1D)
		        			ply.motionY = -0.1D;
		        	}
		        	else{
		        		ply.motionY *= 1.1D;
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
			if(!ply.capabilities.isCreativeMode)
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
