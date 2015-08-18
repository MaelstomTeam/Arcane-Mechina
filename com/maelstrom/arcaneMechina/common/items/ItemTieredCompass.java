package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.registery.IconRegestry;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemTieredCompass extends ExtendableItem {

//	private IIcon[] icons;
	private String[] names = { "Zero", "North", "GPS"};;
	public ItemTieredCompass() {
		super(ItemsReference.tieiredCompassName, Reference.MOD_ID);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	public static ChunkCoordinates gps = new ChunkCoordinates(2, 7, 1996);
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
    {
    	if(player.isSneaking())
    		gps = new ChunkCoordinates((int)player.posX, (int)player.posY, (int)player.posZ);
        return itemStack;
    }
	@Override
    public IIcon getIconFromDamage(int meta)
    {
		try{
			return IconRegestry.compass[meta];
		}
		catch(Exception e)
		{
			return IconRegestry.compass[0];
		}
    }
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
    {
		try{
			return this.getUnlocalizedName() + names[stack.getItemDamage()];
		}
		catch(Exception e)
		{
			return this.getUnlocalizedName() + "Error";
		}
    }
	@Override
    public void getSubItems(Item item, CreativeTabs tab, List itemList)
    {
		for(int i = 0; i < names.length; i++)
	        itemList.add(new ItemStack(item, 1, i));
    }

}
