package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityResearch;
import com.maelstrom.arcanemechina.library.AMBookHelper;
import com.maelstrom.snowcone.extendables.ExtendableItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMechanizedArcaneBook extends ExtendableItem
{
	
	public ItemMechanizedArcaneBook()
	{
		super(ItemsReference.AMBookName, Reference.MOD_ID);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
	
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs tab, List list)
    {
    	AMBookHelper book = AMBookHelper.passItemStack(new ItemStack(this));
    	book.setIsCreativeOnly(true);
    	book.setBookOwner("CreativeOnly");
    	book.clearOldAndApplyChanges();

        list.add(new ItemStack(item, 1, 0));
        list.add(book.getItemstack().copy());
    }
    
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean dunno)
    {
    	AMBookHelper book = AMBookHelper.passItemStack(itemStack);
    	if(book.getIsCreativeOnly())
    	{
    		list.add("§5CREATIVE ONLY");
    	}
    	else
    	{
    		if(book.getBookOwner() == null)
    			list.add("Owner: §K1234756789§R");
    		else
    			list.add("Owner: " + book.getBookOwner());
    	}
    }
    
    public EnumRarity getRarity(ItemStack itemStack)
    {
        return AMBookHelper.passItemStack(itemStack).getIsCreativeOnly() ? EnumRarity.rare : EnumRarity.uncommon;
    }
    
    public boolean hasEffect(ItemStack itemStack)
    {
    	return AMBookHelper.passItemStack(itemStack).getIsCreativeOnly();
    }
    public void onCreated(ItemStack itemStack, World world, EntityPlayer player)
    {
    	AMBookHelper book = AMBookHelper.passItemStack(itemStack);
    	book.setBookOwner(player.getDisplayName());
    	book.clearOldAndApplyChanges();
    }
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		if(AMBookHelper.passItemStack(itemStack).getBookOwner() == null)
		{
			AMBookHelper book = AMBookHelper.passItemStack(itemStack);
			book.setBookOwner(player.getDisplayName());
			book.clearOldAndApplyChanges();
		}
		if(world.isRemote)
			player.openGui(ArcaneMechina.instance, 1, world, (int)player.posX, (int)player.posY, (int)player.posZ);
		//open readonly gui
		return itemStack;
	}

    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float xFloat, float yFloat, float zFloat)
    {
    	//quick fix
		if(AMBookHelper.passItemStack(itemStack).getBookOwner() == null)
		{
			AMBookHelper book = AMBookHelper.passItemStack(itemStack);
			book.setBookOwner(player.getDisplayName());
			book.clearOldAndApplyChanges();
		}
		
    	if(world.getBlock(x, y, z) == Blocks.stonebrick && world.getBlockMetadata(x, y, z) == 0)
    	{
    		for(int x2 = -1; x2 <= 1; x2++)
        		for(int z2 = -1; z2 <= 1; z2++)
        			if(world.getBlock(x + x2, y, z + z2) != Blocks.stonebrick)
        				return false;

    		for(int x2 = -1; x2 <= 1; x2++)
        		for(int z2 = -1; z2 <= 1; z2++)
        			world.setBlock(x + x2, y, z + z2, BlocksReference.rStand, 1, 3);
    		
			world.setBlock(x, y, z, BlocksReference.rStand, 0, 3);
            if(world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityResearch)
            {
            	TileEntityResearch table = (TileEntityResearch) world.getTileEntity(x, y, z);
            	table.setResearchBook(itemStack.copy());
            	itemStack.stackSize = 0;
			return true;
            }
    	}
        return false;
    }
}
