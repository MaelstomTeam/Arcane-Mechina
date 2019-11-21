package com.maelstrom.arcanemechina.common.runic.newrune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.client.tesr.RenderPlane;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.IRuneRenderer2;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.ITicking;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;

public class RuneContainer {
	
	public static RuneContainer getNewMiningRune(){
		RuneContainer container = new RuneContainer();
		//declare
		RuneType.HoldingRune hold         = new RuneType.HoldingRune();
		RuneType.ToggleRune  toggle       = new RuneType.ToggleRune();
		RuneType.VaribleRune off          = new RuneType.VaribleRune();
		RuneType.VaribleRune on           = new RuneType.VaribleRune();
		RuneType.IORune      output_items = new RuneType.IORune();
		RuneType.IORune      input_items  = new RuneType.IORune();
		container.setSize(RuneSize.MEDIUM);
		
		//add to container
		container.addChild(hold);
		container.addChild(toggle);
		container.addChild(on);
		container.addChild(off);
		container.addChild(output_items);
		container.addChild(input_items);
		
		//modify values
		hold.setInventorySlotContents(0, new ItemStack(Items.DIAMOND_PICKAXE));
		on.setValue((short) 200);
		off.setValue((short) 0);
		output_items.setDirection(Direction.EAST);
		output_items.setInput(false);
		input_items .setDirection(Direction.WEST);
		
		//link to other runes
		hold.addLink(toggle);
		toggle.addLink(on);
		toggle.addLink(off);
		output_items.addLink(hold);
		input_items.addLink(hold);
		return container;
	}
	
	public static RuneContainer getNewCraftingRune(){
		RuneContainer container = new RuneContainer();
		//declare
		RuneType.HoldingRune hold = new RuneType.HoldingRune();
		RuneType.ToggleRune 	toggle = new RuneType.ToggleRune();
		RuneType.VaribleRune off = new RuneType.VaribleRune();
		RuneType.VaribleRune on = new RuneType.VaribleRune();
		RuneType.IORune output_items = new RuneType.IORune();
		RuneType.IORune input_items = new RuneType.IORune();
		RuneType.CraftingContainerRune recipe_rune = new RuneType.CraftingContainerRune();

		container.setSize(RuneSize.MEDIUM);
		//add to container
		container.addChild(hold);
		container.addChild(toggle);
		container.addChild(off);
		container.addChild(on);
		container.addChild(output_items);
		container.addChild(input_items);
		container.addChild(recipe_rune);
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
		if(children.size() < this.getCapacity())
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
		for(RuneType rune : children.values())
		{	
			if(uuid ==  rune.getUUID())
				return rune;
		}
		return children.get(uuid);
	}
	
	public void tick(RuneTileEntity rune_tile)
	{
		for(RuneType rune : children.values())
		{
			if(rune instanceof ITicking)
				((ITicking) rune).doAction(rune_tile);
		}
	}


	static final ResourceLocation tiny_resource = new ResourceLocation("arcanemechina:textures/runes/circle.png");
	static final ResourceLocation small_resource = new ResourceLocation("arcanemechina:textures/runes/64px.png");
	static final ResourceLocation medium_resource = new ResourceLocation("arcanemechina:textures/runes/128px.png");
	static final ResourceLocation large_resource = new ResourceLocation("arcanemechina:textures/runes/256px.png");
	static final ResourceLocation huge_resource = new ResourceLocation("arcanemechina:textures/runes/512px.png");

	public ResourceLocation getRuneResource() {
		switch (this.runeSize) {
		case HUGE:
			return huge_resource;
		case LARGE:
			return large_resource;
		case MEDIUM:
			return medium_resource;
		case SMALL:
			return small_resource;
		case TINY:
			return tiny_resource;
		}
		return tiny_resource;
	}

	public float getRuneScale() {
		switch (this.runeSize) {
		case HUGE:
			return 4f;
		case LARGE:
			return 3f;
		case MEDIUM:
			return 2f;
		case SMALL:
			return 1f;
		case TINY:
			return .5f;
		}
		return .5f;
	}

	static final RenderPlane plane = new RenderPlane();
	public void render(float partial_ticks, RuneTileEntity rune_tile)
	{
		GlStateManager.pushMatrix();
		IRuneRenderer2.bindTexture(getRuneResource());
		plane.render();
		GlStateManager.popMatrix();
		for(RuneType rune : children.values())
			if(rune instanceof IRuneRenderer2)
			{
				GlStateManager.pushMatrix();
				((IRuneRenderer2) rune).render(partial_ticks);
				GlStateManager.popMatrix();
			}
	}

	public boolean hasRune(Class<? extends RuneType> type)
	{
		for(RuneType rune : children.values())
		{
			if(rune.getClass().equals(type))
				return true;
		}
		return false;
	}

	public List<RuneType> getRune(Class<? extends RuneType> get)
	{
		List<RuneType> list = new ArrayList<RuneType>();
		for(RuneType rune : children.values())
			if (rune.getClass().equals(get))
				list.add(rune);
		return list;
	}
	
}
