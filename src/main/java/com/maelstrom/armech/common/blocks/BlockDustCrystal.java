package com.maelstrom.armech.common.blocks;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import scala.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import com.maelstrom.armech.common.ConfigurationArcaneMechina;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.armech.common.pleasesortthis.WorldAccess;
import com.maelstrom.armech.common.registry.AMItems;
import com.maelstrom.armech.common.tileentity.clientonly.TileEntityWorldGenCrystal;

@SuppressWarnings("all")
public class BlockDustCrystal extends Block implements ITileEntityProvider
{
	public enum Facing implements IStringSerializable
	{
		UP(0, "UP"), DOWN(1, "DOWN"), NORTH(2, "NORTH"), SOUTH(3, "SOUTH"), WEST(4, "WEST"), EAST(5, "EAST");

		private int id;
		private String name;
		private static int length = 14;

		public static int length()
		{
			return length;
		}

		private Facing(int id, String name)
		{
			this.id = id;
			this.name = name;
		}

		public static Facing getType(int meta)
		{
			for (Facing type : values())
				if (type.getID() == meta)
					return type;
			return Facing.UP;
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

		@Override
		public String toString()
		{
			return getName();
		}
	}

	public static final PropertyEnum DIRECTION = PropertyEnum.create("facing", BlockDustCrystal.Facing.class);

	public BlockDustCrystal()
	{
		super(Material.glass);
		this.setUnlocalizedName("dust_ore");
		this.setLightLevel(1f/15f*4f);
		this.setResistance(10f);
		this.setHardness(3f);
		this.setHarvestLevel("pickaxe", 1);
		this.setStepSound(soundTypeGlass);
	}

	protected boolean canSilkHarvest()
	{
		return false;
	}
	
	@Override
	public BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] { DIRECTION });
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DIRECTION, Facing.getType(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		Facing type = (Facing) state.getValue(DIRECTION);
		return type.getID();
	}

	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing face, float xf, float yf, float zf, int something, EntityLivingBase entity)
	{
		this.setBlockBounds(.25F, .25F, .25F, .75F, .75F, .75F);
		return getStateFromMeta(face.getIndex());
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

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return world.isRemote ? new TileEntityWorldGenCrystal(meta) : null;
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

	
	public void setBlockBoundsBasedOnState(IBlockAccess world, BlockPos pos)
	{
		switch((Facing) world.getBlockState(pos).getValue(DIRECTION))
		{
		case UP: { this.setBlockBounds(.25F, .25F, .25F, .75F, 1F, .75F); break;}
		case DOWN:  { this.setBlockBounds(.25F, 0F, .25F, .75F, .75F, .75F); break;}
		case EAST:  { this.setBlockBounds(0F, .25F, .25F, .75F, .75F, .75F); break;}
		case NORTH: { this.setBlockBounds(.25F, .25F, .25F, .75F, .75F, 1F); break;}
		case SOUTH: { this.setBlockBounds(.25F, .25F, 0F, .75F, .75F, .75F); break;}
		case WEST: { this.setBlockBounds(.25F, .25F, .25F, 1F, .75F, .75F); break;}
		}
	}
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {

		switch((Facing) state.getValue(DIRECTION))
		{
		case UP: return AxisAlignedBB.fromBounds(pos.getX() + .25, pos.getY() + .25, pos.getZ() + .25, pos.getX() + .75, pos.getY() + 1, pos.getZ() + .75);
		case DOWN:  return AxisAlignedBB.fromBounds(pos.getX() + .25, pos.getY(), pos.getZ() + .25, pos.getX() + .75, pos.getY() + .75, pos.getZ() + .75);
		case EAST: return AxisAlignedBB.fromBounds(pos.getX(), pos.getY() + .25, pos.getZ() + .25, pos.getX() + .75, pos.getY() + .75, pos.getZ() + .75);
		case NORTH: return AxisAlignedBB.fromBounds(pos.getX() + .25, pos.getY() + .25, pos.getZ() + .25, pos.getX() + .75, pos.getY() + .75, pos.getZ() + 1);
		case SOUTH: return AxisAlignedBB.fromBounds(pos.getX() + .25, pos.getY() + .25, pos.getZ(), pos.getX() + .75, pos.getY() + .75, pos.getZ() + .75);
		case WEST: return AxisAlignedBB.fromBounds(pos.getX() + .25, pos.getY() + .25, pos.getZ() + .25, pos.getX() + 1, pos.getY() + .75, pos.getZ() + .75);
		}
		return AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
	}
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if(entity.hurtResistantTime == 0 && !(entity instanceof EntityItem))
			entity.attackEntityFrom(Reference.CustomDamageTypes.shards, 1f);
	}
	
	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
	    List<ItemStack> ret = new ArrayList();
	    Random rand = new Random();
	    int countdown = rand.nextInt(3) + 2 + fortune;
	    while(countdown-- > 0)
	    {
	    	int test = rand.nextInt(Reference.dustNames.length);
	    	while(test < 6 || test == 19 || test > 20)
	    		test = rand.nextInt(Reference.dustNames.length);
	    	ret.add(new ItemStack(AMItems.dust_crystal,1,test));
	    }
	    if(ConfigurationArcaneMechina.pureDrops && rand.nextInt(ConfigurationArcaneMechina.pureDropChance) == 13)
	    	ret.add(new ItemStack(AMItems.dust_crystal,1,rand.nextInt(6)));
		return ret;
	}
	
	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock) {
		EnumFacing direction = EnumFacing.UP;
		for(EnumFacing face : EnumFacing.VALUES)
			if(face.getIndex() == getMetaFromState(state))
				direction = face.getOpposite();
		if (!OreDictionary.containsMatch(false, OreDictionary.getOres("stone"), new ItemStack(WorldAccess.getBlockFromPosFace(worldIn, pos, direction).getBlock())))
			worldIn.destroyBlock(pos, ConfigurationArcaneMechina.dropItemsIfPlacementIsInvalid);
	}
	
	@Override
	public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
		return OreDictionary.containsMatch(false, OreDictionary.getOres("stone"), new ItemStack(world.getBlockState(WorldAccess.getFaceOffset(pos, side.getOpposite())).getBlock()));
	}
}
