package com.maelstrom.arcanemechina.common;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.world.World;

public interface IProxy {

    void init();
    World getClientWorld();
    PlayerEntity getClientPlayer();
    RecipeManager getRecipeManager(World world);
}