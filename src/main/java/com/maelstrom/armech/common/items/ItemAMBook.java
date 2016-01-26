package com.maelstrom.armech.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.client.gui.GUIBookBase;

public class ItemAMBook extends Item
{
	
	public ItemAMBook()
	{
		setUnlocalizedName("help_book");
		setCreativeTab(ArMechMain.tab_armech);
	}
	
	@Override
	public void addInformation(ItemStack item_stack, EntityPlayer player, List subtext, boolean bool)
	{
		subtext.add(StatCollector.translateToLocal("helpbook.subtext"));
	}
	
	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float floatX, float floatY, float floatZ)
	{
		//TODO: other things?
		//player.openGui(ArMechMain.INSTANCE, 0, world, pos.getX(), pos.getY(), pos.getZ());
		return super.onItemUse(itemStack, player, world, pos, side, floatX, floatY, floatZ);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(!player.isSneaking())
		{
			player.openGui(ArMechMain.INSTANCE, 0, world, 0, 0, 0);
			GUIBookBase.defaultBackground = GUIBookBase.rand.nextInt(3) + 1;
		}
		
		return itemStack;
	}
}