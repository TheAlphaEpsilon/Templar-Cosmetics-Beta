package tae.packetevent;

/*
 * By TheAlphaEpsilon
 * 2JAN2020
 * 
 */

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;

public class PacketListener extends ChannelDuplexHandler {
	
	@Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		boolean get = true;
		
		if(msg instanceof Packet) {
			//if msg is packet, post new packet event
			PacketEvent.Incoming inPacket = new PacketEvent.Incoming((Packet<?>)msg);
			MinecraftForge.EVENT_BUS.post(inPacket);
			//if the packet is cancelled, dont process it
			if(inPacket.isCanceled()) {
				get = false;
			}
			//if event changes packet, update
			msg = inPacket.getPacket();
		}
		
		
        if(get) super.channelRead(ctx, msg);
    }
	
	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
		
		boolean send = true;
		
		if(msg instanceof Packet) {
			//if msg is packet, post new packet event
			PacketEvent.Outgoing outPacket = new PacketEvent.Outgoing((Packet<?>)msg);
			MinecraftForge.EVENT_BUS.post(outPacket);
			//if the packet is cancelled, dont process it
			if(outPacket.isCanceled()) {
				send = false;
			}
			//if event changes packet, update
			msg = outPacket.getPacket();
		}
		
		if(send) super.write(ctx, msg, promise);
	}
}
