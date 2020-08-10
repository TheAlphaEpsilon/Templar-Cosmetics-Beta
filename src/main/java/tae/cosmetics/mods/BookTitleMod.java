package tae.cosmetics.mods;

import io.netty.buffer.Unpooled;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.guiscreen.utilities.GuiBookTitleMod;
import tae.packetevent.PacketEvent;

public class BookTitleMod extends BaseMod {

	private String title = null;
		
	@SubscribeEvent
	public void onSendPacket(PacketEvent.Outgoing event) {
		//dont send click packets from booktitle gui
		if(mc.currentScreen != null && mc.currentScreen instanceof GuiBookTitleMod && event.getPacket() instanceof CPacketClickWindow) {
			event.setCanceled(true);
		}
		//handle gui
		if(event.getPacket() instanceof CPacketCustomPayload && ((CPacketCustomPayload)event.getPacket()).getChannelName().equals("TAE|BSign")) {
			CPacketCustomPayload packet = (CPacketCustomPayload) event.getPacket();
			event.setPacket(new CPacketCustomPayload("MC|BSign",packet.getBufferData()));
		}

		if(event.getPacket() instanceof CPacketCustomPayload && ((CPacketCustomPayload)event.getPacket()).getChannelName().equals("MC|ItemName")) {
			CPacketCustomPayload packet = (CPacketCustomPayload) event.getPacket();
			String s = "\u00a7" + packet.getBufferData().readString(32767);
			PacketBuffer buff = new PacketBuffer(Unpooled.buffer());
			buff.writeString(s);
			event.setPacket(new CPacketCustomPayload("MC|ItemName", buff));
		}
		
		//depreciated
		//normal ui execution
		if(title != null && 
				event.getPacket() instanceof CPacketCustomPayload && ((CPacketCustomPayload)event.getPacket()).getChannelName().equals("MC|BSign")) {
			
			ItemStack bookItem = mc.player.inventory.getCurrentItem();
			bookItem.setTagInfo("author", new NBTTagString(mc.getSession().getUsername()));
			bookItem.setTagInfo("title", new NBTTagString(title));
					
			PacketBuffer buff = new PacketBuffer(Unpooled.buffer());
			buff.writeItemStack(bookItem);
			CPacketCustomPayload newPacket = new CPacketCustomPayload("MC|BSign",buff);
			event.setPacket(newPacket);
		}
	}

}
