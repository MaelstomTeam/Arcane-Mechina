package com.maelstrom.armech.common.items;

import java.util.List;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.LanguageRegistry;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.client.gui.GuiBookBase;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.registry.AMBlocks;
import com.maelstrom.snowcone.MathHelper;
import com.maelstrom.snowcone.api.energy.library.IEnergyBase;

public class ItemAMBook extends Item
{

	BlockPos linkPosition;
	boolean cleanLinking = false;

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
		if (linkPosition == null && world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof IEnergyBase)
		{
			linkPosition = pos;
		} else if (linkPosition != null && pos != linkPosition && world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof IEnergyBase && MathHelper.getDistance(pos, linkPosition) < 10d)
		{
			player.addChatComponentMessage(new ChatComponentText("client: " + !world.isRemote));
			if (((IEnergyBase) world.getTileEntity(pos)).getParent() != null)
				((IEnergyBase) ((IEnergyBase) world.getTileEntity(pos)).getParent()).addChild(null);
			((IEnergyBase) world.getTileEntity(pos)).addParent(world.getTileEntity(linkPosition));
			((IEnergyBase) world.getTileEntity(linkPosition)).addChild(world.getTileEntity(pos));
		}
		// debug used for clearing large amounts of terrain
		// for (int x2 = -48; x2 < 48; x2++)
		// for (int y2 = 0; y2 < 100; y2++)
		// for (int z2 = -48; z2 < 48; z2++)
		// if(!(world.getBlockState(new BlockPos(pos.getX()+x2, y2,
		// pos.getZ()+z2)).getBlock() == AMBlocks.dust_ore ||
		// world.getBlockState(new BlockPos(pos.getX()+x2, y2,
		// pos.getZ()+z2)).getBlock() == Blocks.bedrock) )
		// world.setBlockToAir(new BlockPos(pos.getX()+x2, y2, pos.getZ()+z2));
		// else if(world.getBlockState(new BlockPos(pos.getX()+x2, y2,
		// pos.getZ()+z2)) == AMBlocks.dust_ore.getStateFromMeta(4))
		// System.out.println(new BlockPos(pos.getX()+x2, y2, pos.getZ()+z2));
		return true;
	}

	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (!player.isSneaking())
		{
			player.openGui(ArMechMain.INSTANCE, 0, world, 0, 0, 0);
			GuiBookBase.defaultBackground = GuiBookBase.rand.nextInt(3) + 1;
		} else
		{
			linkPosition = null;
		}

		return itemStack;
	}
}