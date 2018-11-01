package com.maelstrom.arcanemechina.common.items;

import java.util.List;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.api.book.Book;
import com.maelstrom.arcanemechina.api.book.Library;
import com.maelstrom.arcanemechina.client.gui.GuiBook;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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

public class ItemHelpBook extends Item{

	public ItemHelpBook()
	{
		this.setMaxDamage(0);
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
			String bookName = playerIn.getHeldItem(handIn).getTagCompound().getString("book_id");
			if(worldIn.isRemote) {
				Book book = Library.getBook(bookName);
				if(book == null)
				{
					playerIn.setHeldItem(handIn, new ItemStack(Items.BOOK));
					return super.onItemRightClick(worldIn, playerIn, handIn);
				}
				if(GuiBook.book == book)
				{
					if(GuiBook.book.firstTimeOpening)
					{
						GuiBook.book.firstTimeOpening = false;
						GuiBook.book.page = GuiBook.book.homePage;
					}
					FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
				}
				else
				{
					GuiBook.book = book;
					if(GuiBook.book.firstTimeOpening)
					{
						GuiBook.book.firstTimeOpening = false;
						GuiBook.book.page = GuiBook.book.homePage;
					}
					FMLCommonHandler.instance().showGuiScreen(GuiBook.INSTANCE);
				}
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
    	if(stack.getTagCompound() == null)
    		return super.getUnlocalizedName() + ".unknown";
        return super.getUnlocalizedName() + "." + stack.getTagCompound().getString("book_id");
    }
    

    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
            for (int i = Library.getLibrary().length; i > 0; --i)
            {
            	ItemStack is = new ItemStack(this, 1);
            	NBTTagCompound nbt = new NBTTagCompound();
            	nbt.setString("book_id", Library.getLibrary()[i-1].title);
            	is.setTagCompound(nbt);
                items.add(is);
            }
        }
    }
    

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return super.hasEffect(stack);
    }

}
