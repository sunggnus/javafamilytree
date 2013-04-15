package tree.gui.util;

import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Window;



public class IconUtil {
	
	
	static public void assignIcon(Window frame)
	{
	Image img = frame.getToolkit().getImage(System.class.getResource("/graphics/treeIcon.png"));
	MediaTracker mt = new MediaTracker(frame);

	mt.addImage(img, 0);
	frame.setIconImage(img);
	}
	
	

}
