package tae.cosmetics.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import tae.cosmetics.ColorCode;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.gui.BookArt.Pixel;
import tae.cosmetics.settings.Serializer;
import tae.cosmetics.util.FileHelper;

public class GuiBookArtSaveLoad extends AbstractTAEGuiScreen {

	private static final String fileName = "bookart.txt";
	
	private static final HashMap<String, Pixel[][]> cache = new HashMap<>();
	
	static {
		FileHelper.createFile(fileName);
		load();
	}
	
    private GuiTextField nameField;

    private boolean firstText;
    private GuiButton save = new GuiButton(0, 0, 0, 70, 20, "Save");
    private Pixel[][] currPixels = new Pixel[0][0];
    
    private STATE state;
    
    protected GuiBookArtSaveLoad(GuiScreen parent) {
    	super(parent);
    	state = STATE.LOAD;
    }
    
	protected GuiBookArtSaveLoad(GuiScreen parent, Pixel[][] pixels) {
		super(parent);
		state = STATE.SAVE;
		currPixels = pixels;
		guiwidth = 150;
		guiheight = 100;
	}
	
	@Override
	public void initGui() {
				
		for(String s : cache.keySet()) {
			new NameWrapper(s);
		}
		
		if(state == STATE.SAVE) {
			if(nameField == null) {
				nameField = new GuiTextField(0, fontRenderer, 0, 0, 103, 12);
				nameField.setTextColor(-1);
		    	nameField.setDisabledTextColour(-1);
		    	nameField.setEnableBackgroundDrawing(true);
		    	nameField.setMaxStringLength(32);
		    	
		    	nameField.setText("NAME");
			}
			
	        firstText = true;
	        
	        buttonList.add(save);
		}
        
        super.initGui();
	}

	@Override
	public void onGuiClosed() {		
	}

	@Override
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		
		int i = width / 2;
		int j = height / 2;
				
		if(state == STATE.SAVE) {
			nameField.drawTextBox();
		} else {
			for(NameWrapper nw : NameWrapper.allWrappers){
				nw.draw(i - 130, j - 80, mouseX, mouseY);
			}
		}
	}

	@Override
	protected void updateButtonPositions(int x, int y) {	
		
		if(state == STATE.SAVE) {
			nameField.x = x - nameField.width / 2;
			nameField.y = y - 20;
			
			save.x = x - save.width / 2;
			save.y = y;
			
			back.x = x - back.width / 2;
			back.y = y + 22;
		}
		
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		
		if(nameField != null && nameField.textboxKeyTyped(typedChar, keyCode)) {
			
		} else {
			super.keyTyped(typedChar, keyCode);
		}
		
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
		super.mouseClicked(mouseX, mouseY, mouseButton);
		
		if(firstText && nameField.mouseClicked(mouseX, mouseY, mouseButton)) {
			nameField.setText("");
			firstText = false;
		}
	
		if(NameWrapper.lastHovered != null && NameWrapper.lastHovered.isHovered(mouseX, mouseY)){
			cacheLoad();
			actionPerformed(back);
		}
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
	
		if(button == save) {
			cacheSave();
			super.actionPerformed(back);
		} else {
			super.actionPerformed(button);
		}
	}
	
	private void cacheSave() {
		cache.put(nameField.getText(), currPixels);
	}
	
	private void cacheLoad() {
		BookArt.updatePixels(cache.get(NameWrapper.lastHovered.name));
	}
	
	public static void save() {
		StringBuilder builder = new StringBuilder();
		
		for(String name : cache.keySet()) {
			
			int nameLength = name.length();
			
			builder.append((char)(nameLength + 32)).append(name);
						
			Pixel[][] pixels = cache.get(name);
			
			builder.append((char)(pixels.length + 32));
			
			for(int i = 0; i < pixels.length; i++) {
				for(int j = 0; j < pixels[i].length; j++) {
					
					Pixel p = pixels[i][j];
					
					String chr = Integer.toString(p.getChar());
					
					int charlen = chr.length();
					
					String color = Serializer.bytesToString(Serializer.serializeValue(p.getColor()));
					
					String colorlen = Integer.toString(color.length());
					
					int colorlenlen = colorlen.length();
										
					String format = Serializer.bytesToString(Serializer.serializeValue(p.getFormat()));
					
					String formatlen = Integer.toString(format.length());
					
					int formatlenlen = formatlen.length();
										
					builder.append((char)(charlen + 32)).append(chr)
					.append((char)(colorlenlen + 32)).append(colorlen).append(color)
					.append((char)(formatlenlen + 32)).append(formatlen).append(format);
				}
			}	
			
			builder.append("\n");

		}
				
		FileHelper.overwriteFile(fileName, builder.toString());
		
	}

	private static void load() {
		
		for(String s : FileHelper.readFile(fileName)) {
			
			try {
				char[] chars = s.toCharArray();
				
				int pointer = 0;
				
				int namelen = chars[pointer++] - 32;
				
				String name = decodeHelper(pointer, namelen, chars);
				
				pointer += namelen;
				
				int height = chars[pointer++] - 32;
				
				ArrayList<Pixel> tempArray = new ArrayList<>();
				
				while(pointer < chars.length) {
					
					int charlen = chars[pointer++] - 32;
					
					int chr = Integer.parseInt(decodeHelper(pointer, charlen, chars));
					
					pointer += charlen;
					
					int colorlenlen = chars[pointer++] - 32;
					
					int colorlen = Integer.parseInt(decodeHelper(pointer, colorlenlen, chars));
					
					pointer += colorlenlen;
					
					ColorCode color = (ColorCode) Serializer.deserializeFromBytes(Serializer.stringToBytes(decodeHelper(pointer, colorlen, chars)));
					
					pointer += colorlen;
					
					int formatlenlen = chars[pointer++] - 32;
					
					int formatlen = Integer.parseInt(decodeHelper(pointer, formatlenlen, chars));
					
					pointer += formatlenlen;
					
					ColorCode format = (ColorCode) Serializer.deserializeFromBytes(Serializer.stringToBytes(decodeHelper(pointer, formatlen, chars)));
					
					pointer += formatlen;
					
					Pixel p = new Pixel((char) chr, color, format);
					
					tempArray.add(p);
					
				}
				
				
				int width = tempArray.size() / height;
				
				Pixel[][] pixels = new Pixel[height][width];
				
				for(int i = 0; i < tempArray.size(); i++) {
					pixels[i / width][i % width] = tempArray.get(i);
				}
				
				cache.put(name, pixels);
			} catch (Exception e) {
				new TAEModException(GuiBookArtSaveLoad.class, "Cannot load book page: " + e.getClass() + " " +e.getMessage()).post();
			}
			
		}
		
	}
	
	private static String decodeHelper(int pointer, int length, char[] chars) {
		StringBuilder builder = new StringBuilder();
		for(int i = pointer; i < pointer + length; i++) {
			builder.append(chars[i]);
		}
		return builder.toString();
	}
	
	static class NameWrapper implements Comparable<NameWrapper> {
		
		private static final TreeSet<NameWrapper> allWrappers = new TreeSet<>();
		private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		private static NameWrapper lastHovered;
		private static int totOffset = 0;
		
		private String name;
		private int offset;
		
		private int x;
		private int y;
		private int right;
		private int bottom;
		
		private NameWrapper(String name) {
			this.name = name;
			offset = totOffset;
			
			if(allWrappers.add(this)) {
				totOffset += fontRenderer.FONT_HEIGHT + 2;
			}
			
		}
		
		private void draw(int x, int y, int mouseX, int mouseY) {
			
			y += offset;
			
			this.x = x;
			this.y = y;
			
			right = x + fontRenderer.getStringWidth(name);
			bottom = y + fontRenderer.FONT_HEIGHT;
						
			if(mouseX >= x && mouseY >= y && mouseX <= right && mouseY <= bottom) {
				drawRect(x - 2, y - 2, right + 1, bottom + 1, Color.YELLOW.getRGB());
				lastHovered = this;
			}
			
			drawRect(x - 1, y - 1, right, bottom, Color.BLACK.getRGB());
			
			fontRenderer.drawString(name, x, y, Color.WHITE.getRGB());
			
		}
		
		private boolean isHovered(int mouseX, int mouseY) {
			if(mouseX >= x && mouseY >= y && mouseX <= right && mouseY <= bottom) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public boolean equals(Object obj) {
			if(obj == this) {
				return true;
			} else if(!(obj instanceof NameWrapper)) {
				return false;
			} else {
				NameWrapper nw = (NameWrapper) obj;
				return this.name.equals(nw.name);
			}
		}

		@Override
		public int compareTo(NameWrapper o) {
			return this.name.compareTo(o.name);
		}
		
	}
	
	static enum STATE {
		SAVE, LOAD;
	}
	
}
