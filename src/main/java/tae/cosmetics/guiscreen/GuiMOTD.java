package tae.cosmetics.guiscreen;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Scanner;

import net.minecraft.client.gui.GuiScreen;
import tae.cosmetics.webscrapers.MyMOTD;

public class GuiMOTD extends AbstractTAEGuiScreen {

	private static final int edge = 20;
	
	private static ArrayList<String> motdLines;
	
	static {
		
		motdLines = new ArrayList<>();
		
		String motdraw = MyMOTD.getMOTD();
		Scanner sc = new Scanner(motdraw);
		while(sc.hasNextLine()) {
			motdLines.add(sc.nextLine());
		}
		sc.close();
		
	}
	
	protected GuiMOTD(GuiScreen parent) {
		super(parent);
	}

	@Override
	public void onGuiClosed() {		
	}

	@Override
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		int i = width / 2;
		int j = height / 2;
		
		int yOffset = 0;
		
		for(String s : motdLines) {
			
			fontRenderer.drawStringWithShadow(s, i - guiwidth / 2 + edge, j - guiheight / 2 + yOffset + edge, Color.WHITE.getRGB());
			yOffset += fontRenderer.FONT_HEIGHT;
			
		}
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {		
	}

}
