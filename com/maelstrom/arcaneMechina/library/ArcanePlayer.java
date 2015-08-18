package com.maelstrom.arcanemechina.library;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

import com.mojang.authlib.GameProfile;

public abstract class ArcanePlayer extends EntityPlayer
{
	private boolean isPhilosopherHost = false;
	private int philosopherKills = 0;
	
    public void readEntityFromNBT(NBTTagCompound nbt)
    {
    	//run the super class function
        super.readEntityFromNBT(nbt);
        //get ArcaneMechina NBT data
        NBTTagCompound arcaneNBT = (NBTTagCompound) nbt.getTag("ArcaneMechina");
        //get values
        isPhilosopherHost = arcaneNBT.getBoolean("isPhilosopherHost");
        philosopherKills  = arcaneNBT.getInteger("philosopherKills");
    
    }
    
    public void writeEntityToNBT(NBTTagCompound nbt)
    {
    	//run the super class function
        super.writeEntityToNBT(nbt);

        //create ArcaneMechina NBT data
        NBTTagCompound arcaneNBT = new NBTTagCompound();
        
        //set values 
        arcaneNBT.setBoolean("isPhilosopherHost", isPhilosopherHost);
        arcaneNBT.setInteger("philosopherKills",  philosopherKills );
        
        //export to main NBT
        nbt.setTag("ArcaneMechina", arcaneNBT);
    }
    
    public void setPhilosopherHost(boolean isHost)
    {
    	//only change if it's changed values
    	if(isPhilosopherHost != isHost){
        	isPhilosopherHost = isHost;
        	//if player is now a host set kills to 1
        	if(isPhilosopherHost)
        		philosopherKills = 1;
        	//if player isn't a host and kills isn't equal to zero then set to zero
        	else if(philosopherKills != 0)
        			philosopherKills = 0;
	    	//write changes to player immediatly
        	writeEntityToNBT(new NBTTagCompound());
    	}
    }
    
    public void incrementPhilosopherKills(int amount)
    {
    	//check if amount isn't zero and player is a host
    	if(amount != 0 && isPhilosopherHost){
	    	philosopherKills += amount;
	    	
	    	//if kill count is less or equal to zero set to kills to zero and remove philosopher host ability
	    	if(philosopherKills <= 0)
	    	{
	    		philosopherKills = 0;
	    		isPhilosopherHost = false;
	    	}
	    	//write changes to player immediatly
        	writeEntityToNBT(new NBTTagCompound());
    	}
    }
    //check if player is a host
    public boolean isPhilosopherHost()
    {
    	readEntityFromNBT(new NBTTagCompound());
    	return isPhilosopherHost;
    }
    //get kill count
    public int getPhilosopherKills()
    {
    	readEntityFromNBT(new NBTTagCompound());
    	return philosopherKills;
    }
    
    
    
    

    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================
    //============================================================================================

	//ignore!!
    //last resort remove abstract and implement below functions!!
    
	private ArcanePlayer(World world, GameProfile gp)
	{
		super(world, gp);
	}
//
//	@Override
//	public void addChatMessage(IChatComponent p_145747_1_) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public boolean canCommandSenderUseCommand(int p_70003_1_, String p_70003_2_) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public ChunkCoordinates getPlayerCoordinates() {
//		// TODO Auto-generated method stub
//		return null;
//	}
	
}
