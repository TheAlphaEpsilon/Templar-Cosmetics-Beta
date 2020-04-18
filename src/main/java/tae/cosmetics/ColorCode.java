package tae.cosmetics;

public enum ColorCode implements Globals {
	DARK_RED('4',0), RED('c',0), GOLD('6',0), YELLOW('e',0), DARK_GREEN('2',0),
	GREEN('a',0), AQUA('b',0), DARK_AQUA('3',0), DARK_BLUE('1',0),
	BLUE('9',0), LIGHT_PURPLE('d',0), DARK_PURPLE('5',0), WHITE('f',0),
	GRAY('7',0), DARK_GRAY('8',0), BLACK('0',0), OBFUSCATED('k',1), BOLD('l',1), 
	STRIKETHROUGH('m',1), UNDERLINE('n',1), ITALIC('o',1), RESET('r',1);

	private String c;
	private int type;
	
	private ColorCode(char c, int type) {
		this.c = colorcode + c;
		this.type = type;
	}
	
	public String getCode() {
		return c;
	}
	
	public int getType() {
		return type;
	}
	
}
