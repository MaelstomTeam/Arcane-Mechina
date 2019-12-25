package com.maelstrom.arcanemechina.common;

import java.util.HashMap;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.World;

public class RecipeHelper {
	private static HashMap<ItemStack, CraftingRecipeCache> helper = new HashMap<ItemStack, CraftingRecipeCache>();
	public static CompoundNBT empty3x3 = createFromList(
			new ItemStack[] { ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
					ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
					ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY });
	public static Item reference_item = Registry.blueprint_recipe;
	public static ItemStack createFromListToItemStack(ItemStack[] list)
	{
		ItemStack output = new ItemStack(reference_item, 1);
		output.getOrCreateTag().put("recipe_data",createFromList(list));
		output.setDamage(1);
		return output;
	}

	// has to be a perfect sublist, no xxx,xx,x must all be same size
	public static CompoundNBT createFromList(ItemStack[] list) {
		CompoundNBT recipe_data = new CompoundNBT();
		
		ListNBT list_nbt = new ListNBT();
		for (int slot = 0; slot < 9; slot++) {
			try {
				if (list[slot] != null && list[slot] != null)
					list_nbt.add(list[slot].write(new CompoundNBT()));
				else
					list_nbt.add(ItemStack.EMPTY.write(new CompoundNBT()));
			}
			catch(Exception e)
			{
				list_nbt.add(ItemStack.EMPTY.write(new CompoundNBT()));
			}
		}
		recipe_data.put("recipe", list_nbt);
		return recipe_data;
	}

	public static ItemStack[] getFromItem(ItemStack item) {
		if(isCraftingItem(item))
			return getFromNBT(item.getTag());
		return new ItemStack[] {ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY,ItemStack.EMPTY};
	}
	public static ItemStack[] getFromNBT(CompoundNBT tag) {
		if(tag != null)
			if (tag.get("recipe_data") != null && tag.getCompound("recipe_data").get("recipe") != null) {
				ItemStack[] itemstacks = new ItemStack[9];
				ListNBT list = (ListNBT) tag.getCompound("recipe_data").get("recipe");
	
				for (int slot = 0; slot < 9; slot++) {
					itemstacks[slot] = ItemStack.read(list.getCompound(slot));
				}
				return itemstacks;
			}
		return null;

	}

	public static CraftingRecipeCache getRecipe(World world, ItemStack item) {
		CraftingRecipeCache cachedRecipe = helper.get(item);
		if (cachedRecipe == null) {
			cachedRecipe = new CraftingRecipeCache();
			helper.put(item, cachedRecipe);
			ItemStack[] items = getFromItem(item);
			if(items != null)
				cachedRecipe.setRecipe(items);
		}
		return cachedRecipe;

	}

	public static boolean isCraftingItem(ItemStack item)
	{
		if(item != null && !item.isEmpty() && item.getTag() != null && item.getTag().get("recipe_data") != null)
			return true;
		return false;
	}
    public static boolean isItemStackConsideredEqual(ItemStack result, ItemStack itemstack1) {
        return !itemstack1.isEmpty() && itemstack1.getItem() == result.getItem() && (result.getDamage() == itemstack1.getDamage()) && ItemStack.areItemStackTagsEqual(result, itemstack1);
    }

}
