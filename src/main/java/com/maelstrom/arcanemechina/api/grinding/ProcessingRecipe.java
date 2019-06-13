package com.maelstrom.arcanemechina.api.grinding;

import java.util.Random;

import org.apache.commons.lang3.RandomUtils;

import net.minecraft.item.ItemStack;

public class ProcessingRecipe
{
	ItemStack input;
	ItemStack output;
	float chance;
	int extra;
	
	public ProcessingRecipe(ItemStack in,ItemStack out, float d, int e)
	{
		input = in;
		output = out;
		chance = d;
		extra = e;
	}

	public ItemStack getInput() {
		return input;
	}

	public ItemStack getOutput(Random rand) {
		int amount = RandomUtils.nextFloat(0,1) < chance ? 0 : extra;
		ItemStack temp = output.copy();
		temp.grow(amount);
		return temp;
	}
}
