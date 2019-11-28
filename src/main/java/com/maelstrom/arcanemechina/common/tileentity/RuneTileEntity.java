package com.maelstrom.arcanemechina.common.tileentity;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType;
import com.maelstrom.arcanemechina.common.runic.newrune.RuneType.HoldingRune;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeTileEntity;

public class RuneTileEntity extends TileEntity implements ITickableTileEntity, IForgeTileEntity {

	public RuneTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		this.rune = RuneContainer.getNewMiningRune();
		
	}

	public RuneTileEntity() {
		this(Registry.RUNE_TILE);
	}

	public Direction offset() {
		return Direction.DOWN;
	}

	public RuneContainer rune;

	@Override
	public void read(CompoundNBT compound) {
		CompoundNBT nbt = (CompoundNBT) compound.get("RUNE");
		if (!nbt.isEmpty()) {
			rune.readNBT(nbt);
		}
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		if(rune != null)
		{
			compound.put("RUNE", rune.writeNBT());
		}
		return super.write(compound);
	}

	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	@Nullable
	public SUpdateTileEntityPacket getUpdatePacket() {
		return new SUpdateTileEntityPacket(this.pos, 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
		CompoundNBT tag = pkt.getNbtCompound();
		read(tag);
	}

	@Override
	public void tick() {
		if (world.isRemote);
			//return;
		rune.tick(this);
		
		
		
//		////////////////////////////////////////////////////////////////////////////////////
//		for (RuneType.IInventoryRune r : rune.getAllOfType(RuneType.IInventoryRune.class)) {
//			for (int i = 0; i < r.getItemOutput().size(); i++) {
//				ItemStack item = r.removeItem(false, i, r.getItemOutput().get(i).getCount());
//				if (item.getCount() > 0) {
//					ItemEntity item_entity = new ItemEntity(world, pos2.getX() + .5, pos2.getY() - .9, pos2.getZ() + .5,
//							item);
//					item_entity.setMotion(0, 0, 0);
//					world.addEntity(item_entity);
//				}
//			}
//		}
		// world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
	}

	public void remove() {
		if(getWorld() instanceof ServerWorld)
			for (RuneType r : rune.getRune(HoldingRune.class)) {
				((HoldingRune)r).resetInteractions(this);
			}
		super.remove();
	}
	
	@Override
	public void markDirty()
	{
		super.markDirty();
		world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);//send update to client
	}

	public RuneContainer getRuneContainer()
	{
		return rune;
	}

}
