package com.maelstrom.arcaneMechina.common.tile;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import cofh.api.energy.IEnergyStorage;
import cofh.api.energy.TileEnergyHandler;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TileEntityArraySolar extends TileEnergyHandler implements IEnergyStorage {
	
	public TileEntityArraySolar(EnergyStorage e){
		storage = e;
	}
	
	public void updateEntity() {
		World w = worldObj;
		if(!w.isRemote){
			if(w.canBlockSeeTheSky(xCoord, yCoord, zCoord)){
				float sunAng = w.getCelestialAngle(1f) * 360;
				
				float minEffAng1 = 7f;
				float noEffAng1 = 80f;
				
				float maxEffAng2 = 353f;
				float noEffAng2 = 280f;
				if(sunAng > noEffAng2 || sunAng < noEffAng1)
					this.storage.modifyEnergyStored((int)(16 * calculateLightRatio(w,xCoord,yCoord,zCoord)));
				for(int i = - 7; i < 7; i++)
					for(int i2 = - 7; i2 < 7; i2++)
						for(int i3 = -7; i3 < 1; i3++){
							if(distance(xCoord - i, yCoord, zCoord - i2) <= 5f)
								if(w.getTileEntity(xCoord + i, yCoord + i2, zCoord + i3) instanceof TileEntityRFStorageArray)
									for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS)
										transferEnergyToBlock(xCoord + i, yCoord + i2, zCoord + i3, dir);
						}
			}
		}
	}
	
	public static float calculateLightRatio(World world, int x, int y, int z) {
		int lightValue = world.getSavedLightValue(EnumSkyBlock.Sky, x, y, z) - world.skylightSubtracted;
		float sunAngle = world.getCelestialAngleRadians(1.0F);
		if (sunAngle < 3.141593F) {
			sunAngle += (0.0F - sunAngle) * 0.2F;
		} else {
			sunAngle += (6.283186F - sunAngle) * 0.2F;
		}
		lightValue = Math.round(lightValue * MathHelper.cos(sunAngle));

		lightValue = MathHelper.clamp_int(lightValue, 0, 15);
		return lightValue / 15.0F;
	}
	
	public float distance(int x, int y, int z){
        float f  = (float)((this.xCoord + 0.5) - (x + 0.5f));
        float f1 = (float)((this.yCoord + 0.5) - (y + 0.5f));
        float f2 = (float)((this.zCoord + 0.5) - (z + 0.5f));
        return MathHelper.sqrt_float(f * f + f1 * f1 + f2 * f2);
	}
	
	public boolean transferEnergyToBlock(int x, int y, int z, ForgeDirection dir){
		IEnergyReceiver tile;
		int transfer, cap, canTransfer;
		World w = this.worldObj;
		if(w.getTileEntity(x, y, z) instanceof IEnergyReceiver){
			tile = (IEnergyReceiver) w.getTileEntity(x, y, z);
			transfer = storage.getMaxExtract();
			cap = storage.getEnergyStored();
			
			if(transfer > cap)
				transfer = cap;
			
			canTransfer = tile.receiveEnergy(dir, transfer, true);
			if(canTransfer != 0){
				this.extractEnergy(tile.receiveEnergy(dir,canTransfer, false), false);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		return storage.receiveEnergy(maxReceive, simulate);
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return storage.extractEnergy(maxExtract, simulate);
	}

	@Override
	public int getEnergyStored() {
		return storage.getEnergyStored();
	}

	@Override
	public int getMaxEnergyStored() {
		return storage.getMaxEnergyStored();
	}
}
