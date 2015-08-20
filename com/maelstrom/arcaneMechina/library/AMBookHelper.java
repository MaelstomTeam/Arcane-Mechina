package com.maelstrom.arcanemechina.library;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;

public class AMBookHelper
{
	private ItemStack book = null;
	private boolean creativeBook = false;
	private String bookOwner = "?";
	private NBTTagCompound nbt = null;

	public static AMBookHelper passItemStack(ItemStack itemStack)
	{
		return new AMBookHelper(itemStack);
	}
	
	private AMBookHelper(ItemStack itemstack)
	{
		book = itemstack;
		getVariables();
	}
	
	public void getVariables() {
		if(!book.hasTagCompound())
		{
			book.stackTagCompound = new NBTTagCompound();
			clearOldAndApplyChanges();
		}
		else
		{
			getNBT();
			if(nbt.hasKey("isCreativeBook"))
				creativeBook = nbt.getBoolean("isCreativeBook");
			if(nbt.hasKey("bookOwner"))
				bookOwner = nbt.getString("bookOwner");
		}
	}

	private NBTTagCompound getNBT()
	{
		return book.hasTagCompound() ? (nbt = book.getTagCompound()) : (nbt = (book.stackTagCompound = new NBTTagCompound()));
	}

	public String getBookOwner()
	{
		return bookOwner.length() > 1 ? bookOwner : null;
	}
	public void setBookOwner(String displayName)
	{
		bookOwner = displayName;
	}

	public void setIsCreativeOnly(boolean isCreativeOnly)
	{
		creativeBook = isCreativeOnly;
	}
	public boolean getIsCreativeOnly()
	{
		return creativeBook;
	}
	
	public void clearOldAndApplyChanges()
	{
		nbt = new NBTTagCompound();
		nbt.setBoolean("isCreativeBook", creativeBook);
		nbt.setString("bookOwner", bookOwner);
		book.setTagCompound(nbt);
		book.writeToNBT(new NBTTagCompound());
	}
    
	public ItemStack getItemstack()
	{
		return book;
	}
}