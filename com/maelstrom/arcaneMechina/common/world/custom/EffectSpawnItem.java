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
	
	public EffectSpawnItem(ItemStack stack, ItemStack stack2){
		rItem = true;
		spawn = stack;
		need = stack2;
	}
	public EffectSpawnItem(ItemStack stack){
		rItem = false;
		spawn = stack;
		need = null;
	}

	@Override
	public void effect(World w, int x, int y, int z) {
		w.spawnEntityInWorld(new EntityItem(w, x+.5, y+.5, z+.5, spawn.copy()));
		w.playSoundEffect(x+.5, y+.5, z+.5, "mob.endermen.portal", 1, 1);
	}

	@Override
	public boolean effectCheck(World w, int x, int y, int z) {
		List<EntityItem> entities = (List<EntityItem>) w.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x-1, y-1, z-1, x+1, y+1, z+1));
		if(rItem)
			for(EntityItem entity : entities ){
				if(entity.getEntityItem().isItemEqual(need)){
					entity.getEntityItem().stackSize -= need.stackSize;
					return true;
				}
			}
		else
			return true;
		return false;
	}

}
