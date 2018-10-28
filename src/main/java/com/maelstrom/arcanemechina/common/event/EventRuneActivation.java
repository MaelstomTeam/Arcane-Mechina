package com.maelstrom.arcanemechina.common.event;

import com.maelstrom.arcanemechina.common.registry.RuneRegistry.Array;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class EventRuneActivation extends EventRune {
	
	protected EntityLivingBase entityActivator;
	protected World world;
	protected Array array;
	public EventRuneActivation(BlockPos pos, EntityLivingBase entity, Array array) {
		super(pos);
		entityActivator = entity;
		world = entity.world;
		this.array = array;
	}

	public EntityLivingBase getEntityActivator() {
		return entityActivator;
	}
	
	public World getWorld()
	{
		return entityActivator.world;
	}
	
	public Array getArray()
	{
		return array;
	}
}
