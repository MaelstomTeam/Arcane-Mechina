package com.maelstrom.arcanemechina.common.runic;

import net.minecraft.util.IStringSerializable;

public enum RuneSize implements IStringSerializable {

	TINY("tiny"),
	SMALL("small"),
	MEDIUM("medium"),
	LARGE("large"),
	HUGE("huge");

	RuneSize(String name) {
		this.name = name;
	}

	String name;

	@Override
	public String getName() {
		return name;
	}
}
