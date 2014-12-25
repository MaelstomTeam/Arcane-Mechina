package com.maelstrom.arcaneMechina.block;

import java.util.Random;

import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.arcaneMechina.tileentity.TileEntityFurnace;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class BlockArrayFurnace extends Block implements ITileEntityProvider {

	public BlockArrayFurnace(Material material, String unlocal) {
		super(material);
		this.setBlockName(Reference.MOD_ID+"."+unlocal);
		this.setLightLevel(5f);
		this.setHardness(2.0f);
		this.setResistance(6.0f);
		this.setStepSound(soundTypeStone);
	}
	
	private boolean active = false;

	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int face, float xf, float yf, float zf) {
		return true;
	}


	public boolean renderAsNormalBlock() {
		return false;
	}
	
    public void randomDisplayTick(World w, int x, int y, int z, Random rand)
    {
        if (this.active)
        {
            int meta = w.getBlockMetadata(x, y, z);
            if (meta == 0)
            {
                float f = (float)x + 0.5F;
                float f2 = (float)z + 0.5F;
                float f3 = 0.52F;
            	for(int i = 0; i < 4; i++){
	                w.spawnParticle("smoke", (double)(f - f3), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+(rand.nextDouble()/2), (double)(f2 + rand.nextFloat() * 0.6F - 0.3F), 0.0D, 0.0D, 0.0D);
	                w.spawnParticle("smoke", (double)(f + f3), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+(rand.nextDouble()/2), (double)(f2 + rand.nextFloat() * 0.6F - 0.3F), 0.0D, 0.0D, 0.0D);
	                w.spawnParticle("smoke", (double)(f + rand.nextFloat() * 0.6F - 0.3F), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+(rand.nextDouble()/2), (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
	                w.spawnParticle("smoke", (double)(f + rand.nextFloat() * 0.6F - 0.3F), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+(rand.nextDouble()/2), (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            	}
                w.spawnParticle("flame", (double)(f - f3), (y + 0.0F + rand.nextFloat() * 6.0F / 16.0F)+Math.random(), (double)(f2 + rand.nextFloat() * 0.6F - 0.3F), 0.0D, 0.0D, 0.0D);
                w.spawnParticle("flame", (double)(f + f3), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+Math.random(), (double)(f2 + rand.nextFloat() * 0.6F - 0.3F), 0.0D, 0.0D, 0.0D);
                w.spawnParticle("flame", (double)(f + rand.nextFloat() * 0.6F - 0.3F), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+Math.random(), (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                w.spawnParticle("flame", (double)(f + rand.nextFloat() * 0.6F - 0.3F), (double)y + 0.0F + rand.nextFloat() * 6.0F / 16.0F+Math.random(), (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType(){
		return -1;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iicon){
		this.blockIcon = iicon.registerIcon("minecraft:textures/blocks/stone");
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEntityFurnace();
	}

}
