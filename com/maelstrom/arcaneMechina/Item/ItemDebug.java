package com.maelstrom.arcaneMechina.Item;

import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
import baubles.api.BaubleType;
import baubles.api.IBauble;

import com.maelstrom.arcaneMechina.client.model.ModelGhostWings;
import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.reference.Reference;
import com.maelstrom.snowcone.extendables.ExtendableItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDebug extends ExtendableItem {
	
	private IIcon icon;
	
	public ItemDebug(String name) {
		super(name, Reference.MOD_ID);
	}
	
    public void addInformation(ItemStack is, EntityPlayer ply, List l, boolean bool) {
    	//#really bad elvish
    	l.add("§2"+StatCollector.translateToLocal(getUnlocalizedName() + ".lore"));
    }
    
    public boolean onItemUse(ItemStack is, EntityPlayer ply, World w, int x, int y, int z, int face, float xFloat, float yFloat, float zFloat)
    {
        return false;
    }
    
    public ItemStack onItemRightClick(ItemStack is, World w, EntityPlayer ply)
    {
        return is;
    }
}