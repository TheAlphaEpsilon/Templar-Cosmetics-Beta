package tae.cosmetics.mods;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import tae.cosmetics.ColorCode;
import tae.cosmetics.settings.Setting;
import tae.cosmetics.util.PlayerUtils;

public class ThrownEntityTrails extends BaseMod {

	static public Setting<Boolean> enabled = new Setting<>("enabled thrown entity trails", false);
	
	static private HashSet<ThrownWrapper> entities = new HashSet<>();
	
	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if(enabled.getValue()) {
			Entity e = event.getEntity();
			if(e instanceof EntityArrow) {
				entities.add(new ThrownWrapper((EntityArrow) e));
			} else if(e instanceof EntityEnderEye) {
				entities.add(new ThrownWrapper((EntityEnderEye) e));
			} else if(e instanceof EntityFireball) {
				entities.add(new ThrownWrapper((EntityFireball) e));
			} else if(e instanceof EntityThrowable) {
				entities.add(new ThrownWrapper((EntityThrowable) e));
			}
		}
		
		if(entities.size() > 100) {
			entities.clear();
			PlayerUtils.sendMessage("All entity trail caches cleared due to high entity load.", ColorCode.RED);
		}
		
	}
	
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {
		if(event.phase == Phase.END) {

			Iterator<ThrownWrapper> iter = entities.iterator();
			
			while(iter.hasNext()) {
				ThrownWrapper tw = iter.next();
				
				if(tw.entity.isEntityAlive() && tw.isMoving()) {
					tw.updateLocation();
				}
				
				if(!tw.isAlive()) {
					iter.remove();
				}
		
			}
	
		}
	}
	
	@SubscribeEvent
	public void onDraw(RenderWorldLastEvent event) {
		
		Entity render = mc.getRenderViewEntity();
		
		for(ThrownWrapper tw : new HashSet<ThrownWrapper>(entities)) { //Prevents con mod exceptions Different threads?
			
			double d0 = render.prevPosX + (render.posX - render.prevPosX) * mc.getRenderPartialTicks();
			double d1 = render.prevPosY + (render.posY - render.prevPosY) * mc.getRenderPartialTicks();
			double d2 = render.prevPosZ + (render.posZ - render.prevPosZ) * mc.getRenderPartialTicks();
			
			tw.draw(d0, d1, d2);
						
		}
		
	}
	
	static class ThrownWrapper {
		
		private Color color;
		private Entity entity;
		private ArrayList<Location> path = new ArrayList<>();
		private String owner = "";
		
		private ThrownWrapper(EntityArrow e) {
			entity = e;
			color = Color.GREEN;
			Entity ownere = e.shootingEntity;
			if(ownere != null) {
				owner = e.getName();
			}
			
			if(owner.isEmpty()) {
				
				List<Entity> nearby = mc.world.getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().expand(1, 1, 1));
				
				HashSet<EntityPlayer> players = new HashSet<>();
				
				for(Entity entity : nearby) {
					if(entity instanceof EntityPlayer) {
						players.add((EntityPlayer) entity);
					}
				}
				
				float closest = 99; // should be impossible due to the 1x1x1 bounding box
				EntityPlayer nearest = null;
				
				for(EntityPlayer player : players) {
					float dist = e.getDistance(player);
					if(dist < closest) {
						closest = dist;
						nearest = player;
					}
				}

				if(closest != 99 && nearest != null) {
					owner = nearest.getName();
				}
				
				if(owner.isEmpty()) {
					
					HashSet<Block> blocksNearby = new HashSet<>();
					
					for(int x = -1; x < 2; x++) {
						for(int y = -1; y < 2; y++) {
							for(int z = -1; z < 2; z++) {
								
								blocksNearby.add(mc.world.getBlockState(new BlockPos(e.posX + x, e.posY + y, e.posZ + z)).getBlock());

							}
						}
					}
					
					for(Block block : blocksNearby) {
						
						if(block instanceof BlockDispenser) {
							
							owner = "Dispenser";
							break;
							
						}
						
					}
										
				}
				
			}
		}
		
		private ThrownWrapper(EntityEnderEye e) {
			entity = e;
			color = Color.BLACK;
		}
		
		private ThrownWrapper(EntityFireball e) {
			entity = e;
			color = Color.RED;
		}
		
		private ThrownWrapper(EntityThrowable e) {
			entity = e;
			color = Color.BLUE;
			
			NBTTagCompound tag = new NBTTagCompound();
			e.writeToNBT(tag);
			owner = tag.getString("ownerName");
			
			if(owner.isEmpty()) {
				
				List<Entity> nearby = mc.world.getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().expand(1, 1, 1));
				
				HashSet<EntityPlayer> players = new HashSet<>();
				
				for(Entity entity : nearby) {
					if(entity instanceof EntityPlayer) {
						players.add((EntityPlayer) entity);
					}
				}
				
				float closest = 99; // should be impossible due to the 1x1x1 bounding box
				EntityPlayer nearest = null;
				
				for(EntityPlayer player : players) {
					float dist = e.getDistance(player);
					if(dist < closest) {
						closest = dist;
						nearest = player;
					}
				}

				if(closest != 99 && nearest != null) {
					owner = nearest.getName();
				}
				
				if(owner.isEmpty()) {
					
					HashSet<Block> blocksNearby = new HashSet<>();
					
					for(int x = -1; x < 2; x++) {
						for(int y = -1; y < 2; y++) {
							for(int z = -1; z < 2; z++) {
								
								blocksNearby.add(mc.world.getBlockState(new BlockPos(e.posX + x, e.posY + y, e.posZ + z)).getBlock());

							}
						}
					}
					
					for(Block block : blocksNearby) {
						
						if(block instanceof BlockDispenser) {
							
							owner = "Dispenser";
							break;
							
						}
						
					}
										
				}
				
			}
		}
		
		private void updateLocation() {
			
			double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * mc.getRenderPartialTicks();
			double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * mc.getRenderPartialTicks();
			double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * mc.getRenderPartialTicks();
			
			path.add(new Location(d0, d1, d2));
		}
		
		private boolean isMoving() {
			return entity.posX != entity.lastTickPosX || entity.posY != entity.lastTickPosY || entity.posZ != entity.lastTickPosZ;
		}
		
		private boolean isAlive() {
			
			if(mc.world == null) {
				return false;
			}
			
			return !path.isEmpty();
		}
		
		private void drawNamePlate(double x0, double y0, double z0) {
			
			Entity render = mc.getRenderViewEntity();
			
			double d0 = render.prevPosX + (render.posX - render.prevPosX) * mc.getRenderPartialTicks();
			double d1 = render.prevPosY + (render.posY - render.prevPosY) * mc.getRenderPartialTicks();
			double d2 = render.prevPosZ + (render.posZ - render.prevPosZ) * mc.getRenderPartialTicks();
			
			Vec3d viewVec = new Vec3d(d0, d1, d2);
			
			Vec3d lastPos = new Vec3d(x0, y0, z0);
			
			Vec3d toDraw = lastPos.subtract(viewVec).normalize().scale(5);
			
			float x1 = (float)(toDraw.x);//toDraw.x;
			float y1 = (float)(toDraw.y + 2);//toDraw.y;
			float z1 = (float)(toDraw.z);//toDraw.z;
						
			if(!owner.isEmpty()) {
				
				RenderManager renderManager = mc.getRenderManager();
				float f0 = renderManager.playerViewY;
	            float f1 = renderManager.playerViewX;
	            boolean flag1 = renderManager.options.thirdPersonView == 2;
	            
	            EntityRenderer.drawNameplate(mc.fontRenderer, owner + "'s", x1, y1, z1, 0, f0, f1, flag1, false);
	            EntityRenderer.drawNameplate(mc.fontRenderer, entity.getName(), x1, y1, z1, 10, f0, f1, flag1, false);
				
			}
			
		}
		
		private void draw(double x, double y, double z) {
			
			if(path.isEmpty()) {
				return;
			}
			
			GlStateManager.pushMatrix();
						
			GlStateManager.shadeModel(7424);
			GlStateManager.depthMask(true);
			GlStateManager.enableCull();
			GlStateManager.disableBlend();
			GlStateManager.disableFog();
		        
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
			GlStateManager.glLineWidth(3);
			GlStateManager.disableTexture2D();
			GlStateManager.depthMask(false);
			GlStateManager.disableDepth();
			
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder builder = tessellator.getBuffer();
			
			builder.begin(GL11.GL_LINE_STRIP, DefaultVertexFormats.POSITION_COLOR);
			
			Location first = path.get(0);
			Location last = first;
			builder.pos(first.x - x, first.y - y, first.z - z).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();

			Iterator<Location> iter = path.iterator();
			
			while(iter.hasNext()) {
							
				Location trip = iter.next();
				
				if(trip.alpha < 0) {
					iter.remove();
				} else {
					last = trip;
					builder.pos(trip.x - x, trip.y - y, trip.z - z).color(color.getRed(), color.getGreen(), color.getBlue(), trip.alpha).endVertex();
					trip.alpha -= 2;
				}
			}
	
			builder.pos(last.x - x, last.y - y, last.z - z).color(color.getRed(), color.getGreen(), color.getBlue(), 0).endVertex();
			
			tessellator.draw();
			
			GlStateManager.depthMask(true);
			GlStateManager.enableTexture2D();
			GlStateManager.disableBlend();
			GlStateManager.enableDepth();
			
			GlStateManager.popMatrix();
			
			drawNamePlate(last.x, last.y, last.z);
			
		}
		
		@Override
		public boolean equals(Object obj) {
			if(this == obj) {
				return true;
			} else if(!(obj instanceof ThrownWrapper)) {
				return false;
			} else {
				return this.entity.equals(((ThrownWrapper) obj).entity);
			}
		}
		
		static class Location {
			
			private double x;
			private double y;
			private double z;
			private int alpha = 255;
			
			private Location(double x, double y, double z) {
				this.x = x;
				this.y = y;
				this.z = z;
			}
			
		}
		
	}
	
}
