package tae.cosmetics.mods;

import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import tae.cosmetics.ColorCode;
import tae.cosmetics.Globals;
import tae.cosmetics.settings.Keybind;
import tae.cosmetics.util.PlayerUtils;

public class BaseMod implements Globals {
	
	
	public static final Keybind elytra = new Keybind("Send Elytra Packet",0, () -> {
		mc.addScheduledTask(() -> {
			//TODO: make everythin automatic
			//Vector2d vector = PlayerMathHelper.getXZComponentsOfYaw();
			mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, Action.START_FALL_FLYING));
		});
	});
	
	public static final Keybind modifiedFreecam = new Keybind("Modified Freecam",0, () -> {
		if(ModifiedFreecam.enabled) {
			ModifiedFreecam.onDisabled();
		} else {
			ModifiedFreecam.onEnabled();
		}
	});
	
	protected void sendMessage(String message, ColorCode... codes) {
		PlayerUtils.sendMessage(message, codes);
	}
	
}
