package com.maelstrom.arcanemechina.client.texture;

import com.maelstrom.arcanemechina.common.ItemsReference;
import com.maelstrom.arcanemechina.common.Reference;
import com.maelstrom.arcanemechina.common.items.ItemTieredCompass;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class TieredCompassTexture extends TextureCompass {
	
	int itemDamage = 0;
	
	public TieredCompassTexture(String compassType, int damage) {
		super(Reference.MOD_ID + ":" + ItemsReference.tieiredCompassName + compassType);
		itemDamage = damage;
	}
    @Override
    public void updateAnimation() {
        Minecraft mc = Minecraft.getMinecraft();

        if( mc.theWorld != null && mc.thePlayer != null ) {
            this.updateCompass(mc.theWorld, mc.thePlayer.posX, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, false, false);
        } else {
            super.updateAnimation();
        }
    }
    public void updateCompass(World world, double x, double y, double z, boolean unknown1, boolean unknown2)
    {
        if (!this.framesTextureData.isEmpty())
        {
            double d3 = 0.0D;

            if (world != null && !unknown1)
            {
            	ChunkCoordinates chunkcoordinates;
            	///////////////////////////////////////////////////////////////////////////////
            	///////////////////////////////////////////////////////////////////////////////
            	///////////////////////////////////////////////////////////////////////////////
            	///CUSTOM//////////////////////////////////////////////////////////////////////
            	switch(itemDamage)
            	{
                	default: case 0:
            		{
            			chunkcoordinates = new ChunkCoordinates((int)x,(int)y,(int)z - 1000);
            			break;
            		}
                	case 1:
            		{
            			chunkcoordinates = new ChunkCoordinates(0,64,0);
            			break;
            		}
                	case 2:
                	{
                		chunkcoordinates = ItemTieredCompass.gps;
                		break;
                	}
            	}
            	///////////////////////////////////////////////////////////////////////////////
            	///////////////////////////////////////////////////////////////////////////////
            	///////////////////////////////////////////////////////////////////////////////
            	///////////////////////////////////////////////////////////////////////////////
                double d4 = (double)chunkcoordinates.posX - x;
                double d5 = (double)chunkcoordinates.posZ - y;
                z %= 360.0D;
                d3 = -((z - 90.0D) * Math.PI / 180.0D - Math.atan2(d5, d4));

                if (!world.provider.isSurfaceWorld())
                {
                    d3 = Math.random() * Math.PI * 2.0D;
                }
            }

            if (unknown2)
            {
                this.currentAngle = d3;
            }
            else
            {
                double d6;

                for (d6 = d3 - this.currentAngle; d6 < -Math.PI; d6 += (Math.PI * 2D))
                {
                    ;
                }

                while (d6 >= Math.PI)
                {
                    d6 -= (Math.PI * 2D);
                }

                if (d6 < -1.0D)
                {
                    d6 = -1.0D;
                }

                if (d6 > 1.0D)
                {
                    d6 = 1.0D;
                }

                this.angleDelta += d6 * 0.1D;
                this.angleDelta *= 0.8D;
                this.currentAngle += this.angleDelta;
            }

            int i;

            for (i = (int)((this.currentAngle / (Math.PI * 2D) + 1.0D) * (double)this.framesTextureData.size()) % this.framesTextureData.size(); i < 0; i = (i + this.framesTextureData.size()) % this.framesTextureData.size())
            {
                ;
            }

            if (i != this.frameCounter)
            {
                this.frameCounter = i;
                TextureUtil.uploadTextureMipmap((int[][])this.framesTextureData.get(this.frameCounter), this.width, this.height, this.originX, this.originY, false, false);
            }
        }
    }

}
