package com.maelstrom.arcanemechina.client.tesr;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.CraftingContainerRune;
import com.maelstrom.arcanemechina.common.runic.RuneType.HoldingRune;
import com.maelstrom.arcanemechina.common.runic.RuneType.IORune;
import com.maelstrom.arcanemechina.common.runic.RuneType.RedstoneIORune;
import com.maelstrom.arcanemechina.common.runic.RuneType.ToggleRune;
import com.maelstrom.arcanemechina.common.runic.RuneType.VaribleRune;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class RenderRune extends TileEntityRenderer<RuneTileEntity> {
	public void render(RuneTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		super.render(te, x, y, z, partialTicks, destroyStage);
		// render runes
		GlStateManager.pushMatrix();
			GlStateManager.translated(x, y+.01d, z);
			if((Minecraft.getInstance().player.getHeldItemMainhand().getItem() == Registry.chalk || Minecraft.getInstance().player.getHeldItemOffhand().getItem() == Registry.chalk) && Minecraft.getInstance().player.isCreative())
			{
				GlStateManager.pushMatrix();
					GlStateManager.translated(.5, 2, .5);
					GlStateManager.rotated(-Minecraft.getInstance().player.rotationYawHead + 180d + Minecraft.getInstance().player.cameraYaw, 0, 1, 0);
					GlStateManager.translated(-.5,0,0);
					GlStateManager.rotated(180, 1, 0, 0);
					GlStateManager.scaled(.1, .1, .1);
					GlStateManager.scaled(.1, .1, .1);
					drawRuneContainerDebug(te.rune);
				GlStateManager.popMatrix();
			}
			GlStateManager.scaled(1f/16f, 1f/16f, 1f/16f);
			te.rune.render(partialTicks);
		GlStateManager.popMatrix();
		sub = 0;
	}
	int sub = 0;
	public void drawRuneContainerDebug(RuneContainer rune) {
		for(RuneType rune_to_render : rune.getChildren())
		{
			if(rune_to_render != null)
			{
				GlStateManager.translated(0, 10, 0);
				if(rune_to_render!=null)
					drawRuneDebug(rune_to_render);
			}
		}
	}
	public void drawRuneDebug(RuneType rune)
	{
		sub++;
		switch(rune.getID())
		{
		case 0:
			this.getFontRenderer().drawString("CraftingContainerRune: " + ((CraftingContainerRune)rune).getRecipe(this.getWorld()), (float)0, (float)0, 0x000000);
			break;
		case 1: 
			this.getFontRenderer().drawString("VaribleRune: " + ((VaribleRune)rune).getValue(), (float)0, (float)0, 0x000000);
			break;
		case 2: 
			this.getFontRenderer().drawString("HoldingRune: " + ((HoldingRune)rune).getStackInSlot(0).getDisplayName().getFormattedText() + ", uses: " + (((HoldingRune)rune).getStackInSlot(0).getMaxDamage() - ((HoldingRune)rune).getStackInSlot(0).getDamage()) + " " +((HoldingRune)rune).getStackInSlot(0).getCount(), (float)0, (float)0, 0x000000);
			break;
		case 3: 
			this.getFontRenderer().drawString("ToggleRune: " + (((ToggleRune)rune).getState() ? "Active" : "Inactive") + " T:" + ((ToggleRune)rune).getCounter(), (float)0, (float)0, 0x000000);
			break;
		case 4: 
			this.getFontRenderer().drawString("IORune: " + ((IORune)rune).getName(), (float)0, (float)0, 0x000000);
			break;
		case 5: 
			this.getFontRenderer().drawString("RedstoneIORune: R:" + ((RedstoneIORune)rune).getPower() + " P:" + ((RedstoneIORune)rune).canOutputRedstone(), (float)0, (float)0, 0x000000);
			break;
		}
	}
}
