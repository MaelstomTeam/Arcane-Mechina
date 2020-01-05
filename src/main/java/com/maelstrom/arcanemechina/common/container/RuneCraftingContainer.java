package com.maelstrom.arcanemechina.common.container;

import com.maelstrom.arcanemechina.common.Registry;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;

public class RuneCraftingContainer extends Container
{

	public RuneCraftingContainer(int windowID, World world, BlockPos pos, PlayerInventory inv, PlayerEntity player)
	{
		super(Registry.RCC, windowID);
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		//make sure player is real!!
		return !(playerIn instanceof FakePlayer);
	}

}
