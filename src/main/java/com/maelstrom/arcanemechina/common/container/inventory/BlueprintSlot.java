package com.maelstrom.arcanemechina.common.container.inventory;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.items.BlueprintItem;
import com.maelstrom.snowcone.container.slot.FilteredSlot;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class BlueprintSlot extends FilteredSlot
{
	final List<Class<? extends Item>> list;

	public BlueprintSlot(IInventory inventoryIn, int index, int xPosition, int yPosition)
	{
		super(inventoryIn, index, xPosition, yPosition, null);
		list = new ArrayList<Class<? extends Item>>();
		list.add(BlueprintItem.class);
		this.set_filter_list(list);
	}

	public static ResourceLocation BLUEPRINT_SLOT_SPRITE = new ResourceLocation(ArcaneMechina.MODID, "items/scroll_outline2");

	@Nullable
	@OnlyIn(Dist.CLIENT)
	public net.minecraft.client.renderer.texture.TextureAtlasSprite getBackgroundSprite()
	{
		return getBackgroundMap().getSprite(BLUEPRINT_SLOT_SPRITE);
	}
}
