package com.maelstrom.arcanemechina.common.items;

import java.util.Random;

import com.maelstrom.arcanemechina.common.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

//for use with people who spawn items they shouldn't ever have
public class PotionEffectFreeze extends Potion
{

	public PotionEffectFreeze(String name, int id)
	{
		super(id, true, 5149489);
		this.setIconIndex(0, 0);
		this.setPotionName(name);
	}
	
    public void affectEntity(EntityLivingBase entity, EntityLivingBase otherEntity, int p_76402_3_, double p_76402_4_)
    {
    }
    
    public int getStatusIconIndex()
    {
    	ResourceLocation icon = new ResourceLocation(Reference.MOD_ID + ":textures/gui/potionEffects.png");
    	ITextureObject texture = Minecraft.getMinecraft().renderEngine.getTexture(icon);
    	Minecraft.getMinecraft().renderEngine.bindTexture(icon);
    	return super.getStatusIconIndex();
    }

}
