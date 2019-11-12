package com.maelstrom.arcanemechina.common.runic;

import java.util.ArrayList;
import java.util.List;

import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.snowcone.common.WorldUtilities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

public abstract class IRuneType
{
	protected IRuneType parent;
	protected IRuneType[] children;
	
	public IRuneType setParent(IRuneType p)
	{
		parent = p;
		return this;
	}
	
	public int getEmptyChildIndex()
	{
		for(int i = 0; i < children.length; i++)
		{
			if(children[i] == null)
				return i;
		}
		return -1;
	}
	
	public boolean isMyChild(IRuneType r)
	{
		for(IRuneType child : children)
		{
			if(child == r)
				return true;
		}
		return false;
	}
	
	public IRuneType setChildren(IRuneType c, int index)
	{
		children[index] = c;
		c.setParent(this);
		return this;
	}
	
	public IRuneType getParent()
	{
		return parent;
	}
	
	public IRuneType[] getChildren()
	{
		return children;
	}
	public abstract CompoundNBT serializeNBT();
	public abstract void deserializeNBT(CompoundNBT nbt);
	public abstract boolean canBeMyChild(IRuneType rune);
	public abstract short getID();
	public interface hasAction
	{
		public void doAction(RuneTileEntity entity);
	}
	public interface IInventoryRune
	{
		public NonNullList<ItemStack> getItemInput();
		public NonNullList<ItemStack> getItemOutput();
		public boolean canAddItem(ItemStack item);
		public void addItem(ItemStack item);
		public ItemStack removeItem(boolean input, int index, int amount);
	}
	public enum RuneSize
	{
		TINY,
		SMALL,
		MEDIUM,
		LARGE,
		HUGE
	}
	
	public static class RuneList
	{
		public static IRuneType getFromID(short id)
		{
			switch(id)
			{
			case -1: return new RuneContainer();
			case 0: return new VARIBLE(0);
			case 1: return new TOGGLE();
			case 2: return new HOLD();
			}
			return new VARIBLE(0);
		}
		
		public static class RuneContainer extends IRuneType
		{
			private RuneSize runeSize;
			private IRuneType north;
			private IRuneType south;
			private IRuneType east;
			private IRuneType west;
			
			public RuneContainer()
			{
				this(RuneSize.TINY);
			}
			
			public RuneContainer(RuneSize size)
			{
				setSize(size);
			}
			
			public int getCapacity()
			{
				switch(runeSize)
				{
				case HUGE: return 40;
				case LARGE: return 20;
				case MEDIUM:return 9;
				case SMALL: return 4;
				case TINY: return 1;
				}
				return 0;
			}
			
			public void setSize(RuneSize size)
			{
				children = new IRuneType[50];
				this.runeSize = size;
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putShort("ID", getID());
				nbt.putInt("SIZE", runeSize.ordinal());
				ListNBT list = new ListNBT();
				for(IRuneType child : children)
				{
					if(child != null)
						list.add(child.serializeNBT());
				}
				nbt.put("CHILDREN", list);
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt)
			{
				
				setSize(RuneSize.values()[nbt.getInt("SIZE")]);
				ListNBT list = (ListNBT) nbt.get("CHILDREN");//.getList("CHILDREN", 0);
				for(int i = 0; i < list.size(); i++) {
					CompoundNBT tag = list.getCompound(i);
					children[i] = RuneList.getFromID(tag.getShort("ID"));
					children[i].deserializeNBT(tag);
				}
			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				return true;
			}

			@Override
			public short getID() {
				return -1;
			}

			public <T> List<T> getAllOfType(Class<T> classA)
			{
				List<T> listOfAllActions = new ArrayList<T>();

				IRuneType[] list = getChildren();
				for(IRuneType r : list)
				{
					if(r != null)
					{
						List<T> subList = getAllOfType(r, classA);
						if(classA.isInstance(r))
							listOfAllActions.add((T) r);
						listOfAllActions.addAll(subList);
					}
				}
				return listOfAllActions;
			}
			public <T> List<T> getAllOfType(IRuneType rune, Class<T> classA)
			{
				List<T> listOfAllActions = new ArrayList<T>();
				IRuneType[] list = rune.getChildren();
				for(IRuneType r : list)
				{
					if(r != null)
					{
						List<T> subList = getAllOfType(r,classA);
						if(classA.isInstance(r))
							listOfAllActions.add((T) r);
						listOfAllActions.addAll(subList);
					}
				}
				
				return listOfAllActions;
			}

			public List<hasAction> getAll()
			{
				List<hasAction> listOfAllActions = new ArrayList<hasAction>();

				IRuneType[] list = getChildren();
				for(IRuneType r : list)
				{
					if(r != null)
					{
						List<hasAction> subList = getAll(r);
						if(r instanceof hasAction)
							listOfAllActions.add((hasAction) r);
						listOfAllActions.addAll(subList);
					}
				}
				return listOfAllActions;
			}
			public List<hasAction> getAll(IRuneType rune)
			{
				List<hasAction> listOfAllActions = new ArrayList<hasAction>();
				IRuneType[] list = rune.getChildren();
				for(IRuneType r : list)
				{
					if(r != null)
					{
						List<hasAction> subList = getAll(r);
						if(r instanceof hasAction)
							listOfAllActions.add((hasAction) r);
						listOfAllActions.addAll(subList);
					}
				}
				
				return listOfAllActions;
			}
		}
		public static class VARIBLE extends IRuneType
		{
			public short getID()
			{
				return 0;
			}
			private int value = 0;
			
			public VARIBLE(int v)
			{
				value = v;
				children = new IRuneType[0];
			}
			
			
			public void setValue(int v)
			{
				value = v;
			}
			public int getValue()
			{
				return value;
			}
			
			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putShort("ID", getID());
				nbt.putInt("VALUE", value);
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt)
			{
				value = nbt.getInt("VALUE");
			}


			@Override
			public boolean canBeMyChild(IRuneType rune)
			{
				return false;
			}
			
		}
		public static class TOGGLE extends IRuneType implements hasAction
		{

			public short getID()
			{
				return 1;
			}
			
			public TOGGLE()
			{
				children = new IRuneType[2];
			}

			public boolean hasOnVarible()
			{
				return children[1] != null;
			}
			public boolean hasOffVarible()
			{
				return children[0] != null;
			}
			
			protected boolean state = false;
			protected int counter = 0;
			
			public boolean getState()
			{
				return state;
			}
			
			private VARIBLE getOnRune()
			{
				return (VARIBLE) children[1];
			}
			
			private VARIBLE getOffRune()
			{
				return (VARIBLE) children[0];
			}
			
			public void toggleState()
			{
				counter = 0;
				state = !state;
			}
			
			@Override
			public void doAction(RuneTileEntity entity)
			{
				if(!hasOnVarible() && !hasOffVarible())
				{
					//do nothing
				}
				else
				{
					counter++;
					if(state)
					{
						if(hasOnVarible())
						{
							if(counter >= getOnRune().getValue())
							{
								toggleState();
							}
						}
						else
						{
							toggleState();
						}
					}
					else
					{
						if(hasOffVarible())
						{
							if(counter >= getOffRune().getValue())
							{
								toggleState();
							}
						}
						else
						{
							toggleState();
						}
					}
				}
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putShort("ID", getID());
				nbt.putBoolean("STATE", state);
				nbt.putInt("COUNTER", counter);
				ListNBT list = new ListNBT();
				for(int i = 0; i < children.length; i++)
				{
					if(children[i] != null && children[i] instanceof VARIBLE)
					{
						CompoundNBT tag = children[i].serializeNBT();
						tag.putInt("CHILD_NUMBER",i);
						list.add(tag);
					}
				}
				nbt.put("CHILDREN", list);
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt)
			{
				ListNBT list = (ListNBT) nbt.get("CHILDREN");
				state = nbt.getBoolean("STATE");
				counter = nbt.getInt("COUNTER");
				for(int i = 0; i < list.size(); i++)
				{
					CompoundNBT tag = list.getCompound(i);
					int id = tag.getInt("CHILD_NUMBER");
					children[id] = new VARIBLE(0);
					children[id].deserializeNBT(tag);
					children[id].setParent(this);
				}
			}

			@Override
			public boolean canBeMyChild(IRuneType rune)
			{
				return rune instanceof VARIBLE;
			}
			
		}
		public static class HOLD extends IRuneType implements hasAction, IInventoryRune
		{
			protected ItemStack heldItem = ItemStack.EMPTY;
			protected NonNullList<ItemStack> outputItems = NonNullList.withSize(9, ItemStack.EMPTY);
			protected int counter = 0;
			public HOLD()
			{
				children = new IRuneType[1];
			}
			
			public boolean hasItem()
			{
				return !heldItem.isEmpty();
			}
			
			public boolean hasToggle()
			{
				return children[0] != null;
			}

			private TOGGLE getToggle()
			{
				return (TOGGLE) children[0];
			}
			int progress;
			
			@Override
			public void doAction(RuneTileEntity entity) 
			{
				if(hasItem() && hasToggle() && entity.getWorld() instanceof ServerWorld)
				{
					if(heldItem.getItem() == Items.CRAFTING_TABLE)
					{
						//do crafting
						if(isParentRuneContainer())
						{
							
						}
					}
					else
					{
						ServerWorld world = (ServerWorld) entity.getWorld();
						BlockPos pos = entity.getPos().offset(entity.offset());
						BlockState state = world.getBlockState(pos);
						FakePlayer player = WorldUtilities.getFakePlayer(world);
						if(player.getHeldItemMainhand() != heldItem)
							player.inventory.mainInventory.set(0, heldItem);//.setItemStackToSlot(EquipmentSlotType.MAINHAND, heldItem);//.setHeldItem(Hand.MAIN_HAND, heldItem);
						if(world.canMineBlockBody(player, pos))
						{
							if(getToggle().getState())
							{
								counter++;
								progress = (int) ((((float)counter) / state.getBlockHardness(world, pos)));// * player.getHeldItemMainhand().getDestroySpeed(state));
								world.sendBlockBreakProgress(-1, pos, progress - 1);
								if(progress >= 10) {
									List<ItemStack> items = WorldUtilities.BreakBlock(world, pos, player);
									for(ItemStack item2 : items)
									{
										if(item2 != null && !item2.isEmpty())
										{
											boolean found = false;
											for(int i = 0; i < outputItems.size(); i++)
											{
												if(outputItems.get(i).isEmpty())
												{
													outputItems.set(i, item2);	
													found = true;
												}
											}
											if(found)
												world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(),item2));
										}
									}
									world.sendBlockBreakProgress(-1, pos, -1);
									counter = 0;
								}
							}
							else
							{
								if(counter != 0)
								{
									world.sendBlockBreakProgress(-1, pos, -1);
									player.resetActiveHand();
									counter = 0;
								}
							}
						}
					}
				}
			}

			private boolean isParentRuneContainer()
			{
				return parent instanceof RuneContainer;
			}

			@Override
			public CompoundNBT serializeNBT()
			{
				CompoundNBT nbt = new CompoundNBT();
				nbt.putShort("ID", getID());
				nbt.put("HELD_ITEM",heldItem.serializeNBT());
				ListNBT outputList = new ListNBT();
				for(ItemStack i : this.outputItems)
				{
					outputList.add(i.serializeNBT());
				}
				nbt.put("OUTPUT", outputList);
				if(children[0] != null && children[0] instanceof TOGGLE)
				{
					nbt.put("CHILD",children[0].serializeNBT());
				}
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt)
			{
				outputItems.clear();
				heldItem = ItemStack.read(nbt.getCompound("HELD_ITEM"));
				CompoundNBT list = nbt.getCompound("OUTPUT");
				ItemStackHelper.loadAllItems(list, outputItems);
				CompoundNBT tag = (CompoundNBT) nbt.getCompound("CHILD");
				if(!tag.isEmpty())
				{
					children[0] = new TOGGLE();
					children[0].deserializeNBT(tag);
					children[0].setParent(this);
				}
			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				return rune instanceof TOGGLE;
			}

			@Override
			public short getID() {
				return 2;
			}

			public void setItem(ItemStack itemStack)
			{
				heldItem = itemStack;
			}

			@Override
			public NonNullList<ItemStack> getItemInput() {
				NonNullList<ItemStack> list = NonNullList.create();
				list.add(heldItem);
				return list;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput()
			{
				return outputItems;
			}
			
			@Override
			public void addItem(ItemStack item)
			{
				heldItem = item;
			}

			//returns the removed item
			@Override
			public ItemStack removeItem(boolean input, int index, int amount)
			{
				if(input)
				{
					NonNullList<ItemStack> list = getItemInput();
					ItemStack value = ItemStackHelper.getAndSplit(list, 0, amount);
					return value;
				}
				else
				{
					return ItemStackHelper.getAndSplit(outputItems, 0, amount);
				}
			}

			@Override
			public boolean canAddItem(ItemStack item)
			{
				if(item.isStackable() && heldItem.isStackable())
					if(ItemStack.areItemStackTagsEqual(item, heldItem))
						return true;
				if(heldItem.isEmpty())
					return true;
				return false;
						
			}
			
		}
	}
	
}
