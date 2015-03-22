package com.maelstrom.arcaneMechina.common.items;

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
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import baubles.api.BaubleType;
import baubles.common.lib.PlayerHandler;

import com.maelstrom.arcaneMechina.client.model.ModelGhostWings;
import com.maelstrom.arcaneMechina.common.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.common.reference.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ItemGhostWingAmmulet extends ItemBaubleEx implements IBaubleRenderer {
	
	private IIcon icon;
	
	public ItemGhostWingAmmulet(String name) {
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
    
    
	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			if(!ply.capabilities.isCreativeMode)
		        if (ply.fallDistance > 1f) {
		        	if(ply.isSneaking() && !ply.capabilities.isFlying){
		        		ply.motionY += .09999999D;
		        		if(ply.motionY > -0.1D)
		        			ply.motionY = -0.1D;
		        	}
		        }
		}
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		super.onEquipped(itemstack, player);
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			ply.jumpMovementFactor *= 2;
			ply.capabilities.allowFlying = true;
		}
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		super.onUnequipped(itemstack, player);
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			if(!ply.capabilities.isCreativeMode){
				ply.capabilities.isFlying = false;
				ply.capabilities.allowFlying = false;
			}
		}
		player.fallDistance = 0f;
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
		// This is for @Spor hope you use it later
		//event.renderer.modelBipedMain.bipedBody.isHidden = true;
		//event.renderer.modelBipedMain.bipedEars.isHidden = false;
		
		Minecraft.getMinecraft().renderEngine.bindTexture(new ResourceLocation("Minecraft:textures/blocks/stone.png"));
		if(!player.isSneaking() && player.capabilities.isFlying){
			GL11.glTranslated(0, 0, -.1);
			GL11.glRotated(35, 1, 0, 0);
		}
		try{
			ModelGhostWings.wingsTemp.render(player, 0, 0, event.partialRenderTick, 0, 0, .1f);
			}
		catch(Error e){
			System.out.println("Error in Render Wings:\n" + e);
			}
	}

	@Override
	public RenderLocation getRenderLocation() {
		return RenderLocation.BODY;
	}
	
	@SubscribeEvent
	public void onPlayerJump(LivingJumpEvent event) {
		if(event.entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.entityLiving;
			ItemStack wing = PlayerHandler.getPlayerBaubles(player).getStackInSlot(0);

			if(PlayerHandler.getPlayerBaubles(player).getStackInSlot(0) != null)
				if(PlayerHandler.getPlayerBaubles(player).getStackInSlot(0).getItem() == this)
				player.motionY += 0.1;
		}
	}
}
