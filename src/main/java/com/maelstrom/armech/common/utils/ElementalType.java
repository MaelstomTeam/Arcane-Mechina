package com.maelstrom.armech.common.utils;

import java.awt.Color;

public class ElementalType 
{
	public static enum ELEMENT_TYPES {
		FIRE(Color.RED), WATER(Color.BLUE), EARTH(Color.GREEN), AIR(Color.YELLOW), DARK(Color.BLACK), LIGHT(Color.WHITE), CHARGED(Color.CYAN);
		public Color clr= Color.WHITE;
		private ELEMENT_TYPES(Color color)
		{
			clr = color;
		}
	}
	public float concentrate;
	public ELEMENT_TYPES type;
	public boolean isValid = true;
	public ElementalType(ELEMENT_TYPES t, float pure)
	{
		concentrate = pure;
		type = t;
	}
	public void addConcentrate(ElementalType f)
	{
		concentrate += f.concentrate;
		f.isValid = false;
	}
}