package com.maelstrom.armech.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
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
import com.maelstrom.armech.common.tileentity.OLD_TileEntityCrystal;

@SuppressWarnings("all")
public class BlockPowerStorageCrystal extends Block implements ITileEntityProvider
{

	public static final PropertyEnum TYPE = PropertyEnum.create("type", MetaBlock.class);
	
	public BlockPowerStorageCrystal()
	{
		super(Material.glass);
		setUnlocalizedName("multiblock.powercrystal");
		setCreativeTab(ArMechMain.tab_armech);
		setHardness(1f);
		setResistance(1f);
//	    setHarvestLevel("pickaxe", 1); //re-enable later maybe
		setBlockBounds(.2f, 0f, .2f, .8f, 1f, .8f);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, MetaBlock.Main));
	}

	public boolean canBeReplacedByLeaves(IBlockAccess world, BlockPos pos)
	{
		return false;
	}

	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumFacing face, float x, float y, float z)
	{
		OLD_TileEntityCrystal tile = ((OLD_TileEntityCrystal) world.getTileEntity(pos));
		if(!world.isRemote)
		if(tile != null)
		{
			if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.diamond)
				tile.setEnergy(Integer.MAX_VALUE);
			else if(player.getHeldItem() != null && player.getHeldItem().getItem() == Items.iron_ingot)
				tile.setEnergy(0);
			else
				System.out.println(tile.getEnergy());
			return true;
		}
		else
		{
			if(((OLD_TileEntityCrystal) world.getTileEntity(pos.up())) != null)
			{
				onBlockActivated(world, pos.up(), state, player, face, x, y, z);
				return true;
			}
			else if(((OLD_TileEntityCrystal) world.getTileEntity(pos.down())) != null)
			{
				this.onBlockActivated(world, pos.down(), state, player, face, x, y, z);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, BlockPos pos) {
		if(world.isAirBlock(pos.up()) && world.isAirBlock(pos.up().up()))
			return true;
		return false;
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing face, float xf, float yf, float zf, int meta, EntityLivingBase entityPlacer)
	{
		world.setBlockState(pos.up().up(), getStateFromMeta(0));
		world.setBlockState(pos.up(), getStateFromMeta(1));
		return getStateFromMeta(0);
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		if(getMetaFromState(state) == 0)
		{
			if(world.getBlockState(pos.up()).getBlock() == this && this.getMetaFromState(world.getBlockState(pos.up())) == 1)
			{
				world.setBlockToAir(pos.up());
				world.setBlockToAir(pos.up().up());
			}
			else if(world.getBlockState(pos.down()).getBlock() == this && this.getMetaFromState(world.getBlockState(pos.down())) == 1)
			{
				world.setBlockToAir(pos.down().down());
				world.setBlockToAir(pos.down());
			}
		}
		else if(getMetaFromState(state) == 1)
		{
			world.setBlockToAir(pos.up());
			world.setBlockToAir(pos.down());
		}
		
	}
	  
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox( World world, BlockPos pos) {

		if(this.getMetaFromState(world.getBlockState(pos)) == 0)
		{
			if(world.getBlockState(pos.up()).getBlock() == this && this.getMetaFromState(world.getBlockState(pos.up())) == 1)
				return AxisAlignedBB.fromBounds(pos.getX() + .2f, pos.getY() + .005f, pos.getZ() + .2f, pos.getX() + .8f, pos.getY() + 2.995f, pos.getZ() + .8f);
			else
				return AxisAlignedBB.fromBounds(pos.getX() + .2f, pos.getY() - 1.995f, pos.getZ() + .2f, pos.getX() + .8f, pos.getY() + .995f, pos.getZ() + .8f);
		}
		else if(this.getMetaFromState(world.getBlockState(pos)) == 1)
		{
			return AxisAlignedBB.fromBounds(pos.getX() + .2f, pos.getY() - .995f, pos.getZ() + .2f, pos.getX() + .8f, pos.getY() + 1.995f, pos.getZ() + .8f);
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
		return 0x7DF9FF;
	}
	
	public static enum MetaBlock implements IStringSerializable
	{
		Sub1(0, "sub1"),
		Main(1,"main");
		
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
		return meta == 1 ? new OLD_TileEntityCrystal() : null;
	}
}