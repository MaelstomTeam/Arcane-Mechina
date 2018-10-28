package com.maelstrom.arcanemechina.common.arrays;

import com.maelstrom.arcanemechina.api.ElementTypes;
import com.maelstrom.arcanemechina.api.RuneActivity;
import com.maelstrom.arcanemechina.common.event.EventRuneBackfire;
import com.maelstrom.arcanemechina.common.event.EventRuneBackfire.SEVERITY;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
//DOES NOTHING
public class BasicActivity extends RuneActivity {

	@Override
	public EventRuneBackfire getBackfireResult(World w, BlockPos pos, EntityLivingBase entity) {
		return new EventRuneBackfire(pos,w,SEVERITY.NONE,ElementTypes.OBLIVIOUS, entity);
	}

	@Override
	public void runActivity(World w, BlockPos pos) {
	}

	@Override
	public boolean isValid(World w, BlockPos pos)
	{
		return true;
	}

}
