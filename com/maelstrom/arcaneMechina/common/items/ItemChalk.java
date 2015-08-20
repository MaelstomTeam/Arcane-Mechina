package com.maelstrom.arcanemechina.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.maelstrom.arcanemechina.common.BlocksReference;
import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.tileentity.TileWardTest;
import com.maelstrom.snowcone.extendables.ExtendableItem;

public class ItemChalk extends ExtendableItem
{

	public ItemChalk()
	{
		super(ItemsReference.chalkName, Reference.MOD_ID);
		setMaxStackSize(1);
		setContainerItem(this);
		setMaxDamage(64);
	}

	public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float floatX, float floatY, float floatZ)
	{
//		if(true)
//		{
//			world.setTileEntity(x, y, z, new TileWardTest());
//		}else
		//if direction clicked is top, block top is solid and block above is equal to air it can be placed. if block is equal to glowstone it can also be placed
		if((ForgeDirection.getOrientation(side) == ForgeDirection.UP 
				&& world.getBlock(x, y, z).isSideSolid(world, x, y, z, ForgeDirection.getOrientation(side)) 
				&& world.getBlock(x, y+1, z).equals(Blocks.air))
				|| world.getBlock(x,y,z).equals(Blocks.glowstone))
		{
			//place glyph
			world.setBlock(x, y+1, z, BlocksReference.glyphBlock);
			itemStack.damageItem(1, player);
		}
		return super.onItemUse(itemStack, player, world, x, y, z, side, floatX, floatY, floatZ);
	}
	
	public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player)
	{
		return itemStack;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	//=================================================================================================================
	
	
	
	
	
	
	
	/*
	
	
	
	
	
			//secret maybe
		if(false)
			//check if tile is a chest
			if(world.getTileEntity(x, y, z) instanceof TileEntityChest)
			{
				//the chest tile
				TileEntityChest chest = (TileEntityChest) world.getTileEntity(x, y, z);
				
				//chest contents list
				ArrayList<Item> chestItems = new ArrayList<Item>();
				
				//all minecraft disks
				ArrayList<Item> comparedTo = new ArrayList<Item>();
				comparedTo.add(Items.record_11);
				comparedTo.add(Items.record_13);
				comparedTo.add(Items.record_blocks);
				comparedTo.add(Items.record_cat);
				comparedTo.add(Items.record_chirp);
				comparedTo.add(Items.record_far);
				comparedTo.add(Items.record_mall);
				comparedTo.add(Items.record_mellohi);
				comparedTo.add(Items.record_stal);
				comparedTo.add(Items.record_strad);
				comparedTo.add(Items.record_wait);
				comparedTo.add(Items.record_ward);
				
				//add items form chest to list
				for(int i = 0; i < chest.getSizeInventory(); i++)
					if(chest.getStackInSlot(i) != null)
						chestItems.add(chest.getStackInSlot(i).getItem());
				
				//count disks
				int amountFound = 0;
				for(Item item : chestItems)
					for(Item item2 : comparedTo)
						if(item == item2)
							amountFound++;
				if(amountFound == comparedTo.size())
				{
					//remove disks
					for(int i = 0; i < chest.getSizeInventory(); i++)
						for(Item item2 : comparedTo)
							if(chest.getStackInSlot(i).getItem() == item2)
								chest.setInventorySlotContents(i, null);
					
					//save other items from destruction
					Blocks.chest.breakBlock(world, x, y, z, Blocks.chest, 0);
					
					//destroy block
					world.setBlockToAir(x, y, z);
					
					//spawn item in world.. currently using the chalk as a placeholder
					world.spawnEntityInWorld(new EntityItem(world, x, y, z, new ItemStack(ItemsReference.itemChalk)));
				}
						
			}
	
	
	
	
	
	
	
	
	
	*/
}
