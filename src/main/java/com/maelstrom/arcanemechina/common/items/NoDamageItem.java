package com.maelstrom.arcanemechina.common.items;

import com.maelstrom.arcanemechina.ArcaneMechina;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class NoDamageItem extends Item
{

	public NoDamageItem(Properties properties, int maxTypes, String unlocal)
	{
		super(properties.setNoRepair().maxDamage(maxTypes));
		this.setRegistryName(ArcaneMechina.MODID, unlocal);
	}
	
	public int getItemStackLimit(ItemStack stack)
    {
    	if(stack.getItem() == this)
    		return 64;
        return getItem().getMaxStackSize();
    }

	public boolean showDurabilityBar(ItemStack stack)
	{
		return false;
	}

	public String getTranslationKey(ItemStack stack)
	{
		if(this.isDamageable() && stack.getDamage() != 0)
			return this.getTranslationKey() + "." +stack.getDamage();
		return this.getTranslationKey();
	}
	public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items)
	{
		if (this.isInGroup(group))
		{
			for(int damage = 0; damage < this.getMaxDamage(getDefaultInstance()); damage++)
			{
				ItemStack itemstack = new ItemStack(this);
				itemstack.setDamage(damage);
				items.add(itemstack);
			}
		}

	}

}
