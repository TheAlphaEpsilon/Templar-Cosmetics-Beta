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
import tae.cosmetics.ColorCode;
import tae.cosmetics.gui.util.mainscreen.VisualizePacketsGuiList;
import tae.cosmetics.gui.util.packet.AbstractPacketModule;
import tae.cosmetics.gui.util.packet.TimestampModule;
import tae.cosmetics.gui.util.packet.UnknownPacketModule;
import tae.cosmetics.gui.util.packet.client.*;
import tae.cosmetics.gui.util.packet.server.*;
import tae.cosmetics.settings.Keybind;
import tae.cosmetics.settings.Setting;
import tae.cosmetics.util.PlayerUtils;
import tae.packetevent.PacketEvent;

public class VisualizePacketsMod extends BaseMod {

	private static boolean enabled = false;
	
	private static Setting<Integer> xCoord = new Setting<>("Visualize x Coord", 100);
	private static Setting<Integer> yCoord = new Setting<>("Visualize y Coord", 50);
	
	private static VisualizePacketsGuiList gui = new VisualizePacketsGuiList("Caught Packet:", xCoord.getValue(), yCoord.getValue());
	
	public static ArrayList<AbstractPacketModule> modulesToDraw = new ArrayList<>();

	public static final Keybind toggle = new Keybind("Start Tracking Packets",0, () -> {
		toggle();
		if(enabled) {
			PlayerUtils.sendMessage("Tracking Packets",ColorCode.LIGHT_PURPLE);
		} else {
			PlayerUtils.sendMessage("Stopped Tracking Packets", ColorCode.GREEN);
		}
	});
	
	public static final Keybind addMarker = new Keybind("Add Packet Marker",0, () -> {
		addMarker(new TimestampModule(Instant.now().toEpochMilli()));
	});
	
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
		xCoord.setValue(x);
		yCoord.setValue(y);
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
	public static AbstractPacketModule getModuleFromPacket(Packet<?> packet, long timestamp) {
		
		if(packet instanceof CPacketAnimation) {
			return new CPacketAnimationModule((CPacketAnimation) packet, timestamp);
		} else if(packet instanceof CPacketChatMessage) {
			return new CPacketChatMessageModule((CPacketChatMessage) packet, timestamp);
		} else if(packet instanceof CPacketClickWindow) {
			return new CPacketClickWindowModule((CPacketClickWindow) packet, timestamp);
		} else if(packet instanceof CPacketClientSettings) {
			return new CPacketClientSettingsModule((CPacketClientSettings) packet, timestamp);
		} else if(packet instanceof CPacketClientStatus) {
			return new CPacketClientStatusModule((CPacketClientStatus) packet, timestamp);
		} else if(packet instanceof CPacketCloseWindow) {
			return new CPacketCloseWindowModule((CPacketCloseWindow) packet, timestamp);
		} else if(packet instanceof CPacketConfirmTeleport) {
			return new CPacketConfirmTeleportModule((CPacketConfirmTeleport) packet, timestamp);
		} else if(packet instanceof CPacketConfirmTransaction) {
			return new CPacketConfirmTransactionModule((CPacketConfirmTransaction) packet, timestamp);
		} else if(packet instanceof CPacketCreativeInventoryAction) {
			return new CPacketCreativeInventoryActionModule((CPacketCreativeInventoryAction) packet, timestamp);
		} else if(packet instanceof CPacketCustomPayload) {
			return new CPacketCustomPayloadModule((CPacketCustomPayload) packet, timestamp);
		} else if(packet instanceof CPacketEnchantItem) {
			return new CPacketEnchantItemModule((CPacketEnchantItem) packet, timestamp);
		} else if(packet instanceof CPacketEntityAction) {
			return new CPacketEntityActionModule((CPacketEntityAction) packet, timestamp);
		} else if(packet instanceof CPacketHeldItemChangeModule) {
			return new CPacketHeldItemChangeModule((CPacketHeldItemChange) packet, timestamp);
		} else if(packet instanceof CPacketInput) {
			return new CPacketInputModule((CPacketInput) packet, timestamp);
		} else if(packet instanceof CPacketKeepAlive) {
			return new CPacketKeepAliveModule((CPacketKeepAlive) packet, timestamp);
		} else if(packet instanceof CPacketPing) {
			return new CPacketPingModule((CPacketPing) packet, timestamp);
		} else if(packet instanceof CPacketPlayerAbilities) {
			return new CPacketPlayerAbilitiesModule((CPacketPlayerAbilities) packet, timestamp);
		} else if(packet instanceof CPacketPlayerDigging) {
			return new CPacketPlayerDiggingModule((CPacketPlayerDigging) packet, timestamp);
		} else if(packet instanceof CPacketPlayer) {
			return new CPacketPlayerModule((CPacketPlayer) packet, timestamp);
		} else if(packet instanceof CPacketPlayerTryUseItem) {
			return new CPacketPlayerTryUseItemModule((CPacketPlayerTryUseItem) packet, timestamp);
		} else if(packet instanceof CPacketPlayerTryUseItemOnBlock) {
			return new CPacketPlayerTryUseItemOnBlockModule((CPacketPlayerTryUseItemOnBlock) packet, timestamp);
		} else if(packet instanceof CPacketRecipeInfo) {
			return new CPacketRecipeInfoModule((CPacketRecipeInfo) packet, timestamp);
		} else if(packet instanceof CPacketResourcePackStatus) {
			return new CPacketResourcePackStatusModule((CPacketResourcePackStatus) packet, timestamp);
		} else if(packet instanceof CPacketSeenAdvancements) {
			return new CPacketSeenAdvancementsModule((CPacketSeenAdvancements) packet, timestamp);
		} else if(packet instanceof CPacketSpectate) {
			return new CPacketSpectateModule((CPacketSpectate) packet, timestamp);
		} else if(packet instanceof CPacketSteerBoat) {
			return new CPacketSteerBoatModule((CPacketSteerBoat) packet, timestamp);
		} else if(packet instanceof CPacketTabComplete) {
			return new CPacketTabCompleteModule((CPacketTabComplete) packet, timestamp);
		} else if(packet instanceof CPacketUpdateSign) {
			return new CPacketUpdateSignModule((CPacketUpdateSign) packet, timestamp);
		} else if(packet instanceof CPacketUseEntity) {
			return new CPacketUseEntityModule((CPacketUseEntity) packet, timestamp);
		} else if(packet instanceof CPacketVehicleMove) {
			return new CPacketVehicleMoveModule((CPacketVehicleMove) packet, timestamp);
		} else if(packet instanceof SPacketAdvancementInfo) {
			return new SPacketAdvancementInfoModule((SPacketAdvancementInfo) packet, timestamp);
		} else if(packet instanceof SPacketAnimation) {
			return new SPacketAnimationModule((SPacketAnimation) packet, timestamp);
		} else if(packet instanceof SPacketBlockAction) {
			return new SPacketBlockActionModule((SPacketBlockAction) packet, timestamp);
		} else if(packet instanceof SPacketBlockBreakAnim) {
			return new SPacketBlockBreakAnimModule((SPacketBlockBreakAnim) packet, timestamp);
		} else if(packet instanceof SPacketBlockChange) {
			return new SPacketBlockChangeModule((SPacketBlockChange) packet, timestamp);
		} else if(packet instanceof SPacketCamera) {
			return new SPacketCameraModule((SPacketCamera) packet, timestamp);
		} else if(packet instanceof SPacketChangeGameState) {
			return new SPacketChangeGameStateModule((SPacketChangeGameState) packet, timestamp);
		} else if(packet instanceof SPacketChat) {
			return new SPacketChatModule((SPacketChat) packet, timestamp);
		} else if(packet instanceof SPacketChunkData) {
			return new SPacketChunkDataModule((SPacketChunkData) packet, timestamp);
		} else if(packet instanceof SPacketCloseWindow) {
			return new SPacketCloseWindowModule((SPacketCloseWindow) packet, timestamp);
		} else if(packet instanceof SPacketCollectItem) {
			return new SPacketCollectItemModule((SPacketCollectItem) packet, timestamp);
		} else if(packet instanceof SPacketCombatEvent) {
			return new SPacketCombatEventModule((SPacketCombatEvent) packet, timestamp);
		} else if(packet instanceof SPacketConfirmTransaction) {
			return new SPacketConfirmTransactionModule((SPacketConfirmTransaction) packet, timestamp);
		} else if(packet instanceof SPacketCooldown) {
			return new SPacketCooldownModule((SPacketCooldown) packet, timestamp);
		} else if(packet instanceof SPacketCustomPayload) {
			return new SPacketCustomPayloadModule((SPacketCustomPayload) packet, timestamp);
		} else if(packet instanceof SPacketCustomSound) {
			return new SPacketCustomSoundModule((SPacketCustomSound) packet, timestamp);
		} else if(packet instanceof SPacketDestroyEntities) {
			return new SPacketDestroyEntitiesModule((SPacketDestroyEntities) packet, timestamp);
		} else if(packet instanceof SPacketDisplayObjective) {
			return new SPacketDisplayObjectiveModule((SPacketDisplayObjective) packet, timestamp);
		} else if(packet instanceof SPacketEffect) {
			return new SPacketEffectModule((SPacketEffect) packet, timestamp);
		} else if(packet instanceof SPacketEntity) {
			return new SPacketEntityModule((SPacketEntity) packet, timestamp);
		} else if(packet instanceof SPacketEntityAttach) {
			return new SPacketEntityAttachModule((SPacketEntityAttach) packet, timestamp);
		} else if(packet instanceof SPacketEntityEffect) {
			return new SPacketEntityEffectModule((SPacketEntityEffect) packet, timestamp);
		} else if(packet instanceof SPacketEntityEquipment) {
			return new SPacketEntityEquipmentModule((SPacketEntityEquipment) packet, timestamp);
		} else if(packet instanceof SPacketEntityHeadLook) {
			return new SPacketEntityHeadLookModule((SPacketEntityHeadLook) packet, timestamp);
		} else if(packet instanceof SPacketEntityMetadata) {
			return new SPacketEntityMetadataModule((SPacketEntityMetadata) packet, timestamp);
		} else if(packet instanceof SPacketEntityProperties) {
			return new SPacketEntityPropertiesModule((SPacketEntityProperties) packet, timestamp);
		} else if(packet instanceof SPacketEntityStatus) {
			return new SPacketEntityStatusModule((SPacketEntityStatus) packet, timestamp);
		} else if(packet instanceof SPacketEntityTeleport) {
			return new SPacketEntityTeleportModule((SPacketEntityTeleport) packet, timestamp);
		} else if(packet instanceof SPacketEntityVelocity) {
			return new SPacketEntityVelocityModule((SPacketEntityVelocity) packet, timestamp);
		} else if(packet instanceof SPacketExplosion) {
			return new SPacketExplosionModule((SPacketExplosion) packet, timestamp);
		} else if(packet instanceof SPacketHeldItemChange) {
			return new SPacketHeldItemChangeModule((SPacketHeldItemChange) packet, timestamp);
		} else if(packet instanceof SPacketKeepAlive) {
			return new SPacketKeepAliveModule((SPacketKeepAlive) packet, timestamp);
		} else if(packet instanceof SPacketMaps) {
			return new SPacketMapsModule((SPacketMaps) packet, timestamp);
		} else if(packet instanceof SPacketMoveVehicle) {
			return new SPacketMoveVehicleModule((SPacketMoveVehicle) packet, timestamp);
		} else if(packet instanceof SPacketMultiBlockChange) {
			return new SPacketMultiBlockChangeModule((SPacketMultiBlockChange) packet, timestamp);
		} else if(packet instanceof SPacketOpenWindow) {
			return new SPacketOpenWindowModule((SPacketOpenWindow) packet, timestamp);
		} else if(packet instanceof SPacketParticles) {
			return new SPacketParticlesModule((SPacketParticles) packet, timestamp);
		} else if(packet instanceof SPacketPlayerAbilities) {
			return new SPacketPlayerAbilitiesModule((SPacketPlayerAbilities) packet, timestamp);
		} else if(packet instanceof SPacketPlayerListHeaderFooter) {
			return new SPacketPlayerListHeaderFooterModule((SPacketPlayerListHeaderFooter) packet, timestamp);
		} else if(packet instanceof SPacketPlayerListItem) {
			return new SPacketPlayerListItemModule((SPacketPlayerListItem) packet, timestamp);
		} else if(packet instanceof SPacketPlayerPosLook) {
			return new SPacketPlayerPosLookModule((SPacketPlayerPosLook) packet, timestamp);
		} else if(packet instanceof SPacketPong) {
			return new SPacketPongModule((SPacketPong) packet, timestamp);
		} else if(packet instanceof SPacketRecipeBook) {
			return new SPacketRecipeBookModule((SPacketRecipeBook) packet, timestamp);
		} else if(packet instanceof SPacketRemoveEntityEffect) {
			return new SPacketRemoveEntityEffectModule((SPacketRemoveEntityEffect) packet, timestamp);
		} else if(packet instanceof SPacketResourcePackSend) {
			return new SPacketResourcePackSendModule((SPacketResourcePackSend) packet, timestamp);
		} else if(packet instanceof SPacketRespawn) {
			return new SPacketRespawnModule((SPacketRespawn) packet, timestamp);
		} else if(packet instanceof SPacketScoreboardObjective) {
			return new SPacketScoreboardObjectiveModule((SPacketScoreboardObjective) packet, timestamp);
		} else if(packet instanceof SPacketSelectAdvancementsTab) {
			return new SPacketSelectAdvancementsTabModule((SPacketSelectAdvancementsTab) packet, timestamp);
		} else if(packet instanceof SPacketServerDifficulty) {
			return new SPacketServerDifficultyModule((SPacketServerDifficulty) packet, timestamp);
		} else if(packet instanceof SPacketServerInfo) {
			return new SPacketServerInfoModule((SPacketServerInfo) packet, timestamp);
		} else if(packet instanceof SPacketSetExperience) {
			return new SPacketSetExperienceModule((SPacketSetExperience) packet, timestamp);
		} else if(packet instanceof SPacketSetPassengers) {
			return new SPacketSetPassengersModule((SPacketSetPassengers) packet, timestamp);
		} else if(packet instanceof SPacketSetSlot) {
			return new SPacketSetSlotModule((SPacketSetSlot) packet, timestamp);
		} else if(packet instanceof SPacketSignEditorOpen) {
			return new SPacketSignEditorOpenModule((SPacketSignEditorOpen) packet, timestamp);
		} else if(packet instanceof SPacketSoundEffect) {
			return new SPacketSoundEffectModule((SPacketSoundEffect) packet, timestamp);
		} else if(packet instanceof SPacketSpawnExperienceOrb) {
			return new SPacketSpawnExperienceOrbModule((SPacketSpawnExperienceOrb) packet, timestamp);
		} else if(packet instanceof SPacketSpawnGlobalEntity) {
			return new SPacketSpawnGlobalEntityModule((SPacketSpawnGlobalEntity) packet, timestamp);
		} else if(packet instanceof SPacketSpawnMob) {
			return new SPacketSpawnMobModule((SPacketSpawnMob) packet, timestamp);
		} else if(packet instanceof SPacketSpawnObject) {
			return new SPacketSpawnObjectModule((SPacketSpawnObject) packet, timestamp);
		} else if(packet instanceof SPacketSpawnPainting) {
			return new SPacketSpawnPaintingModule((SPacketSpawnPainting) packet, timestamp);
		} else if(packet instanceof SPacketSpawnPlayer) {
			return new SPacketSpawnPlayerModule((SPacketSpawnPlayer) packet, timestamp);
		} else if(packet instanceof SPacketSpawnPosition) {
			return new SPacketSpawnPositionModule((SPacketSpawnPosition) packet, timestamp);
		} else if(packet instanceof SPacketStatistics) {
			return new SPacketStatisticsModule((SPacketStatistics) packet, timestamp);
		} else if(packet instanceof SPacketTabComplete) {
			return new SPacketTabCompleteModule((SPacketTabComplete) packet, timestamp);
		} else if(packet instanceof SPacketTeams) {
			return new SPacketTeamsModule((SPacketTeams) packet, timestamp);
		} else if(packet instanceof SPacketTimeUpdate) {
			return new SPacketTimeUpdateModule((SPacketTimeUpdate) packet, timestamp);
		} else if(packet instanceof SPacketTitle) {
			return new SPacketTitleModule((SPacketTitle) packet, timestamp);
		} else if(packet instanceof SPacketUnloadChunk) {
			return new SPacketUnloadChunkModule((SPacketUnloadChunk) packet, timestamp);
		} else if(packet instanceof SPacketUpdateBossInfo) {
			return new SPacketUpdateBossInfoModule((SPacketUpdateBossInfo) packet, timestamp);
		} else if(packet instanceof SPacketUpdateHealth) {
			return new SPacketUpdateHealthModule((SPacketUpdateHealth) packet, timestamp);
		} else if(packet instanceof SPacketUpdateScore) {
			return new SPacketUpdateScoreModule((SPacketUpdateScore) packet, timestamp);
		} else if(packet instanceof SPacketUpdateTileEntity) {
			return new SPacketUpdateTileEntityModule((SPacketUpdateTileEntity) packet, timestamp);
		} else if(packet instanceof SPacketUseBed) {
			return new SPacketUseBedModule((SPacketUseBed) packet, timestamp);
		} else if(packet instanceof SPacketWindowItems) {
			return new SPacketWindowItemsModule((SPacketWindowItems) packet, timestamp);
		} else if(packet instanceof SPacketWindowProperty) {
			return new SPacketWindowPropertyModule((SPacketWindowProperty) packet, timestamp);
		} else if(packet instanceof SPacketWorldBorder) {
			return new SPacketWorldBorderModule((SPacketWorldBorder) packet, timestamp);
		} else {
			return null;
		}
	}
	
	
	private static void addClientPacket(Packet<?> packet, long timestamp) {
	
		AbstractPacketModule module = getModuleFromPacket(packet, timestamp);
		
		if(module == null) {
			module = new UnknownPacketModule(packet, timestamp, true);
		}
		
		modulesToDraw.add(module);
		
	}
	
	private static void addServerPacket(Packet<?> packet, long timestamp) {

		AbstractPacketModule module = getModuleFromPacket(packet, timestamp);
		
		if(module == null) {
			module = new UnknownPacketModule(packet, timestamp, false);
		}
		
		modulesToDraw.add(module);
		
	}
}
