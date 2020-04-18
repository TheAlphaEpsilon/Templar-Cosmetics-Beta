package tae.cosmetics.mods;

import java.time.Instant;
import java.util.Date;

import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.ColorCode;
import tae.packetevent.PacketEvent;

public class TimeStampMod extends BaseMod {
	
	public static boolean enabled = true;
	public static boolean hour24 = true;
	public static boolean noDate = true;
	
	public static boolean nobold = true;
	public static boolean noitalic = true;
	public static boolean nounderline = true;
	public static ColorCode code = ColorCode.WHITE;
	
	@SubscribeEvent(priority = EventPriority.LOW)
	public void getChatEvent(PacketEvent.Incoming event) {
		if(enabled && event.getPacket() instanceof SPacketChat) {			
			SPacketChat packet = (SPacketChat) event.getPacket();
			
			sendMessage(getFormattedTimeAsString() + " " + packet.getChatComponent().getFormattedText());
			event.setCanceled(true);
			
		}
	}
	
	@SuppressWarnings("deprecation")
	public static String getFormattedTimeAsString() {
		Date now = Date.from(Instant.now());
		int hours = now.getHours();
		int minutes = now.getMinutes();
		int seconds = now.getSeconds();
		int year = now.getYear() + 1900;
		int month = now.getMonth() + 1;
		int day = now.getDate();
		
		String time = code.getCode();
		
		if(!nobold) {
			time += ColorCode.BOLD.getCode();
		}
		
		if(!noitalic) {
			time += ColorCode.ITALIC.getCode();
		}
		
		if(!nounderline) {
			time += ColorCode.UNDERLINE.getCode();
		}
		
		time += "[";
		
		if(!noDate) {
			time += "<" + day + "/";
			time += month +"/";
			time += year+"> ";
		}
		
		String ampm = "";
		boolean addampm = false;
		if(hour24) {
			if(hours < 10) time += "0";
			time += hours+":";
		} else {
			if(hours < 12) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}
			int hour = hours % 12;
			if (hour == 0) hour = 12;
			time += hour+":";
			addampm = true;
		}
		if(minutes < 10) time += "0";
		time += minutes+":";				
		if(seconds < 10) time += "0";
		time += seconds;
		if(addampm) {
			time += ampm;
		}
		time += "]"+ColorCode.RESET.getCode();
		
		return time;
	}
	
}
