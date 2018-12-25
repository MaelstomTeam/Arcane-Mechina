package com.maelstrom.snowcone.common;

import org.lwjgl.input.Keyboard;

import com.maelstrom.snowcone.SnowCone;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class Config
{
	public static KeyBinding switchTool;
	
	public static void RegisterKeybinds()
	{
		switchTool = new KeyBinding(SnowCone.MODID+".key.switchTool", Keyboard.KEY_R, "key.catagories."+SnowCone.MODID);
	}

    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event)
    {
        if (Config.switchTool.isPressed())
        {
        	//send command to server to switch tool id
            
        }
    }
	
	
	
}
