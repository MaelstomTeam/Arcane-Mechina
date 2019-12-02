package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;

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
			items.add(RuneHelper.toItem(RuneHelper.getNewCraftingRune())
					.setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.crafting_rune")));
			items.add(
					RuneHelper.toItem(RuneHelper.getNewMiningRune())
					.setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.mining_rune")));
		}

	}
}
