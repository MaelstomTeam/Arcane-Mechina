package com.maelstrom.arcanemechina.common.block;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;

import org.apache.commons.lang3.RandomUtils;

import com.maelstrom.arcanemechina.api.grinding.ProcessingList;
import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityMortar;
import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMortar extends Block implements ITileEntityProvider, IHasName {
	
	private static final String[] names = new String[] {"stone", "granite","diorite","andesite","marble"};
	private static final PropertyInteger meta = PropertyInteger.create("stone_type", 0, names.length);
	
	public BlockMortar() {
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(meta, 0));
	}
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
    	TileEntity ent = world.getTileEntity(pos);
    	if(ent != null && ent instanceof IInventory)
    	{
    		for(int i = 0; i < ((IInventory)ent).getSizeInventory(); i++)
    		{
    			ItemStack temp = ((IInventory)ent).getStackInSlot(i);
    			if (temp != ItemStack.EMPTY)
    				drops.add(temp);
    		}
    	}
        drops.add(new ItemStack(Item.getItemFromBlock(this), 1, this.damageDropped(state)));
    }
    @Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMortar();
	}
	
    public int getMetaFromState(IBlockState state)
    {
        if (state.getPropertyKeys().isEmpty())
        {
            return 0;
        }
        else
        {
            return state.getValue(meta).intValue();
        }
    }
	
    public IBlockState getStateFromMeta(int value)
    {
    	return this.getDefaultState().withProperty(meta, value);
    }
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
			for(int i = 0; i < names.length; i++)
				items.add(new ItemStack(this, 1, i));
	}
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {meta});
    }
    
    public int damageDropped(IBlockState state)
    {
    	return state.getValue(meta).intValue();
    }
    
	@Override
	public String getNameFromMeta(int meta)
	{
		if(meta >=0 && meta < names.length)
			return names[meta];
		return "unknown";
	}
    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }
	
	
	
	
	
	
	

	
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	TileEntityMortar tile = (TileEntityMortar) worldIn.getTileEntity(pos);
    	if(tile != null)
		{
    		if(playerIn.isSneaking())
	    	{
    			if(playerIn.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY && tile.input != ItemStack.EMPTY)
		    	{
    				tile.forceUpdate();
		    		playerIn.setHeldItem(EnumHand.MAIN_HAND, tile.removeStackFromSlot(0));
		    		return true;
		    	}
	    	}
    		else {
				if(playerIn.getHeldItem(EnumHand.MAIN_HAND).getItem() == ItemList.pestle)
		    	{
		    		if(tile.canProcess())
		    		{
		    			playerIn.getHeldItem(EnumHand.MAIN_HAND).damageItem(1, playerIn);
		    			if(tile.output.getCount() >= tile.output.getMaxStackSize())return false;
		    			if(playerIn.isCreative())
		    			{
		    				tile.procTime = 100;
			    			tile.process(RANDOM);
		    			}
		    			else
		    				tile.process(RANDOM);
		    			while(RandomUtils.nextInt(0, 10)>3)
							worldIn.spawnParticle(EnumParticleTypes.CRIT, 
									pos.getX()+hitX, 
									pos.getY()+hitY, 
									pos.getZ()+hitZ, 
									RandomUtils.nextFloat(0,0.1f)-0.05f,
									.1,
									RandomUtils.nextFloat(0,0.1f)-0.05f);
		    			tile.forceUpdate();
			    		return true;
		    		}
		    	}
				else if(ProcessingList.containsInput(playerIn.getHeldItem(EnumHand.MAIN_HAND)) && tile.input == ItemStack.EMPTY)
		    	{
					if(tile.canInsertItem(0, playerIn.getHeldItem(EnumHand.MAIN_HAND), EnumFacing.UP))
					{
						tile.setInventorySlotContents(0, playerIn.getHeldItem(hand).copy());
			    		playerIn.setHeldItem(hand, ItemStack.EMPTY);
						tile.forceUpdate();
			    		return true;
					}
		    	}
		    	else if(/*playerIn.getHeldItem(EnumHand.MAIN_HAND) == ItemStack.EMPTY && */tile.output != ItemStack.EMPTY)
		    	{
		    		if(tile.output.getCount() > tile.output.getMaxStackSize())
		    		{
		    			playerIn.setHeldItem(EnumHand.MAIN_HAND, tile.decrStackSize(0, tile.output.getMaxStackSize()));
		    		}
		    		else
		    		{
			    		playerIn.setHeldItem(EnumHand.MAIN_HAND, tile.removeStackFromSlot(1));
		    		}
					tile.forceUpdate();
		    		return true;
		    	}
	    	}
		}
        return false;
    }

	static final double one_sixteenth = 0.0625;
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
    	return new AxisAlignedBB(one_sixteenth, 0.0, one_sixteenth, 1-one_sixteenth, one_sixteenth*9, 1-one_sixteenth);
	}
    @SuppressWarnings("deprecation")
	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(one_sixteenth,one_sixteenth,one_sixteenth, 1-one_sixteenth, one_sixteenth, 1-one_sixteenth));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(one_sixteenth*2,0.0,one_sixteenth*2, 1-(one_sixteenth*2), one_sixteenth, 1-(one_sixteenth*2)));
        
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(one_sixteenth,one_sixteenth,one_sixteenth, one_sixteenth*2, one_sixteenth*9, 1-one_sixteenth));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(one_sixteenth,one_sixteenth,one_sixteenth, 1-one_sixteenth, one_sixteenth*9, one_sixteenth*2));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1-one_sixteenth*2,one_sixteenth,1-one_sixteenth*2, one_sixteenth*2, one_sixteenth*9, 1-one_sixteenth));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1-one_sixteenth*2,one_sixteenth,1-one_sixteenth*2, one_sixteenth*2, one_sixteenth*9, 1-one_sixteenth));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(1-one_sixteenth*2,one_sixteenth,1-one_sixteenth*2, 1-one_sixteenth, one_sixteenth*9, one_sixteenth*2));
    }
}