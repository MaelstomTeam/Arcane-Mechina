package com.maelstrom.arcanemechina.common.block;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.maelstrom.arcanemechina.common.tileentity.TileEntityCrystalizer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBeacon;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCrystalizer extends Block implements ITileEntityProvider{
	public BlockCrystalizer() {
		super(Material.GLASS);
	}

    
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }
    
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityCrystalizer();
	}
	
	
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }
    public EnumBlockRenderType getRenderType(IBlockState state)
    {
        return EnumBlockRenderType.MODEL;
    }
    
    private static final AxisAlignedBB CHAMBER_OUTER = new AxisAlignedBB(0.375, 0.438, 0.375, 0.75, 1, 0.75);
    private static final AxisAlignedBB LEG_1 = new AxisAlignedBB(0.594, 0, 0.281, 0.719, 0.312, 0.406);
    private static final AxisAlignedBB LEG_2 = new AxisAlignedBB(0.281, 0, 0.281, 0.406, 0.312, 0.406);
    private static final AxisAlignedBB LEG_3 = new AxisAlignedBB(0.281, 0, 0.594, 0.406, 0.312, 0.719);
    private static final AxisAlignedBB LEG_4 = new AxisAlignedBB(0.594, 0, 0.594, 0.719, 0.312, 0.719);
    private static final AxisAlignedBB TABLE = new AxisAlignedBB(0.188, 0.312, 0.188, 0.812, 0.438, 0.812);
    private static final AxisAlignedBB TESTTUBE = new AxisAlignedBB(0.188, 0.438, 0.188, 0.375, 0.812, 0.375);
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.188, 0, 0.188, 0.812, 1, 0.812);
    private static final List<AxisAlignedBB> COLLISION_BOXES = Lists.newArrayList(CHAMBER_OUTER, LEG_1, LEG_2, LEG_3, LEG_4, TABLE, TESTTUBE);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entity, boolean isActualState)
    {
        entityBox = entityBox.offset(-pos.getX(), -pos.getY(), -pos.getZ());
        for (AxisAlignedBB box : COLLISION_BOXES)
        {
            if (entityBox.intersects(box))
                collidingBoxes.add(box.offset(pos));
        }
    }

    @Override
    @Nullable
    public RayTraceResult collisionRayTrace(IBlockState state, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        double distanceSq;
        double distanceSqShortest = Double.POSITIVE_INFINITY;
        RayTraceResult resultClosest = null;
        RayTraceResult result;
        start = start.subtract(pos.getX(), pos.getY(), pos.getZ());
        end = end.subtract(pos.getX(), pos.getY(), pos.getZ());
        for (AxisAlignedBB box : COLLISION_BOXES)
        {
            result = box.calculateIntercept(start, end);
            if (result == null)
                continue;

            distanceSq = result.hitVec.squareDistanceTo(start);
            if (distanceSq < distanceSqShortest)
            {
                distanceSqShortest = distanceSq;
                resultClosest = result;
            }
        }
        return resultClosest == null ? null : new RayTraceResult(RayTraceResult.Type.BLOCK, resultClosest.hitVec.add(pos.getX(), pos.getY(), pos.getZ()), resultClosest.sideHit, pos);
    }
}
