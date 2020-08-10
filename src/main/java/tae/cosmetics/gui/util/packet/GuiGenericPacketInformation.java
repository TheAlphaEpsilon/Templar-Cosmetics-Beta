package tae.cosmetics.gui.util.packet;

import java.awt.Color;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.network.Packet;
import tae.cosmetics.exceptions.TAEModException;
import tae.cosmetics.gui.util.ScrollBar;
import tae.cosmetics.guiscreen.AbstractTAEGuiScreen;

public class GuiGenericPacketInformation extends AbstractTAEGuiScreen {
	
	private static final Set<Class<?>> BLACKLIST;
	
	static {
		BLACKLIST = new HashSet<>();
		BLACKLIST.add(Boolean.class);
		BLACKLIST.add(Character.class);
		BLACKLIST.add(Byte.class);
		BLACKLIST.add(Short.class);
		BLACKLIST.add(Integer.class);
		BLACKLIST.add(Long.class);
		BLACKLIST.add(Float.class);
		BLACKLIST.add(Double.class);
		BLACKLIST.add(Void.class);
		BLACKLIST.add(Object.class);
		BLACKLIST.add(String.class);
		BLACKLIST.add(Logger.class);
	}
	
	private static final int cutoff = 10;
	
	private Packet<?> packet;
	private ScrollBar scroll = new ScrollBar(0, 0, 100);
	
	private int drawY;
	private int prevMax;
	
	public GuiGenericPacketInformation(Packet<?> packet, GuiScreen parent) {
		
		super(parent);
		override = true;
		this.packet = packet;
	}
	
	@Override
	public void initGui() {
		scroll.x = width / 2 + 100;
		scroll.y = width / 2 - 100;
		
		super.initGui();
	}
	
	@Override
	public void onGuiClosed() {
		
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		this.drawDefaultBackground();
		
		int i = width / 2;
		int j = height / 2;
		
		scroll.draw(mouseX, mouseY);
		
		if(packet != null) {
			drawData(0, j - 100);
		}
		
		updateButtonPositions(i, j);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		
	}
	
	private void drawData(int x, int y) {
		
		float scrollAmt = scroll.getScroll();
		
		drawY = 0;
		
		Class<?> packet0 = packet.getClass();
		
		Class<?> temp = packet.getClass();
		
		//For subclass packets
		while(true) {
			
			Class<?> superClazz = temp.getSuperclass();
			
			if(superClazz != Packet.class && Packet.class.isAssignableFrom(superClazz)) {
				packet0 = superClazz;
			} else {
				break;
			}
			
			temp = superClazz;
			
		}
		
		fontRenderer.drawString("Packet: " + packet.getClass().getSimpleName(), x, (int) (y - (scrollAmt * prevMax)), Color.WHITE.getRGB());
		
		fontRenderer.drawString("Parent: " + packet0.getSimpleName(), x, (int) ((y + fontRenderer.FONT_HEIGHT + 5) - (scrollAmt * prevMax)), Color.WHITE.getRGB());
		
		drawData0(packet, packet0, x, (int) ((y + fontRenderer.FONT_HEIGHT + 5) - (scrollAmt * prevMax)), 1, 0);
		
		prevMax = drawY;
		
	}
	
	private void drawData0(Object parent, Class<?> clazz, int x, int y, int tab, int counter) {
		
		if(counter > cutoff) {
			
			fontRenderer.drawString("... data cut ...", x + 5 * tab, y + (drawY += fontRenderer.FONT_HEIGHT + 5), Color.WHITE.getRGB());
			return;
			
		}
		
		for(Field f : (clazz == null) ? parent.getClass().getDeclaredFields() : clazz.getDeclaredFields()) {
						
			try {
				
				f.setAccessible(true);
				
				Object obj = f.get(parent);
				
				fontRenderer.drawString(f.getType().getSimpleName() + " " + f.getName()  + " :", x + 5 * tab, y + (drawY += fontRenderer.FONT_HEIGHT + 5), Color.WHITE.getRGB());
				
				if(obj == null) {
					return;
				}
				
				fontRenderer.drawString(obj.toString(), x + 5 * tab + 5 + fontRenderer.getStringWidth(f.getType().getSimpleName() + " " + f.getName() + " :"), y + drawY , Color.WHITE.getRGB());
				
				if(f.getType().isArray()) {
										
					int length = Array.getLength(obj);
					
					fontRenderer.drawString("Length: " + length, x + 5 * tab + fontRenderer.getStringWidth(f.getType().getSimpleName() + " " + f.getName() + " :") + 10 + fontRenderer.getStringWidth(obj.toString()), y + drawY, Color.WHITE.getRGB());
										
					for(int i = 0; i < (length > cutoff ? cutoff : length); i++) {
											
						Object e = Array.get(obj, i);
						
						if(e != null) {
							fontRenderer.drawString(e.getClass().getSimpleName() + " : " + e.toString(), x + 5 * tab + 5, y + (drawY += fontRenderer.FONT_HEIGHT + 5), Color.WHITE.getRGB());
						
							if( ! f.getType().isPrimitive() && ! BLACKLIST.contains(e.getClass()) && !e.getClass().isEnum() ) {
								drawData0(e, null, x, y, tab + 2, counter + 1);
							}
						}
						
					}
					
					if(length > cutoff) {
						fontRenderer.drawString("... data cut ...", x + 5 * tab, y + (drawY += fontRenderer.FONT_HEIGHT + 5), Color.WHITE.getRGB());
					}
					
					//help try to prevent infinite loops
				} else if( ! f.getType().isPrimitive() 
						&& ! BLACKLIST.contains(f.getType()) 
						&& !f.getType().isEnum() 
						&& !f.getType().equals(parent.getClass()) 
						&& obj != null) {
					
					drawData0(obj, null, x, y, tab + 1, counter + 1);
				}
				
			} catch(Exception e) {
				new TAEModException(e.getClass(), e.getMessage()).post();
			}
			
		}
		
	}

	@Override
	protected void updateButtonPositions(int x, int y) {	
		back.x = 0;
		back.y = 0;
	}

	@Override
	protected void drawScreen0(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		
	}
	
}
