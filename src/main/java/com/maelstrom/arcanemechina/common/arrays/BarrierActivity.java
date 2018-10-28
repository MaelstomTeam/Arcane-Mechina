package com.maelstrom.arcanemechina.common.arrays;

import java.util.ArrayList;
import java.util.List;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.arcanemechina.api.RuneActivity;
import com.maelstrom.arcanemechina.common.event.EventRuneBackfire;
import com.maelstrom.arcanemechina.common.event.EventRuneBackfire.SEVERITY;
import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityBarrier;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BarrierActivity extends RuneActivity {

	ItemStack[] exact = new ItemStack[] {
			new ItemStack(ItemList.Crystal, 2, 6),
			new ItemStack(Items.SHIELD),
			new ItemStack(Items.ENDER_PEARL)};
	
	@Override
	public EventRuneBackfire getBackfireResult(World w, BlockPos pos, EntityLivingBase entity)
	{
		for(EntityItem e : w.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(3, 1, 3),pos.add(-2, -1, -2))))
		{
			e.setDead();
		}
		return new EventRuneBackfire(pos,w,SEVERITY.MEDIUM,ElementTypes.ETHERIC, entity);
	}

	@Override
	public void runActivity(World w, BlockPos pos) {
		TileEntityBarrier tile = new TileEntityBarrier();
		w.setTileEntity(pos, tile);
	}

	@Override
	public boolean isValid(World w, BlockPos pos)
	{
		List<EntityItem> listEntities = w.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(pos.add(3, 1, 3),pos.add(-2, -1, -2)));
		List<ItemStack> listItems = new ArrayList<ItemStack>();
		EntityItem pearl = null;
		EntityItem dust = null;
		for(EntityItem i : listEntities)
		{
			listItems.add(i.getItem());
			if(i.getItem().getItem() == Items.ENDER_PEARL)
				pearl = i;
			else if(i.getItem().getItem() == ItemList.Crystal)
				dust = i;
		}
		if(listItems.size() == 3)
		{
			ItemStack[] itemList = new ItemStack[3];
			listItems.toArray(itemList);
			ItemStack is_dust = null;
			ItemStack is_shield = null;
			ItemStack is_pearl = null;
			for(ItemStack is : itemList)
			{
				if(ItemStack.areItemsEqual(is,exact[0]) && is.getCount() == exact[0].getCount())
					is_dust = is;
				else if(ItemStack.areItemsEqualIgnoreDurability(is,exact[1]) && is.getCount() == exact[1].getCount())
					is_shield = is;
				else if(ItemStack.areItemsEqual(is,exact[2]) && is.getCount() == exact[2].getCount())
					is_pearl = is;
			}
			if(is_dust != null && is_shield != null && is_pearl != null)
			{
				if(pearl != null && dust != null) {
					pearl.setDead();
					dust.setDead();
					return true;
				}
			}
		}
		return false;
	}
}
