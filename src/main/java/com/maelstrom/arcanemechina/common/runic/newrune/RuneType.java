package com.maelstrom.arcanemechina.common.runic.newrune;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.maelstrom.arcanemechina.client.tesr.RenderPlane;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.IInventoryRune;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.IRuneRenderer2;
import com.maelstrom.arcanemechina.common.runic.newrune.rune_interfaces.ITicking;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.snowcone.common.RomanNumeral;
import com.maelstrom.snowcone.common.WorldUtilities;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayer;

public abstract class RuneType implements IStringSerializable, IRuneRenderer2 {
	static final RenderPlane plane = new RenderPlane();
	// static helpers and such

	private static List<Class<? extends RuneType>> hiddenList;
	static {
		hiddenList.add(CraftingContainerRune.class);
		hiddenList.add(VaribleRune.class);
		hiddenList.add(HoldingRune.class);
		hiddenList.add(ToggleRune.class);
		hiddenList.add(IORune.class);
	}

	private static short getID(RuneType runeType) {
		return (short) hiddenList.indexOf(runeType.getClass());
	}

	public static RuneType getFromID(short rune_id) {
		try {
			if (rune_id < hiddenList.size())
				return hiddenList.get(rune_id).newInstance();
		} catch (Exception e) {
		}
		return null;
	}

	// actual class here!
	private UUID uuid;

	public UUID getUUID() {
		return this.uuid;
	}

	private List<UUID> connections = new ArrayList<UUID>();

	public List<UUID> getListUUID() {
		return connections;
	}

	public abstract boolean canLink(RuneType rune);

	public boolean canLink(UUID rune) {
		return canLink(getParent().getLink(uuid));
	}

	public void addLink(RuneType rune) {
		this.addLink(rune.uuid);
	}

	public void addLink(UUID rune) {
		if (!connections.contains(rune) && canLink(rune)) {
			connections.add(rune);
		}
	}

	private RuneContainer parent;

	public RuneContainer getParent() {
		return parent;
	}

	public void setParent(RuneContainer runeContainer) {
		this.parent = runeContainer;
	}

	private Vec2f pos = Vec2f.ZERO;

	public void readFromNBT(CompoundNBT tag) {
		connections.clear();
		tag.getUniqueId("UUID");
		setPosition(tag.getCompound("pos"));
		ListNBT list = (ListNBT) tag.get("connections");
		for (int i = 0; i < list.size(); i++) {
			connections.add(((CompoundNBT) list.get(i)).getUniqueId("uuid"));
		}
		readNBT(tag.getCompound("data"));
	}

	public CompoundNBT writeToNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putShort("ID", getID(this));
		tag.putUniqueId("uuid", uuid);
		CompoundNBT position = new CompoundNBT();
		position.putFloat("X", pos.x);
		position.putFloat("Y", pos.y);
		tag.put("pos", position);
		tag.put("data", writeNBT());

		ListNBT list = new ListNBT();

		for (UUID connection : connections) {
			CompoundNBT con = new CompoundNBT();
			con.putUniqueId("uuid", connection);
			list.add(con);
		}
		tag.put("connections", list);
		tag.put("data", writeNBT());
		return tag;
	}

	private void setPosition(CompoundNBT tag) {
		float X = tag.getFloat("X");
		float Y = tag.getFloat("Y");
		setPosition(X, Y);
	}

	public Vec2f getPosition() {
		return pos;
	}

	public void setPosition(float x, float y) {
		this.pos = new Vec2f(x, y);
	}

	public abstract CompoundNBT writeNBT();

	public abstract void readNBT(CompoundNBT tag);

	// subclasses

	public static class CraftingContainerRune extends RuneType implements IInventoryRune {
		private static Item referenceItem = Items.PAPER;

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "crafting_container";
		}

		@Override
		public void render(float particks) {
			// donothing
		}

		@Override
		public CompoundNBT writeNBT() {
			CompoundNBT tag = new CompoundNBT();
			tag.put("item", this.getStackInSlot(0).getTag());
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag) {
			if (tag.get("item") != null)
				this.setInventorySlotContents(0, ItemStack.read(tag.getCompound("item")));
		}

		@Override
		public int getSizeInventory() {
			return 1;
		}

		boolean isDirty = false;

		@Override
		public boolean isDirty() {
			// TODO Auto-generated method stub
			return isDirty;
		}

		@Override
		public void markDirty() {
			isDirty = true;
		}

		private NonNullList<ItemStack> inventory = NonNullList.withSize(1, ItemStack.EMPTY);

		@Override
		public NonNullList<ItemStack> getAllItems() {
			return inventory;
		}

		public ICraftingRecipe getRecipe(World world) {
			return RecipeHelper.getRecipe(world, this.getStackInSlot(0));
		}

		@Override
		public boolean canAddItem(ItemStack item) {
			int value = 0;
			for (ItemStack i : this.getAllItems()) {
				if (value == 0)
					if (item.getItem() == referenceItem)
						if (canAddItem(item, i))
							return true;
				value++;
			}
			return false;
		}

		@Override
		public boolean canLink(RuneType rune) {
			return rune instanceof HoldingRune;
		}
	}

	public static class VaribleRune extends RuneType {
		static ResourceLocation varible = new ResourceLocation("arcanemechina:textures/runes/varible.png");
		private short value;

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "varible";
		}

		@Override
		public void render(float particks) {
			GlStateManager.pushMatrix();
			GlStateManager.translated(this.getPosition().x, 0, this.getPosition().y);
			GlStateManager.scaled(.5f, .5f, .5f);
			GlStateManager.pushMatrix();
			GlStateManager.pushMatrix();
			String text = this.getValueAsString();
			GlStateManager.translated(8f, 0, 7f);
			GlStateManager.rotated(90, 1, 0, 0);
			GlStateManager.rotated(180, 0, 0, 1);
			float center_x = IRuneRenderer2.getFontRenderer().getStringWidth(text) / 2f;
			float center_y = IRuneRenderer2.getFontRenderer().FONT_HEIGHT / 2f;
			float scaler = MathHelper.lerp(4f / (center_x * 2), 0f, 2f);
			GlStateManager.scaled(scaler, scaler, scaler);
			IRuneRenderer2.getFontRenderer().drawString(text, -center_x, -center_y, 0x00000000);
			GlStateManager.popMatrix();
			IRuneRenderer2.bindTexture(varible);
			plane.render();
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

		private String num_value;

		private String getValueAsString() {
			if (num_value == null)
				num_value = RomanNumeral.toRomanNumeral(getValue());
			return num_value;
		}

		@Override
		public CompoundNBT writeNBT() {
			CompoundNBT tag = new CompoundNBT();
			tag.putShort("value", value);
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag) {
			tag.getShort("value");
		}

		public short getValue() {
			return value;
		}

		public void setValue(short value) {
			this.value = value;
		}

		@Override
		public boolean canLink(RuneType rune) {
			return true;
		}

	}

	public static class HoldingRune extends RuneType implements IInventoryRune, ITicking {

		private int counter;
		NonNullList<ItemStack> item_list = NonNullList.withSize(9, ItemStack.EMPTY);
		static ResourceLocation hold = new ResourceLocation("arcanemechina:textures/runes/hold.png");

		private boolean isdirty = false;
		private int progress;
		protected static int currSubId = -1;
		private int subID;

		public HoldingRune() {
			subID = currSubId--;
		}

		@Override
		public int getSizeInventory() {
			return 19;
		}

		@Override
		public void markDirty() {
			isdirty = true;
		}

		@Override
		public boolean isDirty() {
			return isdirty;
		}

		@Override
		public String getName() {
			return "hold";
		}

		@Override
		public void render(float particks) {
			GlStateManager.pushMatrix();
			if (item_list.get(0) != ItemStack.EMPTY) {
				GlStateManager.pushMatrix();
				IRuneRenderer2.renderItem(item_list.get(0));
				GlStateManager.popMatrix();
			}
			GlStateManager.pushMatrix();
			IRuneRenderer2.bindTexture(hold);
			plane.render();
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

		@Override
		public NonNullList<ItemStack> getAllItems() {
			return item_list;
		}

		@Override
		public CompoundNBT writeNBT() {
			CompoundNBT tag = new CompoundNBT();
			tag.putInt("counter", counter);
			ItemStackHelper.saveAllItems(tag, item_list, true);
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag) {
			counter = tag.getInt("counter");
			if (tag.contains("Items")) {
				ItemStackHelper.loadAllItems(tag, item_list);
			}
		}

		public boolean hasItem() {
			return !this.getStackInSlot(0).isEmpty();
		}

		public boolean hasToggle() {
			if (this.getListUUID().get(1) != null)
				return this.getParent().getLink(getListUUID().get(0)) != null;
			return false;
		}

		private ToggleRune getToggle() {
			if (hasToggle())
				return (ToggleRune) this.getParent().getLink(getListUUID().get(0));
			return null;
		}

		public boolean hasCraft() {
			if (this.getListUUID().get(1) != null)
				return this.getParent().getLink(getListUUID().get(1)) != null;
			return false;
		}

		private CraftingContainerRune getCraft() {
			if (hasToggle())
				return (CraftingContainerRune) this.getParent().getLink(getListUUID().get(1));
			return null;
		}

		public void resetInteractions(RuneTileEntity entity) {
			ServerWorld world = (ServerWorld) entity.getWorld();
			BlockPos pos = entity.getPos().offset(entity.offset());
			counter = 0;
			world.sendBlockBreakProgress(subID, pos, -1);
		}

		private CraftingContainerRune craftReference;

		@Override
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity) {
			if (hasItem() && hasToggle() && world instanceof ServerWorld) {
				if (this.getStackInSlot(0).getItem() == Items.CRAFTING_TABLE) // temp disabled due to not being tested!
				{
					// do crafting
					if (craftReference == null) {
						craftReference = this.getCraft();
					}
					if (craftReference != null) {
						boolean canCraft = true;
						ICraftingRecipe recipe = craftReference.getRecipe(entity.getWorld());
						for (ItemStack i : getAllItems().subList(1, 10)) {
							if (i != ItemStack.EMPTY && (i.getCount() <= 1))
								canCraft = false;
						}
						if (canCraft && this.canAddItem(recipe.getRecipeOutput())) {
							for (ItemStack i : getAllItems().subList(1, 10)) {
								if (i.hasContainerItem())
									for (ItemStack i2 : getAllItems().subList(10, 19)) {
										canAddItem(i.getContainerItem());
									}
								if (i != ItemStack.EMPTY)
									i.shrink(1);
							}
							ItemStack output = recipe.getRecipeOutput();
							addItem(output);
						}
					}

				} else {
					BlockPos pos = entity.getPos().offset(entity.offset());
					BlockState state = world.getBlockState(pos);
					FakePlayer fakePlayer = WorldUtilities.getFakePlayer((ServerWorld) world);
					if (fakePlayer.getHeldItemMainhand() != this.getStackInSlot(0))
						fakePlayer.inventory.mainInventory.set(0, this.getStackInSlot(0));
					if (!world.isAirBlock(pos) && world.canMineBlockBody(fakePlayer, pos)) {
						if (getToggle().getState()) {
							// find a more accurate way to do this!
							progress = (int) ((state.getBlockHardness(world, pos)
									/ (10f - this.getStackInSlot(0).getDestroySpeed(state))) * counter);
							world.sendBlockBreakProgress(subID, pos, progress - 1);
							if (progress >= 10) {
								List<ItemStack> items = WorldUtilities.BreakBlock((ServerWorld) world, pos, fakePlayer);
								for (ItemStack item2 : items) {
									if (item2 != null && !item2.isEmpty()) {
										boolean found = false;
										if (this.getStackInSlot(0).canHarvestBlock(state))
											for (int i = 0; i < this.getSizeInventory() - 1; i++) {
												if (this.getStackInSlot(i + 1).isEmpty()) {
													this.setInventorySlotContents(i + 1, item2);
													found = true;
												}
											}
										if (found)
											world.addEntity(
													new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), item2));
										if (this.getStackInSlot(0).isDamageable()) {
											if (this.getStackInSlot(0).getDamage() >= this.getStackInSlot(0)
													.getMaxDamage())
												this.setInventorySlotContents(0, ItemStack.EMPTY);
											else
												this.getStackInSlot(0).attemptDamageItem(1,
														entity.getWorld().getRandom(), fakePlayer);
											world.notifyBlockUpdate(entity.getPos(), entity.getBlockState(),
													entity.getBlockState(), 2);
										}
									}
								}
								world.sendBlockBreakProgress(subID, pos, -1);
								counter = 0;
							} else
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
		public boolean canAddItem(ItemStack item) {
			int value = 0;
			for (ItemStack i : this.getAllItems()) {
				if (value == 0)
					if (item.getItem() instanceof PickaxeItem || item.getItem() == Items.CRAFTING_TABLE)
						if (canAddItem(item, i))
							return true;
				if (canAddItem(item, i))
					return true;
				value++;
			}
			return false;
		}

		@Override
		public boolean canLink(RuneType rune) {
			return rune instanceof ToggleRune || rune instanceof IORune;
		}

	}

	public static class ToggleRune extends RuneType implements ITicking {

		private boolean state = false;
		private int counter = 0;

		static ResourceLocation toggle_off = new ResourceLocation("arcanemechina:textures/runes/toggle.png");
		static ResourceLocation toggle_on = new ResourceLocation("arcanemechina:textures/runes/toggle_active.png");

		@Override
		public String getName() {
			return "toggle";
		}

		@Override
		public void render(float particks) {

			GlStateManager.pushMatrix();
			if (this.state)
				IRuneRenderer2.bindTexture(toggle_on);
			else
				IRuneRenderer2.bindTexture(toggle_off);
			plane.render();
			GlStateManager.popMatrix();
		}

		public boolean hasOnVarible() {
			return getOnRune() != null;
		}

		public boolean hasOffVarible() {
			return getOffRune() != null;
		}

		private VaribleRune getOnRune() {
			if (this.getListUUID().get(1) != null)
				return (VaribleRune) getParent().getLink(this.getListUUID().get(0));
			return null;
		}

		private VaribleRune getOffRune() {
			if (this.getListUUID().get(1) != null)
				return (VaribleRune) getParent().getLink(this.getListUUID().get(1));
			return null;
		}

		public void toggleState() {
			counter = 0;
			state = !state;
		}

		@Override
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity) {
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
		public CompoundNBT writeNBT() {
			CompoundNBT tag = new CompoundNBT();
			tag.putBoolean("STATE", state);
			tag.putInt("COUNTER", counter);
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag) {
			state = tag.getBoolean("STATE");
			counter = tag.getInt("COUNTER");
		}

		public boolean getState() {
			return state;
		}

		public int getCounter() {
			return counter;
		}

		@Override
		public boolean canLink(RuneType rune) {
			return true;
		}

	}

	public static class IORune extends RuneType implements ITicking {

		public Direction dir = Direction.DOWN;
		private int maxPull = 1;
		private boolean input = true;

		public void setDirection(Direction newDir) {
			this.dir = newDir;
		}

		public boolean hasInventoryRune() {
			if (this.getListUUID().get(0) != null)
				return this.getParent().getLink(getListUUID().get(0)) != null;
			return false;
		}

		private IInventoryRune getInventoryRune() {
			if (hasInventoryRune())
				return (IInventoryRune) this.getParent().getLink(getListUUID().get(0));
			return null;
		}

		public boolean hasInterRuneConnection() {
			if (this.getListUUID().get(1) != null)
				return this.getParent().getLink(getListUUID().get(1)) != null;
			return false;
		}

		private IInventoryRune getInterRuneConnection() {
			if (hasInventoryRune())
				return (IInventoryRune) this.getParent().getLink(getListUUID().get(1));
			return null;
		}

		@Override
		public String getName() {
			return "io_into_rune";
		}

		@Override
		public void render(float particks) {
			// TODO Auto-generated method stub

		}

		@Override
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity) {
			if (this.hasInventoryRune())
				//VOODOO MAGIC THAT MIGHT NOT WORK!!
				if(this.hasInterRuneConnection())
				{
					transfer(this.getInterRuneConnection());
				}
				else if (world.getTileEntity(blockPos.offset(dir)) instanceof IInventory) {
					TileEntity tile = world.getTileEntity(blockPos.offset(dir));
					transfer((IInventory)tile);
				}
		}
		
		private void transfer(IInventory inventory)
		{
			if (input) {
				int index = 0;
				for (int i = 0; i < inventory.getSizeInventory(); i++)
					if (!inventory.getStackInSlot(i).isEmpty()) {
						index = i;
						break;
					}
				if (getInventoryRune().canAddItem(inventory.getStackInSlot(index).copy().split(maxPull))) {
					ItemStack item = inventory.getStackInSlot(index).split(maxPull);
					getInventoryRune().addItem(item);
				}
			} else {
				int index_rune = 0;
				int index_inventory = 0;
				for (int i = 0; i < getInventoryRune().getSizeInventory() - 1; i++)
					if (!getInventoryRune().getStackInSlot(i + 1).isEmpty()) {
						index_rune = i;
						break;
					}
				for (int i = 0; i < inventory.getSizeInventory(); i++)
					if (getInventoryRune().canAddItem(getInventoryRune().getStackInSlot(index_rune),
							inventory.getStackInSlot(i))) {
						index_inventory = i;
						break;
					}
				if (getInventoryRune().canAddItem(getInventoryRune().getStackInSlot(index_rune),
						inventory.getStackInSlot(index_inventory))) {
					ItemStack item = getInventoryRune().getStackInSlot(index_rune).split(maxPull);
					inventory.setInventorySlotContents(index_inventory, item);
				}
			}
		}

		@Override
		public CompoundNBT writeNBT() {
			CompoundNBT tag = new CompoundNBT();
			tag.putInt("direction", dir.ordinal());
			tag.putInt("maxPull", maxPull);
			tag.putBoolean("input", input);
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag) {
			try {
				dir = Direction.values()[tag.getInt("direction")];
			} finally {
			}
			maxPull = tag.getInt("maxPull");
			input = tag.getBoolean("input");
		}

		@Override
		public boolean canLink(RuneType rune) {
			return rune instanceof IInventoryRune;
		}

	}

}
