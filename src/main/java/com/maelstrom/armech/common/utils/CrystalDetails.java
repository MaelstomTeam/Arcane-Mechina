package com.maelstrom.armech.common.utils;

import java.util.ArrayList;

import com.maelstrom.armech.common.utils.ElementalType.ELEMENT_TYPES;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class CrystalDetails
{
	private static String concentrateName = "purity";
	private static String typeName = "type";
	private static String detailName = "details";
	public ArrayList<ElementalType> types = new ArrayList();;
	public static CrystalDetails readDetails(ItemStack stack)
	{
		//if there's no stack compound return an empty crystal details
		if(stack.getTagCompound() != null && stack.getTagCompound().hasKey(detailName))
		{
			//get the details list from nbt
    		NBTTagList details = (NBTTagList)stack.getTagCompound().getTag(detailName);
    		//create empty crystal details
    		CrystalDetails cd = new CrystalDetails();
    		for(int i = 0; i < details.tagCount(); i++)
    		{
    			try{
    				//create elemental types and add them to the array in crystal details
    				cd.types.add(new ElementalType(ELEMENT_TYPES.valueOf(details.getCompoundTagAt(i).getString(typeName)),details.getCompoundTagAt(i).getFloat(concentrateName)));
    			}
    			catch(Exception e)
    			{
    				//if an error occurs while creating details set nbt to new empty and return blank crystal details
    				stack.setTagCompound(new NBTTagCompound());
    				return new CrystalDetails();
    			}
    		}
    		return cd;
		}
		return new CrystalDetails();
	}
	//i'll comment on the rest of this later
	public NBTTagCompound writeDetails(ItemStack itemstack)
	{
		NBTTagList crystalDetails = new NBTTagList();
		
		for(ElementalType dets : types)
		{
			NBTTagCompound detail = new NBTTagCompound();
			detail.setFloat(concentrateName, dets.concentrate);
			detail.setString(typeName, dets.type.name());
			crystalDetails.appendTag(detail);
		}
		itemstack.setTagInfo(detailName, crystalDetails);
		return itemstack.getTagCompound();
	}
	public static ItemStack writeType(ItemStack itemstack, ElementalType ... t)
	{	
		NBTTagList crystalDetails = new NBTTagList();
		for(ElementalType t2 : t)
		{
			NBTTagCompound detail = new NBTTagCompound();
			detail.setFloat(concentrateName, t2.concentrate);
			detail.setString(typeName, t2.type.name());
			crystalDetails.appendTag(detail);
		}
		itemstack.setTagInfo(detailName, crystalDetails);
		return itemstack;
	}
}