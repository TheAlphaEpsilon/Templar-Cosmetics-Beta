package tae.cosmetics.mods;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.packetevent.PacketEvent;

public class ModifiedFreecam extends BaseMod {

	/*
	private static Vec3d pos = Vec3d.ZERO;
	private static Vec2f pitchyaw = Vec2f.ZERO;
	*/
	
	  private static boolean isRidingEntity;
	
	  public static boolean enabled = false;
	  
	  private static Entity ridingEntity;
	  
	  private static EntityOtherPlayerMP originalPlayer;
	  
	  public static void onEnabled() {
	    if (mc.player == null || mc.world == null) {
	      return;
	    }
	    
	    enabled = true;
	    
	    if (isRidingEntity = mc.player.isRiding()) {
	      ridingEntity = mc.player.getRidingEntity();
	      mc.player.dismountRidingEntity();
	    } 
	    
	    /*
	    pos = mc.player.getPositionVector();
	    pitchyaw = mc.player.getPitchYaw();
	    */
	    
	    originalPlayer = new EntityOtherPlayerMP(mc.world, mc.getSession().getProfile());
	    originalPlayer.copyLocationAndAnglesFrom(mc.player);
	    originalPlayer.rotationYawHead = mc.player.rotationYawHead;
	    originalPlayer.inventory = mc.player.inventory;
	    originalPlayer.inventoryContainer = mc.player.inventoryContainer;
	    mc.world.addEntityToWorld(-100, originalPlayer);
	  }
	  
	  public static void onDisabled() {
	  
		  mc.addScheduledTask(() -> {
		    
			  	EntityPlayer player = mc.player;
			  
		        if (player == null || player.capabilities == null) {
		          return;
		        }
		        
		        PlayerCapabilities gmCaps = new PlayerCapabilities();
		        mc.playerController.getCurrentGameType().configurePlayerCapabilities(gmCaps);
		        
		        PlayerCapabilities capabilities = player.capabilities;
		        capabilities.allowFlying = gmCaps.allowFlying;
		        capabilities.isFlying = gmCaps.allowFlying && capabilities.isFlying;
		        capabilities.setFlySpeed(gmCaps.getFlySpeed());
		      });
		  
	    if (mc.player == null || originalPlayer == null) {
	      return;
	    }
	    
	    enabled = false;
	   
	    mc.world.removeEntityFromWorld(-100);
	    originalPlayer = null;
	    
	    mc.player.noClip = false;
	    mc.player.setVelocity(0, 0, 0);
	    
	    if (isRidingEntity) {
	    	mc.player.startRiding(ridingEntity, true);
	      ridingEntity = null;
	      isRidingEntity = false;
	    }
	  }
	  
	  @SubscribeEvent
	  public void onLocalPlayerUpdate(LivingUpdateEvent event) {
	    if (!enabled || mc.player == null || !event.getEntity().equals(mc.player)) {
	      return;
	    }
	    
	    
	    mc.addScheduledTask(() -> {
	        if (mc.player == null || mc.player.capabilities == null) {
	          return;
	        }
	        
	        mc.player.capabilities.allowFlying = true;
	        mc.player.capabilities.isFlying = true;
	      });
	    
	    
	    mc.player.capabilities.setFlySpeed(0.5F);
	    mc.player.noClip = true;
	    mc.player.onGround = false;
	    mc.player.fallDistance = 0;
	    
	    if (!mc.gameSettings.keyBindForward.isPressed()
	        && !mc.gameSettings.keyBindBack.isPressed()
	        && !mc.gameSettings.keyBindLeft.isPressed()
	        && !mc.gameSettings.keyBindRight.isPressed()
	        && !mc.gameSettings.keyBindJump.isPressed()
	        && !mc.gameSettings.keyBindSneak.isPressed()) {
	      mc.player.setVelocity(0, 0, 0);
	    }
	  }
	  
	  @SubscribeEvent
	  public void onPacketReceived(PacketEvent.Incoming event) {
	    if (!enabled || originalPlayer == null || mc.player == null) {
	      return;
	    }
	    
	    if (event.getPacket() instanceof SPacketPlayerPosLook) {
	      //SPacketPlayerPosLook packet = (SPacketPlayerPosLook) event.getPacket();
	      //pos = new Vec3d(packet.getX(), packet.getY(), packet.getZ());
	      //angle = Angle.degrees(packet.getPitch(), packet.getYaw());
	      event.setCanceled(true);
	    }
	  }
	  
	  @SubscribeEvent
	  public void onWorldLoad(WorldEvent.Load event) {
	    if (!enabled || originalPlayer == null || mc.player == null) {
	      return;
	    }
	    
	    //pos = mc.player.getPositionVector();
	    //angle = LocalPlayerUtils.getViewAngles();
	  }
	  
	  @SubscribeEvent
	  public void onEntityRender(RenderLivingEvent.Pre<?> event) {
	    if (originalPlayer != null
	        && mc.player != null
	        && mc.player.equals(event.getEntity())) {
	      event.setCanceled(true);
	    }
	  }
	  
	  @SubscribeEvent
	  public void onRenderTag(RenderLivingEvent.Specials.Pre<?> event) {
	    if (originalPlayer != null
	        && mc.player != null
	        && mc.player.equals(event.getEntity())) {
	      event.setCanceled(true);
	    }
	  }
	  	
}
