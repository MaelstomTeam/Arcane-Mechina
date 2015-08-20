package com.maelstrom.arcanemechina.client.guis;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;

public class MechinizedArcaneGui extends GuiScreen{
	//Spor's First class in the project!!! Woohoo!!!
	//^left a basic functions and comments for him $Hybolic
	//	refer to GuiScreenBook for other things
	private GuiButton closeButton;
	
	public MechinizedArcaneGui()
	{
		super();
	}
	@Override
	public void initGui()
	{
		super.initGui();
		//new GuiButton(ID, X, Y, Length, Height, Text) $Hybolic
		 
		//absolute position $Hybolic
		//closeButton = new GuiButton(0, 5, 5, 200, 20, "CLOSE");
		
		//relative to screen position $Hybolic
		closeButton = new GuiButton(0, this.width - 40, this.height - 20, 40, 20, "CLOSE");
		
		
		//add button to list $Hybolic
		buttonList.add(closeButton);
	}
	
    @Override
    public void drawScreen(int x, int y, float someFloat)
    {
    	//black gradient $Hybolic
    	this.drawDefaultBackground();
    	super.drawScreen(x, y, someFloat);
    }
    
    @Override
    protected void actionPerformed(GuiButton button)
    {
    	//close the window $Hybolic
    	if(button.equals(closeButton))
    	{
            this.mc.displayGuiScreen((GuiScreen)null);
            this.mc.setIngameFocus();
    	}
    		
    }
	
}
