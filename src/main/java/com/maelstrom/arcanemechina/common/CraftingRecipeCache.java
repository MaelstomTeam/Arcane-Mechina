package com.maelstrom.arcanemechina.common;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.world.World;
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
// based on McJty's CraftingRecipe class 
// https://github.com/McJtyMods/RFToolsUtility/blob/master/src/main/java/mcjty/rftoolsutility/modules/crafter/CraftingRecipe.java
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
@SuppressWarnings("all")
public class CraftingRecipeCache
{
	
	
    public static IRecipe findRecipe(World world, CraftingInventory inv) {
        for (IRecipe r : Registry.PROXY.getRecipeManager(world).getRecipes()) {
            if (r != null && IRecipeType.CRAFTING.equals(r.getType()) && r.matches(inv, world)) {
                return r;
            }
        }
        return null;
    }
    
    public static class CompressedIngredient {
        private final ItemStack stack;
        private int[] gridDistribution = new int[9];

        public CompressedIngredient(ItemStack stack) {
            this.stack = stack;
            for (int i = 0 ; i < gridDistribution.length ; i++) {
                gridDistribution[i] = 0;
            }
        }

        public ItemStack getStack() {
            return stack;
        }

        public int[] getGridDistribution() {
            return gridDistribution;
        }
    }
    
    private ItemStack result = ItemStack.EMPTY;
    private boolean isRecipePresent = false;
    private IRecipe recipe = null;
    private boolean cycle = false;
    
	private CraftingInventory inv = new CraftingInventory(new Container(null, -1) {
        @Override
        public boolean canInteractWith(PlayerEntity var1) {
            return false;
        }
	},3,3);

    public CraftingInventory getInventory() {
        return inv;
    }

    public ItemStack getResult() {
        return result;
    }

    public ItemStack getResultCopy() {
        return result.copy();
    }

    public void setRecipe(ItemStack[] items, ItemStack result) {
        for (int i = 0 ; i < inv.getSizeInventory() ; i++) {
            inv.setInventorySlotContents(i, items[i]);
        }
        this.result = result;
        isRecipePresent = false;
    }
    public void setRecipe(ItemStack[] items) {
        for (int i = 0 ; i < inv.getSizeInventory() ; i++) {
            inv.setInventorySlotContents(i, items[i]);
        }
        isRecipePresent = false;
    }

    public IRecipe getRecipe(World world) {
        if (!isRecipePresent) {
        	isRecipePresent = true;
            recipe = findRecipe(world, inv);
            result = recipe.getRecipeOutput();
        }
        return recipe;
    }

    public boolean isCyclic() {
        return cycle;
    }

    public void setCyclic(boolean shouldKeepOneItem) {
        this.cycle = shouldKeepOneItem;
    }

    private List<CompressedIngredient> compressedIngredients = null;
    public List<CompressedIngredient> getCompressedIngredients() {
        if (compressedIngredients == null) {
            compressedIngredients = new ArrayList<>();
            for (int i = 0 ; i < inv.getSizeInventory() ; i++) {
                ItemStack stack = inv.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    boolean found  = false;
                    for (CompressedIngredient ingredient : compressedIngredients) {
                        if (RecipeHelper.isItemStackConsideredEqual(stack, ingredient.getStack())) {
                            ingredient.getStack().grow(stack.getCount());
                            ingredient.getGridDistribution()[i] += stack.getCount();
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        CompressedIngredient ingredient = new CompressedIngredient(stack.copy());
                        ingredient.getGridDistribution()[i] += stack.getCount();
                        compressedIngredients.add(ingredient);
                    }
                }
            }
        }
        return compressedIngredients;
    }
}
