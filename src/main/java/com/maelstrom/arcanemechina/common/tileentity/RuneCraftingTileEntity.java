package com.maelstrom.arcanemechina.common.tileentity;

import com.maelstrom.arcanemechina.client.gui.RuneWorldCraftingGui;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.container.RuneCraftingContainer;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.extensions.IForgeTileEntity;

public class RuneCraftingTileEntity extends TileEntity implements ITickableTileEntity, IForgeTileEntity, INamedContainerProvider
{

	public RuneCraftingTileEntity(TileEntityType<?> tileEntityTypeIn)
	{
		super(tileEntityTypeIn);
	}

	public RuneCraftingTileEntity() {
		this(Registry.RUNE_CRAFT_TILE);
	}

	@Override
	public Container createMenu(int id, PlayerInventory inv, PlayerEntity player)
	{
		return new RuneCraftingContainer(id,world,pos,inv,player);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return RuneWorldCraftingGui.title;
	}

	@Override
	public void tick()
	{
		// TODO Auto-generated method stub
		
	}
	
}
