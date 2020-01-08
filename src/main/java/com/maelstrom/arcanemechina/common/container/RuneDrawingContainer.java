package com.maelstrom.arcanemechina.common.container;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.CraftingContainerRune;
import com.maelstrom.arcanemechina.common.runic.RuneType.HoldingRune;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class RuneDrawingContainer extends Container
{
	static TranslationTextComponent title = new TranslationTextComponent("gui.arcanemechina.runecrafting");

	private TileEntity tileEntity;
	private PlayerEntity playerEntity;
	private IItemHandler playerInventory;

	public RuneDrawingContainer(int windowID, World world, BlockPos pos, PlayerInventory inv, PlayerEntity player)
	{
		super(Registry.RDC, windowID);
		this.setTileEntity(world.getTileEntity(pos));
		this.setPlayerEntity(player);
		this.playerInventory = new InvWrapper((IInventory) playerInventory);

		int x = 51;
		int y = 112;
		for (int y2 = 0; y2 < 3; ++y2)
		{
			for (int x2 = 0; x2 < 9; ++x2)
			{
				this.addSlot(new Slot(inv, x2 + y2 * 9 + 9, x + x2 * 18, y + y2 * 18));
			}
		}

		for (int x2 = 0; x2 < 9; ++x2)
		{
			this.addSlot(new Slot(inv, x2, x + x2 * 18, y + 58));
		}
		
		RuneContainer container = ((RuneTileEntity)world.getTileEntity(pos)).getRuneContainer();
		for(RuneType temp : container.getRune(CraftingContainerRune.class))
		{
			CraftingContainerRune rune = (CraftingContainerRune) temp;
			this.addSlot(new Slot(rune, 0, (int)rune.getPosition().x * 5 + 40, (int)rune.getPosition().y * 5 - 2));
		}
		for(RuneType temp : container.getRune(HoldingRune.class))
		{
			HoldingRune rune = (HoldingRune) temp;
			this.addSlot(new Slot(rune, 0, (int)rune.getPosition().x * 5 + 40, (int)rune.getPosition().y * 5 - 2));
		}
	}

	@Override
	public boolean canInteractWith(PlayerEntity playerIn)
	{
		return !(playerIn instanceof FakePlayer) && isWithinUsableDistance(IWorldPosCallable.DUMMY, playerIn, Registry.inWorldRune);
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
