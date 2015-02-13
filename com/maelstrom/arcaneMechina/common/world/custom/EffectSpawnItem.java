package com.maelstrom.arcaneMechina.common.world.custom;

import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.world.Effect;

public class EffectSpawnItem extends Effect {
	
	public boolean rItem;
	public ItemStack spawn, need;
	public int amount = 0;
	
	public EffectSpawnItem(ItemStack stack2, ItemStack stack){
		rItem = true;
		spawn = stack2;
		need = stack;
	}
	public EffectSpawnItem(ItemStack stack){
		rItem = false;
		spawn = stack;
		need = null;
	}

	@Override
	public void effect(World w, int x, int y, int z) {
		if(rItem){
			if(amount == 0)
				w.spawnEntityInWorld(new EntityItem(w, x+.5, y+.5, z+.5, spawn.copy()));
			for(int ignore; amount > 0; amount--)
				w.spawnEntityInWorld(new EntityItem(w, x+.5, y+.5, z+.5, spawn.copy()));
		}
		w.playSoundEffect(x+.5, y+.5, z+.5, "mob.endermen.portal", 1, 1);
		amount = 0;
	}

	@Override
	public boolean effectCheck(World w, int x, int y, int z) {
		List<EntityItem> entities = (List<EntityItem>) w.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+2, y+1, z+2));
		boolean temp = false;
		if(!rItem)
			return true;
		for(EntityItem entity : entities)
			if(entity.getEntityItem().isItemEqual(need))
				if(entity.getEntityItem().stackSize >= need.stackSize){
					temp = true;
					while(entity.getEntityItem().stackSize >= need.stackSize){
						amount++;
						entity.getEntityItem().stackSize -= need.stackSize;
					}
				}
		return temp;
	}

}
