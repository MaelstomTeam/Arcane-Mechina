package com.maelstrom.arcanemechina.common;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.client.ClientProxy;
import com.maelstrom.arcanemechina.common.blocks.RuneBlock;
import com.maelstrom.arcanemechina.common.blocks.RuneCraftingTableBlock;
import com.maelstrom.arcanemechina.common.container.RuneCraftingContainer;
import com.maelstrom.arcanemechina.common.container.RuneDrawingContainer;
import com.maelstrom.arcanemechina.common.items.ChalkItem;
import com.maelstrom.arcanemechina.common.items.NoDamageItem;
import com.maelstrom.arcanemechina.common.items.RecipeBlueprintItem;
import com.maelstrom.arcanemechina.common.items.RuneBlueprintItem;
import com.maelstrom.arcanemechina.common.tileentity.RuneCraftingTileEntity;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.arcanemechina.server.ServerProxy;
import com.maelstrom.snowcone.IProxy;
import com.maelstrom.snowcone.itemgroups.CustomItemGroup;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.common.extensions.IForgeContainerType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
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
    @ObjectHolder("arcanemechina:rdc")
	public static TileEntityType<?> RUNE_TILE = null;
    @ObjectHolder("arcanemechina:rcc")
	public static TileEntityType<?> RUNE_CRAFT_TILE = null;
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
	public static Block runeCraftingTable;
	public static RuneBlock inWorldRune;
	
	///////////CONTAINER////////////
    @ObjectHolder("arcanemechina:rdc") public static ContainerType<RuneDrawingContainer> RDC;
    @ObjectHolder("arcanemechina:rcc")  public static ContainerType<RuneCraftingContainer> RCC;

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
				getItem(runeCraftingTable, ARCANE, "rcc"),
				getItem(inWorldRune, null, "rune_block"));
	}

	@SubscribeEvent
	public static void RegisterBlocks(RegistryEvent.Register<Block> event)
	{
		inWorldRune = (RuneBlock) new RuneBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 5F).noDrops()).setRegistryName(ArcaneMechina.MODID, "rune");
		runeCraftingTable = new RuneCraftingTableBlock(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.5F).sound(SoundType.WOOD)).setRegistryName(ArcaneMechina.MODID, "rcc");
		event.getRegistry().registerAll(
				dustCrystalOre,
				runeCraftingTable,
				inWorldRune);
	}

	@SubscribeEvent
	public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event)
	{
		event.getRegistry().registerAll(
				setup(TileEntityType.Builder.create(RuneTileEntity::new, inWorldRune).build(null), "rdc"),
				setup(TileEntityType.Builder.create(RuneCraftingTileEntity::new, runeCraftingTable).build(null), "rcc"));
	}
	
	@SubscribeEvent
    public static void onContainerRegistry(final RegistryEvent.Register<ContainerType<?>> event) {
		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new RuneDrawingContainer(windowId, Registry.PROXY.getClientWorld(), pos, inv, Registry.PROXY.getClientPlayer());
        }).setRegistryName("rdc"));
		event.getRegistry().register(IForgeContainerType.create((windowId, inv, data) -> {
            BlockPos pos = data.readBlockPos();
            return new RuneCraftingContainer(windowId, Registry.PROXY.getClientWorld(), pos, inv, Registry.PROXY.getClientPlayer());
        }).setRegistryName("rcc"));

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
	
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName)
	{
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}
	

	@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
	public static class ForgeSideHandler
	{
	
		//becuase for some reason it doesn't work when used in "bus.MOD" so here it is in this side handler
		@SubscribeEvent
		public static void RegisterItems(LivingDropsEvent event)
		{
			if(event.getEntity() instanceof ZombieEntity)
			{
				if(Math.random() * 100d >= 1d)
				{
					ItemEntity temp = (ItemEntity) event.getDrops().toArray()[0];
					event.getDrops().add(new ItemEntity(temp.getEntityWorld(), temp.posX, temp.posY, temp.posZ, new ItemStack(Items.FEATHER)));
				}
			}
		}
	}

}
