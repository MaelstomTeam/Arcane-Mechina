package com.maelstrom.arcanemechina.common.runic;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.maelstrom.arcanemechina.client.tesr.RenderPlane;
import com.maelstrom.arcanemechina.common.RecipeHelper;
import com.maelstrom.arcanemechina.common.blocks.RuneBlock;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.IInventoryRune;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.IRuneRenderer;
import com.maelstrom.arcanemechina.common.runic.rune_interfaces.ITicking;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.snowcone.common.RomanNumeral;
import com.maelstrom.snowcone.common.WorldUtilities;
import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
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

public abstract class RuneType implements IStringSerializable, IRuneRenderer {
	static final RenderPlane plane = new RenderPlane();
	// static helpers and such

	private static List<Class<? extends RuneType>> indexList = new ArrayList<Class<? extends RuneType>>();
	static {
		indexList.add(CraftingContainerRune.class);
		indexList.add(VaribleRune.class);
		indexList.add(HoldingRune.class);
		indexList.add(ToggleRune.class);
		indexList.add(IORune.class);
		indexList.add(RedstoneIORune.class);
		indexList.add(SubRuneContainer.class);
	}

	private static short getID(RuneType runeType) {
		return (short) indexList.indexOf(runeType.getClass());
	}

	public short getID() {
		return getID(this);
	}

	public static RuneType getFromID(short rune_id) {
		try {
			if (rune_id < indexList.size())
				return indexList.get(rune_id).newInstance();
		} catch (Exception e) {
		}
		return null;
	}
	
	// actual class here!
	private int uuid = -1;
	
	public RuneType()
	{
	}

	public int getUUID() {
		if(uuid == -1)
		{
			uuid = this.parent.currIDMax;
			this.parent.currIDMax++;
		}
		return this.uuid;
	}

	private List<Integer> connections = new ArrayList<Integer>();

	public List<Integer> getListUUID() {
		return connections;
	}

	public abstract boolean canLink(RuneType rune);

	public boolean canLink(UUID rune) {
		if(getParent().getLink(uuid) == null)
			return false;
		return canLink(getParent().getLink(uuid));
	}

	public void addLink(RuneType rune) {
		if (!connections.contains(rune.getUUID()) && canLink(rune)) {
			connections.add(rune.getUUID());
		}
	}

	public void addLink(int rune) {
		if (!connections.contains(rune) && canLink(parent.getLink(rune))) {
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

	private float scale = 0.33f;

	public void readFromNBT(CompoundNBT tag) {
		connections.clear();
		uuid = tag.getInt("uuid");
		scale = tag.getFloat("SCALE");
		setPosition(tag.getCompound("pos"));
		ListNBT list = (ListNBT) tag.get("connections");
		for (int i = 0; i < list.size(); i++) {
			connections.add(((CompoundNBT) list.get(i)).getInt("uuid"));
		}
		readNBT(tag.getCompound("data"));
	}

	public void setScale(float scale)
	{
		this.scale = scale;
	}
	public float getScale()
	{
		return scale;
	}

	public CompoundNBT writeToNBT() {
		CompoundNBT tag = new CompoundNBT();
		tag.putShort("RuneID", getID(this));
		tag.putInt("uuid", uuid);
		tag.putFloat("SCALE", scale);
		CompoundNBT position = new CompoundNBT();
		position.putFloat("X", pos.x);
		position.putFloat("Y", pos.y);
		tag.put("pos", position);
		tag.put("data", writeNBT());

		ListNBT list = new ListNBT();

		for (int connection : connections) {
			CompoundNBT con = new CompoundNBT();
			con.putInt("uuid", connection);
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

		static ResourceLocation craft = new ResourceLocation("arcanemechina:textures/runes/64px.png");
		static ResourceLocation hold = new ResourceLocation("arcanemechina:textures/runes/hold.png");
		

		public float getScale()
		{
			return 0.5f;
		}
		
		@Override
		public void render(float particks) {
			GlStateManager.translated(this.getPosition().x-4.25, 0, this.getPosition().y-4.25);
			GlStateManager.pushMatrix();
			GlStateManager.pushMatrix();
			if(RecipeHelper.isCraftingItem(this.getStackInSlot(0)))
			{
				ItemStack[][] list = RecipeHelper.getFromNBT(this.getStackInSlot(0).getTag());
				int x = 0;
				int y = 0;
				for(ItemStack[] list1 : list)
				{
					for(ItemStack item : list1)
					{
						if (item != ItemStack.EMPTY) {
							GlStateManager.pushMatrix();
							GlStateManager.scaled(0.1, 0.1, 0.1);
							GlStateManager.translated(16 * x+19, 0, 16 * y+18);
							GlStateManager.enableRescaleNormal();
							IRuneRenderer.renderItem(item);
							IRuneRenderer.bindTexture(hold);
							plane.render();
							GlStateManager.popMatrix();
						}
						x++;
					}
					x=0;
					y++;
				}
			}
			GlStateManager.popMatrix();
			GlStateManager.pushMatrix();
				GlStateManager.scaled(getScale(), getScale(), getScale());
				IRuneRenderer.bindTexture(craft);
				plane.render();
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
		}

		@Override
		public CompoundNBT writeNBT() {
			CompoundNBT tag = new CompoundNBT();
			tag.put("item", this.getStackInSlot(0).write(new CompoundNBT()));
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
		private short value = 0;

		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "varible";
		}

		@Override
		public void render(float particks) {
			float v2 = 2.75f;
			GlStateManager.translated(this.getPosition().x-v2, 0, this.getPosition().y-v2);
			GlStateManager.scaled(getScale(), getScale(), getScale());
			GlStateManager.pushMatrix();
				GlStateManager.pushMatrix();
					String text = this.getValueAsString();
					GlStateManager.translated(8f, 0, 7.25f);
					GlStateManager.rotated(90, 1, 0, 0);
					GlStateManager.rotated(90, 0, 0, 1);
					float center_x = getStringWidth(IRuneRenderer.getFontRenderer(),text) / 2f;
					float center_y = IRuneRenderer.getFontRenderer().FONT_HEIGHT / 2f;
					float scaler_max = (this.getParent().isInternal() ? 0.5f : 0.8f);
					float scaler = MathHelper.lerp(3f / (center_x), 0.1f, scaler_max);
					GlStateManager.scaled(scaler, scaler, scaler);
					IRuneRenderer.getFontRenderer().drawString(text, -center_x, -center_y, 0x00000000);
				GlStateManager.popMatrix();
			GlStateManager.translated(0, 0, 15.5);
			GlStateManager.rotated(90, 0, 1, 0);
			IRuneRenderer.bindTexture(varible);
			plane.render();
			GlStateManager.popMatrix();
		}
		
		private static float getStringWidth(FontRenderer font, String text)
		{
			float size = 0f;
			for(int letter = 0; letter < text.length(); letter++)
			{
				size += font.getCharWidth(text.charAt(letter));
			}
			return size;
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
			value = tag.getShort("value");
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
		NonNullList<ItemStack> item_list = NonNullList.withSize(19, ItemStack.EMPTY);
		static ResourceLocation hold = new ResourceLocation("arcanemechina:textures/runes/hold.png");

		private boolean isdirty = false;
		private int progress;
		private int subID = (int) (Math.random() * 10000);

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
			float value = 2.75f;
			GlStateManager.translated(this.getPosition().x-value, 0, this.getPosition().y-value);
			GlStateManager.scaled(getScale(), getScale(), getScale());
			GlStateManager.pushMatrix();
				if (item_list.get(0) != ItemStack.EMPTY) {
					GlStateManager.pushMatrix();
					GlStateManager.translated(.3, 0, .3);
					IRuneRenderer.renderItem(item_list.get(0));
					GlStateManager.popMatrix();
				}
			GlStateManager.pushMatrix();
				IRuneRenderer.bindTexture(hold);
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
			if (this.getListUUID().size() >= 1 && this.getListUUID().get(0) != null)
				return this.getParent().getLink(getListUUID().get(0)) != null;
			return false;
		}

		private ToggleRune getToggle() {
			if (hasToggle())
				return (ToggleRune) this.getParent().getLink(getListUUID().get(0));
			return null;
		}

		public boolean hasCraft() {
			if (this.getListUUID().size() >= 2 && this.getListUUID().get(1) != null)
				return this.getParent().getLink(getListUUID().get(1)) != null;
			return false;
		}

		private CraftingContainerRune getCraft() {
			if (hasToggle() && hasCraft())
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
			if(entity != null)
			if (hasItem() && hasToggle() && world instanceof ServerWorld) {
				if (this.getStackInSlot(0).getItem() == Items.CRAFTING_TABLE)
				{
					// do crafting
					if (craftReference == null) {
						craftReference = this.getCraft();
					}
					if (craftReference != null) {
						boolean canCraft = true;
						canCraft = false;
						ICraftingRecipe recipe = craftReference.getRecipe(entity.getWorld());
						for (ItemStack i : getAllItems().subList(1, 10)) {
							if (i != ItemStack.EMPTY && (i.getCount() > 1))
								canCraft = false;
						}
						try{

							//recipe.matches(new CraftingInventory(null, counter, counter), worldIn);
						}finally {}
						if (canCraft && this.canAddItem(recipe.getRecipeOutput())) {
							int counter = 1;
							for (ItemStack i : getAllItems().subList(1, 10)) {
								if (i.hasContainerItem())
								{
									for (ItemStack i2 : getAllItems().subList(10, 19))
									{
										if(canAddItem(i.getContainerItem(),i2))
										{
											this.addItem(i2);
											break;
										}
									}
								}
								if (i != ItemStack.EMPTY)
									i.shrink(1);
								if(i.isEmpty())
									this.setInventorySlotContents(counter, ItemStack.EMPTY);
								counter++;
							}
							ItemStack output = recipe.getRecipeOutput();
							for(int i = 0; i < this.getSizeInventory(); i++)
							{
								if(i != 0)
									if(this.canAddItem(output,this.getStackInSlot(i)))
									{
										addItem(output,i);
										break;
									}
							}
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
							progress = (int) ((state.getBlockHardness(world, pos) / (10f - this.getStackInSlot(0).getDestroySpeed(state))) * counter);
							world.sendBlockBreakProgress(subID, pos, progress - 1);
							if (progress >= 10) {
								List<ItemStack> block_drops = WorldUtilities.BreakBlock((ServerWorld) world, pos, fakePlayer);
								for (ItemStack current_item : block_drops) {
									if (current_item != null && !current_item.isEmpty()) {
										boolean found = false;
										if (this.getStackInSlot(0).canHarvestBlock(state))
											for (int i = 1; i < this.getSizeInventory(); i++) {
												if (this.canAddItem(current_item, this.getStackInSlot(i))) {
													this.addItem(current_item,i);
													found = true;
													break;
												}
											}
										if(!found)
											world.addEntity(new ItemEntity(world, pos.getX(), pos.getY(), pos.getZ(), current_item));
										if (this.getStackInSlot(0).isDamageable()) {
											if (this.getStackInSlot(0).getDamage() >= this.getStackInSlot(0).getMaxDamage())
												this.setInventorySlotContents(0, ItemStack.EMPTY);
											else
												this.getStackInSlot(0).attemptDamageItem(1,entity.getWorld().getRandom(), fakePlayer);
											world.notifyBlockUpdate(entity.getPos(), entity.getBlockState(), entity.getBlockState(), 2);
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
			return rune instanceof ToggleRune || rune instanceof IORune || rune instanceof CraftingContainerRune;
		}

		@Override
		public void pretick(World world, BlockPos blockPos, RuneTileEntity entity){}

		@Override
		public void posttick(World world, BlockPos blockPos, RuneTileEntity entity){}

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
			float value = 2.75f;
			GlStateManager.translated(this.getPosition().x-value, 0, this.getPosition().y-value);
			GlStateManager.scaled(getScale(), getScale(), getScale());
			GlStateManager.pushMatrix();
			if (this.state)
				IRuneRenderer.bindTexture(toggle_on);
			else
				IRuneRenderer.bindTexture(toggle_off);
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
			if (this.getListUUID().get(0) != null)
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
		public void pretick(World world, BlockPos blockPos, RuneTileEntity entity) {
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
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity){}

		@Override
		public void posttick(World world, BlockPos blockPos, RuneTileEntity entity){}

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
			if (this.getListUUID().size() >= 1 &&this.getListUUID().get(0) != null)
				return this.getParent().getLink(getListUUID().get(0)) != null;
			return false;
		}

		private IInventoryRune getInventoryRune() {
			if (hasInventoryRune())
				return (IInventoryRune) this.getParent().getLink(getListUUID().get(0));
			return null;
		}

		public boolean hasInterRuneConnection() {
			if (this.getListUUID().size() >= 2 && this.getListUUID().get(1) != null)
				return this.getParent().getLink(getListUUID().get(1)) != null;
			return false;
		}

		private IInventoryRune getInterRuneConnection() {
			if (hasInventoryRune() && hasInterRuneConnection())
				return (IInventoryRune) this.getParent().getLink(getListUUID().get(1));
			return null;
		}

		@Override
		public String getName() {
			return "io";
		}

		static ResourceLocation insert_to_rune = new ResourceLocation("arcanemechina:textures/runes/insert.png");
		static ResourceLocation extract_from_rune = new ResourceLocation("arcanemechina:textures/runes/extract.png");
        
		@Override
		public float getScale()
		{
			return .125f;
		}
		@Override
		public void render(float particks) {
			double x = this.dir.getXOffset();
			double z = this.dir.getZOffset();
			x = 8 * x+7;
			z = 8 * z+8.5;
			GlStateManager.translated(x, MathHelper.lerp(pingpong(Minecraft.getInstance().world.getGameTime() + particks, 20) / 20f, 0.1, 1.1), z);
			if(this.dir.getZOffset() != 0)
			{
				GlStateManager.rotated(90, 0, 1, 0);
			}
			GlStateManager.pushMatrix();
			GlStateManager.scaled(getScale(), getScale(), getScale());
			if (input)
				IRuneRenderer.bindTexture(insert_to_rune);
			else
				IRuneRenderer.bindTexture(extract_from_rune);
			plane.render();
			GlStateManager.popMatrix();

		}

		private float pingpong(float value, float maxValue)
		{
			boolean reverse = false;
			float newValue = value;
			while(newValue > maxValue)
			{
				newValue -= maxValue;
				reverse = !reverse;
			}
			if(reverse)
				return maxValue-newValue;
			return newValue;
		}

		@Override
		public void pretick(World world, BlockPos blockPos, RuneTileEntity entity){}

		@Override
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity){}

		@Override
		public void posttick(World world, BlockPos blockPos, RuneTileEntity entity) {
			if(entity != null)
			if (this.hasInventoryRune())
			{
				if(this.getParent().isInternal() && !this.hasInterRuneConnection())
				{
					IInventoryRune rune = (IInventoryRune) this.getParent().getParent().getInventory(this.input);
					if(rune != null)
						transfer(rune);
				}
				else {
					//VOODOO MAGIC THAT MIGHT NOT WORK!!
					if(this.hasInterRuneConnection())
					{
						transfer(this.getInterRuneConnection());
					}
					else if (world.getTileEntity(blockPos.offset(dir)) instanceof IInventory) {
						transfer((IInventory)world.getTileEntity(blockPos.offset(dir)));
					}
					else
					{
						if (!input) {
							for (int i = 0; i < getInventoryRune().getSizeInventory(); i++)
							{
	
								ItemStack item = null;
								if(getInventoryRune() instanceof HoldingRune)
								{
									if(i < getInventoryRune().getSizeInventory() - 1)
										if (!getInventoryRune().getStackInSlot(i + 1).isEmpty())
											item = getInventoryRune().getStackInSlot(i + 1).split(maxPull);
								}
								//don't export anything from the crafting rune as it is required to get a crafting recipe
								//might remove as one might want to use this to cycle recipes in the main crafter
								else if(getInventoryRune() instanceof CraftingContainerRune)
									;
								else
								{
									if (!getInventoryRune().getStackInSlot(i).isEmpty()) {
										item = getInventoryRune().getStackInSlot(i).split(maxPull);
									}
								}
								if(item != null && item != ItemStack.EMPTY)
								{
									ItemEntity item_entity = new ItemEntity(world,blockPos.getX(), blockPos.getY()+1, blockPos.getZ(), item);
									world.addEntity(item_entity);
									break;
								}
								
							}
						}
					}
				}
			}
		}
		
		private void transfer(IInventory inventory)
		{
			if (input) {
				int index = -1;
				if(getInventoryRune() instanceof CraftingContainerRune)
				{
					if(getInventoryRune().getStackInSlot(0).isEmpty())
						for(int i = 0; i < inventory.getSizeInventory(); i++)
							if(RecipeHelper.isCraftingItem(inventory.getStackInSlot(i)))
							{
								index = i;
								break;
							}
				}
				else if(getInventoryRune() instanceof HoldingRune)
				{
					if(getInventoryRune().getStackInSlot(0).isEmpty())
					{
						for(int i = 0; i < inventory.getSizeInventory(); i++)
							if(getInventoryRune().canAddItem(inventory.getStackInSlot(i),getInventoryRune().getStackInSlot(0)) && inventory.getStackInSlot(i).getItem() instanceof ToolItem)
							{
								index = i;
								break;
							}
					}
				}
				else {
					for (int i = 0; i < inventory.getSizeInventory(); i++)
						if (!inventory.getStackInSlot(i).isEmpty()) {
							index = i;
							break;
						}
				}
				if(index == -1)
					return;
				if (getInventoryRune().canAddItem(inventory.getStackInSlot(index).copy().split(maxPull))) {
					ItemStack item = inventory.getStackInSlot(index).split(maxPull);
					if(inventory.getStackInSlot(index).getCount() <= 0)
						inventory.setInventorySlotContents(index, ItemStack.EMPTY);
					getInventoryRune().addItem(item);
				}
			} else {
				//don't export anything from the crafting rune as it is required to get a crafting recipe
				//might remove as one might want to use this to cycle recipes in the main crafter
				if(getInventoryRune() instanceof CraftingContainerRune)
					return;
				int index_rune = -1;
				int index_inventory = -1;
				if(getInventoryRune() instanceof HoldingRune)
				{
					for (int i = 1; i < getInventoryRune().getSizeInventory(); i++)
						if (!getInventoryRune().getStackInSlot(i).isEmpty()) {
							index_rune = i;
							break;
						}
				}
				else
				{
					for (int i = 0; i < getInventoryRune().getSizeInventory(); i++)
						if (!getInventoryRune().getStackInSlot(i).isEmpty()) {
							index_rune = i;
							break;
						}
				}
				if(index_rune == -1)
					return;
					for (int i = 0; i < inventory.getSizeInventory(); i++)
					{
						if (getInventoryRune().canAddItem(getInventoryRune().getStackInSlot(index_rune), inventory.getStackInSlot(i))) {
							index_inventory = i;
							break;
						}
					}
				if(index_inventory == -1)
					return;
				if(getInventoryRune().getStackInSlot(index_rune).isEmpty() && inventory.getStackInSlot(index_inventory).isEmpty())
					return;
				if (getInventoryRune().canAddItem(getInventoryRune().getStackInSlot(index_rune), inventory.getStackInSlot(index_inventory))) {
					ItemStack item = getInventoryRune().getStackInSlot(index_rune).split(maxPull);
					if(inventory.getStackInSlot(index_inventory) != ItemStack.EMPTY)
						inventory.getStackInSlot(index_inventory).grow(item.getCount());
					else
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
			dir = Direction.values()[tag.getInt("direction")];
			maxPull = tag.getInt("maxPull");
			input = tag.getBoolean("input");
		}

		@Override
		public boolean canLink(RuneType rune) {
			return rune instanceof IInventoryRune;
		}

		public void setInput(boolean b)
		{
			input = b;
			
		}

	}
	
	public static class RedstoneIORune extends RuneType implements ITicking
	{
		private boolean isInput = false;
		private int redstone_power = 0;
		private Direction side = Direction.NORTH;
		@Override
		public String getName()
		{
			return "redstone_io";
		}

		public void setInput(boolean isInput)
		{
			this.isInput = isInput;
		}

		public void setPower(int redstone_power)
		{
			this.redstone_power = redstone_power;
		}

		@Override
		public void render(float particks)
		{
			
		}

		@Override
		public void pretick(World world, BlockPos blockPos, RuneTileEntity entity){}
		@Override
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity)
		{
			if(entity != null)
			{
				if(!isInput == world.getBlockState(blockPos).get(RuneBlock.canPowerWeak))
				{
					BlockState state = world.getBlockState(blockPos);
					BlockState newState = state.with(RuneBlock.canPowerWeak, isInput);
					world.setBlockState(blockPos, newState, 2);
				}
				if(isInput)
				{
					this.redstone_power = world.getRedstonePower(blockPos, side);
				}
			}
		}
		@Override
		public void posttick(World world, BlockPos blockPos, RuneTileEntity entity){}

		@Override
		public boolean canLink(RuneType rune)
		{
			return false;
		}

		@Override
		public CompoundNBT writeNBT()
		{
			CompoundNBT tag = new CompoundNBT();
			tag.putInt("side", side.ordinal());
			tag.putInt("redstone_power", redstone_power);
			tag.putBoolean("input", isInput);
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag)
		{
			side = Direction.values()[tag.getInt("side")];
			redstone_power = tag.getInt("redstone_power");
			isInput = tag.getBoolean("input");
			
		}

		public int getPower()
		{
			return this.redstone_power;
		}

		public Direction getSide()
		{
			return this.side ;
		}

		public boolean canOutputRedstone()
		{
			return !this.isInput;
		}
		
	}

	public static class SubRuneContainer extends RuneType implements ITicking
	{
		
		RuneContainer internalRune = new RuneContainer();
		NonNullList<ItemStack> item_list = NonNullList.withSize(19, ItemStack.EMPTY);
		public SubRuneContainer()
		{
			internalRune.setInternal(true);
			internalRune.setParent(this);
		}

		public RuneContainer getInternalRune()
		{
			return internalRune;
		}

		public void setInternalRune(RuneContainer internalRune)
		{
			this.internalRune = internalRune;
			internalRune.setInternal(true);
			internalRune.setParent(this);
		}

		public IInventoryRune getInventory(boolean input)
		{
			if(input)
			{
				if(hasInput())
					return (IInventoryRune) this.getParent().getLink(getListUUID().get(0));
			}
			else
			{
				if(hasOutput())
					return getOutput();
			}
			return null;
		}

		public boolean hasInput() {
			if (this.getListUUID().size() >= 1 &&this.getListUUID().get(0) != null)
				return this.getParent().getLink(getListUUID().get(0)) != null;
			return false;
		}

		public boolean hasOutput() {
			if (this.getListUUID().size() >= 2 && this.getListUUID().get(1) != null)
				return this.getParent().getLink(getListUUID().get(1)) != null;
			else if(this.getListUUID().size() >=1 && this.getListUUID().get(0)!= null)
				return this.getParent().getLink(getListUUID().get(0)) != null;
			return false;
		}
		public IInventoryRune getOutput()
		{
			if (this.getListUUID().size() >= 2 && this.getListUUID().get(1) != null)
				return (IInventoryRune) this.getParent().getLink(getListUUID().get(1));
			else if(this.getListUUID().size() >=1 && this.getListUUID().get(0)!= null)
				return (IInventoryRune) this.getParent().getLink(getListUUID().get(0));
			return null;
		}

		@Override
		public String getName()
		{
			return "sub_rune";
		}

		@Override
		public void render(float particks)
		{
			float value = 2.75f;
			GlStateManager.translated(this.getPosition().x-value, 0, this.getPosition().y-value);
			GlStateManager.scaled(getScale(), getScale(), getScale());
			GlStateManager.pushMatrix();
			internalRune.render(particks);
			GlStateManager.popMatrix();
		}
		
		@Override
		public void pretick(World world, BlockPos blockPos, RuneTileEntity entity)
		{
			internalRune.tick(entity,0);
		}

		@Override
		public void tick(World world, BlockPos blockPos, RuneTileEntity entity)
		{
			internalRune.tick(entity,1);
		}

		@Override
		public void posttick(World world, BlockPos blockPos, RuneTileEntity entity)
		{
			internalRune.tick(entity,2);
		}

		@Override
		public boolean canLink(RuneType rune)
		{
			return rune instanceof IInventoryRune;
		}

		@Override
		public CompoundNBT writeNBT()
		{
			CompoundNBT tag = new CompoundNBT();
			tag.put("rune",internalRune.writeNBT());
			return tag;
		}

		@Override
		public void readNBT(CompoundNBT tag)
		{
			if(tag.get("rune") != null)
			{
				internalRune.readNBT(tag.getCompound("rune"));
			}
		}
		
	}
	
}
