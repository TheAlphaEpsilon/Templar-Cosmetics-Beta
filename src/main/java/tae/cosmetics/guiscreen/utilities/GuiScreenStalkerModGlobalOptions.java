package tae.cosmetics.guiscreen.utilities;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlider;
import net.minecraft.client.gui.GuiPageButtonList.GuiResponder;
import net.minecraft.client.gui.GuiSlider.FormatHelper;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;
import tae.cosmetics.mods.QueuePeekMod;
import tae.cosmetics.util.PlayerAlert;
import tae.cosmetics.webscrapers.API2b2tdev;

public class GuiScreenStalkerModGlobalOptions extends AbstractTAEGuiScreen {

	private GuiButton toggleAlertOn;
	private GuiButton toggleAlertOff;

	private GuiButton toggleQueueOn;
	private GuiButton toggleQueueOff;
	
	private GuiButton updateQueue;
	private GuiSlider updateQueueTimer;

	public GuiScreenStalkerModGlobalOptions(GuiScreen in) {
		
		super(in);
				
		guiwidth = 410;
		
		toggleAlertOn = new GuiButton(0, 50, 50, 175, 20, "Toggle All Alert Notifications On");
 		toggleAlertOff = new GuiButton(1, 200, 50, 175, 20, "Toggle All Alert Notifications Off");
 		
 		toggleQueueOn = new GuiButton(2, 50, 80, 175, 20, "Toggle All Queue Notifications On");
 		toggleQueueOff = new GuiButton(3, 200, 80, 175, 20, "Toggle All Queue Notifications Off");
 		
 		updateQueue = new GuiButton(4, 50, 110, 175, 20, "Update 2b2t Queue Cache");
		updateQueueTimer = new GuiSlider(new GuiResponder() {

			@Override
			public void setEntryValue(int id, boolean value) {				
			}

			@Override
			public void setEntryValue(int id, float value) {				
			}

			@Override
			public void setEntryValue(int id, String value) {				
			}
			
		}, 6, 200, 110, "TEST", 0, 60, QueuePeekMod.minutes.getValue(), new FormatHelper()  {

			@Override
			public String getText(int id, String name, float value) {
				return "";
			}
			
		});
 		
	}
		
	@Override
	public void initGui() {
						
		buttonList.add(toggleAlertOn);
		buttonList.add(toggleAlertOff);

		buttonList.add(toggleQueueOn);
		buttonList.add(toggleQueueOff);

		buttonList.add(updateQueue);
		buttonList.add(updateQueueTimer);
		
		super.initGui();
	}
	
	protected void drawScreen0(int x, int y, float tick) {
		
		int i = width / 2;
		int j = height / 2;
		
		String toSend = "Queue update timer: " + (((int)updateQueueTimer.getSliderValue() == 0) ? "Disabled" : ((int)updateQueueTimer.getSliderValue() + "m"));
		this.drawString(fontRenderer, toSend, i + 20, j + 35, Color.WHITE.getRGB());
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
	
	@Override
	public void onGuiClosed() {
		QueuePeekMod.minutes.setValue((int)updateQueueTimer.getSliderValue());
		Keyboard.enableRepeatEvents(false);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		int i = width / 2;
		int j = height / 2;
		
		if(button == toggleAlertOn) {
			PlayerAlert.getUUIDs().forEach(x -> PlayerAlert.updateSendAlert(x, true));
			addMessage("All alerts toggled on", i - 175, j - 45, 250, Color.GREEN);
		
		} else if(button == toggleAlertOff) {
			PlayerAlert.getUUIDs().forEach(x -> PlayerAlert.updateSendAlert(x, false));
			addMessage("All alerts toggled off", i + 25, j - 45, 250, Color.RED);

		} else if(button == toggleQueueOn) {
			PlayerAlert.getUUIDs().forEach(x -> PlayerAlert.updateQueueSendAlert(x, true));
			addMessage("All queue alerts on", i - 175, j - 5, 250, Color.GREEN);

		} else if(button == toggleQueueOff) {
			PlayerAlert.getUUIDs().forEach(x -> PlayerAlert.updateQueueSendAlert(x, false));
			addMessage("All queue alerts off", i + 25, j - 5, 250, Color.RED);
		} else if(button == updateQueue) {
			QueuePeekMod.initAndUpdate();
			API2b2tdev.update();
			addMessage("Updated", i - 175, j + 35, 250, Color.WHITE);
		} 
		
		super.actionPerformed(button);
	}
	
	@Override
	protected void updateButtonPositions(int x, int y) {
		
		toggleAlertOn.x = x - 180;
		toggleAlertOn.y = y - 70;
		
		toggleAlertOff.x = x + 20;
		toggleAlertOff.y = y - 70;
		
		toggleQueueOn.x = x - 180;
		toggleQueueOn.y = y - 30;
		
		toggleQueueOff.x = x + 20;
		toggleQueueOff.y = y - 30;
		
		updateQueue.x = x - 180;
		updateQueue.y = y + 10;
		
		updateQueueTimer.x = x + 20;
		updateQueueTimer.y = y + 10;
		
		back.x = x - 180;
		back.y = y + 50;
		
	}

}
