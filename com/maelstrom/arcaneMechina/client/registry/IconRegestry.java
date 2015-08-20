package com.maelstrom.arcanemechina.client.registry;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.TextureStitchEvent;

import com.maelstrom.arcanemechina.client.texture.TieredCompassTexture;
import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class IconRegestry {
	
	public static TieredCompassTexture[] compass = new TieredCompassTexture[3];
	
	@SubscribeEvent
	public void registerTextures(TextureStitchEvent.Pre event) {
		TextureMap map = event.map;
		if (map.getTextureType() == 1) {
			compass[0] = new TieredCompassTexture("Zero", 0);
			compass[1] = new TieredCompassTexture("North", 1);
			compass[2] = new TieredCompassTexture("GPS", 2);
			map.setTextureEntry(Reference.MOD_ID + ":" + ItemsReference.tieiredCompassName + "Zero", compass[0]);
			map.setTextureEntry(Reference.MOD_ID + ":" + ItemsReference.tieiredCompassName + "North", compass[1]);
			map.setTextureEntry(Reference.MOD_ID + ":" + ItemsReference.tieiredCompassName + "GPS", compass[2]);
		}
	}
}
