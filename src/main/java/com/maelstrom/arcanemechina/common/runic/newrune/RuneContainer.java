package com.maelstrom.arcanemechina.common.runic.newrune;

import java.util.HashMap;
import java.util.UUID;

import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.CraftingContainerRune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.HoldingRune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.IORune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.ToggleRune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.VaribleRune;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.IRuneRenderer2;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.ITicking;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;

public class RuneContainer {
	
	public static RuneContainer getNewMiningRune(){
		RuneContainer container = new RuneContainer();
		//declare
		HoldingRune hold = new HoldingRune();
		ToggleRune 	toggle = new ToggleRune();
		VaribleRune off = new VaribleRune();
		VaribleRune on = new VaribleRune();
		IORune output_items = new IORune();
		IORune input_items = new IORune();
		
		//add to container
		container.addChild(hold);
		container.addChild(toggle);
		container.addChild(off);
		container.addChild(on);
		container.addChild(output_items);
		container.addChild(input_items);
		container.setSize(RuneSize.MEDIUM);
		
		//modify values
		hold.setInventorySlotContents(0, new ItemStack(Items.DIAMOND_PICKAXE));
		off.setValue((short) 20);
		on.setValue((short) 80);
		output_items.setDirection(Direction.EAST);
		input_items .setDirection(Direction.WEST);
		
		//link to other runes
		hold.addLink(toggle);
		toggle.addLink(off);
		toggle.addLink(on);
		output_items.addLink(hold);
		input_items.addLink(hold);
		return container;
	}
	
	public static RuneContainer getNewCraftingRune(){
		RuneContainer container = new RuneContainer();
		//declare
		HoldingRune hold = new HoldingRune();
		ToggleRune 	toggle = new ToggleRune();
		VaribleRune off = new VaribleRune();
		VaribleRune on = new VaribleRune();
		IORune output_items = new IORune();
		IORune input_items = new IORune();
		CraftingContainerRune recipe_rune = new CraftingContainerRune();
		
		//add to container
		container.addChild(hold);
		container.addChild(toggle);
		container.addChild(off);
		container.addChild(on);
		container.addChild(output_items);
		container.addChild(input_items);
		container.addChild(recipe_rune);
		container.setSize(RuneSize.MEDIUM);
		//modify values
		ItemStack stone_reference = new ItemStack(Items.COBBLESTONE);
		hold.setInventorySlotContents(0, new ItemStack(Items.CRAFTING_TABLE));
		recipe_rune.setInventorySlotContents(0, RecipeHelper.createFromListToItemStack(
				new ItemStack[][]{
					new ItemStack[] {stone_reference, stone_reference, stone_reference},
					new ItemStack[] {stone_reference, ItemStack.EMPTY, stone_reference},
					new ItemStack[] {stone_reference, stone_reference, stone_reference}
				}));
		off.setValue((short) 19);
		on.setValue((short) 1);
		output_items.setDirection(Direction.EAST);
		input_items .setDirection(Direction.WEST);
		
		//link to other runes
		hold.addLink(toggle);
		hold.addLink(recipe_rune);
		toggle.addLink(off);
		toggle.addLink(on);
		output_items.addLink(hold);
		input_items.addLink(hold);
		return container;
	}
	
	private RuneSize runeSize;
	private HashMap<UUID, RuneType> children = new HashMap<UUID, RuneType>();

	public RuneContainer() {
		this(RuneSize.MEDIUM);
	}

	public RuneContainer(RuneSize size) {
		this.setSize(size);
	}

	public int getCapacity() {
		switch (runeSize) {
		case HUGE:
			return 27;
		case LARGE:
			return 18;
		case MEDIUM:
			return 9;
		case SMALL:
			return 5;
		case TINY:
			return 2;
		}
		return 0;
	}

	public void setSize(RuneSize size) {
		this.runeSize = size;
		HashMap<UUID, RuneType> oldchildren = children;
		int max = 0;
		for(RuneType rune : oldchildren.values())
		{
			addChild(rune);
		}
	}

	public HashMap<UUID, RuneType> getChildren() {
		return children;
	}

	public void addChild(RuneType child) {
		if(this.getCapacity() < children.size())
		{
			this.children.put(child.getUUID(),child);
			child.setParent(this);
		}
		else
			//BECOME BATMAN!
			child.setParent(null);
	}

	public CompoundNBT writeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("SIZE", runeSize.ordinal());
		ListNBT list = new ListNBT();
		for (RuneType child : children.values()) {
			if (child != null)
				list.add(child.writeToNBT());
		}
		nbt.put("CHILDREN", list);
		return nbt;
	}

	public void readNBT(CompoundNBT nbt) {
		children.clear();
		setSize(RuneSize.values()[nbt.getInt("SIZE")]);
		ListNBT list = (ListNBT) nbt.get("CHILDREN");
		for (int i = 0; i < list.size(); i++) {
			CompoundNBT tag = list.getCompound(i);
			RuneType rune = RuneType.getFromID(tag.getShort("ID"));
			rune.readFromNBT(tag);
			addChild(rune);
		}
	}

	public RuneType getLink(UUID uuid)
	{
		return children.get(uuid);
	}
	
	public void tick(RuneTileEntity rune_tile)
	{
		for(RuneType rune : children.values())
			if(rune instanceof ITicking)
				((ITicking) rune).doAction(rune_tile);
	}
	
	public void render(float partial_ticks, RuneTileEntity rune_tile)
	{
		for(RuneType rune : children.values())
			if(rune instanceof IRuneRenderer2)
			{
				GlStateManager.pushMatrix();
				((IRuneRenderer2) rune).render(partial_ticks);
				GlStateManager.popMatrix();
			}
	}
	
}
