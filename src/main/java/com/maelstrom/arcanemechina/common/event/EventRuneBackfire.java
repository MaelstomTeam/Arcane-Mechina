package com.maelstrom.arcanemechina.common.event;

import com.maelstrom.arcanemechina.api.ElementTypes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.Cancelable;

@Cancelable
public class EventRuneBackfire extends EventRune {
	public static enum SEVERITY
	{
		LOW,
		MEDIUM,
		HIGH,
		EXTREME,
		NONE;
	}
	protected SEVERITY   severity;
	protected World world;
	protected ElementTypes type;
	protected EntityLivingBase entitybase;
	public EventRuneBackfire(BlockPos pos, World w, SEVERITY s, ElementTypes t, EntityLivingBase entity)
	{
		super(pos);
		world = w;
		severity = s;
		type = t;
		entitybase = entity;
	}
	
	public EventRuneBackfire(BlockPos pos, World w, SEVERITY s, EntityLivingBase entity)
	{
		super(pos);
		world = w;
		severity = s;
		type = ElementTypes.OBLIVIOUS;
		entitybase = entity;
	}
	
	public void setWorld(World w)
	{
		world = w;
	}
	
	public World getWorld()
	{
		
		return world;
	}
	
	public SEVERITY getSeverity()
	{
		return severity;
	}
	
	public ElementTypes getType()
	{
		return type;
	}
}