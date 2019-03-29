package com.maelstrom.arcanemechina.common.items;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.common.block.BlockList;
import com.maelstrom.arcanemechina.common.registry.RuneRegistry;
import com.maelstrom.arcanemechina.common.registry.RuneRegistry.Array;
import com.maelstrom.arcanemechina.common.registry.RuneRegistry.BlockData;
import com.maelstrom.snowcone.util.Development;
import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemChalk extends Item implements IHasName
{
	public ItemChalk()
	{
		super();
		this.setMaxDamage(0);
		this.setMaxStackSize(1);
		this.setHasSubtypes(true);
	}
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	if(Development.isDevEnviroment() && player.isSneaking())
    	{
			Array array = new Array();
			array.data = new ArrayList<BlockData>();
			array.ID = "NAME_HERE";
    		for(int x = -32; x < 32; x++)
    		{
    			for(int z = -32; z < 32; z++)
    			{
    				IBlockState state = world.getBlockState(pos.add(x, 0, z));
    				if(state.getBlock() == BlockList.Rune)
    				{
    					BlockData data = new BlockData();
    					data.META = BlockList.Rune.getMetaFromState(state);
    					data.X = x;
    					data.Z = z;
        				array.data.add(data);	
    				}
    			}
    		}
			RuneRegistry.createArray(array);
    		return EnumActionResult.SUCCESS;
    	}
    	if(world.getBlockState(pos).getBlock() == BlockList.Rune)
    	{
    		Array array = RuneRegistry.checkRune(world, pos);
    		if(array != null)
    		{
				array.run(world, pos, player);
        		return EnumActionResult.SUCCESS;
    		}
    	}
    	if(BlockList.Rune.canPlaceBlockAt(world, pos.offset(facing, 1)))
    	{
    		world.setBlockState(pos.offset(facing, 1), BlockList.Rune.getStateFromMeta(player.getHeldItem(hand).getItemDamage()));
    		return EnumActionResult.SUCCESS;
    	}
    	else
    		return EnumActionResult.FAIL;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    	ItemStack is = playerIn.getHeldItem(handIn);
    	if(playerIn.isSneaking())
    	{
    		is.setItemDamage(is.getItemDamage() + 1);
    		if (is.getItemDamage() > 3)
        		is.setItemDamage(0);
    	}
    	else
    	{
    		if(is.getItemDamage() == 0)
        		is.setItemDamage(3);
    		else
    			is.setItemDamage(is.getItemDamage() - 1);
    	}
    	
        return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, is);
    }
    
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.NONE;
    }
    

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < 4; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
    
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		switch(stack.getItemDamage())
		{
			case 0: tooltip.add((char)167+"6Runic"); break;
			case 1: tooltip.add((char)167+"cValor"); break;
			case 2: tooltip.add((char)167+"9Ethereal"); break;
			case 3: tooltip.add((char)167+"5Oblivious"); break;
		}
    }


    public String getTranslationKey(ItemStack stack)
    {
        return super.getTranslationKey(stack) + "." + getNameFromMeta(stack.getItemDamage());
    }
	
    public boolean showDurabilityBar(ItemStack stack)
    {
        return false;
    }
    
	@Override
	public String getNameFromMeta(int meta) {
		switch(meta)
		{
		default:
		case 0: return "Runic";
		case 1: return "Valor";
		case 2: return "Etheral";
		case 3: return "Daedric";
		}
	}
}
