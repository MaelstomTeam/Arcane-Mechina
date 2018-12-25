package com.maelstrom.snowcone.item.sonic;

import org.lwjgl.opengl.GL11;

import com.maelstrom.snowcone.SnowCone;
import com.maelstrom.snowcone.item.ItemSonic;
import com.maelstrom.snowcone.network.PacketHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid=SnowCone.MODID)
public class ToolTickHandler {
	@SubscribeEvent
	public static void onMouseEvent(MouseEvent event) {
		EntityPlayer player = Minecraft.getMinecraft().player;
		if(event.getDwheel() == 0)
			return;
		boolean mode = event.getDwheel() > 0;
		if(player.isSneaking() && player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemSonic)
		{
			PacketHandler.INSTANCE.sendToServer(new SonicPacketProcessor(player.inventory.currentItem,mode));
//			SonicInventory s = SonicInventory.getInventory(player.getHeldItem(EnumHand.MAIN_HAND))s;
//			s.setCurrentItem(s.getIndex() + (mode? 1 : -1));
//			s.writeData();
//			player.getHeldItem(EnumHand.MAIN_HAND).setTagInfo("ItemInventory", s.getCompound());
			event.setCanceled(true);
		}
	}

	@SubscribeEvent
	public static void onRenderExperienceBar(RenderGameOverlayEvent.Post event)
	{
		if(Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND) != null)
		{
			ItemStack stack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND);
			if(stack.getItem() instanceof ItemSonic)
			{
				SonicInventory sonic = SonicInventory.getInventory(stack);
				if(sonic.getCurrentItem() != null)
					render(sonic.getCurrentItem());
			}
		}
		//may drop this
		else if(Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND) != null)
		{
			ItemStack stack = Minecraft.getMinecraft().player.getHeldItem(EnumHand.OFF_HAND);
			if(stack.getItem() instanceof ItemSonic)
			{
				SonicInventory sonic = SonicInventory.getInventory(stack);
				if(sonic.getCurrentItem() != null)
					render(sonic.getCurrentItem());
			}
		}
			
	}
	private static void render(ItemStack is)
	{
		//write info on screen
		GL11.glPushMatrix();
        GlStateManager.translate(0.0F, 0.0F, 32.0F);
        float temp = Minecraft.getMinecraft().getRenderItem().zLevel;
        Minecraft.getMinecraft().getRenderItem().zLevel = 200f;
        net.minecraft.client.gui.FontRenderer font = is.getItem().getFontRenderer(is);
        if (font == null) font = Minecraft.getMinecraft().fontRenderer;
        Minecraft.getMinecraft().getRenderItem().renderItemAndEffectIntoGUI(is, 1, 1);
        font.drawString(is.getDisplayName(), 20, 9 - font.FONT_HEIGHT / 2, 16777215);
        Minecraft.getMinecraft().getRenderItem().zLevel = temp;
        GL11.glColor3f(1f, 1f, 1f);
		GL11.glPopMatrix();
		Minecraft.getMinecraft().getTextureManager().bindTexture(Gui.ICONS);
	}
}
