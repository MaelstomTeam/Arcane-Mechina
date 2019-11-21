package com.maelstrom.arcanemechina.common;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;
import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.blocks.RuneBlock;
import com.maelstrom.arcanemechina.common.tileentity.RuneTileEntity;
import com.maelstrom.snowcone.common.itemgroups.CustomItemGroup;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

@Mod.EventBusSubscriber(modid = ArcaneMechina.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Registry
{
	public static TileEntityType<?> RUNE_TILE = null;
	public static final CustomItemGroup ARCANE = new CustomItemGroup("AM.ARCANE");
	public static final CustomItemGroup MACHINIST = new CustomItemGroup("AM.MACHINIST");

	public static Item chalk			= new Item(new Item.Properties().group(ARCANE).maxStackSize(1)).setRegistryName(ArcaneMechina.MODID, "chalk");
	public static Item dustCrystals		= new Item(new Item.Properties().group(ARCANE)).setRegistryName(ArcaneMechina.MODID, "dustcrystalitem");
	public static Block dustCrystalOre	= new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(15f)).setRegistryName(ArcaneMechina.MODID,"dustcrystals");
	public static RuneBlock rune		;
	
	public static void RegisterItemGroups()
	{
		ARCANE.setIcon(new ItemStack(chalk));
		MACHINIST.setIcon(new ItemStack(chalk));
	}
	@SubscribeEvent
	public static void RegisterItems(RegistryEvent.Register<Item> event) 
	{
		event.getRegistry().registerAll(
			chalk,
			dustCrystals,
			getItem(dustCrystalOre, ARCANE,"dustcrystalore_block"),
			getItem(rune, ARCANE,"rune_block")
			);
	}
	@SubscribeEvent
	public static void RegisterBlocks(RegistryEvent.Register<Block> event) 
	{
		rune = (RuneBlock) new RuneBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(-1.0F, 3600000.0F).noDrops()).setRegistryName(ArcaneMechina.MODID,"rune");
		event.getRegistry().registerAll(
				dustCrystalOre,
				rune
				);
		
	}
	@SubscribeEvent
	public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event) {
		event.getRegistry().registerAll(
				RUNE_TILE = setup(TileEntityType.Builder.create(RuneTileEntity::new, rune).build(null), "rune")
				);
	}
	public static void RegisterModels()
	{
		
	}
	public static BlockItem getItem(Block block, ItemGroup group, String registry)
	{
		final Item.Properties prop = new Item.Properties().group(group);
		final BlockItem blockItem = new BlockItem(block, prop);
		blockItem.setRegistryName(ArcaneMechina.MODID,registry);
		return blockItem;
	}
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name) {
		Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
		return setup(entry, new ResourceLocation(ArcaneMechina.MODID, name));
	}

	/**
	 * Performs setup on a registry entry
	 *
	 * @param registryName The full registry name of the entry
	 */
	@Nonnull
	private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
		Preconditions.checkNotNull(entry, "Entry cannot be null!");
		Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
		entry.setRegistryName(registryName);
		return entry;
	}
}
