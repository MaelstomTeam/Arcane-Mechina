package com.maelstrom.arcanemechina.common.container;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.inventory.BlueprintSlot;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class RuneCraftingContainer extends Container
{

	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;
	private IInventory inventory;

	public RuneCraftingContainer(int windowID, World world, BlockPos pos, PlayerInventory inv, PlayerEntity player)
	{
		super(Registry.RCC, windowID);
		this.setTileEntity(world.getTileEntity(pos));
		this.setPlayerEntity(player);
		this.playerInventory = new InvWrapper((IInventory) playerInventory);
		this.inventory = (IInventory) world.getTileEntity(pos);
		int gui_x = 8;
		int gui_y = 84;
		for (int slot_y = 0; slot_y < 3; ++slot_y)
		{
			for (int slot_x = 0; slot_x < 9; ++slot_x)
			{
				this.addSlot(new Slot(inv, slot_x + slot_y * 9 + 9, gui_x + slot_x * 18, gui_y + slot_y * 18));
			}
		}

		for (int slot_x = 0; slot_x < 9; ++slot_x)
		{
			this.addSlot(new Slot(inv, slot_x, gui_x + slot_x * 18, gui_y + 58));
		}
		this.addSlot(new BlueprintSlot(inventory, inventory.getSizeInventory()-1, gui_x, gui_y-76));
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return !(playerIn instanceof FakePlayer);
	}

	public TileEntity getTileEntity()
	{
		return tileEntity;
	}

	private void setTileEntity(TileEntity tileEntity)
	{
		this.tileEntity = tileEntity;
	}

	public PlayerEntity getPlayerEntity()
	{
		return playerEntity;
	}

	private void setPlayerEntity(PlayerEntity playerEntity)
	{
		this.playerEntity = playerEntity;
	}
}
