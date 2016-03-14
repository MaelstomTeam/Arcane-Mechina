package com.maelstrom.armech.client.fx.lightning;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.maelstrom.armech.client.fx.ParticleDispatcher;
import com.maelstrom.snowcone.RGB;


@SuppressWarnings("all")
public class FXLightning extends EntityFX
{
	private double tX = 0d;
	private double tY = 0d;
	private double tZ = 0d;
	public float length = 1f;
	public float size = .25f;
	
	ArrayList<Vec3> points = new ArrayList<Vec3>();
	
	ResourceLocation texture = new ResourceLocation("armech", "textures/misc/bolt.png");
	ResourceLocation particleTextures = new ResourceLocation("textures/particle/particles.png");
	
	public FXLightning(World world, double x, double y, double z, double tx, double ty, double tz, double arc_height)
	{
		super(world,x,y,z,0,0,0);
		
		this.setSize(0f, 0f);
		this.noClip = true;
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		this.tX = tx - x;
		this.tY = ty - y;
		this.tZ = tz - z;
		this.particleMaxAge = 1;
		
		double xx = 0d;
		double yy = 0d;
		double zz = 0d;
		
		double gravity = 0.115d;
		double noise = 0.25d / 2d;
		
		Vec3 vs = new Vec3(xx, yy, zz);
		Vec3 ve = new Vec3(this.tX, this.tY, this.tZ);
		Vec3 vc = new Vec3(xx, yy, zz);
		
		this.length = ((float) ve.lengthVector());
		Vec3 vv = calculateVelocity(vs, ve, arc_height, gravity);
		double l = distanceSquared3d(new Vec3(0d,0d,0d), vv);
		
		this.points.add(vs);
		
		int c = 0;
		while((distanceSquared3d(ve, vc) > l) && (c < 50))
		{
			Vec3 vt = vc.addVector(vv.xCoord, vv.yCoord, vv.zCoord);
			vc = new Vec3(vt.xCoord, vt.yCoord, vt.zCoord);
			vt = vt.addVector((this.rand.nextDouble() - this.rand.nextDouble()) * noise, (this.rand.nextDouble() - this.rand.nextDouble()) * noise, (this.rand.nextDouble() - this.rand.nextDouble()) * noise);
			
			
			this.points.add(vt);
			vv = vv.subtract(0d, gravity/1.9d, 0d);
			c++;
		}
	    this.points.add(ve);
	}
	
	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		if(this.particleAge++ >= this.particleMaxAge)
		{
			setDead();
			ParticleDispatcher.particleCount--;
		}
	}

	public void setRGB(float r, float g, float b) {
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
	}
	
	float fade = 0.8f;
	
	public void renderParticle(WorldRenderer wr, Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		Tessellator.getInstance().draw();

		GL11.glPushMatrix();
		double ePX = this.prevPosX + (this.posX - this.prevPosX) * f - this.interpPosX;
		double ePY = this.prevPosY + (this.posY - this.prevPosY) * f - this.interpPosY;
		double ePZ = this.prevPosZ + (this.posZ - this.prevPosZ) * f - this.interpPosZ;
		GL11.glTranslated(ePX, ePY, ePZ);
//		float size = 0.25f;

		Minecraft.getMinecraft().renderEngine.bindTexture(texture);

		GL11.glDepthMask(false);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, 1);

		GL11.glDisable(GL11.GL_CULL_FACE);

		int i = 220;
		int j = i >> 16 & 65535;
		int k = i & 65535;
		
//		fade -= (f / 2);
//		if(fade < 0f)
//			fade = 0f;
		
		wr.begin(5, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

		for (int c = 0; c < this.points.size(); c++) {
			Vec3 v = (Vec3) this.points.get(c);
			float f13 = c / this.length;
			double dx = v.xCoord;
			double dy = v.yCoord;
			double dz = v.zCoord;
			wr.pos(dx, dy - size, dz).tex(f13, 1f).lightmap(j, k).color(this.particleRed, this.particleGreen,this.particleBlue, fade).endVertex();
			wr.pos(dx, dy + size, dz).tex(f13, 0f).lightmap(j, k).color(this.particleRed, this.particleGreen,this.particleBlue, fade).endVertex();
		}

		Tessellator.getInstance().draw();

		wr.begin(5, DefaultVertexFormats.POSITION_TEX_LMAP_COLOR);

		for (int c = 0; c < this.points.size(); c++) {
			Vec3 v = (Vec3) this.points.get(c);
			float f13 = c / this.length;
			double dx = v.xCoord;
			double dy = v.yCoord;
			double dz = v.zCoord;
			wr.pos(dx - size, dy, dz - size).tex(f13, 1f).lightmap(j, k).color(this.particleRed, this.particleGreen,this.particleBlue, fade).endVertex();
			wr.pos(dx + size, dy, dz + size).tex(f13, 0f).lightmap(j, k).color(this.particleRed, this.particleGreen,this.particleBlue, fade).endVertex();
		}
		Tessellator.getInstance().draw();

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDepthMask(true);

		GL11.glPopMatrix();

		Minecraft.getMinecraft().renderEngine.bindTexture(particleTextures);

		wr.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
	}
	
	public static Vec3 calculateVelocity(Vec3 from, Vec3 to, double heightGain,
			double gravity) {
		double endGain = to.yCoord - from.yCoord;
		double horizDist = Math.sqrt(distanceSquared2d(from, to));

		double gain = heightGain;
		double maxGain = gain > endGain + gain ? gain : endGain + gain;

		double a = -horizDist * horizDist / (4.0D * maxGain);
		double b = horizDist;
		double c = -endGain;

		double slope = -b / (2.0D * a) - Math.sqrt(b * b - 4.0D * a * c)
				/ (2.0D * a);

		double vy = Math.sqrt(maxGain * gravity);

		double vh = vy / slope;

		double dx = to.xCoord - from.xCoord;
		double dz = to.zCoord - from.zCoord;
		double mag = Math.sqrt(dx * dx + dz * dz);
		double dirx = dx / mag;
		double dirz = dz / mag;

		double vx = vh * dirx;
		double vz = vh * dirz;

		return new Vec3(vx, vy, vz);
	}

	public static double distanceSquared2d(Vec3 from, Vec3 to) {
		double dx = to.xCoord - from.xCoord;
		double dz = to.zCoord - from.zCoord;
		return dx * dx + dz * dz;
	}

	public static double distanceSquared3d(Vec3 from, Vec3 to) {
		double dx = to.xCoord - from.xCoord;
		double dy = to.yCoord - from.yCoord;
		double dz = to.zCoord - from.zCoord;
		return dx * dx + dy * dy + dz * dz;
	}

	public FXLightning setArcSize(float f)
	{
		size = f;
		return this;
	}

	public FXLightning setFadeAmount(float f)
	{
		fade = f;
		return this;
	}
}
