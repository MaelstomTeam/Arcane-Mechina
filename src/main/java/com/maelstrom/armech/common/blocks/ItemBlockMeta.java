package com.maelstrom.armech.common.blocks;

import java.awt.Color;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.maelstrom.snowcone.IMetaBlockName;

public class ItemBlockMeta extends ItemBlock {

	public ItemBlockMeta(Block block) {
		super(block);
        if (!(block instanceof IMetaBlockName)) {
            throw new IllegalArgumentException(String.format("The given Block %s is not an instance of ISpecialBlockName!", block.getUnlocalizedName()));
        }
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public int getMetadata(int damage)
    {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);// + "." + ((IMetaBlockName)this.block).getSpecialName(stack);
    }
	
	@Override
	public void addInformation(ItemStack item_stack, EntityPlayer player, List subtext, boolean bool)
	{
		if(item_stack.getItemDamage() >= 6)
			subtext.add(StatCollector.translateToLocal("dust_ore.mixed") + " of " + StatCollector.translateToLocal("dust_type."+BlockDustOre.EnumType.getType(item_stack.getItemDamage()).getName()));
		else
			subtext.add(StatCollector.translateToLocal("dust_ore.pure") + " of " + StatCollector.translateToLocal("dust_type."+BlockDustOre.EnumType.getType(item_stack.getItemDamage()).getName()));
	}
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass)
	{
		if (renderPass > 0) {
			return 16777215;
		}
		if(stack.getItemDamage() == 1)
			return Color.GREEN.hashCode();
		else if(stack.getItemDamage() == 2)
			return Color.RED.hashCode();
		else if(stack.getItemDamage() == 3)
			return Color.BLUE.hashCode();
		else if(stack.getItemDamage() == 4)
			return Color.BLACK.hashCode();
		else if(stack.getItemDamage() == 5)
			return Color.WHITE.hashCode();
		else if(stack.getItemDamage() == 6)
			return new Color(255,255,144).hashCode();
		else if(stack.getItemDamage() == 7)
			return new Color(144,255,144).hashCode();
		else if(stack.getItemDamage() == 8)
			return new Color(255,144,144).hashCode();
		else if(stack.getItemDamage() == 9)
			return new Color(144,144,255).hashCode();
		else if(stack.getItemDamage() == 10)
			return new Color(144,144,0).hashCode();
		else if(stack.getItemDamage() == 11)
			return new Color(0,144,0).hashCode();
		else if(stack.getItemDamage() == 12)
			return new Color(144,0,0).hashCode();
		else if(stack.getItemDamage() == 13)
			return new Color(0,0,144).hashCode();
		else
			return Color.yellow.hashCode();
	}

}
