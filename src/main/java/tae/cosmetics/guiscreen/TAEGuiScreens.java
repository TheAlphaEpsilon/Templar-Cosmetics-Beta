package tae.cosmetics.guiscreen;

import tae.cosmetics.guiscreen.packets.Gui2b2tcp;
import tae.cosmetics.guiscreen.packets.GuiCancelPackets;
import tae.cosmetics.guiscreen.packets.GuiScreenMoveScreenElements;
import tae.cosmetics.guiscreen.packets.GuiScreenVisualizePackets;
import tae.cosmetics.guiscreen.render.GuiPlayerFaker;
import tae.cosmetics.guiscreen.render.GuiTimestampMod;
import tae.cosmetics.guiscreen.utilities.BookArt;
import tae.cosmetics.guiscreen.utilities.GuiBookTitleMod;
import tae.cosmetics.guiscreen.utilities.GuiScreenStalkerMod;

public class TAEGuiScreens { //Used to init all guis for keybinds and configs. Prob a better way but whatever

	static {
	
		GuiHome home = new GuiHome();
			GuiScreenRendering rendering = new GuiScreenRendering(home);
				new GuiPlayerFaker(rendering);
				new GuiTimestampMod(rendering);
			GuiScreenUtilities utilities = new GuiScreenUtilities(home);
				new BookArt(utilities);
				new GuiBookTitleMod();
				new GuiScreenStalkerMod(utilities);
			Gui2b2tcp packets = new Gui2b2tcp(home);
				new GuiScreenVisualizePackets(packets);
				GuiCancelPackets.instance();
				new GuiScreenMoveScreenElements(packets);
			GuiMOTD motd = new GuiMOTD(home);
	}
	
}
