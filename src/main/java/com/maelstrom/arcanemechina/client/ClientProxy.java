package com.maelstrom.arcanemechina.client;

import com.maelstrom.arcanemechina.client.gui.RuneCraftingGui;
import com.maelstrom.arcanemechina.client.tesr.RenderRune;
import com.maelstrom.arcanemechina.common.IProxy;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ClientProxy implements IProxy
{

	@Override
	public void init()
	{
        ScreenManager.registerFactory(Registry.RDC, RuneCraftingGui::new);
    	Registry.RegisterModels();
    	ClientRegistry.bindTileEntitySpecialRenderer(RuneTileEntity.class, new RenderRune());
	}

	@Override
	public World getClientWorld()
	{
		return Minecraft.getInstance().world;
	}

	@Override
	public PlayerEntity getClientPlayer()
	{
		return Minecraft.getInstance().player;
	}

}
