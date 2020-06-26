package tae.cosmetics.mods;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketPlayerAbilities;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.GuiScreenEvent.ActionPerformedEvent;
import net.minecraftforge.client.event.GuiScreenEvent.InitGuiEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tae.packetevent.PacketEvent;

public class DeathPlus extends BaseMod {

	private static GuiScreen oldGui;
	private static boolean freecam = false;
	private static NBTTagCompound old = new NBTTagCompound();
	
	private static double x;
	private static double y;
	private static double z;
	
	@SubscribeEvent
	public void initGui(InitGuiEvent.Post event) {
		
		if(event.getGui() instanceof GuiGameOver) {
			event.getButtonList().add(new GuiButton(42, 0, 0, 60, 20, "Explore"));
		}
		
	}
	
	@SubscribeEvent
	public void click(ActionPerformedEvent.Post event) {
		GuiButton button = event.getButton();
		if(button.id == 42 && mc.currentScreen instanceof GuiGameOver) {
			oldGui = event.getGui();
			freecam = true;
			
			if(mc.player == null || mc.player.capabilities == null) {
				return;
			}
			
			mc.player.capabilities.writeCapabilitiesToNBT(old);
			
			mc.player.setHealth(20.0F);
			
			mc.displayGuiScreen(null);

			mc.player.isDead = false;
			mc.player.noClip = true;
			mc.player.onGround = false;
		    mc.player.fallDistance = 0;

		    x = mc.player.posX;
		    y = mc.player.posY;
		    z = mc.player.posZ;
		   
		    
			mc.addScheduledTask(() -> {
				mc.player.capabilities.disableDamage = true;
				mc.player.capabilities.allowFlying = true;
				mc.player.capabilities.setFlySpeed(0.05F);
				mc.player.capabilities.isFlying = true;
			});
			
		}
	}
	
	@SubscribeEvent
	public void onLocalPlayerUpdate(LivingUpdateEvent event) {
		
		if (!freecam || mc.player == null || !event.getEntity().equals(mc.player)) {
			return;
		}
		
		mc.player.isDead = false;
		mc.player.noClip = true;
		mc.player.onGround = false;
	    mc.player.fallDistance = 0;
	    
	    if(mc.player.capabilities == null) {
	    	return;
	    }
	    
	    
	    mc.addScheduledTask(() -> {
			mc.player.capabilities.disableDamage = true;
			mc.player.capabilities.allowFlying = true;
			mc.player.capabilities.setFlySpeed(0.05F);
			mc.player.capabilities.isFlying = true;
		});
		
	}
	
	
	@SubscribeEvent
	public void cancelPackets(PacketEvent.Incoming event) {
		if(freecam && event.getPacket() instanceof SPacketPlayerAbilities) {
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onDrawGameScreen(RenderGameOverlayEvent event) {
		
		if(!freecam || event.getType() != ElementType.TEXT) {
			return;
		}
				
		String display = "Press ESC to return";
		
		int width = new ScaledResolution(mc).getScaledWidth();
		
		mc.fontRenderer.drawString(display, width / 2 - mc.fontRenderer.getStringWidth(display) / 2, 5, Color.GREEN.getRGB());
		
	}
	
	@SubscribeEvent
	public void onKey(TickEvent event) {
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && freecam) {
			
			freecam = false;
			
			mc.player.setHealth(0.0F);
			
			mc.player.noClip = false;
			
			mc.player.isDead = true;
			
			mc.player.dismountRidingEntity();
			
			/*
			mc.player.posX = x;
			mc.player.posY = y;
			mc.player.posZ = z;
			*/
			
			mc.addScheduledTask(() -> {
				
				if(mc.player != null && mc.player.capabilities != null) {
					mc.player.capabilities.readCapabilitiesFromNBT(old);
				}
				
				mc.displayGuiScreen(oldGui);
				
			});
		}
	}
	
	
	
}
