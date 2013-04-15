package main;

import translator.Translator;

public enum OptionList {
	IMAGE_NORTH_TEXT_SOUTH("Bild oben, Text einheitlich unten",OptionList.TYPE_TEXT_LAYOUT),
	IMAGE_NORTH_TEXT_NORTH("Bild oben, Text oben",OptionList.TYPE_TEXT_LAYOUT),
	IMAGE_SOUTH_TEXT_NORTH("Text oben, Bild unten",OptionList.TYPE_TEXT_LAYOUT),
	NO_IMAGE_TEXT_NORTH("Kein Bild, Text oben",OptionList.TYPE_TEXT_LAYOUT),
	DIAGONAL_CONNECTION("Personen mit diagonalen Linien verbinden",OptionList.TYPE_CONNECTION_MODE),
	RECTANGLE_CONNECTION("Personen mit senkrecht und waagrechten Linien verbinden",OptionList.TYPE_CONNECTION_MODE),
	NO_DRAW_BACKGROUND("Kein Hintergrundbild generieren",OptionList.TYPE_BACKGROUND_MODE),
	DRAW_MIRRORED_BACKGROUND("Geladenes Hintergrundbild verwenden",OptionList.TYPE_BACKGROUND_MODE),
	DRAW_LOADED_BACKGROUND("Geladenes Hintergrundbild verwenden",OptionList.TYPE_BACKGROUND_MODE),
	BACKGROUND_SUMMER_TREE("Sommerbaum als Hintergrundbild erzeugen",OptionList.TYPE_BACKGROUND_MODE),
	BACKGROUND_SUMMER_TREE_RANDOM("Sommerbaum mit Zufall als Hintergrundbild erzeugen",OptionList.TYPE_BACKGROUND_MODE),
	NAME_LINE_BREAK("Vorname und Nachname in zwei Zeilen",OptionList.TYPE_LINE_BREAK_MODE),
	NAME_NO_LINE_BREAK("Vorname und Nachname in einer Zeile",OptionList.TYPE_LINE_BREAK_MODE),
	FIXED_PERSON_DATA_POSITIONS("Personeninformationen stehen immer in der selben Höhe",OptionList.TYPE_PERSON_POSITION_DATA_MODE),
	FLOW_PERSON_DATA_POSITIONS("Personeninformationen werden bündig angeordnet",OptionList.TYPE_PERSON_POSITION_DATA_MODE),
	MOUSE_DEFAULT_MODE("Mausstandardeinstellung",OptionList.TYPE_MOUSE_MODE),
	MOUSE_SWAPPED_MODE("Maustasten vertauscht",OptionList.TYPE_MOUSE_MODE),
	KEYBOARD_ACTIVE("Tastatureingaben aktiv", OptionList.TYPE_KEYBOARD_MODE),
	KEYBOARD_INACTIVE("Tastatureingaben inaktiv", OptionList.TYPE_KEYBOARD_MODE); 
	
	static public final int TYPE_TEXT_LAYOUT = 0;
	static public final int TYPE_CONNECTION_MODE = 1;
	static public final int TYPE_BACKGROUND_MODE = 2;
	static public final int TYPE_LINE_BREAK_MODE = 3;
	static public final int TYPE_PERSON_POSITION_DATA_MODE = 4;
	static public final int TYPE_MOUSE_MODE = 5;
	static public final int TYPE_KEYBOARD_MODE = 6;
	
	private String description;
	
	private String configName;
	
	private int id;
	
	private OptionList(String mode, int id){
		this.description = mode;
		this.id = id;
		switch(this.id){
		case TYPE_TEXT_LAYOUT:
			configName = "orientationmode=";
			break;
		case TYPE_CONNECTION_MODE:
			configName = "connectionmode=";
			break;
		case TYPE_BACKGROUND_MODE:
			configName = "backgroundmode=";
			break;
		case TYPE_LINE_BREAK_MODE:
			configName = "linebreakmode=";
			break;
		case TYPE_PERSON_POSITION_DATA_MODE:
			configName = "datapositioningmode=";
			break;
		case TYPE_MOUSE_MODE:
			configName = "mousemode=";
			break;
		case TYPE_KEYBOARD_MODE:
			configName = "keyboardmode=";
			break;
		default:
			configName = "noName";
		}
	}
	
	public int getID(){
		return this.id;
	}
	
	public String getConfigName(){
		return this.configName;
	}
	
	public static void translate(){
		for(OptionList b : OptionList.values()){
			b.description = Main.getTranslator().getTranlation(b.name(), Translator.OPTION_JDIALOG);
		}
	}
	
	public String getMode(){
		return this.description;
	}
	
	@Override
	public String toString(){
		return this.description;
	}
	/**
	 * if there is a corresponding DisplayMode to the String name the Mode is returned
	 * @param mode
	 * @return
	 */
	public static OptionList fromString(String mode){
		if (mode != null) {
		      for (OptionList b : OptionList.values()) {
		        if (mode.equalsIgnoreCase(b.description)) {
		          return b;
		        }
		      }
		    }
		    return null;
		  
	}
	
}
