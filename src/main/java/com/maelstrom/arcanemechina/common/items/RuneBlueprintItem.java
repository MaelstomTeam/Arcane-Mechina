package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneHelper;
import com.maelstrom.arcanemechina.common.runic.RuneType;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RuneBlueprintItem extends NoDamageItem
{

	public RuneBlueprintItem()
	{
		super(new Item.Properties().group(Registry.BLUEPRINTS).setNoRepair(), 2, "predrawnrune");
	}

	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (this.isInGroup(group))
		{
			items.add(new ItemStack(this));
			items.add(RuneHelper.toItem(RuneHelper.getNewCraftingRune()).setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.crafting_rune")));
			items.add(RuneHelper.toItem(RuneHelper.getNewMiningRune()).setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.mining_rune")));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(stack.getTag() != null && stack.getTag().get("rune_data") != null) {
			RuneContainer runes = RuneHelper.fromItem(stack);
			String fullDetails = "";
			for(RuneType rune : runes.getChildren())
			{
				if(rune != null)
				{
					String runeDetails = "";
					if(flagIn.isAdvanced())
					{
						runeDetails += ":" + rune.getUUID();
						if(rune.getListUUID().size() > 0)
						{
							runeDetails += ":L ";
						for(int link : rune.getListUUID())
						{
							runeDetails += link + " ";
						}
						}
						fullDetails += rune.getName() + runeDetails + " \n";
					}
					else
					{
						fullDetails += rune.getName() + runeDetails + " | ";
					}
				}
			}
			StringTextComponent text = new StringTextComponent(fullDetails.substring(0, fullDetails.length() - 3));
			text.getStyle().setColor(TextFormatting.DARK_GRAY);
			tooltip.add(text);
		}
	}
}
