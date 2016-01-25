package com.maelstrom.snowcone;

import java.awt.Color;
import java.util.*;

import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.*;
import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.*;

import org.lwjgl.opengl.GL11;

@Mod(modid = SnowconeMain.MODID, version = SnowconeMain.VERSION)
public class SnowconeMain
{
    public static final String MODID = "snowcone";
    public static final String VERSION = "Lemon";
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
		MinecraftForge.EVENT_BUS.register(this);
    }
    

	int rngLast = 0;
	int rng = 0;
	int jokeNumber1 = 1, jokeNumber2 = 4, jokeNumber3 = 0;
	Random rand = new Random();
    @SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void guiDraw(GuiScreenEvent.DrawScreenEvent event){
		if(event.gui instanceof GuiModList){
			GuiModList gui = (GuiModList) event.gui;
			if(gui.modIndexSelected(ModID())){
		        Calendar calendar = Calendar.getInstance();
				GL11.glPushMatrix();
		        GL11.glTranslatef(70f, 2.0F, 0.0F);
//				GL11.glRotated(-10, 0, 0, 1);
		        float f1 = 1.8F - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0F * (float)Math.PI * 2.0F) * 0.1F);
		        f1 = f1 * ((rng == 4 || rng == 8) ? 60 : 75.0F) / (float)(Minecraft.getMinecraft().fontRendererObj.getStringWidth("HAPPY B-DAY HYBOLIC!!") + 32);
		        GL11.glScalef(f1, f1, f1);
		        //EVENTS
				if((calendar.get(2) + 1 == 2 && calendar.get(5) == 7))
					event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "HAPPY B-DAY HYBOLIC!!", 0, 0, Color.YELLOW.hashCode());
		        else if((calendar.get(2) + 1 == 12 && calendar.get(5) == 16))   
					event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "HAPPY B-DAY SNR!!", 0, 0, Color.YELLOW.hashCode());
		        else if((calendar.get(2) + 1 == 10 && calendar.get(5) == 31))
					event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "§6S§4p§6o§6o§4k§6y §4S§6c§4a§6r§4y §6S§4k§6ele§4t§6o§4n§6s!", 0, 0, Color.WHITE.hashCode());
		        else if((calendar.get(2) + 1 == 4 && calendar.get(5) == 4))
					event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "SNOWCONE IS OLDER! :D", 0, 0, Color.YELLOW.hashCode());
//		        else if((calendar.get(2) + 1 == 4 && calendar.get(5) == 4))
//					event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "SNOWCONE IS OLDER! :D", 0, 0, Color.YELLOW.hashCode());
		        //add the first day out of alpha here
//		        else if((calendar.get(2) + 1 == 10 && calendar.get(5) == 31))
//					event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "HAPPY B-DAY Arcane Mechina!", 0, 0, Color.yellow.hashCode());
				
		        else{
		        	if (rng == 1)
		        		event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "It's not icecream!", 0, 0, Color.YELLOW.hashCode());
		        	else if (rng == 4)
		        		event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, jokeNumber1+","+jokeNumber2+jokeNumber3+"3rd times the charm right?", 0, 0, Color.YELLOW.hashCode());
		        	else if (rng == 7)
		        		event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Superwholock!", 0, 0, Color.YELLOW.hashCode());
		        	else if (rng == 8)
		        		event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, dateBetweenNowAndMC()+" Days since Minecraft Released!", 0, 0, Color.YELLOW.hashCode());
		        	else
		        		event.gui.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Don't worry It's "+VERSION+"!", 0, 0, Color.YELLOW.hashCode());
		        }
				GL11.glPopMatrix();
				if(rngLast != rng)
					rngLast = rng;
			}else{
				if(rand.nextInt(5) == 4)
					while(rngLast == rng)
						rng = rand.nextInt(10);
				else
					rng = 0;
				if(rng == 4)
				{
					jokeNumber1 = rand.nextInt(999)+1;
					jokeNumber2 = rand.nextInt(10);
					jokeNumber3 = rand.nextInt(10);
				}
			}
		}
	}
    
    public static Object dateBetweenNowAndMC()
    {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        cal1.set(2009, 4, 17);
        cal2.setTime(new Date());
        return (int)( (cal2.getTime().getTime() - cal1.getTime().getTime()) / (1000 * 60 * 60 * 24)) + 1;
    }
	private static int myModLocation = -99;

	public static int ModID() {
		if (myModLocation == -99) {
			ArrayList<ModContainer> mods = new ArrayList<ModContainer>();
			FMLClientHandler.instance().addSpecialModEntries(mods);
			int i = 0;
			for (ModContainer mod : Loader.instance().getModList())
				if (mod.getModId().equalsIgnoreCase(MODID)) {
					myModLocation = i;
					break;
				}
				else
					i++;
		}
		return myModLocation;
	}
}
