package tae.cosmetics.mods;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.ColorCode;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.util.PlayerUtils;
import tae.packetevent.PacketEvent;

public class PearlTracking extends BaseMod {

	private static final String fileName = "TemplarCosmetics/PearlLog.txt";
	private static final File file = new File(fileName);
	
	static {
	
		new File("TemplarCosmetics").mkdir();
	
		try {
			file.createNewFile();
		} catch (IOException e) {
			new TAEModException(PearlTracking.class, "Couldn't create " + fileName + " : " + e.getMessage()).post();
		}
		
	}
	
	public static boolean enabled = false;
	
	private static boolean usedItem = false;
	
	@SubscribeEvent
	public void useItem(PacketEvent.Outgoing event) {
		
		if(enabled && event.getPacket() instanceof CPacketPlayerTryUseItem) {
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
		
		if(enabled && usedItem && event.getEntity() instanceof EntityEnderPearl) {
			
			usedItem = false;
			
			StringBuilder toWrite = new StringBuilder();
			
			ArrayList<String> readData = readFile(file);
			
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
			
			writeFile(file, toWrite.toString());
			
			
		}
		
	}
	
	private static void writeFile(File file, String toWrite) {
		try {
			FileWriter writer = new FileWriter(file);
			writer.write(toWrite);
			writer.close();
			PlayerUtils.sendMessage("Logged Pearl", ColorCode.GREEN);
		} catch (IOException e) {
			new TAEModException(PearlTracking.class, "Couldn't write " + fileName + " : " + e.getMessage()).post();

		}
	}
	
	private static ArrayList<String> readFile(File file) {
		
		try {
			Scanner scanner = new Scanner(file);
			
			ArrayList<String> toReturn = new ArrayList<>();
			
			while(scanner.hasNextLine()) {
				toReturn.add(scanner.nextLine());
			}
			
			scanner.close();
			
			return toReturn;
			
		} catch (FileNotFoundException e) {
			new TAEModException(PearlTracking.class, "Couldn't read " + fileName + " : " + e.getMessage()).post();
			return null;
		}
		
	}
	
}
