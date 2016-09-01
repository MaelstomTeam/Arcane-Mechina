package com.maelstrom.armech.common.item;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.maelstrom.armech.common.utils.CrystalDetails;
import com.maelstrom.armech.common.utils.ElementalType;
import com.maelstrom.armech.common.utils.ElementalType.ELEMENT_TYPES;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDustCrystal extends Item implements IItemColor {
	
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
    {
    	//check if crystal contains data
    	if(CrystalDetails.doesContainCrystalDetails(stack))
    	{
			//if f3+h advanced mode is on and player is in creative mode
			if(advanced && (playerIn.isCreative() || Item.class.getCanonicalName() == "net.minecraft.item.Item"))
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
    	//if crystal does not contain data
    	else
    	{
    		//add a tool tip to show this item is broken
    		tooltip.add(I18n.format("armech.text.nbtexception"));
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
    	subItems.add(CrystalDetails.writeType(new ItemStack(itemIn), new ElementalType(ELEMENT_TYPES.DARK, .5f)));
    }
    
    
    /*
     * 
     * TEMPORARY ITEMSTACK COLOR
     * 
     */
    
    
    
    
    @SideOnly(Side.CLIENT)
    @Override
    public int getColorFromItemstack(ItemStack itemstack, int renderPass)
    {
    	Color newColor = Color.WHITE;
    	if(CrystalDetails.doesContainCrystalDetails(itemstack))
    	{
    		CrystalDetails details = CrystalDetails.readDetails(itemstack);
    		if(details.types.size() > 1)
	    		for(ElementalType types : details.types)
	    		{
	    			if(newColor == null)
	    				newColor = types.type.clr;
	    			else
	    				newColor = mixColors(newColor, getLoweredColor(types.type.clr, types.concentrate));
	    		}
    		else
    			newColor = getLoweredColor(details.types.get(0).type.clr, details.types.get(0).concentrate);
    	}
    	else
			newColor = new Color(this.itemRand.nextFloat(),this.itemRand.nextFloat(),this.itemRand.nextFloat());
    	return renderPass == 0 ? newColor.hashCode() : -1;
    }
    
    public Color mixColors(Color clr1, Color clr2)
    {
    	int red = (clr1.getRed() + clr2.getRed()) / 2;
    	int green = (clr1.getGreen() + clr2.getGreen()) / 2;
    	int blue = (clr1.getBlue() + clr2.getBlue()) / 2;
    	return new Color(red,green,blue);
    }
    
    public Color getLoweredColor(Color c, float intensity)
    {
    	int red = (int)((float)c.getRed() * intensity);
    	int green = (int)((float)c.getGreen() * intensity);
    	int blue = (int)((float)c.getBlue() * intensity);
    	return new Color(red,green,blue);
    }
    
}
