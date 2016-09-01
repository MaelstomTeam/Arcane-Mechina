package com.maelstrom.armech.common.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import com.maelstrom.armech.common.reference.ModItems;
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
	public NBTTagCompound writeDetails(ItemStack itemstack)
	{
		//create new nbt tag list;
		NBTTagList crystalDetails = new NBTTagList();
		
		//Iterate over all types in crystal details
		for(ElementalType dets : types)
		{
			//create new nbt tag compound
			NBTTagCompound detail = new NBTTagCompound();
			//set the float of designation [concentrateName] to the details of current concentrate
			detail.setFloat(concentrateName, dets.concentrate);
			//set the float of designation [typeName] to the details of current type's name
			detail.setString(typeName, dets.type.name());
			//add that tag to the list tags
			crystalDetails.appendTag(detail);
		}
		//add the list of tags to the main itemstack
		itemstack.setTagInfo(detailName, crystalDetails);
		//return the itemstacks nbt tag
		return itemstack.getTagCompound();
	}
	
	//i'll comment on this later
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

	//i'll comment on this later
	public static ItemStack writeRandom(ItemStack itemStack, Random rand) {
		if(canContainCrystalDetails(itemStack) == false || doesContainCrystalDetails(itemStack) == true)
			//may not need this
			return itemStack;
    	ArrayList<ElementalType> list = new ArrayList();
		int randomNumber = rand.nextInt(ElementalType.ELEMENT_TYPES.values().length) + 1;
		float percentage = (rand.nextFloat() / 2) + .5f;
		for(int i = 0; i < randomNumber; i++)
		{
			ElementalType newElement = new ElementalType(ELEMENT_TYPES.values()[rand.nextInt(ElementalType.ELEMENT_TYPES.values().length)], ((rand.nextFloat() / 2) + .5f) / randomNumber);
			for(ElementalType e : list)
			{
				if(e.type == newElement.type)
					e.addConcentrate(newElement);
			}
			if(newElement.isValid)
				list.add(newElement);
		}
		Collections.sort(list, new Comparator<ElementalType>(){

			@Override
			public int compare(ElementalType firstElement, ElementalType SecondElement) {
				return firstElement.type.name().compareTo(SecondElement.type.name());
			}});
		return CrystalDetails.writeType(itemStack, list.toArray(new ElementalType[list.size()]));
	}
    
	//basically what it says, this function checks if the itemstack CAN and DOES contain crystal details
    public static boolean doesContainCrystalDetails(ItemStack itemstack)
    {
    	return itemstack.getItem() == ModItems.dustCrystal && itemstack.getTagCompound() != null && itemstack.getTagCompound().hasKey(detailName);
    }
    
    //checks if the itemstack can contain crystal details and doesn't already contain it
    public static boolean canContainCrystalDetails(ItemStack itemstack)
    {
    	return itemstack.getItem() == ModItems.dustCrystal && (itemstack.getTagCompound() == null || itemstack.getTagCompound().hasKey(detailName) == false);
    }
}