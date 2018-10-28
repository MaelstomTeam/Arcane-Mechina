package com.maelstrom.arcanemechina.common.event;

import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.Event;

public abstract class EventRune extends Event {
	protected BlockPos runePosition;
	public EventRune(BlockPos pos)
	{
		runePosition = pos;
	}
	public BlockPos getRunePosition() {
		return runePosition;
	}
}
