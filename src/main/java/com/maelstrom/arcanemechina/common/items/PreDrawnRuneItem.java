package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.newrune.RecipeHelper;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneHelper;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.CraftingContainerRune;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class PreDrawnRuneItem extends Item
{

	public PreDrawnRuneItem()
	{
		super(new Item.Properties().group(Registry.ARCANE).maxDamage(1).setNoRepair());
		this.setRegistryName(ArcaneMechina.MODID, "predrawnrune");
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
			items.add(RuneHelper.toItem(RuneHelper.getNewCraftingRune())
					.setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.crafting_rune")));
			items.add(
					RuneHelper.toItem(RuneHelper.getNewMiningRune())
					.setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.mining_rune")));
		}

	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		try {
		tooltip.add(new StringTextComponent("test"));
		for(RuneType rune : RuneHelper.fromItem(stack).getChildren().values())
			if(rune instanceof RuneType.CraftingContainerRune)
				tooltip.add(new StringTextComponent(
						RecipeHelper.getFromNBT(((RuneType.CraftingContainerRune)rune).getStackInSlot(0).getTag())[0][0].toString()
						.toString()));
		}catch(Exception e) {}
	}
}
