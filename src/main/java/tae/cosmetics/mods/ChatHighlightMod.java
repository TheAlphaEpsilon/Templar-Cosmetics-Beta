package tae.cosmetics.mods;

import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.util.PlayerAlert;
import tae.packetevent.PacketEvent;

public class ChatHighlightMod extends BaseMod {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void getChatEvent(PacketEvent.Incoming event) {
		if(event.getPacket() instanceof SPacketChat) {			
			SPacketChat packet = (SPacketChat) event.getPacket();
			String text = packet.getChatComponent().getFormattedText();
			String[] brokentext = text.split(" ");
			
			for(String uuid : PlayerAlert.getUUIDs()) {
				String name = PlayerAlert.oldName(uuid);
				String format = PlayerAlert.prefix(uuid);
				if(brokentext[0].toLowerCase().contains(name.toLowerCase()) && !format.isEmpty()) {
									
					event.setPacket(new SPacketChat(new TextComponentString(format+text), packet.getType()));
					
					break;
				}
				
			}
			
		}
	}

}
