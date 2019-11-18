package com.maelstrom.arcanemechina.client.tesr;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.HOLD;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.TOGGLE;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.VARIBLE;
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
		if(Minecraft.getInstance().player.getHeldItemMainhand().getItem() == Registry.chalk && Minecraft.getInstance().player.isCreative())
		{
			GlStateManager.pushMatrix();
			GlStateManager.translated(.5, 2, .5);
			GlStateManager.rotated(-Minecraft.getInstance().player.rotationYawHead + 180d + Minecraft.getInstance().player.cameraYaw, 0, 1, 0);
			GlStateManager.translated(-.5,0,0);
			GlStateManager.rotated(180, 1, 0, 0);
			GlStateManager.scaled(.1, .1, .1);
			GlStateManager.scaled(.1, .1, .1);
			drawRuneDebug(te.rune);
			GlStateManager.popMatrix();
		}
		GlStateManager.scaled(1f/16f, 1f/16f, 1f/16f);
		te.rune.render(te, x, y, z, partialTicks, destroyStage);
		//this.getFontRenderer().drawString("WHOA I HAVE TEXT ON ME!", (float)0, (float)0, 0x555555);
		GlStateManager.popMatrix();
		sub = 0;
	}
	int sub = 0;
	public void drawRuneDebug(RuneType rune)
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
			for(RuneType rune2 : rune.getChildren())
			{
				if(rune2!=null)
					drawRuneDebug(rune2);
			}
		GlStateManager.translated(-10, 0, 0);
	}
}
