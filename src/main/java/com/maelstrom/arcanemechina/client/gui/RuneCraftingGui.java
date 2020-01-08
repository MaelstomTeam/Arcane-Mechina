package com.maelstrom.arcanemechina.client.gui;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.RuneCraftingContainer;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class RuneCraftingGui extends ContainerScreen<RuneCraftingContainer> implements IHasContainer<RuneCraftingContainer>
{
	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(ArcaneMechina.MODID, "textures/gui/blueprint_background.png");

	public RuneCraftingGui(RuneCraftingContainer con, PlayerInventory inv, ITextComponent title)
	{
		super(con, Registry.PROXY.getClientPlayer().inventory, title);
		this.passEvents = false;
	}

	protected void init()
	{
		super.init();
	}

	public void tick()
	{
		super.tick();
	}

	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	public boolean keyPressed(int id, int x, int y)
	{
		if (id == 'e')
			keyPressed(256, x, y);
		InputMappings.Input mouseKey = InputMappings.getInputByCode(id, x);
		if (id == 256 || this.minecraft.gameSettings.keyBindInventory.isActiveAndMatches(mouseKey))
		{
			this.minecraft.player.closeScreen();
			return true;
		}
		return false;
	}

	static void bindTexture(ResourceLocation resource)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = (this.height - this.ySize) / 2;
		this.blit(i, j, 0, 0, this.xSize, this.ySize);
	}

}
