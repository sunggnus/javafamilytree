package tree.gui.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Window;



public class GUIUtils {
	
	
	static public void assignIcon(Window frame)
	{
	Image img = frame.getToolkit().getImage(System.class.getResource("/graphics/treeIcon.png"));
	MediaTracker mt = new MediaTracker(frame);

	mt.addImage(img, 0);
	frame.setIconImage(img);
	}
	
	static public void normalizeSize(Component comp){
		Dimension pref = comp.getPreferredSize();
		Dimension min = comp.getMinimumSize();
		Dimension max = comp.getMaximumSize();
		normalizeSize(min,pref);
		normalizeSize(pref,max);
		comp.setMaximumSize(max);
		comp.setPreferredSize(pref);
		comp.setMinimumSize(min);
	}
	
	static private void normalizeSize(Dimension min, Dimension pref){
		if(min.getHeight() >pref.getHeight()){
			pref.setSize(pref.getWidth(), min.getHeight());
		}
		if(min.getWidth()>pref.getWidth()){
			pref.setSize(min.getWidth(), pref.getHeight());
		}
	}
	
	

}
