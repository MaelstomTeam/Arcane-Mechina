package com.maelstrom.snowcone.common;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

public class WorldUtilities
{

	public static final GameProfile gameProfile = new GameProfile(UUID.nameUUIDFromBytes("ARCANEMECHINA MOD".getBytes()), "[ARCANEMECHINA MOD]");
	public static List<ItemStack> BreakBlock(ServerWorld world, BlockPos pos, Entity player) {
		BlockState state = world.getBlockState(pos);

		PlayerEntity passedPlayer;

		if (player instanceof PlayerEntity) {
			passedPlayer = (PlayerEntity) player;
		} else {
			passedPlayer = FakePlayerFactory.get(world, gameProfile);
		}

		BlockEvent.BreakEvent breakEvent = new BlockEvent.BreakEvent(world, pos, state, passedPlayer);
		MinecraftForge.EVENT_BUS.post(breakEvent);

		if (breakEvent.isCanceled()) {
			return new ArrayList<ItemStack>();
		}
		List<ItemStack> items = Block.getDrops(state, world, pos, world.getTileEntity(pos), passedPlayer, passedPlayer.getHeldItemMainhand());
		world.destroyBlock(pos,false);

		return items;
	}
	public static FakePlayer getFakePlayer(ServerWorld world) {
		return FakePlayerFactory.get(world, gameProfile);
	}
}
