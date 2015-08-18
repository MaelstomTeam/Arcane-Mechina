package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import baubles.api.BaubleType;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemRings extends ItemBaubleEx {
	
	//Originally in ItemsReference as a STATIC FINAL but for some reason it kept turning up as a null
	String[] ringTypes = { "emerald", "diamond", "gold", "iron", "stone" };
	IIcon[] icon;
	public ItemRings() {
		super(ItemsReference.ringName, Reference.MOD_ID);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}

    public void registerIcons(IIconRegister registry)
    {
    	//make array of icons equal to the length of the string array
    	icon = new IIcon[ringTypes.length];
    	
    	//register and add each icon
    	for(int i = 0; i < ringTypes.length; i++)
    		icon[i] = registry.registerIcon(mod_id + ":" + this.getUnlocalizedName().substring(5 + mod_id.length() + 1) + ringTypes[i]);
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
        return this.getUnlocalizedName() + ringTypes[stack.getItemDamage()];
    }
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
		//adds each item to creative tab
		for(int i = 0; i < ringTypes.length; i++)
	        itemList.add(new ItemStack(item, 1, i));
    }

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.RING;
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
