package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.helper.RecipeHelper;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;

public class RecipeBlueprintItem extends NoDamageItem
{

	public RecipeBlueprintItem()
	{
		super(new Item.Properties().group(Registry.BLUEPRINTS).setNoRepair(),2,"recipe");
	}

	public boolean showDurabilityBar(ItemStack stack)
	{
		return false;
	}

	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (this.isInGroup(group))
		{
			items.add(new ItemStack(this));
			items.add(RecipeHelper.createFromListToItemStack(new ItemStack[] { new ItemStack(Items.OAK_PLANKS), ItemStack.EMPTY, ItemStack.EMPTY,
															                   new ItemStack(Items.OAK_PLANKS) }).setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.stick")));
		}

	}

}
