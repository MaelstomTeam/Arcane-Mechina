package com.maelstrom.armech.common.items;

import java.awt.Color;
import java.util.List;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.blocks.BlockDustOre;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDustDust extends Item {

	public ItemDustDust()
	{
		super();
		this.setUnlocalizedName("dust_crystal");
		this.setHasSubtypes(true);
		this.setCreativeTab(ArMechMain.tab_armech_dust);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return super.getUnlocalizedName() + "." + BlockDustOre.EnumType.getType(itemstack.getItemDamage()).getName();
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List items)
	{
		for(int i = 0; i < BlockDustOre.EnumType.length(); i++)
			items.add(new ItemStack(item, 1,i));
	}
	
	@Override
	public void addInformation(ItemStack item_stack, EntityPlayer player, List subtext, boolean bool)
	{
		if(item_stack.getItemDamage() >= 6)
			subtext.add(StatCollector.translateToLocal("dust_crystal.mixed") + " of " + StatCollector.translateToLocal("dust_type."+BlockDustOre.EnumType.getType(item_stack.getItemDamage()).getName()));
		else
			subtext.add(StatCollector.translateToLocal("dust_crystal.pure") + " of " + StatCollector.translateToLocal("dust_type."+BlockDustOre.EnumType.getType(item_stack.getItemDamage()).getName()));
	}
	
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		if(stack.getItemDamage() == 1)
			return Color.GREEN.hashCode();
		else if(stack.getItemDamage() == 2)
			return Color.RED.hashCode();
		else if(stack.getItemDamage() == 3)
			return Color.BLUE.hashCode();
		else if(stack.getItemDamage() == 4)
			return Color.BLACK.hashCode();
		else if(stack.getItemDamage() == 5)
			return new Color(255,255,255).hashCode();
		else if(stack.getItemDamage() == 6)
			return new Color(255,255,144).hashCode();
		else if(stack.getItemDamage() == 7)
			return new Color(144,255,144).hashCode();
		else if(stack.getItemDamage() == 8)
			return new Color(255,144,144).hashCode();
		else if(stack.getItemDamage() == 9)
			return new Color(144,144,255).hashCode();
		else if(stack.getItemDamage() == 10)
			return new Color(144,144,0).hashCode();
		else if(stack.getItemDamage() == 11)
			return new Color(0,144,0).hashCode();
		else if(stack.getItemDamage() == 12)
			return new Color(144,0,0).hashCode();
		else if(stack.getItemDamage() == 13)
			return new Color(0,0,144).hashCode();
		else
			return Color.yellow.hashCode();
	}
}
