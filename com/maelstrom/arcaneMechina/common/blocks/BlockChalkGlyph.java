package com.maelstrom.arcaneMechina.common.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.maelstrom.arcaneMechina.common.other.Structures;
import com.maelstrom.arcaneMechina.common.reference.Reference;
import com.maelstrom.arcaneMechina.common.tile.TileEntityGlyph;
import com.maelstrom.snowcone.extendables.ExtendableBlock;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChalkGlyph extends ExtendableBlock implements ITileEntityProvider {
	
	public BlockChalkGlyph(String local) {
		super(Material.circuits, local, Reference.MOD_ID);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.03125F, 1.0F);
        this.setTickRandomly(true);
        this.bounds(0);
	}
	
	public boolean listContainsStack(List<EntityItem> list, ItemStack is, boolean consume){
		for(EntityItem item : list)
			if(item.getEntityItem().isItemEqual(is)){
				if(consume)
					item.getEntityItem().stackSize -= is.stackSize;
				return true;
			}
		return false;
	}
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer ply, int face, float xf, float yf, float zf) {
		//goto array class
		List<EntityItem> ents = w.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(x,y,z,x,y,z).expand(3, 0.5, 3));
		if(ply.getCurrentEquippedItem() != null){
			if(!ply.isSneaking()){
				if(ply.capabilities.isCreativeMode){
					if(ply.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.blaze_rod))){
						if(!w.isRemote){
							//Kill all dragons and re-spawn one 20 blocks above glyph location
							List<EntityDragon> dragon = w.getEntitiesWithinAABB(EntityDragon.class, AxisAlignedBB.getBoundingBox(x,y,z,x,y,z).expand(300, 300, 300));
							for(EntityDragon d : dragon)
								d.setHealth(0f);
							Entity e = new EntityDragon(w);
							e.setPosition(x, y+20, z);
							w.spawnEntityInWorld(e);
						}
					}else if(ply.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.iron_sword))){
						List<EntityLivingBase> ent = w.getEntitiesWithinAABB(EntityLivingBase.class, AxisAlignedBB.getBoundingBox(x,y,z,x,y,z).expand(300, 300, 300));
						for(EntityLivingBase e : ent)
							if(!(e instanceof EntityPlayer))
								e.setHealth(0f);
					}
					else if(ply.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.stick))){
						for(int i = -10; i < 10; i++)
							for(int i2 = 0; i2 < 15; i2++)
								for(int i3 = -10; i3 < 10; i3++)
									if(w.getBlock(x+i, y+i2, z+i3) == Blocks.diamond_block ||
									w.getBlock(x+i, y+i2, z+i3) == Blocks.bedrock ||
									w.getBlock(x+i, y+i2, z+i3) == Blocks.end_portal ||
									w.getBlock(x+i, y+i2, z+i3) == Blocks.dragon_egg ||
									w.getBlock(x+i, y+i2, z+i3) == this){
										System.out.print("w.getBlock(x+" + i + ", y+" + i2 + ", z+" + i3 + ") == " + w.getBlock(x+i, y+i2, z+i3).getLocalizedName() + " &&\n");
									}
						System.out.println("\n\n\n\n\n\n\n");
					}
					else if(ply.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.arrow))){
	//					if(isDragonRes(w,x,y,z)){
	//						System.out.println("IT WORKED");
	//					}
					}
				}
			}
			else if(listContainsStack(ents, new ItemStack(Items.clock), false)){
				if (!w.isRemote) {
					if(w.getBlock(x, y-1, z).equals(Blocks.gold_block)){
				        w.addWeatherEffect(new EntityLightningBolt(w, x, y, z));
				        w.setWorldTime((w.getWorldTime() / 24000) * 24000 + 24000);
				        listContainsStack(ents, new ItemStack(Items.clock), true);
					}
					else if(w.getBlock(x, y-1, z).equals(Blocks.lapis_block)){
				        w.addWeatherEffect(new EntityLightningBolt(w, x, y, z));
				        w.setWorldTime((w.getWorldTime() / 24000) * 24000 + 13800);
				        listContainsStack(ents, new ItemStack(Items.clock), true);
					}
					else if(w.getBlock(x, y-1, z).equals(Blocks.diamond_block)
							&& (w.getBlock(x+1, y-1, z).equals(Blocks.emerald_block) && w.getBlock(x-1, y-1, z).equals(Blocks.emerald_block)
							|| w.getBlock(x, y-1, z+1).equals(Blocks.emerald_block) && w.getBlock(x, y-1, z-1).equals(Blocks.emerald_block))){
						w.addWeatherEffect(new EntityLightningBolt(w, x, y, z));
				        w.setWorldTime((w.getWorldTime() / 24000) * 24000 + 13800 + w.rand.nextInt(100) + ((8 - (w.getWorldTime() / 24000) % 8) * 24000));
				        listContainsStack(ents, new ItemStack(Items.clock), true);
					}
				}
			}
			if(ply.getCurrentEquippedItem().isItemEqual(new ItemStack(Items.brick))){
				Structures.allBlankGlyph(w,x,y,z);
			}
		}
		return false;
		
	}
	
    public int quantityDropped(Random r) {
        return 0;
    }
	
    public void setBlockBoundsForItemRender() {
        this.bounds(0);
    }
    
    private void bounds(int i) {
        byte b0 = 0;
        float f = (float)(1 * (1 + b0)) / 16.0F;
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }
    
    public boolean isOpaqueCube() {
        return false;
    }
    
    public boolean renderAsNormalBlock() {
        return false;
    }
    
    public boolean canPlaceBlockAt(World w, int x, int y, int z)
    {
        return super.canPlaceBlockAt(w, x, y, z) && this.canBlockStay(w, x, y, z);
    }
    
    public void onNeighborBlockChange(World w, int x, int y, int z, Block block)
    {
        this.func_150090_e(w, x, y, z);
    }

    private boolean func_150090_e(World w, int x, int y, int z)
    {
        if (!this.canBlockStay(w, x, y, z)){
            w.setBlockToAir(x, y, z);
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean canBlockStay(World w, int x, int y, int z) {
        return w.getBlock(x, y - 1, z).isOpaqueCube();
    }
    
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess iblock, int x, int y, int z, int i) {
        return i == 1 ? true : super.shouldSideBeRendered(iblock, x, y, z, i);
    }

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
//		if(meta == 1){
//			return new TileEntityGlyph();
//		}
		return null;
	}
}
