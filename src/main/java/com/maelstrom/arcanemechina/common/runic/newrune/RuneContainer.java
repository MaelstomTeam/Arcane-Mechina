package com.maelstrom.arcanemechina.common.runic.newrune;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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
			return 6;
		case TINY:
			return 3;
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
	
	public RuneType getRuneAtPosition(float x, float z)
	{
		return null;
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
			if(uuid ==  rune.getUUID()) {
				return rune;
			}
		}
		return children.get(uuid);
	}
	
	public void tick(RuneTileEntity rune_tile)
	{
		for(RuneType rune : children.values())
		{
			if(rune instanceof ITicking)
			{
				((ITicking) rune).doAction(rune_tile,0);//pre tick
				((ITicking) rune).doAction(rune_tile,1);//tick
				((ITicking) rune).doAction(rune_tile,2);//post tick
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
	public void render(float partial_ticks, RuneTileEntity rune_tile)
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
		for(RuneType rune : children.values())
			if(rune instanceof IRuneRenderer2)
			{
				GlStateManager.pushMatrix();
				GlStateManager.translated(1, 0, 1);
				GlStateManager.translated(-1, 0, -0.5);
				((IRuneRenderer2) rune).render(partial_ticks);
				GlStateManager.popMatrix();
			}
		GlStateManager.popMatrix();
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
