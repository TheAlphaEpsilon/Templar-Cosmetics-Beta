package tae.cosmetics.mods;

import com.mojang.authlib.GameProfile;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import tae.cosmetics.settings.Keybind;

public class PlayerFaker extends BaseMod {

	public static final Keybind addFakePlayer = new Keybind("Add a fake player", 0, () -> {
		EntityOtherPlayerMP fakePlayer = new EntityOtherPlayerMP(mc.world, new GameProfile(null, "Albatros_"));
		fakePlayer.copyLocationAndAnglesFrom(mc.player);
		fakePlayer.rotationYawHead = mc.player.rotationYawHead;
		
		mc.world.addEntityToWorld(fakePlayer.hashCode(), fakePlayer);
	});
	
}
