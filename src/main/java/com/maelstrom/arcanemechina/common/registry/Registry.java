package com.maelstrom.arcanemechina.common.registry;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.api.book.Library;
import com.maelstrom.arcanemechina.common.CommonProxy;
import com.maelstrom.arcanemechina.common.block.BlockList;
import com.maelstrom.arcanemechina.common.items.ItemList;
import com.maelstrom.arcanemechina.common.items.MetaItemBlock;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityBarrier;
import com.maelstrom.snowcone.util.ERegistry;
import com.maelstrom.snowcone.util.IHasName;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@Mod.EventBusSubscriber(modid=ArcaneMechina.MODID)
public class Registry extends ERegistry {

	static Logger LOGGER = LogManager.getLogger("arcanemechina] [Registry");
	protected static ArrayList<Item> itemList = new ArrayList<Item>();
	protected static ArrayList<Block> blockList = new ArrayList<Block>();
	@SidedProxy(clientSide="com.maelstrom.arcanemechina.client.ClientProxy", serverSide="com.maelstrom.arcanemechina.server.ServerProxy", modId = ArcaneMechina.MODID)
	public static CommonProxy proxy;
	@Override
	public String getMODID() {
		return ArcaneMechina.MODID;
	}

	@Override
	public CreativeTabs getTab() {
		return ArcaneMechina.MainTab;
	}

	@Override
	public void preInitialization() {

		Library.init();
		
		LOGGER.info("running sided proxy");
		proxy.preInit();
		
		LOGGER.info("Registering Items");
		registerItem(ItemList.HelpBook, "helpbook",ArcaneMechina.Library);
		registerItem(ItemList.Ingots, "ingot");
		registerItem(ItemList.Gems, "gem");
		registerItem(ItemList.Drops, "drop");
		registerItem(ItemList.Crystal, "crystal");
		registerItem(ItemList.Dust, "dust");
		registerItem(ItemList.Chalk, "chalk", ArcaneMechina.Runic);

		LOGGER.info("Registering Blocks");
		registerBlock(BlockList.Ore, "ore");
		registerBlock(BlockList.CrystalOre, "crystalore");
		registerBlock(BlockList.Rune, "rune", null);
		registerBlock(BlockList.paperLog, "paperbark_log", ArcaneMechina.Vegitation);
		registerBlock(BlockList.paperLogDebarked, "bare_paperbark_log", null);
		registerBlock(BlockList.paperLeaves, "paperbark_leaf", ArcaneMechina.Vegitation);
		registerBlock(BlockList.paperBarkSapling, "paperbark_sapling", ArcaneMechina.Vegitation);
		
		LOGGER.info("Registering Arrays");
		RuneRegistry.Initialize();
		
		LOGGER.info("Registring TileEntities");
		TileEntity.register(ArcaneMechina.MODID+".barrier", TileEntityBarrier.class);
		
		LOGGER.info("Registring Custom shapeless recipe");
		/*GameRegistry.addShapelessRecipe(new ResourceLocation("string"), new ResourceLocation("recipes"), stack(new ItemStack(ItemList.HelpBook, 1),"book_id",Library.getLibrary()[0].title), Ingredient.fromItem(Items.BOOK), Ingredient.fromItem(ItemList.Crystal));
		for(int i = 0; i > Library.getLibrary().length; ++i)
		{
        	int i2 = i == Library.getLibrary().length ? 0 : i + 1;
			GameRegistry.addShapelessRecipe(new ResourceLocation("string"), new ResourceLocation("recipes"), stack(new ItemStack(ItemList.HelpBook,1),"book_id",Library.getLibrary()[i].title), Ingredient.fromStacks(stack(new ItemStack(ItemList.HelpBook,1),"book_id",Library.getLibrary()[i2].title)));
		}*/
	}
	public static ItemStack stack(ItemStack stack, String key, String value)
	{
		stack.serializeNBT();
		stack.getTagCompound().setString(key, value);
		return stack;
	}

	@Override
	public void Initialization() {
		LOGGER.info("running sided proxy");
		proxy.init();
        LOGGER.info("Ore Dictionary");
        registerOreDictionaryEntries();
        
		LOGGER.info("Registering Proxies");
		NetworkRegistry.INSTANCE.registerGuiHandler(ArcaneMechina.INSTANCE, proxy);
	}

	@Override
	public void postInitialization() {
		LOGGER.info("running sided proxy");
		proxy.postInit();
	}
	
	@Override
	public void registerOreDictionaryEntries()
	{
		registerOreDictionaryValue("ingotCopper", new ItemStack(ItemList.Ingots, 1, 0));
		registerOreDictionaryValue("ingotLead",   new ItemStack(ItemList.Ingots, 1, 1));
		registerOreDictionaryValue("ingotSilver", new ItemStack(ItemList.Ingots, 1, 2));
		
		registerOreDictionaryValue("gemSapphire",new ItemStack(ItemList.Gems, 1, 0));
		registerOreDictionaryValue("gemRuby",    new ItemStack(ItemList.Gems, 1, 1));
		registerOreDictionaryValue("gemAmethyst",new ItemStack(ItemList.Gems, 1, 2));
		registerOreDictionaryValue("gemDiamond", new ItemStack(ItemList.Gems, 1, 3));
		registerOreDictionaryValue("gemDiamond", new ItemStack(ItemList.Gems, 1, 4));
		registerOreDictionaryValue("gemDiamond", new ItemStack(ItemList.Gems, 1, 5));
		registerOreDictionaryValue("gemQuartz",  new ItemStack(ItemList.Gems, 1, 6));

		registerOreDictionaryValue("pearl",  new ItemStack(ItemList.Drops, 1, 0));
		registerOreDictionaryValue("paper",  new ItemStack(ItemList.Drops, 1, 1));
		
	}
	
	@SubscribeEvent
	public static void onBlockRegister(final RegistryEvent.Register<Block> event) {
		LOGGER.info("EVENT Registering Blocks");
        final IForgeRegistry<Block> registry = event.getRegistry();
		for(Block i : blockList)
			registry.register(i);
	}
	
	@SubscribeEvent
	public static void onItemRegister(final RegistryEvent.Register<Item> event) {
		LOGGER.info("EVENT Registering Items/ItemBlocks");
        final IForgeRegistry<Item> registry = event.getRegistry();
		for(Item i : itemList)
			registry.registerAll(i);
		for(Block i : blockList)
			if(i instanceof IHasName)
			{
				try {
					registry.registerAll(new MetaItemBlock.MetaItemWithSubNames(i).setRegistryName(i.getRegistryName()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else
				if(Item.getItemFromBlock(i).getHasSubtypes())
					registry.registerAll(new MetaItemBlock(i).setRegistryName(i.getRegistryName()));
				else
					registry.registerAll(new ItemBlock(i).setRegistryName(i.getRegistryName()));
	}
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public static void onModelEvent(final ModelRegistryEvent event) {
		LOGGER.info("EVENT Registering Models");
		for(Item i : itemList)
			registerItemModel(i);
		for(Block i : blockList)
			registerItemModel(Item.getItemFromBlock(i));
		final BlockColors blockColors = Minecraft.getMinecraft().getBlockColors();
		final ItemColors itemColors = Minecraft.getMinecraft().getItemColors();
		try {
		registerItemColourHandlers(blockColors, itemColors);
		}catch(Exception e)
		{
			
		}
	}
	
	public static void registerItemColourHandlers(final BlockColors blockColors, final ItemColors itemColors)
	{
		final IItemColor itemBlockColourHandler = (stack, tintIndex) -> {
			@SuppressWarnings("deprecation")
			final IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
			return blockColors.colorMultiplier(state, null, null, tintIndex);
		};

		itemColors.registerItemColorHandler(itemBlockColourHandler, ItemList.Dust);
		itemColors.registerItemColorHandler(itemBlockColourHandler, ItemList.Crystal);
	}

	@Override
	public ArrayList<Item> itemList() {
		return itemList;
	}

	@Override
	public ArrayList<Block> listBlock() {
		return blockList;
	}
/*
	not currently working
	@SubscribeEvent
	public void onItemUse(final net.minecraftforge.event.entity.living.LivingEntityUseItemEvent event)
	{
		Console.println("test");
		ItemStack stack = event.getItem();
		if(stack.getItem() == ItemList.Drops || stack.getItem() == Items.PAPER)
		{
			RayTraceResult result = event.getEntity().rayTrace(4, 0f);
			if(result.getBlockPos() != null)
			{
				World world = event.getEntity().getEntityWorld();
				List<EntityItem> list = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(result.getBlockPos()).expand(3, 3, 3).offset(-1, -1, -1));
				boolean complete = true;
				boolean[] found = new boolean[] {false,false,false,false,false,false};
				List<EntityItem> listToKill = new ArrayList();
				for(EntityItem item : list)
				{
					if(item.getItem().getItem() == ItemList.Dust && item.getItem().getCount() == 1)
					{
						if(!found[item.getItem().getItemDamage()])
						{
							found[item.getItem().getItemDamage()] = true;
							listToKill.add(item);
						}
					}
				}
				for(boolean check : found)
					if(!check)
						complete = false;
				if(complete)
				{
					for(EntityItem i : listToKill)
					{
						i.setInfinitePickupDelay();
						i.setDead();
					}
					world.spawnEntity(new EntityItem(world, result.getBlockPos().getX() + 0.5, result.getBlockPos().getY() + 1.5, result.getBlockPos().getZ(), new ItemStack(ItemList.Chalk)));
				}
			}
		}
	}*/
}
