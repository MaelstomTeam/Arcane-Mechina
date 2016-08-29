package com.maelstrom.armech.common.item;

import java.util.ArrayList;
import java.util.List;

import com.maelstrom.armech.common.utils.CrystalDetails;
import com.maelstrom.armech.common.utils.ElementalType;
import com.maelstrom.armech.common.utils.ElementalType.ELEMENT_TYPES;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDustCrystal extends Item {
	
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
    	//if f3+h advanced mode is on and player is in creative mode
    	if(advanced && playerIn.isCreative())
    	{
    		//create a Crystal Details from item stack
    		CrystalDetails details = CrystalDetails.readDetails(stack);
    		for(ElementalType t : details.types)
    		{
    			//and here we add the text from the Crystal Details
    			tooltip.add(t.type.name() + " at " + String.format(t.concentrate > .1f ? "%.1f%%" : "%.2f%%" , t.concentrate * 100f));
    		}
    	}
    }
    
    
    //just a function to add the sub items to the creative tab
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems)
    {
    	ArrayList<ElementalType> list = new ArrayList();
    	//Iterate over each element type
    	for(ELEMENT_TYPES t : ELEMENT_TYPES.values())
    	{
        	//add each element type to a list
    		list.add(new ElementalType(t, 1f));
    		//add a modified item to the creative tab (that contains the data for a 100% Element Type)
            subItems.add(CrystalDetails.writeType(new ItemStack(itemIn), new ElementalType(t, 1f)));
    	}
    	//add an item that contains all element types at 100%
    	subItems.add(CrystalDetails.writeType(new ItemStack(itemIn), list.toArray(new ElementalType[list.size()])));
    }
    
}
