package com.maelstrom.arcanemechina.common.tileentity;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.ArcaneMechina;
import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.IRuneType;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.HOLD;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.TOGGLE;
import com.maelstrom.arcanemechina.common.runic.IRuneType.RuneList.VARIBLE;
import com.maelstrom.arcanemechina.common.runic.IRuneType.hasAction;

import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.extensions.IForgeTileEntity;

public class RuneTileEntity extends TileEntity implements ITickableTileEntity, IForgeTileEntity {

	public RuneTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		//net.minecraft.tileentity.BannerTileEntity
		HOLD holder = new RuneList.HOLD();
		TOGGLE toggler = new RuneList.TOGGLE();
		VARIBLE tick_10_0 = new RuneList.VARIBLE(20);
		VARIBLE tick_10_1 = new RuneList.VARIBLE(40);
		rune.setChildren(holder, 0);
		holder.setChildren(toggler, 0);
		holder.setItem(new ItemStack(Items.DIAMOND_PICKAXE));
		toggler.setChildren(tick_10_0, 0);
		toggler.setChildren(tick_10_1, 1);
	}

	public RuneTileEntity() {
		this(Registry.RUNE_TILE);
	}

	public Direction offset() {
		return Direction.DOWN;
	}

	private RuneList.RuneContainer rune = new RuneList.RuneContainer();

	@Override
	public void read(CompoundNBT compound) {
		CompoundNBT nbt = (CompoundNBT) compound.get("RUNE");
		if(!nbt.isEmpty())
		{
			rune.deserializeNBT(nbt);
		}
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("RUNE", rune.serializeNBT());
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
	public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt){
		CompoundNBT tag = pkt.getNbtCompound();
	    read(tag);
	    //Handle your Data
	}

	@Override
	public void tick() {
		if (world.isRemote)
			return;
		BlockPos pos2 = pos.offset(offset().getOpposite(), 1);
		for (hasAction action : rune.getAll(rune)) {
			action.doAction(this);
		}
		for (IRuneType.IInventoryRune r : rune.getAllOfType(IRuneType.IInventoryRune.class)) {
			for (int i = 0; i < r.getItemOutput().size(); i++) {
				ItemStack item = r.removeItem(false, i, r.getItemOutput().get(i).getCount());
				if (item.getCount() > 0)
					world.addEntity(new ItemEntity(world, pos2.getX() + .5, pos2.getY(), pos2.getZ() + .5, item));// .setMotion(0,
																													// 0,
																													// 0)
			}
		}
		//world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
	}

}
