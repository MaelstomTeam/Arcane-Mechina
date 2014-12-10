package com.maelstrom.arcaneMechina.client.renderer;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.model.ModelBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderer implements IItemRenderer {

	public ItemRenderer(ModelBase model) {
		this.model = model;
	}
	
	private enum TransformationTypes{NONE, DROPPED, INV, THIRDPERSON}
	private ModelBase model;
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		switch(type){
		case ENTITY:
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
		case INVENTORY:
			return true;
		default:
			return false;
		}
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		switch (type){

		case ENTITY:{
			return (helper == ItemRendererHelper.ENTITY_BOBBING || helper == ItemRendererHelper.ENTITY_ROTATION || helper == ItemRendererHelper.BLOCK_3D);
		}
		case EQUIPPED:{
			return (helper == ItemRendererHelper.BLOCK_3D || helper == ItemRendererHelper.EQUIPPED_BLOCK);
		}
		case EQUIPPED_FIRST_PERSON:{
			return (helper == ItemRendererHelper.EQUIPPED_BLOCK);
		}
		case INVENTORY:{
			return (helper == ItemRendererHelper.INVENTORY_BLOCK);
		}
		default:
			return false;
		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		TransformationTypes transformer = TransformationTypes.NONE;
		switch(type){
			case EQUIPPED:{
				GL11.glEnable(GL11.GL_CULL_FACE);
				transformer = TransformationTypes.THIRDPERSON;
				break;
			}
			case EQUIPPED_FIRST_PERSON:{
				break;
			}
			case INVENTORY:{
				GL11.glTranslatef(-.5f, -.5f, -.5f);
				transformer = TransformationTypes.INV;
				break;
			}
			case ENTITY:{
				GL11.glScalef(.5f, .5f, .5f);
				GL11.glTranslatef(-.5f, -.5f, -.5f);
				break;
			}
			default:
				break;
		}
		
		
		//entity, r, partial tick, g, b, scale?
		model.render(null, 1, 0, 1, 1, 0, 0.05f);
		
//		renderer
//		NONE, DROPPED, INV, THIRDPERSON
//		GL11.glColor4f(red, green, blue, alpha);
		
		switch(transformer){
			case NONE:
				break;
			case DROPPED:{
				GL11.glTranslated(.5f, .5f, .5f);
				GL11.glScaled(2f, 2f, 2f);
				break;
			}
			case INV:{
				GL11.glTranslated(.5f, .5f, .5f);
				break;
			}
			case THIRDPERSON:
				GL11.glDisable(GL11.GL_CULL_FACE);
			default:
				break;
		}
		
		
	}

}
