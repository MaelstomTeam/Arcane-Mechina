package com.maelstrom.arcanemechina.client.gui;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.RuneDrawingContainer;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RuneCraftingGui extends ContainerScreen<RuneDrawingContainer> implements IHasContainer<RuneDrawingContainer>
{
	public RuneCraftingGui(RuneDrawingContainer con, PlayerInventory inv, ITextComponent title)
	{
		super(con, Registry.PROXY.getClientPlayer().inventory, title);
		this.passEvents = false;
	}

	public static TranslationTextComponent title = new TranslationTextComponent("gui.arcanemechina.runecrafting");
	private RuneContainer rune_instance;

	protected void init()
	{
		super.init();
		this.xSize = 256;
		this.ySize = 218;
		rune_instance = ((RuneTileEntity) this.getContainer().getTileEntity()).getRuneContainer();
		int x = -22;
		int y = 136;
		int width = 20;
		int height = 20;
		int relativeX = (this.width - this.xSize) / 2;
		int relativeY = (this.height - this.ySize) / 2;
		Button button;
		this.addButton(button = new Button(relativeX + x, relativeY + y, width, height, "+", (event) ->
		{
			ArcaneMechina.LOGGER.info("BUTTON!");
		}));
		button.active = false;
	}

	public void tick()
	{
		super.tick();
		rune_instance.tick(((RuneTileEntity) this.getContainer().getTileEntity()));
	}

	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
		
		
		GlStateManager.pushMatrix();
		GlStateManager.translated((this.width - (16 * 5)) / 2, this.height / 2, 10);
		GlStateManager.scaled(5, 5, 5);
		GlStateManager.rotated(90, -1, 0, 0);
		GlStateManager.rotated(90, 0, 1, 0);
		if (rune_instance != null)
			rune_instance.render(partialTicks);
		GlStateManager.popMatrix();
		
		

		if(rune_instance.getRune(RuneType.HoldingRune.class).size() > 0)
		{
			for(RuneType rune : rune_instance.getRune(RuneType.HoldingRune.class))
			{
				GlStateManager.pushMatrix();
				int relativeX = (this.width - (16 * 5)) / 2;
				int relativeY = (this.height) / 2 - 16;
				GlStateManager.translated(relativeX, relativeY, 0);
				ItemStack rune_item = ((RuneType.HoldingRune)rune).getStackInSlot(0);
				Vec2f pos = rune.getPosition();
				GlStateManager.scaled(1.5,1.5,1.5);
				this.itemRenderer.renderItemAndEffectIntoGUI(rune_item, ((int)pos.x * 5) - 3, -((int)pos.y * 2) + 1);
				this.itemRenderer.renderItemOverlayIntoGUI(font, rune_item, ((int)pos.x * 5)- 3, -((int)pos.y * 2) + 4, null);
				GlStateManager.translated(0, 0, 10);
				GlStateManager.popMatrix();
			}
		}
	}

	public boolean keyPressed(int id, int x, int y)
	{
		if (id == 'e')
			super.keyPressed(256, x, y);
		return super.keyPressed(id, x, y);
	}

	public RuneContainer getRune()
	{
		return rune_instance;
	}

	public void setRune(RuneContainer rune_instance)
	{
		this.rune_instance = rune_instance;
	}

	private ResourceLocation SCREEN = new ResourceLocation(ArcaneMechina.MODID, "textures/gui/rdc_background.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color4f(1f, 1f, 1f, 1f);
		bindTexture(SCREEN);
		int relativeX = (this.width - this.xSize) / 2;
		int relativeY = (this.height - this.ySize) / 2;
		this.blit(relativeX, relativeY, 0, 0, this.xSize, this.ySize);
	}

	static void bindTexture(ResourceLocation resource)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
	}

}
