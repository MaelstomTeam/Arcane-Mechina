package com.maelstrom.snowcone.item.sonic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.maelstrom.snowcone.SC_Registry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SonicInventory implements IInventory, /* IEnergyStorage, */ ICapabilityProvider {

	// TODO move to UUID system, change so nbt can be marked as dirty and saved
	// TODO clear list at server join or world start
	public static Map<String, SonicInventory> list = new HashMap<String, SonicInventory>();

	public String UUID;
	NBTTagCompound inventory;
	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	String username;
	int selected = 0;
	int crystalColor = -1;
	int handleColor = -1;
	// EnergyStorage energy = new EnergyStorage(Integer.MAX_VALUE,
	// Integer.MAX_VALUE/100,Integer.MAX_VALUE/100,0);

	public SonicInventory(ItemStack is) {
		NBTTagCompound temp;
		if (!is.hasTagCompound()) {
			is.setTagInfo("ItemInventory", new NBTTagCompound());
		}
		if (is.getTagCompound().getTag("ItemInventory") != null)
			temp = is.getTagCompound().getCompoundTag("ItemInventory");
		else {
			temp = new NBTTagCompound();
		}
		inventory = temp;
		readData();
		if (!list.containsKey(UUID)) {
			list.put(UUID, this);
		}
		is.setTagInfo("ItemInventory", inventory);
	}

	public void readData() {
		if (inventory == null)
			inventory = new NBTTagCompound();

		/*
		 * if(inventory.getTag("FE") != null) { energy = new
		 * EnergyStorage(Integer.MAX_VALUE,
		 * Integer.MAX_VALUE,Integer.MAX_VALUE,inventory.getInteger("FE")); }
		 */

		if (inventory.getTag("UUID") == null) {
			inventory.setString("UUID", java.util.UUID.randomUUID().toString());
		}
		UUID = inventory.getString("UUID");
		if (!inventory.getString("Owner").isEmpty())
			username = inventory.getString("Owner");
		if (inventory.getTag("Tools") == null)
			inventory.setTag("Tools", new NBTTagList());
		if (inventory.getTag("selection") == null)
			inventory.setInteger("selection", selected);
		if (inventory.getTag("Colors") == null) {
			NBTTagCompound Colors = new NBTTagCompound();
			Colors.setInteger("handle", 0xAAAAAA);
			Colors.setInteger("crystal", 0xA374E3);
			inventory.setTag("Colors", Colors);
		}
		crystalColor = inventory.getCompoundTag("Colors").getInteger("crystal");
		handleColor = inventory.getCompoundTag("Colors").getInteger("handle");
		selected = inventory.getInteger("selection");
		NBTTagList itemList = (NBTTagList) inventory.getTag("Tools");
		for (int i = 0; i < itemList.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound) itemList.get(i);
			ItemStack stack = new ItemStack(item);
			items.add(stack);
		}
	}

	public void writeData() {
		if (inventory == null)
			inventory = new NBTTagCompound();
		if (username == null || username.isEmpty())
			username = "Maelstrom";
		inventory.setString("Owner", username);
		if (UUID == null)
			UUID = java.util.UUID.randomUUID().toString();
		inventory.setString("UUID", UUID);

		// inventory.setInteger("FE", energy.getEnergyStored());

		if (crystalColor < 0)
			crystalColor = 0;
		if (handleColor < 0)
			handleColor = 0;
		NBTTagCompound Colors = new NBTTagCompound();
		Colors.setInteger("crystal", crystalColor);
		Colors.setInteger("handle", handleColor);
		inventory.setTag("Colors", Colors);

		NBTTagList itemList = (NBTTagList) new NBTTagList();
		for (ItemStack is : items)
			itemList.appendTag(is.serializeNBT());
		inventory.setInteger("selection", selected);
		inventory.setTag("Tools", itemList);
		list.replace(UUID, this);
	}

	public NBTTagCompound getCompound() {
		return inventory;
	}

	public int[] getColors() {
		return new int[] { crystalColor, handleColor };
	}

	public void setColors(int crystal, int handle) {
		crystalColor = crystal;
		handleColor = handle;
	}

	public void setOwner(String name) {
		username = name;
	}

	public ItemStack getCurrentItem() {
		if (selected > items.size() - 1)
			selected = 0;
		if (selected < 0)
			selected = items.size() - 1;
		if (items.size() > 0)
			return items.get(selected);
		else
			return null;
	}

	public void setCurrentItem(int i) {
		selected = i;
		if (selected > items.size() - 1)
			selected = 0;
		if (selected < 0)
			selected = items.size() - 1;
	}

	/////////////////////////////

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "SonicInventory";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getSizeInventory() {
		return items.size() + 1;
	}

	@Override
	public boolean isEmpty() {
		return items.isEmpty();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return items.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return items.remove(index);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == items.size() + 1) {
			items.add(stack);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		// do something
		writeData();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getName() == username || username.equals("Maelstrom");
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// open inventory
		readData();
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		// drop any held items
		// write nbt
		writeData();

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack.getMaxStackSize() == 1 && index == items.size() + 1;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		items.clear();
	}

	public String getOwner() {
		return username;
	}

	public void changeItem(ItemStack item, int index) {
		items.set(index, item);
	}

	public void changeCurrentItem(ItemStack item) {
		changeItem(item, selected);
	}

	public static SonicInventory getInventory(ItemStack stack) {
		if (stack.hasTagCompound()) {
			NBTTagCompound tag = (NBTTagCompound) stack.getTagCompound().getTag("ItemInventory");
			if (tag != null) {
				String UUID = tag.getString("UUID");
				if (!UUID.isEmpty() && list.containsKey(UUID))
					return list.get(UUID);
			}
		}
		return new SonicInventory(stack);
	}

	public static SonicInventory getInventory(String UUID) {
		if (!UUID.isEmpty() && list.containsKey(UUID))
			return list.get(UUID);
		if (UUID.equals("00000000-0000-0000-0000-000000000000")) {
			SonicInventory inv = new SonicInventory(new ItemStack(SC_Registry.SonicScrewdriver));
			inv.UUID = "00000000-0000-0000-0000-000000000000";
			inv.setColors(0x8080FF, 0xAAAAAA);
			inv.setOwner("Maelstrom");
			ArrayList<ItemStack> list = new ArrayList<ItemStack>();
			// example only
			allItems(inv, list);
			inv.writeData();
			return inv;
		}
		return new SonicInventory(new ItemStack(SC_Registry.SonicScrewdriver));
	}

	public static void allItems(SonicInventory sonic, ArrayList<ItemStack> list) {
		list.add(new ItemStack(Items.BUCKET));
		list.add(new ItemStack(Items.COMPASS));
		list.add(new ItemStack(Items.CLOCK));
		if (Loader.isModLoaded("arcanemechina"))
			list.add(GameRegistry.makeItemStack("arcanemechina:chalk", 0, 1, null));
		if (Loader.isModLoaded("enderio"))
			list.add(GameRegistry.makeItemStack("enderio:item_yeta_wrench", 0, 1, "{\"enderio:displaymode\":neutral}"));
		if (Loader.isModLoaded("draconicevolution"))
			list.add(GameRegistry.makeItemStack("draconicevolution:crystal_binder", 0, 1, null));
		if (Loader.isModLoaded("chisel"))
			list.add(GameRegistry.makeItemStack("chisel:chisel_highteck", 0, 1, "{Unbreakable:1}"));
		if (Loader.isModLoaded("bigreactors"))
			list.add(GameRegistry.makeItemStack("bigreactors:wrench", 0, 1, null));
		if (Loader.isModLoaded("immersiveengineering")) {
			list.add(GameRegistry.makeItemStack("immersiveengineering:tool", 0, 1, "{Unbreakable:1,Damage:0}"));
			list.add(GameRegistry.makeItemStack("immersiveengineering:tool", 1, 1, "{Unbreakable:1,Damage:0}"));
			list.add(GameRegistry.makeItemStack("immersiveengineering:tool", 2, 1, null));
		}
		if (Loader.isModLoaded("rftools"))
			list.add(GameRegistry.makeItemStack("rftools:smartwrench", 0, 1, null));
		if (Loader.isModLoaded("refinedstorage"))
			list.add(GameRegistry.makeItemStack("refinedstorage:wrench", 0, 1, null));
		if (Loader.isModLoaded("storagedrawers")) {
			list.add(GameRegistry.makeItemStack("storagedrawers:drawer_key", 0, 1, null));
			list.add(GameRegistry.makeItemStack("storagedrawers:shroud_key", 0, 1, null));
			list.add(GameRegistry.makeItemStack("storagedrawers:personal_key", 0, 1, null));
			list.add(GameRegistry.makeItemStack("storagedrawers:quantify_key", 0, 1, null));
		}
		if (Loader.isModLoaded("teslacorelib"))
			list.add(GameRegistry.makeItemStack("teslacorelib:wrench", 0, 1, null));
		if (Loader.isModLoaded("actuallyadditions"))
			list.add(GameRegistry.makeItemStack("actuallyadditions:item_laser_wrench", 0, 1, null));
		if (Loader.isModLoaded("appliedenergistics2"))
			list.add(GameRegistry.makeItemStack("appliedenergistics2:network_tool", 0, 1, null));
		if (Loader.isModLoaded("compactmachines3"))
			list.add(GameRegistry.makeItemStack("compactmachines3:psd", 0, 1, null));
		for (ItemStack i : list)
			sonic.setInventorySlotContents(sonic.getSizeInventory(), i);
	}

	// forge energy

	// TODO fix energy snyc issue
	// TODO fix energy recieve and extract
	/*
	 * @Override public int receiveEnergy(int maxReceive, boolean simulate) { int
	 * temp = energy.receiveEnergy(maxReceive, simulate); writeData(); return temp;
	 * }
	 * 
	 * @Override public int extractEnergy(int maxExtract, boolean simulate) { int
	 * temp = energy.extractEnergy(maxExtract, simulate); writeData(); return temp;
	 * }
	 * 
	 * @Override public int getEnergyStored() { return energy.getEnergyStored(); }
	 * 
	 * @Override public int getMaxEnergyStored() { return
	 * energy.getMaxEnergyStored(); }
	 * 
	 * @Override public boolean canExtract() { return energy.canExtract(); }
	 * 
	 * @Override public boolean canReceive() { return energy.canReceive(); }
	 */
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

		if (capability == CapabilityEnergy.ENERGY)
			return true;
		else if (this.getCurrentItem() != null)
			return this.getCurrentItem().hasCapability(capability, facing);
		return false;
	}

	// @SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		// if(capability == CapabilityEnergy.ENERGY)
		// return (T) energy;
		// else
		if (this.getCurrentItem() != null)
			return this.getCurrentItem().getCapability(capability, facing);
		return null;
	}

	public int findItem(String itemID) {
		int id = 0;
		for (ItemStack item : items) {
			if (item.getTranslationKey().equals(itemID))
				return id;
		}
		return 0;
	}

	public int getIndex() {
		return selected;
	}

}
