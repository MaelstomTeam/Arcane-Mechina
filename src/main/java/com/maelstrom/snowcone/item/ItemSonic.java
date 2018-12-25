package com.maelstrom.snowcone.item;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.brandon3055.draconicevolution.api.ICrystalBinder;
import com.maelstrom.snowcone.SC_Registry;
import com.maelstrom.snowcone.block.IItemColored;
import com.maelstrom.snowcone.client.BasicColorHandler;
import com.maelstrom.snowcone.client.SonicColorHandler;
import com.maelstrom.snowcone.item.sonic.SonicInventory;
import com.maelstrom.snowcone.util.Development;

import cofh.api.item.IToolHammer;
import crazypants.enderio.api.tool.ITool;
import mcjty.lib.api.smartwrench.SmartWrench;
import mcjty.lib.api.smartwrench.SmartWrenchMode;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Optional.InterfaceList({
	@Interface(iface = "mcjty.lib.api.smartwrench.SmartWrench", modid = "mcjtylib_ng"),
	@Interface(iface = "crazypants.enderio.api.tool.ITool", modid = "enderio"),
	@Interface(iface = "cofh.api.item.IToolHammer", modid = "cofhcore"),
	@Interface(iface = "cofh.api.item.IToolHammer", modid = "draconicevolution"),


})
public class ItemSonic extends Item implements IItemColored, SmartWrench, ITool, IToolHammer, ICrystalBinder
{
	
	public ItemSonic()
	{
		setMaxStackSize(0);
		setMaxDamage(0);
	}

    public String getHighlightTip( ItemStack item, String displayName )
    {
    	SonicInventory inventory = SonicInventory.getInventory(item);
    	ItemStack curr = inventory.getCurrentItem();
    	if(curr != null)
    		return displayName + " ("+(char)167+"2" + curr.getDisplayName() +(char)167+"r)";
    	return displayName;
    }
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand)
    {
    	SonicInventory inventory = SonicInventory.getInventory(player.getHeldItem(hand));
    	ItemStack itemBackup = player.getHeldItem(hand).copy();
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null)
    	{
    		player.setHeldItem(hand, item);
    		EnumActionResult result = item.onItemUseFirst(player, world, pos, hand, side, hitZ, hitZ, hitZ);
    		inventory.changeCurrentItem(item);
    		itemBackup.getTagCompound().setTag("ItemInventory", inventory.getCompound());
    		player.setHeldItem(hand, itemBackup);
    		return result;
    	}
        return EnumActionResult.FAIL;
    }
    public boolean onBlockStartBreak( ItemStack itemstack, BlockPos pos, EntityPlayer player) {
    	SonicInventory inventory = SonicInventory.getInventory(itemstack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().onBlockStartBreak(item, pos, player);
        return super.onBlockStartBreak(itemstack, pos, player);
    }
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player){
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().canDestroyBlockInCreative(world, pos, item, player);
		
        return super.canDestroyBlockInCreative(world, pos, stack, player);
	}
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
    	SonicInventory inventory = SonicInventory.getInventory(player.getHeldItem(hand));
    	ItemStack itemBackup = player.getHeldItem(hand).copy();
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null)
    	{
    		player.setHeldItem(hand, item);
    		EnumActionResult result = item.onItemUse(player, worldIn, pos, hand, facing, hitZ, hitZ, hitZ);
    		inventory.changeCurrentItem(item);
    		itemBackup.getTagCompound().setTag("ItemInventory", inventory.getCompound());
    		player.setHeldItem(hand, itemBackup);
    		return result;
    	}
        return EnumActionResult.FAIL;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand)
    {
    	SonicInventory inventory = SonicInventory.getInventory(player.getHeldItem(hand));
    	ItemStack itemBackup = player.getHeldItem(hand).copy();
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null && !worldIn.isRemote)
    	{
    		player.setHeldItem(hand, item);
    		ActionResult<ItemStack> result = item.getItem().onItemRightClick(worldIn, player, hand);
    		inventory.changeCurrentItem(item);
    		inventory.writeData();
    		itemBackup.setTagInfo("ItemInventory", inventory.getCompound());
    		player.setHeldItem(hand, itemBackup);
            return new ActionResult<ItemStack>(result.getType(), itemBackup);
    	}
    	return super.onItemRightClick(worldIn, player, hand);
    }
    public int getMaxItemUseDuration(ItemStack stack)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null)
    		return item.getMaxItemUseDuration();
        return 0;
    }
    public void onCreated(ItemStack stack, World world, EntityPlayer player)
    {
    	SonicInventory sonic;
    	NBTTagCompound temp = new NBTTagCompound();
    	temp.setString("Owner", player.getDisplayNameString());
    	System.out.print(player.getDisplayNameString());
    	stack.setTagInfo("ItemInventory", temp);
    	
		sonic = SonicInventory.getInventory(stack);
    	
		//special items added here
    	ArrayList<ItemStack> list = new ArrayList<ItemStack>();
    	if(Development.isDevEnviroment())
    	{
    		SonicInventory.allItems(sonic, list);
    	}
    	else
    	{
    		list.add(new ItemStack(Items.BUCKET));
        	list.add(new ItemStack(Items.COMPASS));
        	list.add(new ItemStack(Items.CLOCK));
    		for(ItemStack i : list)
    			sonic.setInventorySlotContents(sonic.getSizeInventory(),i);
    	}
        sonic.setColors(0x8080FF, 0xAAAAAA);
    	sonic.writeData();
    	stack.setTagInfo("ItemInventory", sonic.getCompound());
    	if(!player.getEntityData().getBoolean("recievedBook"))
    	{
        	//spawn handbook!
    		player.getEntityData().setBoolean("recievedBook", true);
        	ItemStack book = new ItemStack(SC_Registry.HelpBook,1);
        	book.setTagInfo("book_id", new NBTTagString("sonicmultitool"));
        	if(!player.inventory.addItemStackToInventory(book))
        		player.entityDropItem(book, .5f);
    	}
    	//set players sonic uuid
    	if(!player.getEntityData().hasKey("sonic_UUID"))
    		player.getEntityData().setString("sonic_UUID", sonic.UUID);
    }

    public EnumAction getItemUseAction(ItemStack stack)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null)
    		return item.getItemUseAction();
        return EnumAction.NONE;
    }
    public void onPlayerStoppedUsing(ItemStack stack, World world, EntityLivingBase entityLiving, int timeLeft)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null)
    		item.onPlayerStoppedUsing(world, entityLiving, timeLeft);
    }
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
    	ItemStack item = inventory.getCurrentItem();
    	if(item != null)
    		item.getItem().onUsingTick(item, player, count);
    }
    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().onLeftClickEntity(item, player, entity);
        return false;
    }
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.doesSneakBypassUse(world, pos, player);
        return false;
    }
    public boolean showDurabilityBar(ItemStack stack)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().showDurabilityBar(item);
        return false;
    }

    public boolean isDamaged(ItemStack stack)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().isDamaged(item);
        return false;
    }
    public double getDurabilityForDisplay(ItemStack stack)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().getDurabilityForDisplay(item);
        return 0;
    }

    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
    	SonicInventory inventory = SonicInventory.getInventory(stack);
		ItemStack item = inventory.getCurrentItem();
		if(item != null)
			return item.getItem().getRGBDurabilityForDisplay(item);
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
    }
    
    public boolean getShareTag()
    {
        return true;
    }
    
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        if (this.isInCreativeTab(tab))
        {
        	ItemStack item = new ItemStack(this);
            {
            	SonicInventory inventory = SonicInventory.getInventory("00000000-0000-0000-0000-000000000000");
            	//inventory.receiveEnergy(Integer.MAX_VALUE / 2, false);
        		//special items added here
            	inventory.writeData();
            	item.setTagInfo("ItemInventory", inventory.getCompound());
        	}
            items.add(item);
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
    	SonicInventory inventory = new SonicInventory(stack);
    	tooltip.add(inventory.getSizeInventory() - 1 + " Tools internalized!");
    	tooltip.add(inventory.getOwner() + " Made this!");
    	if(inventory.getCurrentItem() != null)
    	{
    		tooltip.add("Currently Selected Tool: " + inventory.getCurrentItem().getDisplayName());
	    	List<String> temp = new ArrayList<String>();
	    	inventory.getCurrentItem().getItem().addInformation(inventory.getCurrentItem(), worldIn, temp, flagIn);
	    	if(temp != null)
	    		tooltip.addAll(temp);
    	}
    	
    	if(flagIn.isAdvanced() && stack.getTagCompound() != null)
    	{
        	//tooltip.add(inventory.getEnergyStored() + " / " + inventory.getMaxEnergyStored() + "FE");
    		tooltip.add((char)167+"4NBT DATA: ");
			tooltip.add((char)167+"4"+stack.getTagCompound().toString());
    	}
    	else
    	{
        	//tooltip.add((inventory.getEnergyStored() / (float)Integer.MAX_VALUE * 100f) + "%");
    	}
    }
    
	public BasicColorHandler getColorHandler()
	{
		return new SonicColorHandler();
	}
	
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
    	SonicInventory inv = new SonicInventory(stack);
    	for(int i = 0; i < inv.getSizeInventory() - 1; i++)
    	{
			//test value
			inv.getStackInSlot(i).getItem().onUpdate(inv.getStackInSlot(i), worldIn, entityIn, itemSlot, isSelected);
    	}
    	inv.writeData();
    	stack.setTagInfo("ItemInventory", inv.getCompound());
    }

    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt)
    {
    	SonicInventory.getInventory(stack).writeData();
    	stack.setTagInfo("ItemInventory",SonicInventory.getInventory(stack).getCompound());
        return SonicInventory.getInventory(stack);
    }
    
    
    //mcjtylib_ng

    @Optional.Method(modid = "mcjtylib_ng")
    @Override
    public SmartWrenchMode getMode(ItemStack itemStack)
    {
    	SonicInventory sonic = SonicInventory.getInventory(itemStack);
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return SmartWrenchMode.MODE_WRENCH;
    	if(curr.getItem() instanceof SmartWrench)
    	{
    		return ((SmartWrench)curr.getItem()).getMode(curr);
    	}
    	return SmartWrenchMode.MODE_WRENCH;
    }
    
    
    //enderio

    @Optional.Method(modid = "enderio")
	@Override
	public boolean shouldHideFacades(ItemStack stack, EntityPlayer player) {
    	SonicInventory sonic = SonicInventory.getInventory(stack);
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return false;
    	if(curr.getTranslationKey().equals("item.item_yeta_wrench"))
    		return true;
    	if(curr.getItem() instanceof ITool)
    	{
    		return ((ITool)curr.getItem()).shouldHideFacades(curr, player);
    	}
		return false;
	}

    @Optional.Method(modid = "enderio")
	@Override
	public boolean canUse(EnumHand hand, EntityPlayer player, BlockPos pos) {
    	SonicInventory sonic = SonicInventory.getInventory(player.getHeldItem(hand));
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return false;
    	if(curr.getItem() instanceof ITool)
    	{
    		ItemStack sonicBackup = player.getHeldItem(hand);
    		player.setHeldItem(hand, curr);
    		boolean bool = ((ITool)curr.getItem()).canUse(hand, player, pos);
    		sonic.changeCurrentItem(curr);
    		sonic.writeData();
    		sonicBackup.setTagInfo("ItemInventory", sonic.getCompound());
    		player.setHeldItem(hand, sonicBackup);
    		return bool;
    	}
		return false;
	}

    @Optional.Method(modid = "enderio")
	@Override
	public void used(EnumHand hand, EntityPlayer player, BlockPos pos) {
    	SonicInventory sonic = SonicInventory.getInventory(player.getHeldItem(hand));
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return;
    	if(curr.getItem() instanceof ITool)
    	{
    		ItemStack sonicBackup = player.getHeldItem(hand);
    		player.setHeldItem(hand, curr);
    		((ITool)curr.getItem()).used(hand, player, pos);
    		sonic.changeCurrentItem(curr);
    		sonic.writeData();
    		sonicBackup.setTagInfo("ItemInventory", sonic.getCompound());
    		player.setHeldItem(hand, sonicBackup);
    	}
	}
    
    //cofcore

    @Optional.Method(modid = "cofhcore")
	@Override
	public boolean isUsable(ItemStack stack, EntityLivingBase user, BlockPos pos) {
    	SonicInventory sonic = SonicInventory.getInventory(stack);
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return false;
    	if(curr.getItem() instanceof IToolHammer)
    		return ((IToolHammer)curr.getItem()).isUsable(curr, user, pos);
		return false;
	}

    @Optional.Method(modid = "cofhcore")
	@Override
	public boolean isUsable(ItemStack stack, EntityLivingBase user, Entity entity) {
    	SonicInventory sonic = SonicInventory.getInventory(stack);
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return false;
    	if(curr.getItem() instanceof IToolHammer)
    		return ((IToolHammer)curr.getItem()).isUsable(curr, user, entity);
		return false;
	}

    @Optional.Method(modid = "cofhcore")
	@Override
	public void toolUsed(ItemStack stack, EntityLivingBase user, BlockPos pos) {
    	SonicInventory sonic = SonicInventory.getInventory(stack);
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr.getItem() instanceof IToolHammer)
    		((IToolHammer)curr.getItem()).toolUsed(curr, user, pos);
	}

    @Optional.Method(modid = "cofhcore")
	@Override
	public void toolUsed(ItemStack stack, EntityLivingBase user, Entity entity) {
    	SonicInventory sonic = SonicInventory.getInventory(stack);
    	ItemStack curr = sonic.getCurrentItem();
    	if(curr == null)
    		return;
    	if(curr.getItem() instanceof IToolHammer)
    		((IToolHammer)curr.getItem()).toolUsed(curr, user, entity);
	}

}
