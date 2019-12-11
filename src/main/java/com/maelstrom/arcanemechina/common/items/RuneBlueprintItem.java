package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneHelper;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
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
			items.add(RuneHelper.toItem(RuneHelper.getSubRuneFurnaceCrafter()).setDisplayName(new TranslationTextComponent(this.getTranslationKey() + ".preset.sub_rune_crafting_miner")));
		}
	}

	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		if (stack.getTag() != null && stack.getTag().get("rune_data") != null)
		{
			RuneContainer runes = RuneHelper.fromItem(stack);
			String fullDetails = "";
			for (RuneType rune : runes.getChildren())
			{
				if (rune != null)
				{
					fullDetails += rune.getName() + " | ";
				}
			}
			StringTextComponent text = new StringTextComponent(fullDetails.substring(0, fullDetails.length() - 3));
			text.getStyle().setColor(TextFormatting.DARK_GRAY);
			tooltip.add(text);
		}
	}
	


	public ActionResultType onItemUse(ItemUseContext context)
	{
		return onItemUse(context.getWorld(), context.getPos(), context.getItem(), context.getHand(),context.getPlayer(), context.getFace());
	}
	
	private ActionResultType onItemUse(World world, BlockPos pos, ItemStack itemstack, Hand hand, PlayerEntity player, Direction face)
	{
		if(world.getTileEntity(pos) instanceof RuneTileEntity)
		{
			RuneContainer container = ((RuneTileEntity)world.getTileEntity(pos)).getRuneContainer();
			RuneHelper.toItem(container, itemstack, true);
		}
		return null;
		
	}
}
