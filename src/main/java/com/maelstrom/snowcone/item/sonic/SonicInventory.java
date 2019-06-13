package com.maelstrom.snowcone.item.sonic;

import java.util.ArrayList;
import java.util.UUID;

import com.maelstrom.snowcone.common.EventHandler;
import com.maelstrom.snowcone.network.PacketHandler;

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
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SonicInventory implements IInventory, IEnergyStorage, ICapabilityProvider {

	// TODO move to UUID system, change so nbt can be marked as dirty and saved
	// TODO clear list at server join or world start
	//public static Map<String, SonicInventory> list = new HashMap<String, SonicInventory>();

	public String uuid;
	//NBTTagCompound inventory;
	ArrayList<ItemStack> items = new ArrayList<ItemStack>();
	String username = "";
	int selected = 0;
	int crystalColor = 0xA374E3;
	int handleColor = 0xAAAAAA;
	EnergyStorage energy = new EnergyStorage(Integer.MAX_VALUE, Integer.MAX_VALUE,Integer.MAX_VALUE,0);
	private SonicInventory() {}
	public static SonicInventory createInventory(NBTTagCompound nbt, java.util.UUID uuid)
	{
		SonicInventory sonic = new SonicInventory();
		
		NBTTagList SubItemList = (NBTTagList) nbt.getTag("SubItems");
		sonic.items.clear();
		for(int i = 0; i < SubItemList.tagCount(); i++)
		{
			ItemStack stack = new ItemStack((NBTTagCompound) SubItemList.get(i));
			sonic.items.add(stack);
		}

		{
			NBTTagCompound colorList = nbt.getCompoundTag("Colors");
			sonic.crystalColor = colorList.getInteger("crystal");
			sonic.handleColor = colorList.getInteger("handle");
		}
		sonic.selected = nbt.getInteger("Selection");
		sonic.uuid = uuid.toString();
		sonic.username = nbt.getString("Owner");
		sonic.energy.receiveEnergy(nbt.getInteger("StoredEnergy"),false);
		return sonic;
	}
	public static SonicInventory createInventory(UUID uuid)
	{
		if(!EventHandler.list.containsKey(uuid))
		{
			SonicInventory sonic = new SonicInventory();
			sonic.uuid = uuid.toString();
			EventHandler.list.put(uuid, sonic);
			return sonic;
		}
		return EventHandler.list.get(uuid);
	}
	public static SonicInventory getInventory(ItemStack is)
	{
		return getInventory(is,null);
	}
	public static SonicInventory getInventory(UUID uuid)
	{
		return EventHandler.list.get(uuid);
	}
	public static SonicInventory getInventory(ItemStack is, EntityPlayer player)
	{
		SonicInventory sonic = null;
		if(is != null && is.getSubCompound("SonicInventory") != null)
		{
			NBTTagCompound nbt = is.getTagCompound().getCompoundTag("SonicInventory");
			UUID _uuid = UUID.fromString(nbt.getString("UUID"));
			sonic = EventHandler.list.get(_uuid);
		}
		if(sonic == null)
		{
			if(player != null)
				sonic = SonicInventory.createInventory(player.getUniqueID());
			else
				sonic = SonicInventory.createInventory(UUID.fromString("00000000-0000-0000-0000-000000000000"));
		}
		return sonic;
	}

	boolean dirty = false;
	NBTTagCompound SonicTag;
	public NBTTagCompound getTag()
	{
		if(dirty || SonicTag == null)
		{
			SonicTag = new NBTTagCompound();
			{
				NBTTagList nbtTagList = new NBTTagList();
				if(!items.isEmpty())
					for(ItemStack is : items)
						nbtTagList.appendTag(is.serializeNBT());
				SonicTag.setTag("SubItems", nbtTagList);
			}
			{
				NBTTagCompound colors = new NBTTagCompound();
				colors.setInteger("crystal", crystalColor);
				colors.setInteger("handle", handleColor);
				SonicTag.setTag("Colors", colors);
			}
			SonicTag.setInteger("Selection", selected);
			SonicTag.setString("Owner", username);
			SonicTag.setInteger("StoredEnergy", energy.getEnergyStored());
			dirty = false;
		}
		return SonicTag;
	}
	/*
	public void readData() {
		if (inventory == null)
			inventory = new NBTTagCompound();

		/*
		 * if(inventory.getTag("FE") != null) { energy = new
		 * EnergyStorage(Integer.MAX_VALUE,
		 * Integer.MAX_VALUE,Integer.MAX_VALUE,inventory.getInteger("FE")); }
		 /

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
	}*/

	//public NBTTagCompound getCompound() {
	//	return inventory;
	//}

	public int[] getColors() {
		return new int[] { crystalColor, handleColor };
	}

	public void setColors(int crystal, int handle) {
		crystalColor = crystal;
		handleColor = handle;
	}

	public void setOwner(String name) {
		dirty = true;
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
		dirty = true;
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
		dirty = true;
		if (index == items.size() + 1) {
			if(!items.contains(stack))
				items.add(stack);
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public void markDirty() {
		dirty = true;
		// do something
		//writeData();
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return player.getName() == username || username.equals("Maelstrom");
	}

	@Override
	public void openInventory(EntityPlayer player) {
		// open inventory
		//readData();
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		dirty = true;
		// drop any held items
		// write nbt
		//writeData();

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
		dirty = true;
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
		dirty = true;
		items.set(index, item);
	}

	public void changeCurrentItem(ItemStack item) {
		dirty = true;
		changeItem(item, selected);
	}
/*
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
*/
	public static void reset(SonicInventory sonic)
	{
		if(sonic != null)
		{
			sonic.clear();
			allItems(sonic,new ArrayList<ItemStack>());
		}
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
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityEnergy.ENERGY)
			return true;
		else 
			if (this.getCurrentItem() != null)
				return this.getCurrentItem().hasCapability(capability, facing);
		return false;
	}

	// @SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		dirty = true;
		// if(capability == CapabilityEnergy.ENERGY)
		// return (T) energy;
		// else
		if (this.getCurrentItem() != null)
			return this.getCurrentItem().getCapability(capability, facing);
		else if (capability == CapabilityEnergy.ENERGY)
			return (T)this;
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
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		if(!simulate)
			markDirty();
		if(hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
			return getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP).receiveEnergy(maxReceive, simulate);
		return energy.receiveEnergy(maxReceive, simulate);
	}
	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		if(!simulate)
			markDirty();
		if(hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
			return getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP).extractEnergy(maxExtract, simulate);
		return energy.extractEnergy(maxExtract, simulate);
	}
	@Override
	public int getEnergyStored() {
		if(hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
			return getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP).getEnergyStored();
		return energy.getEnergyStored();
	}
	@Override
	public int getMaxEnergyStored() {
		if(hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
			return getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP).getMaxEnergyStored();
		return energy.getMaxEnergyStored();
	}
	@Override
	public boolean canExtract() {
		if(hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
			return getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP).canExtract();
		return energy.canExtract();
	}
	@Override
	public boolean canReceive() {
		if(hasCapability(CapabilityEnergy.ENERGY, EnumFacing.UP))
			return getCapability(CapabilityEnergy.ENERGY, EnumFacing.UP).canReceive();
		return energy.canReceive();
	}

}
