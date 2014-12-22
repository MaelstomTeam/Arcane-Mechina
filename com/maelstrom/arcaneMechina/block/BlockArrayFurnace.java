package com.maelstrom.arcaneMechina.block;

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


	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int face, float xf, float yf, float zf) {
		return true;
	}


	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isOpaqueCube() {
		return false;
	}

	public int getRenderType(){
		return -1;
	}

	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iicon){
		this.blockIcon = iicon.registerIcon("minecraft:textures/blocks/stone.png");
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEntityFurnace();
	}

}
