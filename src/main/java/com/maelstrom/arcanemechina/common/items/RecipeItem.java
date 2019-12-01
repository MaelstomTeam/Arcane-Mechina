package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.newrune.RecipeHelper;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class RecipeItem extends Item
{

	public RecipeItem()
	{
		super(new Item.Properties().group(Registry.ARCANE).setNoRepair().maxDamage(1));
		this.setRegistryName(ArcaneMechina.MODID, "recipe");
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
			items.add(RecipeHelper.createFromListToItemStack(new ItemStack[][] { new ItemStack[] { new ItemStack(Items.OAK_PLANKS) }, new ItemStack[] { new ItemStack(Items.OAK_PLANKS) } }).setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.stick")));
		}

	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if(stack.getTag() != null)
			tooltip.add(new StringTextComponent(stack.getTag().toString()));
	}

}
