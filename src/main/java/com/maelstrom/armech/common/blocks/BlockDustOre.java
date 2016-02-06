package com.maelstrom.armech.common.blocks;

import java.awt.Color;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.armech.ArMechMain;
import com.maelstrom.armech.common.AMItems;
import com.maelstrom.armech.common.Reference;
import com.maelstrom.snowcone.IMetaBlockName;

public class BlockDustOre extends Block implements IMetaBlockName {

	public static final PropertyEnum TYPE = PropertyEnum.create("type",	BlockDustOre.EnumType.class);
	
	public BlockDustOre()
	{
		super(Material.rock);
		setUnlocalizedName("dust_ore");
		setCreativeTab(ArMechMain.tab_armech_dust);
		setHardness(1f);
		setResistance(1f);
	    setHarvestLevel("pickaxe", 1);
		setDefaultState(blockState.getBaseState().withProperty(TYPE, EnumType.AIR));
	}
	
	@Override
	public BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[]{TYPE});
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(TYPE, EnumType.getType(meta));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		if(getMetaFromState(state) < EnumType.length())
			return AMItems.dust_crystal;
		return super.getItemDropped(state, rand, fortune); 
	}
	
	@Override
	public int getMetaFromState(IBlockState state)
	{
		EnumType type = (EnumType) state.getValue(TYPE);
		return type.getID();
	}
	
	@Override
	public int damageDropped(IBlockState state)
	{
		return getMetaFromState(state);
	}
	
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.CUTOUT_MIPPED;
	}
	
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list)
	{
		for(int i = 0; i < EnumType.length(); i++)
			list.add(new ItemStack(item,1,i));
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return EnumType.getType(stack.getItemDamage()).getName();
	}
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition targe, World world, BlockPos pos)
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, this.getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public int quantityDroppedWithBonus(int fortune, Random random)
	{
			return random.nextInt(2 + fortune) + 1;
//		return 1;
	}
	
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass)
	{
		if(getMetaFromState(world.getBlockState(pos)) == 1)
			return Color.GREEN.hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 2)
			return Color.RED.hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 3)
			return Color.BLUE.hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 4)
			return Color.darkGray.hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 5)
			return new Color(255,255,255).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 6)
			return new Color(255,255,144).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 7)
			return new Color(144,255,144).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 8)
			return new Color(255,144,144).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 9)
			return new Color(144,144,255).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 10)
			return new Color(144,144,0).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 11)
			return new Color(0,144,0).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 12)
			return new Color(144,0,0).hashCode();
		else if(getMetaFromState(world.getBlockState(pos)) == 13)
			return new Color(0,0,144).hashCode();
		else
			return Color.yellow.hashCode();
	}
	
	public enum EnumType implements IStringSerializable {

		AIR(0, "air"),
		EARTH(1, "earth"),
		FIRE(2, "fire"),
		WATER(3, "water"),
		DARK(4, "dark"),
		LIGHT(5, "light"),
		//sub dark/light varients
		AIR_LIGHT(6, "bright_air"),
		EARTH_LIGHT(7, "bright_earth"),
		FIRE_LIGHT(8, "bright_fire"),
		WATER_LIGHT(9, "bright_water"),
		AIR_DARK(10, "dark_air"),
		EARTH_DARK(11, "dark_earth"),
		FIRE_DARK(12, "dark_fire"),
		WATER_DARK(13, "dark_water");
		
		private int id;
		private String name;
		private static int length = 14;
		public static int length()
		{
			return length;
		}
		private EnumType(int id, String name)
		{
			this.id = id;
			this.name = name;
		}
		
		public static EnumType getType(int meta)
		{
			for(EnumType type : values())
				if(type.getID() == meta)
					return type;
			return EnumType.AIR;
		}
		static String[] allVariants;
		public static String[] allToString()
		{
			if(allVariants ==null)
			{
				allVariants = new String[length()];
				for(int i = 0; i < length(); i++)
					allVariants[i] = Reference.MODID + ":dust_ore_" + getType(i).getName();
			}
			return allVariants;
		}

		@Override
		public String getName() {
			return name;
		}
		
		public int getID() {
			return id;
		}
		
		@Override
		public String toString(){
			return getName();
		}

	}

}
