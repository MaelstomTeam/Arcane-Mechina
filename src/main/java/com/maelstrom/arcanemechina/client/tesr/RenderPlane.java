package com.maelstrom.arcanemechina.client.tesr;

import net.minecraft.client.renderer.entity.model.RendererModel;
import net.minecraft.client.renderer.model.Model;

public class RenderPlane extends Model {

	private RendererModel plane;
	public RenderPlane()
	{
		this.plane = new RendererModel(this, 0,0);
		this.plane.setTextureSize(16, 16);
		this.plane.addBox(0, 0, 0, 16, 0, 16);
		this.plane.setRotationPoint(8, 0, 8);
	}
	public void render()
	{
		this.plane = new RendererModel(this, 0,0);
		this.plane.setTextureSize(16, 16);
		this.plane.addBox(0, 0, 0, 16, 0, 16);
		this.plane.setRotationPoint(8, 0, 8);
		plane.render(1f);
	}
}
