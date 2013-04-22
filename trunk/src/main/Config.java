package main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import tree.gui.TreeCanvasMouseListener;
import tree.gui.util.GUIUtils;




/**
 * this class contains every constants of the config file
 * @author Stefan
 *
 */
public final class Config {
	
	static public final String LINE_SEPARATOR = System.getProperties().getProperty("line.separator");

	
	/**
	 * the image positioning mode
	 */
	static public OptionList ORIENTATION_MODE = OptionList.IMAGE_NORTH_TEXT_SOUTH;
	
	/**
	 * the line connection mode
	 */
	
	static public OptionList CONNECTION_MODE = OptionList.DIAGONAL_CONNECTION;
	
	/**
	 * determines if a background image should be calculated
	 */
	static public OptionList BACKGROUND_MODE = OptionList.NO_DRAW_BACKGROUND;
	
	/**
	 * determines if given name and family name are written down in the same line or not
	 */
	static public OptionList LINE_BREAK_MODE = OptionList.NAME_NO_LINE_BREAK;
	/**
	 * determines the positioning of the person information data
	 */
	static public OptionList DATA_POSITIONING_MODE = OptionList.FLOW_PERSON_DATA_POSITIONS;
	
	/**
	 * determines the mouse actions on a certain mouse button
	 */
	static public OptionList MOUSE_MODE = OptionList.MOUSE_DEFAULT_MODE;
	
	/**
	 * decides whether the keyboard is active or not
	 */
	static public OptionList KEYBOARD_MODE = OptionList.KEYBOARD_ACTIVE;
	
	/**
	 * contains the path to the default folder you will see when you open a file chooser
	 * 
	 */
	
	static public OptionList LOOK_AND_FEEL_MODE = OptionList.NATIVE_LOOK_AND_FEEL;
	
	static public String ADDITIONAL_LOOK_AND_FEEL = "javax.swing.plaf.metal.MetalLookAndFeel";
	
	static public String DEFAULT_PATH="./";
	/**
	 * contains the last path which was used
	 */
	static public String LAST_PATH="./";
	
	/**
	 * the parameters for drawing the tree
	 */
	static public int DEFAULT_PERSON_WIDTH = 110;
	static public int DEFAULT_PERSON_HEIGHT = 200;
	
	static public int PERSON_WIDTH = DEFAULT_PERSON_WIDTH;
	static public int PERSON_HEIGHT = DEFAULT_PERSON_HEIGHT;
	
	static public double SCALING = 1.0;
	/**
	 * true if the model should try to calculate the x position of the 
	 * persons automatically
	 */
	static public boolean XAUTO_POSITIONING = false;
	
	
	/**
	 * this imports the data of the config.ini
	 */
	static public void readConfigIni(){
		File config = new File("./config.ini");
		String line = "";
		if(config.exists()){
			BufferedReader in = null;
			try {
				in = new BufferedReader(new FileReader(config));
				while(in.ready()){
					try{
					line = in.readLine();
					if(line.contains("defaultpath=")){
						String defaultpath = line.replaceAll("defaultpath=", "");
						defaultpath = defaultpath.trim();
						Config.DEFAULT_PATH = defaultpath;
					}
					else if(line.contains("personheight=")){
						
						int height = PERSON_HEIGHT;
						try{
						PERSON_HEIGHT = Integer.parseInt(line.replaceAll("personheight=", "").trim());
						}catch(NumberFormatException e){
							PERSON_HEIGHT  = height;
						}
					}
					else if(line.contains("personwidth=")){
						
						int width = PERSON_WIDTH;
						try{
						PERSON_WIDTH = Integer.parseInt(line.replaceAll("personwidth=", "").trim());
						}catch(NumberFormatException e){
							PERSON_WIDTH  = width;
						}
					}
					else if(line.contains("additionalLookAndFeel=")){
						ADDITIONAL_LOOK_AND_FEEL = line.replace("additionalLookAndFeel=", "").trim();
					}
					else{ 
						ORIENTATION_MODE = readOption(line,ORIENTATION_MODE.getConfigName(),ORIENTATION_MODE);
						CONNECTION_MODE = readOption(line,CONNECTION_MODE.getConfigName(), CONNECTION_MODE);
						BACKGROUND_MODE = readOption(line,BACKGROUND_MODE.getConfigName(), BACKGROUND_MODE);
						LINE_BREAK_MODE = readOption(line,LINE_BREAK_MODE.getConfigName(),LINE_BREAK_MODE);
						DATA_POSITIONING_MODE = readOption(line,DATA_POSITIONING_MODE.getConfigName(),DATA_POSITIONING_MODE);
						MOUSE_MODE = readOption(line,MOUSE_MODE.getConfigName(),MOUSE_MODE);
						KEYBOARD_MODE = readOption(line,KEYBOARD_MODE.getConfigName(),KEYBOARD_MODE);
						LOOK_AND_FEEL_MODE = readOption(line,LOOK_AND_FEEL_MODE.getConfigName(),LOOK_AND_FEEL_MODE);
					}
					}catch (IllegalArgumentException illArg){
						System.out.println(line);
						javax.swing.JOptionPane.showMessageDialog(null, "Warnung: Folgende config.ini Zeile fehlerhaft: " +line);
					}
				}
				
			} catch (FileNotFoundException e) {
			} catch (IOException f) {
				// TODO Auto-generated catch block
				f.printStackTrace();
			}finally{
				if(in!=null){
					try {
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		else{
			javax.swing.JOptionPane.
			showMessageDialog(null, "Warnung: Keine config.ini gefunden, es werden die Default Parameter verwendet.");
		}
		
		LAST_PATH=DEFAULT_PATH;
		
	}
	
	static private OptionList readOption(String line, String pattern, OptionList org){
		if(line.contains(pattern)){
			
			if(OptionList.valueOf(line.replaceAll(pattern, "").trim())!=null){
				return OptionList.valueOf(line.replaceAll(pattern, "").trim());
			
			}
		}
		
		return org;
	}
	
	
	
	static public void writeConfigIni(){
		File config = new File("./config.ini");
		if(config.exists()){
			config.delete();
		}
		
		try {
			class OptionWriter extends BufferedWriter{

				public OptionWriter(Writer arg0) {
					super(arg0);
				}
				public void write(OptionList option) throws IOException{
					write(option.getConfigName() + option.name() + LINE_SEPARATOR);
				}
				
			}
			
			OptionWriter out = new OptionWriter(new FileWriter(config));
			out.write("defaultpath=" + DEFAULT_PATH + LINE_SEPARATOR);
			
			out.write(ORIENTATION_MODE);
			out.write(CONNECTION_MODE);
			out.write(BACKGROUND_MODE);
			out.write("personheight=" + PERSON_HEIGHT + LINE_SEPARATOR);
			out.write("personwidth=" + PERSON_WIDTH + LINE_SEPARATOR);
			out.write("scaling=" + SCALING + LINE_SEPARATOR);
			out.write(LINE_BREAK_MODE);
			out.write(DATA_POSITIONING_MODE);
			out.write(MOUSE_MODE);
			out.write(KEYBOARD_MODE);
			out.write(LOOK_AND_FEEL_MODE);
			out.write("additionalLookAndFeel=" + ADDITIONAL_LOOK_AND_FEEL + LINE_SEPARATOR);
			
			out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	public static void setMouseMode(OptionList mouseMode){
		MOUSE_MODE = mouseMode;
		switch(MOUSE_MODE){
		case MOUSE_DEFAULT_MODE:
			TreeCanvasMouseListener.defaultConfig();
			break;
		case MOUSE_SWAPPED_MODE:
			TreeCanvasMouseListener.swapedConfig();
			break;
		default:
			//do nothing
		}
	}
	
	public static void setKeyboardMode(OptionList keyboardMode){
		KEYBOARD_MODE = keyboardMode;
		switch(KEYBOARD_MODE){
			
		case KEYBOARD_ACTIVE:
			Main.getMainFrame().getKeyInputMap().setDefaultKeyBindings();
			break;
		case KEYBOARD_INACTIVE:
			Main.getMainFrame().getKeyInputMap().deactivateKeyBindings();
			break;
		default:
			//do nothing
			
		}
	}
	
	public static void setLookAndFeel(OptionList lookAndFeel){
		LOOK_AND_FEEL_MODE = lookAndFeel;
		GUIUtils.loadLookAndFeel();
	}
	
	public static void setAdditionalLookAndFeel(String name){
		ADDITIONAL_LOOK_AND_FEEL = name;
		GUIUtils.loadLookAndFeel();
	}
	
	static public void initiateConfig(){
		setMouseMode(MOUSE_MODE);
		setKeyboardMode(KEYBOARD_MODE);
	}

}
