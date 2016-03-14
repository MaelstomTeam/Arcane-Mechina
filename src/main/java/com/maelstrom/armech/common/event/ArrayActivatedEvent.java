package com.maelstrom.armech.common.event;

import net.minecraft.util.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraftforge.fml.common.eventhandler.Event;

@Cancelable
public class ArrayActivatedEvent extends Event
{
	protected BlockPos activationPosition;
	
	public ArrayActivatedEvent(BlockPos act)
	{
		setActivationPosition(act);
	}
	
	public BlockPos getActivationPosition() {
		return activationPosition;
	}

	protected void setActivationPosition(BlockPos activationPosition) {
		this.activationPosition = activationPosition;
	}

	@Cancelable
	public static class ArrayBackfireEvent extends ArrayActivatedEvent
	{
		public static enum SEVERITY
		{
			LOW,
			MEDIUM,
			HIGH,
			SEVERE;
		}
		protected SEVERITY severity;
		
		public ArrayBackfireEvent(BlockPos act, SEVERITY s)
		{
			super(act);
			setSeverity(s);
		}

		public SEVERITY getSeverity()
		{
			return severity;
		}

		protected void setSeverity(SEVERITY severity)
		{
			this.severity = severity;
		}
		
	}
}
