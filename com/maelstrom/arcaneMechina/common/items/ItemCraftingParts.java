package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemCraftingParts extends ExtendableItem {
	
	String[] craftingItemNames;
	IIcon[] icon;
	
	public ItemCraftingParts(String[] ciNames) {
		super(ItemsReference.craftingPartsBaseName, Reference.MOD_ID);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		craftingItemNames = ciNames;
	}

    public void registerIcons(IIconRegister registry)
    {
    	//make array of icons equal to the length of the string array
    	icon = new IIcon[craftingItemNames.length];
    	
    	//register and add each icon
    	for(int i = 0; i < craftingItemNames.length; i++)
    		icon[i] = registry.registerIcon(mod_id + ":" + this.getUnlocalizedName().substring(5 + mod_id.length() + 1).toLowerCase() + "/" + craftingItemNames[i]);
    }
    
	@Override
    public IIcon getIconFromDamage(int meta)
    {
		
		try{
			return icon[meta];
		}
		catch(Exception e)
		{
			return icon[0];
		}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
    {
        return this.getUnlocalizedName() + "." + craftingItemNames[stack.getItemDamage()];
    }
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
		//adds each item to creative tab
		for(int i = 0; i < craftingItemNames.length; i++)
	        itemList.add(new ItemStack(item, 1, i));
    }

}
