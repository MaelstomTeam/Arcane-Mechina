package com.maelstrom.arcanemechina.client.gui;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.client.ClientProxy;
import com.maelstrom.arcanemechina.common.RecipeHelper;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.RuneDrawingContainer;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.HoldingRune;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.IRuneRenderer;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
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
	}

	public void render(int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground();
		GlStateManager.pushMatrix();
		GlStateManager.color4f(1f, 1f, 1f, 1f);
		bindTexture(SCREEN);
		this.blit((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 0, 0, this.xSize, this.ySize);
		GlStateManager.popMatrix();

		
		GlStateManager.pushMatrix();
		GlStateManager.translated((this.width - (16 * 5)) / 2, this.height / 2 - (16 * 5), 10);
		GlStateManager.scaled(5, 5, 5);
		GlStateManager.rotated(90, -1, 0, 0);
		ClientProxy.Toggle();
		GlStateManager.pushMatrix();
		if (rune_instance != null)
			rune_instance.render(partialTicks);
		ClientProxy.Toggle();
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();
		super.render(mouseX, mouseY, partialTicks);

		GlStateManager.pushMatrix();
		GlStateManager.translatef((float) this.guiLeft, (float) this.guiTop, 0.0F);
		if(Screen.hasShiftDown() 
				&& this.hoveredSlot != null 
				&& this.hoveredSlot.getStack() != ItemStack.EMPTY
				&& this.hoveredSlot.getStack().getItem() == Registry.blueprint_recipe)
		{
			GlStateManager.disableDepthTest();
			ItemStack item = RecipeHelper.getRecipe(Registry.PROXY.getClientWorld(), this.hoveredSlot.getStack()).getRecipe(Registry.PROXY.getClientWorld()).getRecipeOutput();
			IRuneRenderer.getItemRenderer().renderItemIntoGUI(item, hoveredSlot.xPos, hoveredSlot.yPos);
			IRuneRenderer.getItemRenderer().renderItemAndEffectIntoGUI(item, hoveredSlot.xPos, hoveredSlot.yPos);
			GlStateManager.enableDepthTest();
		}
		else if (Screen.hasShiftDown()
				&& this.hoveredSlot != null 
				&& this.hoveredSlot.getStack() != ItemStack.EMPTY
				&& this.hoveredSlot.getStack().getItem() == Items.CRAFTING_TABLE)
		{
			GlStateManager.disableDepthTest();
			ItemStack item = ((HoldingRune)rune_instance.getRune(RuneType.HoldingRune.class).get(0)).getStackInSlot(1);
			IRuneRenderer.getItemRenderer().renderItemIntoGUI(item, hoveredSlot.xPos, hoveredSlot.yPos);
			IRuneRenderer.getItemRenderer().renderItemAndEffectIntoGUI(item, hoveredSlot.xPos, hoveredSlot.yPos);
			GlStateManager.enableDepthTest();
		}
		GlStateManager.popMatrix();
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

	public RuneContainer getRune()
	{
		return rune_instance;
	}

	public void setRune(RuneContainer rune_instance)
	{
		this.rune_instance = rune_instance;
	}

	private ResourceLocation SCREEN = new ResourceLocation(ArcaneMechina.MODID, "textures/gui/rdc_background.png");

	@Override protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){}
	
	static void bindTexture(ResourceLocation resource)
	{
		Minecraft.getInstance().getTextureManager().bindTexture(resource);
	}


}
