package tree.gui.draw.backgrounds;

import javax.swing.JMenuItem;

import main.Config;
import main.Main;
import main.OptionList;

public class BackgroundFactory {
	
	static private Background background=null;
	
	static private DrawBackgroundImage image;
	
	static private JMenuItem exportListener;
	
	static public Background generateBackground(int width, int height){
		switch(Config.BACKGROUND_MODE){
		case BACKGROUND_SUMMER_TREE:
		case BACKGROUND_SUMMER_TREE_RANDOM:
			if(background!=null && background instanceof SummerTree){
				return background;
			}
			setBackground(new SummerTree(width, height, 800, 
					Config.BACKGROUND_MODE.equals(OptionList.BACKGROUND_SUMMER_TREE_RANDOM)));
			return background;
		case DRAW_MIRRORED_BACKGROUND:
			if(image!=null){
				image.setMode(DrawBackgroundImage.MODE_MIRRORED);
			}
			setBackground(image);		
			return background;
		case DRAW_LOADED_BACKGROUND:
			if(image!=null){
				image.setMode(DrawBackgroundImage.MODE_SCALED);
			}
			setBackground(image);
			return background; 
		case DRAW_LOADED_BACKGROUND_FIT:
			if(image!=null){
				image.setMode(DrawBackgroundImage.MODE_FIT);
			}
			setBackground(image);
			return background; 
		case NO_DRAW_BACKGROUND:
			return null;
		default:
			return null;
		}
	}
	
	static public void setDrawBackground(DrawBackgroundImage backgroundImage){
		image = backgroundImage;
	}
	
	static private void setBackground(Background back){
		background = back;
		if(background!=null){
			exportListener.setEnabled(true);
		}else{
			exportListener.setEnabled(false);
		}
	}
	
	static public void registerMenuItem(JMenuItem menu){
		exportListener = menu;
		setBackground(background);
	}
	
	static public void deleteSavedBackground(){
		setBackground(null);
		Main.getMainFrame().getCanvas().repaint();
	}
	
	static public Background getBufferedBackground(){
		return background;
	}
	
}
