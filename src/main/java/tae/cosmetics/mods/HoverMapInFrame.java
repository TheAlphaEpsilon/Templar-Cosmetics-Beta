package tae.cosmetics.mods;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraftforge.client.event.RenderItemInFrameEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.ColorCode;

public class HoverMapInFrame extends BaseMod {
	
	@SubscribeEvent
	public void hoverMap(RenderItemInFrameEvent event) {

		if(!event.getItem().getItem().equals(Items.FILLED_MAP)) {
			return;
		}
		
        EntityItemFrame entity = event.getEntityItemFrame();
		RenderManager renderManager = event.getRenderer().getRenderManager();

		if (Minecraft.isGuiEnabled() && !entity.getDisplayedItem().isEmpty() && renderManager.pointedEntity == entity) {
            double dist = entity.getDistanceSq(renderManager.renderViewEntity);
    		
    		int id = entity.getDisplayedItem().getItemDamage();
    		
    		int rotation = entity.getRotation();
    		    		
    		GlStateManager.enableLighting();
    		
    		GlStateManager.rotate(-rotation * 90, 0, 0, 1);
    		
            if (dist <= 4096) {

            		boolean isThirdPerson = renderManager.options.thirdPersonView == 2;
                    
                    EntityRenderer.drawNameplate(
                    		renderManager.getFontRenderer(), 
                    		"ID: " + (id > -1 ? ColorCode.GREEN.getCode() : ColorCode.RED.getCode()) + id, 
                    		0, 
                    		entity.height + 0.25F, 
                    		-0.3F, 
                    		10, 
                    		renderManager.playerViewY - 90 * entity.facingDirection.getXOffset() + 180 * (entity.facingDirection.getZOffset() == 1 ? 1 : 0),
                    		renderManager.playerViewX,
                    		isThirdPerson, 
                    		false);
            }
            
            GlStateManager.rotate(rotation * 90, 0, 0, 1);
			
    		GlStateManager.disableLighting();
            
        }
		
	}
	
}
