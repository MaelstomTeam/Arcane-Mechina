package com.maelstrom.arcanemechina.common.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.maelstrom.arcanemechina.common.Registry;
import com.maelstrom.arcanemechina.common.runic.RuneType;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.HOLD;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.RuneContainer;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.TOGGLE;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneList.VARIBLE;
import com.maelstrom.arcanemechina.common.runic.RuneType.RuneSize;
import com.maelstrom.arcanemechina.common.runic.RuneType.hasAction;

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
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.extensions.IForgeTileEntity;

public class RuneTileEntity extends TileEntity implements ITickableTileEntity, IForgeTileEntity {

	public RuneTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		// net.minecraft.tileentity.BannerTileEntity
		boolean toggle = new Random().nextBoolean();
		if(toggle)
		{
			HOLD holder = new RuneList.HOLD();
			TOGGLE toggler = new RuneList.TOGGLE();
			VARIBLE tick_10_0 = new RuneList.VARIBLE(0+(int)(Math.random() * 60));
			VARIBLE tick_10_1 = new RuneList.VARIBLE(60+(int)(Math.random() * 60));
			rune.setSize(RuneSize.SMALL);
			rune.setChildren(holder, 0);
			holder.setChildren(toggler, 0);
			holder.setItem(new ItemStack(Items.DIAMOND_PICKAXE));
			//holder.getItemInput().get(0).setDamage(holder.getItemInput().get(0).getMaxDamage() / 2);
			toggler.setChildren(tick_10_0, 0);
			toggler.setChildren(tick_10_1, 1);
		}
		else
		{	
			RuneContainer runeCon1 = new RuneContainer();
			runeCon1.setSize(RuneSize.MEDIUM);
			
			RuneContainer runeCon2 = new RuneContainer();
			runeCon2.setSize(RuneSize.MEDIUM);
			rune.setSize(RuneSize.LARGE);
			rune.setChildren(runeCon1, 0);
			rune.setChildren(runeCon2, 1);
			
			for(int i = 0; i < runeCon1.getCapacity(); i++)
			{
				HOLD craftingHold = new HOLD();
				if(i!=4)
					craftingHold.setItem(new ItemStack(Items.COBBLESTONE,64));
				runeCon1.setChildren(craftingHold, i);
			}
			

			HOLD holder2 = new RuneList.HOLD();
			TOGGLE toggler = new RuneList.TOGGLE();
			VARIBLE tick_10_0 = new RuneList.VARIBLE(1);
			VARIBLE tick_10_1 = new RuneList.VARIBLE(1);
			holder2.setChildren(toggler, 0);
			holder2.setItem(new ItemStack(Items.CRAFTING_TABLE));
			toggler.setChildren(tick_10_0, 0);
			toggler.setChildren(tick_10_1, 1);
			runeCon2.setChildren(holder2, 0);
		}
	}

	public RuneTileEntity() {
		this(Registry.RUNE_TILE);
	}

	public Direction offset() {
		return Direction.DOWN;
	}

	public RuneList.RuneContainer rune = new RuneList.RuneContainer();

	@Override
	public void read(CompoundNBT compound) {
		CompoundNBT nbt = (CompoundNBT) compound.get("RUNE");
		if (!nbt.isEmpty()) {
			rune.readFromNBT(nbt);
		}
		super.read(compound);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.put("RUNE", rune.writeToNBT());
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
		BlockPos pos2 = pos.offset(offset().getOpposite(), 1);
		for (hasAction action : rune.getAll(rune)) {
			action.doAction(this);
		}
		
		
		
		////////////////////////////////////////////////////////////////////////////////////
		for (RuneType.IInventoryRune r : rune.getAllOfType(RuneType.IInventoryRune.class)) {
			for (int i = 0; i < r.getItemOutput().size(); i++) {
				ItemStack item = r.removeItem(false, i, r.getItemOutput().get(i).getCount());
				if (item.getCount() > 0) {
					ItemEntity item_entity = new ItemEntity(world, pos2.getX() + .5, pos2.getY() - .9, pos2.getZ() + .5,
							item);
					item_entity.setMotion(0, 0, 0);
					world.addEntity(item_entity);
				}
			}
		}
		// world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);
	}

	public void remove() {
		if(getWorld() instanceof ServerWorld)
			for (HOLD r : rune.getAllOfType(HOLD.class)) {
				r.resetInteractions(this);
			}
		super.remove();
	}
	
	@Override
	public void markDirty()
	{
		super.markDirty();
		world.notifyBlockUpdate(getPos(), getBlockState(), getBlockState(), 2);//send update to client
	}

}
