package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.client.gui.GuiBook;
import com.maelstrom.arcanemechina.client.gui.GuiBookIndex;
import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemHelpBook extends Item implements IHasName {

    public String[] nameList = {"basic", "intermediate", "advanced", "cheat"};
	public ItemHelpBook()
	{
		this.setMaxDamage(0);
		this.setHasSubtypes(true);
		this.setMaxStackSize(1);
	}
	
	@Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
		tooltip.add("by Maelstrom");
    }
	
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
    
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
		if (!playerIn.isSneaking())
		{
			if(worldIn.isRemote) {
				FMLCommonHandler.instance().showGuiScreen(GuiBookIndex.INSTANCE);
				//playerIn.openGui(ArcaneMechina.INSTANCE, 0, worldIn, 0, 0, 0);
				GuiBook.defaultBackground = 1;
			}
		}
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
    

    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn)
    {
    	//register owner
    }

    public String getUnlocalizedName(ItemStack stack)
    {
        return super.getUnlocalizedName() + "." + getNameFromMeta(stack.getItemDamage());
    }
    

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = 0; i < nameList.length; ++i)
            {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }
    

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getItemDamage() == 3 || super.hasEffect(stack);
    }
    
    
	@Override
	public String getNameFromMeta(int meta) {
		return nameList[meta];
	}

}
