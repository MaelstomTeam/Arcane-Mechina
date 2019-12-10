package com.maelstrom.arcanemechina.common.runic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.maelstrom.arcanemechina.client.tesr.RenderPlane;
import com.maelstrom.arcanemechina.common.runic.RuneType.SubRuneContainer;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.IInventoryRune;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.IRuneRenderer2;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.ITicking;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.ResourceLocation;

public class RuneContainer {
	private String name;
	private RuneSize runeSize;
	private RuneType[] children;// = new HashMap<UUID, RuneType>();
	public int currIDMax = 0;
	private boolean isInternal = false;
	private SubRuneContainer parent;
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
			return 6;
		case TINY:
			return 3;
		}
		return 0;
	}

	public void setSize(RuneSize size) {
		setSize(size,false);
	}

	public void setSize(RuneSize size, boolean clear) {
		this.runeSize = size;
		if(clear)
			children = Arrays.copyOf(children, getCapacity());
		else
			children = new RuneType[this.getCapacity()];
	}
	
	public RuneType getRuneAtPosition(float x, float z)
	{
		return null;
	}

	public RuneType[] getChildren() {
		return children;
	}
	public int getEmptySlot()
	{
		int empty = -1;
		for(RuneType rune : children)
		{
			empty++;
			if(rune == null)
				return empty;
		}
		return -1;
	}
	public void addChild(RuneType child) {
		child.setParent(this);
		if(child.getUUID() != -1 && child.getUUID() < this.getCapacity() && (children[child.getUUID()] == null || children[child.getUUID()] == child))
		{
			this.children[child.getUUID()] = child;
		}
		else
			child.setParent(null);
	}

	public CompoundNBT writeNBT() {
		CompoundNBT nbt = new CompoundNBT();
		nbt.putInt("SIZE", runeSize.ordinal());
		nbt.putBoolean("isInternal", isInternal());
		if(getName() != null)
			nbt.putString("NAME", getName());
		ListNBT list = new ListNBT();
		for (RuneType child : children) {
			if (child != null)
				list.add(child.writeToNBT());
		}
		nbt.put("CHILDREN", list);
		return nbt;
	}

	public void readNBT(CompoundNBT nbt) {
		if(nbt.get("NAME") != null)
			setName(nbt.getString("NAME"));
		if(nbt.get("isInternal") != null)
			this.setInternal(nbt.getBoolean("isInternal"));
		children = new RuneType[this.getCapacity()];
		setSize(RuneSize.values()[nbt.getInt("SIZE")]);
		ListNBT list = (ListNBT) nbt.get("CHILDREN");
		for (int i = 0; i < list.size(); i++) {
			CompoundNBT tag = list.getCompound(i);
			RuneType rune = RuneType.getFromID(tag.getShort("RuneID"));
			rune.readFromNBT(tag);
			addChild(rune);
		}
	}

	public RuneType getLink(int uuid)
	{
		for(RuneType rune : children)
		{	
			if(uuid ==  rune.getUUID()) {
				return rune;
			}
		}
		return children[uuid];
	}
	
	public void tick(RuneTileEntity rune_tile)
	{
		for(RuneType rune : children)
		{
			if(rune instanceof ITicking)
			{
				((ITicking) rune).doAction(rune_tile,0);//pre tick
				((ITicking) rune).doAction(rune_tile,1);//tick
				((ITicking) rune).doAction(rune_tile,2);//post tick
			}
		}
	}
	
	public void tick(RuneTileEntity rune_tile, int phase)
	{
		for(RuneType rune : children)
		{
			if(rune instanceof ITicking)
			{
				((ITicking) rune).doAction(rune_tile,phase);
			}
		}
	}


	static final ResourceLocation tiny_resource   = new ResourceLocation("arcanemechina:textures/runes/circle.png");
	static final ResourceLocation small_resource  = new ResourceLocation("arcanemechina:textures/runes/64px.png");
	static final ResourceLocation medium_resource = new ResourceLocation("arcanemechina:textures/runes/128px.png");
	static final ResourceLocation large_resource  = new ResourceLocation("arcanemechina:textures/runes/256px.png");
	static final ResourceLocation huge_resource   = new ResourceLocation("arcanemechina:textures/runes/512px.png");

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
			return 1.5f;
		case SMALL:
			return 1f;
		case TINY:
			return .5f;
		}
		return 1f;
	}

	static final RenderPlane plane = new RenderPlane();
	public void render(float partial_ticks)
	{
		GlStateManager.pushMatrix();
		GlStateManager.pushMatrix();
		switch(this.runeSize)
		{
		case HUGE:
			break;
		case LARGE:
			break;
		case MEDIUM:
			GlStateManager.translated(-16f/getRuneScale()/2f, 0, -16f/getRuneScale()/2f);
			break;
		case SMALL:
			GlStateManager.translated(-.5, 0, -.5);
			break;
		case TINY:
			break;
		
		}
			GlStateManager.scaled(getRuneScale(),getRuneScale(),getRuneScale());
			IRuneRenderer2.bindTexture(getRuneResource());
			plane.render();
		GlStateManager.popMatrix();
		for(RuneType rune : children)
			if(rune != null)
			{
			if(rune instanceof IRuneRenderer2)
			{
				GlStateManager.pushMatrix();
				GlStateManager.translated(1, 0, 1);
				GlStateManager.translated(-1, 0, -0.5);
				((IRuneRenderer2) rune).render(partial_ticks);
				GlStateManager.popMatrix();
			}
			}
		GlStateManager.popMatrix();
	}

	public boolean hasRune(Class<? extends RuneType> type)
	{
		if(type == null)
			return false;
		for(RuneType rune : children)
		{
			if(rune != null)
			if(rune.getClass().equals(type))
				return true;
		}
		return false;
	}

	public List<RuneType> getRune(Class<? extends RuneType> get)
	{
		List<RuneType> list = new ArrayList<RuneType>();
		for(RuneType rune : children)
			if(rune != null)
			if (rune.getClass().equals(get))
				list.add(rune);
		return list;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void markDirty()
	{
		for(RuneType rune : children)
			if(rune instanceof IInventoryRune)
				((IInventoryRune)rune).markDirty();
	}

	public boolean isInternal()
	{
		return isInternal;
	}

	public void setInternal(boolean isInternal)
	{
		this.isInternal = isInternal;
	}

	public SubRuneContainer getParent()
	{
		return parent;
	}

	public void setParent(SubRuneContainer parent)
	{
		this.parent = parent;
	}
	
}
