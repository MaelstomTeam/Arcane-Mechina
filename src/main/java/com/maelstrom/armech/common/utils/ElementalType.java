package com.maelstrom.armech.common.utils;

public class ElementalType 
{
	public static enum ELEMENT_TYPES { FIRE, WATER, EARTH, AIR, DARK, LIGHT, CHARGED }
	public float concentrate;
	public ELEMENT_TYPES type;
	public ElementalType(ELEMENT_TYPES t, float pure)
	{
		concentrate = pure;
		type = t;
	}
}