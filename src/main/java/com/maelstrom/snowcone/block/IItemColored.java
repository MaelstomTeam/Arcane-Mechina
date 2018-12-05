package com.maelstrom.snowcone.block;

import com.maelstrom.snowcone.client.BasicColorHandler;

public interface IItemColored
{   
	public default BasicColorHandler getColorHandler()
	{
		return new BasicColorHandler();
	}
}
