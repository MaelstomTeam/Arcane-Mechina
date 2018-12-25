package com.maelstrom.snowcone.item;

import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class MetaItem extends Item {
	public int subItemCount = 1;
	public MetaItem(int subCount) {
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		subItemCount = subCount;
	}

	public String getTranslationKey(ItemStack stack) {
		return super.getTranslationKey() + "." + stack.getItemDamage();
	}

	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (this.isInCreativeTab(tab)) {
			for (int i = 0; i < subItemCount; ++i) {
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
	
	public static class MetaItemWithNames extends MetaItem implements IHasName
	{
		String[] nameList;
		
		
		public MetaItemWithNames(String[] list) {
			super(list.length);
			nameList = list;
		}

		@Override
		public String getNameFromMeta(int meta) {
			if(meta > nameList.length - 1 || meta < 0)
				return "ERROR";
			return nameList[meta];
		}

		public String getTranslationKey(ItemStack stack) {
			return super.getTranslationKey() + "." + getNameFromMeta(stack.getItemDamage());
		}
		
	}

}
