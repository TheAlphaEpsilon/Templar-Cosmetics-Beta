package tae.cosmetics.guiscreen.render;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import com.mojang.authlib.GameProfile;
import com.mojang.util.UUIDTypeAdapter;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tae.cosmetics.gui.util.GuiSetKeybind;
import tae.cosmetics.gui.util.ScrollBar;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;
import tae.cosmetics.guiscreen.GuiHome;
import tae.cosmetics.guiscreen.GuiScreenRendering;
import tae.cosmetics.guiscreen.button.GuiGenericButton;
import tae.cosmetics.guiscreen.button.OptionsButton;
import tae.cosmetics.guiscreen.render.GuiPlayerFaker.FakePlayerModule.DeleteButtonWrapper;
import tae.cosmetics.guiscreen.render.GuiPlayerFaker.FakePlayerModule.OptionButtonWrapper;
import tae.cosmetics.settings.Keybind;
import tae.cosmetics.util.EntityFakePlayer;
import tae.cosmetics.util.PlayerUtils;
import tae.cosmetics.webscrapers.MojangGetter;

public class GuiPlayerFaker extends AbstractTAEGuiScreen {

	public static final Keybind openGui = new Keybind("Open Player Faker Gui",0, () -> {
		AbstractTAEGuiScreen.displayScreen(new GuiPlayerFaker(new GuiScreenRendering(new GuiHome())));
	});
	
    private GuiTextField nameField;
    private GuiGenericButton addPlayer;
    
    private ScrollBar scroll = new ScrollBar(0, 0, 116);
    
    private static ArrayList<FakePlayerModule> fakePlayers = new ArrayList<>();
    
    private static final int modules = 6;
	
	public GuiPlayerFaker(GuiScreen parent) {
		super(parent);
		addPlayer = new GuiGenericButton(0, 0, 0, 60, 20, "Add Player", "Add this player", 1);
		
		settingsScreen = new AbstractTAEGuiScreen(parent) {

			{
        		guiwidth = 200;
        		guiheight = 100;
        	}
        	
        	private GuiSetKeybind openGuiKeyBind = null;
        	private String keyBindInfo = "Keybind to open Player Faker Gui";
        	
        	@Override
        	public void initGui() {
        		if(openGuiKeyBind == null) {
        			openGuiKeyBind = new GuiSetKeybind(0, fontRenderer, 0, 0, 12, openGui.getInt());
        			openGuiKeyBind.setTextColor(-1);
        			openGuiKeyBind.setDisabledTextColour(-1);
        			openGuiKeyBind.setEnableBackgroundDrawing(true);
        			openGuiKeyBind.setMaxStringLength(32);
        			openGuiKeyBind.setCanLoseFocus(true);
        		}
        		super.initGui();
        	}
        	
			@Override
			public void onGuiClosed() {
				Keyboard.enableRepeatEvents(false);
				
				openGui.updateBinding(openGuiKeyBind.getKeyCode());
								
				mc.gameSettings.saveOptions();
			}
			
			@Override
			protected void keyTyped(char typedChar, int keyCode) throws IOException {
				if (openGuiKeyBind.textboxKeyTyped(typedChar, keyCode)) {
		        } else {	
		            super.keyTyped(typedChar, keyCode);
		        }
		    }
			
			@Override
			protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
				super.mouseClicked(mouseX, mouseY, mouseButton);
				if(openGuiKeyBind.mouseClicked(mouseX, mouseY, mouseButton)) {
				} 				
			}

			@Override
			protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
				
				int i = width / 2;
				int j = height / 2;
				
				openGuiKeyBind.drawTextBox();
				
				this.drawCenteredString(fontRenderer, keyBindInfo, i, j - 20, Color.WHITE.getRGB());
			}

			@Override
			protected void updateButtonPositions(int x, int y) {
				
				openGuiKeyBind.x = x - openGuiKeyBind.width / 2;
				openGuiKeyBind.y = y - 6;
				
				back.x = x - back.width / 2;
				back.y = y + 16;
			}
			
		};
		
	}

	@Override
	public void initGui() {
		
		nameField = new GuiTextField(0, fontRenderer, 0, 0, 103, 12);
		nameField.setTextColor(-1);
    	nameField.setDisabledTextColour(-1);
    	nameField.setEnableBackgroundDrawing(true);
    	nameField.setMaxStringLength(16);
				
		Keyboard.enableRepeatEvents(true);
		
		super.initGui();
		
		buttonList.add(addPlayer);
		
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
		
		if(this.nameField.mouseClicked(mouseX, mouseY, mouseButton)) {
		
		}
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
	}

	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == addPlayer) {
			
			String text = nameField.getText();
			
			if(text.isEmpty()) {
				return;
			}
			
			nameField.setText("");
			
			UUID uuid = null;
			
			String[] data = MojangGetter.getInfoFromName(text);
			
			if(data != null) {
				uuid = UUIDTypeAdapter.fromString(data[0]);
				text = data[1];
			}
			
			GameProfile profile = new GameProfile(uuid, text);
			
			EntityFakePlayer player = new EntityFakePlayer(mc.world, profile);
						
			player.copyLocationAndAnglesFrom(mc.player);
			
			player.rotationYawHead = mc.player.rotationYawHead;

			FakePlayerModule fpm = new FakePlayerModule(this, player);
			
			fakePlayers.add(fpm);
			
			player.inventory.armorInventory.set(3, fpm.container.getSlot(0).getStack());
			player.inventory.armorInventory.set(2, fpm.container.getSlot(1).getStack());
			player.inventory.armorInventory.set(1, fpm.container.getSlot(2).getStack());
			player.inventory.armorInventory.set(0, fpm.container.getSlot(3).getStack());
			
			player.inventory.currentItem = 0;
			player.inventory.mainInventory.set(0, fpm.container.getSlot(4).getStack());
			player.inventory.offHandInventory.set(0, fpm.container.getSlot(5).getStack());

            mc.world.addEntityToWorld(player.hashCode(), player);
            
		} else if(button instanceof DeleteButtonWrapper) {
			FakePlayerModule mod = ((DeleteButtonWrapper)button).module;
			fakePlayers.remove(mod);
			buttonList.remove(mod.delete);
			buttonList.remove(mod.options);
			mc.world.removeEntity(mod.player);
		} else if(button instanceof OptionButtonWrapper){
			FakePlayerModule mod = ((OptionButtonWrapper) button).module;
			displayScreen(mod.optionsScreen);
		} else {
			super.actionPerformed(button);
		}
	}
	
	@Override
	public void onGuiClosed() {
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		
		scroll.setDisabled(fakePlayers.size() <= modules);
		
		scroll.draw(mouseX, mouseY);
		
		nameField.drawTextBox();
				
		int startDrawing = (int) (fakePlayers.size() * scroll.getScroll());
		
		startDrawing = MathHelper.clamp(startDrawing, 0, fakePlayers.size() - modules < 0 ? 0 : fakePlayers.size() - modules);
		
		for(int i = 0; i < startDrawing; i++) {
			buttonList.remove(fakePlayers.get(i).delete);
			buttonList.remove(fakePlayers.get(i).options);
		}
		
		for(int i = startDrawing + modules; i < fakePlayers.size(); i++) {
			buttonList.remove(fakePlayers.get(i).delete);
			buttonList.remove(fakePlayers.get(i).options);
		}
		
		for(int i = startDrawing; i < fakePlayers.size(); i++) {
			if(i >= startDrawing + modules) {
				break;
			}
			DeleteButtonWrapper dbw = fakePlayers.get(i).delete;
			if(!buttonList.contains(dbw)) {
				buttonList.add(dbw);
			}
			OptionButtonWrapper obw = fakePlayers.get(i).options;
			if(!buttonList.contains(obw)) {
				buttonList.add(obw);
			}
			
			fakePlayers.get(i).draw();
		}
				
	}

	@Override
	protected void updateButtonPositions(int x, int y) {
		addPlayer.x = x - 20;
		addPlayer.y = y - 84;
		nameField.x = x - 130;
		nameField.y = y - 80;
		back.x = x + 50;
		back.y = y - 84;
		
		scroll.x = x + 125;
		scroll.y = y - 50;
		
		int startDrawing = (int) (fakePlayers.size() * scroll.getScroll());
		
		startDrawing = MathHelper.clamp(startDrawing, 0, fakePlayers.size() - modules < 0 ? 0 : fakePlayers.size() - modules);
		
		int yScroll = startDrawing * FakePlayerModule.height;
		
		int yOffset = y - 50 - yScroll;
				
		for(FakePlayerModule m : fakePlayers) {
			m.x = x - 130;
			m.y = yOffset;
			yOffset += FakePlayerModule.height;
			m.delete.y = m.y + FakePlayerModule.height / 2 - m.delete.height / 2;
			m.delete.x = m.x + m.width - 10 - m.delete.width;
			m.options.y = m.y + FakePlayerModule.height / 2 - m.options.height / 2;
			m.options.x = m.delete.x - 10 - m.options.width;
		}
		
	}

	static class FakePlayerModule extends Gui {
		
		private EntityFakePlayer player;
		private OptionButtonWrapper options;
		private DeleteButtonWrapper delete;
		
		private int x;
		private int y;
		
		private int width;
		private static final int height = 22;
		
		//private GuiScreen parent;
		
		private OptionsMenu optionsScreen;
		private ContainerFakePlayerLoadout container;
		
		private FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		private FakePlayerModule(GuiScreen parent, EntityFakePlayer player) {
			this(parent, player, 0, 0, 250);
		}
		
		private FakePlayerModule(GuiScreen parent, EntityFakePlayer player, int x, int y, int width) {
			//this.parent = parent;
			this.player = player;
			this.x = x;
			this.y = y;
			this.width = width;
			options = new OptionButtonWrapper(this);
			delete = new DeleteButtonWrapper(this);
			container = new ContainerFakePlayerLoadout(player);
			optionsScreen = new OptionsMenu(container, parent, player);
		}
		
		private void draw() {
			
			drawRect(x, y, x + width, y + height, Color.ORANGE.getRGB());
			drawRect(x + 1, y + 1, x + width - 1, y + height - 1, Color.BLACK.getRGB());
			fontRenderer.drawString(player.getName(), x + 5, y + height / 2 - fontRenderer.FONT_HEIGHT / 2, Color.WHITE.getRGB());

		}
		
		static class OptionButtonWrapper extends OptionsButton {

			private FakePlayerModule module;
			
			private OptionButtonWrapper(FakePlayerModule module) {
				super(0, 0, 0, 14, 14, "", "Options", 1);
				this.module = module;
			}
			
		}
		
		static class DeleteButtonWrapper extends GuiButton {
			
			private FakePlayerModule module;
			
			private DeleteButtonWrapper(FakePlayerModule module) {
				super(1, 0, 0, 60, 20, "Delete");
				this.module = module;
			}
			
		}
		
		static class OptionsMenu extends GuiContainer {

		    private static final ResourceLocation FAKEPLAYERINV = new ResourceLocation("taecosmetics","textures/gui/fakeplayercontainer.png");

		    private EntityFakePlayer fakePlayer;
		    
			private GuiScreen parent;
			
			private GuiButton back = new GuiButton(0, 0, 0, 60, 20, "Back");
			
		    private GuiTextField bodyYaw;
		    
		    private GuiTextField headYaw;

		    private GuiTextField headPitch;
		    
		    private GuiTextField playerX;

		    private GuiTextField playerY;

		    private GuiTextField playerZ;
			
			private OptionsMenu(Container inventorySlotsIn, GuiScreen parent, EntityFakePlayer fakePlayer) {
				super(inventorySlotsIn);
				this.parent = parent;
				this.fakePlayer = fakePlayer;
				xSize = 192;
				ySize = 184;
			}

			@Override
			public void initGui() {
				
				buttonList.add(back);
				
				bodyYaw = new GuiTextField(0, fontRenderer, 0, 0, 50, 12);
				bodyYaw.setTextColor(-1);
				bodyYaw.setDisabledTextColour(-1);
				bodyYaw.setEnableBackgroundDrawing(true);
				bodyYaw.setMaxStringLength(16);
				bodyYaw.setText(Float.toString(Math.round(fakePlayer.rotationYaw % 360)));
				
				headYaw = new GuiTextField(0, fontRenderer, 0, 0, 50, 12);
				headYaw.setTextColor(-1);
				headYaw.setDisabledTextColour(-1);
				headYaw.setEnableBackgroundDrawing(true);
				headYaw.setMaxStringLength(16);
				headYaw.setText(Float.toString(Math.round(fakePlayer.rotationYawHead % 360)));
				
				headPitch = new GuiTextField(0, fontRenderer, 0, 0, 50, 12);
				headPitch.setTextColor(-1);
				headPitch.setDisabledTextColour(-1);
				headPitch.setEnableBackgroundDrawing(true);
				headPitch.setMaxStringLength(16);
				headPitch.setText(Float.toString(Math.round(fakePlayer.rotationPitch % 360)));
				
				playerX = new GuiTextField(0, fontRenderer, 0, 0, 50, 12);
				playerX.setTextColor(-1);
				playerX.setDisabledTextColour(-1);
				playerX.setEnableBackgroundDrawing(true);
				playerX.setMaxStringLength(16);
				playerX.setText(Double.toString(Math.round(fakePlayer.posX * 10) / 10D));
				
				playerY = new GuiTextField(0, fontRenderer, 0, 0, 50, 12);
				playerY.setTextColor(-1);
				playerY.setDisabledTextColour(-1);
				playerY.setEnableBackgroundDrawing(true);
				playerY.setMaxStringLength(16);
				playerY.setText(Double.toString(Math.round(fakePlayer.posY * 10) / 10D));
				
				playerZ = new GuiTextField(0, fontRenderer, 0, 0, 50, 12);
				playerZ.setTextColor(-1);
				playerZ.setDisabledTextColour(-1);
				playerZ.setEnableBackgroundDrawing(true);
				playerZ.setMaxStringLength(16);
				playerZ.setText(Double.toString(Math.round(fakePlayer.posZ * 10) / 10D));
				
		        Keyboard.enableRepeatEvents(true);

				super.initGui();
			}
			
			@Override
			protected void keyTyped(char typedChar, int keyCode) throws IOException {
				
				if (playerX.textboxKeyTyped(typedChar, keyCode)) {
					
				} else if(playerY.textboxKeyTyped(typedChar, keyCode)) {
					
				} else if(playerZ.textboxKeyTyped(typedChar, keyCode)) {
					
				} else if(headYaw.textboxKeyTyped(typedChar, keyCode)) {
					
				} else if(headPitch.textboxKeyTyped(typedChar, keyCode)) {
					
				} else if(bodyYaw.textboxKeyTyped(typedChar, keyCode)) {
					
				} else {
		            super.keyTyped(typedChar, keyCode);
		        }
			}
			
			@Override
			protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
				
				headYaw.mouseClicked(mouseX, mouseY, mouseButton);
				headPitch.mouseClicked(mouseX, mouseY, mouseButton);
				bodyYaw.mouseClicked(mouseX, mouseY, mouseButton);
				
				playerX.mouseClicked(mouseX, mouseY, mouseButton);
				playerY.mouseClicked(mouseX, mouseY, mouseButton);
				playerZ.mouseClicked(mouseX, mouseY, mouseButton);
				
				super.mouseClicked(mouseX, mouseY, mouseButton);
								
			}
			
			@Override
			protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				this.mc.getTextureManager().bindTexture(FAKEPLAYERINV);
				int i = (this.width - this.xSize) / 2;
				int j = (this.height - this.ySize) / 2;
				this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
			}		
			
			@Override
			public void drawScreen(int mouseX, int mouseY, float tick) {
				int i = (this.width - this.xSize) / 2;
				int j = (this.height - this.ySize) / 2;
				updateLocations(i, j);
				
				super.drawScreen(mouseX, mouseY, tick);
		        this.renderHoveredToolTip(mouseX, mouseY);
		        GlStateManager.disableLighting();
		        GlStateManager.disableBlend();
		        
		        fontRenderer.drawString("X", i + 3, j + 142, Color.BLACK.getRGB());
		        fontRenderer.drawString("Y", i + 63, j + 142, Color.BLACK.getRGB());
		        fontRenderer.drawString("Z", i + 123, j + 142, Color.BLACK.getRGB());

		        fontRenderer.drawString("Body Yaw", i + 10, j + 156, Color.BLACK.getRGB());
		        fontRenderer.drawString("Head Yaw", i + 70, j + 156, Color.BLACK.getRGB());
		        fontRenderer.drawString("Head Pitch", i + 130, j + 156, Color.BLACK.getRGB());

				playerX.drawTextBox();
				playerY.drawTextBox();
				playerZ.drawTextBox();
				
				bodyYaw.drawTextBox();
				headYaw.drawTextBox();
				headPitch.drawTextBox();
				
			}
			
			private void updateLocations(int x, int y) {
				playerX.x = x + 10;
				playerX.y = y + 140;
				
				playerY.x = playerX.x + playerX.width + 10;
				playerY.y = y + 140;
				
				playerZ.x = playerY.x + playerY.width + 10;
				playerZ.y = y + 140;
				
				bodyYaw.x = x + 10;
				bodyYaw.y = y + 165;
				
				headYaw.x = bodyYaw.x + bodyYaw.width + 10;
				headYaw.y = y + 165;
				
				headPitch.x = headYaw.x + headYaw.width + 10;
				headPitch.y = y + 165;
				
				back.x = x + 110;
				back.y = y + 38;
			}
			
			@Override
			protected boolean checkHotbarKeys(int keyCode) {
				return false;
			}
			
			@Override
			protected void handleMouseClick(Slot slotIn, int slotId, int mouseButton, ClickType type) {
				inventorySlots.slotClick(slotId, mouseButton, type, Minecraft.getMinecraft().player);
			}
			
			@Override
			public boolean doesGuiPauseGame() {
			    return false;
			}
			
			@Override
			protected void actionPerformed(GuiButton button) throws IOException {
				if(button == back) {
					AbstractTAEGuiScreen.displayScreen(parent);
				} else {
					super.actionPerformed(button);
				}
			}
			
			@Override
			public void onGuiClosed() {
		        super.onGuiClosed();
		        Keyboard.enableRepeatEvents(false);
		        
		        try {
		        	fakePlayer.posX = Double.parseDouble(playerX.getText());
		        } catch(Exception e) {}
		        
		        try {
		        	fakePlayer.posY = Double.parseDouble(playerY.getText());
		        } catch(Exception e) {}
		        
		        try {
		        	fakePlayer.posZ = Double.parseDouble(playerZ.getText());
		        } catch(Exception e) {}
		        
		        try {
		        	fakePlayer.rotationYawHead = Float.parseFloat(headYaw.getText());
		        } catch(Exception e) {}
		        
		        try {
		        	fakePlayer.rotationPitch = Float.parseFloat(headPitch.getText());
		        } catch(Exception e) {}
		        
		        try {
		        	fakePlayer.rotationYaw = Float.parseFloat(bodyYaw.getText());
		        } catch(Exception e) {}
		        
		    }
			
		}
		
		static class ContainerFakePlayerLoadout extends Container {
			
		    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
			
		    private final Inventory inventory = new Inventory();
		    private EntityFakePlayer fakePlayer;
		    
			private ContainerFakePlayerLoadout(EntityFakePlayer fakePlayer) {
				this.fakePlayer = fakePlayer;
				EntityPlayerSP player = Minecraft.getMinecraft().player;
				inventory.setInventorySlotContents(0, player.inventory.armorInventory.get(3));
				inventory.setInventorySlotContents(1, player.inventory.armorInventory.get(2));
				inventory.setInventorySlotContents(2, player.inventory.armorInventory.get(1));
				inventory.setInventorySlotContents(3, player.inventory.armorInventory.get(0));
				
				inventory.setInventorySlotContents(4, player.getHeldItemMainhand());
				inventory.setInventorySlotContents(5, player.getHeldItemOffhand());
				
				for(int i = 0; i < player.inventory.mainInventory.size(); i++) {
					inventory.setInventorySlotContents(6 + i, player.inventory.mainInventory.get(i));
				}
				
				for(int i = 0; i < player.inventory.offHandInventory.size(); i++) {
					inventory.setInventorySlotContents(42 + i, player.inventory.offHandInventory.get(i));
				}
				
				for(int i = 0; i < player.inventory.armorInventory.size(); i++) {
					inventory.setInventorySlotContents(43 + i, player.inventory.armorInventory.get(player.inventory.armorInventory.size() - i - 1));
				}
				
				for (int k = 0; k < 4; ++k) {
		            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
		            this.addSlotToContainer(new ArmorPlaceSlot(entityequipmentslot, inventory, k, 16 + k * 18, 16));
		        }
								
				this.addSlotToContainer(new PlaceSlot(inventory, 4, 88, 16));
					
				this.addSlotToContainer(new PlaceSlot(inventory, 5, 106, 16) {
					@Override
	                @Nullable
	                @SideOnly(Side.CLIENT)
	                public String getSlotTexture() {
		                return "minecraft:items/empty_armor_slot_shield";
	                }
				});
				
				for (int l = 0; l < 4; ++l)
		        {
		            for (int j1 = 0; j1 < 9; ++j1)
		            {
		                this.addSlotToContainer(new CopySlot(inventory, 6 + j1 + l * 9, 16 + j1 * 18, 60 + l * 18));
		            }
		        }
				
				this.addSlotToContainer(new CopySlot(inventory, 42, 16, 42) {
					@Override
	                @Nullable
	                @SideOnly(Side.CLIENT)
	                public String getSlotTexture() {
		                return "minecraft:items/empty_armor_slot_shield";
	                }
				});
				
				for(int i = 0; i < 4; i++) {
		            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[i];
					this.addSlotToContainer(new CopySlot(inventory, 43 + i, 34 + i * 18, 42) {
						@Override
		                @Nullable
		                @SideOnly(Side.CLIENT)
		                public String getSlotTexture() {
		                    return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
		                }
					});
				}
								
			}
			
			@Override
			public void onContainerClosed(EntityPlayer playerIn) {
		        InventoryPlayer inventoryplayer = playerIn.inventory;
	            inventoryplayer.setItemStack(ItemStack.EMPTY);

		        fakePlayer.inventory.armorInventory.set(3, getSlot(0).getStack());
		        fakePlayer.inventory.armorInventory.set(2, getSlot(1).getStack());
		        fakePlayer.inventory.armorInventory.set(1, getSlot(2).getStack());
		        fakePlayer.inventory.armorInventory.set(0, getSlot(3).getStack());
				
		        fakePlayer.inventory.currentItem = 0;
		        fakePlayer.inventory.mainInventory.set(0, getSlot(4).getStack());
		        fakePlayer.inventory.offHandInventory.set(0, getSlot(5).getStack());
		    }
			
			@Override
			public boolean canInteractWith(EntityPlayer playerIn) {
				return true;
			}
			
			@Override
			public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
				return ItemStack.EMPTY;
		    }
			
			@Override
			public void detectAndSendChanges() {
				
			}
			
			@Override
			public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
		        InventoryPlayer inventoryplayer = player.inventory;
				if(slotId < 6 && slotId >= 0) {
					ItemStack stackInHand = inventoryplayer.getItemStack();
					inventoryplayer.setItemStack(inventory.getStackInSlot(slotId));
					inventory.setInventorySlotContents(slotId, stackInHand);
					return ItemStack.EMPTY;
				} else {
					try {
						inventoryplayer.setItemStack(inventory.getStackInSlot(slotId));
						return ItemStack.EMPTY;
					} catch(Exception e) {
						PlayerUtils.sendMessage(e.getClass().toString() + " " + e.getMessage());
						return ItemStack.EMPTY;
					}
				}
			}
			
			static class CopySlot extends Slot {

				public CopySlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
					super(inventoryIn, index, xPosition, yPosition);
				}
				
				@Override
            	public int getSlotStackLimit() {
                    return 64;
                }
                @Override
                public boolean isItemValid(ItemStack stack) {
                	return false;
                }
                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                   return true;
                }
                @Override
                public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
                    return stack;
                }
                @Override
                public void putStack(ItemStack stack) {
                }
                
			}
			
			static class ArmorPlaceSlot extends PlaceSlot {

				private EntityEquipmentSlot slot;
				
				public ArmorPlaceSlot(EntityEquipmentSlot entityequipmentslot, IInventory inventoryIn, int index, int xPosition, int yPosition) {
					super(inventoryIn, index, xPosition, yPosition);
					slot = entityequipmentslot;
				}
				
				@Override
				public boolean isItemValid(ItemStack stack) {
					return stack.getItem().isValidArmor(stack, slot, Minecraft.getMinecraft().player);
				}
				@Override
                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return ItemArmor.EMPTY_SLOT_NAMES[slot.getIndex()];
                }
			}
			
			static class PlaceSlot extends Slot {

				public PlaceSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
					super(inventoryIn, index, xPosition, yPosition);
				}
				
				@Override
            	public int getSlotStackLimit() {
                    return 1;
                }
                @Override
                public boolean isItemValid(ItemStack stack) {
                	return true;
                }
                @Override
                public boolean canTakeStack(EntityPlayer playerIn) {
                   return true;
                }
                @Override
                public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {
                    return ItemStack.EMPTY;
                }
                @Override
                public void putStack(ItemStack stack) {
                    inventory.setInventorySlotContents(slotNumber, stack);
                }
				
			}
			
			static class Inventory implements IInventory {

			    private final NonNullList<ItemStack> inv = NonNullList.<ItemStack>withSize(6, ItemStack.EMPTY);
			    private final NonNullList<ItemStack> mainCopy = NonNullList.<ItemStack>withSize(36, ItemStack.EMPTY);
			    private final NonNullList<ItemStack> offhand = NonNullList.<ItemStack>withSize(1, ItemStack.EMPTY);
			    private final NonNullList<ItemStack> armorCopy = NonNullList.<ItemStack>withSize(4, ItemStack.EMPTY);
			    
				@Override
				public String getName() {
					return "fakeplayerinv";
				}

				@Override
				public boolean hasCustomName() {
					return false;
				}

				@Override
				public ITextComponent getDisplayName() {
					return new TextComponentString("Fake Player Inventory");
				}

				@Override
				public int getSizeInventory() {
					return inv.size() + mainCopy.size() + offhand.size() + armorCopy.size();
				}

				@Override
				public boolean isEmpty() {
					for(ItemStack stack : inv) {
						if(!stack.isEmpty()) {
							return false;
						}
					}
					for(ItemStack stack : mainCopy) {
						if(!stack.isEmpty()) {
							return false;
						}
					}
					for(ItemStack stack : offhand) {
						if(!stack.isEmpty()) {
							return false;
						}
					}
					for(ItemStack stack : armorCopy) {
						if(!stack.isEmpty()) {
							return false;
						}
					}
					return true;
				}

				@Override
				public ItemStack getStackInSlot(int index) {
					if(index >= 0 && index < 6) {
						return inv.get(index);
					} else if(index >= 6 && index < 42) {
						return mainCopy.get(index - 6);
					} else if(index >= 42 && index < 43) {
						return offhand.get(index - 42);
					} else if(index >= 43) {
						return armorCopy.get(index - 43);
					}
					return ItemStack.EMPTY;
				}

				@Override
				public ItemStack decrStackSize(int index, int count) {
					return ItemStack.EMPTY;
				}

				@Override
				public ItemStack removeStackFromSlot(int index) {
					return ItemStack.EMPTY;
				}

				@Override
				public void setInventorySlotContents(int index, ItemStack stack) {
					if(index >= 0 && index < 6) {
						inv.set(index, stack);
					} else if(index >= 6 && index < 42) {
						mainCopy.set(index - 6, stack);
					} else if(index >= 42 && index < 43) {
						offhand.set(index - 42, stack);
					} else if(index >= 43) {
						armorCopy.set(index - 43, stack);
					}
				}

				@Override
				public int getInventoryStackLimit() {
					return 64;
				}

				@Override
				public void markDirty() {
					
				}

				@Override
				public boolean isUsableByPlayer(EntityPlayer player) {
					return true;
				}

				@Override
				public void openInventory(EntityPlayer player) {
					
				}

				@Override
				public void closeInventory(EntityPlayer player) {
					
				}

				@Override
				public boolean isItemValidForSlot(int index, ItemStack stack) {
					return true;
				}

				@Override
				public int getField(int id) {
					return 0;
				}

				@Override
				public void setField(int id, int value) {					
				}

				@Override
				public int getFieldCount() {
					return 0;
				}

				@Override
				public void clear() {					
				}
				
			}
			
		}
		
	}
	
}
