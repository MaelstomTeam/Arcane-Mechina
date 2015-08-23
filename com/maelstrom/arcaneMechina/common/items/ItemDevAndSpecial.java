package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import baubles.api.BaubleType;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.event.EntityEvent;

public class ItemDevAndSpecial extends ItemBaubleEx {
	
	public static final String[] specialItems = { "rosarioAmulet", "devilsRing"};
	public static IIcon[] icons;
	
	public ItemDevAndSpecial()
	{
		super(ItemsReference.dsItemsName, Reference.MOD_ID);
		this.setMaxStackSize(1);
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
	}
	
	public void registerIcons(IIconRegister registry)
	{
		icons = new IIcon[specialItems.length];
		for(int i = 0; i < specialItems.length; i++)
			icons[i] = registry.registerIcon(mod_id + ":" + this.getUnlocalizedName().substring(5 + mod_id.length() + 1).toLowerCase() + "/" + specialItems[i]);
	}
	
	@Override
    public IIcon getIconFromDamage(int meta)
    {
		
		try{
			return icons[meta];
		}
		catch(Exception e)
		{
			return icons[0];
		}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
    {
        return this.getUnlocalizedName() + "." + specialItems[stack.getItemDamage()];
    }
	
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
		//adds each item to creative tab
		for(int i = 0; i < specialItems.length; i++)
	        itemList.add(new ItemStack(item, 1, i));
    }
	
	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		switch(itemstack.getItemDamage())
		{
		case 0 : 
			return BaubleType.AMULET;
		case 1 :
			return BaubleType.RING;
		}
		return null;
	}
	
	@Override
	public void onEquipped(ItemStack itemStack, EntityLivingBase entity)
	{
		entity.addPotionEffect(new PotionEffect(EntityEvent.potion.getId(), 20, 0, false));
	}
	
	@Override
	public void onUnequipped(ItemStack itemStack, EntityLivingBase entity)
	{
		if(entity.isPotionActive(EntityEvent.potion))
			entity.removePotionEffect(EntityEvent.potion.getId());
	}

	@Override
	public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer)
			if(Reference.isContributor(((EntityPlayer) entity).getDisplayName()))
			{
				//does effect maybe?
				return;
			}
		if(entity.isPotionActive(EntityEvent.potion))
		{
			if(entity.getActivePotionEffect(EntityEvent.potion).getDuration() <= 1)
				entity.addPotionEffect(new PotionEffect(EntityEvent.potion.getId(), 20, 0, false));
			if(itemstack.getItemDamage() == 0)
				entity.setFire(1);
		}
		else
			entity.addPotionEffect(new PotionEffect(EntityEvent.potion.getId(), 20, 0, false));
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
