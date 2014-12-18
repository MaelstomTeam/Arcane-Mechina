package com.maelstrom.arcaneMechina.item;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.event.RenderPlayerEvent.Specials.Post;
import baubles.api.BaubleType;

import com.maelstrom.arcaneMechina.interfaces.IBaubleRenderer;
import com.maelstrom.arcaneMechina.reference.Reference;

public class ItemRosarioAmulet extends ItemBaubleEx implements IBaubleRenderer {

	private boolean isEquiped = false;

	public ItemRosarioAmulet(String name) {
		super(name, Reference.MOD_ID);
	}

	public void addInformation(ItemStack is, EntityPlayer ply, List l,
			boolean bool) {
		if (Reference.isContributor(ply.getDisplayName())) {
			l.add("§2" + StatCollector.translateToLocal(getUnlocalizedName() + ".line1.lore"));
			l.add("§2" + StatCollector.translateToLocal(getUnlocalizedName() + ".line2.lore"));
		}
		else if (isEquiped)
				l.add("§4" + StatCollector.translateToLocal(getUnlocalizedName() + ".line3.lore"));
	}

	@Override
	public void onPlayerBaubleRenderer(EntityPlayer player, Post event) {
	}

	@Override
	public RenderLocation getRenderLocation() {
		return RenderLocation.FLOOR;
	}

	@Override
	public BaubleType getBaubleType(ItemStack itemstack) {
		return BaubleType.AMULET;
	}

	@Override
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
		super.onEquipped(itemstack, player);
		isEquiped = true;
	}

	@Override
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
		super.onUnequipped(itemstack, player);
		isEquiped = false;
	}

	@Override
	public void onWornTick(ItemStack is, EntityLivingBase player) {
		if(player instanceof EntityPlayer){
			EntityPlayer ply = (EntityPlayer) player;
			if(!Reference.isContributor(ply.getDisplayName()));
		        if (player.worldObj.isDaytime() && !player.worldObj.isRemote){
		            float f = player.getBrightness(1.0F);
		            if (f > 0.5F && player.worldObj.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && player.worldObj.canBlockSeeTheSky(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY), MathHelper.floor_double(player.posZ))){
		                boolean setFire = true;
		                ItemStack itemstack = player.getEquipmentInSlot(4);
		                if (itemstack != null){
		                    if (itemstack.isItemStackDamageable()){
		                        itemstack.setItemDamage(itemstack.getItemDamageForDisplay() + player.worldObj.rand.nextInt(2));
		
		                        if (itemstack.getItemDamageForDisplay() >= itemstack.getMaxDamage()){
		                        	player.renderBrokenItemStack(itemstack);
		                        	player.setCurrentItemOrArmor(4, (ItemStack)null);
		                        }
		                    }
		
		                    setFire = false;
		                }
		
		                if (setFire)
		                	player.setFire(8);
		            }
		        }
		}
	}

	@Override
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}

	@Override
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
		return true;
	}
}
