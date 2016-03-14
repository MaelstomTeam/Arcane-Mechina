package com.maelstrom.armech.common.pleasesortthis;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IItemRegistry;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.IRecipeRegistry;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;

import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.registry.AMBlocks;
import com.maelstrom.armech.common.registry.AMItems;

@JEIPlugin
public class ArcaneMechinaPlugin implements IModPlugin
{

	public static IJeiHelpers jeiHelper;
	public static AMBlocks blocks;
	public static AMItems items;
	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry)
	{
	}

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers helper)
	{
		jeiHelper = helper;
	}

	@Override
	@Deprecated
	public void onRecipeRegistryAvailable(IRecipeRegistry rreg) {
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime runtime) {
	}

	@Override
	public void register(IModRegistry registry)
	{
		jeiHelper.getItemBlacklist().addItemToBlacklist(new ItemStack(blocks.block_chalk));
		jeiHelper.getItemBlacklist().addItemToBlacklist(new ItemStack(items.test_item));
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		for(int i = 0; i < Reference.dustNames.length; i ++)
		{
			list.add(new ItemStack(items.dust_crystal, 1, i));
		}
		registry.addDescription(list, "description.armech.dust");
	}

}
