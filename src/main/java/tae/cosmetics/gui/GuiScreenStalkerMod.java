package tae.cosmetics.gui;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.play.client.CPacketTabComplete;
import net.minecraft.util.ResourceLocation;
import tae.cosmetics.gui.util.GuiSignature;
import tae.cosmetics.gui.util.ScrollBar;
import tae.cosmetics.util.MojangGetter;
import tae.cosmetics.util.PlayerAlert;

public class GuiScreenStalkerMod extends AbstractTAEGuiScreen {

	private static final ResourceLocation STALKER_MOD_GUI = new ResourceLocation("taecosmetics","textures/gui/playeralert.png");
	private static final ResourceLocation DEFAULT_SKIN = new ResourceLocation("textures/entity/steve.png");
	
	private static final HashMap<String, PlayerTextureData> resourcemap = new HashMap<>();
	
	private static int xOffset = -20;
	private static int yOffset = -40;
	
	private static final int moduleheight = 58;
	
	//Number of rows of modules to draw
	private int rows;
	
	//Reused buttons for all modules
    private GuiPlayerOptionsButton[] playeroptionbuttons = new GuiPlayerOptionsButton[6];
    
    private GuiButton addplayerbutton = new GuiButton(0, 0, 0, 60, 20, "Add Player");
    private GuiButton settingsbutton = new GuiButton(0, 0, 0, 190, 20, "Settings");
	
    private ScrollBar scroll = new ScrollBar(0, 0, 116);
    
    private GuiTextField nameField;
    
    //Used to clear help text from the name field
    private boolean firstText;
    
	public GuiScreenStalkerMod(GuiScreen parent) {
		super(parent);
	}

	@Override
	public void initGui() {
		
		Keyboard.enableRepeatEvents(true);

		new Thread(() -> {
			
			updateResourceMap();
		
		}).start();
		
		int j = height/2 + yOffset;

		for(int i = 0; i < playeroptionbuttons.length; i++) {
			playeroptionbuttons[i] = new GuiPlayerOptionsButton(2+i, 0, 0, 13, 13, null, null, j + 97, j - 50);
		}
		
		buttonList.add(addplayerbutton);
		buttonList.add(settingsbutton);
		
		nameField = new GuiTextField(0, fontRenderer, 0, 0, 103, 12);
		nameField.setTextColor(-1);
    	nameField.setDisabledTextColour(-1);
    	nameField.setEnableBackgroundDrawing(false);
    	nameField.setMaxStringLength(16);
    	
    	nameField.setText("ADD PLAYER");
        firstText = true;
        
        super.initGui();
		
	}

	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
	
		int i = width/2 + xOffset;
		int j = height/2 + yOffset;
		
		if(button == addplayerbutton) {
			String name = nameField.getText();
			if(name.isEmpty()) {
				addMessage("No name!", i + 80, j - 35, 250, Color.RED);
			} else {
								
				if(PlayerAlert.alertExists(name)) {
					
					addMessage("Already Exists!", i + 80, j - 35, 250, Color.RED);
							
				} else {
				
					String truename = PlayerAlert.addName(name);
					if(truename == null) {
						
						addMessage("Invalid Name!", i + 80, j - 35, 250, Color.RED);
						
					} else {
						
						addMessage("Added!", i + 80, j - 35, 250, Color.GREEN);
						
						nameField.setText("");
						new Thread(() -> {
						
							updateResourceMap();
							
						}).start();
						
						//To check if player is online since PlayerAlert rels on join/leave packets
						mc.getConnection().sendPacket(new CPacketTabComplete(name, null, false));
					}
					
				}
			}
			
		} else if (button == settingsbutton) {
				
			displayScreen(new GuiScreenStalkerModGlobalOptions(this));
			
		} else if (button instanceof GuiPlayerOptionsButton) {
			
			displayScreen(new GuiScreenStalkerModPlayerOptions(this, ((GuiPlayerOptionsButton) button).uuid, 
					((GuiPlayerOptionsButton) button).playerTexture.location, ((GuiPlayerOptionsButton) button).playerTexture.width, 
					((GuiPlayerOptionsButton) button).playerTexture.height));
			
		}
		
		super.actionPerformed(button);

	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if (this.nameField.textboxKeyTyped(typedChar, keyCode)) {}
		else {
            super.keyTyped(typedChar, keyCode);
        }
		
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		for(int i = 0; i < playeroptionbuttons.length; i++) {
			GuiPlayerOptionsButton button = playeroptionbuttons[i];
			if(button.mousePressed(mc, mouseX, mouseY)) {
				this.actionPerformed(button);
			}
		}
		
		if(this.nameField.mouseClicked(mouseX, mouseY, mouseButton) && firstText) {
			firstText = false;
			nameField.setText("");
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
				
		int i = width / 2 + xOffset;
		int j = height / 2 + yOffset;
		
		updateButtonPositions(i, j);
		
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.enableAlpha();
		GlStateManager.enableBlend();
				
		//draw background sides
		mc.getTextureManager().bindTexture(STALKER_MOD_GUI);
		this.drawTexturedModalRect(i - 100, j - 50, 0, 0, 7, 178);
		this.drawTexturedModalRect(i + 137, j - 50, 115, 178, 24, 60);
		this.drawTexturedModalRect(i + 137, j + 10, 163, 178, 24, 59);
		this.drawTexturedModalRect(i + 137, j + 68, 139, 178, 24, 60);
		
		//draw scrollbar
		scroll.setDisabled(!this.needsScrollBars());
		scroll.draw(mouseX, mouseY);
		
		Set<String> keyset = resourcemap.keySet();
		rows = (int) Math.ceil(keyset.size() / 2.0D);
		int scrolloffset = (rows -2) * ((int) (scroll.getScroll() * moduleheight));
		
		//draw modules
		
		for(int x = 0; x < 2; x++) {
			//+58
			//top row
			this.drawTexturedModalRect(i - 93 + (x*115), j - 19, 0 , 178 + scrolloffset % moduleheight, 115, moduleheight - (scrolloffset % moduleheight));
			//middle row
			this.drawTexturedModalRect(i - 93 + (x*115), j + 39 - (scrolloffset % moduleheight), 0, 178, 115, 58);
			//bottom row
			this.drawTexturedModalRect(i - 93 + (x*115), j + 97 - (scrolloffset % moduleheight), 0, 178, 115, (scrolloffset % moduleheight));
								
		}
		
		//draw skins and data
		
		//used to get what data to render
		int toprow = scrolloffset / moduleheight;
		
		ArrayList<String> keys = new ArrayList<>(resourcemap.keySet());
		
		//Sort in order online -> queue -> offline -> alphabetical order
		keys.sort(ModuleComparator.INSTANCE);
		
		
		for(int y = 0; y < 3; y++) {
			for(int x = 0; x < 2; x++) {			
				int keypos = (toprow * 2) + (y * 2) + x;
				
				if(keypos < keys.size()) {
					
					String thisUUID = keys.get(keypos);
					
					PlayerTextureData data = resourcemap.get(thisUUID);
					
					if(data.location == null) {
						
						DynamicTexture skinTexture = new DynamicTexture(data.image);
						data.location = mc.getTextureManager().getDynamicTextureLocation(thisUUID, skinTexture);
						
					}
					
					GuiPlayerOptionsButton thisButton = playeroptionbuttons[(y*2) + x];
					thisButton.uuid = thisUUID;
					thisButton.playerTexture = data;
					thisButton.visible = false;
					
					int yoffset = (int) (j + (y * 58) - ((float)scrolloffset % moduleheight));
					
					//show and position buttons
					
					if(yoffset > j - 50 && yoffset + 5 < j + 97) {
						
						thisButton.visible = true;
						thisButton.x = i - 88 + (x * 115);
						thisButton.y = yoffset + 21;
						
						thisButton.drawButton(mc, mouseX, mouseY, partialTicks);
						mc.getTextureManager().bindTexture(data.location);
						
						//skin
						Gui.drawScaledCustomSizeModalRect(i - 7 + (x * 115), yoffset + 5, 8, 8, 8, 8, 24, 24, data.width, data.height);
						
						//online display
						mc.getTextureManager().bindTexture(STALKER_MOD_GUI);
						
						int type = 0;
						if(PlayerAlert.inGame(thisUUID)) {
							type = 0;
						} else if(PlayerAlert.queuePos(thisUUID) != -1) {
							type = 2;
						} else {
							type = 1;
						}
						
						this.drawTexturedModalRect(i - 3 + (x * 115), yoffset + 30, 60, 236 + type * 5, 17, 5);
						
					}
					
					if(yoffset - 9 > j - 50) {	
						//name
						this.drawString(fontRenderer, (PlayerAlert.needsUpdate(thisUUID)?"\u00a7m":"") + PlayerAlert.oldName(thisUUID), i - 85 + (x * 115), yoffset - 9, Color.WHITE.getRGB());
					}
						
					if(yoffset + 8 < j + 97) {
						this.drawString(fontRenderer, "Queue:", i - 85 + (x * 115), yoffset + 8, Color.WHITE.getRGB());
						if(PlayerAlert.prioOutOfBounds(thisUUID)) {
							this.drawString(fontRenderer, "Joining", i - 50 + (x * 115), yoffset + 8, Color.WHITE.getRGB());
						} else {
							this.drawString(fontRenderer, (PlayerAlert.queuePos(thisUUID)==-1)?"N/A":""+PlayerAlert.queuePos(thisUUID), i - 50 + (x * 115), yoffset + 8, Color.WHITE.getRGB());
						}
						
					}
					
				}
				
			}	
			
		}
		
		//draw background top & bottom
		mc.getTextureManager().bindTexture(STALKER_MOD_GUI);
		this.drawTexturedModalRect(i - 93, j - 50, 7, 0, 230, 31);
		this.drawTexturedModalRect(i - 93, j + 97, 7, 147, 230, 31);
		
		
		
		nameField.drawTextBox();
		
		GuiSignature.draw(i + 132, j + 110);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		
		addplayerbutton.x = x - 96;
		addplayerbutton.y = y - 43;
		
		settingsbutton.x = x - 65;
		settingsbutton.y = y + 103;
		
		nameField.x = x - 23;
		nameField.y = y - 36;
		
		scroll.x = x + 141;
		scroll.y = y - 18;
		
	}
	
	public static void updateResourceMap() {
		
		Set<String> alertuuids = PlayerAlert.getUUIDs();
			
		Iterator<String> iter = resourcemap.keySet().iterator();
		
		while(iter.hasNext()) {
			
			String uuid = iter.next();
			PlayerTextureData data = resourcemap.get(uuid);
			
			//If no longer an alert, remove
			if(!alertuuids.contains(uuid)) {
				iter.remove();
			}
			
			//Update texture if needed
			else if(data.update) {
				
				try {
					
					String url = MojangGetter.getTexturesFromUUID(uuid);
					
					URL skinURL = new URL(url);
								
					BufferedImage skinImage = ImageIO.read(skinURL);
			
					data.width = skinImage.getTileWidth();
					data.height = skinImage.getTileHeight();
			
					data.image = skinImage;
					
					data.update = false;

				} catch (Exception e) {
					
					data.height = 64;
					data.width = 64;
					data.location = DEFAULT_SKIN;
					data.update = true;
					
				}
				
			}
			
		}
		
		
		//Add all new alerts
		for(String uuid : alertuuids) {
			if(!resourcemap.containsKey(uuid)) {	
				try {
								
					String url = MojangGetter.getTexturesFromUUID(uuid);
											
					URL skinURL = new URL(url);
					
					BufferedImage skinImage = ImageIO.read(skinURL);	
			
					int imagewidth = skinImage.getTileWidth();
					int imageheight = skinImage.getTileHeight();
					
					boolean update = false;
										
					resourcemap.put(uuid, new PlayerTextureData(imagewidth, imageheight, skinImage, update));
				
				} catch (Exception e) {
					
					resourcemap.put(uuid, new PlayerTextureData(64, 64, DEFAULT_SKIN, true));

				}
			}
		}
		
		PlayerAlert.updateQueuePositions();
		
	}
	
    private boolean needsScrollBars() {
    	return resourcemap.keySet().size() > 4;
    }
    
	
	static class GuiPlayerOptionsButton extends GuiButton {

		private static final ResourceLocation STALKER_MOD_GUI = new ResourceLocation("taecosmetics","textures/gui/playeralert.png");
		
		public PlayerTextureData playerTexture;
		private String uuid;
		private int maxY;
		private int minY;
		
		private GuiPlayerOptionsButton(int buttonId, int x, int y, int width, int height, String uuid, PlayerTextureData playerTexture, int maxY, int minY) {
			super(buttonId, x, y, width, height, null);
			this.uuid = uuid;
			this.playerTexture = playerTexture;
			this.maxY = maxY;
			this.minY = minY;
		}
		
		@Override
		public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
			if(this.visible) {
				
				this.hovered = mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= ((this.y < minY) ? minY : this.y) && mouseY <= ((this.y + this.height > maxY) ? maxY : this.y + this.height);
				
				
				int i = this.getHoverState(this.hovered) - 1;
	      
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.disableLighting();
				GlStateManager.enableAlpha();
				GlStateManager.enableBlend();
				mc.getTextureManager().bindTexture(STALKER_MOD_GUI);
				
				Gui.drawScaledCustomSizeModalRect(this.x, this.y, 187 + (i * 26), 178, 26, 26, 13, 13, 256, 256);
				
				this.mouseDragged(mc, mouseX, mouseY);
				
	    	}
		}
		
		@Override
		public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
	        return this.enabled && this.visible && this.hovered;
	    }
	}

	static class PlayerTextureData {
    	
    	private int width;
    	private int height;
    	private BufferedImage image;
    	private ResourceLocation location;
    	private boolean update;
    	
    	private PlayerTextureData(int width, int height, BufferedImage image, boolean update) {
    		this.width = width;
    		this.height = height;
    		this.image = image;
    		this.update = update;
    		location = null;
    	}
    	
    	private PlayerTextureData(int width, int height, ResourceLocation location, boolean update) {
    		this.width = width;
    		this.height = height;
    		this.location = location;
    		this.update = update;
    	}
    	
    }
	
	static class ModuleComparator implements Comparator<String> {
		
		private static final ModuleComparator INSTANCE = new ModuleComparator();
		
		@Override
		public int compare(String o1, String o2) {
			
			boolean o1InGame = PlayerAlert.inGame(o1);
			boolean o2InGame = PlayerAlert.inGame(o2);
			
			boolean o1InQueue = PlayerAlert.queuePos(o1) != -1 ? true : false;
			boolean o2InQueue = PlayerAlert.queuePos(o2) != -1 ? true : false;
			
			int o1Comp = 0;
			if(!o1InGame) {
				o1Comp = 2;
			}
			if(o1InQueue) {
				o1Comp = 1;
			}
			
			int o2Comp = 0;
			if(!o2InGame) {
				o2Comp = 2;
			}
			if(o2InQueue) {
				o2Comp = 1;
			}
			
			if(o1Comp != o2Comp) {
				return o1Comp - o2Comp;
			} else {
				return PlayerAlert.oldName(o1).toLowerCase().compareTo(PlayerAlert.oldName(o2).toLowerCase());
			}
			
		}
		
	}
	
}

