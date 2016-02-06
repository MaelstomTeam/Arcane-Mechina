package com.maelstrom.armech.common.blocks;

import java.util.Random;

import org.fusesource.jansi.Ansi.Color;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.tileentity.TileEntityCrystal;

public class BlockPowerStorageCrystal extends Block implements ITileEntityProvider
{

	public static final PropertyEnum TYPE = PropertyEnum.create("type", MetaBlock.class);
	
	public BlockPowerStorageCrystal()
	{
		super(Material.glass);
		this.setUnlocalizedName("multiblock.powercrystal");
		this.setCreativeTab(ArMechMain.tab_armech);
		setHardness(1f);
		setResistance(1f);
	    setHarvestLevel("pickaxe", 1);
		this.setBlockBounds(.2f, .2f, .2f, .8f, .8f, .8f);
		this.setDefaultState(blockState.getBaseState().withProperty(TYPE, MetaBlock.Main));
		this.setLightLevel(1f);
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		this.setLightLevel(0f);
		if(world.isAirBlock(pos.up()) && world.isAirBlock(pos.up().up()))
			return true;
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing face, float xf, float yf, float zf, int meta, EntityLivingBase entityPlacer)
	{
		world.setBlockState(pos.up().up(), getStateFromMeta(2));
		world.setBlockState(pos.up(), getStateFromMeta(1));
		return getStateFromMeta(0);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(getMetaFromState(state) == 0)
		{
			world.destroyBlock(pos.up(), false);
			world.destroyBlock(pos.up().up(), false);
		}
		else if(getMetaFromState(state) == 1)
		{
			world.destroyBlock(pos.down(), false);
			world.destroyBlock(pos.up(), false);
		}
		else if(getMetaFromState(state) == 2)
		{
			world.destroyBlock(pos.down(), false);
			world.destroyBlock(pos.down().down(), false);
		}
		
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		if(this.getMetaFromState(world.getBlockState(pos)) == 0)
		{
			this.setBlockBounds(.2f, .2f, .2f, .8f, 1f, .8f);
		}
		else if(this.getMetaFromState(world.getBlockState(pos)) == 1)
		{
			this.setBlockBounds(.2f, 0f, .2f, .8f, 1f, .8f);
		}
		else if(this.getMetaFromState(world.getBlockState(pos)) == 2)
		{
			this.setBlockBounds(.2f, 0f, .2f, .8f, .8f, .8f);
		}
	}

	  
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox( World world, BlockPos pos) {

		if(this.getMetaFromState(world.getBlockState(pos)) == 0)
		{
			return AxisAlignedBB.fromBounds(pos.getX() + .2f, pos.getY() + .2f, pos.getZ() + .2f, pos.getX() + .8f, pos.getY() + 2.8f, pos.getZ() + .8f);
		}
		else if(this.getMetaFromState(world.getBlockState(pos)) == 1)
		{
			return AxisAlignedBB.fromBounds(pos.getX() + .2f, pos.getY() - .8f, pos.getZ() + .2f, pos.getX() + .8f, pos.getY() + 1.8f, pos.getZ() + .8f);
		}
		else if(this.getMetaFromState(world.getBlockState(pos)) == 2)
		{
			return AxisAlignedBB.fromBounds(pos.getX() + .2f, pos.getY() - 1.8f, pos.getZ() + .2f, pos.getX() + .8f, pos.getY() + .8f, pos.getZ() + .8f);
		}
		return super.getSelectedBoundingBox(world, pos);
	}
	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	  
	@Override
	public BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{TYPE});
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(TYPE, MetaBlock.getFromMeta(meta));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return getMetaFromState(state) == 1 ? Item.getItemFromBlock(this) : null;
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return ((MetaBlock)state.getValue(TYPE)).getID();
	}

	@Override
	public int damageDropped(IBlockState state)
	{
		return 0;
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition targe, World world, BlockPos pos)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, 0);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		if(getMetaFromState(world.getBlockState(pos)) == 1)
			return Color.BLUE.hashCode();
		return Color.WHITE.hashCode();
	}
	
	public static enum MetaBlock implements IStringSerializable
	{
		Sub1(0, "sub1"),
		Main(1,"main"),
		Sub2(2, "sub2");
		
		private int id;
		private String name;
		
		private MetaBlock(int id, String name)
		{
			this.id = id;
			this.name = name;
		}
		
		public static MetaBlock getFromMeta(int meta)
		{
			for(MetaBlock type : values())
				if(type.getID() == meta)
					return type;
			return MetaBlock.Sub1;
		}

		@Override
		public String getName()
		{
			return name;
		}
		
		public int getID()
		{
			return id;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return meta == 1 ? new TileEntityCrystal() : null;
	}
}