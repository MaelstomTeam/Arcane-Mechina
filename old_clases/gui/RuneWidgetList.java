package com.maelstrom.arcanemechina.client.gui;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RuneWidgetList extends AbstractOptionList<RuneWidgetList.Entry> {

	private final RuneBuilderGui rune;
	private int maxListLabelWidth;
	private boolean isActive = false;

	public RuneWidgetList(RuneBuilderGui rune) {
		super(Minecraft.getInstance(), rune.width + 45, rune.height, 43, rune.height - 32, 20);
		this.rune = rune;
		for(short id = 0; id < RuneList.getMax(); id++)
		{
			RuneType rune_type = RuneList.getFromID(id);
			int i = Minecraft.getInstance().fontRenderer.getStringWidth(I18n.format("am.rune." + rune_type.getName()));
			if (i > this.maxListLabelWidth) {
				this.maxListLabelWidth = i;
			}

			this.addEntry(new RuneWidgetList.RUneEntry(rune_type));
		}

	}

	protected int getScrollbarPosition() {
		return super.getScrollbarPosition() + 15 + 20;
	}

	public int getRowWidth() {
		return super.getRowWidth() + 32;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	@OnlyIn(Dist.CLIENT)
	public abstract static class Entry extends AbstractOptionList.Entry<RuneWidgetList.Entry> {
	}

	@OnlyIn(Dist.CLIENT)
	public class RUneEntry extends RuneWidgetList.Entry {
		private final String keyDesc;
		private final Button btnSet;
		private RuneType type;
		private RUneEntry(RuneType rune) {
			String string = "am.rune.size." + rune.getName();
			this.keyDesc = I18n.format(string);
			this.type = rune;
			this.btnSet = new Button(0, 0, 75 + 20, 20, "SET", (p_214386_2_) -> {
				//RuneWidgetList.this.rune.;
				//RuneWidgetList.this.rune.hideList();
			}) {
				protected String getNarrationMessage() {
					return string != null || !string.isEmpty()
							? I18n.format("am.rune", RUneEntry.this.keyDesc)
							: "ohno";
				}
			};
		}

		public void render(int unknown, int ty, int tx, int width, int height, int mx, int my, boolean p_render_8_,
				float p_render_9_) {
			boolean flag = false;// rune.rune_size == this.type;
			RuneWidgetList.this.minecraft.fontRenderer.drawString(this.keyDesc,
					(float) (tx + 90 - RuneWidgetList.this.maxListLabelWidth), (float) (ty + height / 2 - 9 / 2),
					16777215);
			this.btnSet.x = tx + 105;
			this.btnSet.y = ty;
			this.btnSet.setMessage(keyDesc);
			boolean flag1 = false;
			boolean keyCodeModifierConflict = true;

			if (flag) {
				this.btnSet.setMessage(TextFormatting.WHITE + "> " + TextFormatting.YELLOW + this.btnSet.getMessage()
						+ TextFormatting.WHITE + " <");
			} else if (flag1) {
				this.btnSet.setMessage((keyCodeModifierConflict ? TextFormatting.GOLD : TextFormatting.RED)
						+ this.btnSet.getMessage());
			}

			this.btnSet.render(mx, my, p_render_9_);
		}

		public List<? extends IGuiEventListener> children() {
			return ImmutableList.of(this.btnSet);
		}

		public boolean mouseClicked(double x, double y, int id) {
			if(RuneWidgetList.this.isActive())
			if (this.btnSet.mouseClicked(x, y, id)) {
				return true;
			}
			return false;
		}

		public boolean mouseReleased(double x, double y, int id) {
			if(RuneWidgetList.this.isActive())
				return this.btnSet.mouseReleased(x, y, id);
			return false;
		}
	}
}
