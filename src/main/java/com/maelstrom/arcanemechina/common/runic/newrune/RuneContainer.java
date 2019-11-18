package com.maelstrom.arcanemechina.common.runic.newrune;

import java.util.HashMap;
import java.util.UUID;

import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.HoldingRune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.IORune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.ToggleRune;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.VaribleRune;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;

public class RuneContainer {
	
	public static RuneContainer miner = new RuneContainer()
	{
		{
			//declare
			HoldingRune hold = new HoldingRune();
			ToggleRune 	toggle = new ToggleRune();
			VaribleRune var1 = new VaribleRune();
			VaribleRune var2 = new VaribleRune();
			IORune output_items = new IORune();
			IORune input_items = new IORune();
			
			//add to container
			this.addChild(hold);
			this.addChild(toggle);
			this.addChild(var1);
			this.addChild(var2);
			this.addChild(output_items);
			this.addChild(input_items);
			
			//modify values
			hold.setInventorySlotContents(0, new ItemStack(Items.DIAMOND_PICKAXE));
			var1.setValue((short) 20);
			var2.setValue((short) 80);
			output_items.setDirection(Direction.EAST);
			input_items .setDirection(Direction.WEST);
			
			//link to other runes
			hold.addLink(toggle);
			toggle.addLink(var1);
			toggle.addLink(var2);
			output_items.addLink(hold);
			input_items.addLink(hold);
		}
	};
	
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
			return 40;
		case LARGE:
			return 20;
		case MEDIUM:
			return 9;
		case SMALL:
			return 4;
		case TINY:
			return 1;
		}
		return 0;
	}

	public void setSize(RuneSize size) {
		this.runeSize = size;
		HashMap<UUID, RuneType> oldchildren = children;
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

}
