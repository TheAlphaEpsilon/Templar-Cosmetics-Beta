package tae.cosmetics.mods;

import java.time.Instant;
import java.util.ArrayList;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.ColorCode;
import tae.cosmetics.settings.Keybind;
import tae.cosmetics.settings.Setting;
import tae.cosmetics.util.FileHelper;
import tae.cosmetics.util.PlayerUtils;
import tae.packetevent.PacketEvent;

public class PearlTracking extends BaseMod {

	private static final String fileName = "PearlLog.txt";
	
	static {
	
		FileHelper.createFile(fileName);
		
	}
	
	public static Setting<Boolean> enabled = new Setting<>("Toggle Pearl Tracking", false);
	
	public static final Keybind toggle = new Keybind("Toggle Pearl Tracking",0, () -> {
		enabled.setValue((boolean)PearlTracking.enabled.getValue() ? false : true);
		
		if((boolean)enabled.getValue()) {
			PlayerUtils.sendMessage("Pearl Tracking Enabled", ColorCode.LIGHT_PURPLE);
		} else {
			PlayerUtils.sendMessage("Pearl Tracking Disabled", ColorCode.LIGHT_PURPLE);
		}
	});

	private static boolean usedItem = false;
	
	@SubscribeEvent
	public void useItem(PacketEvent.Outgoing event) {
		
		if((boolean)enabled.getValue() && event.getPacket() instanceof CPacketPlayerTryUseItem) {
			CPacketPlayerTryUseItem packet = (CPacketPlayerTryUseItem) event.getPacket();
			EnumHand hand = packet.getHand();
			ItemStack stack;
			switch(hand) {
			case OFF_HAND:
				stack = mc.player.getHeldItemOffhand();
				break;
			default:
				stack = mc.player.getHeldItemMainhand();
			}
			
			if(stack.getItem().equals(Items.ENDER_PEARL)) {
				usedItem = true;
			}
			
		}
		
	}
	
	@SubscribeEvent
	public void packetIn(EntityJoinWorldEvent event) {
		
		if((boolean)enabled.getValue() && usedItem && event.getEntity() instanceof EntityEnderPearl) {
			
			usedItem = false;
			
			StringBuilder toWrite = new StringBuilder();
			
			ArrayList<String> readData = FileHelper.readFile(fileName);
			
			for(String s : readData) {
				toWrite.append(s + "\n");
			}
			
			toWrite.append("Timestamp: " + Instant.now().getEpochSecond() + ", ");
			toWrite.append("Player Name: " + mc.getSession().getUsername() + ", ");
			toWrite.append("Dimension: " + mc.world.provider.getDimensionType() + ", ");
			toWrite.append("X: " + (int)mc.player.posX + ", ");
			toWrite.append("Z: " + (int)mc.player.posZ + ", ");
			
			float yaw = mc.player.rotationYaw;
			
			toWrite.append("Yaw: " + yaw + " (");
			
			yaw = (yaw + 360) % 360;
			
			if(yaw < 22.5) {
				toWrite.append("+Z)");
			} else if(yaw >= 22.5 && yaw < 67.5) {
				toWrite.append("+Z/-X)");
			} else if(yaw >= 67.5 && yaw < 112.5) {
				toWrite.append("-X)");
			} else if(yaw >= 112.5 && yaw < 157.5) {
				toWrite.append("-X/-Z)");
			} else if(yaw >= 157.5 && yaw < 202.5) {
				toWrite.append("-Z)");
			} else if(yaw >= 202.5 && yaw < 247.5) {
				toWrite.append("-Z/+X)");
			} else if(yaw >= 247.5 && yaw < 292.5) {
				toWrite.append("+X)");
			} else if(yaw >= 292.5 && yaw < 337.5) {
				toWrite.append("+X/+Z)");
			} else if(yaw >= 337.5 && yaw < 360) {
				toWrite.append("+Z)");
			}
			
			FileHelper.writeFile(fileName, toWrite.toString());
			PlayerUtils.sendMessage("Pearl Logged", ColorCode.GREEN);
			
		}
		
	}
	
}
