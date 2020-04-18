package tae.cosmetics.mods;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import tae.cosmetics.util.API2b2tdev;
import tae.cosmetics.util.PlayerAlert;
import tae.cosmetics.util.RebaneGetter;

public class QueuePeekMod extends BaseMod {
	
	public static boolean update = true;
	public static int minutes = 10;
	public static int tick = 0;
	
	@SubscribeEvent
	public void tickEvent(TickEvent.ClientTickEvent event) {
		if(update && event.phase == TickEvent.Phase.END) {
			if(minutes > 0 && tick++ > minutes * 1200) {
				tick = 0;
				
				initAndUpdate();
				
			}
		}
	}
	
	public static void initAndUpdate() {
		//Downloading the json file is noticable in game so i had to make a new thread for it
		new Thread(() -> {
			RebaneGetter.init();
			API2b2tdev.update();
			PlayerAlert.updateQueuePositions();
		}).start();
	}
	
}
