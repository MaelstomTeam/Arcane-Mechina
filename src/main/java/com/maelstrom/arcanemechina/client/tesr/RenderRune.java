package com.maelstrom.arcanemechina.client.tesr;

import com.maelstrom.arcanemechina.common.runic.IRuneType;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.HOLD;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.TOGGLE;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.VARIBLE;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

public class RenderRune extends TileEntityRenderer<RuneTileEntity> {
	public void render(RuneTileEntity te, double x, double y, double z, float partialTicks, int destroyStage) {
		super.render(te, x, y, z, partialTicks, destroyStage);

		// render runes
		GlStateManager.pushMatrix();
		GlStateManager.translated(x+.5, y+2, z+.5);
		GlStateManager.rotated(-Minecraft.getInstance().player.rotationYawHead + 180d + Minecraft.getInstance().player.cameraYaw, 0, 1, 0);
		GlStateManager.translated(-.5,0,0);
		GlStateManager.rotated(180, 1, 0, 0);
		GlStateManager.scaled(.1, .1, .1);
		GlStateManager.scaled(.1, .1, .1);
		drawRune(te.rune);
		//this.getFontRenderer().drawString("WHOA I HAVE TEXT ON ME!", (float)0, (float)0, 0x555555);
		GlStateManager.popMatrix();
		sub = 0;
	}
	int sub = 0;
	public void drawRune(IRuneType rune)
	{
		sub++;
		switch(rune.getID())
		{
		case -1:
			this.getFontRenderer().drawString("Container: " + ((RuneContainer)rune).getCapacity(), (float)0, (float)0, 0x000000);
			break;
		case 0:
			this.getFontRenderer().drawString("Varible: " + ((VARIBLE)rune).getValue(), (float)0, (float)0, 0x000000);
			break;
		case 1: 
			this.getFontRenderer().drawString("Toggle: " + ((TOGGLE)rune).getState() + " " + (((TOGGLE)rune).counter), (float)0, (float)0, 0x000000);
			break;
		case 2: 
			this.getFontRenderer().drawString("Hold: " + ((HOLD)rune).getItemInput().get(0).getDisplayName().getFormattedText() + ", uses: " + (((HOLD)rune).getItemInput().get(0).getMaxDamage() - ((HOLD)rune).getItemInput().get(0).getDamage()) + " " +((HOLD)rune).getItemInput().get(0).getCount(), (float)0, (float)0, 0x000000);
			break;
		case 3: 
			this.getFontRenderer().drawString("Split", (float)0, (float)0, 0x000000);
			break;
		case 4: 
			this.getFontRenderer().drawString("Insert", (float)0, (float)0, 0x000000);
			break;
		case 5: 
			this.getFontRenderer().drawString("Extract", (float)0, (float)0, 0x000000);
			break;
		case 6: 
			this.getFontRenderer().drawString("Transmute", (float)0, (float)0, 0x000000);
			break;
		case 7: 
			this.getFontRenderer().drawString("Consume", (float)0, (float)0, 0x000000);
			break;
		}
		GlStateManager.translated(10, 10, 0);
		if(sub < 100)
			for(IRuneType rune2 : rune.getChildren())
			{
				if(rune2!=null)
					drawRune(rune2);
			}
		GlStateManager.translated(-10, 0, 0);
	}
}
