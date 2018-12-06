package com.maelstrom.arcanemechina.common.items;

import org.jline.utils.Log;

import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MetaItemBlock extends ItemBlock {

	public MetaItemBlock(Block block) {
		super(block);
		this.hasSubtypes = true;
	}

    public String getUnlocalizedName(ItemStack stack)
    {
        return this.block.getUnlocalizedName() + "." + stack.getMetadata();
    }
    
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState){
    	newState = block.getStateFromMeta(stack.getItemDamage());
    	return super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState);
    }
    
    public static class MetaItemWithSubNames extends MetaItemBlock implements IHasName
    {
    	IHasName ref;
    	
		public MetaItemWithSubNames(Block block) throws Exception {
			super(block);
			if(block instanceof IHasName)
				ref = (IHasName)block;
			else
				throw new Exception("block not instance of IHasName, HECKEN PANIC!!");
		}

		@Override
		public String getNameFromMeta(int meta) {
			return ref.getNameFromMeta(meta);
		}


	    public String getUnlocalizedName(ItemStack stack)
	    {
	        return this.block.getUnlocalizedName() + "." + ref.getNameFromMeta(stack.getItemDamage());
	    }
    	
    }

}
