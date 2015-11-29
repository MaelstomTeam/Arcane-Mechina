package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFunctional extends ExtendableItem
{
	public ItemFunctional(String secondary)
	{
		super(ItemsReference.functionalName + "." + secondary, Reference.MOD_ID);
		this.setHasSubtypes(false);
		this.setMaxDamage(60);
		this.setMaxStackSize(1);
	}
	
	@SubscribeEvent
	public void onEntityUpdate(LivingUpdateEvent event)
	{
		if(event.entityLiving instanceof EntityPlayer)
			onPlayerUpdate((EntityPlayer) event.entityLiving);
	}
	
	private void onPlayerUpdate(EntityPlayer player)
	{
		if(player.inventory.hasItem(ItemsReference.bloodRose))
			bloodRosePlayerUpdate(player);
	}

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStak, EntityPlayer player, List list, boolean something)
    {
    	list.add("§4"+LanguageRegistry.instance().getStringLocalization("item.arcanemechina.functionalItem.bloodRose.lore1"));
    	list.add("§4"+LanguageRegistry.instance().getStringLocalization("item.arcanemechina.functionalItem.bloodRose.lore2"));
    }
//	private int itemCount(EntityPlayer player, Item itemToCount)
//	{
//		int count = 0;
//		for(ItemStack item : player.inventory.mainInventory)
//		{
//			if(item != null)
//				if(item.getItem() == itemToCount)
//					count++;
//		}
//		return count;
//	}
	
	private void bloodRosePlayerUpdate(EntityPlayer player)
	{
			if(!player.isPotionActive(Potion.regeneration.id))
			{
				for(int slot = 0; slot < player.inventory.getSizeInventory(); slot++)
				{
					slot++;
					ItemStack stack = player.inventory.getStackInSlot(slot);
					if(stack != null && stack.getItem() == ItemsReference.bloodRose)
					{
						player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 20, 1));
						stack.damageItem(1, player);
						if(stack.stackSize <= 0)
						{
							player.inventory.setInventorySlotContents(slot, new ItemStack(Blocks.red_flower));
							player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 600, 2));
							player.addPotionEffect(new PotionEffect(Potion.weakness.id, 12000, 10));
							player.addChatMessage(new ChatComponentText("A Great sorrow feels you're heart"));
						}
						break;
					}
				}
			}
	}
}
