package com.maelstrom.arcanemechina.common.runic.newrune;

import java.util.HashMap;
import java.util.Optional;

import com.maelstrom.snowcone.common.WorldUtilities;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.WorkbenchContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;

public class RecipeHelper {
	private static WorkbenchContainer container;
	private static HashMap<ItemStack, ItemStack[][]> helper = new HashMap<ItemStack, ItemStack[][]>();
	public static CompoundNBT empty3x3 = new CompoundNBT();
	public static CompoundNBT stick = new CompoundNBT();
	static {
		empty3x3 = createFromList(
				new ItemStack[][] { new ItemStack[] { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY },
						new ItemStack[] { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY },
						new ItemStack[] { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY } });

		stick = createFromList(new ItemStack[][] { new ItemStack[] { new ItemStack(Items.OAK_WOOD) },
				new ItemStack[] { new ItemStack(Items.OAK_WOOD) } });

	}
	public static Item reference_item = Items.PAPER;
	public static ItemStack createFromListToItemStack(ItemStack[][] list)
	{
		ItemStack output = new ItemStack(reference_item, 1);
		output.setTag(createFromList(list));
		return output;
	}

	// has to be a perfect sublist, no xxx,xx,x must all be same size
	public static CompoundNBT createFromList(ItemStack[][] list) {
		int x = list.length;
		int y = list[0].length;
		CompoundNBT recipe_data = new CompoundNBT();

		ListNBT list_nbt = new ListNBT();

		for (int y2 = 0; y2 < 3; y2++) {
			for (int x2 = 0; x2 < 3; x2++) {
				if (list[x2] != null && list[x2][y2] != null)
					list_nbt.add(list[x2][y2].getOrCreateTag());
				list_nbt.add(ItemStack.EMPTY.getOrCreateTag());
			}
		}
		recipe_data.put("recipe", list_nbt);
		return (CompoundNBT) new CompoundNBT().put("recipe_data", recipe_data);
	}

	public static ItemStack[][] getFromNBT(CompoundNBT tag) {
		if (tag.get("recipe_data") != null && tag.getCompound("recipe_data").get("recipe") != null) {
			ItemStack[][] itemstacks = new ItemStack[3][3];
			ListNBT list = (ListNBT) tag.getCompound("recipe_data").get("recipe");

			for (int y2 = 0; y2 < 3; y2++) {
				for (int x2 = 0; x2 < 3; x2++) {
					if (itemstacks[x2] == null)
						itemstacks[x2] = new ItemStack[3];
					itemstacks[x2][y2] = ItemStack.read(list.getCompound(x2 + (y2 * 3)));
				}
			}
			return itemstacks;
		} else
			return getFromNBT(empty3x3);

	}

	public static ICraftingRecipe getRecipe(World world, ItemStack item) {
		ItemStack[][] itemCraft = helper.get(item);
		if (itemCraft != null) {
		} else {
			itemCraft = getFromNBT(item.getOrCreateTag());
			helper.put(item, itemCraft);
		}
		if (container == null)
			container = new WorkbenchContainer(0,
					WorldUtilities.getFakePlayer(world.getServer().getWorld(world.dimension.getType())).inventory);
		for (int i = 0; i < container.getSize(); i++)
			container.putStackInSlot(i, ItemStack.EMPTY);
		CraftingInventory crafting_inventory = new CraftingInventory(container, 3, 3);
		for (int x1 = 0; x1 < 3; x1++) {
			for (int y1 = 0; y1 < 3; y1++) {
				crafting_inventory.setInventorySlotContents((x1 + y1 * 3), itemCraft[x1][y1]);
			}
		}
		Optional<ICraftingRecipe> s = world.getServer().getRecipeManager().getRecipe(IRecipeType.CRAFTING,
				crafting_inventory, world);
		if (s.isPresent())
			return s.get();
		return null;

	}

}
