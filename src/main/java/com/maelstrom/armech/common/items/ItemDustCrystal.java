package com.maelstrom.armech.common.items;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.Reference;

public class ItemDustCrystal extends Item
{
	public ItemDustCrystal()
	{
		super();
		this.setUnlocalizedName("dust_crystal");
		this.setHasSubtypes(true);
		this.setCreativeTab(ArMechMain.tab_armech_dust);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return super.getUnlocalizedName();// + "." + BlockDustOre.EnumType.getType(itemstack.getItemDamage()).getName();
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List items)
	{
		for(int i = 0; i < Reference.dustNames.length; i++)
			if(i != 19)
				items.add(new ItemStack(item, 1,i));
	}
	
	@Override
	public void addInformation(ItemStack itemstack, EntityPlayer player, List subtext, boolean bool)
	{
		if(itemstack.getItemDamage() >= 6)
			subtext.add(StatCollector.translateToLocal("dust_crystal.mixed") + " of " + StatCollector.translateToLocal("dust_type."+Reference.dustNames[itemstack.getItemDamage()]));
		else
			subtext.add(StatCollector.translateToLocal("dust_crystal.pure") + " of " + StatCollector.translateToLocal("dust_type."+Reference.dustNames[itemstack.getItemDamage()]));
	}
	//33 48 170
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
			return Color.WHITE.hashCode();
		else if(stack.getItemDamage() == 6)
			return new Color(255,255,144).hashCode();
		else if(stack.getItemDamage() == 7)
			return new Color(144,255,144).hashCode();
		else if(stack.getItemDamage() == 8)
			return new Color(255,104,104).hashCode();
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
		else if(stack.getItemDamage() == 14)
			return Color.PINK.hashCode();
		else if(stack.getItemDamage() == 15)
			return new Color(70,95,10).hashCode();
		else if(stack.getItemDamage() == 16)
			return Color.ORANGE.hashCode();
		else if(stack.getItemDamage() == 17)
			return new Color(192,192,192).hashCode();
		else if(stack.getItemDamage() == 18)
			return new Color(255,69,0).hashCode();
		else if(stack.getItemDamage() == 19)
		{
			if(new Random().nextInt(10) >= 5)
				return Color.GREEN.hashCode();
			else
				return new Color(85,205,47).hashCode();
		}
		else if(stack.getItemDamage() == 20)
			return new Color(220,20,60).hashCode();
		else if(stack.getItemDamage() == 21)
			return new Color(0,100,0).hashCode();
		else if(stack.getItemDamage() == 22)
			return new Color(220,20,60).hashCode();
		else if(stack.getItemDamage() == 23)
			return new Color(125, 249, 255).hashCode();
		else if(stack.getItemDamage() == 24)
			return new Color(8, 232, 222).hashCode();
		else
			return Color.yellow.hashCode();
	}
}
