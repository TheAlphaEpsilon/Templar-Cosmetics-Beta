package tae.packetevent;

/*
 * By TheAlphaEpsilon
 * 2JAN2020
 * 
 */

import io.netty.channel.ChannelPipeline;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ChannelHandlerInput {
	
	
	public static Minecraft mc = Minecraft.getMinecraft();

	public static boolean firstConnection = true;
	
	@SubscribeEvent
	public void init(ClientConnectedToServerEvent event)  {
		if(firstConnection) {
						
			firstConnection = false;
			
			ChannelPipeline pipeline = event.getManager().channel().pipeline();
						
			pipeline.addBefore("packet_handler","listener", new PacketListener());
			
		}
	}
	
	@SubscribeEvent (priority = EventPriority.HIGHEST)
	public void onDisconnect(ClientDisconnectionFromServerEvent event) {
		firstConnection = true;
	}
	
}
