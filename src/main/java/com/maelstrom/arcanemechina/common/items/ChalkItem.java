package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneHelper;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class ChalkItem extends Item
{

	public ChalkItem()
	{
		super(new Item.Properties().group(Registry.ARCANE).maxStackSize(1));
		this.setRegistryName(ArcaneMechina.MODID, "chalk");
	}

	public ActionResultType onItemUse(ItemUseContext context)
	{
		return onItemUse(context.getWorld(), context.getPos(), context.getItem(), context.getHand(),context.getPlayer(), context.getFace());
	}
	
	private ActionResultType onItemUse(World world, BlockPos pos2, ItemStack itemstack, Hand hand, PlayerEntity player, Direction face)
	{
		if(player instanceof FakePlayer)
			return ActionResultType.FAIL;
		if(player.getHeldItem(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND).getItem() == Registry.blueprint_rune)
		{
			ItemStack drawn_rune = player.getHeldItem(hand == Hand.MAIN_HAND ? Hand.OFF_HAND : Hand.MAIN_HAND);
			if(RuneHelper.hasRune(drawn_rune))
			{
				BlockPos pos = pos2.offset(face);
				world.setBlockState(pos, Registry.rune.getDefaultState());
				RuneContainer rune = RuneHelper.fromItem(drawn_rune);
				if(rune == null)
					rune = RuneHelper.getEmpty();
				((RuneTileEntity)world.getTileEntity(pos)).setContainer(rune);
			}
		}
		else
		{
			//show basic rune draw gui
		}
		return ActionResultType.SUCCESS;
		
	}

}
