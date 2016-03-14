package com.maelstrom.armech.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import com.maelstrom.armech.common.Reference;
import com.maelstrom.snowcone.modelloader.AdvancedModelLoader;
import com.maelstrom.snowcone.modelloader.IModelCustom;

public class ModelCrystal extends ModelBase
{
	IModelCustom model = AdvancedModelLoader.loadModel(new ResourceLocation(Reference.MODID,"models/obj/crystal_charge.obj"));
	
	public void renderTile(TileEntity tile)
	{
		model.renderAll();
	}
}