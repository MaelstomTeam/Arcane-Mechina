package com.maelstrom.arcanemechina.common.runic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.RuneContainer;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.snowcone.common.WorldUtilities;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.util.FakePlayer;

public abstract class IRuneType {

	public CompoundNBT writeToNBT(){
		CompoundNBT nbt = new CompoundNBT();
		nbt.putShort("ID", getID());
		nbt.put("data",this.serializeNBT());
		return nbt;
	}

	public void readFromNBT(CompoundNBT nbt)
	{
		deserializeNBT(nbt.getCompound("data"));
	}
	
	
	
	
	protected IRuneType parent;
	protected IRuneType[] children;

	public IRuneType setParent(IRuneType p) {
		parent = p;
		return this;
	}

	public int getEmptyChildIndex() {
		for (int i = 0; i < children.length; i++) {
			if (children[i] == null)
				return i;
		}
		return -1;
	}

	public boolean isMyChild(IRuneType r) {
		for (IRuneType child : children) {
			if (child == r)
				return true;
		}
		return false;
	}

	public IRuneType setChildren(IRuneType c, int index) {
		children[index] = c;
		c.setParent(this);
		return this;
	}

	public IRuneType getParent() {
		return parent;
	}

	public IRuneType[] getChildren() {
		return children;
	}

	public abstract CompoundNBT serializeNBT();

	public abstract void deserializeNBT(CompoundNBT nbt);

	public abstract boolean canBeMyChild(IRuneType rune);

	public abstract short getID();

	public boolean isParentRuneContainer() {
		return parent != null && parent instanceof RuneContainer;
	}

	public interface hasAction {
		public void doAction(RuneTileEntity entity);
	}

	public interface IInventoryRune {
		public NonNullList<ItemStack> getItemInput();

		public NonNullList<ItemStack> getItemOutput();

		public boolean canAddItem(ItemStack item);

		public void addItem(ItemStack item);

		public ItemStack removeItem(boolean input, int index, int amount);
	}

	public enum RuneSize {
		TINY, SMALL, MEDIUM, LARGE, HUGE
	}

	public static class RuneList {
		public static IRuneType getFromID(short id) {
			switch (id) {
			case -1:
				return new RuneContainer();
			case 0:
				return new VARIBLE(0);
			case 1:
				return new TOGGLE();
			case 2:
				return new HOLD();
			case 3:
				return new SPLIT();
			case 4:
				return new INSERT();
			case 5:
				return new EXTRACT();
			case 6:
				return new TRANSMUTE();
			case 7:
				return new CONSUME();
			}
			return new VARIBLE(0);
		}

		public static class RuneContainer extends IRuneType {
			private RuneSize runeSize;

			public boolean isCraftingHolder() {
				if (runeSize == RuneSize.MEDIUM || runeSize == RuneSize.SMALL) {
					for (int i = 0; i < children.length; i++) {
						if (!(children[i] instanceof HOLD))
							return false;
					}
					return true;
				} else
					return false;
			}

			public RuneContainer() {
				this(RuneSize.TINY);
			}

			public RuneContainer(RuneSize size) {
				setSize(size);
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
				children = new IRuneType[getCapacity()];
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putInt("SIZE", runeSize.ordinal());
				ListNBT list = new ListNBT();
				for (IRuneType child : children) {
					if (child != null)
						list.add(child.writeToNBT());
				}
				nbt.put("CHILDREN", list);
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {

				setSize(RuneSize.values()[nbt.getInt("SIZE")]);
				ListNBT list = (ListNBT) nbt.get("CHILDREN");
				for (int i = 0; i < list.size(); i++) {
					CompoundNBT tag = list.getCompound(i);
					children[i] = RuneList.getFromID(tag.getShort("ID"));
					children[i].readFromNBT(tag);
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

			public <T> List<T> getAllOfType(Class<T> classA) {
				List<T> listOfAllActions = new ArrayList<T>();

				IRuneType[] list = getChildren();
				for (IRuneType r : list) {
					if (r != null) {
						List<T> subList = getAllOfType(r, classA);
						if (classA.isInstance(r))
							listOfAllActions.add((T) r);
						listOfAllActions.addAll(subList);
					}
				}
				return listOfAllActions;
			}

			public <T> List<T> getAllOfType(IRuneType rune, Class<T> classA) {
				List<T> listOfAllActions = new ArrayList<T>();
				IRuneType[] list = rune.getChildren();
				for (IRuneType r : list) {
					if (r != null) {
						List<T> subList = getAllOfType(r, classA);
						if (classA.isInstance(r))
							listOfAllActions.add((T) r);
						listOfAllActions.addAll(subList);
					}
				}

				return listOfAllActions;
			}

			public List<hasAction> getAll() {
				List<hasAction> listOfAllActions = new ArrayList<hasAction>();

				IRuneType[] list = getChildren();
				for (IRuneType r : list) {
					if (r != null) {
						List<hasAction> subList = getAll(r);
						if (r instanceof hasAction)
							listOfAllActions.add((hasAction) r);
						listOfAllActions.addAll(subList);
					}
				}
				return listOfAllActions;
			}

			public List<hasAction> getAll(IRuneType rune) {
				List<hasAction> listOfAllActions = new ArrayList<hasAction>();
				IRuneType[] list = rune.getChildren();
				for (IRuneType r : list) {
					if (r != null) {
						List<hasAction> subList = getAll(r);
						if (r instanceof hasAction)
							listOfAllActions.add((hasAction) r);
						listOfAllActions.addAll(subList);
					}
				}

				return listOfAllActions;
			}

			private WorkbenchContainer container;

			public ICraftingRecipe getRecipe(World world) {
				int x;
				if (this.getCapacity() == 9)
					x = 3;
				else if (this.getCapacity() == 4)
					x = 2;
				else
					return null;
				if (container == null)
					container = new WorkbenchContainer(0, WorldUtilities
							.getFakePlayer(world.getServer().getWorld(world.dimension.getType())).inventory);
				int counter = 0;
				for (int x1 = 0; x1 < 3; x1++) {
					for (int y1 = 0; y1 < 3; y1++) {
						if (x1 < x && y1 < x) {
							container.putStackInSlot(x1 + y1 * 3, ((HOLD) children[counter]).heldItem);
							counter++;
						}
					}
				}
				Optional<ICraftingRecipe> s = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING,
						new CraftingInventory(container, x, x), world);
				if (s.isPresent())
					return s.get();
				return null;

			}

			public List<ItemStack> getCraftInventory() {
				if (this.isCraftingHolder()) {
					List<ItemStack> list = new ArrayList<ItemStack>();
					for (IRuneType rune : children) {
						list.add(((HOLD) rune).heldItem);
					}
					return list;
				}
				return null;
			}
		}

		public static class VARIBLE extends IRuneType {
			public short getID() {
				return 0;
			}

			private int value = 0;

			public VARIBLE(int v) {
				value = v;
				children = new IRuneType[0];
			}

			public void setValue(int v) {
				value = v;
			}

			public int getValue() {
				return value;
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putInt("VALUE", value);
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				value = nbt.getInt("VALUE");
			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				return false;
			}

		}

		public static class TOGGLE extends IRuneType implements hasAction {

			public short getID() {
				return 1;
			}

			public TOGGLE() {
				children = new IRuneType[2];
			}

			public boolean hasOnVarible() {
				return children[1] != null;
			}

			public boolean hasOffVarible() {
				return children[0] != null;
			}

			protected boolean state = false;
			protected int counter = 0;

			public boolean getState() {
				return state;
			}

			private VARIBLE getOnRune() {
				return (VARIBLE) children[1];
			}

			private VARIBLE getOffRune() {
				return (VARIBLE) children[0];
			}

			public void toggleState() {
				counter = 0;
				state = !state;
			}

			@Override
			public void doAction(RuneTileEntity entity) {
				if (!hasOnVarible() && !hasOffVarible()) {
					// do nothing
				} else {
					counter++;
					if (state) {
						if (hasOnVarible()) {
							if (counter >= getOnRune().getValue()) {
								toggleState();
							}
						} else {
							toggleState();
						}
					} else {
						if (hasOffVarible()) {
							if (counter >= getOffRune().getValue()) {
								toggleState();
							}
						} else {
							toggleState();
						}
					}
				}
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putBoolean("STATE", state);
				nbt.putInt("COUNTER", counter);
				ListNBT list = new ListNBT();
				for (int i = 0; i < children.length; i++) {
					if (children[i] != null && children[i] instanceof VARIBLE) {
						CompoundNBT tag = children[i].writeToNBT();
						tag.putInt("CHILD_NUMBER", i);
						list.add(tag);
					}
				}
				nbt.put("CHILDREN", list);
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				ListNBT list = (ListNBT) nbt.get("CHILDREN");
				state = nbt.getBoolean("STATE");
				counter = nbt.getInt("COUNTER");
				for (int i = 0; i < list.size(); i++) {
					CompoundNBT tag = list.getCompound(i);
					int id = tag.getInt("CHILD_NUMBER");
					children[id] = new VARIBLE(0);
					children[id].readFromNBT(tag);
					children[id].setParent(this);
				}
			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				return rune instanceof VARIBLE;
			}

		}

		public static class HOLD extends IRuneType implements hasAction, IInventoryRune {
			protected ItemStack heldItem = ItemStack.EMPTY;
			protected NonNullList<ItemStack> outputItems = NonNullList.withSize(9, ItemStack.EMPTY);
			protected int counter = 0;
			protected static int currSubId = -1;
			protected int subID;
			public HOLD() {
				children = new IRuneType[1];
				subID = currSubId--;
			}

			public boolean hasItem() {
				return !heldItem.isEmpty();
			}

			public boolean hasToggle() {
				return children[0] != null;
			}

			private TOGGLE getToggle() {
				return (TOGGLE) children[0];
			}

			int progress;
			private RuneContainer craftReference;

			@Override
			public void doAction(RuneTileEntity entity) {
				if (hasItem() && hasToggle() && entity.getWorld() instanceof ServerWorld) {
					if (heldItem.getItem() == Items.CRAFTING_TABLE && false) // temp disabled due to not being tested!
					{
						// do crafting
						if (isParentRuneContainer()) {
							if (getParent().isParentRuneContainer()) {
								if (craftReference == null) {
									for (IRuneType type : getParent().getParent().getChildren()) {
										if (type != null && type != getParent() && type instanceof RuneContainer
												&& ((RuneContainer) type).isCraftingHolder()) {
											craftReference = (RuneContainer) type;
											break;
										}
									}
								}
								if (craftReference != null) {
									boolean canCraft = true;
									ICraftingRecipe recipe = craftReference.getRecipe(entity.getWorld());
									for (ItemStack i : craftReference.getCraftInventory()) {
										if (i.getCount() <= 1 || i.hasContainerItem())
											canCraft = false;
									}
									if (canCraft && this.canAddItem(recipe.getRecipeOutput())) {
										for (ItemStack i : craftReference.getCraftInventory()) {
											i.shrink(1);
										}
										this.addItem(recipe.getRecipeOutput().copy());
									}
								}

							}
						}
					} else {
						ServerWorld world = (ServerWorld) entity.getWorld();
						BlockPos pos = entity.getPos().offset(entity.offset());
						BlockState state = world.getBlockState(pos);
						FakePlayer player = WorldUtilities.getFakePlayer(world);
						if (player.getHeldItemMainhand() != heldItem)
							player.inventory.mainInventory.set(0, heldItem);
						if (!world.isAirBlock(pos) && world.canMineBlockBody(player, pos)) {
							if (getToggle().getState()) {
								ArcaneMechina.LOGGER.info(state.getBlockHardness(world, pos)/heldItem.getDestroySpeed(state) * counter);
								progress = (int) (state.getBlockHardness(world, pos)/heldItem.getDestroySpeed(state) * counter);
								world.sendBlockBreakProgress(subID, pos, progress - 1);
								if (progress >= 10) {
									List<ItemStack> items = WorldUtilities.BreakBlock(world, pos, player);
									for (ItemStack item2 : items) {
										if (item2 != null && !item2.isEmpty()) {
											boolean found = false;
											for (int i = 0; i < outputItems.size(); i++) {
												if (outputItems.get(i).isEmpty()) {
													outputItems.set(i, item2);
													found = true;
												}
											}
											if (found)
												world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(),
														pos.getZ(), item2));
										}
									}
									world.sendBlockBreakProgress(subID, pos, -1);
									counter = 0;
								}else
									counter++;
							} else {
								if (counter != 0) {
									world.sendBlockBreakProgress(subID, pos, -1);
									counter = 0;
								}
							}
						} else {
							if (counter != 0) {
								world.sendBlockBreakProgress(subID, pos, -1);
								counter = 0;
							}
						}
					}
				}
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.put("HELD_ITEM", heldItem.serializeNBT());
				ListNBT outputList = new ListNBT();
				for (ItemStack i : this.outputItems) {
					outputList.add(i.serializeNBT());
				}
				nbt.put("OUTPUT", outputList);
				if (children[0] != null && children[0] instanceof TOGGLE) {
					nbt.put("CHILD", children[0].writeToNBT());
				}
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				outputItems.clear();
				heldItem = ItemStack.read(nbt.getCompound("HELD_ITEM"));
				CompoundNBT list = nbt.getCompound("OUTPUT");
				ItemStackHelper.loadAllItems(list, outputItems);
				CompoundNBT tag = (CompoundNBT) nbt.getCompound("CHILD");
				if (!tag.isEmpty()) {
					children[0] = new TOGGLE();
					children[0].readFromNBT(tag);
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

			public void setItem(ItemStack itemStack) {
				heldItem = itemStack;
			}

			@Override
			public NonNullList<ItemStack> getItemInput() {
				NonNullList<ItemStack> list = NonNullList.create();
				list.add(heldItem);
				return list;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput() {
				return outputItems;
			}

			@Override
			public void addItem(ItemStack item) {
				heldItem = item;
			}

			// returns the removed item
			@Override
			public ItemStack removeItem(boolean input, int index, int amount) {
				if (input) {
					NonNullList<ItemStack> list = getItemInput();
					ItemStack value = ItemStackHelper.getAndSplit(list, 0, amount);
					return value;
				} else {
					return ItemStackHelper.getAndSplit(outputItems, 0, amount);
				}
			}

			@Override
			public boolean canAddItem(ItemStack item) {
				if (item.isStackable() && heldItem.isStackable())
					if (ItemStack.areItemStackTagsEqual(item, heldItem))
						return true;
				if (heldItem.isEmpty())
					return true;
				return false;

			}

		}

		public static class SPLIT extends IRuneType implements hasAction, IInventoryRune {

			@Override
			public NonNullList<ItemStack> getItemInput() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean canAddItem(ItemStack item) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void addItem(ItemStack item) {
				// TODO Auto-generated method stub

			}

			@Override
			public ItemStack removeItem(boolean input, int index, int amount) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void doAction(RuneTileEntity entity) {
				// TODO Auto-generated method stub

			}

			@Override
			public CompoundNBT serializeNBT() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public short getID() {
				// TODO Auto-generated method stub
				return 3;
			}

		}

		public static class INSERT extends IRuneType implements hasAction, IInventoryRune {

			@Override
			public NonNullList<ItemStack> getItemInput() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean canAddItem(ItemStack item) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void addItem(ItemStack item) {
				// TODO Auto-generated method stub

			}

			@Override
			public ItemStack removeItem(boolean input, int index, int amount) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void doAction(RuneTileEntity entity) {
				// TODO Auto-generated method stub

			}

			@Override
			public CompoundNBT serializeNBT() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public short getID() {
				// TODO Auto-generated method stub
				return 4;
			}

		}

		public static class EXTRACT extends IRuneType implements hasAction, IInventoryRune {

			@Override
			public NonNullList<ItemStack> getItemInput() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean canAddItem(ItemStack item) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void addItem(ItemStack item) {
				// TODO Auto-generated method stub

			}

			@Override
			public ItemStack removeItem(boolean input, int index, int amount) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void doAction(RuneTileEntity entity) {
				// TODO Auto-generated method stub

			}

			@Override
			public CompoundNBT serializeNBT() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public short getID() {
				// TODO Auto-generated method stub
				return 5;
			}

		}

		public static class TRANSMUTE extends IRuneType implements hasAction, IInventoryRune {

			private NonNullList<ItemStack> list = NonNullList.withSize(2, ItemStack.EMPTY);
			
			@Override
			public NonNullList<ItemStack> getItemInput() {
				NonNullList<ItemStack> list2 = NonNullList.create();
				list2.addAll(list.subList(0, 0));
				return list2;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput() {
				NonNullList<ItemStack> list2 = NonNullList.create();
				list2.addAll(list.subList(1, 1));
				return list2;
			}
			private boolean hasConsumeRune()
			{
				for(IRuneType rune : children)
					if(rune instanceof CONSUME)
						return true;
				return false;
			}
			@Override
			public boolean canAddItem(ItemStack item)
			{
				return (hasConsumeRune() && canSmeltItem(item)) || canTransmuteItem(item);
			}

			private void transmuteItem(ItemStack item) {
				// TODO Auto-generated method stub
				
			}

			private boolean canTransmuteItem(ItemStack item)
			{
				return false;
			}

			private void smeltItem(ItemStack item)
			{
			}

			private boolean canSmeltItem(ItemStack item)
			{
				return false;
			}

			@Override
			public void addItem(ItemStack item)
			{
				list.set(0, item);
			}

			@Override
			public ItemStack removeItem(boolean input, int index, int amount)
			{
				return list.get(1).split(amount);
			}

			@Override
			public void doAction(RuneTileEntity entity)
			{
				if(this.hasConsumeRune())
				{
					
				}
			}

			@Override
			public CompoundNBT serializeNBT(){
				return null;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt)
			{
				
			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				// TODO Auto-generated method stub
				return rune instanceof CONSUME;
			}

			@Override
			public short getID() {
				// TODO Auto-generated method stub
				return 6;
			}

		}

		public static class CONSUME extends IRuneType implements IInventoryRune {

			private long burnTime = 0;
			private NonNullList<ItemStack> list = NonNullList.withSize(1, ItemStack.EMPTY);

			@Override
			public NonNullList<ItemStack> getItemInput() {
				return list;
			}

			@Override
			public NonNullList<ItemStack> getItemOutput() {
				return list;
			}

			@Override
			public boolean canAddItem(ItemStack item) {
				return ForgeHooks.getBurnTime(item) > 0 && list.get(0).isEmpty();
			}

			@Override
			public void addItem(ItemStack item) {
				if (this.getParent() instanceof TRANSMUTE) {
					burnTime += ForgeHooks.getBurnTime(item);
					if (item.hasContainerItem())
						list.set(0, item.getContainerItem());
				}
			}

			@Override
			public ItemStack removeItem(boolean input, int index, int amount) {
				return list.get(index).split(amount);
			}

			@Override
			public CompoundNBT serializeNBT() {
				CompoundNBT nbt = new CompoundNBT();
				nbt.putLong("burnTime", burnTime);
				nbt.put("OUTPUT", list.get(0).serializeNBT());
				return nbt;
			}

			@Override
			public void deserializeNBT(CompoundNBT nbt) {
				list.set(0, ItemStack.read(nbt.getCompound("OUTPUT")));
				burnTime = nbt.getLong("burnTime");
			}

			@Override
			public short getID() {
				return 7;
			}

			@Override
			public boolean canBeMyChild(IRuneType rune) {
				return false;
			}

		}
	}

}
