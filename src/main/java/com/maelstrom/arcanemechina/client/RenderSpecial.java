package com.maelstrom.arcanemechina.client;

import java.util.HashMap;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneHelper;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class RenderSpecial
{
	private static HashMap<ItemStack, RuneContainer> quickList = new HashMap<ItemStack, RuneContainer>();

	static boolean isRuneBlueprint(ItemStack item)
	{
		return item != ItemStack.EMPTY && item.getItem() == Registry.blueprint_rune && item.getTag() != null && item.getTag().get("rune_data") != null;
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void ItemTooltipEvent(ItemTooltipEvent event)
	{
		if (isRuneBlueprint(event.getItemStack()))
		{
			RuneContainer runecontainer;
			if (!quickList.containsKey(event.getItemStack()))
			{
				runecontainer = RuneHelper.fromItem(event.getItemStack());
			} else
			{
				runecontainer = quickList.get(event.getItemStack());
			}

			if (runecontainer != null)
			{
				if (Screen.hasShiftDown())
				{
					event.getToolTip().remove(event.getToolTip().size() - 1);
					tempIndex = event.getToolTip().size() + 1;
					event.getToolTip().add(new StringTextComponent("                  "));
					event.getToolTip().add(new StringTextComponent(""));
					event.getToolTip().add(new StringTextComponent(""));
					event.getToolTip().add(new StringTextComponent(""));
					event.getToolTip().add(new StringTextComponent(""));
					event.getToolTip().add(new StringTextComponent(""));
				}
			}

		}
	}

	static int tempIndex = 0;

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public static void RenderTooltipEvent(RenderTooltipEvent.PostText event)
	{
		try
		{
			if (isRuneBlueprint(event.getStack()) && Screen.hasShiftDown())
			{
				Minecraft mc = Minecraft.getInstance();
				RuneContainer runecontainer;
				if (!quickList.containsKey(event.getStack()))
				{
					runecontainer = RuneHelper.fromItem(event.getStack());
				} else
				{
					runecontainer = quickList.get(event.getStack());
				}
				if (runecontainer != null)
				{
					GlStateManager.pushMatrix();
					int offset = (tempIndex) * event.getFontRenderer().FONT_HEIGHT;
					mc.getTextureManager().bindTexture(new ResourceLocation("minecraft", "textures/map/map_background.png"));

					GlStateManager.pushMatrix();
					Tessellator tessellator = Tessellator.getInstance();
					BufferBuilder bufferbuilder = tessellator.getBuffer();
					GlStateManager.color4f(0.0F, 0.0F, 255.0F, 255.0F);
					GlStateManager.disableTexture();
					GlStateManager.enableColorLogicOp();
					GlStateManager.logicOp(GlStateManager.LogicOp.OR_REVERSE);

					float startX = event.getX()-2;
					float startY = event.getY()+offset-8;
					float endX = 74 + event.getX();
					float endY = 54 + event.getY()+offset;
					bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
					bufferbuilder.pos((double) startX, (double) endY, 0.0D).endVertex();
					bufferbuilder.pos((double) endX, (double) endY, 0.0D).endVertex();
					bufferbuilder.pos((double) endX, (double) startY, 0.0D).endVertex();
					bufferbuilder.pos((double) startX, (double) startY, 0.0D).endVertex();
					tessellator.draw();
					GlStateManager.disableColorLogicOp();
					GlStateManager.enableTexture();
					GlStateManager.popMatrix();
					GlStateManager.translatef(event.getX() + (24 * runecontainer.getRuneScale()) - 20, event.getY() + (24 / runecontainer.getRuneScale()) + 26 + offset - 1f, 0);
					GlStateManager.pushMatrix();
					GlStateManager.translatef(0, 0, 700);
					GlStateManager.rotated(90, -1, 0, 0);
					GlStateManager.rotated(90, 0, 1, 0);
					GlStateManager.scaled(3f, 3f, 3f);
					GlStateManager.scaled(1f / runecontainer.getRuneScale(), 1f / runecontainer.getRuneScale(), 1f / runecontainer.getRuneScale());
					runecontainer.render(0f);
					AbstractGui.blit(event.getX(), event.getY(), 50, 50, 0xFFFFFF, 0, 0, 256, 256);
					GlStateManager.popMatrix();
					GlStateManager.popMatrix();

				}
			}
		} catch (Exception e)
		{

		}
	}
}
