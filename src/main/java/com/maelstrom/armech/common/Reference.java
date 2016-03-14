package com.maelstrom.armech.common;

import net.minecraft.util.DamageSource;


public class Reference
{
    public static final String MODID = "armech";
    public static final String VERSION = "Super Early Alpha";

    public static final String CLIENT_PROXY_CLASS = "com.maelstrom.armech.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.maelstrom.armech.proxy.CommonProxy";
    
    public static final String[] dustNames = new String[]{"air","earth","fire","water","dark","light",
    									"bright_air","bright_earth","bright_fire","bright_water",
    									"dark_air","dark_earth","dark_fire","dark_water",
    									"life","death","molten","steam","strong_fire","earth_unstable","explosive",
    									"ender","hellish","power","overcharged"};
    
    /*
     * 
     	earth + water = life
    	life + fire or dark = death (or poison) - should be unstable and cause damage around source
    	earth + fire = molten - does not solidify
    	water + fire = steam - unstable dust type
    	air + fire = strong fire
    	molten + water = earth - unstable type
    	strong fire + water = explosive - very unstable should cause instant explosion
    	
    	===artifitial===
    	ender - unstable until solidified, may cause teleportation
    		Byproduct void - may collapse areas inward
    	hellish 		   |
    		Byproduct void ^> makes less then ender but still there
    	power - unstable until solidified!
    		made by charging a light air with 1,000RF or being struck by have lightning strike near the workstation
    	Overcharged - impossible to pickup until fully drained and converted back to power
    		center of multiblock super battery
    		
    */
    public static class CustomDamageTypes
    {
    	public static final DamageSource electrocute = new DamageSource("electrocute").setDamageBypassesArmor();
    	public static final DamageSource shards = new DamageSource("shards");
    }

	public static ConfigurationArcaneMechina config;
}
