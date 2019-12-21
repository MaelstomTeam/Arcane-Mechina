package com.maelstrom.arcanemechina.common;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.client.ClientProxy;
import com.maelstrom.arcanemechina.client.gui.RuneCraftingGui;
import com.maelstrom.arcanemechina.common.blocks.RuneBlock;
import com.maelstrom.arcanemechina.common.container.RuneDrawingContainer;
import com.maelstrom.arcanemechina.common.items.ChalkItem;
import com.maelstrom.arcanemechina.common.items.NoDamageItem;
import com.maelstrom.arcanemechina.common.items.RecipeBlueprintItem;
import com.maelstrom.arcanemechina.common.items.RuneBlueprintItem;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.arcanemechina.server.ServerProxy;
import com.maelstrom.snowcone.common.itemgroups.CustomItemGroup;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registry
{
	
	
    public static IProxy PROXY = DistExecutor.runForDist(() -> () -> new ClientProxy(), () -> () -> new ServerProxy());
	
	//////////////////////////////////////
    @ObjectHolder("arcanemechina:rune")
	public static TileEntityType<?> RUNE_TILE      = null;
	public static final CustomItemGroup ARCANE     = new CustomItemGroup("AM.ARCANE");
	public static final CustomItemGroup MACHINIST  = new CustomItemGroup("AM.MACHINIST");
	public static final CustomItemGroup BLUEPRINTS = new CustomItemGroup("AM.BLUEPRINTS");


	///////////////ITEMS/////////////
	public static Item chalk            = new ChalkItem();
	public static Item dustCrystals     = new NoDamageItem(new Item.Properties().group(ARCANE).addToolType(ToolType.PICKAXE, 1), 6, "dustcrystalitem");
	public static Item blueprint_rune   = new RuneBlueprintItem();
	public static Item blueprint_recipe	= new RecipeBlueprintItem();
	public static Item gears            = new NoDamageItem(new Item.Properties().group(Registry.MACHINIST).setNoRepair(), 4, "gears");//wood,stone,iron,diamond
	public static Item belts            = new NoDamageItem(new Item.Properties().group(Registry.MACHINIST).setNoRepair(), 3, "belts");//leather, rubber, reinforced
	public static Item elemtents        = new NoDamageItem(new Item.Properties().group(Registry.MACHINIST).setNoRepair(), 2, "tempreture_element");//heating, cooling
	public static Item industrialOthers;
	
	
	
	//////////////BLOCKS/////////////
	public static Block dustCrystalOre  = new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 10.0F)).setRegistryName(ArcaneMechina.MODID, "dustcrystals");
	public static RuneBlock rune;
	
	///////////CONTAINER////////////
    @ObjectHolder("arcanemechina:rune")
    public static ContainerType<RuneDrawingContainer> RDC;

	public static void RegisterItemGroups()
	{
		ARCANE.setIcon(new ItemStack(chalk));
		MACHINIST.setIcon(new ItemStack(chalk));
		BLUEPRINTS.setIcon(new ItemStack(blueprint_rune));
	}

	@SubscribeEvent
	public static void RegisterItems(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(
				chalk, 
				dustCrystals,
				blueprint_rune,
				blueprint_recipe,
				gears,
				belts,
				elemtents,
				getItem(dustCrystalOre, ARCANE, "dustcrystalore_block"), 
				getItem(rune, null, "rune_block"));
	}

	@SubscribeEvent
	public static void RegisterBlocks(RegistryEvent.Register<Block> event)
	{
		rune = (RuneBlock) new RuneBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).noDrops()).setRegistryName(ArcaneMechina.MODID, "rune");
		event.getRegistry().registerAll(dustCrystalOre, rune);
	}

	@SubscribeEvent
	public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().registerAll(setup(TileEntityType.Builder.create(RuneTileEntity::new, rune).build(null), "rune"));
	}
	
	@SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new RuneDrawingContainer(windowId, Registry.PROXY.getClientWorld(), pos, inv, Registry.PROXY.getClientPlayer());
        }).setRegistryName("rune"));

    }

	public static void RegisterModels()
	{

	}
	public static BlockItem getItem(Block block, ItemGroup group, String registry)
	{
		final Item.Properties prop = new Item.Properties().group(group);
		final BlockItem blockItem = new BlockItem(block, prop);
		blockItem.setRegistryName(ArcaneMechina.MODID, registry);
		return blockItem;
	}

	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name)
	{
		Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
		return setup(entry, new ResourceLocation(ArcaneMechina.MODID, name));
	}

	/**
	 * Performs setup on a registry entry
	 * @param registryName The full registry name of the entry
	 */
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName)
	{
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}
}
