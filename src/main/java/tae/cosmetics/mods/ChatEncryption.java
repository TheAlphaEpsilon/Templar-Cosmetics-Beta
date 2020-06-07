package tae.cosmetics.mods;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tae.cosmetics.ColorCode;
import tae.packetevent.PacketEvent;

/**
 * 
 * unimplemented
 * TODO: implement
 */
public class ChatEncryption extends BaseMod {

	public static boolean enabled = false;
	
	public static boolean showRaw = false;
	
	private static final char notifier = 0xffff;
	
	private static final char preStamp = 0x257f;
	
	private static final String stamp = UnicodeKeyboard.toUnicode("Templar Cosmetics", UnicodeKeyboard.TextType.WIDE);
	
	private static final String method = "Blowfish";
	
	@SubscribeEvent
	public void chatOutgoing(PacketEvent.Outgoing event) {
		if(!enabled || !(event.getPacket() instanceof CPacketChatMessage)) {
			return;
		}
		
		CPacketChatMessage packet = (CPacketChatMessage) event.getPacket();
		String message = packet.getMessage();
		
		if(message.charAt(0) == '/') {
			return;
		}
				
		String encrypted = encrypt(mc.player.getName(), message);
				
		int length = (encrypted + preStamp + stamp).length();
		if(length > 255) {
			encrypted = encrypted.substring(0, 255 - (preStamp + stamp).length());
		}
		
		event.setPacket(new CPacketChatMessage(notifier + encrypted + preStamp + stamp));
		
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void chatIncoming(PacketEvent.Incoming event) {
		if(!(event.getPacket() instanceof SPacketChat)) {
			return;
		}
		
		try {
			SPacketChat packet = (SPacketChat) event.getPacket();
			
			String text = packet.getChatComponent().getUnformattedText();
					
			String[] brokenText = text.split(" ");
							
			if(brokenText[1].charAt(0) == notifier) {	
				
				if(!showRaw) {
					event.setCanceled(true);
				}
				
				StringBuffer buff = new StringBuffer();
				buff.append(brokenText[1].substring(1));
				for(int i = 2; i < brokenText.length; i++) {
					buff.append(" " + brokenText[i]);
				}
				
				String encrypt = buff.toString();
				
				char[] chars = encrypt.toCharArray();
				
				int index;
				for(index = 2; index < chars.length; index++) {
					if(chars[index] == preStamp) {
						break;
					}
				}
							
				encrypt = encrypt.substring(0, index);
							
				String decrypt = decrypt(brokenText[0].substring(1, brokenText[0].length() - 1), encrypt);
				
				sendMessage("[Encrypted] " + brokenText[0] + " " + decrypt, ColorCode.AQUA);
			}
			
		} catch (Exception e) {
			//Expert coding
		}
		
	}
	
	private static String encrypt(String key, String message) {
		try {
			byte[] keyData = key.getBytes();
			SecretKeySpec keyspec = new SecretKeySpec(keyData, method);
			Cipher cipher = Cipher.getInstance(method);
			cipher.init(Cipher.ENCRYPT_MODE, keyspec);
			byte[] hasil = cipher.doFinal(message.getBytes());
			return new String(Base64.getEncoder().encode(hasil));
		} catch (Exception e) {
			return "";
		}
	}
	
	private static String decrypt(String key, String encrypted) {
		try {
			byte[] keyData = key.getBytes();
			SecretKeySpec keyspec = new SecretKeySpec(keyData, method);
			Cipher cipher = Cipher.getInstance(method);
			cipher.init(Cipher.DECRYPT_MODE, keyspec);
			byte[] hasil = cipher.doFinal(Base64.getDecoder().decode(encrypted));
			return new String(hasil);
		} catch (Exception e) {
			return "";
		}
	}
	
}
