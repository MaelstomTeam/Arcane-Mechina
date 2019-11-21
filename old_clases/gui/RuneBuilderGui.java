package com.maelstrom.arcanemechina.client.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneSize;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.util.text.TranslationTextComponent;

public class RuneBuilderGui extends Screen {
	RuneTileEntity rune_tile;
	RuneContainer rune = new RuneContainer();
	protected RuneBuilderGui(RuneTileEntity tile) {
		super(new TranslationTextComponent("rune.builder"));
		rune_tile = tile;
	}
	private boolean showCreationList;
	private Button create_rune;
	private RuneWidgetList runelist;
	
	public void setCurrenRune(short runeID)
	{
		//this.buttons.add(new Button(x, y, 10, 10, null, null));
	}
	
	//RuneBuilderGui.this.buttons.add(new RunePartRender.RuneContainerRender(50,50,20,20, null, null));
	protected void init() {
		this.buttons.add(create_rune = new Button(0,0,10,10,"",new IPressable()
				{
					@Override
					public void onPress(Button button) 
					{
						if(button == create_rune)
							showCreationList();
					}
			
				}));
		runelist = new RuneWidgetList(this);
		this.children.add(runelist);
	}
	
	public void showCreationList()
	{
		runelist.setActive(true);
		showCreationList = true;
	}
	public void hideCreationList()
	{
		runelist.setActive(false);
		showCreationList = false;
	}

	public void removed() {
		// disable editing the rune
	}

	public void tick() {
		if (!this.rune_tile.getType().isValidBlock(this.rune_tile.getBlockState().getBlock())) {
			this.close();
		}
	}

	private void close() {
		this.rune_tile.markDirty();
		this.minecraft.displayGuiScreen((Screen) null);
	}

	public void onClose() {
		this.close();
	}

	public void render(int x, int y, float zDepth) {
		this.renderBackground();
		this.drawCenteredString(this.font, "Rune Builder MK.I", this.width / 2, 0, 16777215);
		GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.pushMatrix();
		// draw screen!
		if (rune_tile != null) {
		}
		GlStateManager.popMatrix();
		if(showCreationList)
			runelist.render(x, y, zDepth);
		super.render(x, y, zDepth);
	}

	public static abstract class RunePartRender extends Button {

		protected final List<AbstractGui> buttons = Lists.newArrayList();
		protected Button set;
		protected Button edit;
		protected Button remove;
		private RuneBuilderGui gui;
		private RuneType rune;
		public RunePartRender(int x, int y, int width, int height, IPressable onPress, RuneType type, RuneBuilderGui gui) {
			super(x, y, width, height, type.getID() + "", onPress);
			this.gui = gui;
			set = new Button(x, y, 10, 10, "", new IPressable() {

				@Override
				public void onPress(Button button) {
					if (button == set)
						try {
							gui.rune.setChildren(rune, gui.rune.getEmptyChildIndex());
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			});
			edit = new Button(x, y, 10, 10, "", new IPressable() {

				@Override
				public void onPress(Button button) {
					if (button == edit)
						RunePartRender.this.edit();
				}
			});
			remove = new Button(x + 10, y, 10, 10, "", new IPressable() {

				@Override
				public void onPress(Button button) {
					if (button == remove)
						RunePartRender.this.remove();
				}

			});
		}

		protected abstract void edit();
		protected void remove() {
			gui.buttons.remove(this);
		}

		public static class RuneSizeButton extends RunePartRender {
			public RuneSize rune_size = RuneSize.TINY;
			public RuneSizeWidgetList list;
			public RuneSizeButton(int x, int y, int width, int height, IPressable onPress, RuneBuilderGui gui) {
				super(x, y, width, height, onPress, new RuneContainer(), gui);
			}

			protected void hideList() {
				buttons.remove(list);
			}
			
			protected void showList() {
				buttons.add(list = new RuneSizeWidgetList(this));
			}

			@Override
			protected void edit() {
				showList();
			}

		}

	}

}