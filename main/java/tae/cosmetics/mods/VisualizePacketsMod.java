package tae.cosmetics.mods;

import java.time.Instant;
import java.util.ArrayList;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;
import tae.cosmetics.gui.util.mainscreen.VisualizePacketsGuiList;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;
import tae.cosmetics.gui.util.packet.TimestampModule;
import tae.cosmetics.gui.util.packet.UnknownPacketModule;
import tae.cosmetics.gui.util.packet.client.*;
import tae.cosmetics.gui.util.packet.server.*;
import tae.packetevent.PacketEvent;

public class VisualizePacketsMod extends BaseMod {

	private static boolean enabled = false;
	
	private static VisualizePacketsGuiList gui = new VisualizePacketsGuiList("Caught Packet:", 100, 50);
	
	public static ArrayList<AbstractPacketModule> modulesToDraw = new ArrayList<>();

	public static void toggle() {
		enabled = enabled ? false : true;
		if(enabled) {
			modulesToDraw = new ArrayList<>();
		}
	}
	
	public static VisualizePacketsGuiList getGuiTitleCopy() {
		return new VisualizePacketsGuiList(gui.getTitle(), gui.x, gui.y);
	}
	
	public static void updateGui(int x, int y) {
		gui.x = x;
		gui.y = y;
	}
	
	@SubscribeEvent
	public void onDisconnect(ClientDisconnectionFromServerEvent event) {
		enabled = false;
	}
	
	@SubscribeEvent (priority = EventPriority.LOWEST)
	public void onSendPacket(PacketEvent.Outgoing event) {
		
		if(enabled && !event.isCanceled()) {
			gui.addPacket(event.getPacket());
			addClientPacket(event.getPacket(), Instant.now().toEpochMilli());
		}
		
	}
	
	@SubscribeEvent (priority = EventPriority.LOWEST)
	public void onReceivePacket(PacketEvent.Incoming event) {
		
		if(enabled && !event.isCanceled()) {
			gui.addPacket(event.getPacket());
			addServerPacket(event.getPacket(), Instant.now().toEpochMilli());
		}
		
	}
	
	@SubscribeEvent
	public void onDrawGameScreen(RenderGameOverlayEvent event) {
		
		if(!enabled || event.getType() != ElementType.TEXT) {
			return;
		}
		
		gui.draw(-1, -1);
	
	}
	
	public static void addMarker(TimestampModule module) {
		modulesToDraw.add(module);
	}
	
	//Epic coder skillz upcoming
	
	private static void addClientPacket(Packet<?> packet, long timestamp) {
	
		if(packet instanceof CPacketAnimation) {
			modulesToDraw.add(new CPacketAnimationModule((CPacketAnimation) packet, timestamp));
		} else if(packet instanceof CPacketChatMessage) {
			modulesToDraw.add(new CPacketChatMessageModule((CPacketChatMessage) packet, timestamp));
		} else if(packet instanceof CPacketClickWindow) {
			modulesToDraw.add(new CPacketClickWindowModule((CPacketClickWindow) packet, timestamp));
		} else if(packet instanceof CPacketClientSettings) {
			modulesToDraw.add(new CPacketClientSettingsModule((CPacketClientSettings) packet, timestamp));
		} else if(packet instanceof CPacketClientStatus) {
			modulesToDraw.add(new CPacketClientStatusModule((CPacketClientStatus) packet, timestamp));
		} else if(packet instanceof CPacketCloseWindow) {
			modulesToDraw.add(new CPacketCloseWindowModule((CPacketCloseWindow) packet, timestamp));
		} else if(packet instanceof CPacketConfirmTeleport) {
			modulesToDraw.add(new CPacketConfirmTeleportModule((CPacketConfirmTeleport) packet, timestamp));
		} else if(packet instanceof CPacketConfirmTransaction) {
			modulesToDraw.add(new CPacketConfirmTransactionModule((CPacketConfirmTransaction) packet, timestamp));
		} else if(packet instanceof CPacketCreativeInventoryAction) {
			modulesToDraw.add(new CPacketCreativeInventoryActionModule((CPacketCreativeInventoryAction) packet, timestamp));
		} else if(packet instanceof CPacketCustomPayload) {
			modulesToDraw.add(new CPacketCustomPayloadModule((CPacketCustomPayload) packet, timestamp));
		} else if(packet instanceof CPacketEnchantItem) {
			modulesToDraw.add(new CPacketEnchantItemModule((CPacketEnchantItem) packet, timestamp));
		} else if(packet instanceof CPacketEntityAction) {
			modulesToDraw.add(new CPacketEntityActionModule((CPacketEntityAction) packet, timestamp));
		} else if(packet instanceof CPacketHeldItemChangeModule) {
			modulesToDraw.add(new CPacketHeldItemChangeModule((CPacketHeldItemChange) packet, timestamp));
		} else if(packet instanceof CPacketInput) {
			modulesToDraw.add(new CPacketInputModule((CPacketInput) packet, timestamp));
		} else if(packet instanceof CPacketKeepAlive) {
			modulesToDraw.add(new CPacketKeepAliveModule((CPacketKeepAlive) packet, timestamp));
		} else if(packet instanceof CPacketPing) {
			modulesToDraw.add(new CPacketPingModule((CPacketPing) packet, timestamp));
		} else if(packet instanceof CPacketPlayerAbilities) {
			modulesToDraw.add(new CPacketPlayerAbilitiesModule((CPacketPlayerAbilities) packet, timestamp));
		} else if(packet instanceof CPacketPlayerDigging) {
			modulesToDraw.add(new CPacketPlayerDiggingModule((CPacketPlayerDigging) packet, timestamp));
		} else if(packet instanceof CPacketPlayer) {
			modulesToDraw.add(new CPacketPlayerModule((CPacketPlayer) packet, timestamp));
		} else if(packet instanceof CPacketPlayerTryUseItem) {
			modulesToDraw.add(new CPacketPlayerTryUseItemModule((CPacketPlayerTryUseItem) packet, timestamp));
		} else if(packet instanceof CPacketPlayerTryUseItemOnBlock) {
			modulesToDraw.add(new CPacketPlayerTryUseItemOnBlockModule((CPacketPlayerTryUseItemOnBlock) packet, timestamp));
		} else if(packet instanceof CPacketRecipeInfo) {
			modulesToDraw.add(new CPacketRecipeInfoModule((CPacketRecipeInfo) packet, timestamp));
		} else if(packet instanceof CPacketResourcePackStatus) {
			modulesToDraw.add(new CPacketResourcePackStatusModule((CPacketResourcePackStatus) packet, timestamp));
		} else if(packet instanceof CPacketSeenAdvancements) {
			modulesToDraw.add(new CPacketSeenAdvancementsModule((CPacketSeenAdvancements) packet, timestamp));
		} else if(packet instanceof CPacketSpectate) {
			modulesToDraw.add(new CPacketSpectateModule((CPacketSpectate) packet, timestamp));
		} else if(packet instanceof CPacketSteerBoat) {
			modulesToDraw.add(new CPacketSteerBoatModule((CPacketSteerBoat) packet, timestamp));
		} else if(packet instanceof CPacketTabComplete) {
			modulesToDraw.add(new CPacketTabCompleteModule((CPacketTabComplete) packet, timestamp));
		} else if(packet instanceof CPacketUpdateSign) {
			modulesToDraw.add(new CPacketUpdateSignModule((CPacketUpdateSign) packet, timestamp));
		} else if(packet instanceof CPacketUseEntity) {
			modulesToDraw.add(new CPacketUseEntityModule((CPacketUseEntity) packet, timestamp));
		} else if(packet instanceof CPacketVehicleMove) {
			modulesToDraw.add(new CPacketVehicleMoveModule((CPacketVehicleMove) packet, timestamp));
		} else {
			modulesToDraw.add(new UnknownPacketModule(packet, timestamp, true));
		}
		
	}
	
	private static void addServerPacket(Packet<?> packet, long timestamp) {

		if(packet instanceof SPacketAdvancementInfo) {
			modulesToDraw.add(new SPacketAdvancementInfoModule((SPacketAdvancementInfo) packet, timestamp));
		} else if(packet instanceof SPacketAnimation) {
			modulesToDraw.add(new SPacketAnimationModule((SPacketAnimation) packet, timestamp));
		} else if(packet instanceof SPacketBlockAction) {
			modulesToDraw.add(new SPacketBlockActionModule((SPacketBlockAction) packet, timestamp));
		} else if(packet instanceof SPacketBlockBreakAnim) {
			modulesToDraw.add(new SPacketBlockBreakAnimModule((SPacketBlockBreakAnim) packet, timestamp));
		} else if(packet instanceof SPacketBlockChange) {
			modulesToDraw.add(new SPacketBlockChangeModule((SPacketBlockChange) packet, timestamp));
		} else if(packet instanceof SPacketCamera) {
			modulesToDraw.add(new SPacketCameraModule((SPacketCamera) packet, timestamp));
		} else if(packet instanceof SPacketChangeGameState) {
			modulesToDraw.add(new SPacketChangeGameStateModule((SPacketChangeGameState) packet, timestamp));
		} else if(packet instanceof SPacketChat) {
			modulesToDraw.add(new SPacketChatModule((SPacketChat) packet, timestamp));
		} else if(packet instanceof SPacketChunkData) {
			modulesToDraw.add(new SPacketChunkDataModule((SPacketChunkData) packet, timestamp));
		} else if(packet instanceof SPacketCloseWindow) {
			modulesToDraw.add(new SPacketCloseWindowModule((SPacketCloseWindow) packet, timestamp));
		} else if(packet instanceof SPacketCollectItem) {
			modulesToDraw.add(new SPacketCollectItemModule((SPacketCollectItem) packet, timestamp));
		} else if(packet instanceof SPacketCombatEvent) {
			modulesToDraw.add(new SPacketCombatEventModule((SPacketCombatEvent) packet, timestamp));
		} else if(packet instanceof SPacketConfirmTransaction) {
			modulesToDraw.add(new SPacketConfirmTransactionModule((SPacketConfirmTransaction) packet, timestamp));
		} else if(packet instanceof SPacketCooldown) {
			modulesToDraw.add(new SPacketCooldownModule((SPacketCooldown) packet, timestamp));
		} else if(packet instanceof SPacketCustomPayload) {
			modulesToDraw.add(new SPacketCustomPayloadModule((SPacketCustomPayload) packet, timestamp));
		} else if(packet instanceof SPacketCustomSound) {
			modulesToDraw.add(new SPacketCustomSoundModule((SPacketCustomSound) packet, timestamp));
		} else if(packet instanceof SPacketDestroyEntities) {
			modulesToDraw.add(new SPacketDestroyEntitiesModule((SPacketDestroyEntities) packet, timestamp));
		} else if(packet instanceof SPacketDisplayObjective) {
			modulesToDraw.add(new SPacketDisplayObjectiveModule((SPacketDisplayObjective) packet, timestamp));
		} else if(packet instanceof SPacketEffect) {
			modulesToDraw.add(new SPacketEffectModule((SPacketEffect) packet, timestamp));
		} else if(packet instanceof SPacketEntity) {
			modulesToDraw.add(new SPacketEntityModule((SPacketEntity) packet, timestamp));
		} else if(packet instanceof SPacketEntityAttach) {
			modulesToDraw.add(new SPacketEntityAttachModule((SPacketEntityAttach) packet, timestamp));
		} else if(packet instanceof SPacketEntityEffect) {
			modulesToDraw.add(new SPacketEntityEffectModule((SPacketEntityEffect) packet, timestamp));
		} else if(packet instanceof SPacketEntityEquipment) {
			modulesToDraw.add(new SPacketEntityEquipmentModule((SPacketEntityEquipment) packet, timestamp));
		} else if(packet instanceof SPacketEntityHeadLook) {
			modulesToDraw.add(new SPacketEntityHeadLookModule((SPacketEntityHeadLook) packet, timestamp));
		} else if(packet instanceof SPacketEntityMetadata) {
			modulesToDraw.add(new SPacketEntityMetadataModule((SPacketEntityMetadata) packet, timestamp));
		} else if(packet instanceof SPacketEntityProperties) {
			modulesToDraw.add(new SPacketEntityPropertiesModule((SPacketEntityProperties) packet, timestamp));
		} else if(packet instanceof SPacketEntityStatus) {
			modulesToDraw.add(new SPacketEntityStatusModule((SPacketEntityStatus) packet, timestamp));
		} else if(packet instanceof SPacketEntityTeleport) {
			modulesToDraw.add(new SPacketEntityTeleportModule((SPacketEntityTeleport) packet, timestamp));
		} else if(packet instanceof SPacketEntityVelocity) {
			modulesToDraw.add(new SPacketEntityVelocityModule((SPacketEntityVelocity) packet, timestamp));
		} else if(packet instanceof SPacketExplosion) {
			modulesToDraw.add(new SPacketExplosionModule((SPacketExplosion) packet, timestamp));
		} else if(packet instanceof SPacketHeldItemChange) {
			modulesToDraw.add(new SPacketHeldItemChangeModule((SPacketHeldItemChange) packet, timestamp));
		} else if(packet instanceof SPacketKeepAlive) {
			modulesToDraw.add(new SPacketKeepAliveModule((SPacketKeepAlive) packet, timestamp));
		} else if(packet instanceof SPacketMaps) {
			modulesToDraw.add(new SPacketMapsModule((SPacketMaps) packet, timestamp));
		} else if(packet instanceof SPacketMoveVehicle) {
			modulesToDraw.add(new SPacketMoveVehicleModule((SPacketMoveVehicle) packet, timestamp));
		} else if(packet instanceof SPacketMultiBlockChange) {
			modulesToDraw.add(new SPacketMultiBlockChangeModule((SPacketMultiBlockChange) packet, timestamp));
		} else if(packet instanceof SPacketOpenWindow) {
			modulesToDraw.add(new SPacketOpenWindowModule((SPacketOpenWindow) packet, timestamp));
		} else if(packet instanceof SPacketParticles) {
			modulesToDraw.add(new SPacketParticlesModule((SPacketParticles) packet, timestamp));
		} else if(packet instanceof SPacketPlayerAbilities) {
			modulesToDraw.add(new SPacketPlayerAbilitiesModule((SPacketPlayerAbilities) packet, timestamp));
		} else if(packet instanceof SPacketPlayerListHeaderFooter) {
			modulesToDraw.add(new SPacketPlayerListHeaderFooterModule((SPacketPlayerListHeaderFooter) packet, timestamp));
		} else if(packet instanceof SPacketPlayerListItem) {
			modulesToDraw.add(new SPacketPlayerListItemModule((SPacketPlayerListItem) packet, timestamp));
		} else if(packet instanceof SPacketPlayerPosLook) {
			modulesToDraw.add(new SPacketPlayerPosLookModule((SPacketPlayerPosLook) packet, timestamp));
		} else if(packet instanceof SPacketPong) {
			modulesToDraw.add(new SPacketPongModule((SPacketPong) packet, timestamp));
		} else if(packet instanceof SPacketRecipeBook) {
			modulesToDraw.add(new SPacketRecipeBookModule((SPacketRecipeBook) packet, timestamp));
		} else if(packet instanceof SPacketRemoveEntityEffect) {
			modulesToDraw.add(new SPacketRemoveEntityEffectModule((SPacketRemoveEntityEffect) packet, timestamp));
		} else if(packet instanceof SPacketResourcePackSend) {
			modulesToDraw.add(new SPacketResourcePackSendModule((SPacketResourcePackSend) packet, timestamp));
		} else if(packet instanceof SPacketRespawn) {
			modulesToDraw.add(new SPacketRespawnModule((SPacketRespawn) packet, timestamp));
		} else if(packet instanceof SPacketScoreboardObjective) {
			modulesToDraw.add(new SPacketScoreboardObjectiveModule((SPacketScoreboardObjective) packet, timestamp));
		} else if(packet instanceof SPacketSelectAdvancementsTab) {
			modulesToDraw.add(new SPacketSelectAdvancementsTabModule((SPacketSelectAdvancementsTab) packet, timestamp));
		} else if(packet instanceof SPacketServerDifficulty) {
			modulesToDraw.add(new SPacketServerDifficultyModule((SPacketServerDifficulty) packet, timestamp));
		} else if(packet instanceof SPacketServerInfo) {
			modulesToDraw.add(new SPacketServerInfoModule((SPacketServerInfo) packet, timestamp));
		} else if(packet instanceof SPacketSetExperience) {
			modulesToDraw.add(new SPacketSetExperienceModule((SPacketSetExperience) packet, timestamp));
		} else if(packet instanceof SPacketSetPassengers) {
			modulesToDraw.add(new SPacketSetPassengersModule((SPacketSetPassengers) packet, timestamp));
		} else if(packet instanceof SPacketSetSlot) {
			modulesToDraw.add(new SPacketSetSlotModule((SPacketSetSlot) packet, timestamp));
		} else if(packet instanceof SPacketSignEditorOpen) {
			modulesToDraw.add(new SPacketSignEditorOpenModule((SPacketSignEditorOpen) packet, timestamp));
		} else if(packet instanceof SPacketSoundEffect) {
			modulesToDraw.add(new SPacketSoundEffectModule((SPacketSoundEffect) packet, timestamp));
		} else if(packet instanceof SPacketSpawnExperienceOrb) {
			modulesToDraw.add(new SPacketSpawnExperienceOrbModule((SPacketSpawnExperienceOrb) packet, timestamp));
		} else if(packet instanceof SPacketSpawnGlobalEntity) {
			modulesToDraw.add(new SPacketSpawnGlobalEntityModule((SPacketSpawnGlobalEntity) packet, timestamp));
		} else if(packet instanceof SPacketSpawnMob) {
			modulesToDraw.add(new SPacketSpawnMobModule((SPacketSpawnMob) packet, timestamp));
		} else if(packet instanceof SPacketSpawnObject) {
			modulesToDraw.add(new SPacketSpawnObjectModule((SPacketSpawnObject) packet, timestamp));
		} else if(packet instanceof SPacketSpawnPainting) {
			modulesToDraw.add(new SPacketSpawnPaintingModule((SPacketSpawnPainting) packet, timestamp));
		} else if(packet instanceof SPacketSpawnPlayer) {
			modulesToDraw.add(new SPacketSpawnPlayerModule((SPacketSpawnPlayer) packet, timestamp));
		} else if(packet instanceof SPacketSpawnPosition) {
			modulesToDraw.add(new SPacketSpawnPositionModule((SPacketSpawnPosition) packet, timestamp));
		} else if(packet instanceof SPacketStatistics) {
			modulesToDraw.add(new SPacketStatisticsModule((SPacketStatistics) packet, timestamp));
		} else if(packet instanceof SPacketTabComplete) {
			modulesToDraw.add(new SPacketTabCompleteModule((SPacketTabComplete) packet, timestamp));
		} else if(packet instanceof SPacketTeams) {
			modulesToDraw.add(new SPacketTeamsModule((SPacketTeams) packet, timestamp));
		} else if(packet instanceof SPacketTimeUpdate) {
			modulesToDraw.add(new SPacketTimeUpdateModule((SPacketTimeUpdate) packet, timestamp));
		} else if(packet instanceof SPacketTitle) {
			modulesToDraw.add(new SPacketTitleModule((SPacketTitle) packet, timestamp));
		} else if(packet instanceof SPacketUnloadChunk) {
			modulesToDraw.add(new SPacketUnloadChunkModule((SPacketUnloadChunk) packet, timestamp));
		} else if(packet instanceof SPacketUpdateBossInfo) {
			modulesToDraw.add(new SPacketUpdateBossInfoModule((SPacketUpdateBossInfo) packet, timestamp));
		} else if(packet instanceof SPacketUpdateHealth) {
			modulesToDraw.add(new SPacketUpdateHealthModule((SPacketUpdateHealth) packet, timestamp));
		} else if(packet instanceof SPacketUpdateScore) {
			modulesToDraw.add(new SPacketUpdateScoreModule((SPacketUpdateScore) packet, timestamp));
		} else if(packet instanceof SPacketUpdateTileEntity) {
			modulesToDraw.add(new SPacketUpdateTileEntityModule((SPacketUpdateTileEntity) packet, timestamp));
		} else if(packet instanceof SPacketUseBed) {
			modulesToDraw.add(new SPacketUseBedModule((SPacketUseBed) packet, timestamp));
		} else if(packet instanceof SPacketWindowItems) {
			modulesToDraw.add(new SPacketWindowItemsModule((SPacketWindowItems) packet, timestamp));
		} else if(packet instanceof SPacketWindowProperty) {
			modulesToDraw.add(new SPacketWindowPropertyModule((SPacketWindowProperty) packet, timestamp));
		} else if(packet instanceof SPacketWorldBorder) {
			modulesToDraw.add(new SPacketWorldBorderModule((SPacketWorldBorder) packet, timestamp));
		} else {
			modulesToDraw.add(new UnknownPacketModule(packet, timestamp, false));
		}
		
	}
}
