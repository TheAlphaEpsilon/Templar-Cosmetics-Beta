package tae.cosmetics.mods;

import java.util.ArrayList;

public class AllMods {

	public static final BaseMod BASEMOD = new BaseMod();
	public static final BookTitleMod BOOKTITLEMOD = new BookTitleMod();
	public static final CancelPacketMod CANCELPACKETMOD = new CancelPacketMod();
	public static final ChatEncryption CHATENCRYPTION = new ChatEncryption();
	public static final ChatHighlightMod CHATHIGHLIGHT = new ChatHighlightMod();
	public static final DeathPlus DEATHPLUS = new DeathPlus();
	public static final HoverMapAndBook HOVERMAPBOOK = new HoverMapAndBook();
	public static final HoverMapInFrame HOVERMAPFRAME = new HoverMapInFrame();
	public static final ModifiedFreecam FREECAM = new ModifiedFreecam();
	public static final PearlTracking PEARLTRACKING = new PearlTracking();
	public static final PlayerFaker PLAYERFAKER = new PlayerFaker();
	public static final QueuePeekMod QUEUEPEEK = new QueuePeekMod();
	public static final StalkerMod STALKERMOD = new StalkerMod();
	public static final ThrownEntityTrails ENTITYDATA = new ThrownEntityTrails();
	public static final TimeStampMod TIMESTAMP = new TimeStampMod();
	public static final UnicodeKeyboard KEYBOARD = new UnicodeKeyboard();
	public static final VisualizePacketsMod PACKETVIEWER = new VisualizePacketsMod();
	
	public static final ArrayList<BaseMod> mods = new ArrayList<>();
	
	static {
		mods.add(BASEMOD);
		mods.add(BOOKTITLEMOD);
		mods.add(CANCELPACKETMOD);
		mods.add(CHATENCRYPTION);
		mods.add(DEATHPLUS);
		mods.add(ENTITYDATA);
		mods.add(FREECAM);
		mods.add(HOVERMAPBOOK);
		mods.add(HOVERMAPFRAME);
		mods.add(KEYBOARD);
		mods.add(PACKETVIEWER);
		mods.add(PEARLTRACKING);
		mods.add(PLAYERFAKER);
		mods.add(QUEUEPEEK);
		mods.add(STALKERMOD);
		mods.add(TIMESTAMP);
		mods.add(CHATHIGHLIGHT);
	}
	
}
