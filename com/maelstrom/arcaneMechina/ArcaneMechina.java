package com.maelstrom.arcaneMechina;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;

import com.maelstrom.arcaneMechina.common.handler.PlayerJoinWorldEvent;
import com.maelstrom.arcaneMechina.common.init.InitBlock;
import com.maelstrom.arcaneMechina.common.init.InitItem;
import com.maelstrom.arcaneMechina.common.init.InitRecipe;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.tileentity.TileEntityFurnace;
import com.maelstrom.arcaneMechina.common.world.Structure;
import com.maelstrom.arcaneMechina.common.world.StructureRegistery;
import com.maelstrom.arcaneMechina.common.world.WorldGen;
import com.maelstrom.arcaneMechina.common.world.custom.NoEffect;
import com.maelstrom.arcaneMechina.common.world.structure.Layer;
import com.maelstrom.arcaneMechina.common.world.structure.Row;
import com.maelstrom.snowcone.proxy.IProxy;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid=Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.MOD_VERSION, useMetadata = Reference.MOD_METADATA)
public class ArcaneMechina {
	
	@SidedProxy(clientSide = Reference.PROXY_CLIENT, serverSide = Reference.PROXY_SERVER)
	public static IProxy proxy;
	
	@Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
		InitItem.init();
		InitBlock.init();
	}
	
	@Mod.EventHandler
    public void postInit(FMLInitializationEvent event){
		InitRecipe.init();
		if(Loader.isModLoaded("NotEnoughItems")){
			codechicken.nei.api.API.hideItem(new ItemStack(InitItem.wandOfDebug));
		}
		GameRegistry.registerWorldGenerator(new WorldGen(), 10);
		GameRegistry.registerTileEntity(TileEntityFurnace.class, Reference.MOD_ID+".TileEntityFurnace");
	}
	
	@Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
		proxy.registerEvents();
		proxy.registerRenderers();
		MinecraftForge.EVENT_BUS.register(new PlayerJoinWorldEvent());
		Row saa1 = new Row(" GGG " ,' ', null, 'G', InitBlock.glyphblank);
		Row saa2 = new Row("G   G" ,' ', null, 'G', InitBlock.glyphblank);
		Row saa3 = new Row("G G G" ,' ', null, 'G', InitBlock.glyphblank);
		StructureRegistery.register(new Structure("SmallArrayActivator", new Layer[]{new Layer(new Row[]{saa1,saa2,saa3,saa2,saa1})}, new NoEffect()));

		Row r1  = new Row("               E   E               ",' ', null, 'E', InitBlock.glyphenergy);
		Row r2  = new Row("                E E                ",' ', null, 'E', InitBlock.glyphenergy);
		Row r3  = new Row("             E   E   E             ",' ', null, 'E', InitBlock.glyphenergy);
		Row r4  = new Row("              E  E  E              ",' ', null, 'E', InitBlock.glyphenergy);
		Row r4_ = new Row("               E   E               ",' ', null, 'E', InitBlock.glyphenergy);
		Row r5  = new Row("          EE    E E    EE          ",' ', null, 'E', InitBlock.glyphenergy);
		Row r6  = new Row("            EE   E   EE            ",' ', null, 'E', InitBlock.glyphenergy);
		Row r7  = new Row("              EE   EE              ",' ', null, 'E', InitBlock.glyphenergy);
		Row r8  = new Row("       E        EEE        E       ",' ', null, 'E', InitBlock.glyphenergy);
		Row r9  = new Row("       E                   E       ",' ', null, 'E', InitBlock.glyphenergy);
		Row r10 = new Row("        E       BBB       E        ",' ', null, 'E', InitBlock.glyphenergy, 'B', InitBlock.glyphblank);
		Row r11 = new Row("    E   E     BB   BB     E   E    ",' ', null, 'E', InitBlock.glyphenergy, 'B', InitBlock.glyphblank);
		Row r12 = new Row("     E   E   B       B   E   E     ",' ', null, 'E', InitBlock.glyphenergy, 'B', InitBlock.glyphblank);
		Row r13 = new Row("EE    E  E   B       B   E  E    EE",' ', null, 'E', InitBlock.glyphenergy, 'B', InitBlock.glyphblank);
		Row r14 = new Row("  EE   E  E B         B E  E   EE  ",' ', null, 'E', InitBlock.glyphenergy, 'B', InitBlock.glyphblank);
		Row r15 = new Row("    EE  E E B    C    B E E  EE    ",' ', null, 'E', InitBlock.glyphenergy, 'B', InitBlock.glyphblank, 'C', InitBlock.glyphCenter);
		StructureRegistery.register(new Structure("arrayCompression", new Layer[]{new Layer(new Row[]{r1,r1,r2,r2,r3,r4,r4_,r5,r6,r7,r8,r9,r10,r11,r12,r13,r14,r15,r14,r13,r12,r11,r10,r9,r8,r7,r6,r5,r4_,r4,r3,r2,r2,r1,r1})}, new NoEffect()));

		
		//jump boost for pegasus wings 1.5 blocks
		MinecraftForge.EVENT_BUS.register(InitItem.pegasusWingAmulet);
	}
}